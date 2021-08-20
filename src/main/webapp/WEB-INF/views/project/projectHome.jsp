<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link href="<c:url value="/css/custom_project.css" />" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
    <title>Projects</title>
</head>

<body>
    <jsp:include page="../frames/navbar.jsp" />
    <div class="container pt-2 pb-4">
        <jsp:include page="../frames/project_header.jsp" />

        <div class="d-flex justify-content-between flex-wrap pt-3">
            <c:forEach var="p" items="${projectList}">
                <div class="card mb-3" style="width: 25rem;">
                    <div class="card-body">
                        <h5 class="card-title m-0 text-truncate" style="height: 2rem;">${p.title}</h5>
                        <p class="card-text m-0 text-truncate fw-light" style="height: 2rem;">${p.description}</p>
                        <p class="text-muted m-0" style="height: 2rem;">${p.dateStart} ~ ${p.dateEnd}</p>

                        <p class="card-text m-0" style="height: 2rem;">
                            <span class="fst-italic me-2">Looking for ${p.member} members</span>
                            <a href="${contextPath}/project/view/${p.id}" class="link-primary">Check detail</a>
                        </p>

                    </div>
                </div>
            </c:forEach>
        </div>


<%--        <div style="overflow: auto; height: 700px; width: 25%;">--%>
<%--            <c:forEach var="p" items="${projectList}">--%>
<%--                <div style="padding: 3px; border-bottom: 1px solid grey;">--%>
<%--                    <p><b>${p.title}</b></p>--%>
<%--                    <p>${p.description}</p>--%>
<%--                    <small><b>Looking for ${p.member} members</b> (${p.dateStart} - ${p.dateEnd})</small>--%>
<%--                    <br>--%>
<%--                    <small><a href="${contextPath}/project/view/${p.id}">Check detail</a></small>--%>
<%--                </div>--%>

<%--            </c:forEach>--%>
<%--        </div>--%>
    </div>



    <jsp:include page="../frames/footer.jsp" />
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js" integrity="sha384-eMNCOe7tC1doHpGoWe/6oMVemdAVTMs2xqW4mwXrXsW0L84Iytr2wi5v2QjrP/xp" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.min.js" integrity="sha384-cn7l7gDp0eyniUwwAZgrzD06kc/tftFf19TOAs2zVinnD/C7E91j9yyk5//jjpt/" crossorigin="anonymous"></script>
</body>
</html>