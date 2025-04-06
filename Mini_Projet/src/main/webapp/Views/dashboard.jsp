<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="Model.Utilisateur"%>
<%@ page import="Model.Conference"%>
<%@ page import="Model.Role"%>
<%@ page import="Model.TypeRole"%>
<%@ page import="DAO.RoleDAO"%>
<%@ page import="java.util.List"%>
<%@ page import="java.text.SimpleDateFormat"%>

<%
    // Vérification de la session
    Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
    if (utilisateur == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    
    // Format de date
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dashboard - SciConf Manager</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <style>
        .conference-card {
            transition: transform 0.2s;
        }
        .conference-card:hover {
            transform: translateY(-5px);
        }
        .role-badge {
            font-size: 0.8rem;
        }
    </style>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container-fluid">
            <a class="navbar-brand" href="#">ConfFST</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav me-auto">
                    <li class="nav-item">
                        <a class="nav-link active" href="#">
                            <i class="bi bi-house"></i> Dashboard
                        </a>
                    </li>
                </ul>
                <div class="d-flex align-items-center">
                    <span class="text-light me-3">
                        <i class="bi bi-person-circle"></i>
                        <%= utilisateur.getPrenom() %> <%= utilisateur.getNom() %>
                    </span>
                    <a href="LogoutServlet" class="btn btn-outline-light btn-sm">
                        <i class="bi bi-box-arrow-right"></i> Déconnexion
                    </a>
                </div>
            </div>
        </div>
    </nav>

    <div class="container mt-4">
        <div class="row mb-4">
            <div class="col">
                <h2><i class="bi bi-calendar-event"></i> Mes Conférences</h2>
            </div>
            <div class="col text-end">
                <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#newConferenceModal">
                    <i class="bi bi-plus-circle"></i> Nouvelle Conférence
                </button>
            </div>
        </div>

        <div class="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4">
            <%
                List<Role> roles = RoleDAO.getRolesUtilisateur(utilisateur.getUtilisateurId());
                for(Role role : roles) {
                    Conference conf = role.getConference();
            %>
            <div class="col">
                <div class="card h-100 conference-card">
                    <div class="card-body">
                        <h5 class="card-title"><%= conf.getNomConf() %></h5>
                        <span class="badge bg-primary role-badge">
                            <%= role.getTypeRole().name() %>
                        </span>
                        <p class="card-text mt-2">
                            <small class="text-muted">
                                <i class="bi bi-calendar"></i> <%= dateFormat.format(conf.getDateDebut()) %>
                            </small>
                        </p>
                        <div class="d-grid gap-2">
                            <a href="conference?id=<%= conf.getConferenceID() %>" class="btn btn-outline-primary btn-sm">
                                <i class="bi bi-arrow-right"></i> Gérer
                            </a>
                        </div>
                    </div>
                </div>
            </div>
            <% } %>
        </div>
    </div>

    <!-- Modal Nouvelle Conférence -->
    <div class="modal fade" id="newConferenceModal" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Nouvelle Conférence</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <form action="CreateConferenceServlet" method="POST">
                    <div class="modal-body">
                        <div class="mb-3">
                            <label class="form-label required">Nom de la conférence</label>
                            <input type="text" class="form-control" name="nomConf" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Thématique</label>
                            <input type="text" class="form-control" name="thematiqueConf">
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Acronyme</label>
                            <input type="text" class="form-control" name="acronym">
                        </div>
                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label class="form-label required">Date de début</label>
                                <input type="date" class="form-control" name="dateDebut" required>
                            </div>
                            <div class="col-md-6 mb-3">
                                <label class="form-label required">Date de fin</label>
                                <input type="date" class="form-control" name="dateFin" required>
                            </div>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Lieu</label>
                            <input type="text" class="form-control" name="lieuConf">
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Site web</label>
                            <input type="url" class="form-control" name="siteConf" 
                                   placeholder="https://example.com">
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Annuler</button>
                        <button type="submit" class="btn btn-primary">Créer</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
