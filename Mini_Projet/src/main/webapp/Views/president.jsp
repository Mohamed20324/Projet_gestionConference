<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Espace Président - ConfFST</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <link href="${pageContext.request.contextPath}/css/dashboard.css" rel="stylesheet">
</head>
<body>
    <!-- Navigation -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container-fluid">
            <a class="navbar-brand" href="president.jsp"><i class="bi bi-person-badge"></i> Espace Président</a>
            <div class="d-flex align-items-center">
                <span class="text-light me-3">${sessionScope.email}</span>
                <a href="creerConference.jsp" class="btn btn-success me-2">
                    <i class="bi bi-plus-circle"></i> Nouvelle Conférence
                </a>
                <a href="LogoutServlet" class="btn btn-outline-light">
                    <i class="bi bi-box-arrow-right"></i> Déconnexion
                </a>
            </div>
        </div>
    </nav>

    <!-- Contenu principal -->
    <div class="container mt-5">
        <h2 class="mb-4">Conférences en cours</h2>
        <div class="row">
            <!-- Liste des conférences -->
            <div class="col-md-6">
                <div class="card shadow">
                    <div class="card-header bg-primary text-white">
                        Conférences planifiées
                    </div>
                    <div class="card-body">
                        <ul class="list-group">
                            <li class="list-group-item">Conférence IA - 15/12/2025</li>
                            <li class="list-group-item">Symposium Biotech - 20/01/2026</li>
                        </ul>
                    </div>
                </div>
            </div>

            <!-- Statistiques -->
            <div class="col-md-6">
                <div class="card shadow">
                    <div class="card-header bg-info text-white">
                        <i class="bi bi-graph-up"></i> Statistiques
                    </div>
                    <div class="card-body">
                        <canvas id="statsChart" width="400" height="200"></canvas>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Pied de page -->
    <footer class="fixed-bottom bg-dark text-light py-3">
        <div class="container text-center">
            <p class="mb-0">© 2025 ConfFST - Tous droits réservés</p>
        </div>
    </footer>
</body>
</html>