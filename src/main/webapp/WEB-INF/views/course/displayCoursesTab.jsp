<?xml version="1.0" encoding="iso-8859-1"?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ include file="/WEB-INF/views/include/taglib.jspf"%>

<div id="tab-${major}">

<c:if test="${fn:length(courses) == 0}">
  <p>No course information on record.</p>
</c:if>

<c:if test="${fn:length(courses) > 0}">
<table class="tablesorter shadow" cellspacing="2" cellpadding="5" style="width: 100%;">
<thead>
  <tr><th>Code</th><th>Name</th><th>Prerequisite(s)</th>
  <c:if test="${staffOnly }">
  	<th><br /></th>
  </c:if>
  </tr>
</thead>
<tbody>
  <c:forEach items="${courses}" var="course">
  <tr style="background-color: #F0F0F6; font-size: 14px;">
    <td>${course.code}</td>
    <td>${course.name}</td>
    <td>${course.prereqAsString}</td>
    <c:if test="${staffOnly }">
		<td>
		  <a href="<c:url value='/course/editCourse.html?courseId=${course.id}' />">Edit</a>
		</td>
	</c:if>
  </tr>
  </c:forEach>
</tbody>
</table>
</c:if>

</div>

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
