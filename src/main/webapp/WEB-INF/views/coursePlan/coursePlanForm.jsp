<?xml version="1.0" encoding="iso-8859-1"?>
<%@ include file="/WEB-INF/views/include/taglib.jspf"%>
<c:set var="headElements">
	<title>Course Plan</title>
	<script type="text/javascript">
	/* <![CDATA[ */
	$(function() {  
	    $(".courseTable").tablesorter({
			// sort on the second column
			sortList: [[0,0]],widgets: ['zebra'],
			headers: { 
	            
	            
	        } 
		}); 
	    $(".sectionTable1").tablesorter({
			// sort on the second column
			sortList: [[1,0],[2,0]],widgets: ['zebra'],
			headers: { 
	            //(we start counting zero)
	            // assign the fourth column (we start counting zero) 
	            0: { 
	                // disable it by setting the property sorter to false 
	                sorter: false 
	            },
	            4: { 
	                // disable it by setting the property sorter to false 
	                sorter: false 
	            }, 
	            5: { 
	                // disable it by setting the property sorter to false 
	                sorter: false 
	            }, 
	            6: { 
	                // disable it by setting the property sorter to false 
	                sorter: false 
	            }, 
	            7: { 
	                // disable it by setting the property sorter to false 
	                sorter: false 
	            }, 
	           	8: { 
	                // disable it by setting the property sorter to false 
	                sorter: false 
	            }, 
	            9: { 
	                // disable it by setting the property sorter to false 
	                sorter: false 
	            }, 
	            10: { 
	                // disable it by setting the property sorter to false 
	                sorter: false 
	            } 
	            
	            
	        } 
		}); 
	    $(".sectionTable2").tablesorter({
			// sort on the second column
			sortList: [[1,0], [2,0]],widgets: ['zebra'],
			headers: { 
	            //(we start counting zero)
	            // assign the fourth column (we start counting zero) 
	            0: { 
	                // disable it by setting the property sorter to false 
	                sorter: false 
	            },
	            4: { 
	                // disable it by setting the property sorter to false 
	                sorter: false 
	            }, 
	            5: { 
	                // disable it by setting the property sorter to false 
	                sorter: false 
	            }, 
	            6: { 
	                // disable it by setting the property sorter to false 
	                sorter: false 
	            }, 
	            7: { 
	                // disable it by setting the property sorter to false 
	                sorter: false 
	            }, 
	           	8: { 
	                // disable it by setting the property sorter to false 
	                sorter: false 
	            }, 
	            9: { 
	                // disable it by setting the property sorter to false 
	                sorter: false 
	            } 
	            
	            
	        } 
		}); 
	    
	    $("input[type=checkbox]").click( function() {
	    	if ($(this).is(':checked')){
	    		var rowInd = $(this).closest("tr").index();
            	var capacity = $(this).closest("tr").find("td").eq(8).text();
            	var enrollTotal = $(this).closest("tr").find("td").eq(9).text();
            	var day = $(this).closest("tr").find("td").eq(5).text();
            	var time = $(this).closest("tr").find("td").eq(6).text();
            	var section = $(this).closest("tr").find("td").eq(1).text();           	
            	
            	var s = section.split(" ");
            	if (s.length > 1) {
            		var temp = "";
            		for (var a = 0; a < s.length; a++) {
            			temp = temp + trim(s[a]);
            		}
            		section = temp;
            	} 
            	
            	if (trim(capacity) != "") {
            		if (enrollTotal >= parseInt(trim(capacity))) {
        				var confirmEnrollment = confirm("This " + trim(section) + " section is already full. \nDo you want to add it to your course plan anyway?");
        				if (confirmEnrollment==true) {
           	    	  		$(this).attr('checked','checked');
           	    	  	} else {
           	    	  		$(this).removeAttr("checked");
           	    	  	}
            		}
	    		}
	    	
            	var conflictSections = new Array();
            	var confCount = 0;
            
            	$("input[type=checkbox]").each(function() {   
            		if($(this).is(':checked')) {
            			var rowInd2 = $(this).closest("tr").index();
                		var day2 = $(this).closest("tr").find("td").eq(5).text();
                		var time2 = $(this).closest("tr").find("td").eq(6).text();
                		var section2 = $(this).closest("tr").find("td").eq(1).text();
                		
                		var s2 = section2.split(" ");
                    	if (s2.length > 1) {
                    		var temp2 = "";
                    		for (var b = 0; b < s2.length; b++) {
                    			temp2 = temp2 + trim(s2[b]);
                    		}
                    		section2 = temp2;
                    	} 
                    	
                        if (contains(conflictSections, section2) == false) {
                            if((day2.indexOf(day) !== -1 || day.indexOf(day2) !== -1) && rowInd2 !== rowInd) {
                             	var str1 = time.split("-");
                                var s1 = new Date("1/1/2013 " + str1[0]);
                    			var e1 = new Date("1/1/2013 " + str1[1]);
                                	
                                var str2 = time2.split("-");
                                var s2 = new Date("1/1/2013 " + str2[0]);
                    			var e2 = new Date("1/1/2013 " + str2[1]);
                    				
                    			if (e1 < s2 || s1 > e2) {
                    						
                    			} else {
                    				conflictSections[confCount] = trim(section2);
                    				confCount++;
                    			}        				
                            }
                        }
            		}
           		});
            	
                if (conflictSections.length != 0) {
                	var comfirmQuestion=confirm("This " + trim(section) + " section has time conflict with " + conflictSections + "! \nAre you sure you want to enroll this section?");
       	    	  	if (comfirmQuestion==true) {
       	    	  		$(this).attr('checked','checked');
       	    	  	} else {
       	    	  		$(this).removeAttr("checked");
       	    	  	}
                }
	    	}
	    });
	    
	});
	
	function contains(a, obj) {
	    var i = a.length;
	    while (i--) {
	       if (a[i] === obj) {
	           return true;
	       }
	    }
	    return false;
	}
	
	function trim(str) {
		return str.replace(/^\s+|\s+$/g, '');
	}
	/* ]]> */
	</script>
</c:set>
<%@ include file="/WEB-INF/views/include/header.jspf"%>
<h3>Course Plan</h3>
<c:if test="${ coursePlan.isTemplate }">
	<a href="<c:url value='/user/coursePlan/viewCoursePlanTemplates.html' />">View</a>
</c:if>
<c:if test="${not coursePlan.isTemplate }">
<c:if test="${isAdvisor }">
<h4>
<a href="<c:url value='/user/display.html?userId=${coursePlan.student.id }&#tab-advisement' />">View</a>
|Add
</h4>
</c:if>
<c:if test="${not isAdvisor }">
<h4>
<a href="<c:url value='/profile.html?#tab-advisement' />">View</a>
|Add
</h4>
</c:if>
</c:if>
	<c:if test="${fn:length(availSections) == 0 }">
	<p style="color:red">${warning }</p>
	</c:if>
	
	<c:if test="${fn:length(availSections) != 0 }" >
		<p>Course plan in ${quarterPlan.quarter}</p>
	</c:if>	
	<form:form action ="/cesar/coursePlan/addCoursePlan.html" method="POST" modelAttribute="quarterPlan" >
	<c:if test="${not empty greyCourses }">
		<c:if test="${fn:length(availSections) != 0 }">
			<p>Following <span style="color:grey">courses </span>neither meet the prerequisites nor are offered in this quarter.</p>
		</c:if>
		<c:if test="${fn:length(availSections) == 0 }">
			<p>Following  <span style="color:grey">courses </span> courses do not meet the prerequisites</p>
		</c:if>
		
	<table class="tablesorter shadow courseTable">
		<thead>
			<tr><c:if test="${fn:length(availSections) == 0 }">
				<th></th>
				</c:if>
				<th>Course Code</th><th>Units</th><th>Missing prerequisites</th>
			</tr>	
		</thead>
		<tbody>
		<c:forEach items="${greyCourses}" var="course" > 
			<tr>
				<c:if test="${fn:length(availSections) == 0 }">
					<td>
						<c:set value="${false}" var="isChecked" />
						
						<c:forEach items="${quarterPlan.courses }" var="preCourse">
							<c:if test="${preCourse.id eq course.id }">
								<form:checkbox path="courses" value="${course.id }" checked="true"/>
								<c:set value="${true}" var="isChecked" />
							</c:if>
						</c:forEach>
						
						<c:if test="${not isChecked }">
								<form:checkbox path="courses" value="${course.id }" />
						</c:if>
					</td>
				</c:if>
				<td style="color:grey">
					${course.code }
				</td>
				<td>
					${course.units }
				</td>
				<td>
					${course.extraInfo}
				</td>
			</tr>
		</c:forEach>	
		</tbody>	
	</table>
	</c:if>
	
	<c:if test="${not empty redCourses}" >
	<p>Following <span style="color:red">courses </span> meet prerequisites, but courses are not offered this quarter.</p>
	
	<table class="tablesorter shadow courseTable">
		<thead>
			<tr><th>Course Code</th><th>Units</th></tr>	
		</thead>
		<tbody>
		<c:forEach items="${redCourses}" var="course" > 
			<tr>
				<td style="color:red">
					${course.code }
				</td>
				<td>
					${course.units }
				</td>
			</tr>
		</c:forEach>	
		</tbody>	
	</table>
	</c:if>
	
		<c:if test="${not empty blueCourses}" >
		<p>You may take the following <span style="color:green">courses </span> in this quarter based on the course prerequisites
		and the courses you have or would have taken by this quarter:</p>
		<table class="tablesorter shadow courseTable">
			<thead>
				<tr><th></th><th>Course</th><th>Units</th></tr>	
			</thead>
			<tbody>
			<c:forEach items="${blueCourses}" var="course">
				<tr>
					<td>
						<c:set value="${false}" var="isChecked" />
						
						<c:forEach items="${quarterPlan.courses }" var="preCourse">
							<c:if test="${preCourse.id eq course.id }">
								<form:checkbox path="courses" value="${course.id }" checked="true"/>
								<c:set value="${true}" var="isChecked" />
							</c:if>
						</c:forEach>
						
						<c:if test="${not isChecked }">
								<form:checkbox path="courses" value="${course.id }" />
						</c:if>
					</td>
					<td style="color:green">
						 ${course.code}
					</td>
				
					<td>
						${course.units }
					</td>
					
				</tr>
			</c:forEach>	
			</tbody>	
		</table>
		</c:if>
		
		<c:if test="${not empty yellowSections}" >
			<p> Following <span style="color:orange">courses </span> are offered this quarter. However, missing the prerequisite(s).  </p>
			<table class="tablesorter shadow sectionTable1">
				<thead>
					<tr><th></th><th>Course</th><th>Section #</th><th>Call #</th><th>Units</th><th>Days</th><th>Times</th><th>Location</th><th>Capacity</th><th>Enrollment Total</th><th>Course Info</th><th>Prerequisites</th></tr>	
				</thead>
				<tbody>
				<c:forEach items="${yellowSections}" var="section" > 
					<tr>
						<td>
							<c:set value="${false}" var="isChecked" />
							
							<c:forEach items="${quarterPlan.sections }" var="preSection">
								<c:if test="${preSection.id eq section.id }">
									<form:checkbox path="sections" value="${section.id }" checked="true" />
									<c:set value="${true}" var="isChecked" />
								</c:if>
							</c:forEach>
							
							<c:if test="${not isChecked }">
									<form:checkbox path="sections" value="${section.id }" />
							</c:if>
						</td>
						<td style="color:orange">
							 ${section.course.code }<c:if test="${not empty section.option}">-${section.option}</c:if>
						</td>
						
						<td>
							${section.sectionNumber }
						</td>
						<td>
							${section.callNumber }
						</td>
						<td>
							${section.units }
						</td>
						
						<td>
							<c:forEach items="${section.weekDays }" var="weekDay">
								${weekDay.symbol} 
							</c:forEach>
						</td>
						<td>${section.startTime }-${section.endTime }</td>
						<td>
							${section.location }
						</td>
						<c:choose>
            			<c:when test="${(fn:trim(section.capacity) le section.enrollmentTotal) && (not empty (fn:trim(section.capacity)))}">
							<td style="color: red; font-size : large;">
								${section.capacity }
							</td>
						</c:when>
            			<c:otherwise>
            				<td>
								${section.capacity }
							</td>
            			</c:otherwise>
        				</c:choose>
        				<c:choose>
            			<c:when test="${(fn:trim(section.capacity) le section.enrollmentTotal) && (not empty (fn:trim(section.capacity)))}">
							<td style="color: red; font-size : large;">
								${section.enrollmentTotal }
							</td>
						</c:when>
            			<c:otherwise>
            				<td>
								${section.enrollmentTotal }
							</td>
            			</c:otherwise>
        				</c:choose>
						<td>
							${section.info}
						</td>
						<td>
							${section.extraInfo}
						</td>
					</tr>
				</c:forEach>	
				</tbody>	
			</table>
		</c:if>
		
		<c:if test="${not empty greenSections}" >
		<p>You may take the following <span style="color:green">courses </span> in this quarter based on the course prerequisites
		and the courses you have or would have taken by this quarter:</p>
		<table class="tablesorter shadow sectionTable2">
			<thead>
				<tr><th></th><th>Course</th><th>Section #</th><th>Call #</th><th>Units</th><th>Days</th><th>Times</th><th>Location</th><th>Capacity</th><th>Enrollment Total</th><th>Course Info</th></tr>	
			</thead>
			<tbody>
			<c:forEach items="${greenSections}" var="section">
				<tr>
					<td>
					<c:set value="${false}" var="isChecked" />
						
						<c:forEach items="${quarterPlan.sections }" var="preSection">
							<c:if test="${preSection.id eq section.id }">
								<form:checkbox path="sections" value="${section.id }" checked="true" />
								<c:set value="${true}" var="isChecked" />
							</c:if>
						</c:forEach>
						
						<c:if test="${not isChecked }">
							<form:checkbox path="sections" value="${section.id }" />
						</c:if>
					</td>	
					<td style="color:green">
						${section.course.code}<c:if test="${not empty section.option}">-${section.option}</c:if>
					</td>
				
					<td>
						${section.sectionNumber }
					</td>
					<td>
						${section.callNumber }
					</td>
					<td>
						${section.units }
					</td>
					
					<td>
						<c:forEach items="${section.weekDays }" var="weekDay">
							${weekDay.symbol} 
						</c:forEach>
					</td>
					<td>${section.startTime }-${section.endTime }</td>
					<td>
						${section.location }
					</td>
					<c:choose>
            			<c:when test="${(fn:trim(section.capacity) le section.enrollmentTotal) && (not empty (fn:trim(section.capacity)))}">
							<td style="color: red; font-size : large;">
								${section.capacity }
							</td>
						</c:when>
            			<c:otherwise>
            				<td>
								${section.capacity }
							</td>
            			</c:otherwise>
        			</c:choose>
        			<c:choose>
            			<c:when test="${(fn:trim(section.capacity) le section.enrollmentTotal) && (not empty (fn:trim(section.capacity)))}">
							<td style="color: red; font-size : large;">
								${section.enrollmentTotal }
							</td>
						</c:when>
            			<c:otherwise>
            				<td>
								${section.enrollmentTotal }
							</td>
            			</c:otherwise>
        			</c:choose>
					<td>
						${section.info}
					</td>
					
				</tr>
			</c:forEach>	
			</tbody>	
		</table>
		</c:if>

		<c:if test="${empty fromStudent }">
			<p>
			<a href="<c:url value='/schedule/section/addSection.html?_page=${currentPage }&_target=${targetPage}&quarterCode1=${quarterPlan.quarter.code}&fromCoursePlan=true' />">Add A Section</a>
			</p>
		</c:if>
		
		<br />
		Note:<br />
		<form:textarea cols='80' rows='10' path="notes" />
		
		<p>
			<c:if test="${currentPage > 1 }" >
				<input type="submit" name="_previous" value="Back" class="subbutton" />
			</c:if>
			
			<c:if test="${currentPage !=-1}">
				<input type="submit" name="_target${targetPage}" value="Next" class="subbutton" onclick="checkTimeConflict()" />
			</c:if>
			
			<input type="submit" name="_finish" value="finish" class="subbutton" onclick="checkTimeConflict()" />
		</p>
		
		<input type="hidden" name="_page" value="${currentPage}" />
		
		<input type="hidden" name="quarterCode" value="${quarterPlan.quarter.code}" />
		<form:hidden path="quarter" />
	</form:form>	

<%@ include file="/WEB-INF/views/include/footer.jspf"%>