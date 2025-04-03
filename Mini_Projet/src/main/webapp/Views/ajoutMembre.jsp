<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Ajouter Membre</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .dynamic-section { display: none; }
        .required:after { content: "*"; color: red; margin-left: 3px; }
    </style>
</head>
<body class="container mt-4">
    <div class="card shadow">
        <div class="card-header bg-primary text-white">
            <h3>Ajouter un membre</h3>
        </div>
        
        <div class="card-body">
            <form action="GestionMembre" method="post">
                <!-- Sélection du type -->
                <div class="row mb-4">
                    <div class="col-md-6">
                        <label class="form-label required">Type de membre</label>
                        <select class="form-select" id="memberType" name="type" onchange="toggleFields()">
                            <option value="PC">Comité de Programme (PC)</option>
                            <option value="SC">Comité Scientifique (SC)</option>
                        </select>
                    </div>
                </div>

                <!-- Informations Personnelles -->
                <fieldset class="border p-3 mb-4">
                    <legend>Informations Personnelles</legend>
                    
                    <div class="row g-3">
                        <div class="col-md-6">
                            <label class="form-label required">Nom complet</label>
                            <input type="text" class="form-control" name="nom" required>
                        </div>
                        
                        <div class="col-md-6">
                            <label class="form-label required">Email</label>
                            <input type="email" class="form-control" name="email" required>
                        </div>
                        
                        <div class="col-md-6">
                            <label class="form-label required">Institution</label>
                            <input type="text" class="form-control" name="institution" required>
                        </div>
                        
                        <div class="col-md-6">
                            <label class="form-label required">Pays</label>
                            <input type="text" class="form-control" name="pays" required>
                        </div>
                    </div>
                </fieldset>

                <!-- Informations Académiques -->
                <fieldset class="border p-3 mb-4">
                    <legend>Informations Académiques</legend>
                    
                    <div class="row g-3">
                        <div class="col-md-6">
                            <label class="form-label required">Titre</label>
                            <input type="text" class="form-control" name="titre" required>
                        </div>
                        
                        <div class="col-md-6 pc-field dynamic-section">
                            <label class="form-label required">Domaine d'expertise</label>
                            <input type="text" class="form-control" name="expertise">
                        </div>
                        
                        <div class="col-12">
                            <label class="form-label">Biographie</label>
                            <textarea class="form-control" name="biographie" rows="3"></textarea>
                        </div>
                        
                        <div class="col-md-6">
                            <label class="form-label">Site web</label>
                            <input type="url" class="form-control" name="siteWeb">
                        </div>
                    </div>
                </fieldset>

                <!-- Section spécifique PC -->
                <div class="pc-field dynamic-section">
                    <fieldset class="border p-3 mb-4">
                        <legend>Statistiques PC</legend>
                        
                        <div class="row g-3">
                            <div class="col-md-4">
                                <label class="form-label">Soumissions assignées</label>
                                <input type="number" class="form-control" name="soumissions">
                            </div>
                            
                            <div class="col-md-4">
                                <label class="form-label">Évaluations complétées</label>
                                <input type="number" class="form-control" name="evaluations">
                            </div>
                            
                            <div class="col-md-4">
                                <label class="form-label">Taux participation (%)</label>
                                <input type="number" class="form-control" name="taux" step="0.1">
                            </div>
                        </div>
                    </fieldset>
                </div>

                <!-- Section spécifique SC -->
                <div class="sc-field dynamic-section">
                    <div class="form-check mb-3">
                        <input type="checkbox" class="form-check-input" name="responsable">
                        <label class="form-check-label">Désigner comme responsable</label>
                    </div>
                </div>

                <button type="submit" class="btn btn-primary">Enregistrer</button>
            </form>
        </div>
    </div>

    <script>
        function toggleFields() {
            const type = document.getElementById('memberType').value;
            
            document.querySelectorAll('.dynamic-section').forEach(div => {
                div.style.display = 'none';
            });
            
            document.querySelectorAll('.pc-field').forEach(div => {
                div.style.display = type === 'PC' ? 'block' : 'none';
            });
            
            document.querySelectorAll('.sc-field').forEach(div => {
                div.style.display = type === 'SC' ? 'block' : 'none';
            });

            // Gestion des attributs required
            document.querySelector('[name="expertise"]').required = type === 'PC';
        }
        
        // Initialisation au chargement
        window.onload = toggleFields;
    </script>

</body>
</html>