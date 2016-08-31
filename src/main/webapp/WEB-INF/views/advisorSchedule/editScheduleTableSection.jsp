<?xml version="1.0" encoding="iso-8859-1"?>
<%@ include file="/WEB-INF/views/include/taglib.jspf" %>
<c:set var="headElements">
  <title>CESAR - Edit Appointment</title>
  <script type="text/javascript">
		/* <![CDATA[ */
		$(function() {
		    $( "#cancelButton" ).click( function(){
		    	var url = 'advisorsScheduleTable.html';
		    	window.location.href = url;
		    	return false;
		    });
		    
		});
		/* ]]> */
	</script>
</c:set>
<%@ include file="/WEB-INF/views/include/header.jspf" %>
	<h3>Edit Schedule Table Section</h3>
		<h4>
			<a href="<c:url value='/user/advisorSchedule/advisorsScheduleTable.html'/>">View Schedule Table</a> &gt;
			<c:if test="${isAdd }">
				Add schedule
			</c:if> 
			<c:if test="${not isAdd }" >
				Delete schedule 
			</c:if>
       </h4>
      
      <c:if test="${isAdd }">
       <form action="addScheduleTableSection.html" method="post">
       </c:if> 
       <c:if test="${not isAdd }" >
       <form action="deleteScheduleTableSection.html" method="post">
       </c:if>
	       <input type="hidden" name="id"  value="${section.id}"/>
	      
	       <table class="usertable shadow" cellpadding="1.5em" cellspacing="0">
			
			<tr>
				<th>Advisor:</th>
				<td>
					${user.firstName } ${user.lastName }
				</td>
			</tr>
			
			<tr>
				<th>WeekDay</th>
				<td>
					${weekDay}
				</td>
			</tr>
			
			<tr>
				<th>Section:</th>
				<td>
					${startHour } - ${endHour }
				</td>
			</tr>


			<tr>
				<c:if test="${ isAdd }" >
					<th style="text-align:center;" colspan="2"><input type="submit" name="save" value="Save" />
				</c:if>
				<c:if test="${not isAdd }" >
					<th style="text-align:center;" colspan="2"><input type="submit" name="delete" value="Delete" />
				</c:if>
				<input id="cancelButton" type="button" value="Cancel" /> </th>
			</tr>
		   </table>
       </form>

<%@ include file="/WEB-INF/views/include/footer.jspf" %>