<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Welcome</title>
</head>
<body>
<c:if test="${pageContext.request.userPrincipal.name != null}">
    <h2>Welcome ${pageContext.request.userPrincipal.name}</h2>

    <a href="<c:url value="${contextPath}/project/upload" />">Project</a>
    <a href="<c:url value="${contextPath}/logout" />">Logout</a>
</c:if>

</body>
</html>
