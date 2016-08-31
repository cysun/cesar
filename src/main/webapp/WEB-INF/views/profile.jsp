<?xml version="1.0" encoding="iso-8859-1"?>
<%@ include file="/WEB-INF/views/include/taglib.jspf"%>
<c:set var="headElements">
<title>CESAR-Display</title>
<script type="text/javascript">
/* <![CDATA[ */
	$(function() {
		$( "#tabs" ).tabs();
		$(".usertable tbody tr:odd").css("background-color","#F0F0F6");
		
		$('#addFinancialAidForm').hide();

	    $('#addFinancialAidLink').click( function() {
	        $('#addFinancialAidForm').toggle();
	    });
	    
	    $('#addExtraCurriculumForm').hide();

	    $('#addExtraCurriculumLink').click( function() {
	        $('#addExtraCurriculumForm').toggle();
	    });
	    
	    $('#addCourseForm').hide();
	    
	    $('#addCourseLink').click( function(){
	    	$('#addCourseForm').toggle();
	    });
	    
	 	$('#editLink').click( function() {
	        var editTypes = [ 'account','account', 'account', 'contact', 'demographic',
	                          'program', 'highSchool', 'tests',"account","account" ];
	        var editPath = '<c:url value="/profile/edit/" />' +
	                       editTypes[$('#tabs').tabs("option", "selected")] +
	                       '.html';
	        window.location.href = editPath;
	    });
	 	
	 	<c:forEach items="${quarters}" var="q">   
		var key = '<c:out value="${q.key}"/>';
		var value = '<c:out value="${q.value}"/>';
			$("#quarterCode").append(

	            $("<option></option>")
	                .attr("value", key)
	                .text(value)

	    	);
		</c:forEach>

		var currrentQuarter = "${currentQuarter}";
	    $("select[name='quarterCode'] option").each(function(){
	        if( $(this).val() == currrentQuarter) 
	            $(this).attr('selected', true);
	    });
	    $("select[name='quarterCode']").change(function(){
	    	var userId = "${user.id}";
	        var quarter = $("select[name='quarterCode'] option:selected").val();
	        window.location.href = "/cesar/user/displayEnrollment.html?userId=" + userId + "&quarterCode=" + quarter + "&forProfile=true";
	    });

	});
/* ]]> */
</script>
</c:set>
<%@ include file="/WEB-INF/views/include/header.jspf"%>
<h3>Profile</h3>
<h4>
	<a href="<c:url value='/'/>">Home</a> >
	${user.name} &nbsp;&nbsp;&nbsp; 
 	[<a id="editLink" href="#">Edit</a>]

</h4>

<div id="tabs">
<ul>
	<li><a href="#tab-advisement">Advisement</a></li>
	<li><a href="#tab-courses">Courses</a></li>
	<li><a href="#tab-account">Account</a></li>
	<li><a href="#tab-contact">Contact</a></li>
	<li><a href="#tab-demographic">Demographic</a></li>
	<li><a href="#tab-program">Program</a></li>
	<li><a href="#tab-highSchool">High School</a></li>
	<li><a href="#tab-tests">Tests</a></li>
	<li><a href="#tab-enrollment">Enrollment</a></li>
	<li><a href="#tab-other">Other</a></li>
</ul>


	<div id="tab-advisement">
	
		<h3>Course Plan</h3>
		<p><a href="<c:url value='/coursePlan/addCoursePlanIntro.html' />">Add Course Plan</a></p>
		<c:if test="${fn:length(coursePlans) == 0 }">
		<p>No course plan saved</p>
		</c:if>
		<c:if test="${fn:length(coursePlans)>0 }">
			<table class="tablesorter shadow" cellspacing="2" cellpadding="5" style="width: 55em;">
				<thead>
				  <tr><th>Name</th><th>Approved by</th><th>Approved date</th><th>Edit</th><th>Delete</th>
				  	
				  </tr>
				</thead>
				<tbody>
				  <c:forEach items="${coursePlans}" var="coursePlan">
				  <tr style="background-color: #F0F0F6; font-size: 14px;">
				    <td><a href="<c:url value='/profile/coursePlan/view/viewCoursePlan.html?coursePlanId=${coursePlan.id}' />">
				    ${coursePlan.name }</a></td>
				    <td>${coursePlan.advisor}</td>
				   	<td><fmt:formatDate value="${coursePlan.approvedDate}" pattern="yyyy-MM-dd" /></td>
				   <c:if test="${not coursePlan.approved}" >
				  		<td>
					 		<a href="<c:url value='/coursePlan/editCoursePlan.html?coursePlanId=${coursePlan.id}' />">Edit</a>
						</td>
						<td>
						 	<a href="<c:url value='/coursePlan/deleteCoursePlan.html?coursePlanId=${coursePlan.id}' />">Delete</a>
						</td>
					</c:if>
					<c:if test="${coursePlan.approved}" >
						<td></td>
						<td></td>
					</c:if>
				  </tr>
				  
				  </c:forEach>
				</tbody>
			</table>
		</c:if>
		
		<h3> Advisement </h3>
		<c:if test="${fn:length(advisements) == 0}">
		<p>No advisement information on record.</p>
		</c:if>
		
		<c:if test="${fn:length(advisements) > 0}">
			<table cellspacing="2" cellpadding="5" style="width: 55em;">
				<c:forEach items="${advisements}" var="advisement">
				  <tr style="background-color: #F0F0F6; font-size: 14px;">
				    <td>
				      ${advisement.advisor.name}
				      <fmt:formatDate value="${advisement.createDate}" pattern="yyyy-MM-dd HH:mm:ss" />
				      <c:if test="${not empty advisement.editedBy}">, edited by ${advisement.editedBy.name} at
				      <fmt:formatDate value="${advisement.editDate}" pattern="yyyy-MM-dd HH:mm:ss" />      
				      </c:if>
				    </td>
				  </tr>
				  <tr>
				    <td colspan="2">
				      <p><c:out value="${advisement.comment}" /></p>
				    </td>
				  </tr>
				</c:forEach>
			</table>
		</c:if>
	</div>
	
	<div id="tab-courses">
		<h3>Courses Taken</h3>
	
		<c:if test="${fn:length(courses) == 0}">
		<p>No course information on record.</p>
		</c:if>

		<p><a id="addCourseLink" href="javascript:void(0)">Add Course</a></p>
		<form id="addCourseForm" action="/cesar/addCourseTaken.html" method="post">
			<table class="usertable" cellpadding="1.5em" cellspacing="0">
				<tr>
					<td>Course</td> 
					<td>
						<input type="text" name="courseCode" />
						<c:if test="${courseTakenError!=null}">
			  			<br/>
			  			<p style="color:red">${courseTakenError }</p>
		  			</c:if>
					</td>
				</tr>
				<tr>
					<td>Grade</td>
					<td>
						<select name="grade">
							<option value="A">A</option>
							<option value="A-">A-</option>
							<option value="B+">B+</option>
							<option value="B">B</option>
							<option value="B-">B-</option>
							<option value="C+">C+</option>
							<option value="C">C</option>
							<option value="C-">C-</option>
							<option value="D+">D+</option>
							<option value="D">D</option>
							<option value="D-">D-</option>
							<option value="F">F</option>
							<option value="CR">CR</option>
							<option value="NC">NC</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>Term</td>
					<td>
						<select name="quarter">
							<option value="Fall">Fall</option>
							<option value="Winter">Winter</option>
							<option value="Spring">Spring</option>
							<option value="Summer">Summer</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>Year</td>
					<td>
						<input type="text" name="year"></input>
					</td>
				</tr>
				
				<tr>
					<td style="text-align:center;" colspan="2"><input type="submit" name="save" value="Save" /></td>
				</tr>
			</table>
			<input type="hidden" name="forProfile" value="true" />
			<input type="hidden" name="userId" value="${user.id}" />
			
		</form>


		<c:if test="${fn:length(coursesTaken) == 0 }">
			No course information in record.
		</c:if>
		<c:if test="${fn:length(coursesTaken) > 0}">
			<table class="usertable" cellpadding="1.5em" cellspacing="0">
				<tr><th>Course</th><th>Grade</th><th>Date</th><th></th></tr>
				<c:forEach items="${coursesTaken}" var="courseTaken">
				  <tr> 
				    <td>
				    	${courseTaken.course.code}
				    </td>
				    <td>
				    	${courseTaken.grade}
				    </td>
				    <td>
				    	${courseTaken.quarter },${courseTaken.year }
				    </td>
				    <td>
				    	<a
				        href="<c:url value='/other/editCourseTaken.html?courseTakenId=' /> ${courseTaken.id}">Edit</a>
				    </td>
				  </tr>
				</c:forEach>
			</table>
		</c:if>	
		
		<h4>Courses Waived</h4>
		<c:if test="${fn:length(coursesWaived) == 0 }" >
			No course waived information in record.
		</c:if>
		<c:if test="${fn:length(coursesWaived) > 0}">
		
			<table class="usertable" cellpadding="1.5em" cellspacing="0">
				<tr>
				  <th>Course</th><th>Advisor</th><th>Date</th><th>Comment</th>
				</tr>
				<c:forEach items="${coursesWaived}" var="courseWaived">
					<tr>
						 <td>${courseWaived.course.code}</td>
						 <td>${courseWaived.advisor.firstName} ${courseWaived.advisor.lastName}</td>
						 <td><fmt:formatDate pattern="yyyy-MM-dd" value="${courseWaived.date}" /></td>
						 <td>${courseWaived.comment}</td>
						 
					</tr>
				</c:forEach>
			</table>
		</c:if>
		
		<h4>Courses Transferred</h4>
		<c:if test="${fn:length(coursesTransferred) == 0 }" >
			No course transferred information in record.
		</c:if>
		<c:if test="${fn:length(coursesTransferred) > 0}">
		
			<table class="usertable" cellpadding="1.5em" cellspacing="0">
				<tr>
				  <th>Course</th><th>Advisor</th><th>Date</th><th>Comment</th>
				</tr>
				<c:forEach items="${coursesTransferred}" var="courseTransferred">
					<tr>
						 <td>${courseTransferred.course.code}</td>
						 <td>${courseTransferred.advisor.name}</td>
						 <td><fmt:formatDate pattern="yyyy-MM-dd" value="${courseTransferred.date}" /></td>
						 <td>${courseTransferred.comment}</td>
						 
					</tr>
				</c:forEach>
			</table>
		</c:if>
		
	</div>

	<div id="tab-account">
	
		<table class="usertable" cellpadding="1.5em" cellspacing="0">
		
			<tr>
				<th>Name</th>
				<td>${user.firstName} ${user.middleName} ${user.lastName}</td>
			</tr>
			<tr>
				<th>Email</th>
				<td>${user.email}</td>
			</tr>
			<tr>
				<th>Username</th>
				<td>${user.username}</td>
			</tr>
		
			<tr>
				<th>CIN</th>
				<td>${user.cin}</td>
			</tr>
			
			<tr>
				<th>Advisor</th>
				<td>${user.currentAdvisor.name }</td>
			</tr>
		</table>
	
	</div>


	<div id="tab-contact">
	
		
		<table class="usertable" cellpadding="1.5em" cellspacing="0">
			<tr>
				<th>Address</th>
				<td>
					<c:if test="${not empty user.address1}">${user.address1} <br />
					</c:if> <c:if test="${not empty user.address2}">${user.address2} <br />
					</c:if> <c:if test="${not empty user.city}">${user.city} <br />
					</c:if> ${user.state} ${user.zip} ${user.country}
				</td>
			</tr>
			<tr>
				<th>Home Phone</th>
				<td>${user.homePhone}</td>
			</tr>
			<tr>
				<th>Office Phone</th>
				<td>${user.officePhone}</td>
			</tr>
			<tr>
				<th>Cell Phone</th>
				<td>${user.cellPhone}</td>
			</tr>
		</table>

	</div>


	<div id="tab-demographic">
	
		<table class="usertable" cellpadding="1.5em" cellspacing="0">
			<tr>
				<th>Gender</th>
				<td>
					<c:if test="${user.gender == 'M' }">Male</c:if> <c:if
					test="${user.gender == 'F' }">Female</c:if>
				</td>
			</tr>
			<tr>
				<th>Birthday (mm/dd/yyyy)</th>
				<td><fmt:formatDate pattern="MM/dd/yyyy" value="${user.birthday}" /></td>
			</tr>
		
			<tr>
				<th>Ethnicity</th>
				<td>${user.ethnicity.name}</td>
			</tr>
		
			<tr>
				<th>Work Hours Per Week</th>
				<td>${user.workHoursPerWeek}</td>
			</tr>
		
			<tr>
				<th>Commute Time In Minutes</th>
				<td>${user.commuteTimeInMinutes}</td>
			</tr>
		
			<tr>
			</tr>
		
		</table>
		
	</div>


	<div id="tab-program">
	
		<table class="usertable" cellpadding="1.5em" cellspacing="0">
			<tr>
				<th>Major</th>
				<td>${user.major.name}</td>
			</tr>
			<tr>
				<th>Term Admitted</th>
				<td>${user.quarterAdmitted}</td>
			</tr>
			<tr>
				<th>Expected Graduation Date</th>
				<td>
					<fmt:formatDate pattern="MM/dd/yyyy"
					value="${user.expectedGradudationDate}" />
				</td>
			</tr>
			<tr></tr>
		</table>
	</div>


	<div id="tab-highSchool">
	
		
		<table class="usertable" cellpadding="1.5em" cellspacing="0">
			<tr>
				<th>High School</th>
				<td>${user.highSchool.name}</td>
			</tr>
			<tr>
				<th>High School Physics</th>
				<td>${user.highSchoolPhysics}</td>
			</tr>
			<tr>
				<th>High School Chemistry</th>
				<td>${user.highSchoolChemistry}</td>
			</tr>
			<tr>
				<th>High School Calculus</th>
				<td>${user.highSchoolCalculus}</td>
			</tr>
			<tr>
				<th>High School Trigonometry</th>
				<td>${user.highSchoolTrigonometry}</td>
			</tr>
			<tr>
				<th>High School GPA</th>
				<td>${user.highSchoolGPA}</td>
			</tr>
			<tr>
				<th>Community College Courses</th>
				<td>${user.communityCollegeCourses}</td>
			</tr>
			<tr>
				<th>High School programs</th>
				<td>
					<ul>
						<c:forEach items="${user.highSchoolPrograms}" var="highSchoolProgram">
							<li>${highSchoolProgram.name}</li>
						</c:forEach>
					</ul>
				</td>
			</tr>
		</table>
	</div>


	<div id="tab-tests">
	
		
		<table class="usertable" cellpadding="1.5em" cellspacing="0">
			<tr>
				<th>ELM</th>
				<td>${user.elm}</td>
			</tr>
			<tr>
				<th>EPT</th>
				<td>${user.ept}</td>
			</tr>
			<tr>
				<th>SAT Math</th>
				<td>${user.satMath}</td>
			</tr>
			<tr>
				<th>SAT Verbal</th>
				<td>${user.satVerbal}</td>
			</tr>
			<tr>
				<th>ACT Math</th>
				<td>${user.actMath}</td>
			</tr>
			<tr>
				<th>ACT Verbal</th>
				<td>${user.actVerbal}</td>
			</tr>
			<tr>
				<th>EAP Math</th>
				<td>${user.eapMath}</td>
			</tr>
		
			<tr>
				<th>EAP Verbal</th>
				<td>${user.eapVerbal}</td>
			</tr>
		
			<tr>
				<th>AP Calculus A</th>
				<td>${user.apCalculusA}</td>
			</tr>
		
			<tr>
				<th>AP Calculus B</th>
				<td>${user.apCalculusB}</td>
			</tr>
		
			<tr>
				<th>AP Calculus C</th>
				<td>${user.apCalculusC}</td>
			</tr>
		
			<tr>
				<th>AP Physics</th>
				<td>${user.apPhysics}</td>
			</tr>
		
			<tr>
				<th>AP Chemistry</th>
				<td>${user.apChemistry}</td>
			</tr>
		
			<tr>
				<th>AP Biology</th>
				<td>${user.apBiology}</td>
			</tr>
		</table>
	</div>

	<div id="tab-enrollment">
		
		<h3>Enrollment</h3>

		<p><input type="hidden" name="userId" value="${user.id}" />
		  	<select name="quarterCode" id="quarterCode" style="width:20%;">
		  		<option value="">Select term</option>
			</select>
			<input type="hidden" name="forProfile" value="true" />
		</p>
		
		<c:if test="${fn:length(coursePlansMap) > 0 }">
		<form id="enrollmentForm">
			<table class="tablesorter shadow" cellspacing="2" cellpadding="5" style="width: 55em;">
				<thead>
				  <tr><th>Term</th><th>Course Plan</th><th>Section Enrolled</th></tr>
				</thead>
				<tbody>
				  <c:forEach items="${coursePlansMap}" var="entry">
				  		<tr style="background-color: #F0F0F6; font-size: 14px;">
				   			<td>${entry.value.quarter}</td>
				    		<td>${entry.key.name}</td>
				    		<td>
				    			<c:forEach items="${entry.value.sections}" var="section">
				    				<c:choose>
            							<c:when test="${fn:contains(enrolled, section)}">
                							<input type="checkbox" name="sections" value="${section.id }" checked disabled /> ${section.course.code }
                							<c:if test="${not empty section.option}">
                								-${section.option}
                							</c:if> <br />
            							</c:when>
            							<c:otherwise>
             	   							<input type="checkbox" name="sections" value="${section.id }" disabled /> ${section.course.code }
             	   							<c:if test="${not empty section.option}">
                								-${section.option}
                							</c:if> <br />
            							</c:otherwise>
        							</c:choose>
				    			</c:forEach>
				    		</td>
				  		</tr>
				  </c:forEach>
				</tbody>
			</table>
			<input type="hidden" name="userId" value="${user.id}" />
			<input type="hidden" name="quarterCode" value="${currentQuarter}" />
			<input type="hidden" name="forProfile" value="true" />
		</form>
		</c:if>
		
	</div>
	
	<div id="tab-other">
	
	<h3>Financial Aids</h3>
	
		<c:if test="${fn:length(financialAids) == 0}">
		<p>No financial aids information on record.</p>
		</c:if>

		<p><a id="addFinancialAidLink" href="javascript:void(0)">Add financial aid</a></p>
		<form id="addFinancialAidForm" action="/cesar/addFinancialAid.html" method="post">
			<p>Type: <select name="typeId">
						<c:forEach items="${financialAidTypes}" var="financialAidType">
							<option value="${financialAidType.id}">
				   					 			${financialAidType.name}
				   			</option>
						</c:forEach>
					</select>
			</p>
			<p>Details</p>
			<textarea name="details" rows="5" cols="80"></textarea>
			<p><input type="hidden" name="userId" value="${user.id}" />
				<input type="hidden" name="forProfile" value="true" />
			<input type="submit" name="submit" value="OK" /></p>
		</form>


		<c:if test="${fn:length(financialAids) > 0}">
			<table cellspacing="2" cellpadding="5" style="width: 55em;">
				<c:forEach items="${financialAids}" var="financialAid">
				  <tr style="background-color: #F0F0F6; font-size: 14px;"> 
				    <td>
				    	type: ${financialAid.type.name}
				    </td>
				    <td>
				    	<a href="<c:url value='/other/editFinancialAid.html?financialAidId=' /> ${financialAid.id}">Edit</a>
				    </td>
				  </tr>
				  
				  <tr>
				    <td colspan="2">
				      <p><c:out value="${financialAid.details}" /></p>
				    </td>
				  </tr>
				</c:forEach>
			</table>
		</c:if>	
	
	<h3>Extra Curriculum Activities</h3>
	
		<c:if test="${fn:length(extraCurriculums) == 0}">
			<p>No extra curriculums information on record.</p>
		</c:if>

		<p><a id="addExtraCurriculumLink" href="javascript:void(0)">Add Extra Curriculums</a></p>
		<form id="addExtraCurriculumForm" action="/cesar/addExtraCurriculums.html" method="post">
			<p>Type: <select name="typeId">
						<c:forEach items="${extraCurriculumTypes}" var="extraCurriculumType">
							<option value="${extraCurriculumType.id}">
				   					 			${extraCurriculumType.name}
				   			</option>
						</c:forEach>
					</select>
			</p>
			<p>Description</p>
			<textarea name="description" rows="5" cols="80"></textarea>
			<p><input type="hidden" name="userId" value="${user.id}" />
				<input type="hidden" name="forProfile" value="true" />
			<input type="submit" name="submit" value="OK" /></p>
		</form>


		<c:if test="${fn:length(extraCurriculums) > 0}">
			<table cellspacing="2" cellpadding="5" style="width: 55em;">
				<c:forEach items="${extraCurriculums}" var="extraCurriculum">
				  <tr style="background-color: #F0F0F6; font-size: 14px;">
				    <td>
				    	type: ${extraCurriculum.type.name}
				    </td>
				    <td>
				    	<a href="<c:url value='/other/editExtraCurriculum.html?extraCurriculumId=' /> ${extraCurriculum.id}">Edit</a>
				    </td>
				  </tr>
				  
				  <tr>
				    <td colspan="2">
				      <p><c:out value="${extraCurriculum.description}" /></p>
				    </td>
				  </tr>
				</c:forEach>
			</table>
		</c:if>
	</div>
	
</div>

<%@ include file="/WEB-INF/views/include/footer.jspf"%>