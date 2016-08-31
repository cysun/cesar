<?xml version="1.0" encoding="iso-8859-1"?>
<%@ include file="/WEB-INF/views/include/taglib.jspf"%>
<c:set var="headElements">
<title>CESAR- Edit High School</title>
<script type="text/javascript">
/* <![CDATA[ */
$(function() { $(".usertable tbody tr:odd").css("background-color","#F0F0F6"); });
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
				${user.middleName} ${user.lastName}</a> &gt; High School
		</h4>
	</c:if>
	<c:if test="${ forProfile }">
		<h3>Profile</h3>
		<h4>
			<a href="<c:url value='/'/>">Home</a> &gt;
			<a href="<c:url value='/profile.html'/>">${user.firstName}
			${user.middleName} ${user.lastName}</a> &gt; HighSchool
		</h4>
	</c:if>
	<form:hidden path="id" />
		<table class="usertable" cellpadding="1.5em" cellspacing="0">
			<tr>
				<th>High School</th>
				<td>
					<form:select path="highSchool">
						<c:forEach items="${highSchools}" var="highSchool">
							<form:option value="${highSchool}">
				   					 			${highSchool.name}
				   					 		</form:option>
						</c:forEach>
					</form:select>
				</td>
			</tr>
	
	
			<tr>
				<th>High School Physics</th>
				<td>
					<form:select path="highSchoolPhysics">
						<form:option value="true">Yes</form:option>
						<form:option value="false">No</form:option>
					</form:select>
				</td>
			</tr>
	
			<tr>
				<th>High School Chemistry</th>
				<td>
					<form:select path="highSchoolChemistry">
						<form:option value="true">Yes</form:option>
						<form:option value="false">No</form:option>
					</form:select>
				</td>
			</tr>
	
			<tr>
				<th>High School Calculus</th>
				<td>
					<form:select path="highSchoolCalculus">
						<form:option value="true">Yes</form:option>
						<form:option value="false">No</form:option>
					</form:select>
				</td>
			</tr>
	
			<tr>
				<th>High School Trigonometry</th>
				<td>
					<form:select path="highSchoolTrigonometry">
						<form:option value="true">Yes</form:option>
						<form:option value="false">No</form:option>
					</form:select>
				</td>
			</tr>
	
	
			<tr>
				<th>High School GPA</th>
				<td><form:input path="highSchoolGPA" /></td>
			</tr>
	
			<tr>
				<th>Community College Courses</th>
				<td><form:input path="communityCollegeCourses" size="40" /></td>
			</tr>
			
			<tr>
				<th>High School Programs</th>
				<td>
					<c:forEach items="${highSchoolPrograms}" var="highSchoolProgram">
					  ${highSchoolProgram.name} <form:checkbox path="highSchoolPrograms" value="${highSchoolProgram}" />
						<br />
					</c:forEach>
				</td>
			</tr>
	
			<tr>
				<td style="text-align:center;" colspan="2"><input type="submit" name="save" value="Save" /></td>
			</tr>
		</table>
	</form:form>
<%@ include file="/WEB-INF/views/include/footer.jspf"%>