package Model;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class Conference {
    private int conferenceId;
    private String nomConf;
    private String acronym;
    private String siteConf;
    private String type;
    private String lieuConf;
    private Date dateDebut;
    private Date dateFin;
    private String thematiqueConf;
    private List<String> topics;
    private List<String> subTopics;
    private String logo;
    private Date dateLimiteSoumission;
    private Date dateNotification;
    private Date dateextension;
    private int presidentId;
    
    public Conference() {
        this.topics = new ArrayList<>();
        this.subTopics = new ArrayList<>();
    }
    
    // Getters et Setters
    public int getConferenceId() {
        return conferenceId;
    }
    
    public void setConferenceId(int conferenceId) {
        this.conferenceId = conferenceId;
    }
    
    public int getPresidentId() {
        return presidentId;
    }
    
    public void setPresidentId(int presidentId) {
        this.presidentId = presidentId;
    }
    
    public String getNomConf() {
        return nomConf;
    }
    
    public void setNomConf(String nomConf) {
        this.nomConf = nomConf;
    }
    
    public String getAcronym() {
        return acronym;
    }
    
    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }
    
    public String getSiteConf() {
        return siteConf;
    }
    
    public void setSiteConf(String siteConf) {
        this.siteConf = siteConf;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public String getLieuConf() {
        return lieuConf;
    }
    
    public void setLieuConf(String lieuConf) {
        this.lieuConf = lieuConf;
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
    
    public String getThematiqueConf() {
        return thematiqueConf;
    }
    
    public void setThematiqueConf(String thematiqueConf) {
        this.thematiqueConf = thematiqueConf;
    }
    
    public List<String> getTopics() {
        return topics;
    }
    
    public void setTopics(List<String> topics) {
        this.topics = topics;
    }
    
    public List<String> getSubTopics() {
        return subTopics;
    }
    
    public void setSubTopics(List<String> subTopics) {
        this.subTopics = subTopics;
    }
    
    public String getLogo() {
        return logo;
    }
    
    public void setLogo(String logo) {
        this.logo = logo;
    }
    
    public Date getDateLimiteSoumission() {
        return dateLimiteSoumission;
    }
    
    public void setDateLimiteSoumission(Date dateLimiteSoumission) {
        this.dateLimiteSoumission = dateLimiteSoumission;
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
}
