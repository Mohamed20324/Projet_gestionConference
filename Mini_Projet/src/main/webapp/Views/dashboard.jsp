<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="Model.Conference" %>
<%@ page import="Model.Utilisateur" %>
<%@ page import="DAO.ConferenceDAO" %>
<%
    Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
    if (utilisateur == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dashboard - SciConf Manager</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        .conference-card {
            margin-bottom: 20px;
            transition: transform 0.2s;
        }
        .conference-card:hover {
            transform: translateY(-5px);
        }
        .role-badge {
            font-size: 0.8em;
            padding: 5px 10px;
            border-radius: 15px;
        }
        .role-PRESIDENT {
            background-color: #ffd700;
            color: #000;
        }
        .role-AUTEUR {
            background-color: #98FB98;
            color: #000;
        }
        .role-REVIEWER {
            background-color: #87CEEB;
            color: #000;
        }
    </style>
</head>
<body>
    <style>
        .navbar {
            background-color: #343a40;
            padding: 0.5rem 1rem;
        }
        .navbar-brand {
            color: white;
            font-weight: 500;
            font-size: 1.25rem;
        }
        .navbar-nav .nav-link {
            color: rgba(255,255,255,.8);
            padding: 0.5rem 1rem;
        }
        .navbar-nav .nav-link:hover {
            color: white;
        }
    </style>

    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container-fluid">
            <a class="navbar-brand" href="#">ConfFST</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav me-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="#" data-bs-toggle="modal" data-bs-target="#newConferenceModal">
                            <i class="fas fa-plus-circle"></i> Nouvelle Conférence
                        </a>
                    </li>
                </ul>
                <ul class="navbar-nav">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" 
                           data-bs-toggle="dropdown">
                            <i class="fas fa-user-circle"></i> 
                            <%= utilisateur.getNom() %> <%= utilisateur.getPrenom() %>
                        </a>
                        <ul class="dropdown-menu dropdown-menu-end">
                            <li>
                                <a class="dropdown-item" href="../LogoutServlet">
                                    <i class="fas fa-sign-out-alt"></i> Déconnexion
                                </a>
                            </li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container mt-5">
        <div class="row mb-4">
            <div class="col">
                <h2><i class="fas fa-calendar-event"></i> Mes Conférences</h2>
            </div>
            <div class="col text-end">
                <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#newConferenceModal">
                    <i class="fas fa-plus-circle"></i> Nouvelle Conférence
                </button>
            </div>
        </div>
        
        <div class="row">
            <%
            List<Map<String, Object>> conferencesWithRoles = ConferenceDAO.getConferencesWithRoles(utilisateur.getUtilisateurId());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            
            if (conferencesWithRoles != null && !conferencesWithRoles.isEmpty()) {
                for (Map<String, Object> conferenceInfo : conferencesWithRoles) {
                    Conference conf = (Conference) conferenceInfo.get("conference");
                    String role = (String) conferenceInfo.get("role");
            %>
            <div class="col-md-6 mb-4">
                <div class="card conference-card">
                    <div class="card-body">
                        <div class="d-flex justify-content-between align-items-start mb-3">
                            <span class="role-badge role-<%= role %>"><%= role %></span>
                            <small class="text-muted">ID: <%= conf.getConferenceId() %></small>
                        </div>
                        <h5 class="card-title mb-3"><%= conf.getNomConf() %></h5>
                        <div class="mb-3">
                            <p class="mb-2">
                                <i class="fas fa-calendar-alt me-2"></i>
                                <%= sdf.format(conf.getDateDebut()) %> - <%= sdf.format(conf.getDateFin()) %>
                            </p>
                            <p class="mb-2">
                                <i class="fas fa-map-marker-alt me-2"></i>
                                <%= conf.getLieuConf() != null ? conf.getLieuConf() : "" %>
                            </p>
                            <p class="mb-0">
                                <i class="fas fa-tag me-2"></i>
                                <%= conf.getType() %>
                            </p>
                        </div>
                        <div class="d-flex gap-2">
                            <% if("PRESIDENT".equals(role)) { %>
                            <button class="btn btn-warning" onclick="editConference(<%= conf.getConferenceId() %>)">
                                <i class="fas fa-edit me-1"></i> Modifier
                            </button>
                            <% } %>
                            <button class="btn btn-info" onclick="viewConferenceDetails(<%= conf.getConferenceId() %>)">
                                <i class="fas fa-info-circle me-1"></i> Details
                            </button>
                        </div>
                    </div>
                </div>
            </div>
            <%
                }
            } else {
            %>
            <div class="col-12">
                <div class="alert alert-info">
                    <i class="fas fa-info-circle me-2"></i> Vous n'avez pas encore de conférences. Cliquez sur "Nouvelle Conférence" pour en créer une.
                </div>
            </div>
            <%
            }
            %>
        </div>
    </div>

    <!-- Modal de details -->
    <div class="modal fade" id="conferenceDetailsModal" tabindex="-1">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Details de la conference</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body" id="conferenceDetails">
                    <!-- Le contenu sera charge dynamiquement -->
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Fermer</button>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal pour créer une nouvelle conférence -->
    <div class="modal fade" id="newConferenceModal" tabindex="-1">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Créer une nouvelle conférence</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <form id="newConferenceForm" action="../CreateConferenceServlet" method="post" enctype="multipart/form-data">
                    <div class="modal-body">
                        <div class="row mb-3">
                            <div class="col-md-6">
                                <label for="nomConf" class="form-label">Nom de la conférence*</label>
                                <input type="text" class="form-control" id="nomConf" name="nomConf" required>
                            </div>
                            <div class="col-md-6">
                                <label for="type" class="form-label">Type*</label>
                                <select class="form-select" id="type" name="type" required>
                                    <option value="PHYSIQUE">PHYSIQUE</option>
                                    <option value="VIRTUELLE">VIRTUELLE</option>
                                    <option value="HYBRIDE">HYBRIDE</option>
                                </select>
                            </div>
                        </div>
                        <div class="row mb-3">
                            <div class="col-md-6">
                                <label for="dateDebut" class="form-label">Date de début*</label>
                                <input type="date" class="form-control" id="dateDebut" name="dateDebut" required>
                            </div>
                            <div class="col-md-6">
                                <label for="dateFin" class="form-label">Date de fin*</label>
                                <input type="date" class="form-control" id="dateFin" name="dateFin" required>
                            </div>
                        </div>
                        <div class="row mb-3">
                            <div class="col-md-6">
                                <label for="dateLimiteSoumission" class="form-label">Date limite de soumission*</label>
                                <input type="date" class="form-control" id="dateLimiteSoumission" name="dateLimiteSoumission" required>
                            </div>
                            <div class="col-md-6">
                                <label for="dateNotification" class="form-label">Date de notification*</label>
                                <input type="date" class="form-control" id="dateNotification" name="dateNotification" required>
                            </div>
                        </div>
                        <div class="row mb-3">
                            <div class="col-md-6">
                                <label for="lieuConf" class="form-label">Lieu</label>
                                <input type="text" class="form-control" id="lieuConf" name="lieuConf">
                            </div>
                            <div class="col-md-6">
                                <label for="siteConf" class="form-label">Site web</label>
                                <input type="url" class="form-control" id="siteConf" name="siteConf">
                            </div>
                        </div>
                        <div class="mb-3">
                            <label for="thematiqueConf" class="form-label">Thématique</label>
                            <input type="text" class="form-control" id="thematiqueConf" name="thematiqueConf">
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Annuler</button>
                        <button type="submit" class="btn btn-primary">Créer la conférence</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <!-- Modal pour la modification -->
    <div class="modal fade" id="editConferenceModal" tabindex="-1">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Modifier la conference</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <form id="editConferenceForm" method="post">
                    <div class="modal-body">
                        <!-- Le contenu sera charge dynamiquement -->
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        document.getElementById('newConferenceForm').addEventListener('submit', function(e) {
            e.preventDefault();
            
            const formData = new FormData(this);
            fetch('../CreateConferenceServlet', {
                method: 'POST',
                body: formData
            })
            .then(response => {
                if (!response.ok) {
                    return response.text().then(text => {
                        throw new Error(text);
                    });
                }
                return response.text();
            })
            .then(result => {
                const modal = bootstrap.Modal.getInstance(document.getElementById('newConferenceModal'));
                modal.hide();
                alert('La conférence a été créée avec succès');
                location.reload();
            })
            .catch(error => {
                console.error('Erreur:', error);
                alert('Erreur lors de la création de la conférence: ' + error.message);
            });
        });

        function viewConferenceDetails(id) {
            const modal = new bootstrap.Modal(document.getElementById('conferenceDetailsModal'));
            const detailsDiv = document.getElementById('conferenceDetails');
            
            fetch('../conference/details?id=' + id)
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Erreur lors de la recuperation des details');
                    }
                    return response.text();
                })
                .then(html => {
                    detailsDiv.innerHTML = html;
                    modal.show();
                })
                .catch(error => {
                    console.error('Erreur:', error);
                    alert('Erreur lors de la recuperation des details de la conference');
                });
        }

        function editConference(id) {
            const modal = new bootstrap.Modal(document.getElementById('editConferenceModal'));
            const editForm = document.getElementById('editConferenceForm');
            
            editForm.innerHTML = '<div class="modal-body"><div class="text-center"><div class="spinner-border" role="status"></div></div></div>';
            editForm.action = '../conference/update';
            
            fetch('../conference/details?id=' + id + '&mode=edit')
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Erreur lors de la recuperation des details');
                    }
                    return response.text();
                })
                .then(html => {
                    editForm.innerHTML = html;
                    modal.show();
                })
                .catch(error => {
                    console.error('Erreur:', error);
                    alert('Erreur lors de la recuperation des details de la conference');
                });
        }

        document.getElementById('editConferenceForm').addEventListener('submit', function(e) {
            e.preventDefault();
            
            const formData = new FormData(this);
            fetch('../conference/update', {
                method: 'POST',
                body: formData
            })
            .then(response => {
                if (!response.ok) {
                    return response.text().then(text => {
                        throw new Error(text);
                    });
                }
                return response.text();
            })
            .then(result => {
                const editModal = bootstrap.Modal.getInstance(document.getElementById('editConferenceModal'));
                editModal.hide();
                alert('La conference a ete mise a jour avec succes');
                location.reload();
            })
            .catch(error => {
                console.error('Erreur:', error);
                alert('Erreur lors de la mise a jour de la conference: ' + error.message);
            });
        });
    </script>
</body>
</html>
