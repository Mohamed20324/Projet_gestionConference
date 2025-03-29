package Model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Article {
	    private int articleID;
	    private String fichierPdf;
	    private String motsCle;
	    private String resume;
	    private String titre;
	    private Membresc membresc;
	    private List<Auteur> auteurs;
	    private List<ContributionAuteur> contributionsAuteurs = new ArrayList<>();
		public int getArticleID() {
			return articleID;
		}
		public void setArticleID(int articleID) {
			this.articleID = articleID;
		}
		public String getFichierPdf() {
			return fichierPdf;
		}
		public void setFichierPdf(String fichierPdf) {
			this.fichierPdf = fichierPdf;
		}
		public String getMotsCle() {
			return motsCle;
		}
		public void setMotsCle(String motsCle) {
			this.motsCle = motsCle;
		}
		public String getResume() {
			return resume;
		}
		public void setResume(String resume) {
			this.resume = resume;
		}
		public String getTitre() {
			return titre;
		}
		public void setTitre(String titre) {
			this.titre = titre;
		}
		public Membresc getMembresc() {
			return membresc;
		}
		public void setMembresc(Membresc membresc) {
			this.membresc = membresc;
		}
		public void setAuteurs(List<Auteur> auteurs) {
			this.auteurs = auteurs;
		}
		public Article(int articleID, String fichierPdf, String motsCle, String resume, String titre, Membresc membresc,
				List<Auteur> auteurs) {
			super();
			this.articleID = articleID;
			this.fichierPdf = fichierPdf;
			this.motsCle = motsCle;
			this.resume = resume;
			this.titre = titre;
			this.membresc = membresc;
			this.auteurs = auteurs;
		}
		public void ajouterContribution(Auteur auteur, int ordre) {
	        contributionsAuteurs.add(new ContributionAuteur(this, auteur, ordre));
	    }
	    
	    public List<Auteur> getAuteurs() {
	        return contributionsAuteurs.stream()
	            .sorted(Comparator.comparingInt(ContributionAuteur::getOrdreAuteur))
	            .map(ContributionAuteur::getAuteur)
	            .collect(Collectors.toList());
	    }
	    
}
