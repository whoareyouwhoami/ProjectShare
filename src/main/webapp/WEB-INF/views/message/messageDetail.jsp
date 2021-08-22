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
        <div class="row">
            <div class="col">
                <c:if test="${showMessageList}">
                    <div class="p-3 border border-secondary border-2 rounded-4 message-detail overflow-scroll">
                        <c:if test="${messages != null}">
                            <c:forEach var="messageList" items="${messages}">
                                <c:if test="${pageContext.request.userPrincipal.name != messageList.sender.email}">
                                    <div class="text-end msg-content">
                                        <p class="fw-bold" style="margin: 3px">${messageList.sender.firstName}</p>
                                        <p style="margin: 3px">${messageList.content}</p>
                                        <small style="margin: 3px">${messageList.sent}</small>
                                    </div>
                                </c:if>
                                <c:if test="${pageContext.request.userPrincipal.name == messageList.sender.email}">
                                    <div class="text-start msg-content">
                                        <p class="fw-bold" style="margin: 3px">Me</p>
                                        <p class="text-wrap" style="margin: 3px">${messageList.content}</p>
                                        <small style="margin: 3px">${messageList.sent}</small>
                                    </div>
                                </c:if>
                            </c:forEach>
                        </c:if>
                        <div content="text-end chat" id="response"></div>
                    </div>

                    <c:if test="${pageContext.request.userPrincipal.name == messageChat.project.author.email}">
                        <div class="row mt-2">
                            <div class="col-7">
                                <!-- Button trigger modal -->
                                <a class="link-success" data-bs-toggle="modal" data-bs-target="#exampleModal">
                                    Add user to group chat
                                </a>
                                <!-- Modal -->
                                <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="addUserModal" aria-hidden="true">
                                    <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title" id="addUserModal">Project Title</h5>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                            </div>
                                            <div class="modal-body">
                                                Project description
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-danger" data-bs-dismiss="modal">Close</button>
                                                <button type="button" class="btn btn-primary">
                                                    <a class="text-reset  text-decoration-none" href="<c:url value="${contextPath}/messages/p/add/${messageChat.id}" />">Add</a>
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:if>

                    <div class="row mt-2">
                        <div class="col-10">
                            <textarea class="form-control" id="msg" placeholder="Send message to ${authorEmail}"></textarea>
                            <input type="hidden" id="to" value="${authorEmail}" />
                            <input type="hidden" id="pid" value="${messageChat.project.id}" />
                        </div>
                        <div class="col align-self-center">
                            <button type="button" class="btn btn-primary" id="btnSend" >Send</button>
                        </div>
                    </div>
                </c:if>
            </div>
            <div class="col">
                <h1 class="h2 mt-3 p-0 fw-normal">Project detail</h1>
                <table class="table">
                    <tbody>
                    <tr>
                        <th scope="row">Title</th>
                        <td>${messageChat.project.title}</td>
                    </tr>
                    <tr>
                        <th scope="row">Description</th>
                        <td>${messageChat.project.description}</td>
                    </tr>
                    <tr>
                        <th scope="row">Number of members</th>
                        <td>${messageChat.project.member}</td>
                    </tr>
                    <tr>
                        <th scope="row">Duration</th>
                        <td class="text-muted">${messageChat.project.dateStart} ~ ${messageChat.project.dateEnd}</td>
                    </tr>
                    <tr>
                        <th scope="row">Owner</th>
                        <td class="text-muted">${messageChat.project.author.firstName} [ <span class="text-primary">${messageChat.project.author.email}</span> ]</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>



        <jsp:include page="../frames/footer.jsp" />
        <script src="/js/sckfnc.js"></script>
        <script src="/js/sck.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js" integrity="sha384-eMNCOe7tC1doHpGoWe/6oMVemdAVTMs2xqW4mwXrXsW0L84Iytr2wi5v2QjrP/xp" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.min.js" integrity="sha384-cn7l7gDp0eyniUwwAZgrzD06kc/tftFf19TOAs2zVinnD/C7E91j9yyk5//jjpt/" crossorigin="anonymous"></script>
    </div>
</body>
</html>