<?xml version="1.0" encoding="iso-8859-1"?>
<%@ include file="/WEB-INF/views/include/taglib.jspf"%>
<c:set var="headElements">
  <title>Cesar - Stored Query Results</title>
    <script type="text/javascript">
  /* <![CDATA[ */
	$(function() {
		$( "#okButton" ).click( function(){
	    	var url = 'viewStoredQueries.html';
	    	window.location.href = url;
	    	return false;
	    });
	    
	});
	/* ]]> */
  </script>
</c:set>
<%@ include file="/WEB-INF/views/include/header.jspf"%>

<h3>
<a href="viewStoredQueries.html">Stored Queries</a> &gt;
<a href="viewStoredQuery.html?queryId=${query.id}"><c:out value="${query.name}" escapeXml="true" /></a>
</h3>

<div style="margin-bottom: 1em;">
<a href="plotStoredQuery.html?queryId=${query.id}">
<img style="width: 400px; height: 300px; border: none;" alt="[Qery Result Chart]"
   title="Click to See the Full Image"
   src="plotStoredQuery.html?queryId=${query.id}" /></a>
</div>

<table class="usertable shadow">
<tr>
	<th><br /></th>
	<c:forEach items="${result.colLabels}" var="colLabel">
		<th>${colLabel}</th>
	</c:forEach>
</tr>
<c:forEach items="${result.data}" var="row" varStatus="status">
<tr>
  <td style="background-color: #ffffc0">${result.rowLabels[status.index]}</td>
  <c:forEach items="${row}" var="value">
  <td style="text-align: center;">
    <fmt:formatNumber minFractionDigits="2" maxFractionDigits="2" value="${value}" />
  </td>
  </c:forEach>
</tr>
</c:forEach>
</table>

<br />

<input id="okButton" type="button" value="OK" />

<%@ include file="/WEB-INF/views/include/footer.jspf"%>
