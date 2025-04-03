package DAO;
import java.sql.Connection;

import java.sql.SQLException;

import DAO.Database;

public class TestConnexion {
    public static void main(String[] args) {
        try {
            Connection conn = Database.getConnection();
            System.out.println("✅ Connexion réussie !");
            conn.close();
        } catch (SQLException e) {
            System.err.println("❌ Échec de connexion :");
            e.printStackTrace();
        }
    }
}