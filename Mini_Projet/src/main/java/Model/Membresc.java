package Model;

public class Membresc extends Membre {
    private boolean estResponsable;
    private MembreComitePilotage membreComitePilotage;
	public Membresc(int utilisateurId, String nom, String prenom, String email, String motdepasse, String pays,
			String siteWeb, String biographie, String institution, String titre) {
		super(utilisateurId, nom, prenom, email, motdepasse, pays, siteWeb, biographie, institution, titre);	}
	public boolean isEstResponsable() {
		return estResponsable;
	}
	public void setEstResponsable(boolean estResponsable) {
		this.estResponsable = estResponsable;
	}
	public MembreComitePilotage getMembreComitePilotage() {
		return membreComitePilotage;
	}
	public void setMembreComitePilotage(MembreComitePilotage membreComitePilotage) {
		this.membreComitePilotage = membreComitePilotage;
	}

   
}