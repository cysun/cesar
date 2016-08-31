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
		    	var url = 'deleteAppointment.html?id=${as.id}';
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
		    $( "#cancelButton" ).click( function(){
 		    	var url = '/cesar/appointment/student/viewAppointment.html';
 		    	window.location.href = url;
 		    	return false;
 		    });
		    
		});
		/* ]]> */
	</script>
</c:set>
<%@ include file="/WEB-INF/views/include/header.jspf" %>
	<h3>Walk In Appointment</h3>
		<h4>
		
			<a href="<c:url value='/appointment/student/viewAppointment.html'/>">View appointments</a>
	
       </h4>

    <form action = "cancelAppointment.html" method="post" >
       <table class="usertable shadow" cellpadding="1.5em" cellspacing="0">
       <tr>
			<th>Advisor:</th>
			<td>
				 ${as.advisor.firstName} ${as.advisor.lastName }
			</td>
		</tr>
		<tr>
			<th>Start:</th>
			<td>
				<fmt:formatDate pattern="MM/dd/yyyy HH:mm" value="${as.startTime}" />
			</td>
		</tr>
		
		<tr>
			<th>End:</th>
			<td>
				<fmt:formatDate pattern="MM/dd/yyyy HH:mm" value="${as.endTime}" />
			
			</td>
		</tr>
		
		<tr>
			<th>Reason For Appointment</th>
			<td>
				${as.reasonForAppointment.name }
			</td>
		</tr>
		
		<tr>
			<th style="text-align:center;" colspan="2">
				<input type="submit" name="delete" value="Delete" />
				<input id="cancelButton" type="button" value="Cancel" />
			</th>	
		</tr>
	   </table>
	   <input type="hidden" name="id" value="${as.id }" />
	</form>

<%@ include file="/WEB-INF/views/include/footer.jspf" %>