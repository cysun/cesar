<?xml version="1.0" encoding="iso-8859-1"?>
<%@ include file="/WEB-INF/views/include/taglib.jspf" %>
<c:set var="headElements">
  <title>CESAR Status</title>
</c:set>
<c:if test="${empty backUrl}">
  <c:set var="backUrl" value="/" />
</c:if>
<%@ include file="/WEB-INF/views/include/header.jspf" %>

<h2>${msgTitle}</h2>
<p>${msg}</p>
<p><button type="button" class="subbutton"
onclick="window.location.href='<c:url value="${backUrl}" />';">OK</button></p>


<%@ include file="/WEB-INF/views/include/footer.jspf" %>


