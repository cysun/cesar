<?xml version="1.0" encoding="iso-8859-1"?>
<%@ include file="/WEB-INF/views/include/taglib.jspf"%>
<c:set var="headElements">
  <title>Cesar - Create Stored Query</title>

	<script type="text/javascript">
	/* <![CDATA[ */
	          $(function() {
	             $(".usertable tbody tr:odd").css("background-color","#F0F0F6");
	             
	             $( "#stored_query_transpose_results_help_link" ).dialog({
	     			autoOpen: false,
	     			show: "blind",
	     			hide: "explode"
	     		});

	     		$( "#opener" ).click(function() {
	     			$( "#stored_query_transpose_results_help_link" ).dialog( "open" );
	     			return false;
	     		});
	          });
	/* ]]> */
	</script>	             
</c:set>
<%@ include file="/WEB-INF/views/include/header.jspf"%>

<h3>
<a href="<c:url value='/storedQuery/viewStoredQueries.html' />">Stored Queries</a>
&gt;
New Stored Query
</h3>

<form:form modelAttribute="query">

	<table class="usertable shadow" cellpadding="1.5em" cellspacing="0">
		<tr>
			<th>*Name:</th>
			<td><form:input path="name" size="80" /></td>
		</tr>
		<tr>	
			<th>*Query:</th>
			<td><form:textarea  rows="10" cols="80" path="query" size="80" /></td>
		</tr>
		
		<tr>
			<th style="text-align:center; font-size:18px; " colspan="2">Chart Option</th>
		</tr>
		<tr>
			<th>Title:</th>
			<td><form:input path="chartTitle" /></td>
		</tr>
		
		<tr>
			<th>X Axis Label:</th>
			<td><form:input path="chartXAxisLabel" /></td>
		</tr>
		<tr>
			<th>Y Axis Label:</th>
			<td><form:input path="chartYAxisLabel" /></td>
		</tr>
		<tr>
			<th>Transpose Results:</th>
			  <td>
			    <form:checkbox path="transposeResults" />
			    <a id="stored_query_transpose_results_help_link" href="#">Help</a>
			  </td>
		 </tr>
		 <tr>
			<th style="text-align:center;" colspan="2"><input type="submit" name="save" value="Save" /></th>
		</tr>
	</table>
</form:form>
<%@ include file="/WEB-INF/views/include/footer.jspf"%>
