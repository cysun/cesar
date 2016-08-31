<?xml version="1.0" encoding="iso-8859-1"?>
<%@ include file="/WEB-INF/views/include/taglib.jspf"%>
<c:set var="headElements">
<title>CESAR - Edit Account</title>
<script type="text/javascript">
/* <![CDATA[ */
$(function() {
    $(".usertable tbody tr:odd").css("background-color","#F0F0F6");
});
$().ready(function() {
	// validate the comment form when it is submitted
	$("#commentForm").validate();
	
	// validate signup form on keyup and submit
	$("#signupForm").validate({
		rules: {
			firstName: "required",
			lastName: "required",
			cin:"required",
			username: {
				required: true,
				minlength: 2
			},
			password1: {
				
				minlength: 5
			},
			password2: {
				
				minlength: 5,
				equalTo: "#password1"
			},
			email: {
				required: true,
				email: true
			},
			topic: {
				required: "#newsletter:checked",
				minlength: 2
			},
			agree: "required"
		},
		messages: {
			firstName: "<br>Please enter firstname",
			lastName: "<br>Please enter lastname",
			cin: "<br>Please enter cin",
			username: {
				required: "<br>Please enter a username",
				minlength: "<br>Username must consist of at least 2 characters"
			},
			password1: {
				required: "<br>Please provide a password",
				minlength: "<br>Password must be at least 5 characters long"
			},
			password2: {
				required: "<br>Please provide a password",
				minlength: "<br>Password must be at least 5 characters long",
				equalTo: "<br>Please enter the same password as above"
			},
			email: "<br>Please enter a valid email address",
			agree: "Please accept our policy"
		}
	});
	
	// propose username by combining first- and lastname
	$("#username").focus(function() {
		var firstname = $("#firstname").val();
		var lastname = $("#lastname").val();
		if(firstname && lastname && !this.value) {
			this.value = firstname + "." + lastname;
		}
	});
	
	//code to hide topic selection, disable for demo
	var newsletter = $("#newsletter");
	// newsletter topics are optional, hide at first
	var inital = newsletter.is(":checked");
	var topics = $("#newsletter_topics")[inital ? "removeClass" : "addClass"]("gray");
	var topicInputs = topics.find("input").attr("disabled", !inital);
	// show when newsletter is checked
	newsletter.click(function() {
		topics[this.checked ? "removeClass" : "addClass"]("gray");
		topicInputs.attr("disabled", !this.checked);
	});
});
/* ]]> */
</script>
</c:set>
<%@ include file="/WEB-INF/views/include/header.jspf"%>

<form:form id="signupForm" modelAttribute="user">
	<form:hidden path="id" />
	<c:if test="${ not forProfile }">
		<c:if test="${ directory == 0 }">
			<h3>Students</h3>
			<h4>
				<a href="<c:url value='/user/search/student/search.html'/>">Search</a>	
		</c:if>
		
		<c:if test="${  directory == 1 }">
			<h3>Advisors</h3>
			<h4>
				<a href="<c:url value='/user/search/advisor/search.html'/>">Search</a>
		</c:if>
		
		<c:if test="${  directory == 2 }">
			<h3>Staffs</h3>
			<h4>
				<a href="<c:url value='/user/search/staff/search.html'/>">Search</a>
		</c:if>
		
		<c:if test="${  directory == 3 }">
			<h3>Users</h3>
			<h4>
				<a href="<c:url value='/user/search/user/search.html'/>">Search</a>
		</c:if>
			 &gt;
				<a href="<c:url value='/user/display.html?userId=${user.id }'/>">${user.firstName}
				${user.middleName} ${user.lastName}</a> &gt; Account
		</h4>
	</c:if>
	<c:if test="${ forProfile }">
		<h3>Profile</h3>
		<h4><a href="<c:url value='/'/>">Home</a> &gt;
		<a href="<c:url value='/profile.html'/>">${user.name}</a> &gt; Account</h4>
	</c:if>
	<table class="usertable shadow" cellpadding="1.5em" cellspacing="0">
		
		<c:if test="${forProfile }">
			<tr>
				<th>CIN</th>
				<td>${user.cin }</td>
			</tr>
	
			<tr>
				<th>Username</th>
				<td>${user.username }</td>
			</tr>
			
		</c:if>
		
		<tr>
			<th>*First Name</th>
			<td><form:input class="required firstName" path="firstName" size="40" /></td>
		</tr>

		<tr>
			<th>Middle Name</th>
			<td><form:input path="MiddleName" size="40" /></td>
		</tr>

		<tr>
			<th>*Last Name</th>
			<td><form:input class="required lastName" path="lastName" size="40"/></td>
		</tr>

		<tr>
			<th>*Email</th>
			<td>
				<form:input  path="email" size="40" />
			</td>
		</tr>

		

		<tr>
			<th>*Password</th>
			<td><form:password path="password1" size="40" /></td>
			
		</tr>
		
		<tr>
			<th>*Repeat Password</th>
			<td><form:password  path="password2" size="40" /></td>
		</tr>
		
		<c:if test="${not forProfile }">
			<tr>
				<th>*Username</th>
				<td><form:input path="username" size="40"/>
					<c:if test="${not empty usernameError}" >
						<p class="error">${usernameError }</p>
					</c:if>
				</td>
			</tr>
			
			<tr>
				<th>*CIN</th>
				<td><form:input path="cin" size="40" class="required"/>
					<c:if test="${not empty cinError }">
						<p class="error">${cinError }</p>
					</c:if>
				</td>
				
			</tr>
			<c:if test="${forStaffOnly }">
				<tr>
					<th>Roles</th>
					<td>
						<c:forEach items="${roles}" var="role">
							
					  		${role.name} <form:checkbox path="roles" value="${role}" />
					  		<br />
						  
						</c:forEach>
					</td>
				</tr>
			</c:if>
			<tr>
				<th>Advisor</th>
				<td><form:select path="currentAdvisor" items="${advisors}" itemValue="id" itemLabel="name" /></td>
			</tr>
			<c:if test="${forStaffOnly }">
				<tr>
					<th>Enabled</th>
					<td><form:checkbox path="enabled" cssStyle="width: auto;" /></td>
				</tr>
			</c:if>
		</c:if>
		
		<tr>
			<th style="text-align:center;" colspan="2"><input type="submit" name="save" value="Save" /></th>
		</tr>
		
	</table>
</form:form>
<%@ include file="/WEB-INF/views/include/footer.jspf"%>
