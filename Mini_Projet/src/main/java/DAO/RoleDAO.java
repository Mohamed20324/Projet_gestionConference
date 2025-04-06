package DAO;

import Model.Conference;
import Model.Role;
import Model.TypeRole;
import Model.Utilisateur;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoleDAO {
    private static final String INSERT_ROLE_SQL = 
            "INSERT INTO Role (ConferenceID, UtilisateurID, TypeRole) VALUES (?, ?, ?)";
    private static final String GET_USER_ROLES_SQL = 
            "SELECT r.*, c.* FROM Role r JOIN Conference c ON r.ConferenceID = c.ConferenceID WHERE r.UtilisateurID = ?";
    private static final String GET_CONFERENCE_ROLES_SQL = 
            "SELECT r.*, u.* FROM Role r JOIN Utilisateur u ON r.UtilisateurID = u.UtilisateurID WHERE r.ConferenceID = ?";
    private static final String CHECK_USER_ROLE_SQL = 
            "SELECT TypeRole FROM Role WHERE ConferenceID = ? AND UtilisateurID = ?";

    public static void attribuerRole(int conferenceId, int utilisateurId, TypeRole typeRole) throws SQLException {
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_ROLE_SQL)) {
            
            stmt.setInt(1, conferenceId);
            stmt.setInt(2, utilisateurId);
            stmt.setString(3, typeRole.name());
            
            stmt.executeUpdate();
        }
    }

    public static List<Role> getRolesUtilisateur(int utilisateurId) throws SQLException {
        List<Role> roles = new ArrayList<>();
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_USER_ROLES_SQL)) {
            
            stmt.setInt(1, utilisateurId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Role role = new Role();
                role.setRoleID(rs.getInt("RoleID"));
                role.setTypeRole(TypeRole.valueOf(rs.getString("TypeRole")));
                
                Conference conference = new Conference();
                conference.setConferenceID(rs.getInt("ConferenceID"));
                conference.setType(rs.getString("Titre"));
                role.setConference(conference);
                
                roles.add(role);
            }
        }
        return roles;
    }

    public static TypeRole getRoleUtilisateurConference(int conferenceId, int utilisateurId) throws SQLException {
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(CHECK_USER_ROLE_SQL)) {
            
            stmt.setInt(1, conferenceId);
            stmt.setInt(2, utilisateurId);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return TypeRole.valueOf(rs.getString("TypeRole"));
            }
            return TypeRole.SANS_ROLE;
        }
    }

    public static List<Role> getRolesConference(int conferenceId) throws SQLException {
        List<Role> roles = new ArrayList<>();
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_CONFERENCE_ROLES_SQL)) {
            
            stmt.setInt(1, conferenceId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Role role = new Role();
                role.setRoleID(rs.getInt("RoleID"));
                role.setTypeRole(TypeRole.valueOf(rs.getString("TypeRole")));
                
                Utilisateur utilisateur = new Utilisateur();
                utilisateur.setUtilisateurId(rs.getInt("UtilisateurID"));
                utilisateur.setNom(rs.getString("Nom"));
                utilisateur.setPrenom(rs.getString("Prenom"));
                utilisateur.setEmail(rs.getString("email"));
                role.setUtilisateur(utilisateur);
                
                roles.add(role);
            }
        }
        return roles;
    }
}
