package Model;

import java.util.ArrayList;
import java.util.List;

public class Topic {
    private int topicID;
    private String topic;
    private Topic parentTopic;
    private List<Topic> subTopics;
    private Conference conference;
    
    public Topic() {
        this.subTopics = new ArrayList<>();
    }
    
    public Topic(int topicID, String topic, Topic parentTopic) {
        this();
        this.topicID = topicID;
        this.topic = topic;
        this.parentTopic = parentTopic;
    }
    
    public int getTopicID() {
        return topicID;
    }
    
    public void setTopicID(int topicID) {
        this.topicID = topicID;
    }
    
    public String getTopic() {
        return topic;
    }
    
    public void setTopic(String topic) {
        this.topic = topic;
    }
    
    public Topic getParentTopic() {
        return parentTopic;
    }
    
    public void setParentTopic(Topic parentTopic) {
        this.parentTopic = parentTopic;
    }
    
    public List<Topic> getSubTopics() {
        return subTopics;
    }
    
    public void setSubTopics(List<Topic> subTopics) {
        this.subTopics = subTopics;
    }
    
    public void addSubTopic(Topic subTopic) {
        if (this.subTopics == null) {
            this.subTopics = new ArrayList<>();
        }
        this.subTopics.add(subTopic);
    }
    
    public Conference getConference() {
        return conference;
    }
    
    public void setConference(Conference conference) {
        this.conference = conference;
    }
    
    public boolean isMainTopic() {
        return parentTopic == null;
    }
}
