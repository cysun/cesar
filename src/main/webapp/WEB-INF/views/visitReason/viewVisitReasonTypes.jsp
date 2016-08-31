<?xml version="1.0" encoding="iso-8859-1"?>
<%@ include file="/WEB-INF/views/include/taglib.jspf"%>
<c:set var="headElements">
	<title>Add A Visit Reason Type</title>
	<script type="text/javascript">
	/* <![CDATA[ */
	          $(function() {
	             $(".usertable tbody tr:odd").css("background-color","#F0F0F6");

	             $('#addVisitReasonTypeForm').hide();
	             $('#addVisitReasonTypeLink').click( function() {
	                 $('#addVisitReasonTypeForm').toggle();
	             });
	             
	          });
	/* ]]> */
	</script>	             
</c:set>
<%@ include file="/WEB-INF/views/include/header.jspf"%>
<h3><a href="<c:url value='/visitReason/addVisitReason.html' />">Add A Visit Reason</a> | Add A Visit Reason Type
</h3>

<p><a id="addVisitReasonTypeLink" href="javascript:void(0)">Add a visit reason type</a></p>
<form id="addVisitReasonTypeForm" action="/cesar/visitReason/addVisitReasonType.html" method="post">
		 <p>Visit Reason type: <input type="text" name="visitReasonTypeName"/></p>
		  <p><input type="submit" name="submit" value="Save" /></p>
</form>
<table class="tablesorter shadow" cellspacing="2" cellpadding="5" style="width: 100%;">
<thead>
  <tr><th>Type</th><th></th></tr>
</thead>
<tbody>
  <c:forEach items="${visitReasonTypes}" var="visitReasonType">
  <tr>
    <td>${visitReasonType.name}</td>
    <td>
	  <a href="<c:url value='/visitReason/deleteVisitReasonType.html?id=${visitReasonType.id}' />">Delete</a>
	</td>
  </tr>
  </c:forEach>
</tbody>
</table>
<%@ include file="/WEB-INF/views/include/footer.jspf"%>