<%--
  Created by IntelliJ IDEA.
  User: alejandro
  Date: 7/22/25
  Time: 1:14 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title>Definir Atributos</title>
    <%@include file="/WEB-INF/components/navbar_Company.jsp"%>
</head>
<body>

<h2>Atributos a recopilar</h2>

<form:form action="/schema/doAddSchema" method="post" modelAttribute="schemaForm">
  <table border="1">
      <form:hidden path="id" />
      <form:input path="name" />

      <form:select path="type">
        <form:option value="Text" label="Texto" />
        <form:option value="Number" label="Numero" />
        <form:option value="Email" label="Email" />
        <form:option value="Date" label="Fecha" />
        <form:option value="Bool" label="Booleano" />
      </form:select>

      <form:checkbox path="mandatory" />

    <form:hidden path="companyId"/>
  </table>

  <br/>

  <input type="button" value="Cancelar" onclick="window.location.href='/schema/addSchema'" /> <!-- si le da el boton recarga la pagina borrando asi los datos -->
  <input type="submit" value="Guardar">
</form:form>

</body>
</html>
