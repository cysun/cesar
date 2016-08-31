<?xml version="1.0" encoding="iso-8859-1"?>
<%@ include file="/WEB-INF/views/include/taglib.jspf"%>
<c:set var="headElements">
  <title>Cesar - Stored Query</title>
  
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
<c:out value="${query.name}" escapeXml="true" /></h3>

<p>
<c:if test="${isAuthor || isAdmin}"><a href="editStoredQuery.html?queryId=${query.id}">Edit</a></c:if>
<c:if test="${(isAuthor || isAdmin) && query.enabled}">|</c:if>
<c:if test="${query.enabled}"><a href="runStoredQuery.html?queryId=${query.id}">Run</a></c:if>
</p>

<pre>${query.query}</pre>

<c:if test="${not query.enabled}">
<p><em>This query has not been reviewed and cannot be run.</em></p>
</c:if>

<input id="okButton" type="button" value="OK" />

<%@ include file="/WEB-INF/views/include/footer.jspf"%>

