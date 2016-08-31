<?xml version="1.0" encoding="iso-8859-1"?>
<%@ include file="/WEB-INF/views/include/taglib.jspf"%>
<c:set var="headElements">
	<title>Add Visit Reason</title>
	<script type="text/javascript">
	/* <![CDATA[ */
	          $(function() {
	             $(".usertable tbody tr:odd").css("background-color","#F0F0F6");

	          });
	/* ]]> */
	</script>	             
</c:set>
<%@ include file="/WEB-INF/views/include/header.jspf"%>
<h3>Add Visit Reason | <a href="<c:url value='/visitReason/viewVisitReasonTypes.html' />">Add A Visit Reason Type</a>
</h3>

<form:form modelAttribute="visitReason">
  	<table class="usertable shadow" cellpadding="1.5em" cellspacing="0">
	  	<tr>
	  		<th>CIN:</th>
	  		<td> <input type="text" name="cin" /> <span style="color:red">${errorMessage}</span></td>
	  	</tr>
	  	
	  	<tr>
	  		<th>Visit Reason Type:</th>
	  		<td><form:select path="visitReasonType" items="${visitReasonTypes}" itemValue="id" itemLabel="name" /></td>
	  	</tr>

  		<tr>
			<th style="text-align:center;" colspan="2"><input type="submit" name="save" value="Save" /></th>
		</tr>
	</table>
</form:form>

<%@ include file="/WEB-INF/views/include/footer.jspf"%>