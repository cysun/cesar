<?xml version="1.0" encoding="iso-8859-1"?>
<%@ include file="/WEB-INF/views/include/taglib.jspf" %>
<c:set var="headElements">
  <title>CESAR - Edit Advisement</title>
  <script type="text/javascript">
    /* <![CDATA[ */
    $(function() {
	  $( "#deleteButton" ).click( function(){
		    	var url = 'deleteAdvisement.html?advisementId=${advisement.id}';
		    	window.location.href = url;
		    	return false;
	 	});
  	});

    /* ]]> */
    </script> 
</c:set>
<%@ include file="/WEB-INF/views/include/header.jspf" %>


<c:set var="user" value="${advisement.student}" />
<h3>Students</h3>
<h4><a href="<c:url value='/user/search/student/search.html' />">Search</a> &gt; ${user.name}  &gt; Edit Advisement</h4>

<p><a href="<c:url value='/user/student/display.html?userId=' />${user.id}">View</a> | Edit</p>

<form:form modelAttribute="advisement">
  <form:hidden path="id" />
  <form:textarea path="comment" rows="5" cols="80" />
  <p>For advisor only: <form:checkbox path="forAdvisorOnly" /></p>
  <input type="submit" name="submit" value="save" />
  <input id="deleteButton" type="button" value="Delete" />
</form:form>

<%@ include file="/WEB-INF/views/include/footer.jspf" %>