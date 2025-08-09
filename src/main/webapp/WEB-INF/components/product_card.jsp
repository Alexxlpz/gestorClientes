<%--
  Created by IntelliJ IDEA.
  User: alejandro
  Date: 8/9/25
  Time: 4:41 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="actual_product" scope="request" type="com.alexxlpz04.gestorclientes.entities.Product"/>
<c:set var="product" value="${actual_product}" />

<div>
    <div>
        <img src="https://cdn-icons-png.flaticon.com/512/5650/5650378.png" alt="Logo de ${product.name}" />
        <h2>${product.name}</h2>
        <p>desc: ${product.desc}</p>
        <a href="/products/view?productid=${product.id}" target="_blank">Visitar</a>
    </div>
</div>
