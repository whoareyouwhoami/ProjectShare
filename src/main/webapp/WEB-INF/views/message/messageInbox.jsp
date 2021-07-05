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
    <script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.5.0/sockjs.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
    <title>Messages</title>
</head>
<body>
    <div>
        <div>
            <a href="${contextPath}/" class="ml-1">Back</a>
        </div>
        <div>
            <div>
                <h1>Messages inbox</h1>
                <a href="<c:url value="${contextPath}/home" />">Home</a>
                <a href="<c:url value="${contextPath}/project" />">Project</a>
                <a href="<c:url value="${contextPath}/messages" />">Message</a>
            </div>

            <div style="overflow: auto; height: 700px; width: 25%; margin-top: 15px;">


                <c:forEach var="message" items="${messageList}" varStatus="status">
                    <div style="padding: 5px; border-bottom: 1px solid grey;">
                        <small> Message ID: ${message.id}</small>

                        <div style="margin-bottom: 3px">
                            <p style="margin: 3px"><b>@${message.project.author.firstName}</b></p>
                            <p style="margin: 3px;">Project - ${message.project.title}</p>
                            <a href="${contextPath}/messages/m/${message.id}">Send message</a>
                        </div>
                    </div>
                </c:forEach>

            </div>
        </div>
    </div>
</body>
</html>