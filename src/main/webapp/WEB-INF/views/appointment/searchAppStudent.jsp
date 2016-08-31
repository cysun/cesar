<?xml version="1.0" encoding="iso-8859-1"?>
<%@ include file="/WEB-INF/views/include/taglib.jspf"%>
<c:set var="headElements">
<title>Search</title>
<script type="text/javascript">
/* <![CDATA[ */
$(function() {
	
    $('#searchStudentInput').autocomplete({
        source: 'ajaxSearch.html',
        select: function(event, ui) {
            if( ui.item )
            	window.location.href='/cesar/appointment/makeAppointment.html?studentId=' + ui.item.userId;
        }
    });

       
    $("table").tablesorter({
		// sort on the second column
		sortList: [[1,0]],widgets: ['zebra'],
		headers: { 
            //(we start counting zero) 
            2: { 
                // disable it by setting the property sorter to false 
                sorter: false 
            }, 
            // assign the third column (we start counting zero) 
            3: { 
                // disable it by setting the property sorter to false 
                sorter: false 
            },
            4: { 
                // disable it by setting the property sorter to false 
                sorter: false 
            } 
            
        } 
	}); 
});
/* ]]> */
</script>
</c:set>
<%@ include file="/WEB-INF/views/include/header.jspf"%>

<h4>
Search A Student For Appointments 
</h4>


<form action="searchStudentForApp.html" method="get">
	<p>
		<input id="searchStudentInput" type="text" name="term" size="30" /> 
		<input type="submit" name="search" value="Search" />
	</p>
</form>

<c:if test="${not empty users}">
	

	<form id="usersForm" method="get" action="#"><display:table
		class="tablesorter shadow" name="${users}" uid="user" pagesize="40"
		requestURI="search.html">
		
		<display:setProperty name="paging.banner.item_name" value="user" />
  		<display:setProperty name="paging.banner.items_name" value="users" />

		<display:column title="CIN">${user.cin}</display:column>
		
		<display:column title="Name">
		<a href="<c:url value='/user/display.html?userId=${user.id}'/>">${user.lastName},
				${user.firstName}</a>
		</display:column>
		
		<display:column title="Advisor">${user.currentAdvisor.name}</display:column>
	
		<display:column title="View Appointments">
		<a href="<c:url value='/appointment/student/viewAppointment.html?studentId=${user.id }'/>">
				Scheduled Appointments</a>
		</display:column>

		<display:column style="text-align: center; white-space: nowrap;">
		<a href="<c:url value='/appointment/makeAppointment.html?studentId=${user.id }'/>">Make An Appointment</a>
		</display:column>

	</display:table></form>

</c:if>

<%@ include file="/WEB-INF/views/include/footer.jspf"%>