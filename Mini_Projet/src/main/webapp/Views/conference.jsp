<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="Model.Conference"%>
<%@ page import="Model.Utilisateur"%>
<%@ page import="DAO.ConferenceDAO"%>
<%@ page import="java.text.SimpleDateFormat"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Détails de la Conférence</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.2/font/bootstrap-icons.css" rel="stylesheet">
</head>
<body>
    <%
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        if (utilisateur == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        int conferenceId = Integer.parseInt(request.getParameter("id"));
        Conference conference = ConferenceDAO.getConferenceById(conferenceId);
        boolean isPresident = ConferenceDAO.isPresident(conferenceId, utilisateur.getUtilisateurId());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    %>
    
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container-fluid">
            <a class="navbar-brand" href="#">ConfFST</a>
            <div class="collapse navbar-collapse">
                <ul class="navbar-nav me-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="dashboard.jsp">
                            <i class="bi bi-arrow-left"></i> Retour
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container mt-4">
        <!-- Messages d'erreur/succès -->
        <% if (request.getAttribute("error") != null) { %>
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <%= request.getAttribute("error") %>
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        <% } %>
        <% if (session.getAttribute("success") != null) { %>
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                <%= session.getAttribute("success") %>
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
            <% session.removeAttribute("success"); %>
        <% } %>

        <div class="card">
            <div class="card-header d-flex justify-content-between align-items-center">
                <h4 class="mb-0"><%= conference.getNomConf() %></h4>
                <% if (isPresident) { %>
                    <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#extensionModal">
                        <i class="bi bi-calendar-plus"></i> Modifier la date d'extension
                    </button>
                <% } %>
            </div>
            <div class="card-body">
                <div class="row">
                    <div class="col-md-6">
                        <p><strong>Acronyme:</strong> <%= conference.getAcronym() %></p>
                        <p><strong>Type:</strong> <%= conference.getType() != null ? conference.getType() : "Non spécifié" %></p>
                        <p><strong>Thématique:</strong> <%= conference.getThematiqueConf() != null ? conference.getThematiqueConf() : "Non spécifiée" %></p>
                        <p><strong>Lieu:</strong> <%= conference.getLieuConf() != null ? conference.getLieuConf() : "Non spécifié" %></p>
                    </div>
                    <div class="col-md-6">
                        <p><strong>Date de début:</strong> <%= dateFormat.format(conference.getDateDebut()) %></p>
                        <p><strong>Date de fin:</strong> <%= conference.getDateFin() != null ? dateFormat.format(conference.getDateFin()) : "Non spécifiée" %></p>
                        <p><strong>Date limite de soumission:</strong> <%= conference.getDateLimiteSoumission() != null ? dateFormat.format(conference.getDateLimiteSoumission()) : "Non spécifiée" %></p>
                        <p><strong>Date d'extension:</strong> <%= conference.getDateextension() != null ? dateFormat.format(conference.getDateextension()) : "Non spécifiée" %></p>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal Extension -->
    <% if (isPresident) { %>
    <div class="modal fade" id="extensionModal" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Modifier la date d'extension</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <form action="../UpdateConferenceServlet" method="POST">
                    <input type="hidden" name="conferenceId" value="<%= conference.getConferenceID() %>">
                    <div class="modal-body">
                        <div class="mb-3">
                            <label class="form-label">Nouvelle date d'extension</label>
                            <input type="date" class="form-control" name="dateExtension" 
                                   value="<%= conference.getDateextension() != null ? dateFormat.format(conference.getDateextension()) : "" %>"
                                   min="<%= dateFormat.format(conference.getDateLimiteSoumission()) %>"
                                   <%= conference.getDateFin() != null ? "max=\"" + dateFormat.format(conference.getDateFin()) + "\"" : "" %>>
                            <div class="form-text">
                                La date d'extension doit être après la date limite de soumission 
                                <%= conference.getDateFin() != null ? "et avant la date de fin" : "" %>.
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Annuler</button>
                        <button type="submit" class="btn btn-primary">Enregistrer</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <% } %>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
