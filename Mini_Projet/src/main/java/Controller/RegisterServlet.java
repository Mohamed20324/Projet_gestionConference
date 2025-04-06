package Controller;

import java.io.IOException;
import java.sql.SQLException;

import DAO.UtilisateurDAO;
import Model.Utilisateur;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Récupération des paramètres
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String email = request.getParameter("email");
        String motdepasse = request.getParameter("motdepasse");
        String motdepasseConfirm = request.getParameter("motdepasseConfirm");
        String pays = request.getParameter("pays");
        String siteWeb = request.getParameter("siteWeb");

        // Validation des champs obligatoires
        if (nom == null || prenom == null || email == null || motdepasse == null || 
            nom.trim().isEmpty() || prenom.trim().isEmpty() || email.trim().isEmpty() || motdepasse.trim().isEmpty()) {
            request.setAttribute("error", "Tous les champs obligatoires doivent être remplis");
            request.getRequestDispatcher("Views/register.jsp").forward(request, response);
            return;
        }

        // Validation du mot de passe
        if (!motdepasse.equals(motdepasseConfirm)) {
            request.setAttribute("error", "Les mots de passe ne correspondent pas");
            request.getRequestDispatcher("Views/register.jsp").forward(request, response);
            return;
        }

        // Validation de la longueur du mot de passe
        if (motdepasse.length() < 8) {
            request.setAttribute("error", "Le mot de passe doit contenir au moins 8 caractères");
            request.getRequestDispatcher("Views/register.jsp").forward(request, response);
            return;
        }

        try {
            // Vérification email unique
            if (UtilisateurDAO.emailExiste(email)) {
                request.setAttribute("error", "Cet email existe déjà");
                request.getRequestDispatcher("Views/register.jsp").forward(request, response);
                return;
            }

            // Création de l'utilisateur
            Utilisateur user = new Utilisateur();
            user.setNom(nom);
            user.setPrenom(prenom);
            user.setEmail(email);
            user.setMotdepasse(motdepasse);
            user.setPays(pays);
            user.setSiteWeb(siteWeb);

            // Sauvegarde de l'utilisateur
            int userId = UtilisateurDAO.creerUtilisateur(user);
            user.setUtilisateurId(userId);

            // Redirection vers la page de connexion avec message de succès
            response.sendRedirect("Views/login.jsp?success=true");

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Une erreur est survenue lors de l'inscription");
            request.getRequestDispatcher("Views/register.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("Views/register.jsp").forward(request, response);
    }
}