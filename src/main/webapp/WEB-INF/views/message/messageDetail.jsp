<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.5.0/sockjs.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
    <title>Messages</title>
</head>
<body>
    <div>
        <div>
            <h1>Messages inbox</h1>
            <a href="<c:url value="${contextPath}/home" />">Home</a>
            <a href="<c:url value="${contextPath}/project" />">Project</a>
            <a href="<c:url value="${contextPath}/messages" />">Message</a>
        </div>

        <div style="width: 25%; margin-top: 15px;">
            <c:if test="${showMessageList}">
                <div>
                    <div style="overflow: auto; height: 600px; margin-top: 15px;">
                        <div>
                            <c:if test="${messages != null}">
                                <c:forEach var="messageList" items="${messages}">

                                    <c:if test="${pageContext.request.userPrincipal.name != messageList.fromUser}">
                                        <div style="padding: 5px; border-bottom: 1px solid grey; background-color: dodgerblue">
                                            <p style="margin: 3px"><b>${messageList.fromUser}</b></p>
                                            <p style="margin: 3px">${messageList.content}</p>
                                            <small style="margin: 3px">${messageList.time}</small>
                                        </div>
                                    </c:if>

                                    <c:if test="${pageContext.request.userPrincipal.name == messageList.fromUser}">
                                        <div style="padding: 5px; border-bottom: 1px solid grey; background-color: greenyellow">
                                            <p style="margin: 3px"> <b>Me</b></p>
                                            <p style="margin: 3px">${messageList.content}</p>
                                            <small style="margin: 3px">${messageList.time}</small>
                                        </div>
                                    </c:if>

                                </c:forEach>
                            </c:if>
                        </div>

                        <div>
                            <div id="response" class="chat"></div>
                        </div>
                    </div>
                    <hr>
                    <div>
                        <c:if test="${pageContext.request.userPrincipal.name == authorInfo.email}">
                            <a href="<c:url value="${contextPath}/messages/p/add/${messageChat.id}" />">Add to a group chat</a>
                        </c:if>
                    </div>
                    <div>
                        <span>Sending to: </span>
                        <span id="to"><b>${authorInfo.email}</b></span>
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
        </div>

        <script src="/js/sckfnc.js"></script>
        <script src="/js/sck.js"></script>
    </div>
</body>
</html>