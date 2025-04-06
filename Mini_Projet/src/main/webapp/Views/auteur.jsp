<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Espace Auteur - ConfFST</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
</head>
<body>
    <!-- Navigation -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container-fluid">
            <a class="navbar-brand" href="auteur.jsp">
                <i class="bi bi-pencil-square"></i> Espace Auteur
            </a>
            <div class="d-flex align-items-center">
                <a href="nouvelleSoumission.jsp" class="btn btn-success me-2">
                    <i class="bi bi-file-earmark-plus"></i> Nouvelle soumission
                </a>
                <a href="LogoutServlet" class="btn btn-outline-light">
                    <i class="bi bi-box-arrow-right"></i> Déconnexion
                </a>
            </div>
        </div>
    </nav>

    <!-- Contenu principal -->
    <div class="container mt-4">
        <div class="alert alert-info">
            <h4>Vos soumissions</h4>
            <ul class="list-group mt-3">
                <li class="list-group-item d-flex justify-content-between align-items-center">
                    "IA Évolutive" 
                    <span class="badge bg-success">Accepté</span>
                </li>
            </ul>
        </div>
    </div>

    <!-- Pied de page -->
    <footer class="bg-dark text-light fixed-bottom py-2">
        <div class="container text-center">
            <small>© 2025 ConfFST - Auteur : ${sessionScope.email}</small>
        </div>
    </footer>
</body>
</html>