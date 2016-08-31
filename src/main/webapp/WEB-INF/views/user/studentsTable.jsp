<?xml version="1.0" encoding="iso-8859-1"?>
<%@ include file="/WEB-INF/views/include/taglib.jspf"%>
<c:set var="headElements">
<title>CESAR-Students Table</title>
<script type="text/javascript">
/* <![CDATA[ */

</script>
</c:set>
<%@ include file="/WEB-INF/views/include/header.jspf"%>

<h3>Students</h3>

<br />
<display:table
		class="tablesorter shadow" name="${students}" uid="student" pagesize="40"
		requestURI="displayStudentsTable.html" export="true">
		
		<display:setProperty name="paging.banner.item_name" value="student" />
  		<display:setProperty name="paging.banner.items_name" value="students" />

        <display:column title="CIN" sortable="true">${student.cin}</display:column>
        <display:column title="Name" sortable="true">
            ${student.lastName},
            ${student.firstName}
        </display:column>
        <display:column title="Username" sortable="true">
        ${student.username}
        </display:column>
	
	</display:table>

<%@ include file="/WEB-INF/views/include/footer.jspf"%>