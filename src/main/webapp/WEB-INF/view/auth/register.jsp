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
    <title>Register</title>
</head>
<body>

<h1>Register Page</h1>

<form:form action="/auth/doregister" method="post" modelAttribute="account">
    <label for="user">usuario: </label> <br/>
    <form:input path="user" id="user" placeholder="Username..." /> <br/>
    <label for="password">contraseña: </label> <br/>
    <form:input path="password" type="password" id="password"  placeholder="Password..." /> <br/>

    <label for="name">nombre: </label> <br/>
    <form:input path="name" id="name" placeholder="Nombre..." /> <br/>
    <label for="surname">apellido: </label> <br/>
    <form:input path="surname" id="surname"  placeholder="Apellido..." /> <br/>

    <form:button>Register</form:button>
</form:form>

<p>
    si ya tienes cuenta pulsa <a href="/auth/login">Login</a>
</p>

<br/>

<p>${error}</p>
</body>
</html>
