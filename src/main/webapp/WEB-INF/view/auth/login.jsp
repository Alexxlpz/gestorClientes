<jsp:useBean id="error" scope="request" class="java.lang.String" type="java.lang.String"/>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/auth/login.css">
</head>
<body>

<div class="form-container">
    <h1>Login</h1>

    <form:form action="/auth/dologin" method="post" modelAttribute="authForm">
        <label for="username">Usuario:</label>
        <form:input path="username" id="username" placeholder="Username..." />

        <label for="password">Contraseña:</label>
        <form:input path="password" type="password" id="password" placeholder="Password..." />

        <div class="toggle-container">
             <label for="companyToggle">¿eres empresa?</label>
             <label class="switch">
                 <input type="checkbox" name="company" id="companyToggle"/>
                 <span class="slider"></span>
             </label>
         </div>

        <div>
            <form:button type="submit" class="glass-button">Login</form:button>
        </div>
    </form:form>

    <div class="form-footer">
        <p>¿No tienes cuenta? <a href="${pageContext.request.contextPath}/auth/register">Regístrate</a></p>
        <p style="color:red">${error}</p>
    </div>
</div>

</body>
</html>
