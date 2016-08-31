<?xml version="1.0" encoding="iso-8859-1"?>
<%@ include file="/WEB-INF/views/include/taglib.jspf"%>
<c:set var="headElements">
	<title>Import schedule</title>
</c:set>
<%@ include file="/WEB-INF/views/include/header.jspf"%>
<h3>
Schedules</h3>


<h4>
	<a href="<c:url value='/schedule/displaySchedules.html' />">Display A Schedule</a> | Import A Schedule 
</h4>
<p>Please choose the department and the quarter, then copy and paste courses schedule from XML spreadsheet to
the text area below. The XML spreadsheet should be in the following format:<br />
(Monday= M, TuesDay = T, WednesDay = W, Thursday = R/TH, Friday = F, Saturday = S, Sunday = U ):</p>
<table class="tablesorter">
	<tr>
		<th>Course code(-option)</th><th>Section #</th><th>Call #</th><th>Units</th><th>Days</th><th>Times</th><th>Location</th><th>Capacity</th>
	</tr>
	
	<tr>
		<td>Engr154 - Math206</td><td>1</td><td>1234</td><td>1</td><td>MW</td><td>10:00 AM - 12:00 PM</td><td>SH111</td><td>30</td>
	</tr>
	
</table>
<form:form method="post" modelAttribute="importScheduleCommand">
	Department:<form:select path="major" items="${majors}" itemValue="id" itemLabel="symbol" />
	
	Quarter:
	<form:select path="quarter" >
		<c:forEach var="quarter" items="${quarters }">
			<form:option value="${quarter}">
				${quarter}
			</form:option>
		</c:forEach>
	</form:select>
	<br />
	
	<form:textarea path="text" rows="20" cols="80" cssStyle="width: 95%;" />
	<input type="hidden" name="_page" value="0" />
	<p>
	<input type="submit" name="_target1" value="Next" class="subbutton" />
	</p>
</form:form>

<%@ include file="/WEB-INF/views/include/footer.jspf"%>