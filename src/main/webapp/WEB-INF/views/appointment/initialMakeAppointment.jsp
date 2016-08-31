<?xml version="1.0" encoding="iso-8859-1"?>
<%@ include file="/WEB-INF/views/include/taglib.jspf" %>
<c:set var="headElements">
  <title>CESAR - Make An Appointment</title>
  <script type="text/javascript">
		/* <![CDATA[ */
		$(function() {
			$(".usertable tbody tr:odd").css("background-color","#F0F0F6");
			
			
			$('#appDate').datepicker({
		        inline: true,
		        beforeShowDay: $.datepicker.noWeekends,
		        minDate: new Date(),
		        maxDate: '+13'
		    });
		
		    
		});
		/* ]]> */
	</script>
</c:set>
<%@ include file="/WEB-INF/views/include/header.jspf" %>
	<h3>Make An Appointment</h3>
		<h4>
			<a href="<c:url value='/student/advisorSchedule/advisorsScheduleTable.html'/>">Advisors office hours</a> &gt;
			
       </h4>
      
      
       <form action="initialMakeAppointment.html" method="post">
       <p style="color:red">${beginError }</p>
	       <table class="usertable shadow" cellpadding="1.5em" cellspacing="0">
	       		<tr>
	       			<th>Date:</th>
	       			<td>
	       				<input type="text" style="width: 70px" id="appDate"  name="date" value="${date }"></input> 
	       			</td>
	       		</tr>
				
				<tr>
					<th style="text-align:center;" colspan="2"><input type="submit" name="save" value="Next" /></th>
				</tr>
			
		   </table>
		   <input type="hidden" name="isFirstTime" value=${isFirstTime } />
       </form>
       


<%@ include file="/WEB-INF/views/include/footer.jspf" %>