<%--
  Created by IntelliJ IDEA.
  User: alejandro
  Date: 7/20/25
  Time: 10:27 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="appt" value="${currentAppt}" />

<c:if test="${not empty appt}"> <%-- sin esto cuando appointments es vacio se ejecuta una vez con los datos vacios --%>
    <li>
        <div>
            Fecha: ${appt.date} | Hora: ${appt.time}<br/>
            Motivo: ${appt.desc}
        </div>
    </li>
</c:if>
