<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: alejandro
  Date: 7/22/25
  Time: 1:05 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Atributos para la ficha</title>
    <%@include file="/WEB-INF/components/navbar_Company.jsp"%>
</head>
<body>

<h1>Atributos a recopilar para la ficha</h1>


<a href="/schema/addSchema">Add Schema</a>

<table border="1">
    <thead>
    <tr>
      <th>Nombre</th>
      <th>Tipo</th>
      <th>Obligatorio</th>
      <th>Editar</th>
      <th>Eliminar</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${schemas}" var="attr">
      <tr>
        <td>
          <p>${attr.name}</p>
        </td>
        <td>
          <p>${attr.type}</p>
        </td>
        <td>
          <c:choose>
            <c:when test="${attr.mandatory}">
              <p>Yes</p>
            </c:when>
            <c:otherwise>
              <p>No</p>
            </c:otherwise>
          </c:choose>
        </td>
        <td>
          <a href="/schema/editSchema?schemaid=${attr.id}">editar</a>
        </td>
        <td>
          <a href="/schema/removeSchema?schemaid=${attr.id}">X</a>
        </td>
      </tr>
    </c:forEach>
    </tbody>
  </table>

</body>
</html>
