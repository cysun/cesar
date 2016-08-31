<?xml version="1.0" encoding="iso-8859-1"?>
<%@ include file="/WEB-INF/views/include/taglib.jspf"%>
<c:set var="headElements">
    <title>Edit Course</title>
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
	     		
	     		$( "#deleteButton" ).click( function(){
	 		    	var url = 'deleteCourse.html?courseId=${course.id}';
	 		    	window.location.href = url;
	 		    	return false;
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
&gt; Edit A Course
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
            <th>Majors</th>
            <td>
            	<c:if test="${majorError!=null}">
		  			<br/>
		  			<p style="color:red">${majorError }</p>
	  			</c:if>
	            <c:forEach items="${majors }" var="major" >
	            	<c:set value="${false}" var="checkFlag" />
	            	<c:forEach items="${major.courses }" var="testCourse">
	            		<c:if test="${testCourse.code eq course.code }" >
	            			<c:if test="${testCourse.name eq course.name }" >
		            			<c:if test="${major.id == 307 }">
		            				<input type="checkbox" id="generalMajor" name="symbols" value="${major.symbol }" checked />${major.name } <br />
		            			</c:if>
		            			<c:if test="${major.id != 307 }">
		            				<input type="checkbox" id="${major.symbol}" name="symbols" value="${major.symbol }" checked />${major.name } <br />
		            			</c:if>
		            			<c:set value="${true}" var="checkFlag" />
		            		</c:if>
	            		</c:if>
	            	</c:forEach>
	            	<c:if test="${not checkFlag }" >
	            		<c:if test="${major.id == 307 }">
	            			<input type="checkbox" name="symbols" value="${major.symbol }" />${major.name } <br />
	            		</c:if>
	            		<c:if test="${major.id != 307 }">
	            			<input type="checkbox" name="symbols" value="${major.symbol }" />${major.name } <br />
	            		</c:if>
	            	</c:if>
	            </c:forEach>
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
            <th style="text-align:center;" colspan="2"><input type="submit" name="save" value="Save" />
            <input id="deleteButton" type="button" value="Delete" />
            <input id="cancelButton" type="button" value="Cancel" />
           	</th>
        </tr>
    </table>
<input type="hidden" name="id" value="${course.id}" />
</form:form>

<div id="dialog" title="Format" style="font-size:12px">
<p>Use space to separate the prerequisites, e.g. <tt>CS101 CS201</tt>.</p>
<p>If two or more classes can serve as the same prerequisite, use "/" to separate
them. For example, if the prerequisites for a class are CS120, CS122, and either
CS245 or CS345, enter them as <tt>CS120 CS122 CS245/CS345</tt>.</p>
</div>

<%@ include file="/WEB-INF/views/include/footer.jspf"%>