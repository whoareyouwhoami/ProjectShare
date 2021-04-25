<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Upload project</title>
</head>

<body>
    <div>
        <div>
            <h1>Projects</h1>
            <a href="<c:url value="${contextPath}/home" />">Home</a>
            <a href="<c:url value="${contextPath}/project" />">Project</a>
            <a href="<c:url value="${contextPath}/project/upload" />">Upload</a>
            <a href="<c:url value="${contextPath}/project/search" />">Search</a>
            <a href="<c:url value="${contextPath}/project/view" />">View My Project</a>
        </div>
    </div>

    <div style="overflow: auto; height: 700px; width: 25%;">
        <c:forEach var="p" items="${projectList}">
            <div style="padding: 3px; border-bottom: 1px solid grey;">
                <p><b>${p.title}</b></p>
                <p>${p.description}</p>
                <small><b>Looking for ${p.member} members</b> (${p.dateStart} - ${p.dateEnd})</small>
                <br>
                <small><a href="${contextPath}/project/view/${p.id}">Check detail</a></small>
            </div>

        </c:forEach>
    </div>
</body>
</html>