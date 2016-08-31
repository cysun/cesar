<?xml version="1.0" encoding="iso-8859-1"?>
<%@ include file="/WEB-INF/views/include/taglib.jspf"%>
<c:set var="headElements">
	<title>Add No Seen Reason</title>
	<script type="text/javascript">
	/* <![CDATA[ */
	          $(function() {
	             $(".usertable tbody tr:odd").css("background-color","#F0F0F6");

	          });
	/* ]]> */
	</script>	             
</c:set>
<%@ include file="/WEB-INF/views/include/header.jspf"%>
<h3>Add No Seen Reason ( Walk In Appointment)| <a href="<c:url value='/noSeenReason/viewNoSeenReasonTypes.html' />">Add A No Seen Reason Type</a>
</h3>

<form:form modelAttribute="noSeenReason">
  	<table class="usertable shadow" cellpadding="1.5em" cellspacing="0">
	  	<tr>
	  		<th>CIN:</th>
	  		<td> <input type="text" name="cin" /> <span style="color:red">${errorMessage}</span></td>
	  	</tr>
	  	
	  	<tr>
	  		<th>No Seen Reason Type:</th>
	  		<td><form:select path="noSeenReasonType" items="${noSeenReasonTypes}" itemValue="id" itemLabel="name" /></td>
	  	</tr>

  		<tr>
			<th style="text-align:center;" colspan="2"><input type="submit" name="save" value="Save" /></th>
		</tr>
	</table>
</form:form>

<%@ include file="/WEB-INF/views/include/footer.jspf"%>