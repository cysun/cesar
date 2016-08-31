<?xml version="1.0" encoding="iso-8859-1"?>
<%@ include file="/WEB-INF/views/include/taglib.jspf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="headElements">
	<title>Course Plan</title>
</c:set>
<%@ include file="/WEB-INF/views/include/header.jspf"%>
<c:if test="${coursePlan.isTemplate }">
<h3>Course Plan Template Display</h3>
</c:if>
<c:if test="${not coursePlan.isTemplate }">
<h3>Course Plan Display</h3>
</c:if>
<h4>

<c:if test="${not forStudentView }">
	<c:if test="${coursePlan.isTemplate }">
		<a href="<c:url value='/user/coursePlan/viewCoursePlanTemplates.html' />">View</a>
	</c:if>
	<c:if test="${not coursePlan.isTemplate }">
		<c:if test="${not empty forProfile}">
			 <c:if test="${forProfile}" >
				<a href="<c:url value='/profile.html?#tab-advisement' />">View</a>
			 </c:if>
			 <c:if test="${not forProfile}" >
			 	<a href="<c:url value='/user/display.html?userId=${userId}#tab-advisement' />">View</a>
			 </c:if>
		</c:if>
		
		<c:if test="${empty forProfile}">
			<c:if test="${ not isAdvisor}" >
				<a href="<c:url value='/profile.html?#tab-advisement' />">View</a>
			 </c:if>
			 <c:if test="${isAdvisor}" >
			 	<a href="<c:url value='/user/display.html?userId=${coursePlan.student.id}#tab-advisement' />">View</a>
			 </c:if> 
		</c:if>
	</c:if>
</c:if>

<c:if test="${forStudentView }">
	<a href="<c:url value='/coursePlan/addCoursePlanIntro.html' />">Intro Course Plan</a>
</c:if>
</h4>



<c:if test="${fn:length(coursePlan.quarterPlans)==0 }">
	<p> No course plan available</p>
</c:if>

<c:if test="${fn:length(coursePlan.quarterPlans) > 0}">

<c:if test="${forView }">
	<table cellspacing="2" cellpadding="5" style="width: 40em;">
		
		<c:if test="${not coursePlan.isTemplate }">
			<tr style="background-color: #F0F0F6; font-size: 14px; text-align: center">
				<th>Student Name:</th>
				<td>${coursePlan.student.name }</td>
			</tr>	
			<tr style="background-color: #F0F0F6; font-size: 14px; text-align: center">
				<th>CIN:</th>
				<td>${coursePlan.student.cin }</td>
			</tr>	
			<tr style="background-color: #F0F0F6; font-size: 14px; text-align: center">
				<c:if test="${not empty coursePlan.student.currentAdvisor }" >
					<th>Advisor:</th>
					<td>${coursePlan.student.currentAdvisor.name }</td>
				</c:if>
			</tr>
		</c:if>
			<tr style="background-color: #F0F0F6; font-size: 14px; text-align: center">
				<th>Major</th>
				<c:if test="${coursePlan.isTemplate }">
					<td>${coursePlan.major.symbol }</td>
				</c:if>
				<c:if test="${not coursePlan.isTemplate }">
					<td>${coursePlan.student.major.symbol }</td>
				</c:if>
			</tr>
		
		<tr style="background-color: #F0F0F0; font-size: 14px; text-align: center">
			<th>Name</th>
			<td>${coursePlan.name }</td>
		</tr>
		
		<tr style="background-color: #F0F0F6; font-size: 14px; text-align: center">
			<th>Note</th>
			<td>${coursePlan.note }</td>
		</tr>
	</table>
</c:if>

	<p>Following courses are planned to take in each quarter:</p>
	<c:forEach items="${coursePlan.quarterPlans}" var="quarterPlan" >
		<c:if test="${fn:length(quarterPlan.sections)>0 }">
			<h3>${quarterPlan.quarter}</h3>
			<table class="tablesorter shadow">
			<thead>
				<tr><th>Course</th><th>Section #</th><th>Call #</th><th>Units</th><th>Days</th><th>Times</th><th>Location</th><th>Capacity</th><th>Course Info</th></tr>	
			</thead>
			<tbody>
				<c:forEach items="${quarterPlan.sections }" var="section" >
					<tr>
						
						<td>
							${section.course.code }
							<c:if test="${not empty section.option}" >
								-${section.option}
							</c:if>
						</td>
						
						<td>
							${section.sectionNumber }
						</td>
						<td>
							${section.callNumber }
						</td>
						<td>
							${section.units }
						</td>
						
						<td>
							<c:forEach items="${section.weekDays }" var="weekDay">
								${weekDay.symbol} 
							</c:forEach>
						</td>
						<td>${section.startTime }-${section.endTime }</td>
						<td>
							${section.location }
						</td>
						<td>
							${section.capacity }
						</td>
						<td>
							${section.info}
						</td>
					</tr>
				
				</c:forEach>
				<c:forEach items="${quarterPlan.courses }" var="course" >
					<tr>
						<td>${course.code }</td>
						<td>${course.name }</td>
						<td></td>
						<td></td>
						<td>${course.units }</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						
					</tr>
				</c:forEach>
			</tbody>	
		</table>
		</c:if>
		<c:if test="${(fn:length(quarterPlan.sections)==0) && (fn:length(quarterPlan.courses)>0)}">
			<h3>${quarterPlan.quarter}</h3>
			<table class="tablesorter shadow">
				<thead>
					<tr><th>Course</th><th>Title</th><th>Units</th></tr>	
				</thead>
				<tbody>
					<c:forEach items="${quarterPlan.courses}" var="course">
						<tr>
							<td>${course.code }</td>
							<td>${course.name }</td>
							<td>${course.units }</td>
						</tr>
						 
					</c:forEach>
				</tbody>
			</table>
		</c:if>
		<c:if test="${(fn:length(quarterPlan.sections)>0) || (fn:length(quarterPlan.courses)>0)}">
			<table cellspacing="2" cellpadding="5" style="width: 40em;">
				<tr style="background-color: #F0F0F6; font-size: 14px; text-align: center">
					<th>Total units</th>
					<td> ${quarterPlan.units }</td>
				</tr>
			
				<tr style="background-color: #F0F0F6; font-size: 14px; text-align: center">
					<th>Note</th>
					<td>${quarterPlan.notes }</td>
				</tr>
				
				
			</table>
		</c:if>
	</c:forEach>
</c:if>

<br />
Total Units: ${coursePlan.units }
<br />
<br />

<table cellspacing="2" cellpadding="5" style="width: 40em;">
	<tr style="background-color: #F0F0F6; font-size: 14px; text-align: center">	
		<c:if test="${not coursePlan.isTemplate }">
			<td>Signed:</td>
			<td>${coursePlan.student.name }</td>
		</c:if>
		<td>Date:</td>
		<td><fmt:formatDate value="${coursePlan.timeStamp }" pattern="yyyy-MM-dd" /></td>
	</tr>
	<c:if test="${coursePlan.approved }">
		<tr style="background-color: #F0F0F6; font-size: 14px; text-align: center">
		
				<td>Signed:</td>
				<td>${coursePlan.advisor }</td>
		
			<td>Date:</td>
			<td><fmt:formatDate value="${coursePlan.approvedDate }" pattern="yyyy-MM-dd" /></td>
		</tr>
	</c:if>
	
</table>
<br />
<br />
<c:if test="${not coursePlan.isTemplate }">
	<c:if test="${not empty forProfile }">
	<form method="post" action="/cesar/user/coursePlan/view/viewCoursePlan.html">
		<c:if test="${coursePlan.approved}">
			<input type="checkbox" name="signature" value="approved" checked="checked" />Approved <br />
		</c:if>
		<c:if test="${not coursePlan.approved}">
			<input type="checkbox" name="signature" value="approved" />Approved <br />
		</c:if>
		<input type="hidden" name="userId" value="${userId}" />
		<input type="hidden" name="coursePlanId" value="${coursePlan.id}" />
		<p>
			<input type="submit" name="_save" value="Save" class="subbutton" />
		</p>
	</form>
	
	</c:if>
</c:if>

<c:if test="${forView }">
	<c:if test="${coursePlan.isTemplate }" >
		<c:if test="${ not forStudentView }" >
			<form method="get" action="/cesar/user/coursePlan/addCoursePlanTemplate.html">
				<input type="hidden" name="coursePlanId" value="${coursePlan.id }"></input>
				<p>
					<input type="submit" name="Edit" value="Edit" class="subbutton" />
				</p>
			</form>
		</c:if>
	</c:if>
</c:if>
<c:if test="${not forView}" >
	<c:if test="${not coursePlan.isTemplate}">
		<form method="post" action="addCoursePlan.html" >
			<input type="hidden" name="name" value="${coursePlan.name }" />
			<input type="hidden" name="note" value="${coursePlan.note }" />
	</c:if>
	<c:if test="${coursePlan.isTemplate}">
		<form method="post" action="/cesar/user/coursePlan/addCoursePlanTemplate.html" >
		<p>
			Name:<input type="text" name="name" size="80" value="${coursePlan.name }"/><br />
			Note:<br /> <textarea name="note"  rows="5" cols="80">${coursePlan.note }</textarea>
		</p>
	</c:if>
	<p>
		<input type="submit" name="_previous" value="Back" class="subbutton" />
		<input type="submit" name="_save" value="Save" class="subbutton" />
	</p>
	<input type="hidden" name="_page" value="${currentPage}" />
		
		<input type="hidden" name="quarterCode" value=${quarterCode} />
</form>
</c:if>

<script type="text/javascript">
    /* <![CDATA[ */
    $(function() {      
        
        $("table").tablesorter({
              sortList: [[0,0],[1,0]]
        });
                
    });
    /* ]]> */
</script>

<%@ include file="/WEB-INF/views/include/footer.jspf"%>