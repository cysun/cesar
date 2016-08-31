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
<h3>Schedules</h3>
<p></p>
<h4>Display Schedules
<c:if test="${staffOnly }">|
 <a href="<c:url value='/schedule/importSchedule.html'/>">Import A Schedule</a>
 </c:if>
 </h4>
<div id="tabs">
	<ul>
		<li><a href="displaySchedules.html?major=CS">CS</a></li>
		<li><a href="displaySchedules.html?major=CE">CE</a></li>
		<li><a href="displaySchedules.html?major=EE">EE</a></li>
		<li><a href="displaySchedules.html?major=ME">ME</a></li>
		<li><a href="displaySchedules.html?major=TECH">TECH</a></li>
		<li><a href="displaySchedules.html?major=MATH">MATH</a></li>
		<li><a href="displaySchedules.html?major=PHYS">PHYS</a></li>
		<li><a href="displaySchedules.html?major=GENERAL">GENERAL</a></li>
		
		
	</ul>
</div>

<%@ include file="/WEB-INF/views/include/footer.jspf"%>