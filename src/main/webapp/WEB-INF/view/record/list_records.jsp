<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: alejandro
  Date: 7/24/25
  Time: 1:10 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>lista de fichas no completas</title>
    <%@include file="/WEB-INF/components/navbar_Company.jsp"%>
</head>
<body>
    <h1>Lista de fichas no completas</h1>
    <ul>
        <c:forEach items="${records}" var="record">
            <li>
                <a href="${pageContext.request.contextPath}/record/viewRecord?recordid=${record.id}">${record.user.account.name}</a>
            </li>
        </c:forEach>
    </ul>
    <p>Total de fichas no completas: ${records.size()}</p>
</body>
</html>
