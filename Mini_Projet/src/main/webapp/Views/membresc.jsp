<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Espace Membre - ConfFST</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
</head>
<body class="bg-light">
    <!-- Navigation -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container-fluid">
            <a class="navbar-brand" href="membresc.jsp">
                <i class="bi bi-person-workspace"></i> Espace Membre
            </a>
            <div class="d-flex align-items-center">
                <a href="taches.jsp" class="btn btn-info me-2">
                    <i class="bi bi-list-task"></i> Tâches assignées
                </a>
                <a href="LogoutServlet" class="btn btn-outline-light">
                    <i class="bi bi-box-arrow-right"></i> Déconnexion
                </a>
            </div>
        </div>
    </nav>

    <!-- Contenu principal -->
    <div class="container mt-4">
        <div class="card shadow">
            <div class="card-header bg-primary text-white">
                <h5 class="card-title mb-0">Tableau de bord</h5>
            </div>
            <div class="card-body">
                <div class="row">
                    <div class="col-md-4">
                        <div class="card bg-light mb-3">
                            <div class="card-body text-center">
                                <h5><i class="bi bi-chat-dots"></i> Discussions</h5>
                                <p class="text-muted">3 nouvelles messages</p>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="card bg-light mb-3">
                            <div class="card-body text-center">
                                <h5><i class="bi bi-calendar-check"></i> Événements</h5>
                                <p class="text-muted">2 réunions à venir</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Pied de page -->
    <footer class="bg-dark text-light py-3 mt-4">
        <div class="container text-center">
            <p class="mb-0">ConfFST - Membre ${sessionScope.role}</p>
        </div>
    </footer>
</body>
</html>