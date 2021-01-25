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
    <title>Sign in with your account</title>
</head>

<body>
<div>
    <div>
        <h1>Project Galaxias</h1>
    </div>

    <form method="post" action="${contextPath}/login">
        <h2>Sign in</h2>
        <div>
            <input name="email" type="text" placeholder="Email"/>
            <input name="password" type="password" placeholder="Password"/>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <button type="submit">Sign in</button>
            <c:if test="${not empty errorMessage}"><div style="color:red; font-weight: bold; margin: 30px 0px;">${errorMessage}</div></c:if>
        </div>
        <div>
            <a href="${contextPath}/register">Create an account</a>
            <a href="#">Forgot password</a>
        </div>
    </form>


    <div class="container-right col-md-4">
        <h1>Make Difference.</h1>
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