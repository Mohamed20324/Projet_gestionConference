package Controller;

import DAO.UtilisateurDAO;
import Model.Utilisateur;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String email = request.getParameter("email");
        String motdepasse = request.getParameter("password");

        try {
            Utilisateur utilisateur = UtilisateurDAO.getUtilisateur(email, motdepasse);
            
            if (utilisateur != null) {
                HttpSession session = request.getSession();
                session.setAttribute("utilisateur", utilisateur);
                session.setAttribute("utilisateurId", utilisateur.getUtilisateurId());
                session.setAttribute("email", utilisateur.getEmail());
                session.setAttribute("nom", utilisateur.getNom());
                session.setAttribute("prenom", utilisateur.getPrenom());

                // Redirection vers le dashboard
                response.sendRedirect("Views/dashboard.jsp");
            } else {
                response.sendRedirect("Views/login.jsp?error=Email ou mot de passe incorrect");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("Views/login.jsp?error=Erreur de base de données");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("Views/login.jsp").forward(request, response);
    }
}