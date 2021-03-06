<?xml version="1.0" encoding="iso-8859-1"?>
<%@ include file="/WEB-INF/views/include/taglib.jspf"%>
<c:set var="headElements">
	<title>Add No Seen Reason Type</title>
	<script type="text/javascript">
	/* <![CDATA[ */
	          $(function() {
	             $(".usertable tbody tr:odd").css("background-color","#F0F0F6");

	             $('#addNoSeenReasonTypeForm').hide();
	             $('#addNoSeenReasonTypeLink').click( function() {
	                 $('#addNoSeenReasonTypeForm').toggle();
	             });
	             
	          });
	/* ]]> */
	</script>	             
</c:set>
<%@ include file="/WEB-INF/views/include/header.jspf"%>
<h3><a href="<c:url value='/noSeenReason/addNoSeenReason.html' />">Add A No Seen Reason ( Walk In Appointment)</a> | Add A No Seen Reason Type
</h3>

<p><a id="addNoSeenReasonTypeLink" href="javascript:void(0)">Add a no seen reason type</a></p>
<form id="addNoSeenReasonTypeForm" action="/cesar/noSeenReason/addNoSeenReasonType.html" method="post">
		 <p>No Seen Reason type: <input type="text" name="noSeenReasonTypeName"/></p>
		  <p><input type="submit" name="submit" value="Save" /></p>
</form>
<table class="tablesorter shadow" cellspacing="2" cellpadding="5" style="width: 100%;">
<thead>
  <tr><th>Type</th><th></th></tr>
</thead>
<tbody>
  <c:forEach items="${noSeenReasonTypes}" var="noSeenReasonType">
  <tr>
    <td>${noSeenReasonType.name}</td>
    <td>
	  <a href="<c:url value='/noSeenReason/deleteNoSeenReasonType.html?id=${noSeenReasonType.id}' />">Delete</a>
	</td>
  </tr>
  </c:forEach>
</tbody>
</table>
<%@ include file="/WEB-INF/views/include/footer.jspf"%>