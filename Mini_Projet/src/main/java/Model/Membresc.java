package Model;

public class Membresc extends Membre {
    
	private boolean estResponsable;
    protected MembreComitePilotage membreComitePilotage;
   
	public Membresc(int utilisateurId, String nom, String prenom, String email, String motdepasse, String pays,
			String siteWeb, Role role, String biographie, String institution, String titre, boolean estResponsable,
			MembreComitePilotage membreComitePilotage) {
		super(utilisateurId, nom, prenom, email, motdepasse, pays, siteWeb, role, biographie, institution, titre);
		this.estResponsable = estResponsable;
		this.membreComitePilotage = membreComitePilotage;
	}

	public void setEstResponsable(boolean estResponsable) {
		this.estResponsable = estResponsable;
	}
	
	public boolean isEstResponsable() {
		return estResponsable;
	}
	public MembreComitePilotage getMembreComitePilotage() {
		return membreComitePilotage;
	}
	public void setMembreComitePilotage(MembreComitePilotage membreComitePilotage) {
		this.membreComitePilotage = membreComitePilotage;
	}

   
}