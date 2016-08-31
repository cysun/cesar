<?xml version="1.0" encoding="iso-8859-1"?>
<%@ include file="/WEB-INF/views/include/taglib.jspf"%>
<c:set var="headElements">
	<title>Add Service Type</title>
	<script type="text/javascript">
	/* <![CDATA[ */
	          $(function() {
	             $(".usertable tbody tr:odd").css("background-color","#F0F0F6");

	             $('#addServiceTypeForm').hide();
	             $('#addServiceTypeLink').click( function() {
	                 $('#addServiceTypeForm').toggle();
	             });
	             
	          });
	/* ]]> */
	</script>	             
</c:set>
<%@ include file="/WEB-INF/views/include/header.jspf"%>
<h3><a href="<c:url value='/service/addService.html' />">Add A Service</a> | Add A Service Type
</h3>

<p><a id="addServiceTypeLink" href="javascript:void(0)">Add a service type</a></p>
<form id="addServiceTypeForm" action="/cesar/service/addServiceType.html" method="post">
		 <p>Service type: <input type="text" name="serviceTypeName"/></p>
		  <p><input type="submit" name="submit" value="Save" /></p>
</form>
<table class="tablesorter shadow" cellspacing="2" cellpadding="5" style="width: 100%;">
<thead>
  <tr><th>Type</th><th></th></tr>
</thead>
<tbody>
  <c:forEach items="${serviceTypes}" var="serviceType">
  <tr>
    <td>${serviceType.name}</td>
    <td>
	  <a href="<c:url value='/service/deleteServiceType.html?id=${serviceType.id}' />">Delete</a>
	</td>
  </tr>
  </c:forEach>
</tbody>
</table>
<%@ include file="/WEB-INF/views/include/footer.jspf"%>