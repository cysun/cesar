<?xml version="1.0" encoding="iso-8859-1"?>
<%@ include file="/WEB-INF/views/include/taglib.jspf" %>
<c:set var="headElements">
  <title>CESAR - Edit Financial Aid</title>
  <script type="text/javascript">
    /* <![CDATA[ */
              $(function() {
                	$( "#deleteButton" ).click( function(){
	 		    	var url = 'deleteExtraCurriculum.html?extraCurriculumId=${extraCurriculum.id}';
	 		    	window.location.href = url;
	 		    	return false;
	 		    });
              });
    /* ]]> */
    </script> 
</c:set>
<%@ include file="/WEB-INF/views/include/header.jspf" %>
<c:set var="user" value="${extraCurriculum.student}" />
<c:if test="${not forProfile }" >
<c:if test="${ directory == 0 }">		
	<h3>
		<a href="<c:url value='/user/search/student/search.html'/>">Search</a>
</c:if>
<c:if test="${ directory == 1 }">		
	<h3>
		<a href="<c:url value='/user/search/advisor/search.html'/>">Search</a>
</c:if>
<c:if test="${  directory == 2 }">
	<h3>
		<a href="<c:url value='/user/search/staff/search.html'/>">Search</a>
</c:if>

<c:if test="${  directory == 3 }">
	<h3>
		<a href="<c:url value='/user/search/user/search.html'/>">Search</a>
</c:if>
&gt; ${user.name}  &gt; Edit Extra Curriculum Activity</h3>
</c:if>
<c:if test="${ forProfile }" >
<h3>Profile</h3>
<h4><a href="<c:url value='/' />">Home</a> &gt; ${user.name} &gt; Edit Extra Curriculum Activity</h4>
</c:if>
<c:if test="${not forProfile }" >
<p><a href="<c:url value='/user/display.html?userId=' />${user.id}">View</a> | Edit</p>
</c:if>
<c:if test="${ forProfile }" >
<p><a href="<c:url value='/profile.html'/> ">View</a> | Edit</p>
</c:if>

<form:form modelAttribute="extraCurriculum">
  <form:hidden path="id" />
  <form:textarea path="description" rows="5" cols="80" />

  <p><input type="submit" name="submit" value="Save" />
  <input id="deleteButton" type="button" value="Delete" /></p>
</form:form>

<%@ include file="/WEB-INF/views/include/footer.jspf" %>