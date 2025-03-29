package Model;

public class President extends Utilisateur  {
	    private int presidentId;

		public President(int utilisateurId, String nom, String prenom, String email, String motdepasse, String pays,
				String siteWeb, int presidentId) {
			super(utilisateurId, nom, prenom, email, motdepasse, pays, siteWeb);
			this.presidentId = presidentId;
		}

		public int getPresidentId() {
			return presidentId;
		}
		public void setPresidentId(int presidentId) {
			this.presidentId = presidentId;
		}

}
