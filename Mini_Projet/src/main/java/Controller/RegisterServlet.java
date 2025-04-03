package Controller;

import java.io.IOException;
import java.sql.SQLException;

import DAO.MembreDAO;
import DAO.UtilisateurDAO;
import Model.Membre;
import Model.Role;
import Model.TypeRole;
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
    	 response.getWriter().println("La servlet fonctionne !");
        // Récupération des paramètres
        String nom = request.getParameter("lastName");
        String prenom = request.getParameter("firstName");
        String email = request.getParameter("email");
        String motdepasse = request.getParameter("password");
        String pays = request.getParameter("country");
        String siteWeb = request.getParameter("website");
        String institution = request.getParameter("institution");

        try {
            // Vérification email unique
            if(UtilisateurDAO.emailExiste(email)) {
                request.setAttribute("errorMessage", "Cet email existe déjà");
                request.getRequestDispatcher("register.jsp").forward(request, response);
                return;
            }

            // Création du rôle par défaut
            Role role = new Role();
            role.setTypeRole(TypeRole.AUTEUR); // À adapter selon votre logique

            // Création utilisateur
            Utilisateur user = new Utilisateur(
                0, 
                nom,
                prenom,
                email,
                motdepasse,
                pays,
                siteWeb,
                role
            );

            // Sauvegarde utilisateur
            int userId = UtilisateurDAO.creerUtilisateur(user);
            user.setUtilisateurId(userId);

            // Si institution existe, créer membre
            if(institution != null && !institution.isEmpty()) {
                Membre membre = new Membre(
                    userId,
                    nom,
                    prenom,
                    email,
                    motdepasse,
                    pays,
                    siteWeb,
                    role
                );
                membre.setInstitution(institution);
                MembreDAO.creerMembre(membre);
            }

            response.sendRedirect("login.jsp?success=Inscription réussie");
            
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Erreur de base de données");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }
}