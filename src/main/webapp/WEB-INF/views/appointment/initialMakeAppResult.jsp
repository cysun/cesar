<?xml version="1.0" encoding="iso-8859-1"?>
<%@ include file="/WEB-INF/views/include/taglib.jspf" %>
<c:set var="headElements">
  <title>CESAR - Edit An Appointment</title>
  <script type="text/javascript">
		/* <![CDATA[ */
		$(function() {
		
			$("table").tablesorter({
					// sort on the second column
					widgets: ['zebra'],
					headers: { 
			            //(we start counting zero) 
			            0: { 
			                // disable it by setting the property sorter to false 
			                sorter: false 
			            },
			            // assign the third column (we start counting zero) 
			            1: { 
			                // disable it by setting the property sorter to false 
			                sorter: false 
			            },
			           
			            2: { 
			                // disable it by setting the property sorter to false 
			                sorter: false 
			            } 
			        } 
				});
		});
		/* ]]> */
	</script>
</c:set>
<%@ include file="/WEB-INF/views/include/header.jspf" %>
	<h3>Make An Appointment</h3>
	<h4>
		<c:if test="${isFirstTime == false }">
			<c:if test="${empty studentId }" >
				<a href="<c:url value='/appointment/student/viewAppointment.html'/>">View appointments</a> &gt;
			</c:if>
			<c:if test="${not empty studentId }" >
				<a href="<c:url value='/appointment/advisor/initialMakeAppointment.html'/>">Search A Student For Appointments</a> &gt;
			</c:if>
			Make An Appointment
		</c:if>
		<c:if test="${isFirstTime == true }">
			<a href="<c:url value='/student/advisorSchedule/advisorsScheduleTable.html'/>">Advisors office hours</a> &gt;
			Available advisor schedule(s)
		</c:if>
		
	</h4>
	<c:if test="${empty appointmentSections}">
		
		No advisor available at selected date and time, please <a href="<c:url value='/appointment/makeAppointment.html' />">click here</a> to try again later.
		
	</c:if>
	<c:if test="${isChangeAdvisor==true }">
		${advisor.firstName } is not available in 14 days, here is the recommended advisor: ${newAdvisor.firstName}
	</c:if>
	
	<c:if test="${not empty appointmentSections}">
       <table class="tablesorter" cellpadding="1.5em" cellspacing="0">
       <thead>
       	<tr>
       		<th> Advisors </th><th> Appointment time</th> <th></th>
       	</tr>
       	</thead>
       	<tbody>
       		<c:forEach items="${appointmentSections}" var="section">
       		
			  <tr>
			    <td>
			    	${section.advisor.firstName } ${section.advisor.lastName }
			    </td>
			    <td> 
			    	<fmt:formatDate pattern="MM/dd/yyyy HH:mm:ss" value="${section.startTime}" /> - 
			    	<fmt:formatDate pattern="MM/dd/yyyy HH:mm:ss" value="${section.endTime}" />
			    </td>
				<td>
				
				  <form action="/cesar/appointment/initialMakeAppointmentSubmit.html" method="post" >
					  <input type="hidden" name="advisorId" value="${section.advisor.id }" />
					  <input type="hidden" name="startTimeInt" value="${section.startTimeInt }" />
					  <input type="hidden" name="isWalkIn" value="${isWalkIn }" />
					  <input type="hidden" name="isChangeAdvisor" value="${isChangeAdvisor }" />
					  <input type="hidden" name="startTime" value="<fmt:formatDate pattern="MM/dd/yyyy HH:mm:ss" value="${section.startTime}" />" />
					  <input type="hidden" name="endTime" value="<fmt:formatDate pattern="MM/dd/yyyy HH:mm:ss" value="${section.endTime}" />" />
					  <input type="hidden" name="studentId" value="${studentId }" />
					  <input type="hidden" name="id" value="${id }" />
					  <input type="submit" name="submit" value="Next" />
				  </form>
				</td>
			  </tr>
			 </c:forEach>
			</tbody>
	   </table>
      </c:if>


<%@ include file="/WEB-INF/views/include/footer.jspf" %>