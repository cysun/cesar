<%@ include file="/WEB-INF/views/include/taglib.jspf"%>
<c:set var="headElements">
	<title>CESAR Register</title>
	<script type="text/javascript">
	/* <![CDATA[ */
	$(function() {
	    $('#birthday').datepicker({
	        inline: true,
	        changeMonth: true,
	        changeYear: true,
	        yearRange: '-60:+00'
	    });
	    
	    $("#registerTable tr:odd").css("background-color","#F0F0F6");
	});
	$().ready(function() {
		// validate the comment form when it is submitted
		$("#commentForm").validate();
		$("input.phone").mask("(999) 999-9999");
		$("input.zipcode").mask("99999");
		// validate signup form on keyup and submit
		$("#signupForm").validate({
			rules: {
				firstname: "required",
				lastname: "required",
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
				firstname: "<br>Please enter firstname",
				lastname: "<br>Please enter lastname",
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
<p>Please fill out the following information.</p>
<form:form id="signupForm" modelAttribute="user">
	<form:hidden path="cin"/>
	<table class="usertable shadow" id="registerTable" cellpadding="1.5em" cellspacing="0">
		<tr>
			<th>CIN</th>
			<td>${user.cin}</td>
		</tr>
		
		<tr>
			<th>*First Name</th>
			<td><form:input class="required firstName" path="firstName" size="40"/></td>
		</tr>
		
		<tr>
			<th>Middle Name</th>
			<td><form:input path="middleName" size="40"/></td>
		</tr>
		
		<tr>
			<th>*Last Name</th>
			<td><form:input class="required lastName" path="lastName" size="40"/></td>
		</tr>
		
		<tr>
			<th>Gender</th>
			<td><form:select path="gender">
				<form:option value="M">Male</form:option>
				<form:option value="F">Female</form:option>
			</form:select></td>
		</tr>
		
		<tr>
			<th>Birthday (mm/dd/yyyy)</th>
			<td><form:input path="birthday" size="40"/></td>
		</tr>
		
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
			<th>* Email</th>
			<td><form:input path="email" size="40" /></td>
		</tr>
		
		<tr>
			<th>Address1</th>
			<td><form:input path="address1" size="40" /></td>
		</tr>

		<tr>
			<th>Address2</th>
			<td><form:input path="address2" size="40" /></td>
		</tr>

		<tr>
			<th>City</th>
			<td><form:input path="city" size="40" /></td>
		</tr>

		<tr>
			<th>State</th>
			<td><form:input path="state" size="40" /></td>
		</tr>

		<tr>
			<th>Zip</th>
			<td><form:input class="zipcode" path="zip" size="40" /></td>
		</tr>

		<tr>
			<th>Country</th>
			<td><form:input path="country" size="40" /></td>
		</tr>

		<tr>
			<th>Home Phone</th>
			<td><form:input class="phone" path="homePhone" size="40" /></td>
		</tr>

		<tr>
			<th>Office Phone</th>
			<td><form:input class="phone" path="officePhone" size="40" /></td>
		</tr>

		<tr>
			<th>Cell Phone</th>
			<td><form:input class="phone" path="cellPhone" size="40" /></td>
		</tr>

		<tr>
			<th style="text-align:center;" colspan="2">
				<input type="hidden" value="1" name="_page" />

				<input type="submit" name="_finish" value="Register" />

			</th>
		</tr>
	</table>

</form:form>
<%@ include file="/WEB-INF/views/include/footer.jspf"%>