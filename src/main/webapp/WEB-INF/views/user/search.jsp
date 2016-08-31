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
            	window.location.href='/cesar/user/display.html?userId=' + ui.item.userId;
        }
    });
    $('#searchAdvisorInput').autocomplete({
        source: 'ajaxSearch.html',
        select: function(event, ui) {
            if( ui.item )
            	window.location.href='/cesar/user/display.html?userId=' + ui.item.userId;
        }
    });
    $('#searchStaffInput').autocomplete({
        source: 'ajaxSearch.html',
        select: function(event, ui) {
            if( ui.item )
            	window.location.href='/cesar/user/display.html?userId=' + ui.item.userId;
        }
    });
    $('#searchUserInput').autocomplete({
        source: 'ajaxSearch.html',
        select: function(event, ui) {
            if( ui.item )
            	window.location.href='/cesar/user/display.html?userId=' + ui.item.userId;
        }
    });

    $('#selectAll').toggle(
	    function() { $(':checkbox[name="userId"]').attr('checked',true); },
	    function() { $(':checkbox[name="userId"]').attr('checked',false); }
    );
	
    $('#email').click( function() {
        if( $(':checkbox[name="userId"]:checked').length == 0 )
            alert( 'Please select the user(s) to contact.' );
        else
            $('#usersForm').attr('action', '<c:url value="/user/email.html" />').submit();
	});
    
    $("table").tablesorter({
		// sort on the second column
		sortList: [[1,0]],widgets: ['zebra'],
		headers: { 
            //(we start counting zero) 
            0: { 
                // disable it by setting the property sorter to false 
                sorter: false 
            }, 
            3: { 
                // disable it by setting the property sorter to false 
                sorter: false 
            }, 
            // assign the third column (we start counting zero) 
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
<c:if test="${directory == 0}" >
<h3>Students</h3>
</c:if>
<c:if test="${directory == 1}" >
<h3>Advisors</h3>
</c:if>
<c:if test="${directory == 2}" >
<h3>Staffs</h3>
</c:if>
<c:if test="${directory == 3}" >
<h3>Users</h3>
</c:if>
<h4>
Search 
<security:authorize access="hasAnyRole('ROLE_STAFF')">
| <a href="<c:url value='/user/add.html' />">Add</a>
| <a href="<c:url value='/user/importUsers.html' />">Import</a>
</security:authorize>
</h4>

<form action="search.html" method="get">
	<p>
		<c:if test="${directory == 0}" >
		<input id="searchStudentInput" type="text" name="term" size="30" /> 
		</c:if>
		<c:if test="${directory == 1}" >
		<input id="searchAdvisorInput" type="text" name="term" size="30" />
		</c:if>
		<c:if test="${directory == 2}" >
		<input id="searchStaffInput" type="text" name="term" size="30" />
		</c:if>
		<c:if test="${directory == 3}" >
		<input id="searchUserInput" type="text" name="term" size="30" />
		</c:if>
		<input type="submit" name="search" value="Search" />
	</p>
</form>




<c:if test="${not empty users}">
	<p><a id="email" href="javascript:void(0)">Email</a></p>

	<form id="usersForm" method="get" action="#"><display:table
		class="tablesorter shadow" name="${users}" uid="user" pagesize="40"
		requestURI="search.html">
		
		<display:setProperty name="paging.banner.item_name" value="user" />
  		<display:setProperty name="paging.banner.items_name" value="users" />

		<display:column title='<input type="checkbox" id="selectAll" />'
			sortable="false" media="html" style="width: 1%; text-align: center;">
			<input type="checkbox" name="userId" value="${user.id}" />
		</display:column>

		<display:column title="CIN">${user.cin}</display:column>
		<display:column title="Name">
		
		<a href="<c:url value='/user/display.html?userId=${user.id}'/>">${user.lastName},
			${user.firstName}</a>

		</display:column>
		<display:column title="Email">
			<a href="<c:url value='/user/email.html?userId=${user.id}' />">${user.email}</a>
		</display:column>

		<display:column style="text-align: center; white-space: nowrap;">
			<a href="<c:url value='/user/edit/account.html?userId=${user.id }'/>">Edit</a>	
		</display:column>

	</display:table></form>

</c:if>

<%@ include file="/WEB-INF/views/include/footer.jspf"%>