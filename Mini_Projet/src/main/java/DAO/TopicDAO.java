package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Model.Topic;
import Model.Conference;

public class TopicDAO {
    
    public static Topic getTopicById(int topicId) throws SQLException {
        String query = "SELECT * FROM Topic WHERE TopicID = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, topicId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Topic topic = new Topic();
                    topic.setTopicID(rs.getInt("TopicID"));
                    topic.setTopic(rs.getString("Topic"));
                    
                    int parentTopicId = rs.getInt("ParentTopicID");
                    if (!rs.wasNull()) {
                        topic.setParentTopic(getTopicById(parentTopicId));
                    }
                    
                    // Charger les sous-topics
                    topic.setSubTopics(getSubTopics(topicId));
                    
                    return topic;
                }
            }
        }
        return null;
    }
    
    public static List<Topic> getSubTopics(int parentTopicId) throws SQLException {
        List<Topic> subTopics = new ArrayList<>();
        String query = "SELECT * FROM Topic WHERE ParentTopicID = ?";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, parentTopicId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Topic subTopic = new Topic();
                    subTopic.setTopicID(rs.getInt("TopicID"));
                    subTopic.setTopic(rs.getString("Topic"));
                    subTopics.add(subTopic);
                }
            }
        }
        return subTopics;
    }
    
    public static List<Topic> getMainTopics(int conferenceId) throws SQLException {
        List<Topic> mainTopics = new ArrayList<>();
        String query = "SELECT t.* FROM Topic t " +
                      "JOIN ConferenceTopic ct ON t.TopicID = ct.TopicID " +
                      "WHERE ct.ConferenceID = ? AND t.ParentTopicID IS NULL";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, conferenceId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Topic topic = new Topic();
                    topic.setTopicID(rs.getInt("TopicID"));
                    topic.setTopic(rs.getString("Topic"));
                    topic.setSubTopics(getSubTopics(topic.getTopicID()));
                    mainTopics.add(topic);
                }
            }
        }
        return mainTopics;
    }
    
    public static void createTopic(Topic topic, int conferenceId) throws SQLException {
        String query = "INSERT INTO Topic (Topic, ParentTopicID) VALUES (?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, topic.getTopic());
            if (topic.getParentTopic() != null) {
                stmt.setInt(2, topic.getParentTopic().getTopicID());
            } else {
                stmt.setNull(2, java.sql.Types.INTEGER);
            }
            
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    topic.setTopicID(rs.getInt(1));
                    
                    // Ajouter l'association avec la conférence si c'est un topic principal
                    if (topic.getParentTopic() == null) {
                        String confTopicQuery = "INSERT INTO ConferenceTopic (ConferenceID, TopicID) VALUES (?, ?)";
                        try (PreparedStatement confTopicStmt = conn.prepareStatement(confTopicQuery)) {
                            confTopicStmt.setInt(1, conferenceId);
                            confTopicStmt.setInt(2, topic.getTopicID());
                            confTopicStmt.executeUpdate();
                        }
                    }
                }
            }
        }
    }
    
    public static void updateTopic(Topic topic) throws SQLException {
        String query = "UPDATE Topic SET Topic = ?, ParentTopicID = ? WHERE TopicID = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, topic.getTopic());
            if (topic.getParentTopic() != null) {
                stmt.setInt(2, topic.getParentTopic().getTopicID());
            } else {
                stmt.setNull(2, java.sql.Types.INTEGER);
            }
            stmt.setInt(3, topic.getTopicID());
            
            stmt.executeUpdate();
        }
    }
    
    public static void deleteTopic(int topicId) throws SQLException {
        // D'abord supprimer tous les sous-topics
        String deleteSubTopicsQuery = "DELETE FROM Topic WHERE ParentTopicID = ?";
        String deleteTopicQuery = "DELETE FROM Topic WHERE TopicID = ?";
        String deleteConfTopicQuery = "DELETE FROM ConferenceTopic WHERE TopicID = ?";
        
        try (Connection conn = Database.getConnection()) {
            // Supprimer l'association avec la conférence
            try (PreparedStatement stmt = conn.prepareStatement(deleteConfTopicQuery)) {
                stmt.setInt(1, topicId);
                stmt.executeUpdate();
            }
            
            // Supprimer les sous-topics
            try (PreparedStatement stmt = conn.prepareStatement(deleteSubTopicsQuery)) {
                stmt.setInt(1, topicId);
                stmt.executeUpdate();
            }
            
            // Supprimer le topic lui-même
            try (PreparedStatement stmt = conn.prepareStatement(deleteTopicQuery)) {
                stmt.setInt(1, topicId);
                stmt.executeUpdate();
            }
        }
    }
}
