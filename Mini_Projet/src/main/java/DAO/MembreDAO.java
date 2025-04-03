package DAO;

import Model.Membre;
import java.sql.*;

public class MembreDAO {
    private static final String INSERT_MEMBER_SQL = 
        "INSERT INTO Membre(MembreID, biographie, institution, titre) VALUES (?, ?, ?, ?)";

    public static void creerMembre(Membre membre) throws SQLException {
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_MEMBER_SQL)) {
            
            stmt.setInt(1, membre.getUtilisateurId());
            stmt.setString(2, membre.getBiographie());
            stmt.setString(3, membre.getInstitution());
            stmt.setString(4, membre.getTitre());
            
            stmt.executeUpdate();
        }
    }
}