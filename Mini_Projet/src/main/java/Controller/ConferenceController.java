package Controller;

import Model.Conference;
import Model.Utilisateur;
import DAO.ConferenceDAO;
import DAO.UtilisateurDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@WebServlet(urlPatterns = {
    "/conference/*",
    "/getConferenceDetails",
    "/updateConference",
    "/conference/membres-disponibles",
    "/conference/update-comite"
})
public class ConferenceController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        
        if (pathInfo == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "URL invalide");
            return;
        }
        
        if (pathInfo.equals("/details")) {
            handleGetConferenceDetails(request, response);
        } else if (pathInfo.equals("/membres-disponibles")) {
            handleGetMembresDisponibles(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Action non trouvée");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        
        if (pathInfo == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "URL invalide");
            return;
        }
        
        if (pathInfo.equals("/update")) {
            handleUpdateConference(request, response);
        } else if (pathInfo.equals("/update-comite")) {
            handleUpdateComite(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Action non trouvée");
        }
    }
    
    private void handleGetMembresDisponibles(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        try {
            int conferenceId = Integer.parseInt(request.getParameter("conferenceId"));
            
            // Vérifier si l'utilisateur est président de la conférence
            Utilisateur utilisateur = (Utilisateur) request.getSession().getAttribute("utilisateur");
            ConferenceDAO conferenceDAO = new ConferenceDAO();
            Conference conference = conferenceDAO.getConferenceById(conferenceId);
            
            if (conference == null || conference.getPresidentId() != utilisateur.getUtilisateurId()) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Accès non autorisé");
                return;
            }
            
            // Récupérer tous les utilisateurs disponibles
            UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
            List<Map<String, Object>> membresInfo = new ArrayList<>();
            
            List<Utilisateur> tousUtilisateurs = utilisateurDAO.getAllUtilisateurs();
            Set<Integer> membresPilotage = conferenceDAO.getMembresPilotage(conferenceId);
            
            for (Utilisateur membre : tousUtilisateurs) {
                if (membre.getUtilisateurId() != conference.getPresidentId()) {
                    Map<String, Object> membreInfo = new HashMap<>();
                    membreInfo.put("utilisateurId", membre.getUtilisateurId());
                    membreInfo.put("nom", membre.getNom());
                    membreInfo.put("prenom", membre.getPrenom());
                    membreInfo.put("estMembreComite", membresPilotage.contains(membre.getUtilisateurId()));
                    membresInfo.add(membreInfo);
                }
            }
            
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(membresInfo.toString());
            
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur lors de la récupération des membres");
        }
    }
    
    private void handleUpdateComite(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        try {
            // Lire le corps de la requête
            StringBuilder buffer = new StringBuilder();
            String line;
            while ((line = request.getReader().readLine()) != null) {
                buffer.append(line);
            }
            
            // Parser le JSON
            Map<String, Object> jsonData = new HashMap<>();
            jsonData.put("conferenceId", buffer.toString().split("\"conferenceId\":")[1].split(",")[0].replaceAll("[^0-9]", ""));
            jsonData.put("membres", buffer.toString().split("\"membres\":")[1].split("]")[0].replaceAll("[^0-9,]", "").split(","));
            
            int conferenceId = ((String) jsonData.get("conferenceId")).isEmpty() ? 0 : Integer.parseInt((String) jsonData.get("conferenceId"));
            List<Integer> membres = new ArrayList<>();
            for (String d : (String[]) jsonData.get("membres")) {
                membres.add(d.isEmpty() ? 0 : Integer.parseInt(d));
            }
            
            // Vérifier si l'utilisateur est président de la conférence
            Utilisateur utilisateur = (Utilisateur) request.getSession().getAttribute("utilisateur");
            ConferenceDAO conferenceDAO = new ConferenceDAO();
            Conference conference = conferenceDAO.getConferenceById(conferenceId);
            
            if (conference == null || conference.getPresidentId() != utilisateur.getUtilisateurId()) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Accès non autorisé");
                return;
            }
            
            // Mettre à jour les membres du comité
            boolean success = conferenceDAO.updateMembresPilotage(conferenceId, membres);
            
            Map<String, Object> result = new HashMap<>();
            result.put("success", success);
            
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(result.toString());
            
        } catch (Exception e) {
            Map<String, Object> result = new HashMap<>();
            result.put("success", false);
            result.put("error", e.getMessage());
            
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(result.toString());
        }
    }
    
    private void handleGetConferenceDetails(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            int conferenceId = Integer.parseInt(request.getParameter("id"));
            Conference conference = ConferenceDAO.getConferenceById(conferenceId);
            
            if (conference != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat inputSdf = new SimpleDateFormat("yyyy-MM-dd");
                StringBuilder html = new StringBuilder();
                
                try {
                    if ("edit".equals(request.getParameter("mode"))) {
                        // Formulaire de modification
                        html.append("<div class=\"modal-body\">");
                        html.append("<input type=\"hidden\" name=\"conferenceId\" value=\"").append(conference.getConferenceId()).append("\">");
                        
                        html.append("<div class=\"row mb-3\">");
                        html.append("<div class=\"col-md-6\">");
                        html.append("<label class=\"form-label\">Nom de la conférence</label>");
                        html.append("<input type=\"text\" class=\"form-control\" name=\"nomConf\" value=\"")
                            .append(conference.getNomConf()).append("\" required>");
                        html.append("</div>");
                        html.append("<div class=\"col-md-6\">");
                        html.append("<label class=\"form-label\">Type</label>");
                        html.append("<select class=\"form-select\" name=\"type\" required>");
                        for (String type : new String[]{"PHYSIQUE", "VIRTUELLE", "HYBRIDE"}) {
                            html.append("<option value=\"").append(type).append("\"")
                                .append(type.equals(conference.getType()) ? " selected" : "")
                                .append(">").append(type).append("</option>");
                        }
                        html.append("</select>");
                        html.append("</div></div>");
                        
                        // Dates
                        html.append("<div class=\"row mb-3\">");
                        html.append("<div class=\"col-md-6\">");
                        html.append("<label class=\"form-label\">Date de début</label>");
                        html.append("<input type=\"date\" class=\"form-control\" name=\"dateDebut\" value=\"")
                            .append(conference.getDateDebut() != null ? inputSdf.format(conference.getDateDebut()) : "")
                            .append("\" required>");
                        html.append("</div>");
                        html.append("<div class=\"col-md-6\">");
                        html.append("<label class=\"form-label\">Date de fin</label>");
                        html.append("<input type=\"date\" class=\"form-control\" name=\"dateFin\" value=\"")
                            .append(conference.getDateFin() != null ? inputSdf.format(conference.getDateFin()) : "")
                            .append("\" required>");
                        html.append("</div></div>");
                        
                        html.append("<div class=\"row mb-3\">");
                        html.append("<div class=\"col-md-4\">");
                        html.append("<label class=\"form-label\">Date limite de soumission</label>");
                        html.append("<input type=\"date\" class=\"form-control\" name=\"dateLimiteSoumission\" value=\"")
                            .append(conference.getDateLimiteSoumission() != null ? inputSdf.format(conference.getDateLimiteSoumission()) : "")
                            .append("\" required>");
                        html.append("</div>");
                        html.append("<div class=\"col-md-4\">");
                        html.append("<label class=\"form-label\">Date de notification</label>");
                        html.append("<input type=\"date\" class=\"form-control\" name=\"dateNotification\" value=\"")
                            .append(conference.getDateNotification() != null ? inputSdf.format(conference.getDateNotification()) : "")
                            .append("\" required>");
                        html.append("</div>");
                        html.append("<div class=\"col-md-4\">");
                        html.append("<label class=\"form-label\">Date d'extension (optionnelle)</label>");
                        html.append("<input type=\"date\" class=\"form-control\" name=\"dateExtension\" value=\"")
                            .append(conference.getDateextension() != null ? inputSdf.format(conference.getDateextension()) : "")
                            .append("\">");
                        html.append("</div></div>");
                        
                        html.append("<div class=\"row mb-3\">");
                        html.append("<div class=\"col-md-4\">");
                        html.append("<label class=\"form-label\">Lieu</label>");
                        html.append("<input type=\"text\" class=\"form-control\" name=\"lieuConf\" value=\"")
                            .append(conference.getLieuConf() != null ? conference.getLieuConf() : "")
                            .append("\" required>");
                        html.append("</div>");
                        html.append("<div class=\"col-md-4\">");
                        html.append("<label class=\"form-label\">Site web</label>");
                        html.append("<input type=\"url\" class=\"form-control\" name=\"siteConf\" value=\"")
                            .append(conference.getSiteConf() != null ? conference.getSiteConf() : "")
                            .append("\" required>");
                        html.append("</div>");
                        html.append("<div class=\"col-md-4\">");
                        html.append("<label class=\"form-label\">Thématique</label>");
                        html.append("<input type=\"text\" class=\"form-control\" name=\"thematiqueConf\" value=\"")
                            .append(conference.getThematiqueConf() != null ? conference.getThematiqueConf() : "")
                            .append("\" required>");
                        html.append("</div></div>");
                        
                        html.append("<div class=\"modal-footer\">");
                        html.append("<button type=\"button\" class=\"btn btn-secondary\" data-bs-dismiss=\"modal\">Annuler</button>");
                        html.append("<button type=\"submit\" class=\"btn btn-primary\">Enregistrer</button>");
                        html.append("</div>");
                        html.append("</div>");
                    } else {
                        // Affichage des détails
                        // ... (code existant pour l'affichage)
                    }
                    
                    response.setContentType("text/html;charset=UTF-8");
                    response.getWriter().write(html.toString());
                    
                } catch (Exception e) {
                    System.err.println("Erreur lors de la génération du HTML: " + e.getMessage());
                    e.printStackTrace();
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur lors de la génération du formulaire");
                }
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Conférence non trouvée");
            }
        } catch (NumberFormatException e) {
            System.err.println("ID de conférence invalide: " + request.getParameter("id"));
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de conférence invalide");
        } catch (SQLException e) {
            System.err.println("Erreur SQL: " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur lors de la récupération de la conférence: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erreur inattendue: " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur inattendue: " + e.getMessage());
        }
    }
    
    private void handleUpdateConference(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            // Vérifier si l'utilisateur est connecté et est président de la conférence
            Utilisateur user = (Utilisateur) request.getSession().getAttribute("user");
            
            String conferenceIdStr = request.getParameter("conferenceId");
            System.out.println("ID de conférence reçu: " + conferenceIdStr);
            
            if (conferenceIdStr == null || conferenceIdStr.trim().isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("ID de conférence manquant");
                return;
            }
            
            int conferenceId = Integer.parseInt(conferenceIdStr.trim());
            System.out.println("ID de conférence parsé: " + conferenceId);
            
            System.out.println("Tentative de mise à jour de la conférence ID: " + conferenceId);
            System.out.println("Utilisateur: " + (user != null ? user.getNom() : "non connecté"));
            
            if (user == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Utilisateur non connecté");
                return;
            }
            
            if (!ConferenceDAO.isPresident(conferenceId, user.getUtilisateurId())) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("Accès non autorisé");
                return;
            }
            
            // Créer l'objet Conference avec les données du formulaire
            Conference conference = new Conference();
            conference.setConferenceId(conferenceId);
            conference.setNomConf(request.getParameter("nomConf"));
            conference.setType(request.getParameter("type"));
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            
            try {
                String dateDebut = request.getParameter("dateDebut");
                System.out.println("Date début reçue: " + dateDebut);
                if (dateDebut != null && !dateDebut.isEmpty()) {
                    conference.setDateDebut(sdf.parse(dateDebut));
                }
                
                String dateFin = request.getParameter("dateFin");
                System.out.println("Date fin reçue: " + dateFin);
                if (dateFin != null && !dateFin.isEmpty()) {
                    conference.setDateFin(sdf.parse(dateFin));
                }
                
                String dateLimiteSoumission = request.getParameter("dateLimiteSoumission");
                System.out.println("Date limite soumission reçue: " + dateLimiteSoumission);
                if (dateLimiteSoumission != null && !dateLimiteSoumission.isEmpty()) {
                    conference.setDateLimiteSoumission(sdf.parse(dateLimiteSoumission));
                }
                
                String dateNotification = request.getParameter("dateNotification");
                System.out.println("Date notification reçue: " + dateNotification);
                if (dateNotification != null && !dateNotification.isEmpty()) {
                    conference.setDateNotification(sdf.parse(dateNotification));
                }
                
                String dateExtension = request.getParameter("dateExtension");
                System.out.println("Date extension reçue: " + dateExtension);
                if (dateExtension != null && !dateExtension.isEmpty()) {
                    conference.setDateextension(sdf.parse(dateExtension));
                }
            } catch (ParseException e) {
                System.err.println("Erreur de parsing des dates: " + e.getMessage());
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Format de date invalide: " + e.getMessage());
                return;
            }
            
            conference.setLieuConf(request.getParameter("lieuConf"));
            conference.setSiteConf(request.getParameter("siteConf"));
            conference.setThematiqueConf(request.getParameter("thematiqueConf"));
            
            // Mettre à jour la conférence
            ConferenceDAO.updateConference(conference);
            
            // Envoyer une réponse de succès
            response.setContentType("text/plain");
            response.getWriter().write("La conférence a été mise à jour avec succès");
            
        } catch (SQLException e) {
            System.err.println("Erreur SQL: " + e.getMessage());
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Erreur lors de la mise à jour de la conférence: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Erreur de format d'ID: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("ID de conférence invalide");
        }
    }
}
