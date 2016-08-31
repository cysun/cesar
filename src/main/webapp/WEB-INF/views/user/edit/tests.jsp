<?xml version="1.0" encoding="iso-8859-1"?>
<%@ include file="/WEB-INF/views/include/taglib.jspf"%>
<c:set var="headElements">
<title>CESAR-Edit-Tests</title>
<script type="text/javascript">
/* <![CDATA[ */
$(function() {
    $(".usertable tbody tr:odd").css("background-color","#F0F0F6");
});
/* ]]> */
</script>
</c:set>
<%@ include file="/WEB-INF/views/include/header.jspf"%>
<form:form modelAttribute="user">
	<c:if test="${ not forProfile }">
		<c:if test="${ directory == 0 }">
			<h3>Students</h3>
			<h4>
				<a href="<c:url value='/user/search/student/search.html'/>">Search</a>
		</c:if>
		
		<c:if test="${  directory == 1 }">
			<h3>Advisors</h3>
			<h4>
				<a href="<c:url value='/user/search/advisor/search.html'/>">Search</a>
		</c:if>
		
		<c:if test="${  directory == 2 }">
			<h3>Staffs</h3>
			<h4>
				<a href="<c:url value='/user/search/staff/search.html'/>">Search</a>
		</c:if>
		
		<c:if test="${  directory == 3 }">
			<h3>Users</h3>
			<h4>
				<a href="<c:url value='/user/search/user/search.html'/>">Search</a>
		</c:if>
			 &gt;
				<a href="<c:url value='/user/display.html?userId=${user.id }'/>">${user.firstName}
				${user.middleName} ${user.lastName}</a> &gt; Tests
		</h4>
	</c:if>
	<c:if test="${ forProfile }">
		<h3>Profile</h3>
		<h4>
			<a href="<c:url value='/'/>">Home</a> &gt;
			<a href="<c:url value='/profile.html'/>">${user.firstName}
			${user.middleName} ${user.lastName}</a> &gt; Tests
		</h4>
	</c:if>
	<form:hidden path="id" />
	<table class="usertable shadow" cellpadding="1.5em" cellspacing="0">

		<tr>
			<th>ELM</th>
			<td><form:input path="elm" /></td>
		</tr>

		<tr>
			<th>EPT</th>
			<td><form:input path="ept" /></td>
		</tr>


		<tr>
			<th>SAT_MATH</th>
			<td><form:input path="satMath" /></td>
		</tr>

		<tr>
			<th>SAT_VERBAL</th>
			<td><form:input path="satVerbal" /></td>
		</tr>

		<tr>
			<th>ACT_MATH</th>
			<td><form:input path="actMath" /></td>
		</tr>

		<tr>
			<th>ACT_VERBAL</th>
			<td><form:input path="actVerbal" /></td>
		</tr>

		<tr>
			<th>EAP_MATH</th>
			<td><form:input path="eapMath" /></td>
		</tr>

		<tr>
			<th>EAP_VERBAL</th>
			<td><form:input path="eapVerbal" /></td>
		</tr>

		<tr>
			<th>AP_CALCULUS_A</th>
			<td><form:input path="apCalculusA" /></td>
		</tr>

		<tr>
			<th>AP_CALCULUS_B</th>
			<td><form:input path="apCalculusB" /></td>
		</tr>

		<tr>
			<th>AP_CALCULUS_C</th>
			<td><form:input path="apCalculusC" /></td>
		</tr>

		<tr>
			<th>AP_PHYSICS</th>
			<td><form:input path="apPhysics" /></td>
		</tr>

		<tr>
			<th>AP_CHEMISTRY</th>
			<td><form:input path="apChemistry" /></td>
		</tr>

		<tr>
			<th>AP_BIOLOGY</th>
			<td><form:input path="apBiology" /></td>
		</tr>
		<tr>
			<td style="text-align:center;" colspan="2"><input type="submit" name="save" value="Save" /></td>
		</tr>
	</table>

</form:form>
<%@ include file="/WEB-INF/views/include/footer.jspf"%>