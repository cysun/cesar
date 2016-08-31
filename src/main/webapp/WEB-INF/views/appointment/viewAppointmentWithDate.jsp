<?xml version="1.0" encoding="iso-8859-1"?>
<%@ include file="/WEB-INF/views/include/taglib.jspf"%>
<c:set var="headElements">
	<title>Display appointments with a specific date</title>
	<script type="text/javascript">
	/* <![CDATA[ */
	$(function() {
	    $('#selectedDate').datepicker({
	        inline: true,
	        changeMonth: true,
	        changeYear: true,
	        yearRange: '-01:+01'
	    });
	    $( "#accordion" ).accordion();
	    $(".usertable tbody tr:odd").css("background-color","#F0F0F6");
	});
	
	function cancelAppointment( id )
	{
    	var url =  "<c:url value='cancelAppointment.html?id=' />"+id;
		window.location.href = url;
		return false;
	}
	
	function reschedule( id, studentId )
	{
		var url =  "<c:url value='/student/advisorSchedule/advisorsScheduleTable.html?id=' />"+id+"&studentId="+studentId;
    	window.location.href = url;
    	return false;
	}

	/* ]]> */
	</script>
</c:set>
<%@ include file="/WEB-INF/views/include/header.jspf"%>
<h3> View Scheduled Appointments With A Date </h3>
	
		<form action ="/cesar/appointment/staff/viewAppointmentWithSpecificDate.html"  method="post" >
		Date : <input type="text" id="selectedDate" name="date" />
		<input type="submit" value="Submit"/>
		</form>
		<br />
		<c:if test="${empty appointmentSections }" >
			No appointment is available.
		</c:if>
		<div id="accordion">
		<c:forEach items="${appointmentSections}" var="entry">
	  		 <h3><a href="#">${entry.key }</a></h3>
	  		 <div>
	
				<table class="tablesorter" cellpadding="1.5em" cellspacing="0" width="100%">
				       <thead>
				       	<tr>
				       		<th> Title </th><th> Appointment time </th><th> Student </th><th> CIN </th><th> Major </th><th>   </th>
				       	</tr>
				       	</thead>
				       	<tbody>
		    				<c:forEach items="${entry.value}" var="item" >
							  <tr>
							    <td width="12%">
							    	${item.title }
							    </td>
							    <td width="29%"> 
							    	<fmt:formatDate pattern="MM/dd/yyyy HH:mm:ss" value="${item.startTime}" /> --- 
							    	<fmt:formatDate pattern="MM/dd/yyyy HH:mm:ss" value="${item.endTime}" />
							    </td>
								<td width="17%">
									${item.student.firstName } ${item.student.lastName }
								</td>
								<td width="10%">
									${item.student.cin }
								</td>
								<td width="6%">
									${item.student.major.symbol }
								</td>
								<td align="right" width="26%">
									<input id="deleteButton" type="button" value="Cancel Appointment" onclick="cancelAppointment(${item.id});" />
									<input id="rescheduleButton" type="button" value="Reschedule" onclick="reschedule(${item.id }, ${item.student.id });" />
								</td>
							  </tr>
							</c:forEach>
	
	    				</tbody>
				</table>
				      
			 </div>
		</c:forEach>
		</div>


<%@ include file="/WEB-INF/views/include/footer.jspf"%>