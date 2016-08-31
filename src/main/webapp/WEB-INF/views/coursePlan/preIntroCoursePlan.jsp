<?xml version="1.0" encoding="iso-8859-1"?>
<%@ include file="/WEB-INF/views/include/taglib.jspf"%>
<c:set var="headElements">
	<title>Intro Course Plan</title>
</c:set>
<%@ include file="/WEB-INF/views/include/header.jspf"%>
<h3>Intro Course Plan</h3>
<c:if test="${isAdvisor }">
<h4>
<a href="<c:url value='/user/display.html?userId=${currentUser.id }&#tab-advisement' />">View</a>
> Select
</h4>
</c:if>
<c:if test="${not isAdvisor }">
<h4>
<a href="<c:url value='/profile.html?#tab-advisement' />">View</a>
> Select
</h4>
</c:if>

<p>Course plan for quarters is based on the user's major and the courses that the user has taken.<br /><br />
In order to plan courses better,
<c:if test="${not isAdvisor }" >
 please select your <a href="/cesar/profile.html?#tab-program"> major </a> and fill out the <a href="/cesar/profile.html?#tab-courses"> courses </a> that you have taken.
</c:if>
<c:if test="${isAdvisor }" >
 please select your <a href="/cesar/user/display.html?userId=${currentUser.id }#tab-program"> major </a> and fill out the <a href="/cesar/user/display.html?userId=${currentUser.id }#tab-courses"> courses </a> that you have taken.

</c:if>
</p>
<p style="color:red">${majorError}</p>
<c:if test="${empty majorError }">
	<form method="post" action="addCoursePlanIntro.html">
	
		
		<input type="hidden" name="studentId" value="${currentUser.id }" />
		
		<c:if test="${fn:length(coursePlanTemplates) > 0}">
			<p>
			Start from course plan template(s):
			</p>
		</c:if>
		<table class="tablesorter" >
			<tr>
				<th></th><th>Name</th><th>Note</th>
			</tr>
			<c:if test="${fn:length(coursePlanTemplates) > 0}">
				<c:forEach items="${coursePlanTemplates}" var="coursePlan" >
					
					<tr>
						<td><input type="radio" name="coursePlanId" value="${coursePlan.id }"/></td>
						
						<td>
							<a href="<c:url value='/profile/coursePlan/view/viewCoursePlan.html?coursePlanId=${coursePlan.id}&forStudentView=true' />">
								${coursePlan.name }
					   		</a>
					 	</td>
					 	<td>
					 		${coursePlan.note }
					 	</td>
					</tr>
				</c:forEach>
			</c:if>
		</table>
		<br />
		<p>Create a course plan: </p>
		<table class="tablesorter">
			<tr>
				<th></th><th>Name</th><th>Note</th>
			</tr>
			<tr>
				<td><input type="radio" name="coursePlanId" value="-1" checked/></td>
				<td>New Course Plan</td><td> </td>
			</tr>
		</table>
	
	
		<p>
		<input type="submit" name="next" value="Next" class="subbutton" />
		</p>
	</form>
</c:if>

<%@ include file="/WEB-INF/views/include/footer.jspf"%>