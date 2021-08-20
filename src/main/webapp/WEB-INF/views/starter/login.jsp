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
                <h1 class="h3 mb-3 fw-normal">Sign in</h1>
                <div class="form-signin">
                    <form method="post" action="${contextPath}/login">
                        <div class="row">
                            <div class="col">
                                <div class="form-floating">
                                    <input name="email" type="text" class="form-control" id="floatingInputEmail" placeholder="Email"/>
                                    <label for="floatingInputEmail">Email</label>
                                </div>
                            </div>
                            <div class="col">
                                <div class="form-floating">
                                    <input name="password" type="password" class="form-control" id="floatingPassword" placeholder="Password"/>
                                    <label for="floatingPassword">Password</label>
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                </div>
                            </div>

                        </div>
                        <div class="row">
                            <div class="col">
                                <button class="w-100 btn btn-lg btn-primary" type="submit">Sign in</button>
                                <c:if test="${not empty errorMessage}"><div style="color:red; font-weight: bold; margin: 30px 0px;">${errorMessage}</div></c:if>
                            </div>
                            <div class="col">
                                <div>
                                    <a href="${contextPath}/register">Create an account</a>
                                </div>
                                <div>
                                    <a href="#">Forgot password</a>
                                </div>
                            </div>

                        </div>

                    </form>
                </div>
            </div>
            <div class="col align-self-center">
                <h1 class="text-center">Make Difference.</h1>
            </div>
        </div>

        <jsp:include page="../frames/footer.jsp" />

    </div>

    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js" integrity="sha384-eMNCOe7tC1doHpGoWe/6oMVemdAVTMs2xqW4mwXrXsW0L84Iytr2wi5v2QjrP/xp" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.min.js" integrity="sha384-cn7l7gDp0eyniUwwAZgrzD06kc/tftFf19TOAs2zVinnD/C7E91j9yyk5//jjpt/" crossorigin="anonymous"></script>
</body>
</html>