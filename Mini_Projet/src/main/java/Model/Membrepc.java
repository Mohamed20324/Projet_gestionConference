package Model;
public class Membrepc extends Membre {
    private String domExpertise;
    private int nbEvaluation;
    private int nbSoumission;
    private double tauxParticipation;
    private MembreComitePilotage membreComitePilotage;
	public Membrepc(int utilisateurId, String nom, String prenom, String email, String motdepasse, String pays,
			String siteWeb, String biographie, String institution, String titre, String domExpertise, int nbEvaluation,
			int nbSoumission, double tauxParticipation, MembreComitePilotage membreComitePilotage) {
		super(utilisateurId, nom, prenom, email, motdepasse, pays, siteWeb, biographie, institution, titre);
		this.domExpertise = domExpertise;
		this.nbEvaluation = nbEvaluation;
		this.nbSoumission = nbSoumission;
		this.tauxParticipation = tauxParticipation;
		this.membreComitePilotage = membreComitePilotage;
	}
	public String getDomExpertise() {
		return domExpertise;
	}
	public void setDomExpertise(String domExpertise) {
		this.domExpertise = domExpertise;
	}
	public int getNbEvaluation() {
		return nbEvaluation;
	}
	public void setNbEvaluation(int nbEvaluation) {
		this.nbEvaluation = nbEvaluation;
	}
	public int getNbSoumission() {
		return nbSoumission;
	}
	public void setNbSoumission(int nbSoumission) {
		this.nbSoumission = nbSoumission;
	}
	public double getTauxParticipation() {
		return tauxParticipation;
	}
	public void setTauxParticipation(double tauxParticipation) {
		this.tauxParticipation = tauxParticipation;
	}
	public MembreComitePilotage getMembreComitePilotage() {
		return membreComitePilotage;
	}
	public void setMembreComitePilotage(MembreComitePilotage membreComitePilotage) {
		this.membreComitePilotage = membreComitePilotage;
	}

}
