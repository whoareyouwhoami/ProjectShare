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
    <%--    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">--%>
    <%--    <link href="${contextPath}/resources/css/starter.css" rel="stylesheet">--%>
    <title>Upload project</title>
</head>

<body>
<div>
    <div>
        <h1>View Project</h1>
        <a href="<c:url value="${contextPath}/home" />">Home</a>
    </div>

    <div>
        <c:if test="${p_detail != null}">
            <div>
                <p>Title: ${p_detail.title}</p>
            </div>
            <div>
                <p>Description: ${p_detail.description}</p>
            </div>
            <div>
                <p>No. of members: ${p_detail.member}</p>
            </div>
            <div>
                <p>Duration: ${p_detail.dateStart} ~ ${p_detail.dateEnd}</p>
            </div>

            <div>
                <a href="<c:url value="${contextPath}/project/update/${p_detail.id}" />">Update</a>
                <a href="<c:url value="${contextPath}/project/delete/${p_detail.id}" />">Delete</a>
            </div>
        </c:if>
        <c:if test="${p_detail == null}">
            <div>
                <p>Project doesn't exist...</p>
            </div>
        </c:if>
    </div>


    <footer class="footer mt-auto py-3">
        <div class="container">
            <small class="text-muted">Copyright &copy; J Production 2021</small>
            <br/>
            <small class="text-muted">All Right Reserved</small>
        </div>
    </footer>
</div>
</body>
</html>