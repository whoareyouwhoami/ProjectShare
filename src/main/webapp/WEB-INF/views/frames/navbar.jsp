<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<nav class="navbar navbar-expand-lg navbar-light bg-light p-3">
   <div class="container">
      <a class="navbar-brand" href="/home">Project Share</a>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
         <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarNavDropdown">
         <ul class="navbar-nav">
            <li class="nav-item">
               <a class="nav-link" href="<c:url value="${contextPath}/project" />">Project</a>
            </li>
            <li class="nav-item">
               <a class="nav-link" href="<c:url value="${contextPath}/messages" />">Message</a>
            </li>
            <li class="nav-item dropdown">
               <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                  Settings
               </a>
               <ul class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                  <li><a class="dropdown-item" href="<c:url value="${contextPath}/privacy" />">Change password</a></li>
                  <li><a class="dropdown-item" href="<c:url value="${contextPath}/logout" />">Logout</a></li>
               </ul>
            </li>
         </ul>
      </div>
   </div>
</nav>