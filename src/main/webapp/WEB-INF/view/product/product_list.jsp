<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: alejandro
  Date: 8/9/25
  Time: 4:35 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="user" scope="session" type="com.alexxlpz04.gestorclientes.entities.User" class="com.alexxlpz04.gestorclientes.entities.User"/>
<html>
<head>
    <title>Listado de productos</title>

    <c:choose>
        <c:when test="${user.id != null}">
            <%@include file="/WEB-INF/components/navbar_User.jsp"%>
        </c:when>
        <c:otherwise>
            <%@include file="/WEB-INF/components/navbar_Company.jsp"%>
        </c:otherwise>
    </c:choose>

</head>
<body>

<h1>Listado de productos</h1>

<form action="${pageContext.request.contextPath}/products/filterByName" method="post">
    <label for="filtro">buscar por nombre: </label>
    <input type="text" name="filtro" id="filtro">
    <input type="submit" value="Buscar">
</form>

<jsp:useBean id="products" scope="request" type="java.util.List"/>
<c:forEach var="actual_product" items="${products}">
    <jsp:include page="/WEB-INF/components/product_card.jsp" />
</c:forEach>


</body>
</html>

