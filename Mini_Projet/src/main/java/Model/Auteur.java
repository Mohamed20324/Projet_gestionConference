package Model;

import java.util.List;

public class Auteur extends Utilisateur {
    private boolean estCorrespondant;
    private String institution;
	public Auteur(int utilisateurId, String nom, String prenom, String email, String motdepasse, String pays,
			String siteWeb, boolean estCorrespondant, String institution) {
		super(utilisateurId, nom, prenom, email, motdepasse, pays, siteWeb);
		this.estCorrespondant = estCorrespondant;
		this.institution = institution;
	}
	public boolean isEstCorrespondant() {
		return estCorrespondant;
	}
	public void setEstCorrespondant(boolean estCorrespondant) {
		this.estCorrespondant = estCorrespondant;
	}
	public String getInstitution() {
		return institution;
	}
	public void setInstitution(String institution) {
		this.institution = institution;
	}

    
}
