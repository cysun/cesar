<?xml version="1.0" encoding="iso-8859-1"?>
<%@ include file="/WEB-INF/views/include/taglib.jspf"%>
<c:set var="headElements">
<c:if test="${addSection}">
	<title>Add A Section</title>
</c:if>
<c:if test="${not addSection}" >
	<title>Edit A Section</title>
</c:if>
	<script type="text/javascript">
	/* <![CDATA[ */
	          $(function() {
	             $(".usertable tbody tr:odd").css("background-color","#F0F0F6");
	           
	             $('.startTime').timepicker({
	                 hours: { starts: 7, ends: 20 },
	                 minutes: { interval: 5 },
	                 showPeriodLabel: true,
	                 showPeriod: true,
	                 showLeadingZero: true,
	                 minuteText: 'Min'
	             });
	 		    
	 		    $('.endTime').timepicker({
	                 hours: { starts: 8, ends: 22 },
	                 minutes: { interval: 5 },
	                 showPeriodLabel: true,
	                 showPeriod: true,
	                 showLeadingZero: true,
	                 minuteText: 'Min'
	             });
	 		    
	 		   $( "#deleteButton" ).click( function(){
	 		    	var url = 'deleteSection.html?id=${section.id}&majorId=${schedule.major.id}&scheduleId=${schedule.id}';
	 		    	window.location.href = url;
	 		    	return false;
	 		    });
	 		  $( "#cancelButton" ).click( function(){
	 		    	var url = '/cesar/schedule/displaySchedules.html';
	 		    	window.location.href = url;
	 		    	return false;
	 		    });
	 		 $( "#cancelButton1" ).click( function(){
	 		    	var url = '/cesar/schedule/section/addSection.html?_page=${_page}&_target=${_target}&fromCoursePlan=${fromCoursePlan}&quarterCode1=${quarterCode1}&cancelSection=true';
	 		    	window.location.href = url;
	 		    	return false;
	 		    });
	          });
	/* ]]> */
	</script>	             
</c:set>
<%@ include file="/WEB-INF/views/include/header.jspf"%>
<h3>
<c:if test="${empty fromCoursePlan}">
<a href="<c:url value='/schedule/displaySchedules.html' />">Schedules</a>
&gt;
</c:if>
<c:if test="${not empty fromCoursePlan }">
<a href="<c:url value='/schedule/section/addSection.html?_page=${_page}&_target=${_target}&fromCoursePlan=${fromCoursePlan}&quarterCode1=${quarterCode1}&cancelSection=true' />">Course Plan</a>
</c:if>
<c:if test="${addSection}">
<title>Add A Section</title> 
</c:if>
<c:if test="${not addSection}" >
	<title>Edit A Section</title>
</c:if>

</h3>

<c:if test="${empty fromCoursePlan }" >
	<c:if test="${addSection}">
		<h4>Department: ${major}</h4>
	</c:if>
	<c:if test="${not addSection}">
		<h4>Department: ${schedule.major.symbol}, Quarter:${schedule.quarter}</h4>
	</c:if>
</c:if>
<form:form modelAttribute="section">
  	<table class="usertable shadow" cellpadding="1.5em" cellspacing="0">
  		<c:if test="${not empty fromCoursePlan }">
  			<tr>
  				<th>*Major</th>
  				<td>
  					<select name="major1">
  						<c:forEach items="${allMajors}" var="major">
  							<option value="${major.symbol}">${major.symbol }</option>
  						</c:forEach>
  						
  					</select>
  				</td>
  			</tr>
  		</c:if>
  		<c:if test="${addSection}">
		  	<tr>
				<th>*Quarter</th>
				<td>
					<select name="quarterCode">
				 		<c:forEach items="${quarterList}" var="quarter">
				 			<c:if test="${quarter.code eq quarterCode1 }">
				 				<option value="${quarter.code }" SELECTED>${quarter }</option>
				 			</c:if>
				 			<c:if test="${quarter.code ne quarterCode1 }">
				 				<option value="${quarter.code }" >${quarter }</option>
				 			</c:if>
				 		</c:forEach> 
				 	</select>
		 		</td>
		 	</tr>
	 	</c:if>
	  	<tr>
	  		<th>*Course:</th>
	  		<td>
	  			<input type="text" name="courseCode" value="${section.course.code }" />
	  			<c:if test="${not empty courseError}">
			  			<br/>
			  			<p style="color:red">${courseError }</p>
		  		</c:if>
	  		</td>
		</tr>
		
		<tr>
			<th>Option</th>
			<td><form:input type="text" path="option" size="80" /></td>
		</tr>
		
		<tr>
			<th>*Start time</th>
			<td><form:input class="startTime" path="startTime" size="80"/>
				<c:if test="${not empty startTimeError}">
			  			<br/>
			  			<p style="color:red">${startTimeError }</p>
		  		</c:if>
			</td>
		</tr>
		
		<tr>
			<th>*End time</th>
			<td><form:input class="endTime" path="endTime" size="80"/>
				<c:if test="${not empty endTimeError}">
			  			<br/>
			  			<p style="color:red">${endTimeError }</p>
		  		</c:if>
			</td>
		</tr>
		
		<tr>
			<th>*Week Days:</th>
			<td>
				<c:if test="${not empty weekDayError }">
					<p style="color:red">${weekDayError }</p>
					<br />
				</c:if>
				<c:forEach items="${weekDays}" var="weekDay">
				  ${weekDay.name} <form:checkbox path="weekDays" value="${weekDay}" />
					<br />
				</c:forEach>
			</td>
		</tr>
		
		<tr>
	  		<th>*Units:</th>
	  		<td><form:input path="units" size="5" /></td>
	  	</tr>
	  	
	  	<tr>
	  		<th>Section #:</th>
	  		<td><form:input path="sectionNumber" size="80" /></td>
	  	</tr>
	  	
	  	<tr>
	  		<th>Call #:</th>
	  		<td><form:input path="callNumber" size="80" /></td>
	  	</tr>
	  	
	  	<tr>
	  		<th>Location</th>
	  		<td><form:input path="location" size="80" /></td>
	  	</tr>
	  	
	  	<tr>
	  		<th>Capacity</th>
	  		<td><form:input path="capacity" size="80" /></td>
	  	</tr>
	  	
	  	<tr>
	  		<th>Info:</th>
	  		<td><form:input path="info" size="80" /></td>
	  	</tr>
	  	
	  
  		<tr>
			<th style="text-align:center;" colspan="2">
			<input type="submit" name="save" value="Save" />
			<c:if test="${ not addSection }">
				<input id="deleteButton" type="button" value="Delete" />
			</c:if>
			<c:if test="${empty fromCoursePlan }" >
				<input id="cancelButton" type="button" value="Cancel" />
			</c:if>
			<c:if test="${not empty fromCoursePlan }" >
				<input id="cancelButton1" type="button" value="Cancel" />
			</c:if>
			</th>
		</tr>
	</table>
	<input type="hidden" name="scheduleId" value="${schedule.id }" />
	
	
	<form:hidden path="id" value="${section.id }" />
	
</form:form>

<%@ include file="/WEB-INF/views/include/footer.jspf"%>