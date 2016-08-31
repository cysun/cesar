<?xml version="1.0" encoding="iso-8859-1"?>
<%@ include file="/WEB-INF/views/include/taglib.jspf" %>
<c:set var="headElements">
  <title>CESAR Register_1</title>
</c:set>
<%@ include file="/WEB-INF/views/include/header.jspf" %>
<c:if test="${empty error }" >
<p> Enter your CIN to register</p>
</c:if>
<p>${error }</p>
<form action="register.html" method="post">

CIN: <input name="cin" style="width: 10em;" /> 

<input type="hidden" value="0" name="_page" />
<input type="submit" value="Next" name="_target0" />
<input type="submit" value="Cancel" name="_cancel"/> <br />
</form>

<%@ include file="/WEB-INF/views/include/footer.jspf"%>