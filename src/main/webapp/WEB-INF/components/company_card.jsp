<%--
  Created by IntelliJ IDEA.
  User: alejandro
  Date: 7/19/25
  Time: 8:10 PM
  To change this template use File | Settings | File Templates.
--%>

<%-- podria poner que el fondo este ligeramente rojo si esta cerrado e indicarlo?? --%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="company" value="${currentCompany}" />

<div>
    <div>
        <img src="https://cdn-icons-png.flaticon.com/512/5650/5650378.png" alt="Logo de ${company.companyName}" />
        <h2>${company.companyName}</h2>
        <p>Sector: ${company.companyType.companyType}</p>
        <a href="/company/view?companyid=${company.id}" target="_blank">Visitar</a>
    </div>
</div>