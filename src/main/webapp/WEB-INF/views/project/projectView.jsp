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
    <title>Upload project</title>
</head>

<body>
    <div>
        <div>
            <h1>View Project</h1>
            <a href="<c:url value="${contextPath}/home" />">Home</a>
            <a href="<c:url value="${contextPath}/project" />">Project</a>
            <a href="<c:url value="${contextPath}/project/upload" />">Upload</a>
            <a href="<c:url value="${contextPath}/project/search" />">Search</a>
            <a href="<c:url value="${contextPath}/project/view" />">View My Project</a>
        </div>

        <div>
            <c:if test="${p_detail == null}">
                <div>
                    <p>Project doesn't exist...</p>
                </div>
            </c:if>

            <c:if test="${p_detail != null}">
                <div>
                    <p>Title: ${p_detail.title}</p>
                </div>
                <div>
                    <p>Description: ${p_detail.description}</p>
                </div>
                <div>
                    <p>No. of members: ${p_detail.member}</p>
                </div>
                <div>
                    <p>Duration: ${p_detail.dateStart} ~ ${p_detail.dateEnd}</p>
                </div>

                <div>
                    <c:if test="${pageContext.request.userPrincipal.name == p_detail.user.iterator().next().email}">
                        <a href="<c:url value="${contextPath}/project/update/${p_detail.id}" />">Update</a>
                        <a href="<c:url value="${contextPath}/project/delete/${p_detail.id}" />">Delete</a>
                    </c:if>
                    <c:if test="${pageContext.request.userPrincipal.name != p_detail.user.iterator().next().email}">
                        <a href="<c:url value="${contextPath}/messages/p/${p_detail.id}" />">Send message</a>
                    </c:if>
                </div>
            </c:if>
        </div>
    </div>
</body>
</html>