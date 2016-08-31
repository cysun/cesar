<?xml version="1.0" encoding="iso-8859-1"?>
<%@ include file="/WEB-INF/views/include/taglib.jspf"%>
<c:set var="headElements">
<title>CESAR-Edit Demographic</title>
<script type="text/javascript">
/* <![CDATA[ */
$(function() {
    $('#birthday').datepicker({
        inline: true,
        changeMonth: true,
        changeYear: true,
        yearRange: '-60:+00'
    });
    
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
				${user.middleName} ${user.lastName}</a> &gt; Demographic
		</h4>
	</c:if>
	<c:if test="${ forProfile }">
		<h3>Profile</h3>
		<h4>
			<a href="<c:url value='/'/>">Home</a> &gt;
			<a href="<c:url value='/profile.html'/>">${user.firstName}
			${user.middleName} ${user.lastName}</a> &gt; Demographic
		</h4>
	</c:if>
	<form:hidden path="id" />
	<table class="usertable shadow" cellpadding="1.5em" cellspacing="0">
		<tr>
			<th>Gender</th>
			<td>
				<form:select path="gender">
					<form:option value="M">Male</form:option>
					<form:option value="F">Female</form:option>
				</form:select>
			</td>
		</tr>

		<tr>
			<th>Birthday (mm/dd/yyyy)</th>
			<td><form:input path="birthday" size="40"/></td>
		</tr>

		<tr>
			<th>Ethnicity</th>
			<td>
				<form:select path="ethnicity">
					<c:forEach items="${ethnicities}" var="ethnicity">
						<form:option value="${ethnicity}">
			   					 			${ethnicity.name}
			   			</form:option>
					</c:forEach>
				</form:select>
			</td>
		</tr>

		<tr>
			<th>Work Hours Per Week</th>
			<td><form:input path="workHoursPerWeek" size="40" /></td>
		</tr>

		<tr>
			<th>Community time in minutes</th>
			<td><form:input path="commuteTimeInMinutes" size="40" /></td>
		</tr>

		<tr>
			<th style="text-align:center;" colspan="2"><input type="submit" name="save" value="Save" /></th>
		</tr>
	</table>
</form:form>
<%@ include file="/WEB-INF/views/include/footer.jspf"%>