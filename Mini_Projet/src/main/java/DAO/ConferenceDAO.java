package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Calendar;
import Model.Conference;
import Model.Role;
import Model.TypeRole;
import Model.Utilisateur;

public class ConferenceDAO {
    
    public static int creerConference(Conference conference, int presidentId) throws SQLException {
        // Vérifier les champs obligatoires
        if (conference.getNomConf() == null || conference.getNomConf().trim().isEmpty()) {
            throw new SQLException("Le nom de la conférence est obligatoire");
        }
        if (conference.getDateDebut() == null) {
            throw new SQLException("La date de début est obligatoire");
        }
        if (conference.getDateFin() == null) {
            throw new SQLException("La date de fin est obligatoire");
        }
        if (conference.getDateLimiteSoumission() == null) {
            throw new SQLException("La date limite de soumission est obligatoire");
        }
        if (conference.getDateNotification() == null) {
            throw new SQLException("La date de notification est obligatoire");
        }
        if (conference.getType() == null || conference.getType().trim().isEmpty()) {
            throw new SQLException("Le type de conférence est obligatoire");
        }

        // Générer l'acronyme de base
        String acronymeBase = genererAcronyme(conference.getNomConf(), conference.getDateDebut());
        String acronymeFinal = acronymeBase;
        int compteur = 1;

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int conferenceId = -1;

        try {
            conn = Database.getConnection();
            conn.setAutoCommit(false);

            // Insérer la conférence
            String sql = "INSERT INTO conference (nom_conf, acronym, site_conf, type, lieu_conf, date_debut, " +
                        "date_fin, thematique_conf, logo, date_limite_soumission, date_notification, date_extension, president_id) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, conference.getNomConf());
            pstmt.setString(2, acronymeFinal);
            pstmt.setString(3, conference.getSiteConf());
            pstmt.setString(4, conference.getType());
            pstmt.setString(5, conference.getLieuConf());
            pstmt.setDate(6, new java.sql.Date(conference.getDateDebut().getTime()));
            pstmt.setDate(7, new java.sql.Date(conference.getDateFin().getTime()));
            pstmt.setString(8, conference.getThematiqueConf());
            pstmt.setString(9, conference.getLogo());
            pstmt.setDate(10, new java.sql.Date(conference.getDateLimiteSoumission().getTime()));
            pstmt.setDate(11, new java.sql.Date(conference.getDateNotification().getTime()));
            pstmt.setDate(12, conference.getDateextension() != null ? 
                         new java.sql.Date(conference.getDateextension().getTime()) : null);
            pstmt.setInt(13, presidentId);
            
            pstmt.executeUpdate();
            
            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                conferenceId = rs.getInt(1);
                conference.setConferenceId(conferenceId);
            }

            // Ajouter le rôle de président
            sql = "INSERT INTO role (Typerole, ConferenceID, UtilisateurID) VALUES (?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "PRESIDENT");
            pstmt.setInt(2, conferenceId);
            pstmt.setInt(3, presidentId);
            pstmt.executeUpdate();

            conn.commit();
            return conferenceId;
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw e;
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (pstmt != null) try { pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (conn != null) try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    public static Conference getConferenceById(int conferenceId) throws SQLException {
        String sql = "SELECT * FROM conference WHERE conference_id = ?";
        Conference conference = null;
        
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, conferenceId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                conference = new Conference();
                conference.setConferenceId(rs.getInt("conference_id"));
                conference.setNomConf(rs.getString("nom_conf"));
                conference.setAcronym(rs.getString("acronym"));
                conference.setSiteConf(rs.getString("site_conf"));
                conference.setType(rs.getString("type"));
                conference.setLieuConf(rs.getString("lieu_conf"));
                conference.setDateDebut(rs.getDate("date_debut"));
                conference.setDateFin(rs.getDate("date_fin"));
                conference.setThematiqueConf(rs.getString("thematique_conf"));
                conference.setLogo(rs.getString("logo"));
                conference.setDateLimiteSoumission(rs.getDate("date_limite_soumission"));
                conference.setDateNotification(rs.getDate("date_notification"));
                conference.setDateextension(rs.getDate("date_extension"));
                conference.setPresidentId(rs.getInt("president_id"));
            }
        }
        return conference;
    }

    public List<Conference> getConferencesUtilisateur(int utilisateurId) throws SQLException {
        List<Conference> conferences = new ArrayList<>();
        String sql = "SELECT DISTINCT c.* FROM conference c " +
                    "INNER JOIN role r ON c.conference_id = r.ConferenceID " +
                    "WHERE r.UtilisateurID = ?";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, utilisateurId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Conference conference = new Conference();
                    conference.setConferenceId(rs.getInt("conference_id"));
                    conference.setNomConf(rs.getString("nom_conf"));
                    conference.setAcronym(rs.getString("acronym"));
                    conference.setSiteConf(rs.getString("site_conf"));
                    conference.setType(rs.getString("type"));
                    conference.setLieuConf(rs.getString("lieu_conf"));
                    conference.setDateDebut(rs.getDate("date_debut"));
                    conference.setDateFin(rs.getDate("date_fin"));
                    conference.setThematiqueConf(rs.getString("thematique_conf"));
                    conference.setLogo(rs.getString("logo"));
                    conference.setDateLimiteSoumission(rs.getDate("date_limite_soumission"));
                    conference.setDateNotification(rs.getDate("date_notification"));
                    conference.setDateextension(rs.getDate("date_extension"));
                    conference.setPresidentId(rs.getInt("president_id"));
                    conferences.add(conference);
                }
            }
        }
        return conferences;
    }

    public List<Conference> getConferencesPresident(int presidentId) throws SQLException {
        List<Conference> conferences = new ArrayList<>();
        String sql = "SELECT c.* FROM conference c " +
                    "INNER JOIN role r ON c.conference_id = r.ConferenceID " +
                    "WHERE r.UtilisateurID = ? AND r.Typerole = ?";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, presidentId);
            pstmt.setString(2, "PRESIDENT");
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Conference conference = new Conference();
                    conference.setConferenceId(rs.getInt("conference_id"));
                    conference.setNomConf(rs.getString("nom_conf"));
                    conference.setAcronym(rs.getString("acronym"));
                    conference.setSiteConf(rs.getString("site_conf"));
                    conference.setType(rs.getString("type"));
                    conference.setLieuConf(rs.getString("lieu_conf"));
                    conference.setDateDebut(rs.getDate("date_debut"));
                    conference.setDateFin(rs.getDate("date_fin"));
                    conference.setThematiqueConf(rs.getString("thematique_conf"));
                    conference.setLogo(rs.getString("logo"));
                    conference.setDateLimiteSoumission(rs.getDate("date_limite_soumission"));
                    conference.setDateNotification(rs.getDate("date_notification"));
                    conference.setDateextension(rs.getDate("date_extension"));
                    conference.setPresidentId(rs.getInt("president_id"));
                    conferences.add(conference);
                }
            }
        }
        return conferences;
    }

    public static void updateConference(Conference conference) throws SQLException {
        String sql = "UPDATE conference SET " +
                    "nom_conf = ?, type = ?, date_debut = ?, date_fin = ?, " +
                    "date_limite_soumission = ?, date_notification = ?, date_extension = ?, " +
                    "lieu_conf = ?, site_conf = ?, thematique_conf = ? " +
                    "WHERE conference_id = ?";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, conference.getNomConf());
            pstmt.setString(2, conference.getType());
            pstmt.setDate(3, conference.getDateDebut() != null ? 
                         new java.sql.Date(conference.getDateDebut().getTime()) : null);
            pstmt.setDate(4, conference.getDateFin() != null ? 
                         new java.sql.Date(conference.getDateFin().getTime()) : null);
            pstmt.setDate(5, conference.getDateLimiteSoumission() != null ? 
                         new java.sql.Date(conference.getDateLimiteSoumission().getTime()) : null);
            pstmt.setDate(6, conference.getDateNotification() != null ? 
                         new java.sql.Date(conference.getDateNotification().getTime()) : null);
            pstmt.setDate(7, conference.getDateextension() != null ? 
                         new java.sql.Date(conference.getDateextension().getTime()) : null);
            pstmt.setString(8, conference.getLieuConf());
            pstmt.setString(9, conference.getSiteConf());
            pstmt.setString(10, conference.getThematiqueConf());
            pstmt.setInt(11, conference.getConferenceId());
            
            pstmt.executeUpdate();
        }
    }

    public static boolean isPresident(int conferenceId, int userId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM role WHERE conference_id = ? AND utilisateur_id = ? AND Typerole = ?";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, conferenceId);
            pstmt.setInt(2, userId);
            pstmt.setString(3, "PRESIDENT");
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public boolean isMembrePilotage(int conferenceId, int userId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM comite_pilotage WHERE conference_id = ? AND utilisateur_id = ?";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, conferenceId);
            pstmt.setInt(2, userId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public List<Utilisateur> getMembresPilotageDetails(int conferenceId) throws SQLException {
        List<Utilisateur> membres = new ArrayList<>();
        String sql = "SELECT u.* FROM utilisateur u " +
                    "INNER JOIN comite_pilotage cp ON u.utilisateur_id = cp.utilisateur_id " +
                    "WHERE cp.conference_id = ?";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, conferenceId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Utilisateur membre = new Utilisateur();
                membre.setUtilisateurId(rs.getInt("utilisateur_id"));
                membre.setNom(rs.getString("nom"));
                membre.setPrenom(rs.getString("prenom"));
                membre.setEmail(rs.getString("email"));
                membre.setPays(rs.getString("pays"));
                membre.setSiteWeb(rs.getString("site_web"));
                membres.add(membre);
            }
        }
        return membres;
    }

    public Set<Integer> getMembresPilotage(int conferenceId) throws SQLException {
        Set<Integer> membres = new HashSet<>();
        String sql = "SELECT utilisateur_id FROM comite_pilotage WHERE conference_id = ?";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, conferenceId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                membres.add(rs.getInt("utilisateur_id"));
            }
        }
        return membres;
    }
    
    public boolean updateMembresPilotage(int conferenceId, List<Integer> nouveauxMembres) throws SQLException {
        Connection conn = null;
        try {
            conn = Database.getConnection();
            conn.setAutoCommit(false);
            
            // Supprimer les anciens membres et leurs rôles
            String deleteSql = "DELETE cp, ru FROM comite_pilotage cp " +
                             "LEFT JOIN role_utilisateur ru ON cp.conference_id = ru.conference_id " +
                             "AND cp.utilisateur_id = ru.utilisateur_id " +
                             "AND ru.type_role = 'MEMBRE_PILOTAGE' " +
                             "WHERE cp.conference_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(deleteSql)) {
                pstmt.setInt(1, conferenceId);
                pstmt.executeUpdate();
            }
            
            if (!nouveauxMembres.isEmpty()) {
                // Ajouter les nouveaux membres
                String insertSql = "INSERT INTO comite_pilotage (conference_id, utilisateur_id) VALUES (?, ?)";
                try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
                    for (int membreId : nouveauxMembres) {
                        pstmt.setInt(1, conferenceId);
                        pstmt.setInt(2, membreId);
                        pstmt.addBatch();
                    }
                    pstmt.executeBatch();
                }
                
                // Ajouter le rôle de membre pilotage pour chaque nouveau membre
                String roleSql = "INSERT INTO role_utilisateur (utilisateur_id, conference_id, type_role) VALUES (?, ?, 'MEMBRE_PILOTAGE')";
                try (PreparedStatement pstmt = conn.prepareStatement(roleSql)) {
                    for (int membreId : nouveauxMembres) {
                        pstmt.setInt(1, membreId);
                        pstmt.setInt(2, conferenceId);
                        pstmt.addBatch();
                    }
                    pstmt.executeBatch();
                }
            }
            
            conn.commit();
            return true;
            
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw e;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static List<Map<String, Object>> getConferencesWithRoles(int utilisateurId) throws SQLException {
        List<Map<String, Object>> conferencesWithRoles = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = Database.getConnection();
            String sql = """
                SELECT DISTINCT c.*, ru.Typerole
                FROM conference c
                JOIN role ru ON c.ConferenceID = ru.ConferenceID
                WHERE ru.UtilisateurID = ?
                ORDER BY c.ConferenceID DESC
            """;
            
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, utilisateurId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> conferenceInfo = new HashMap<>();
                Conference conference = new Conference();
                conference.setConferenceId(rs.getInt("conference_id"));
                conference.setNomConf(rs.getString("nom_conf"));
                conference.setAcronym(rs.getString("acronym"));
                conference.setSiteConf(rs.getString("site_conf"));
                conference.setType(rs.getString("type"));
                conference.setLieuConf(rs.getString("lieu_conf"));
                conference.setDateDebut(rs.getDate("date_debut"));
                conference.setDateFin(rs.getDate("date_fin"));
                conference.setThematiqueConf(rs.getString("thematique_conf"));
                conference.setDateLimiteSoumission(rs.getDate("date_limite_soumission"));
                conference.setDateNotification(rs.getDate("date_notification"));
                conference.setDateextension(rs.getDate("date_extension"));
                conference.setPresidentId(rs.getInt("president_id"));
                
                conferenceInfo.put("conference", conference);
                conferenceInfo.put("role", rs.getString("Typerole"));
                conferencesWithRoles.add(conferenceInfo);
            }
        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }

        return conferencesWithRoles;
    }

    private static String genererAcronyme(String nomConference, java.util.Date dateDebut) {
        if (nomConference == null || dateDebut == null) {
            throw new IllegalArgumentException("Le nom de la conférence et la date de début sont requis pour générer l'acronyme");
        }

        // Extraire l'année de la date de début
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateDebut);
        int annee = cal.get(Calendar.YEAR);
        
        // Générer l'acronyme de base à partir du nom
        StringBuilder acronyme = new StringBuilder();
        String[] mots = nomConference.trim().split("\\s+");
        
        // Prendre la première lettre de chaque mot en majuscule
        for (String mot : mots) {
            if (!mot.isEmpty()) {
                acronyme.append(mot.substring(0, 1).toUpperCase());
            }
        }
        
        // Ajouter l'année
        acronyme.append(annee);
        
        return acronyme.toString();
    }
}
