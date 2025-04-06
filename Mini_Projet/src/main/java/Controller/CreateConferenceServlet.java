package Controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import DAO.ConferenceDAO;
import Model.Conference;
import Model.Utilisateur;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@WebServlet("/CreateConferenceServlet")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024, // 1 MB
    maxFileSize = 1024 * 1024 * 5,   // 5 MB
    maxRequestSize = 1024 * 1024 * 10 // 10 MB
)
public class CreateConferenceServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("utilisateur") == null) {
            response.sendRedirect("Views/login.jsp");
            return;
        }
        
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        
        try {
            // Récupérer les paramètres obligatoires
            String nomConf = request.getParameter("nomConf");
            String dateDebutStr = request.getParameter("dateDebut");
            String dateFinStr = request.getParameter("dateFin");
            String dateLimiteSoumissionStr = request.getParameter("dateLimiteSoumission");
            String dateNotificationStr = request.getParameter("dateNotification");
            String type = request.getParameter("type");
            String thematiqueConf = request.getParameter("thematiqueConf");
            
            // Validation des champs obligatoires
            if (nomConf == null || nomConf.trim().isEmpty()) {
                request.setAttribute("error", "Le nom de la conférence est obligatoire");
                request.getRequestDispatcher("Views/dashboard.jsp").forward(request, response);
                return;
            }
            
            // Récupérer les paramètres optionnels
            String lieuConf = request.getParameter("lieuConf");
            String siteConf = request.getParameter("siteConf");
            String topicsStr = request.getParameter("topics");
            String subTopicsStr = request.getParameter("subTopics");
            
            // Traiter le logo
            String logoPath = null;
            Part filePart = request.getPart("logo");
            if (filePart != null && filePart.getSize() > 0) {
                String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                String extension = fileName.substring(fileName.lastIndexOf("."));
                String newFileName = "logo_" + System.currentTimeMillis() + extension;
                
                // Créer le dossier uploads s'il n'existe pas
                Path uploadsDir = Paths.get(getServletContext().getRealPath("/uploads"));
                if (!Files.exists(uploadsDir)) {
                    Files.createDirectories(uploadsDir);
                }
                
                // Sauvegarder le fichier
                Path filePath = uploadsDir.resolve(newFileName);
                try (InputStream input = filePart.getInputStream()) {
                    Files.copy(input, filePath, StandardCopyOption.REPLACE_EXISTING);
                }
                logoPath = "uploads/" + newFileName;
            }
            
            // Parser les dates
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date dateDebut = dateFormat.parse(dateDebutStr);
            Date dateFin = dateFormat.parse(dateFinStr);
            Date dateLimiteSoumission = dateFormat.parse(dateLimiteSoumissionStr);
            Date dateNotification = dateFormat.parse(dateNotificationStr);
            
            // Validation des dates
            if (dateFin.before(dateDebut)) {
                request.setAttribute("error", "La date de fin doit être après la date de début");
                request.getRequestDispatcher("Views/dashboard.jsp").forward(request, response);
                return;
            }
            
            if (dateLimiteSoumission.after(dateFin)) {
                request.setAttribute("error", "La date limite de soumission doit être avant la date de fin");
                request.getRequestDispatcher("Views/dashboard.jsp").forward(request, response);
                return;
            }
            
            if (dateNotification.after(dateFin)) {
                request.setAttribute("error", "La date de notification doit être avant la date de fin");
                request.getRequestDispatcher("Views/dashboard.jsp").forward(request, response);
                return;
            }
            
            // Créer l'objet Conference
            Conference conference = new Conference();
            conference.setNomConf(nomConf);
            conference.setType(type);
            conference.setThematiqueConf(thematiqueConf);
            conference.setDateDebut(dateDebut);
            conference.setDateFin(dateFin);
            conference.setDateLimiteSoumission(dateLimiteSoumission);
            conference.setDateNotification(dateNotification);
            conference.setLieuConf(lieuConf);
            conference.setSiteConf(siteConf);
            conference.setLogo(logoPath);
            
            // Traiter les topics et sous-topics
            if (topicsStr != null && !topicsStr.trim().isEmpty()) {
                List<String> topics = Arrays.stream(topicsStr.split("\n"))
                                          .map(String::trim)
                                          .filter(s -> !s.isEmpty())
                                          .collect(Collectors.toList());
                conference.setTopics(topics);
            }
            
            if (subTopicsStr != null && !subTopicsStr.trim().isEmpty()) {
                List<String> subTopics = Arrays.stream(subTopicsStr.split("\n"))
                                             .map(String::trim)
                                             .filter(s -> !s.isEmpty())
                                             .collect(Collectors.toList());
                conference.setSubTopics(subTopics);
            }
            
            // Sauvegarder la conférence
            int conferenceId = ConferenceDAO.creerConference(conference, utilisateur.getUtilisateurId());
            
            // Rediriger vers le dashboard avec message de succès
            session.setAttribute("success", "Conférence créée avec succès. Acronyme généré : " + conference.getAcronym());
            response.sendRedirect("Views/dashboard.jsp");
            
        } catch (ParseException e) {
            request.setAttribute("error", "Format de date invalide");
            request.getRequestDispatcher("Views/dashboard.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("Views/dashboard.jsp").forward(request, response);
        }
    }
}
