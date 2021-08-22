<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link href="<c:url value="/css/custom_message.css" />" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.5.0/sockjs.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
    <title>Messages</title>
</head>

<body>
    <jsp:include page="../frames/navbar.jsp" />
    <div class="container pt-2 pb-4">
        <jsp:include page="../frames/message_header.jsp" />


        <div class="row mb-3 pe-3 ps-3">
            <h1 class="h2 mt-3 p-0 fw-normal">Inbox</h1>
        </div>
        <div class="row">
            <div class="col-3 border border-primary  overflow-scroll message-inbox-block me-5">
                <c:forEach var="message" items="${messageList}" varStatus="status">
                    <div class="row">
                        <div class="col">
                            <a href="${contextPath}/messages/m/${message.id}" class="text-decoration-none text-body">

                                <div class="card">

                                    <small class="visually-hidden">${message.id}</small>
                                    <div class="card-header">
                                        Project: ${message.project.title}
                                    </div>
                                    <div class="card-body">
                                        <h5 class="card-title">${message.project.author.firstName}</h5>
                                        <p class="card-text text-truncate">Last message received from the user</p>
                                        <p class="card-text"><small class="text-muted">Last updated 3 mins ago</small></p>
                                    </div>
                                </div>

                            </a>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>

    <jsp:include page="../frames/footer.jsp" />
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js" integrity="sha384-eMNCOe7tC1doHpGoWe/6oMVemdAVTMs2xqW4mwXrXsW0L84Iytr2wi5v2QjrP/xp" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.min.js" integrity="sha384-cn7l7gDp0eyniUwwAZgrzD06kc/tftFf19TOAs2zVinnD/C7E91j9yyk5//jjpt/" crossorigin="anonymous"></script>
</body>
</html>