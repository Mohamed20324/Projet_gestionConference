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
    </style>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container-fluid">
            <a class="navbar-brand" href="home.jsp">ConfFST</a>
        </div>
    </nav>

    <!-- Registration Form -->
    <div class="container">
        <div class="register-card">
            <div class="text-center mb-4">
                <i class="bi bi-person-plus" style="font-size: 3rem; color: #0d6efd;"></i>
                <h3>Création de compte</h3>
            </div>

            <%-- Affichage des erreurs --%>
            <% if(request.getAttribute("errorMessage") != null) { %>
                <div class="alert alert-danger" role="alert">
                    ${errorMessage}
                </div>
            <% } %>

            <form action="RegisterServlet" method="POST" onsubmit="return validatePasswords()">
                
                <!-- Informations Personnelles -->
                <div class="form-section">
                    <h5><i class="bi bi-person-vcard"></i> Informations Personnelles</h5>
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label class="form-label required">Prénom</label>
                            <input type="text" class="form-control" name="firstName" required>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label required">Nom de famille</label>
                            <input type="text" class="form-control" name="lastName" required>
                        </div>
                    </div>
                    
                    <div class="mb-3">
                        <label class="form-label required">Email</label>
                        <input type="email" class="form-control" name="email" required 
                               placeholder="exemple@domain.com">
                    </div>
                </div>

                <!-- Informations de Connexion -->
                <div class="form-section">
                    <h5><i class="bi bi-shield-lock"></i> Sécurité</h5>
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label class="form-label required">Mot de passe</label>
                            <input type="password" class="form-control" id="password" name="password" required 
                                   minlength="8">
                            <small class="form-text text-muted">Minimum 8 caractères</small>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label required">Confirmer le mot de passe</label>
                            <input type="password" class="form-control" id="confirmPassword" required>
                        </div>
                    </div>
                </div>

                <!-- Informations Professionnelles -->
                <div class="form-section">
                    <h5><i class="bi bi-building"></i> Informations Professionnelles</h5>
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label class="form-label required">Pays</label>
                            <select class="form-select" name="country" required>
                                <option value="">Sélectionnez un pays</option>
                                <option value="FR">France</option>
                                <option value="MA">Maroc</option>
                                <option value="CA">Canada</option>
                                <!-- Ajouter d'autres pays -->
                            </select>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Institution/Organisation</label>
                            <input type="text" class="form-control" name="institution">
                        </div>
                    </div>
                    
                    <div class="mb-3">
                        <label class="form-label">Site web personnel</label>
                        <input type="url" class="form-control" name="website" 
                               placeholder="https://www.example.com">
                    </div>
                </div>

                <div class="d-grid gap-2">
                    <button type="submit" class="btn btn-primary">
                        <i class="bi bi-person-fill-add"></i> Créer le compte
                    </button>
                    
                    <div class="text-center mt-3">
                        <p class="text-muted">Déjà inscrit ? 
                            <a href="login.jsp" class="text-decoration-none">Se connecter</a>
                        </p>
                    </div>
                </div>
            </form>
        </div>
    </div>

    <script>
        function validatePasswords() {
            const password = document.getElementById('password').value;
            const confirmPassword = document.getElementById('confirmPassword').value;
            
            if(password !== confirmPassword) {
                alert("Les mots de passe ne correspondent pas !");
                return false;
            }
            return true;
        }
    </script>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>