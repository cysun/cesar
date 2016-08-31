<?xml version="1.0" encoding="iso-8859-1"?>
<%@ include file="/WEB-INF/views/include/taglib.jspf"%>
<c:set var="headElements">
	<title>Display Available Walk In Appointments</title>
	<script type="text/javascript">
	/* <![CDATA[ */
	$(function() {	    
		$( "#accordion" ).accordion();
	});
	/* ]]> */
	</script>
</c:set>
<%@ include file="/WEB-INF/views/include/header.jspf"%>
<h3>Walk In Appointments</h3>
<p></p>
<c:if test="${empty appointmentSections }" >
  	No walk in appointment available.
</c:if>

<div id="accordion">
	<c:forEach items="${appointmentSections}" var="entry">
  		 <h3><a href="#">${entry.key }</a></h3>
  		 <div>
  		 	<c:if test="${empty entry.value}">
		
				No walk in appointment available today!
		
			</c:if>
			<c:if test="${not empty entry.value }">
			<table class="tablesorter" cellpadding="1.5em" cellspacing="0">
			       <thead>
			       	<tr>
			       		<th> Advisors </th><th> Appointment time</th> <th></th>
			       	</tr>
			       	</thead>
			       	<tbody>
	    				<c:forEach items="${entry.value}" var="item" >
						  <tr>
						    <td>
						    	${item.advisor.firstName } ${item.advisor.lastName }
						    </td>
						    <td> 
						    	<fmt:formatDate pattern="MM/dd/yyyy HH:mm:ss" value="${item.startTime}" /> --- 
						    	<fmt:formatDate pattern="MM/dd/yyyy HH:mm:ss" value="${item.endTime}" />
						    </td>
							<td>
							
							  <form action="/cesar/appointment/advisor/walkInAppointmentConfirm.html" method="post" >
								  <input type="hidden" name="advisorId" value="${item.advisor.id }" />
								  <input type="hidden" name="startTimeInt" value="${item.startTimeInt }" />
								  <input type="hidden" name="fromStaff" value="true" />
								  <input type="hidden" name="isChangeAdvisor" value="${isChangeAdvisor }" />
								  <input type="hidden" name="startTime" value="<fmt:formatDate pattern="MM/dd/yyyy HH:mm:ss" value="${item.startTime}" />" />
								  <input type="hidden" name="endTime" value="<fmt:formatDate pattern="MM/dd/yyyy HH:mm:ss" value="${item.endTime}" />" />
								  <input type="submit" name="submit" value="Next" />
							  </form>
							</td>
						  </tr>
						 </c:forEach>

    				</tbody>
				   </table>
			      </c:if>
			      </div>
		</c:forEach>
		
	
</div>

<%@ include file="/WEB-INF/views/include/footer.jspf"%>