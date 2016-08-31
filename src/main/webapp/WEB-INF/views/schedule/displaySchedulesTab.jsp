<?xml version="1.0" encoding="iso-8859-1"?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ include file="/WEB-INF/views/include/taglib.jspf"%>

<div id="tab-${major}">

<c:if test="${staffOnly }">
	<p><a href="<c:url value='/schedule/section/addSection.html?major=${major }'/>">Add A Section</a></p>
</c:if>
	<c:if test="${fn:length(schedules) == 0}">
	  <p>No schedule information on record.</p>
	</c:if>
	
	<c:if test="${fn:length(schedules) > 0}">
	
		<form action="displaySchedules.html" method="post">
		 	<select name="quarter">
		 		<c:forEach items="${quarters}" var="quarter">
		 			<option value="${quarter.code }">${quarter }</option>
		 		</c:forEach> 
		 	</select>
		 	<input type="hidden" name="major" value="${major}" />
		 	<input type="submit" value="Ok"/>
		</form>
		<p>Term: ${schedule.quarter} </p> 
	
		<table class="tablesorter shadow" cellspacing="2" cellpadding="5" style="width:100%;">
		<thead>
		  <tr><th>Course</th><th>Section #</th><th>Call #</th><th>Unit</th><th>Days</th><th>Times</th><th>Location</th><th>Capacity</th><th>Info</th>
		  <c:if test="${staffOnly }">
		  	<th></th>
		  </c:if></tr>
		</thead>
		<tbody>
		
		  <c:forEach items="${schedule.sections}" var="section">
		  <c:if test="${not section.course.deleted }">
			  <tr style="background-color: #F0F0F6; font-size: 14px;">
			    <td>${section.course.code}
			    <c:if test="${not empty section.option}">
			   	 	-${section.option}
			    </c:if>
			    </td>
			    <td>${section.sectionNumber }</td>
			    <td>${section.callNumber }</td>
			    <td>${section.units }</td>
			    <td>
			    	<c:forEach items="${section.weekDays}" var="weekDay">
			 			${weekDay.symbol } 
			 		</c:forEach> 
			    </td>
			    <td>${section.startTime }-${section.endTime}</td>
			    <td>${section.location }</td>
			   	<td>${section.capacity }</td>
			    <td>${section.info}</td>
			    <c:if test="${staffOnly }">
					<td>
					  <a href="<c:url value='/schedule/section/editSection.html?sectionId=${section.id}&scheduleId=${schedule.id }' />">Edit</a>
					</td>
				</c:if>
			  </tr>
		  </c:if>
		  </c:forEach>
		</tbody>
		</table>
	</c:if>

</div>

<script type="text/javascript">
	/* <![CDATA[ */
	$(function() {	    
		
		$("table").tablesorter({
		      sortList: [[0,0],[1,0]],widgets: ['zebra'],
		      headers: { 
		          
		          5: { sorter: false },6: { sorter: false },7: { sorter: false },
		          8: { sorter: false },9: { sorter: false },10: { sorter: false }
		      } 
		});
		  
		$(".usertable tr:odd").css("background-color","#F0F0F6");
				
	});
	/* ]]> */
</script>
