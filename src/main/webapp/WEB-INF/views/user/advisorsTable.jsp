<?xml version="1.0" encoding="iso-8859-1"?>
<%@ include file="/WEB-INF/views/include/taglib.jspf"%>
<c:set var="headElements">
<title>CESAR-Advisors Table</title>
<script type="text/javascript">
/* <![CDATA[ */
function removeAdvisor( id )
{
    var msg = "Are you sure you want to remove this advisor?";
    if( confirm(msg) )
        window.location.href = "removeAdvisor?id=" + id;
}
/* ]]> */
</script>
</c:set>
<%@ include file="/WEB-INF/views/include/header.jspf"%>

<h3>Advisors</h3>
<br />
<display:table
		class="tablesorter shadow" name="${advisors}" uid="advisor" pagesize="40"
		requestURI="displayAdvisorsTable.html" export="true">
		
		<display:setProperty name="paging.banner.item_name" value="advisor" />
  		<display:setProperty name="paging.banner.items_name" value="advisors" />

        <display:column title="Name" sortable="true">
            ${advisor.lastName},
            ${advisor.firstName}
        </display:column>

		<display:column title="Username" sortable="true">
		${advisor.username}
		</display:column>

  <security:authorize access="hasAnyRole('ROLE_STAFF')">
    <display:column>
      <a href="javascript:removeAdvisor(${advisor.id})">Remove</a>
    </display:column>
  </security:authorize>	
</display:table>

<%@ include file="/WEB-INF/views/include/footer.jspf"%>
