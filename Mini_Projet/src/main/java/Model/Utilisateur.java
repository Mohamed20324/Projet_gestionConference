package Model;

import java.util.List;

public class Utilisateur {
    protected int utilisateurId;
    protected String nom;
    protected String prenom;
    protected String email;
    protected String motdepasse;
    protected String pays;
    protected String siteWeb;

    public Utilisateur() {
    }

    public Utilisateur(int utilisateurId, String nom, String prenom, String email, String motdepasse, String pays, String siteWeb) {
        this.utilisateurId = utilisateurId;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motdepasse = motdepasse;
        this.pays = pays;
        this.siteWeb = siteWeb;
    }

    public int getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(int utilisateurId) {
        this.utilisateurId = utilisateurId;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotdepasse() {
        return motdepasse;
    }

    public void setMotdepasse(String motdepasse) {
        this.motdepasse = motdepasse;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getSiteWeb() {
        return siteWeb;
    }

    public void setSiteWeb(String siteWeb) {
        this.siteWeb = siteWeb;
    }
}
