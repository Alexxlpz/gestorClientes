<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--
  Created by IntelliJ IDEA.
  User: alejandro
  Date: 7/19/25
  Time: 8:05 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Listado de empresas</title>
    <%@include file="/WEB-INF/components/navbar_User.jsp"%>
</head>
<body>

<h1>Listado de empresas</h1>
<h2>Seleccione la compañia con la que desea contactar</h2>

<form action="/company/filterByName" method="post">
    <label for="filtro">buscar por nombre: </label>
    <input type="text" name="filtro" id="filtro">
    <input type="submit" value="Buscar">
</form>

<c:forEach var="company" items="${companies}">
    <c:set var="currentCompany" value="${company}" scope="request" />
    <c:set var="currentCompany" value="${company}" scope="request" />
    <jsp:include page="/WEB-INF/components/company_card.jsp" />
</c:forEach>


</body>
</html>
