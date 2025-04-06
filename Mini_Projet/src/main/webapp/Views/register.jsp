<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Inscription - SciConf Manager</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <style>
        .register-card {
            max-width: 600px;
            margin: 3rem auto;
            padding: 2rem;
            border-radius: 15px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            background: #f8f9fa;
        }
        .form-section {
            border-bottom: 1px solid #dee2e6;
            padding-bottom: 1.5rem;
            margin-bottom: 1.5rem;
        }
        .required:after {
            content: " *";
            color: red;
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
        <div class="register-card">
            <div class="text-center mb-4">
                <i class="bi bi-person-plus" style="font-size: 3rem; color: #0d6efd;"></i>
                <h3>Création de compte</h3>
                <p class="text-muted">Rejoignez la communauté scientifique</p>
            </div>

            <%-- Message d'erreur --%>
            <% if(request.getAttribute("error") != null) { %>
                <div class="alert alert-danger" role="alert">
                    <i class="bi bi-exclamation-triangle"></i> <%= request.getAttribute("error") %>
                </div>
            <% } %>

            <form action="/Mini_Projet/RegisterServlet" method="POST" id="registerForm">
                <!-- Informations Personnelles -->
                <div class="form-section">
                    <h5><i class="bi bi-person-vcard"></i> Informations Personnelles</h5>
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label class="form-label required">Prénom</label>
                            <div class="input-group">
                                <span class="input-group-text">
                                    <i class="bi bi-person"></i>
                                </span>
                                <input type="text" class="form-control" name="prenom" required>
                            </div>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label required">Nom</label>
                            <div class="input-group">
                                <span class="input-group-text">
                                    <i class="bi bi-person"></i>
                                </span>
                                <input type="text" class="form-control" name="nom" required>
                            </div>
                        </div>
                    </div>
                    
                    <div class="mb-3">
                        <label class="form-label required">Email</label>
                        <div class="input-group">
                            <span class="input-group-text">
                                <i class="bi bi-envelope"></i>
                            </span>
                            <input type="email" class="form-control" name="email" required 
                                   placeholder="exemple@domain.com">
                        </div>
                    </div>
                </div>

                <!-- Mot de passe -->
                <div class="form-section">
                    <h5><i class="bi bi-shield-lock"></i> Sécurité</h5>
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label class="form-label required">Mot de passe</label>
                            <div class="input-group">
                                <span class="input-group-text">
                                    <i class="bi bi-key"></i>
                                </span>
                                <input type="password" class="form-control" id="motdepasse" name="motdepasse" 
                                       required minlength="8">
                            </div>
                            <small class="form-text text-muted">Minimum 8 caractères</small>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label required">Confirmation</label>
                            <div class="input-group">
                                <span class="input-group-text">
                                    <i class="bi bi-key-fill"></i>
                                </span>
                                <input type="password" class="form-control" id="motdepasseConfirm" 
                                       name="motdepasseConfirm" required minlength="8">
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Informations Complémentaires -->
                <div class="form-section">
                    <h5><i class="bi bi-geo-alt"></i> Informations Complémentaires</h5>
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Pays</label>
                            <select class="form-select" name="pays">
                                <option value="">Sélectionnez un pays</option>
                                <option value="MA">Maroc</option>
                                <option value="FR">France</option>
                                <option value="BE">Belgique</option>
                                <option value="CH">Suisse</option>
                                <option value="CA">Canada</option>
                            </select>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Site Web</label>
                            <div class="input-group">
                                <span class="input-group-text">
                                    <i class="bi bi-globe"></i>
                                </span>
                                <input type="url" class="form-control" name="siteWeb" 
                                       placeholder="https://example.com">
                            </div>
                        </div>
                    </div>
                </div>

                <div class="d-grid gap-2">
                    <button type="submit" class="btn btn-primary">
                        <i class="bi bi-person-plus-fill"></i> S'inscrire
                    </button>
                    <a href="login.jsp" class="btn btn-outline-secondary">
                        <i class="bi bi-arrow-left"></i> Retour à la connexion
                    </a>
                </div>
            </form>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        document.getElementById('registerForm').addEventListener('submit', function(e) {
            var password = document.getElementById('motdepasse');
            var confirm = document.getElementById('motdepasseConfirm');
            
            if (password.value !== confirm.value) {
                e.preventDefault();
                alert('Les mots de passe ne correspondent pas');
            }
        });
    </script>
</body>
</html>