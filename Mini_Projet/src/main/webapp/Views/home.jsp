<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Plateforme de Conférences Scientifiques</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.2/font/bootstrap-icons.css">
    <link href="${pageContext.request.contextPath}/css/home.css" rel="stylesheet">
</head>
<body>
    <!-- Navigation -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container-fluid">
            <a class="navbar-brand" href="home.jsp"><i class="bi bi-globe"></i> ConfFST</a>
            <div class="d-flex align-items-center">
                <a href="createConference.jsp" class="btn btn-success bouton-navigation">
                    <i class="bi bi-plus-circle"></i> Créer Conférence
                </a>
                <a href="login.jsp" class="btn btn-outline-light bouton-navigation">
                    <i class="bi bi-box-arrow-in-right"></i> Login
                </a>
            </div>
        </div>
    </nav>

    <!-- Contenu principal -->
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-10">
                <div class="conteneur-hero text-center">
                    <div class="superposition-hero">
                        <div class="texte-hero">
                            <h1 class="display-4 mb-4 fw-bold">Bienvenue sur ConfFST</h1>
                            <p class="lead fs-3">Plateforme de gestion de manifestations scientifiques</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Pied de page -->
    <footer class="pied-de-page">
        <div class="container text-center">
            <div class="row">
                <div class="col-md-12">
                    <p class="mb-0">
                        © 2025 ConfFST - Tous droits réservés | 
                        <a href="#" class="lien-pied-de-page">Contact</a>
                    </p>
                </div>
            </div>
        </div>
    </footer>
</body>
</html>