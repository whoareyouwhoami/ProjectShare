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

    <form:form method="post" action="${contextPath}/register" modelAttribute="UserForm">
        <div>
            <form:input type="text" path="firstName" placeholder="First name"/>
            <form:errors path="firstName"/>

            <form:input type="text" path="lastName" placeholder="Last name"/>
            <form:errors path="lastName"/>
        </div>
        <div>
            <form:input type="text" path="email" placeholder="Email"/>
            <form:errors path="email"/>
        </div>
        <div>
            <form:input type="text" path="password" placeholder="Password"/>

            <form:input type="text" path="passwordConfirm" placeholder="Confirm password"/>
            <form:errors path="passwordConfirm"/>
        </div>
        <div>
            <form:label path="">Date of birth</form:label>
            <form:input type="date" path="dateOfBirth"/>
            <form:errors path="dateOfBirth"/>
        </div>
        <div>
            <form:select path="gender">
                <form:option value="" label="Gender" disabled="true" selected="true"/>
                <form:option value="M" label="Male"/>
                <form:option value="F" label="Female"/>
            </form:select>
            <form:errors path="gender"/>
        </div>
        <div>
            <button class="btn btn-lg btn-primary btn-block" type="submit">Create account</button>
            <a href="${contextPath}/login">Have an account?</a>
        </div>
    </form:form>

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
