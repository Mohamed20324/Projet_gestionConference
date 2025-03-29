package Model;

public class Detailconfarticle {
    private int detailconfarticleID;
    private String decision;
    private Article article;
    private Conference conference;
	public Detailconfarticle(int detailconfarticleID, String decision, Article article, Conference conference) {
		super();
		this.detailconfarticleID = detailconfarticleID;
		this.decision = decision;
		this.article = article;
		this.conference = conference;
	}
	public int getDetailconfarticleID() {
		return detailconfarticleID;
	}
	public void setDetailconfarticleID(int detailconfarticleID) {
		this.detailconfarticleID = detailconfarticleID;
	}
	public String getDecision() {
		return decision;
	}
	public void setDecision(String decision) {
		this.decision = decision;
	}
	public Article getArticle() {
		return article;
	}
	public void setArticle(Article article) {
		this.article = article;
	}
	public Conference getConference() {
		return conference;
	}
	public void setConference(Conference conference) {
		this.conference = conference;
	}

   
}