<?xml version="1.0" encoding="iso-8859-1"?>
<%@ include file="/WEB-INF/views/include/taglib.jspf"%>
<c:set var="headElements">
	<title>Course Plan Template</title>
</c:set>
<%@ include file="/WEB-INF/views/include/header.jspf"%>
<h3>Course Plan Template</h3>
<h4>
<a href="<c:url value='/user/coursePlan/viewCoursePlanTemplates.html' />">View</a>
|Add
</h4>


<p>Course plan template for terms is based on the majors and Course schedules<br /><br />
In order to plan course template better,

 please fill out the <a href="<c:url value='user/schedule/displaySchedules.html' />"> Course Schedules </a> that have offered.
</p>
and choose a major to create a template.
<form method="post" action="addCoursePlanTemplate.html">
	<p>
	Major:
	 <c:if test="${ not isEdit }">
		<select name="majorId">
			<c:forEach items="${majors }" var="major">
				<option value="${major.id }"> ${major.symbol }</option>
			</c:forEach>
		</select>
	
	</c:if>
	<c:if test="${ isEdit }">
		${coursePlan.major.symbol}
		<input type="hidden" name="majorId" value="${coursePlan.major.id}" />
		
	</c:if>
	</p>
	<input type="hidden" name="quarterCode" value="${quarter.code}" />
	<input type="hidden" name="firstQuarter" value="true" />
	<input type="hidden" name="_page" value="0" />
	<p>
	<input type="submit" name="_target1" value="Next" class="subbutton" />
	</p>
</form>

<%@ include file="/WEB-INF/views/include/footer.jspf"%>