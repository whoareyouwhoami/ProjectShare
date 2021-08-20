<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<ul class="nav nav-tabs">
  <li class="nav-item">
    <a class="nav-link" href="<c:url value="${contextPath}/project" />">Home</a>
  </li>
  <li class="nav-item">
    <a class="nav-link" href="<c:url value="${contextPath}/project/upload" />">Upload</a>
  </li>
  <li class="nav-item">
    <a class="nav-link" href="<c:url value="${contextPath}/project/search" />">Search</a>
  </li>
</ul>