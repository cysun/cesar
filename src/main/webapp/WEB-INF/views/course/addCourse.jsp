<?xml version="1.0" encoding="iso-8859-1"?>
<%@ include file="/WEB-INF/views/include/taglib.jspf"%>
<c:set var="headElements">
	<title>Add Course</title>
	<script type="text/javascript">
	/* <![CDATA[ */
	          $(function() {
	             $(".usertable tbody tr:odd").css("background-color","#F0F0F6");
	             
	             $( "#dialog" ).dialog({
	     			autoOpen: false,
	     			show: "blind",
	     			hide: "explode"
	     		});

	     		$( "#opener" ).click(function() {
	     			$( "#dialog" ).dialog( "open" );
	     			return false;
	     		});
	     		
	     		
	     		$( "#checkedAll").click(function(){
	     			$(':checkbox').attr('checked', true);
	     		});
	     		
	     		$( "#checkedNone").click(function(){
	     			$(':checkbox').attr('checked', false);
	     		});
	     		
	     		$( "#checkedRev" ).click( function(){
	     			$(':checkbox').each( function(){
	     				$(this).attr("checked",!$(this).attr("checked") );
	     			});
	     		});
	     		$( "#cancelButton" ).click( function(){
	 		    	var url = '/cesar/course/displayCourses.html';
	 		    	window.location.href = url;
	 		    	return false;
	 		    });
	          });
	/* ]]> */
	</script>	             
</c:set>
<%@ include file="/WEB-INF/views/include/header.jspf"%>
<h3><a href="<c:url value='/course/displayCourses.html' />">Courses</a>
&gt; Add A Course
</h3>

<form:form modelAttribute="course">
  	<table class="usertable shadow" cellpadding="1.5em" cellspacing="0">
	  	<tr>
	  		<th>Code:</th>
	  		<td> <form:input path="code" size="80" />
				<c:if test="${codeError!=null}">
		  			<br/>
		  			<p style="color:red">${codeError }</p>
	  			</c:if>		
	  		 </td>
	  	</tr>
	  	
	  	<tr>
	  		<th>Name:</th>
	  		<td><form:input path="name" size="80" /></td>
	  	</tr>
  	
	  	<tr>
	  		<th>Units:</th>
	  		<td><form:input path="units" size="5" /></td>
	  	</tr>
	  	
	  	<tr>
	  		<th>Prerequisite(s)</th>
	  		<td>
	  			<c:if test="${prerequisiteError!=null}">
		  			<br/>
		  			<p style="color:red">${prerequisiteError }</p>
	  			</c:if>
	  			<form:input path="prereqString" size="60" /> <button id="opener">Help</button>
	  			
	  		</td>
	  	</tr>

	  	<tr>
	  		<th>Majors:</th>
	  		
	  		<td>
	  			<c:if test="${majorError!=null}">
		  			<br/>
		  			<p style="color:red">${majorError }</p>
	  			</c:if>
		  		<input type="checkbox" name="symbols" value="CS" /> Computer Science<br />
		  		<input type="checkbox" name="symbols" value="EE" /> Electrical Engineering<br />
		  		<input type="checkbox" name="symbols" value="CE" /> Civil Engineering<br />
		  		<input type="checkbox" name="symbols" value="ME" /> Mechanical Engineering<br />
		  		<input type="checkbox" name="symbols" value="TECH" /> Technology<br />
		  		<input type="checkbox" name="symbols" value="MATH" /> Mathematics<br />
		  		<input type="checkbox" name="symbols" value="PHYS" /> Physics<br />
		  		<input type="checkbox" name="symbols" value="GENERAL" /> General Education  <br />
		  		<input type="button" id="checkedAll" value="Select All" />
		  		<input type="button" id="checkedNone" value="Select None" />
		  		<input type="button" id="checkedRev" value="Select Inverse" />
		  	</td>
	  	</tr>

		<tr>
	  		<th>Repeatable</th>
	  		<td><form:checkbox path="repeatable" /></td>
	  	</tr>
	  	
  		<tr>
			<th style="text-align:center;" colspan="2">
				<input type="submit" name="save" value="Save" />
				<input id="cancelButton" type="button" value="Cancel" />
			</th>
		</tr>
	</table>
</form:form>

<div id="dialog" title="Format" style="font-size:12px">
<p>Use space to separate the prerequisites, e.g. <tt>CS101 CS201</tt>.</p>
<p>If two or more classes can serve as the same prerequisite, use ""/" to separate
them. For example, if the prerequisites for a class are CS120, CS122, and either
CS245 or CS345, enter them as <tt>CS120 CS122 CS245/CS345</tt>.</p>
</div>


<%@ include file="/WEB-INF/views/include/footer.jspf"%>