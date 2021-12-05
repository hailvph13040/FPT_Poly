<%--
  Created by IntelliJ IDEA.
  User: hai95
  Date: 11/30/2021
  Time: 09:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<c:url var="url" value="/"></c:url>
<a class="navbar-brand" href="${url}Home">ONLINE ENTERTAMENT</a>
<button class="navbar-toggler" type="button"
        data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
        aria-controls="navbarSupportedContent" aria-expanded="false"
        aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
</button>
<div class="collapse navbar-collapse" id="navbarSupportedContent">
    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item">
            <a class="nav-link active"
               aria-current="page" href="${url}user/favorite">MY FAVORITE</a></li>
    </ul>
    <div class="nav-item dropdown me-4">
        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
           role="button" data-bs-toggle="dropdown" aria-expanded="false" style="color: black">
            <c:choose>
                <c:when test="${not empty sessionScope.user}">
                    ${sessionScope.user.username.toUpperCase()}
                </c:when>
                <c:otherwise>
                    <span class="material-icons">account_circle</span>
                </c:otherwise>
            </c:choose>
        </a>
        <ul class="dropdown-menu dropdown-menu-end dropdown-menu-lg-star"
            aria-labelledby="navbarDropdown">
            <c:choose>
                <c:when test="${not empty sessionScope.user}">
                    <li><a class="dropdown-item" href="${url}sign-out">Log Off</a></li>
                    <li><a class="dropdown-item" href="${url}user/change-password">Change Password</a></li>
                    <li>
                        <hr class="dropdown-divider">
                    </li>
                    <li><a class="dropdown-item" href="${url}user/edit-profile">Edit Profile</a></li>
                </c:when>
                <c:otherwise>
                    <li><a class="dropdown-item" href="${url}sign-in">Login</a></li>
                    <li><a class="dropdown-item" href="${url}sign-up">Registration</a></li>
                    <li><a class="dropdown-item" href="${url}forgot-password">Forgot Password</a></li>
                </c:otherwise>
            </c:choose>
        </ul>
    </div>
</div>
