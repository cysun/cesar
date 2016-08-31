<%@ include file="/WEB-INF/views/include/taglib.jspf"%>
<c:set var="headElements">
	<title>CESAR Register</title>
	<script type="text/javascript">
	/* <![CDATA[ */

	$().ready(function() {
		// validate signup form on keyup and submit
		$("#signupForm").validate({
			rules: {
				username: {
					required: true,
					minlength: 2
				},
				password1: {
					required: true,
					minlength: 5
				},
				password2: {
					required: true,
					minlength: 5,
					equalTo: "#password1"
				}
			},
			messages: {

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
				}
			}
		});

	});
	/* ]]> */
	</script>
</c:set>
<%@ include file="/WEB-INF/views/include/header.jspf"%>
<p>Please fill out the following information.</p>
<form:form id="signupForm" modelAttribute="user">
	<form:hidden path="id" />
	<table class="usertable shadow" cellpadding="1.5em" cellspacing="0">
		<tr>
			<th>* Username</th>
			<td><form:input path="username" size="40" />
				<c:if test="${not empty usernameError}" >
					<p class="error">${usernameError }</p>
				</c:if>
			</td>
		</tr>
		
		<tr>
			<th>* Password</th>
			<td><form:password path="password1" size="40" /></td>
		</tr>

		<tr>
			<th>* Repeat Password</th>
			<td><form:password path="password2" size="40" /></td>
		</tr>
		
		<tr>
			<th style="text-align:center;" colspan="2">
				<input type="hidden" value="2" name="_page" />
				<input type="submit" name="_finish" value="Register" />
			</th>
		</tr>
	</table>
</form:form>

<%@ include file="/WEB-INF/views/include/footer.jspf"%>