<?xml version="1.0" encoding="iso-8859-1"?>
<%@ include file="/WEB-INF/views/include/taglib.jspf" %>
<c:set var="headElements">
  <title>CESAR - Edit An Appointment</title>
  <script type="text/javascript">
		/* <![CDATA[ */
		$(function() {
		    $( "#cancelButton" ).click( function(){
		    	var url = '/cesar/appointment/advisor/initialMakeAppointment.html';
		    	window.location.href = url;
		    	return false;
		    });
		    $( "#cancelButtonStudent" ).click( function(){
		    	var url = '/cesar/appointment/student/viewAppointment.html';
		    	window.location.href = url;
		    	return false;
		    });
		    
		});
		
		function cancelAppointment( year, month, day )
		{
			var startDate = new Date();
	    	startDate.setFullYear(year, month-1, day);
	    	startDate.setHours(0,0,0);
	    	var currentDate = new Date();
	    	currentDate.setHours(0,0,0);
	    	
	    	if (startDate > currentDate) {
	    	  var comfirmQuestion=confirm("Are you sure you want to cancel your appointment?");
	    	  if (comfirmQuestion==true) {
	    		var url =  "<c:url value='cancelAppointment.html?id=${as.id}' />";
			   	window.location.href = url;
			    return false;
	    	  }
	    	} else {
	    	  alert("Note: At this time you are no longer able to CANCEL your appointment. You must call the ECST Advisement and Recruitment Center at 323-343-4574 to reschedule.");
	    	}
		}

		/* ]]> */
	</script>
</c:set>
<%@ include file="/WEB-INF/views/include/header.jspf" %>
	<h3>Appointment</h3>
		<h4>
			<c:if test="${isAdvisor }">
				<a href="<c:url value='/appointment/advisor/initialMakeAppointment.html'/>">Search A Student For Appointments</a> &gt;
				<a href="<c:url value='/appointment/makeAppointment.html?studentId=${as.student.id}'/>">Make An Appointment</a>&gt;
				Confirm An Appointment
			</c:if>
			<c:if test="${not isAdvisor }">
				<a href="<c:url value='/appointment/student/viewAppointment.html'/>">View appointments</a> &gt;
			</c:if>
			 <c:if test="${isOldOne == false }" >
			 	Edit An Appointment
			 </c:if>
			 <c:if test="${isOldOne == true }" >
			 	View Appointments
			 </c:if>
       </h4>
       
       <form:form modelAttribute="as" action="/cesar/appointment/student/editAppointment.html" method="post">
	       <input type="hidden" name="isAdvisor" value="${isAdvisor }" />
	       <form:hidden path="id" />
	       <form:hidden path="startTime" />
	       <form:hidden path="endTime" />
	       <form:hidden path="startTimeInt" />
	       <form:hidden path="advisor" />
	       <form:hidden path="student" />
	       <form:hidden path="isWalkInAppointment" />
	       <form:hidden path="isShowUp" />
	       <form:hidden path="isAvailable" />
	       <table class="usertable shadow" cellpadding="1.5em" cellspacing="0">
			
			<tr>
				<th>Start:</th>
				<td>
					<fmt:formatDate pattern="MM/dd/yyyy HH:mm:ss" value="${as.startTime}" />
				</td>
			</tr>
			
			<tr>
				<th>End:</th>
				<td>
					<fmt:formatDate pattern="MM/dd/yyyy HH:mm:ss" value="${as.endTime}" />
				
				</td>
			</tr>
			
			<tr>
				<th>Reason For Appointment</th>
				<td>
					<form:select path="reasonForAppointment" items="${reasonsForAppointment}" itemValue="id" itemLabel="name" />

				</td>
			</tr>
			
			<c:if test="${isAdvisor }">
			
				<tr>
					<th>Appointment Type</th>
					<td>
						<form:select path="appointmentType" items="${appointmentTypes}" itemValue="id" itemLabel="name" />
	
					</td>
				</tr>
			</c:if>
			
			<tr>
				<th>Advisor:</th>
				<td>
					 ${as.advisor.firstName} ${as.advisor.lastName }
				</td>
			</tr>
			
			<tr>
				<th>Student:</th>
				<td>
					${as.student.firstName } ${as.student.lastName }
				</td>
			</tr>
			
			<tr>
				<th>Notes:</th>
				<td>
					<form:textarea path="notes" items="${as.notes}" />
					
				</td>
			</tr>	
			
			<c:if test="${isOldOne == false }" >
				<tr>
					<th style="text-align:center;" colspan="2">
						<input type="submit" name="save" value="Save" />
						<input id="deleteButton" type="button" value="Cancel Appointment" onclick="cancelAppointment(${year}, ${month}, ${day});" />
						<input id="cancelButtonStudent" type="button" value="Go Back" />
						
					</th>
					
				</tr>
			</c:if>
			
			<c:if test="${isOldOne == true }" >
				<tr>
					<th style="text-align:center;" colspan="2">
						<input type="submit" name="save" value="Save" />
					</th>
				</tr>
			</c:if>
			
			<c:if test="${saveApp == true }" >
				<tr>
					<th style="text-align:center;" colspan="2">
						<input type="submit" name="save" value="Save" />
						<c:if test="${isAdvisor }">
							<input id="cancelButton" type="button" value="Cancel" />
						</c:if>
						<c:if test="${not isAdvisor }">
							<input id="cancelButtonStudent" type="button" value="Cancel" />
						</c:if>
					</th>
				</tr>
			</c:if>
		   </table>
       </form:form>

<%@ include file="/WEB-INF/views/include/footer.jspf" %>