<?xml version="1.0" encoding="iso-8859-1"?>
<%@ include file="/WEB-INF/views/include/taglib.jspf"%>
<c:set var="headElements">
  <title>Cesar - Stored Queries</title>
	  <script type="text/javascript">
		/* <![CDATA[ */
		function cloneQuery()
		{
		  var queryId = "none";
		  var elements = document.getElementsByName( "queryId" );
		  for( var i=0 ; i < elements.length ; ++i )
		    if( elements[i].checked )
		    {
		      queryId = elements[i].value;
		      break;
		    }
		  
		  if( queryId == "none" )
		    alert( "Please select the query you want to clone." );
		  else
			  window.location.href= 'createStoredQuery.html?queryId=' + queryId;
		}
		/* ]]> */
	 </script>
</c:set>
<%@ include file="/WEB-INF/views/include/header.jspf"%>

<h2>Stored Queries</h2>

<p>
  <a href="createStoredQuery.html">Create</a> |
  <a href="#" onclick="cloneQuery();">Clone</a>
</p>

<display:table name="${queries}" uid="query" class="usertable shadow" requestURI="viewStoredQueries.html" pagesize="40">
<display:setProperty name="paging.banner.item_name" value="query" />
<display:setProperty name="paging.banner.items_name" value="queries" />
<display:setProperty name="paging.banner.onepage" value="" />
  <display:column>
    <input name="queryId" type="radio" value="${query.id}" />
  </display:column>
  <display:column title="Name" sortProperty="name" sortable="true">
    <a href="viewStoredQuery.html?queryId=${query.id}">${query.name}</a>
  </display:column>
  <display:column title="Author" sortable="true" sortProperty="author.username" >
    ${query.author.username}
  </display:column>
  <display:column title="Date" sortable="true">
    <fmt:formatDate value="${query.date}" pattern="yyyy-MM-dd" />
  </display:column>
  <display:column>
    <c:if test="${query.enabled}">
    <a href="runStoredQuery.html?queryId=${query.id}">Run</a>
    </c:if>
  </display:column>
</display:table>

<%@ include file="/WEB-INF/views/include/footer.jspf"%>
