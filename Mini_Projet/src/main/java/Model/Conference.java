package Model;

import java.util.Date;
import java.util.List;

public class Conference {
    private int conferenceID;
    private String acronym;
    private Date dateDebut;
    private Date dateFin;
    private Date dateNotification;
    private Date dateextension;
    private String lieuConf;
    private String logo;
    private String nomConf;
    private String siteConf;
    private String thematiqueConf;
    private String type;
    private President president;
    private List<Topic> topics;
	public int getConferenceID() {
		return conferenceID;
	}
	public void setConferenceID(int conferenceID) {
		this.conferenceID = conferenceID;
	}
	public String getAcronym() {
		return acronym;
	}
	public void setAcronym(String acronym) {
		this.acronym = acronym;
	}
	public Date getDateDebut() {
		return dateDebut;
	}
	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}
	public Date getDateFin() {
		return dateFin;
	}
	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}
	public Date getDateNotification() {
		return dateNotification;
	}
	public void setDateNotification(Date dateNotification) {
		this.dateNotification = dateNotification;
	}
	public Date getDateextension() {
		return dateextension;
	}
	public void setDateextension(Date dateextension) {
		this.dateextension = dateextension;
	}
	public String getLieuConf() {
		return lieuConf;
	}
	public void setLieuConf(String lieuConf) {
		this.lieuConf = lieuConf;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getNomConf() {
		return nomConf;
	}
	public void setNomConf(String nomConf) {
		this.nomConf = nomConf;
	}
	public String getSiteConf() {
		return siteConf;
	}
	public void setSiteConf(String siteConf) {
		this.siteConf = siteConf;
	}
	public String getThematiqueConf() {
		return thematiqueConf;
	}
	public void setThematiqueConf(String thematiqueConf) {
		this.thematiqueConf = thematiqueConf;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public President getPresident() {
		return president;
	}
	public void setPresident(President president) {
		this.president = president;
	}
	public List<Topic> getTopics() {
		return topics;
	}
	public void setTopics(List<Topic> topics) {
		this.topics = topics;
	}
	public Conference(int conferenceID, String acronym, Date dateDebut, Date dateFin, Date dateNotification,
			Date dateextension, String lieuConf, String logo, String nomConf, String siteConf, String thematiqueConf,
			String type, President president, List<Topic> topics) {
		super();
		this.conferenceID = conferenceID;
		this.acronym = acronym;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.dateNotification = dateNotification;
		this.dateextension = dateextension;
		this.lieuConf = lieuConf;
		this.logo = logo;
		this.nomConf = nomConf;
		this.siteConf = siteConf;
		this.thematiqueConf = thematiqueConf;
		this.type = type;
		this.president = president;
		this.topics = topics;
	}
    
}
