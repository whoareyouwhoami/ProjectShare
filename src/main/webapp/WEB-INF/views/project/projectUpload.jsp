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
    <%--    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">--%>
    <%--    <link href="${contextPath}/resources/css/starter.css" rel="stylesheet">--%>
    <title>Upload project</title>
</head>

<body>
<div>
    <div>
        <h1>Upload project</h1>
        <a href="<c:url value="${contextPath}/home" />">Home</a>
    </div>

    <form:form method="post" modelAttribute="ProjectForm">
        <div>
            <form:input type="text" path="title" placeholder="Title"/>
            <form:errors path="title"/>
        </div>
        <div>
<%--            <form:input type="text" path="description" placeholder="Description"/>--%>
            <form:textarea path="description" placeholder="Description" style="resize:none" rows="6" cols="50"/>
            <form:errors path="description"/>
        </div>
        <div>
            <form:label path="">No. of members</form:label>
            <form:input type="number" path="member"/>
            <form:errors path="member"/>
        </div>
        <div>
            <form:label path="dateEnd">Start date</form:label>
            <form:input type="date" path="dateStart"/>
            <form:errors path="dateStart"/>
        </div>
        <div>
            <form:label path="dateEnd">End date</form:label>
            <form:input type="date" path="dateEnd"/>
            <form:errors path="dateEnd"/>
        </div>
<%--        <div>--%>
<%--            <form:select path="visibility">--%>
<%--                <form:option value="" label="Visibility" disabled="true" selected="true"/>--%>
<%--                <form:option value="public" label="Public"/>--%>
<%--                <form:option value="private" label="Private"/>--%>
<%--            </form:select>--%>
<%--        </div>--%>
        <div>

            <c:if test="${fn:endsWith(path, '/upload')}">
                <button class="" type="submit">Upload</button>
            </c:if>

            <c:if test="${not fn:endsWith(path, '/upload')}">
                <button class="" type="submit">Update</button>
            </c:if>

            <a href="<c:url value="${contextPath}/home" />">Cancel</a>
        </div>
    </form:form>

    <footer class="footer mt-auto py-3">
        <div class="container">
            <small class="text-muted">Copyright &copy; J Production 2021</small>
            <br/>
            <small class="text-muted">All Right Reserved</small>
        </div>
    </footer>
</div>
</body>
</html>