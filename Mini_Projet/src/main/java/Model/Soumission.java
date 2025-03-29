package Model;

import java.util.Date;

public class Soumission {
    private int soumissionID;
    private Date dateSoumission;
    private String etat;
    private Auteur auteur;
    private Article article;
    private Conference conference;
	public Soumission(int soumissionID, Date dateSoumission, String etat, Auteur auteur, Article article,
			Conference conference) {
		super();
		this.soumissionID = soumissionID;
		this.dateSoumission = dateSoumission;
		this.etat = etat;
		this.auteur = auteur;
		this.article = article;
		this.conference = conference;
	}
	public int getSoumissionID() {
		return soumissionID;
	}
	public void setSoumissionID(int soumissionID) {
		this.soumissionID = soumissionID;
	}
	public Date getDateSoumission() {
		return dateSoumission;
	}
	public void setDateSoumission(Date dateSoumission) {
		this.dateSoumission = dateSoumission;
	}
	public String getEtat() {
		return etat;
	}
	public void setEtat(String etat) {
		this.etat = etat;
	}
	public Auteur getAuteur() {
		return auteur;
	}
	public void setAuteur(Auteur auteur) {
		this.auteur = auteur;
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