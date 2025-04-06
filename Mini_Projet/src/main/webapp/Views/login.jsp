<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Connexion - SciConf Manager</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <style>
        .login-card {
            max-width: 400px;
            margin: 5rem auto;
            padding: 2rem;
            border-radius: 15px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            background: #f8f9fa;
        }
        .form-icon {
            font-size: 4rem;
            color: #0d6efd;
        }
    </style>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container-fluid">
            <a class="navbar-brand" href="home.jsp">ConfFST</a>
        </div>
    </nav>

    <div class="container">
        <div class="login-card">
            <div class="text-center mb-4">
                <i class="bi bi-person-circle form-icon"></i>
                <h3>Connexion</h3>
            </div>

            <%-- Message de succès après inscription --%>
            <% if(request.getParameter("success") != null) { %>
                <div class="alert alert-success" role="alert">
                    <i class="bi bi-check-circle"></i> Inscription réussie ! Vous pouvez maintenant vous connecter.
                </div>
            <% } %>

            <%-- Message d'erreur --%>
            <% if(request.getParameter("error") != null) { %>
                <div class="alert alert-danger" role="alert">
                    <i class="bi bi-exclamation-triangle"></i> <%= request.getParameter("error") %>
                </div>
            <% } %>

            <form action="/Mini_Projet/LoginServlet" method="POST">
                <div class="mb-3">
                    <label for="email" class="form-label">Email</label>
                    <div class="input-group">
                        <span class="input-group-text">
                            <i class="bi bi-envelope"></i>
                        </span>
                        <input type="email" 
                               class="form-control" 
                               id="email" 
                               name="email" 
                               required
                               placeholder="exemple@domain.com">
                    </div>
                </div>

                <div class="mb-4">
                    <label for="password" class="form-label">Mot de passe</label>
                    <div class="input-group">
                        <span class="input-group-text">
                            <i class="bi bi-key"></i>
                        </span>
                        <input type="password" 
                               class="form-control" 
                               id="password" 
                               name="password" 
                               required
                               placeholder="••••••••">
                    </div>
                </div>

                <div class="d-grid gap-2">
                    <button type="submit" class="btn btn-primary">
                        <i class="bi bi-box-arrow-in-right"></i> Se connecter
                    </button>
                    <a href="register.jsp" class="btn btn-outline-secondary">
                        <i class="bi bi-person-plus"></i> Créer un compte
                    </a>
                </div>
            </form>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>