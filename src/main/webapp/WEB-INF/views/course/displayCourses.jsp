<?xml version="1.0" encoding="iso-8859-1"?>
<%@ include file="/WEB-INF/views/include/taglib.jspf"%>
<c:set var="headElements">
	<title>Display Department Schedules</title>
	<script type="text/javascript">
	/* <![CDATA[ */
	$(function() {	    
		$( "#tabs" ).tabs({
			ajaxOptions: {cache: false}
		});
	});
	/* ]]> */
	</script>
</c:set>
<%@ include file="/WEB-INF/views/include/header.jspf"%>
<h3>Courses</h3>
<p></p>
<c:if test="${staffOnly }">
<h4><a href="<c:url value='/course/addCourse.html'/>">Add A Course</a></h4>
</c:if>
<div id="tabs">
	<ul>
		<li><a href="displayCourses.html?symbol=CS">CS</a></li>
		<li><a href="displayCourses.html?symbol=CE">CE</a></li>
		<li><a href="displayCourses.html?symbol=EE">EE</a></li>
		<li><a href="displayCourses.html?symbol=ME">ME</a></li>
		<li><a href="displayCourses.html?symbol=TECH">TECH</a></li>
		<li><a href="displayCourses.html?symbol=MATH">MATH</a></li>
		<li><a href="displayCourses.html?symbol=PHYS">PHYS</a></li>
		<li><a href="displayCourses.html?symbol=GENERAL">GENERAL</a></li>
	</ul>
</div>

<%@ include file="/WEB-INF/views/include/footer.jspf"%>