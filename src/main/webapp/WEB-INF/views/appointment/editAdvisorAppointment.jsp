<?xml version="1.0" encoding="iso-8859-1"?>
<%@ include file="/WEB-INF/views/include/taglib.jspf" %>
<c:set var="headElements">
  <title>CESAR - Edit An Appointment</title>
  <script type="text/javascript">
		/* <![CDATA[ */
		$(function() {
		    $( "#deleteButton" ).click( function(){
		    	var url = 'cancelAppointment.html?id=${as.id}&advisorView=${advisorView}';
		    	window.location.href = url;
		    	return false;
		    });
		    $( "#cancelButton1" ).click( function(){
		    	var url = '/cesar/appointment/student/viewAppointment.html?studentId=${as.student.id }';
		    	window.location.href = url;
		    	return false;
		    });
		    $( "#cancelButton2" ).click( function(){
		    	var url = '/cesar/appointment/student/viewAppointment.html';
		    	window.location.href = url;
		    	return false;
		    });
		    $('#startDate').datepicker({
		        beforeShowDay: $.datepicker.noWeekends,
		        inline: true,
		        changeMonth: true,
		        changeYear: true,
		        yearRange: '-01:+01'
		    });
		    
		    $('#endDate').datepicker({
		        beforeShowDay: $.datepicker.noWeekends,
		        inline: true,
		        changeMonth: true,
		        changeYear: true,
		        yearRange: '-01:+01'
		    });
		    
		    $('#startTime').timepicker({
                hours: { starts: 9, ends: 20 },
                minutes: { interval: 1 },
                showPeriodLabels: false,
                minuteText: 'Min'
            });
		    
		    $('#endTime').timepicker({
                hours: { starts: 9, ends: 20 },
                minutes: { interval: 1 },
                showPeriodLabels: false,
                minuteText: 'Min'
            });
		    
		});
		/* ]]> */
	</script>
</c:set>
<%@ include file="/WEB-INF/views/include/header.jspf" %>
	<h3>Appointment</h3>
		<h4>
			<c:if test="${advisorView }">
				<a href="<c:url value='/appointment/student/viewAppointment.html?studentId=${as.student.id }'/>">View appointments</a> &gt;
			 Edit An Appointment
			</c:if>
			<c:if test="${not advisorView }">
				<a href="<c:url value='/appointment/advisor/viewAppointment.html'/>">View appointments</a> &gt;
			 Edit An Appointment
			 </c:if>
       </h4>
       <p class="error">${error}</p>
       <form:form modelAttribute="as" action="/cesar/appointment/advisor/updateAppSchedule.html" method="post">
	       <input type="hidden" name="id"  value="${as.id}"/>
	       <input type="hidden" name="advisorView" value="${advisorView }" />
	       <table class="usertable shadow" cellpadding="1.5em" cellspacing="0">
			
			
			<tr>
				<th>Start:<br /><span class="notice">(Format: MM/DD/YYYY HH:mm)</span></th>
				<td>
					<input id="startDate" type="text" name="startDate1" value="<fmt:formatDate pattern="MM/dd/yyyy" value="${as.startTime}" />" />
					<input id="startTime" type="text" name="startTime1" value="<fmt:formatDate pattern="HH:mm" value="${as.startTime}" />" />
				
					<p class="error">${startError }</p>
				</td>
			</tr>
			
			<tr>
				<th>End:<br /><span class="notice">(Format: MM/DD/YYYY HH:mm)</span></th>
				<td>
					<input id="endDate" type="text" name="endDate1" value="<fmt:formatDate pattern="MM/dd/yyyy" value="${as.endTime}" />" />
					<input id="endTime" type="text" name="endTime1" value="<fmt:formatDate pattern="HH:mm" value="${as.endTime}" />" />
					<p class="error">${endError }</p>
					
				</td>
			</tr>
	
			<tr>
				<th>Student:</th>
				<td>
				
				<a href="<c:url value='/user/display.html?userId=${as.student.id }'/>">${as.student.firstName}
				${student.middleName} ${as.student.lastName}</a> <br />
				
				</td>
			</tr>
			
			<tr>
				<th>Advisor:</th>
				<td>
					 ${as.advisor.firstName} ${as.advisor.lastName }
				</td>
			</tr>
			
			<tr>
				<th>Reason For Appointment</th>
				<td>
					<form:select path="reasonForAppointment" items="${reasonsForAppointment}" itemValue="id" itemLabel="name" />

				</td>
			</tr>	
			
			<tr>
				<th>Appointment Type</th>
				<td>
					<form:select path="appointmentType" items="${appointmentTypes}" itemValue="id" itemLabel="name" />

				</td>
			</tr>	
			
			<tr>
				<th>Notes:</th>
				<td>
					<form:textarea path="notes" items="${as.notes}" />
					
				</td>
			</tr>
			
			<tr>
				<th>Completed:</th>
				<td>
					<form:checkbox path="isShowUp" />
				</td>
			</tr>
		
			<tr>
				<th style="text-align:center;" colspan="2"><input type="submit" name="save" value="Save" />
				<input id="deleteButton" type="button" value="Delete" />
				<c:if test="${advisorView }">
					<input id="cancelButton1" type="button" value="Cancel" />
				</c:if> 
				<c:if test="${not advisorView }">
					<input id="cancelButton2" type="button" value="Cancel" />
				</c:if>
				</th>
			</tr>
		   </table>
		   <form:hidden path="startTime" />
		   <form:hidden path="endTime" />
		   <form:hidden path="student" />
		   <form:hidden path="advisor" />
       </form:form>

<%@ include file="/WEB-INF/views/include/footer.jspf" %>