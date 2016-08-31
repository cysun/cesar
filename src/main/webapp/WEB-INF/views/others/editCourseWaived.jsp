<?xml version="1.0" encoding="iso-8859-1"?>
<%@ include file="/WEB-INF/views/include/taglib.jspf" %>
<c:set var="headElements">
  <title>CESAR - Edit Course Waived</title>
  <script type="text/javascript">
	/* <![CDATA[ */
	          $(function() {
	             $(".usertable tbody tr:odd").css("background-color","#F0F0F6");
	           
	             $( "#deleteButton" ).click( function(){
	 		    	var url = 'deleteCourseWaived.html?id=${courseWaived.id}';
	 		    	window.location.href = url;
	 		    	return false;
	 		    });
	          });
	/* ]]> */
	</script>
</c:set>
<%@ include file="/WEB-INF/views/include/header.jspf" %>
<c:set var="user" value="${courseWaived.student}" />
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
 &gt;  <a href="<c:url value='/user/display.html?userId=' />${user.id} ">${user.name}</a> &gt; Edit Course Waived </h3>
<p><a href="<c:url value='/user/display.html?userId=' />${user.id}&#tab-courses">View</a> | Edit</p>

<form:form modelAttribute="courseWaived">
  <form:hidden path="id" />
  <table class="usertable" cellpadding="1.5em" cellspacing="0">
  	<tr>
  		<td>Course</td>
  		<td>
  			<form:input path="course" size="40"/>
  			<c:if test="${courseWaivedError!=null}">
	  			<br/>
	  			<p style="color:red">${courseWaivedError }</p>
		  	</c:if>
  		</td>
  	</tr>
  	
	<tr>
		<th>Comment</th>
		<td><form:textarea path="comment" rows="5" cols="40" /></td>
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