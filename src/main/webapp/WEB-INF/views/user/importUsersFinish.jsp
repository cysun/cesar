<?xml version="1.0" encoding="iso-8859-1"?>
<%@ include file="/WEB-INF/views/include/taglib.jspf"%>
<c:set var="headElements">
	<title>Import Users</title>
	<script type="text/javascript">
/* <![CDATA[ */
$(function() {
    $(".usertable tbody tr:odd").css("background-color","#F0F0F6");
});
/* ]]> */
</script>
</c:set>
<%@ include file="/WEB-INF/views/include/header.jspf"%>

<h3>
	<a href="<c:url value='/user/add.html' />">Add User</a> | 
	<a href="<c:url value='/user/search.html' />">Search Users</a> | Import Users 
</h3>


<p>Please examine the user records and click the Confirm button to import them
into the system. In case the user records are not parsed correctly, use the
Back button to go back to the previous page, and adjust the copy&amp;pasted
user list manually. In particular, make sure each records is in the form:</p>
<p style="text-align: center;"></p>

<display:table name="importUsersCommand.importedUsers" uid="importedUser"
    class="usertable shadow" style="width: 90%" >
  <display:column title="CIN"  property="cin" />
  <display:column title="Last Name" property="lastName" />
  <display:column title="First Name" property="firstName" />
  <display:column title="Email" property="email" />
  <display:column title="CellPhone" property="cellPhone" />
  <display:column title="Address1" property="address1" />
  <display:column title="Quarter Admitted" property="quarterAdmitted" />

</display:table>

<form:form commandName="importUsersCommand">
	<input type="hidden" name="_page" value="1" />
	<p>
		<input type="submit" name="_target0" value="Back" class="subbutton" />
		<input type="submit" name="_finish" value="Finish" class="subbutton" />
	</p>
</form:form>

<c:if test="${fn:length(importUsersCommand.invalidLines) != 0}">
	<p class="error">Invalid lines:</p>
	<ul>
		<c:forEach var="invalidLine" items="${importUsersCommand.invalidLines}">
			<li><tt><c:out value="${invalidLine}" escapeXml="true" /></tt></li>
		</c:forEach>
	</ul>
</c:if>

<%@ include file="/WEB-INF/views/include/footer.jspf"%>