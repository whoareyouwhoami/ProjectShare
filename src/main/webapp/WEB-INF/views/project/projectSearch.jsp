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
    <title>Search project</title>
</head>

<body>
    <div>
        <div>
            <h1>Search project</h1>
            <a href="<c:url value="${contextPath}/home" />">Home</a>
            <a href="<c:url value="${contextPath}/project" />">Project</a>
            <a href="<c:url value="${contextPath}/project/upload" />">Upload</a>
            <a href="<c:url value="${contextPath}/project/view" />">View My Project</a>
        </div>

        <div>
            <form method="get" action="${contextPath}/project/search">
                <input type="text" name="q" placeholder="Search projects...">
                <button type="submit">Search</button>
            </form>

            <c:if test="${emptyResult == 0}">
                <p>No result found...</p>
            </c:if>

            <c:forEach var="qq" items="${searchResult}">
                <div>
                    <p>Title: <c:out value="${qq.title}"/></p>
                    <p>Description: <c:out value="${qq.description}"/></p>
                    <p>Required members: <c:out value="${qq.member}"/></p>
                    <p>Start date: <c:out value="${qq.dateStart}"/></p>
                    <p>End date: <c:out value="${qq.dateEnd}"/></p>
                    <p>Posted by: <c:out value="${qq.ownerName}"/></p>
                    <small><a href="${contextPath}/project/view/${qq.id}">Check project</a></small>
                </div>
                <br>
            </c:forEach>
        </div>

    </div>
</body>
</html>