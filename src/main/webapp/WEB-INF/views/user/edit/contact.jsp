<?xml version="1.0" encoding="iso-8859-1"?>
<%@ include file="/WEB-INF/views/include/taglib.jspf"%>
<c:set var="headElements">
<title>CESAR- Edit Contact</title>
<script type="text/javascript">
/* <![CDATA[ */
$(function() {
    $(".usertable tbody tr:odd").css("background-color","#F0F0F6");
});
$().ready(function() {
	$("input.phone").mask("(999) 999-9999");
	$("input.zipcode").mask("99999");
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
				${user.middleName} ${user.lastName}</a> &gt; Contact
		</h4>
	</c:if>
	
	<c:if test="${ forProfile }">
		<h3>Profile</h3>
		<h4>
			<a href="<c:url value='/'/>">Home</a> &gt;
			<a href="<c:url value='/profile.html'/>">${user.name}</a> &gt; Contact
		</h4>
	</c:if>
	<form:hidden path="id" />
	<table class="usertable shadow" cellpadding="1.5em" cellspacing="0">

		<tr>
			<th>Address1</th>
			<td><form:input path="address1" size="40" /></td>
		</tr>

		<tr>
			<th>Address2</th>
			<td><form:input path="address2" size="40" /></td>
		</tr>

		<tr>
			<th>City</th>
			<td><form:input path="city" size="40" /></td>
		</tr>

		<tr>
			<th>State</th>
			<td><form:input path="state" size="40" /></td>
		</tr>

		<tr>
			<th>Zip</th>
			<td><form:input class="zipcode" path="zip" size="40" /></td>
		</tr>

		<tr>
			<th>Country</th>
			<td><form:input path="country" size="40" /></td>
		</tr>

		<tr>
			<th>Home Phone</th>
			<td><form:input  class="phone" path="homePhone" size="40" /></td>
		</tr>

		<tr>
			<th>Office Phone</th>
			<td><form:input  class="phone" path="officePhone" size="40" /></td>
		</tr>

		<tr>
			<th>Cell Phone</th>
			<td><form:input class="phone" path="cellPhone" size="40" /></td>
		</tr>

		<tr>
			<th style="text-align:center;" colspan="2"><input type="submit" name="save" value="Save" /></th>
		</tr>
	</table>
</form:form>
<%@ include file="/WEB-INF/views/include/footer.jspf"%>