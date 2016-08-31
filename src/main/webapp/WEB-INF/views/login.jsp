<?xml version="1.0" encoding="iso-8859-1"?>
<%@ include file="/WEB-INF/views/include/taglib.jspf" %>
<c:set var="headElements">
  <title>CESAR Login Error</title>
</c:set>
<%@ include file="/WEB-INF/views/include/header.jspf" %>

<p> If you forgot your password, please provide your username or CIN or email below,
then click the Reset Password button. Your username and a new password will be sent
to the email address in your account profile.</p>

<form action="resetPassword.html" method="post">
Username: <input name="username" style="width: 10em;" /> or
CIN: <input name="cin" style="width: 10em;" /> or
Email: <input name="email" style="width: 10em;" />
<input name="reset" value="Reset Password" type="submit" class="subbutton" /> <br />
</form>

<%@ include file="/WEB-INF/views/include/footer.jspf"%>
