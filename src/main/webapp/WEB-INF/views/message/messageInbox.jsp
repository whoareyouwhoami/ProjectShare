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
                <h2>Messages inbox</h2>
            </div>
            <div>
                <c:forEach var="tempMessageList" items="${messageList}" varStatus="status">
                    <small> Message ID: ${tempMessageList.key}</small>
                    <c:forEach var="m" items="${tempMessageList.value}" varStatus="status">
                        <div>
                            <div>
                                <div>
                                    <div>
                                        <strong>${tempMessageList.key}</strong>
                                        <h3>Project: ${m.key.title}</h3>
                                        <div>@${m.value.firstName}</div>
                                        <p>${m.key.description}</p>
                                        <a href="${contextPath}/messages/${tempMessageList.key}">SEND MESSAGE</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </c:forEach>
            </div>
        </div>
    </div>
</body>
</html>