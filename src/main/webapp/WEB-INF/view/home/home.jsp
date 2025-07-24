<%--
  Created by IntelliJ IDEA.
  User: alejandro
  Date: 7/18/25
  Time: 5:05 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.alexxlpz04.gestorclientes.entities.Company" %>
<%@ page import="com.alexxlpz04.gestorclientes.entities.User" %>
<%
    Company company = (Company) request.getAttribute("company");
    User user = (User) request.getAttribute("user");

    boolean isCompany = company != null;
%>

<html>
<head>
    <title>Gestor de clientes</title>
    <%if(isCompany){%>
        <%@include file="/WEB-INF/components/navbar_Company.jsp"%>
    <%}else {%>
        <%@include file="/WEB-INF/components/navbar_User.jsp"%>
    <%}%>

</head>
<body>

<h1>Bienvenido al Gestor de Clientes</h1>

<p>Utiliza el menú de navegación para acceder a las diferentes secciones.</p>

<p>Poner carruseles y tal para adornar la pagina home.</p>

<%if(isCompany){%>
    <p><strong> Soy <%=company.getAccount().getName()%> y soy company </strong></p>
<%}else {%>
    <p><strong> Soy <%=user.getAccount().getName()%> y soy user </strong></p>
<%}%>



</body>
</html>
