<?xml version="1.0" encoding="iso-8859-1"?>
<%@ include file="/WEB-INF/views/include/taglib.jspf"%>
<c:set var="headElements">
<title>CESAR-Advisors Table</title>
<script type="text/javascript">
/* <![CDATA[ */
function removeStaff( id )
{
    var msg = "Are you sure you want to remove this staff?";
    if( confirm(msg) )
        window.location.href = "removeStaff?id=" + id;
}
/* ]]> */
</script>
</c:set>
<%@ include file="/WEB-INF/views/include/header.jspf"%>

<h3>Staff</h3>
<br />
<display:table
		class="tablesorter shadow" name="${staffs}" uid="staff" pagesize="40"
		requestURI="displayStaffsTable.html" export="true">
		
		<display:setProperty name="paging.banner.item_name" value="staff" />
  		<display:setProperty name="paging.banner.items_name" value="staff" />

        <display:column title="Name" sortable="true">
            ${staff.lastName},
            ${staff.firstName}
        </display:column>

		<display:column title="Username" sortable="true">
		${staff.username}
		</display:column>

  <security:authorize access="hasAnyRole('ROLE_STAFF')">
    <display:column>
      <a href="javascript:removeStaff(${staff.id})">Remove</a>
    </display:column>
  </security:authorize> 
	
	</display:table>

<%@ include file="/WEB-INF/views/include/footer.jspf"%>
