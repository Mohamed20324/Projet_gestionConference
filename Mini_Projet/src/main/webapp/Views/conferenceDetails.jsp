<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Détails de la conférence</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <%
        String nomConf = (String) request.getAttribute("nomConf");
        String type = (String) request.getAttribute("type");
        String lieuConf = (String) request.getAttribute("lieuConf");
        String siteConf = (String) request.getAttribute("siteConf");
        String thematiqueConf = (String) request.getAttribute("thematiqueConf");
        String dateDebut = (String) request.getAttribute("dateDebut");
        String dateFin = (String) request.getAttribute("dateFin");
        String dateLimiteSoumission = (String) request.getAttribute("dateLimiteSoumission");
        String dateNotification = (String) request.getAttribute("dateNotification");
        String dateExtension = (String) request.getAttribute("dateExtension");
    %>
    <div class="container mt-4">
        <div class="card">
            <div class="card-header">
                <h3><%=nomConf != null ? nomConf : "Sans titre"%></h3>
            </div>
            <div class="card-body">
                <div class="row">
                    <div class="col-md-6">
                        <p><strong>Type:</strong> <%=type != null ? type : "Non défini"%></p>
                        <p><strong>Lieu:</strong> <%=lieuConf != null ? lieuConf : "Non défini"%></p>
                        <p><strong>Site web:</strong> 
                        <%
                            if(siteConf != null && !siteConf.trim().isEmpty()) {
                                out.print("<a href=\"" + siteConf + "\" target=\"_blank\">" + siteConf + "</a>");
                            } else {
                                out.print("Non défini");
                            }
                        %>
                        </p>
                        <p><strong>Thématique:</strong> <%=thematiqueConf != null ? thematiqueConf : "Non défini"%></p>
                    </div>
                    <div class="col-md-6">
                        <p><strong>Date de début:</strong> <%=dateDebut != null ? dateDebut : "Non définie"%></p>
                        <p><strong>Date de fin:</strong> <%=dateFin != null ? dateFin : "Non définie"%></p>
                        <p><strong>Date limite de soumission:</strong> <%=dateLimiteSoumission != null ? dateLimiteSoumission : "Non définie"%></p>
                        <p><strong>Date de notification:</strong> <%=dateNotification != null ? dateNotification : "Non définie"%></p>
                        <p><strong>Date d'extension:</strong> <%=dateExtension != null ? dateExtension : "Non définie"%></p>
                    </div>
                </div>
            </div>
            <div class="card-footer">
                <a href="dashboard" class="btn btn-primary">Retour au tableau de bord</a>
            </div>
        </div>
    </div>
</body>
</html>
