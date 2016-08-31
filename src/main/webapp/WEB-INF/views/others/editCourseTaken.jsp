<?xml version="1.0" encoding="iso-8859-1"?>
<%@ include file="/WEB-INF/views/include/taglib.jspf" %>
<c:set var="headElements">
  <title>CESAR - Edit Course Taken</title>
  <script type="text/javascript">
	/* <![CDATA[ */
	          $(function() {
	             $(".usertable tbody tr:odd").css("background-color","#F0F0F6");
	           
	             $( "#deleteButton" ).click( function(){
	 		    	var url = 'deleteCourseTaken.html?id=${courseTaken.id}';
	 		    	window.location.href = url;
	 		    	return false;
	 		    });
	          });
	/* ]]> */
	</script>
</c:set>
<%@ include file="/WEB-INF/views/include/header.jspf" %>
<c:set var="user" value="${courseTaken.student}" />
<c:if test="${not forProfile }" >
<br />
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

 &gt;  <a href="<c:url value='/user/display.html?userId=' />${user.id} ">${user.name}</a>  &gt; Edit Course Taken </h3>
<p><a href="<c:url value='/user/display.html?userId=' />${user.id}&#tab-courses">View</a> | Edit</p>
</c:if>
<c:if test="${ forProfile }" >
<h3>Profile</h3>
<h4><a href="<c:url value='/' />">Home</a> &gt; ${user.name} &gt; Edit Course Taken</h4>
<p><a href="<c:url value='/profile.html#tab-courses'/> ">View</a> | Edit</p>
</c:if>

<form:form modelAttribute="courseTaken">
  <form:hidden path="id" />
  <table class="usertable" cellpadding="1.5em" cellspacing="0">
  	<tr>
  		<td>Course</td>
  		<td>
  			<form:input path="course" size="40"/>
  			<c:if test="${courseTakenError!=null}">
	  			<br/>
	  			<p style="color:red">${courseTakenError }</p>
		  	</c:if>
  		</td>
  	</tr>
  	<tr>
  		<td>Grade</td>
  		<td>
  			<form:select path="grade" items="${courseTaken.gradeValues}" />
  		</td>
  	</tr>
  	<tr>
  		<td>Quarter</td>
  		<td>
  			<form:select path="quarter" items="${courseTaken.quarterValues}" />
  		</td>
  	</tr>
  	
  	<tr>
  		<td>Year</td>
  		<td><form:input path="year" size="40" /></td>
  	</tr>

  <tr>
  	<td style="text-align:center;" colspan="2">
  		<input type="submit" name="submit" value="Save" />
  		<input id="deleteButton" type="button" value="Delete" />
  	</td>
  </tr>
  </table>
</form:form>

<%@ include file="/WEB-INF/views/include/footer.jspf" %>