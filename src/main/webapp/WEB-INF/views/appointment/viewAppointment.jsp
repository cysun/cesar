<?xml version="1.0" encoding="iso-8859-1"?>
<%@ include file="/WEB-INF/views/include/taglib.jspf"%>
<c:set var="headElements">
	<title>Display Appointments</title>
	<script type="text/javascript">
	/* <![CDATA[ */
	$(function() {
		$( "#accordion" ).accordion({ autoHeight: false });
	    $(".usertable tbody tr:odd").css("background-color","#F0F0F6");	
	});
	/* ]]> */
	</script>
</c:set>
<%@ include file="/WEB-INF/views/include/header.jspf"%>
<h3> 
<c:if test="${advisorView}" >
	 <a href="<c:url value='/appointment/advisor/initialMakeAppointment.html'/>">Search A Student For Appointments</a> &gt;
</c:if>
View Scheduled Appointments  </h3>
	
	<div id="accordion">
	<c:forEach items="${appointmentSections}" var="entry">
	<h3><a href="#">${entry.key }</a></h3>
	<div>
	
	<table class="tablesorter shadow" cellspacing="2" cellpadding="5" style="width: 100%;">
		<c:if test="${isAdvisor == false }">
			<c:if test="${ empty entry.value }">
				<p> No appointment in the date range</p>
			</c:if>
			<c:if test="${ not empty entry.value }">
			<thead>
				<tr>
					<th >Name</th>
					<th >Start Time</th>
					<th >End Time</th>
					<th >Student</th>
					<th >CIN</th>
					<th >Term Admitted</th>
					<th >Advisor</th>
					<th></th>
					 
				</tr>
			</thead>
			</c:if>
		</c:if>
		
		<c:if test="${isAdvisor == true }">
			<c:if test="${empty entry.value }">
				<p> No appointment in the date range</p>
			</c:if>
			
			<c:if test="${not empty entry.value }">
			<thead>
				<tr>
					<th >Name</th>
					<th >Start Time</th>
					<th >End Time</th>
					<th >Student</th>
					<th >CIN</th>
					<th >Term Admitted</th>
					<th >Advisor</th>
					<th></th>
				</tr>
			</thead>
			</c:if>
		</c:if>
		
			<tbody>
			<c:if test="${isAdvisor == true }">
				<c:if test="${entry.key == 'Past Appointments' }">
				<c:forEach items="${entry.value}" var="section" >
					<tr>
						<td style="background-color: #F0F0F6;">${section.title }</td>
						<td style="background-color: #F0F0F6;"><fmt:formatDate pattern="MM/dd/yyyy HH:mm" value="${section.startTime}" /></td>
						<td style="background-color: #F0F0F6;"><fmt:formatDate pattern="MM/dd/yyyy HH:mm" value="${section.endTime}" /></td>
						<td style="background-color: #F0F0F6;">${section.student.firstName } ${section.student.lastName }</td>
						<td style="background-color: #F0F0F6;">${section.student.cin }</td>
						<td style="background-color: #F0F0F6;">${section.student.quarterAdmitted }</td>
						<td style="background-color: #F0F0F6;">${section.advisor.firstName } ${section.advisor.lastName }</td>
						<td style="background-color: #F0F0F6;">
							<c:if test="${section.isWalkInAppointment }" >
								<a href="updateWalkInAppSchedule.html?id=${section.id }">Edit</a>
							</c:if>
							<c:if test="${not section.isWalkInAppointment }" >
								<a href="updateAppSchedule.html?id=${section.id }">Edit</a>
							</c:if>
						</td>
					</tr>
				</c:forEach>
				</c:if>
			</c:if>
			
			<c:if test="${entry.key == 'Future Appointments' }">
			<c:forEach items="${entry.value}" var="section" >
				<tr class="cuurentColor">
					<td style="background-color: #DDECF7;">${section.title }</td>
					<td style="background-color: #DDECF7;"><fmt:formatDate pattern="MM/dd/yyyy HH:mm" value="${section.startTime}" /></td>
					<td style="background-color: #DDECF7;"><fmt:formatDate pattern="MM/dd/yyyy HH:mm" value="${section.endTime}" /></td>
					<td style="background-color: #DDECF7;"> ${section.student.firstName } ${section.student.lastName }</td>
					<td style="background-color: #DDECF7;">${section.student.cin }</td>
					<td style="background-color: #DDECF7;">${section.student.quarterAdmitted }</td>
					<td style="background-color: #DDECF7;">${section.advisor.firstName } ${section.advisor.lastName }</td>
					<td style="background-color: #F0F0F6;">
							<c:if test="${section.isWalkInAppointment }" >
								<c:if test="${advisorView}" >
									 <a href="<c:url value='/appointment/advisor/updateWalkInAppSchedule.html?id=${section.id }&advisorView=${advisorView }'/>">Edit</a>
								</c:if>
								<c:if test="${not advisorView }" >
									<a href="updateWalkInAppSchedule.html?id=${section.id }">Edit</a>
								</c:if>
								
							</c:if>
							<c:if test="${not section.isWalkInAppointment }" >
								<c:if test="${advisorView}" >
									 <a href="<c:url value='/appointment/advisor/updateAppSchedule.html?id=${section.id }&advisorView=${advisorView }' />">Edit</a>
								</c:if>
								<c:if test="${not advisorView }" >
									<a href="updateAppSchedule.html?id=${section.id }">Edit</a>
								</c:if>
							</c:if>
					</td>
				</tr>
				
			</c:forEach>
			</c:if>		
			
		</tbody>
	</table>
	</div>
	</c:forEach>
	</div>
	


<%@ include file="/WEB-INF/views/include/footer.jspf"%>