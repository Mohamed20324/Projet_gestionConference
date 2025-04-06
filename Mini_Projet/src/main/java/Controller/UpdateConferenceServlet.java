package Controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import DAO.ConferenceDAO;
import Model.Conference;
import Model.Utilisateur;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/UpdateConferenceServlet")
public class UpdateConferenceServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("utilisateur") == null) {
            response.sendRedirect("Views/login.jsp");
            return;
        }
        
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        
        try {
            // Récupérer l'ID de la conférence
            int conferenceId = Integer.parseInt(request.getParameter("conferenceId"));
            
            // Vérifier que l'utilisateur est le président de la conférence
            Conference conference = ConferenceDAO.getConferenceById(conferenceId);
            if (conference == null || !ConferenceDAO.isPresident(conferenceId, utilisateur.getUtilisateurId())) {
                response.sendRedirect("Views/dashboard.jsp");
                return;
            }
            
            // Récupérer la nouvelle date d'extension
            String dateExtensionStr = request.getParameter("dateExtension");
            if (dateExtensionStr != null && !dateExtensionStr.trim().isEmpty()) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date dateExtension = dateFormat.parse(dateExtensionStr);
                
                // Vérifier que la date d'extension est après la date limite de soumission
                // et avant la date de fin
                if (dateExtension.before(conference.getDateLimiteSoumission())) {
                    request.setAttribute("error", "La date d'extension doit être après la date limite de soumission");
                    request.getRequestDispatcher("Views/conference.jsp?id=" + conferenceId).forward(request, response);
                    return;
                }
                
                if (conference.getDateFin() != null && dateExtension.after(conference.getDateFin())) {
                    request.setAttribute("error", "La date d'extension doit être avant la date de fin");
                    request.getRequestDispatcher("Views/conference.jsp?id=" + conferenceId).forward(request, response);
                    return;
                }
                
                // Mettre à jour la date d'extension
                conference.setDateextension(dateExtension);
                ConferenceDAO.updateConference(conference);
                
                session.setAttribute("success", "La date d'extension a été mise à jour avec succès");
            }
            
            response.sendRedirect("Views/conference.jsp?id=" + conferenceId);
            
        } catch (ParseException e) {
            request.setAttribute("error", "Format de date invalide");
            request.getRequestDispatcher("Views/dashboard.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("Views/dashboard.jsp").forward(request, response);
        }
    }
}
