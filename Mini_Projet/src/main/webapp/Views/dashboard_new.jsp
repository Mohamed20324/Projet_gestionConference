<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dashboard - SciConf Manager</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        .conference-card {
            height: 100%;
            border: none;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            transition: transform 0.2s;
            margin-bottom: 20px;
        }
        .conference-card:hover {
            transform: translateY(-5px);
        }
        .role-badge {
            font-size: 0.8rem;
            padding: 0.25rem 0.5rem;
            border-radius: 4px;
            background-color: #ffc107;
            color: #000;
        }
        .conference-details p {
            margin-bottom: 0.5rem;
            color: #666;
        }
        .conference-details i {
            width: 20px;
            color: #666;
        }
        .btn-sm {
            padding: 0.25rem 0.5rem;
            font-size: 0.875rem;
        }
        .btn-warning {
            background-color: #ffc107;
            border: none;
        }
        .btn-info {
            background-color: #17a2b8;
            border: none;
            color: white;
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
                        <a class="nav-link" href="#" data-bs-toggle="modal" data-bs-target="#newConferenceModal">
                            <i class="fas fa-plus-circle"></i> Nouvelle Conference
                        </a>
                    </li>
                </ul>
                <ul class="navbar-nav">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" 
                           data-bs-toggle="dropdown">
                            <i class="fas fa-user-circle"></i> 
                            EL Hafyani Mohamed
                        </a>
                        <ul class="dropdown-menu dropdown-menu-end">
                            <li>
                                <a class="dropdown-item" href="../LogoutServlet">
                                    <i class="fas fa-sign-out-alt"></i> Deconnexion
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
                <h2><i class="fas fa-calendar-alt"></i> Mes Conferences</h2>
            </div>
            <div class="col text-end">
                <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#newConferenceModal">
                    <i class="fas fa-plus-circle"></i> Nouvelle Conference
                </button>
            </div>
        </div>

        <div class="row">
            <%
            List<Map<String, Object>> conferencesWithRoles = ConferenceDAO.getConferencesWithRoles(utilisateur.getUtilisateurId());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            
            for(Map<String, Object> conferenceInfo : conferencesWithRoles) {
                Conference conf = (Conference) conferenceInfo.get("conference");
                String role = (String) conferenceInfo.get("role");
            %>
            <div class="col-md-6 col-lg-4 mb-4">
                <div class="card conference-card">
                    <div class="card-body">
                        <div class="d-flex justify-content-between align-items-start mb-2">
                            <div>
                                <h5 class="card-title mb-1"><%= conf.getNomConf() %></h5>
                                <h6 class="text-muted"><%= conf.getAcronym() %></h6>
                            </div>
                            <span class="role-badge"><%= role %></span>
                        </div>
                        <div class="conference-details">
                            <p class="mb-2">
                                <i class="fas fa-calendar me-2"></i>
                                <%= sdf.format(conf.getDateDebut()) %> - <%= sdf.format(conf.getDateFin()) %>
                            </p>
                            <p class="mb-2">
                                <i class="fas fa-map-marker-alt me-2"></i>
                                <%= conf.getLieuConf() != null ? conf.getLieuConf() : "fes" %>
                            </p>
                            <p class="mb-3">
                                <i class="fas fa-tag me-2"></i>
                                <span class="badge bg-light text-dark"><%= conf.getType() %></span>
                            </p>
                        </div>
                        <div class="d-flex gap-2 mt-3">
                            <% if("PRESIDENT".equals(role)) { %>
                            <button class="btn btn-warning btn-sm" onclick="editConference(<%= conf.getConferenceId() %>)">
                                <i class="fas fa-edit me-1"></i> Modifier
                            </button>
                            <% } %>
                            <button class="btn btn-info btn-sm text-white" onclick="viewConferenceDetails(<%= conf.getConferenceId() %>)">
                                <i class="fas fa-info-circle me-1"></i> Details
                            </button>
                        </div>
                    </div>
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
