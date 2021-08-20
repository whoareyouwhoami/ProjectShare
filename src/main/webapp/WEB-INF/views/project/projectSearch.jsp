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
    <link href="<c:url value="/css/custom_project.css" />" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
    <title>Search project</title>
</head>

<body>
    <jsp:include page="../frames/navbar.jsp" />

    <div class="container pt-2 pb-4">
        <jsp:include page="../frames/project_header.jsp" />

        <div class="row pe-4 ps-4">
            <h1 class="h2 mt-3 p-0 fw-normal">Search projects</h1>
        </div>

        <div class="row pe-4 ps-4">
            <div class="col-8 p-0">
                <form method="get" action="${contextPath}/project/search">
                    <div class="input-group mb-3">
                        <input type="text" name="q" class="form-control" aria-label="Search projects" aria-describedby="button-addon2">
                        <button class="btn btn-primary" type="submit" id="button-addon2">Search</button>
                    </div>
                </form>
            </div>
        </div>

        <c:if test="${emptyResult == 0}">
            <div class="row pe-4 ps-4">
                <h1 class="h3 mt-3 p-0 fw-normal">No projects found...</h1>
            </div>
        </c:if>

        <div class="row">
            <div class="search-result-block overflow-scroll col-8">
                <c:forEach var="qq" items="${searchResult}">
                    <div class="row pe-4 ps-4">
                        <div class="card col-8">
                            <a href="${contextPath}/project/view/${qq.id}" class="text-decoration-none text-body">
                                <div class="card-body">
                                    <h5 class="card-title text-truncate"><c:out value="${qq.title}" /></h5>
                                    <p class="card-text text-truncate"><c:out value="${qq.description}" /></p>
                                    <p class="card-text"><c:out value="${qq.dateStart}"/> ~ <c:out value="${qq.dateEnd}"/></p>
                                    <p class="card-text">Looking for <span class="fw-bold"><c:out value="${qq.member}"/></span> members</p>
                                </div>
                            </a>
                        </div>
                    </div>
                </c:forEach>

            </div>
        </div>

    </div>

    <jsp:include page="../frames/footer.jsp" />
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js" integrity="sha384-eMNCOe7tC1doHpGoWe/6oMVemdAVTMs2xqW4mwXrXsW0L84Iytr2wi5v2QjrP/xp" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.min.js" integrity="sha384-cn7l7gDp0eyniUwwAZgrzD06kc/tftFf19TOAs2zVinnD/C7E91j9yyk5//jjpt/" crossorigin="anonymous"></script>
</body>
</html>