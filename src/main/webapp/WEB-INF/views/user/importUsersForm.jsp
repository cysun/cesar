<?xml version="1.0" encoding="iso-8859-1"?>
<%@ include file="/WEB-INF/views/include/taglib.jspf"%>
<c:set var="headElements">
	<title>Import Users</title>
</c:set>
<%@ include file="/WEB-INF/views/include/header.jspf"%>
<h3>Users</h3>
<c:if test="${ directory == 0 }" >
	<h4>
		<a href="<c:url value='/user/search/student/search.html' />">Search</a>
</c:if>
<c:if test="${ directory == 1 }" >
	<h4>
		<a href="<c:url value='/user/search/advisor/search.html' />">Search</a>
</c:if>
<c:if test="${ directory == 2 }" >
	<h4>
		<a href="<c:url value='/user/search/staff/search.html' />">Search</a>
</c:if>
<c:if test="${ directory == 3 }" >
	<h4>
		<a href="<c:url value='/user/search/user/search.html' />">Search</a>
</c:if>

	| <a href="<c:url value='/user/add.html' />">Add</a>
	| Import
</h4>

<p>Please copy and paste courses schedule from XML spreadsheet to
the text area below. The XML spreadsheet should be in the following format:<br />
</p>
<table style="font-size: 10px" class="tablesorter">
	<tr>
		<th>Admin Term</th><th>Acad Plan</th><th>Prog Actn</th><th>Action Rsn</th><th>Action Date</th><th>Admin Type</th><th>Acad Level</th><th>High School</th><th>ID</th><th>Last</th><th>First</th><th>Middle</th><th>Country</th><th>Address1</th><th>Address2</th><th>City</th><th>State</th><th>Postal</th><th>Sex</th><th>Email</th><th>Phone</th>
	</tr>
	
	<tr>
		<td>2109</td><td>BS</td><td>DEIN</td><td>SIR</td><td>05/01/2010</td><td>5</td><td>B0</td><td>Arroyo High</td><td>30000</td><td>Jone</td><td>Doe</td><td></td><td>USA</td><td>Sample Ave</td><td></td><td>Bell</td><td>CA</td><td>91000</td><td>M</td><td>sample@local.com</td><td>999/999-9999</td>
	</tr>
	
</table>
<form:form method="post" modelAttribute="importUsersCommand">
	<form:textarea path="text" rows="20" cols="80" cssStyle="width: 95%;" />
	<input type="hidden" name="_page" value="0" />
	<p><input type="submit" name="_target1" value="Next" class="subbutton" /></p>
</form:form>

<%@ include file="/WEB-INF/views/include/footer.jspf"%>