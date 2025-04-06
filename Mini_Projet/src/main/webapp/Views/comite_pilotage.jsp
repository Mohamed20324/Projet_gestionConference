<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Comité de Pilotage - ConfFST</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
</head>
<body class="bg-light">
    <!-- Navigation -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container-fluid">
            <a class="navbar-brand" href="comite_pilotage.jsp">
                <i class="bi bi-people-fill"></i> Comité de Pilotage
            </a>
            <div class="d-flex align-items-center">
                <a href="evaluations.jsp" class="btn btn-warning me-2">
                    <i class="bi bi-clipboard-check"></i> Évaluations
                </a>
                <a href="LogoutServlet" class="btn btn-outline-light">
                    <i class="bi bi-box-arrow-right"></i> Déconnexion
                </a>
            </div>
        </div>
    </nav>

    <!-- Contenu principal -->
    <div class="container mt-4">
        <h3 class="mb-4">Soumissions à évaluer</h3>
        <div class="card shadow">
            <div class="card-body">
                <table class="table table-hover">
                    <thead>
                        <tr>
                            <th>Titre</th>
                            <th>Auteur</th>
                            <th>Statut</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>IA et Éthique</td>
                            <td>Dr. Smith</td>
                            <td><span class="badge bg-warning">En attente</span></td>
                            <td>
                                <button class="btn btn-sm btn-primary">
                                    <i class="bi bi-eye"></i> Consulter
                                </button>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <!-- Pied de page -->
    <footer class="bg-dark text-light py-3 mt-5">
        <div class="container text-center">
            <p class="mb-0">ConfFST - Plateforme scientifique</p>
        </div>
    </footer>
</body>
</html>