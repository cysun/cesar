<?xml version="1.0" encoding="iso-8859-1"?>
<%@ include file="/WEB-INF/views/include/taglib.jspf"%>
<c:set var="headElements">
	<title>Intro Course Plan</title>
</c:set>
<%@ include file="/WEB-INF/views/include/header.jspf"%>
<h3>Intro Course Plan</h3>

<c:if test="${isAdvisor }">
<h4>
<a href="<c:url value='/user/display.html?userId=${coursePlan.student.id }&#tab-advisement' />">View</a>
<c:if test="${not isEdit }">
	<a href="<c:url value='/coursePlan/addCoursePlanIntro.html' />"> &gt; Select </a>
</c:if> &gt; Add
</h4>
</c:if>
<c:if test="${not isAdvisor }">
<h4>
<a href="<c:url value='/profile.html?#tab-advisement' />">View</a>
<c:if test="${not isEdit }"> 
	<a href="<c:url value='/coursePlan/addCoursePlanIntro.html' />"> &gt; Select </a>
</c:if> &gt; Add
</h4>
</c:if>

<br />

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
	</c:forEach>

<form method="post" action="addCoursePlan.html">

	<input type="hidden" name="quarterCode" value="${quarter.code}" />
	<input type="hidden" name="firstQuarter" value="true" />
	<input type="hidden" name="_page" value="0" />
	<p>
		Name:<input type="text" name="name" size="80" value="${coursePlan.name }"/><br />
		Note:<br /> <textarea name="note"  rows="5" cols="80">${coursePlan.note }</textarea>
	</p>
	<p>
	<input type="submit" name="_target1" value="Next" class="subbutton" />
	</p>
</form>

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