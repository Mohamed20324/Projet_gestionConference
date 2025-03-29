package Model;

public class Evaluation {
    private int evaluationID;
    private String decision;
    private Membrepc evaluateur; 
    private Article articleEvalue;
    private String recommandation;
    private String remarques;
    private Membrepc membrepc;
    private Article article;
    private Responsablesc responsablesc;
	
	public Evaluation(int evaluationID, String decision, Membrepc evaluateur, Article articleEvalue,
			String recommandation, String remarques, Membrepc membrepc, Article article, Responsablesc responsablesc) {
		super();
		this.evaluationID = evaluationID;
		this.decision = decision;
		this.evaluateur = evaluateur;
		this.articleEvalue = articleEvalue;
		this.recommandation = recommandation;
		this.remarques = remarques;
		this.membrepc = membrepc;
		this.article = article;
		this.responsablesc = responsablesc;
	}
	public int getEvaluationID() {
		return evaluationID;
	}
	public void setEvaluationID(int evaluationID) {
		this.evaluationID = evaluationID;
	}
	public String getDecision() {
		return decision;
	}
	public void setDecision(String decision) {
		this.decision = decision;
	}
	public String getRecommandation() {
		return recommandation;
	}
	public void setRecommandation(String recommandation) {
		this.recommandation = recommandation;
	}
	public String getRemarques() {
		return remarques;
	}
	public void setRemarques(String remarques) {
		this.remarques = remarques;
	}
	public Membrepc getMembrepc() {
		return membrepc;
	}
	public void setMembrepc(Membrepc membrepc) {
		this.membrepc = membrepc;
	}
	public Article getArticle() {
		return article;
	}
	public void setArticle(Article article) {
		this.article = article;
	}
	public Responsablesc getResponsablesc() {
		return responsablesc;
	}
	public void setResponsablesc(Responsablesc responsablesc) {
		this.responsablesc = responsablesc;
	}

	public Membrepc getEvaluateur() { return evaluateur; }
    public Article getArticleEvalue() { return articleEvalue; }
}