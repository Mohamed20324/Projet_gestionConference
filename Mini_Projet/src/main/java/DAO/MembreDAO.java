package DAO;

import Model.Membre;
import java.sql.*;

public class MembreDAO {
    private static final String INSERT_MEMBER_SQL = 
        "INSERT INTO Membre (Biographie, Institution, Titre, UtilisateurID) VALUES (?, ?, ?, ?)";

    public static void creerMembre(Membre membre) throws SQLException {
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_MEMBER_SQL)) {
            
            // Correction : Ordre aligné avec la requête SQL
            stmt.setString(1, membre.getBiographie());   // Biographie
            stmt.setString(2, membre.getInstitution());  // Institution
            stmt.setString(3, membre.getTitre());        // Titre
            stmt.setInt(4, membre.getUtilisateurId());   // UtilisateurID
            
            stmt.executeUpdate();
        }
    }
}