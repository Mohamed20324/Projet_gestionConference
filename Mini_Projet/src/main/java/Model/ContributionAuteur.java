package Model;

//Classe ContributionAuteur (pour la relation Article-Auteur)
public class ContributionAuteur {
    private Article article;
    private Auteur auteur;
    private int ordreAuteur; 
    
    public ContributionAuteur(Article article, Auteur auteur, int ordre) {
        this.article = article;
        this.auteur = auteur;
        this.ordreAuteur = ordre;
    }
    public Article getArticle() { return article; }
    public Auteur getAuteur() { return auteur; }
    public int getOrdreAuteur() { return ordreAuteur; }
}