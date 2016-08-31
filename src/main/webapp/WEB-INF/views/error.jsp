<%@ include file="/WEB-INF/views/include/taglib.jspf" %>
<c:set var="headElements">
  <title>CESAR error</title>
</c:set>
<c:if test="${empty backUrl}">
  <c:set var="backUrl" value="/" />
</c:if>
<%@ include file="/WEB-INF/views/include/header.jspf" %>


<h3>${error}</h3>
<p>${errorCause}</p>
<p><button type="button" class="subbutton"
onclick="window.location.href='<c:url value="${backUrl}" />';">OK</button></p>

<%@ include file="/WEB-INF/views/include/footer.jspf" %>
