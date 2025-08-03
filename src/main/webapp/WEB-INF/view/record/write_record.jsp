<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: alejandro
  Date: 7/24/25
  Time: 7:36 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>rellenar ficha</title>
</head>
<body>
<form:form modelAttribute="recordForm" method="post" action="${pageContext.request.contextPath}/record/viewRecordCompleted">

    <h1>Rellenar ficha</h1>

    <form:hidden path="record"/>
    <!-- Iteración sobre los atributos -->
    <c:forEach var="pair" items="${recordForm.atributes}" varStatus="status">
        <div>
            <strong>${pair.schema.name}</strong><br/>

            <form:hidden path="atributes[${status.index}].schema" />

            <form:label path="atributes[${status.index}].atribute.value">Value</form:label>
            <form:input path="atributes[${status.index}].atribute.value" />

            <form:hidden path="atributes[${status.index}].atribute.scheme" />
            <form:hidden path="atributes[${status.index}].atribute.record" />
        </div>
    </c:forEach>

    <input type="submit" value="Enviar" />
</form:form>

</body>
</html>
