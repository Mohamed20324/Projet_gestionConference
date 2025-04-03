package Model;

public class Role {
    private int roleID;
    private TypeRole typeRole; 
    private Conference conference;
    private Utilisateur utilisateur;

   
    public Role(int roleID, TypeRole typeRole, Conference conference, Utilisateur utilisateur) {
		super();
		this.roleID = roleID;
		this.typeRole = typeRole;
		this.conference = conference;
		this.utilisateur = utilisateur;
	}

	public Role() {
	}

	public TypeRole getTypeRole() {
        return typeRole;
    }

    public void setTypeRole(TypeRole typeRole) {
        this.typeRole = typeRole;
    }

	public int getRoleID() {
		return roleID;
	}

	public void setRoleID(int roleID) {
		this.roleID = roleID;
	}

	public Conference getConference() {
		return conference;
	}

	public void setConference(Conference conference) {
		this.conference = conference;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

   
}