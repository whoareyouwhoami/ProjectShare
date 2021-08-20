<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="path" value="${requestScope['javax.servlet.forward.servlet_path']}" />

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link href="<c:url value="/css/custom_project.css" />" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
    <title>Upload project</title>
</head>

<body>
    <jsp:include page="../frames/navbar.jsp" />
    <div class="container pt-2 pb-4">
        <jsp:include page="../frames/project_header.jsp" />

        <div class="row pe-4 ps-4">
            <h1 class="h2 mt-3 p-0 fw-normal">Upload project</h1>
        </div>
        <div class="row pe-4 ps-4">
            <div class="col-6 p-0 form-signin">
                <form:form method="post" modelAttribute="ProjectForm">
                    <div class="row">
                        <div class="col">
                            <div class="form-floating">
                                <form:input type="text" class="form-control" id="floatingInputTitle" path="title" placeholder="Title"/>
                                <label for="floatingInputTitle">Title</label>
                                <form:errors path="title"/>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col">
                            <div class="form-floating">
                                <form:textarea class="form-control" id="floatingTextareaDescription" path="description" placeholder="Description"  style="height: 150px"/>
                                <label for="floatingTextareaDescription">Description</label>
                                <form:errors path="description"/>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col">
                            <div class="form-floating">
                                <form:input type="date" class="form-control" id="floatingInputDateStart"  path="dateStart"/>
                                <label for="floatingInputDateStart">Start date</label>
                                <form:errors path="dateStart"/>
                            </div>
                        </div>
                        <div class="col">
                            <div class="form-floating">
                                <form:input type="date" class="form-control" id="floatingInputDateEnd"  path="dateEnd"/>
                                <label for="floatingInputDateEnd">End date</label>
                                <form:errors path="dateEnd"/>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col">
                            <div class="form-floating">
                                <form:input type="number" class="form-control" id="floatingInputMember"  path="member"/>
                                <label for="floatingInputMember">Number of members</label>
                                <form:errors path="member"/>
                            </div>
                        </div>
                        <div class="col">
                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="status" id="statusPublic" value="true" checked>
                                <label class="form-check-label" for="statusPublic">Public</label>
                            </div>
                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="status" id="statusPrivate" value="false">
                                <label class="form-check-label" for="statusPrivate">Private</label>
                            </div>
                        </div>
                    </div>
                    <div class="row row-button">
                        <c:if test="${fn:endsWith(path, '/upload')}">
                            <button class="btn btn-primary" type="submit">Upload</button>
                        </c:if>
                        <c:if test="${not fn:endsWith(path, '/upload')}">
                            <button class="w-50 btn  btn-primary" type="submit">Update</button>
                        </c:if>
                        <a class="p-0 mt-2" href="<c:url value="${contextPath}/home" />">Cancel</a>
                    </div>
                </form:form>
            </div>
        </div>


    </div>

    <jsp:include page="../frames/footer.jsp" />
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js" integrity="sha384-eMNCOe7tC1doHpGoWe/6oMVemdAVTMs2xqW4mwXrXsW0L84Iytr2wi5v2QjrP/xp" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.min.js" integrity="sha384-cn7l7gDp0eyniUwwAZgrzD06kc/tftFf19TOAs2zVinnD/C7E91j9yyk5//jjpt/" crossorigin="anonymous"></script>
</body>
</html>