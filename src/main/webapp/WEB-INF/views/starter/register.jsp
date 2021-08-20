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
    <link href="<c:url value="/css/custom_starter.css" />" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
    <title>Sign in with your account</title>
</head>
<body>
    <div class="container">
        <div class="row">
            <h1 class="h1 mb-3 fw-normal">Project Share</h1>
        </div>
        <div class="row">
            <div class="col align-self-center">
                <h1 class="h3 mb-3 fw-normal">Create an account</h1>
                <div class="form-signin">
                    <form:form method="post" action="${contextPath}/register" modelAttribute="UserForm">
                        <div class="row">
                            <div class="col">
                                <div class="form-floating">
                                    <form:input type="text" class="form-control" id="floatingInputFirstName" path="firstName" placeholder="First name"/>
                                    <label for="floatingInputFirstName">First name</label>
                                    <form:errors path="firstName"/>
                                </div>
                            </div>
                            <div class="col">
                                <div class="form-floating">
                                    <form:input type="text" class="form-control" id="floatingInputLastName" path="lastName" placeholder="Last name"/>
                                    <label for="floatingInputLastName">Last name</label>
                                    <form:errors path="lastName"/>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col">
                                <div class="form-floating">
                                    <form:input type="email" class="form-control" id="floatingInputEmail" path="email" placeholder="Email"/>
                                    <label for="floatingInputEmail">Email</label>
                                    <form:errors path="email"/>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col">
                                <div class="form-floating">
                                    <form:input type="password" class="form-control" id="floatingPassword" path="password" placeholder="Password"/>
                                    <label for="floatingPassword">Password</label>
                                </div>
                            </div>
                            <div class="col">
                                <div class="form-floating">
                                    <form:input type="password" class="form-control" id="floatingPasswordConfirm" path="passwordConfirm" placeholder="Confirm password"/>
                                    <label for="floatingPasswordConfirm">Confirm password</label>
                                    <form:errors path="passwordConfirm"/>
                                </div>
                            </div>
                        </div>

                        <div class="row align-items-center">
                            <div class="col">
                                <div class="form-floating">
                                    <form:select class="form-select" id="floatingSelectGender" aria-label="Select gender" path="gender">
                                        <form:option value="" label="Select..." disabled="true" selected="true"/>
                                        <form:option value="M" label="Male"/>
                                        <form:option value="F" label="Female"/>
                                    </form:select>
                                    <form:errors path="gender"/>
                                    <label for="floatingSelectGender">Gender</label>
                                </div>
                            </div>
                            <div class="col">
                                <div class="form-floating">
                                    <form:input type="date" class="form-control" id="floatingInputDOB" path="dateOfBirth"/>
                                    <label for="floatingInputDOB">Date of birth</label>
                                    <form:errors path="dateOfBirth"/>
                                </div>
                            </div>
                        </div>

                        <div>
                            <button class="w-100 btn btn-lg btn-primary" type="submit">Create account</button>
                            <a href="${contextPath}/login">Have an account?</a>
                        </div>
                    </form:form>
                </div>
            </div>

            <div class="col align-self-center">
                <h1 class="text-center">Make Difference.</h1>
            </div>
        </div>


        <jsp:include page="../frames/footer.jsp" />

    </div>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js" integrity="sha384-eMNCOe7tC1doHpGoWe/6oMVemdAVTMs2xqW4mwXrXsW0L84Iytr2wi5v2QjrP/xp" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.min.js" integrity="sha384-cn7l7gDp0eyniUwwAZgrzD06kc/tftFf19TOAs2zVinnD/C7E91j9yyk5//jjpt/" crossorigin="anonymous"></script>

</body>
</html>
