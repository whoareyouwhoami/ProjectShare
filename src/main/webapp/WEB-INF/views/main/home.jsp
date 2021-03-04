<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Welcome</title>
</head>
<body>
    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <h2>Welcome ${pageContext.request.userPrincipal.name}</h2>

        <a href="<c:url value="${contextPath}/project" />">Project</a>
        <a href="<c:url value="${contextPath}/messages" />">Message</a>
        <a href="<c:url value="${contextPath}/logout" />">Logout</a>
    </c:if>
</body>
</html>
