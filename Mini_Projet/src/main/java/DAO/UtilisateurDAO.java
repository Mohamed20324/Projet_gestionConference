package DAO;

import Model.Utilisateur;
import java.sql.*;

public class UtilisateurDAO {
    private static final String CHECK_EMAIL_SQL = "SELECT COUNT(*) FROM Utilisateur WHERE email = ?";
    private static final String INSERT_USER_SQL = 
        "INSERT INTO Utilisateur(nom, prenom, email, motdepasse, pays, siteWeb) VALUES (?, ?, ?, ?, ?, ?)";

    public static boolean emailExiste(String email) throws SQLException {
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(CHECK_EMAIL_SQL)) {
            
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
    }

    public static int creerUtilisateur(Utilisateur user) throws SQLException {
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_USER_SQL, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, user.getNom());
            stmt.setString(2, user.getPrenom());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getMotdepasse());
            stmt.setString(5, user.getPays());
            stmt.setString(6, user.getSiteWeb());

            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Échec de la création de l'utilisateur");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Échec de récupération de l'ID utilisateur");
                }
            }
        }
    }
}