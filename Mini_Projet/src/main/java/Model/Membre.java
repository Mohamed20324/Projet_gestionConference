package Model;

public class Membre extends Utilisateur {
    public Membre(int utilisateurId, String nom, String prenom, String email, String motdepasse, String pays,
			String siteWeb, Role role) {
		super(utilisateurId, nom, prenom, email, motdepasse, pays, siteWeb, role);
	}
	protected String biographie;
    protected String institution;
    protected String titre;
 
	public String getBiographie() {
		return biographie;
	}
	public void setBiographie(String biographie) {
		this.biographie = biographie;
	}
	public String getInstitution() {
		return institution;
	}
	public void setInstitution(String institution) {
		this.institution = institution;
	}
	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
	}
    
}