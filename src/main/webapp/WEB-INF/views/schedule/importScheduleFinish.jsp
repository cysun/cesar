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

<h3>Schedules</h3>

<h4>
	<a href="<c:url value='/schedule/displaySchedules.html' />">Display A Schedule</a> | 
	 Import A Schedule 
</h4>


<p>Please examine the course records and click the Confirm button to import them
into the system. In case the course records are not parsed correctly, use the
Back button to go back to the previous page, and adjust the copy&amp;pasted
courses list manually. In particular, make sure each course is entered in the system.
</p>
<p style="text-align: center;"></p>

<display:table name="importScheduleCommand.importedSections" uid="section"
    class="usertable shadow" style="width: 90%" >
  <display:column title="Course" >
  	${section.course.code } 
  	<c:if test="${ not empty section.option }">
  	-${section.option }
  	</c:if>
  </display:column>
  <display:column title="Section #" property="sectionNumber" />
  <display:column title="Call #" property="callNumber" />
  <display:column title="Units" property="units" />
  <display:column title="Days" >
  	<c:forEach items="${section.weekDays }" var="weekDay">
  		${weekDay.symbol } 
  	</c:forEach>
  </display:column>
  <display:column title="Times">
  	${section.startTime} - ${section.endTime}
  </display:column>
  <display:column title="Location" property="location" />
  <display:column title="Capacity" property="capacity" />
  

</display:table>

<form:form commandName="importUsersCommand">
	<input type="hidden" name="_page" value="1" />
	<p>
		<input type="submit" name="_target0" value="Back" class="subbutton" />
		<input type="submit" name="_finish" value="Confirm" class="subbutton" />
	</p>
</form:form>

<c:if test="${fn:length(importScheduleCommand.invalidUnitsLines) != 0}">
	<p class="error">Please check following invalid lines,
	 the units format listed in each line is invalid:</p>
	<ul>
		<c:forEach var="invalidFormatLine" items="${importScheduleCommand.invalidFormatLines}">
			<li><tt><c:out value="${invalidFormatLine}" escapeXml="true" /></tt></li>
		</c:forEach>
	</ul>
</c:if>

<c:if test="${fn:length(importScheduleCommand.invalidCourseLines) != 0}">
	<p class="error">Please check following invalid lines,
	 the course listed in each line does not exist:</p>
	<ul>
		<c:forEach var="invalidCourseLine" items="${importScheduleCommand.invalidCourseLines}">
			<li><tt><c:out value="${invalidCourseLine}" escapeXml="true" /></tt></li>
		</c:forEach>
	</ul>
</c:if>

<c:if test="${fn:length(importScheduleCommand.invalidWeekDayLines) != 0}">
	<p class="error">Please check following invalid lines,
	 the week days listed in each line are invalid:</p>
	<ul>
		<c:forEach var="invalidWeekDayLine" items="${importScheduleCommand.invalidWeekDayLines}">
			<li><tt><c:out value="${invalidWeekDayLine}" escapeXml="true" /></tt></li>
		</c:forEach>
	</ul>
</c:if>

<c:if test="${fn:length(importScheduleCommand.invalidTimeLines) != 0}">
	<p class="error">Please check following invalid lines,
	 the times listed in each line are invalid:</p>
	<ul>
		<c:forEach var="invalidTimeLine" items="${importScheduleCommand.invalidTimeLines}">
			<li><tt><c:out value="${invalidTimeLine}" escapeXml="true" /></tt></li>
		</c:forEach>
	</ul>
</c:if>

<%@ include file="/WEB-INF/views/include/footer.jspf"%>