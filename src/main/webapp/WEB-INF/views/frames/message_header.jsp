<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<ul class="nav nav-tabs">
  <li class="nav-item">
    <a class="nav-link" href="<c:url value="${contextPath}/messages" />">Private</a>
  </li>
  <li class="nav-item">
    <a class="nav-link" href="<c:url value="${contextPath}/messages/g" />">Project Group</a>
  </li>
</ul>