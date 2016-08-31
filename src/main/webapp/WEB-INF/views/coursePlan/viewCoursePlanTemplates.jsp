<?xml version="1.0" encoding="iso-8859-1"?>
<%@ include file="/WEB-INF/views/include/taglib.jspf"%>
<c:set var="headElements">
	<title>Course Plan Template</title>
	<script type="text/javascript">
	/* <![CDATA[ */
	  $("table").tablesorter({
	      sortList: [[0,0]],widgets: ['zebra'],
	      headers: { 
	          2: { sorter: false },
	          3: { sorter: false }
	      } 
	  });
	/* ]]> */
	</script>
</c:set>
<%@ include file="/WEB-INF/views/include/header.jspf"%>
<h3>Course Plan Templates</h3>
<h4>
View
|<a href="<c:url value='/user/coursePlan/addCoursePlanTemplate.html' />">Add</a>
</h4>

<c:if test="${fn:length(coursePlanTemplates) == 0}">
  <p>No course plan templates on record.</p>
</c:if>

<c:if test="${fn:length(coursePlanTemplates) > 0}">
<table class="tablesorter shadow" cellspacing="2" cellpadding="5" style="width: 100%;">
<thead>
  <tr><th>Name</th><th>Date</th><th>Advisor</th><th>Major</th><th>Note</th><th></th><th></th></tr>
</thead>
<tbody>
  <c:forEach items="${coursePlanTemplates}" var="template">
  <tr style="background-color: #F0F0F6; font-size: 14px;">
    <td><a href="<c:url value='/user/coursePlan/view/viewCoursePlan.html?coursePlanId=${template.id}' />">
   ${template.name}</a></td>
    <td><fmt:formatDate value="${template.timeStamp}" pattern="yyyy-MM-dd HH:mm" /></td>
    <td>${template.advisor }</td>
    <td>${template.major.symbol }</td>
    <td>${template.note}</td>
	<td>
	  <a href="<c:url value='/user/coursePlan/addCoursePlanTemplate.html?coursePlanId=${template.id }' />">Edit</a>
	</td>
	<td>
		<a href="<c:url value='/coursePlan/deleteCoursePlan.html?coursePlanId=${template.id}' />">Delete</a>
	</td>
  </tr>
  </c:forEach>
</tbody>
</table>
</c:if>

<%@ include file="/WEB-INF/views/include/footer.jspf"%>