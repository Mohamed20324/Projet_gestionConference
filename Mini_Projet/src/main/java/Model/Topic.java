package Model;

public class Topic {
	
	    private int topicID;
	    private String topic;
	    private Topic parentTopic;
		public Topic(int topicID, String topic, Topic parentTopic) {
			super();
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

}
