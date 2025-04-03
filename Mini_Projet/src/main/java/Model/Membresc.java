package Model;

public class Membresc extends Membre {
    public Membresc(int utilisateurId, String nom, String prenom, String email, String motdepasse, String pays,
			String siteWeb, Role role) {
		super(utilisateurId, nom, prenom, email, motdepasse, pays, siteWeb, role);
		// TODO Auto-generated constructor stub
	}
	private boolean estResponsable;
    protected MembreComitePilotage membreComitePilotage;
   
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