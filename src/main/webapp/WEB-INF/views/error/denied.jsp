<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Something wrong</title>
</head>
<body>
    <h2>Sorry. Something went wrong... We are working on it!</h2>
    <a href="<c:url value="${contextPath}/login" />">Sign in again</a>
</body>
</html>
