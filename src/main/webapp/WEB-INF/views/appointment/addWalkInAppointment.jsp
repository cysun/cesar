<?xml version="1.0" encoding="iso-8859-1"?>
<%@ include file="/WEB-INF/views/include/taglib.jspf" %>
<c:set var="headElements">
	<c:if test="${isUpdate == false }">
  		<title>CESAR - Add A Walk In Appointment</title>
  	</c:if>
  	<c:if test="${isUpdate == true }">
  		<title>CESAR - Update A Walk In Appointment</title>
  	</c:if>
  <script type="text/javascript">
		/* <![CDATA[ */
		$(function() {
			
			$( "#delButton" ).click( function(){
		    	var url = 'cancelAppointment.html?id=${as.id}&advisorView=${advisorView}';
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
		    
		    $( "#cancelButton1" ).click( function(){
 		    	var url = '/cesar/appointment/advisor/walkInAppointment.html';
 		    	window.location.href = url;
 		    	return false;
 		    });
		    $( "#cancelButton2" ).click( function(){
 		    	var url = '/cesar/appointment/student/viewAppointment.html?studentId=${as.student.id }';
 		    	window.location.href = url;
 		    	return false;
 		    });
		    $( "#cancelButton3" ).click( function(){
 		    	var url = '/cesar/appointment/advisor/viewAppointment.html';
 		    	window.location.href = url;
 		    	return false;
 		    });
		    
		});
		/* ]]> */
	</script>
</c:set>
<%@ include file="/WEB-INF/views/include/header.jspf" %>
	<h3>Appointment</h3>
		<h4>
		<c:if test="${not empty fromStaff }">
			<a href="<c:url value='/appointment/advisor/walkInAppointment.html'/>">View Available Appointments</a> &gt;
			 Add A Walk In Appointment
		</c:if>
		<c:if test="${ empty fromStaff}">
			<c:if test="${advisorView }">
				<a href="<c:url value='/appointment/student/viewAppointment.html?studentId=${as.student.id }'/>">View appointments</a>
			</c:if>
			<c:if test="${not advisorView }">
				<a href="<c:url value='/appointment/advisor/viewAppointment.html'/>">View appointments</a>
			</c:if>
		</c:if>
       </h4>
       <p class="error">${error}</p>
       <form:form modelAttribute="as" action="/cesar/appointment/advisor/addWalkInAppointment.html" method="post">
	      
	       
	       <table class="usertable shadow" cellpadding="1.5em" cellspacing="0">
			<tr>
				<th>Advisor</th>
				<td>${advisor.firstName} ${advisor.lastName }</td>
			</tr>
			
			<tr>
				<th>Start:<br /><span class="notice">(Format: MM/DD/YYYY HH:mm)</span></th>
				<td>
					<input id="startDate" type="text" name="startDate" value="<fmt:formatDate pattern="MM/dd/yyyy" value="${startTime}" />" />
					<input id="startTime" type="text" name="startTime1" value="<fmt:formatDate pattern="HH:mm" value="${startTime}" />" />
				
					<p class="error">${startError }</p>
				</td>
			</tr>
			
			<tr>
				<th>End:<br /><span class="notice">(Format: MM/DD/YYYY HH:mm)</span></th>
				<td>
					<input id="endDate" type="text" name="endDate" value="<fmt:formatDate pattern="MM/dd/yyyy" value="${endTime}" />" />
					<input id="endTime" type="text" name="endTime1" value="<fmt:formatDate pattern="HH:mm" value="${endTime}" />" />
					<p class="error">${endError }</p>
					
				</td>
			</tr>
	
			<c:if test="${isUpdate == true }" >
				<tr>
					<th>Student</th>
					<td>${as.student.firstName } ${as.student.lastName }</td>
				
				</tr>
			
			</c:if>
			
			<c:if test="${isUpdate == false }" >
				<tr>
					<th>Student(CIN):</th>
					<td>
					
					<input type="text" name="cin" value="${cin }" />
					
					<p class="error">${cinError }</p>
					</td>
				</tr>
			</c:if>
			
			<tr>
				<th>Reason For Appointment</th>
				<td>
					<form:select path="reasonForAppointment" items="${reasonsForAppointment}" itemValue="id" itemLabel="name" />

				</td>
			</tr>
			
			<c:if test="${isUpdate == true }" >
				<tr>
					<th>Completed:</th>
					<td>
						<form:checkbox path="isShowUp" />
					</td>
				</tr>
			</c:if>
			<tr>
				<th style="text-align:center;" colspan="2">
				<input type="submit" name="save" value="Save" />
				<c:if test="${isUpdate == true }" >
				<input id="delButton" type="button" value="Delete" />
				</c:if>
				
				<c:if test="${not empty fromStaff }">
					<input id="cancelButton1" type="button" value="Cancel" />
				</c:if>
				<c:if test="${ empty fromStaff}">
					<c:if test="${advisorView }">
						<input id="cancelButton2" type="button" value="Cancel" />
					</c:if>
					<c:if test="${not advisorView }">
						<input id="cancelButton3" type="button" value="Cancel" />
					</c:if>
				</c:if>
				 </th>
			</tr>
		   </table>
		   <input type="hidden" name="fromStaff" value="${fromStaff }" />
		   <input type="hidden" name="advisorId" value="${advisor.id }" />
		   <input type="hidden" name="asId" value="${as.id }" />
		   <input type="hidden" name="advisorView" value="${advisorView }" />
		   <c:if test="${isUpdate == true }" >
		   	<input type="hidden" name="cin" value="${as.student.cin }" />
		   </c:if>
       </form:form>

<%@ include file="/WEB-INF/views/include/footer.jspf" %>