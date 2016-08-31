<?xml version="1.0" encoding="iso-8859-1"?>
<%@ include file="/WEB-INF/views/include/taglib.jspf" %>
<c:set var="headElements">
  <title>Email Error</title>
</c:set>
<%@ include file="/WEB-INF/views/include/header.jspf" %>

<h2>Email Error</h2>
<p>Fail to send email to the following addresse(s):</p>
<ul>
<c:forEach items="${failedAddresses}" var="addr">
<li><c:out value="${addr}" escapeXml="true"/></li>
</c:forEach>
</ul>

<%@ include file="/WEB-INF/views/include/footer.jspf" %>
