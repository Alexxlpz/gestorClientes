<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.alexxlpz04.gestorclientes.entities.Company" %>
<%@ page import="java.util.List" %>
<%@ page import="com.alexxlpz04.gestorclientes.entities.Appointment" %><%--
  Created by IntelliJ IDEA.
  User: alejandro
  Date: 7/20/25
  Time: 10:03 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Company company = (Company) request.getAttribute("company");
    List<Appointment> appointments = (List<Appointment>) request.getAttribute("appointments");
    String[] days = (String[]) request.getAttribute("days");
%>

<html>
<head>
    <title>Detalles de la Compañía</title>
</head>
<body>

<h1><%=company.getCompanyName()%></h1>

<img src="https://cdn-icons-png.flaticon.com/512/5650/5650378.png" alt="Logo de <%=company.getCompanyName()%>" width="150" />

<p><strong>Tipo:</strong> <%=company.getCompanyType().getCompanyType()%></p>

<h2>Horario de Atención</h2>
<ul>
    <%for(String day:days){%>
        <li><%=day%> -> desde <%=company.getHoraInicio()%> hasta <%=company.getHoraFin()%></li>
    <%}%>
</ul>

<h2>Tus Citas</h2>
<p>appointments: ${appointments}</p>
<p>empty? ${empty appointments}</p>
<c:choose>
    <c:when test="${not empty appointments}">
        <ul>
            <c:forEach var="appt" items="${appointments}">
                <c:set var="currentAppt" value="${appt}" scope="request" />
                <jsp:include page="/WEB-INF/components/appointment_card.jsp" />
            </c:forEach>
        </ul>
    </c:when>
    <c:otherwise>
        <p>No tienes citas registradas con esta compañía.</p>
    </c:otherwise>
</c:choose>

<p>si eres cliente pulsa <a href="/schema/createRecord?companyid=<%=company.getId()%>"> aqui </a></p>

</body>
</html>
