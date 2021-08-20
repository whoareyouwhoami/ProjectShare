<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
    <title>Upload project</title>
</head>

<body>
    <jsp:include page="../frames/navbar.jsp" />
    <div class="container pt-2 pb-4">
        <jsp:include page="../frames/project_header.jsp" />

        <c:if test="${p_detail == null}">
            <div class="row pe-4 ps-4">
                <h1 class="h3 mt-3 fw-normal">Project doesn't exist...</h1>
            </div>
        </c:if>

        <c:if test="${p_detail != null}">
            <div class="row mb-3 pe-4 ps-4">
                <h1 class="h2 mt-3 p-0 fw-normal">Project details</h1>
            </div>
            <div class="row pe-4 ps-4">
                <div class="col-8 p-0">
                    <table class="table">
                        <tbody>
                            <tr>
                                <th scope="row">Title</th>
                                <td>${p_detail.title}</td>
                            </tr>
                            <tr>
                                <th scope="row">Description</th>
                                <td>${p_detail.description}</td>
                            </tr>
                            <tr>
                                <th scope="row">Number of members</th>
                                <td>${p_detail.member}</td>
                            </tr>
                            <tr>
                                <th scope="row">Duration</th>
                                <td class="text-muted">${p_detail.dateStart} ~ ${p_detail.dateEnd}</td>
                            </tr>
                        </tbody>
                    </table>
                    <div class="row">

                        <c:if test="${pageContext.request.userPrincipal.name == p_detail.author.email}">
                            <div class="col">
                                <a class="btn btn-primary" href="<c:url value="${contextPath}/project/update/${p_detail.id}" />">Update</a>
                                <a class="btn btn-danger" href="<c:url value="${contextPath}/project/delete/${p_detail.id}" />">Delete</a>
                            </div>
                        </c:if>
                        <c:if test="${pageContext.request.userPrincipal.name != p_detail.author.email}">
                            <div class="col">
                                <a class="btn btn-primary" href="<c:url value="${contextPath}/messages/d/${p_detail.id}" />">Send message</a>
                            </div>
                        </c:if>
                    </div>
                </div>

            </div>
        </c:if>
    </div>

    <jsp:include page="../frames/footer.jsp" />
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js" integrity="sha384-eMNCOe7tC1doHpGoWe/6oMVemdAVTMs2xqW4mwXrXsW0L84Iytr2wi5v2QjrP/xp" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.min.js" integrity="sha384-cn7l7gDp0eyniUwwAZgrzD06kc/tftFf19TOAs2zVinnD/C7E91j9yyk5//jjpt/" crossorigin="anonymous"></script>
</body>
</html>