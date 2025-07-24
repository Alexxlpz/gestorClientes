<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: alejandro
  Date: 7/17/25
  Time: 5:36 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>

<h1>Login Page</h1>

<form:form action="/auth/dologin" method="post" modelAttribute="authForm">
    <label for="username">usuario: </label> <br/>
    <form:input path="username" id="username" placeholder="Username..." /> <br/>
    <label for="password">contraseña: </label> <br/>
    <form:input path="password" type="password" id="password"  placeholder="Password..." /> <br/>
    <label for="isCompany">soy una empresa </label>
    <form:checkbox path="company" id="isCompany"/>
    <form:button>Login</form:button>
</form:form>

<p>
    si no tienes cuenta pulsa <a href="/auth/register">Register</a>
</p>

<br/>

<p>${error}</p>

</body>
</html>
