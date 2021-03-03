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
        <c:if test="${showMessageList}">
            <div>
                <div>
                    <div>
                        <c:if test="${messages != null}">
                            <c:forEach var="messageList" items="${messages}">
                                <div>
                                    <p>
                                        <c:if test="${pageContext.request.userPrincipal.name != messageList.fromUser}">
                                            <b>${messageList.fromUser}</b>
                                        </c:if>
                                        <c:if test="${pageContext.request.userPrincipal.name == messageList.fromUser}">
                                            <b>Me</b>
                                        </c:if>

                                    </p>
                                    <p>${messageList.content}</p>
                                    <small>${messageList.time}</small>
                                </div>
                            </c:forEach>
                        </c:if>
                    </div>

                    <div>
                        <div id="response" class="chat"></div>
                    </div>
                </div>

                <hr>

                <div>
                    <span id="to">${authorInfo.email}</span>
                    <div>
                        <label for="msg"></label><input type="text" id="msg" placeholder="Send message to ${authorInfo.email}" />
                        <input type="hidden" id="pid" value="${projectInfo}" />
                        <div>
                            <button id="btnSend" type="button">Send</button>
                        </div>
                    </div>
                </div>
            </div>
        </c:if>

        <script src="/js/sckfnc.js"></script>
        <script src="/js/sck.js"></script>
    </div>
</body>
</html>