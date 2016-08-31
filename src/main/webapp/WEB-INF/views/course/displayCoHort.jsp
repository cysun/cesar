<?xml version="1.0" encoding="iso-8859-1"?>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/WEB-INF/views/include/taglib.jspf"%>
<c:set var="headElements">
<title>Co-Hort</title>
<script type="text/javascript">
/* <![CDATA[ */
$(function() {
	
    $('#searchAdvisorInput').autocomplete({
        source: 'ajaxAdvisorSearch.html',
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
<h4>Search Cohort by Advisor Name</h4>

<div style="width:100%;height:30px;position:relative;">
<form action="displayCoHort.html" method="get">
	<p>
		<c:if test="${directory == 1}" >
		<br />
		</c:if>
		<c:if test="${directory == 2}" >
		<select title="Advisors List" name="term" id="term" >
			<option value="Evelyn Crosby">Evelyn Crosby</option>
			<option value="Frances Hidalgo-Segura">Frances Hidalgo-Segura</option>
			<option value="Thelma Federico">Thelma Federico</option>
			<option value="Jose Sanchez">Jose Sanchez</option>
			<option value="Ruben Guardado">Ruben Guardado</option>
			<option value="Sarah Manuel">Sarah Manuel</option>
		</select>
		<!-- input id="searchAdvisorInput" type="text" name="term" size="30" /> -->
		<input type="submit" value="Submit" />
		</c:if>
	</p>
</form>
</div>


<c:set var="now" value="<%=new java.util.Date()%>" />
<c:if test="${not empty users}">
	<h4>Advisor: ${advisor.firstName }  ${advisor.lastName }</h4>
	
	<form id="usersForm" method="get" action="#"><display:table
		class="tablesorter shadow" name="${users}" uid="user" pagesize="40"
		requestURI="displayCoHort.html">
		
		<display:setProperty name="paging.banner.item_name" value="user" />
  		<display:setProperty name="paging.banner.items_name" value="users" />

		<display:column title="Name" style="width:175px;">
		<a href="<c:url value='/user/display.html?userId=${user.id}'/>">${user.lastName},
			${user.firstName}</a>
		</display:column>
		
		<display:column title="CIN" style="width:125px;">${user.cin}</display:column>
		
		<display:column title="Major" style="width:75px;">${user.major.symbol}</display:column>
		
		<display:column title="Class Standing" style="width:125px;">
		<c:if test="${not empty user.quarterAdmitted.code}" >
			<fmt:parseNumber var="qAdmitted" integerOnly="true" type="number" value="${user.quarterAdmitted.code / 10 + 1800}" />
			<fmt:formatDate var="year" value="${now}" pattern="yyyy" />
			<c:if test="${year - qAdmitted == 1}" >
				Freshmen
			</c:if>
			<c:if test="${year - qAdmitted == 2}" >
				Sophomore
			</c:if>
			<c:if test="${year - qAdmitted == 3}" >
				Junior
			</c:if>
			<c:if test="${year - qAdmitted == 4}" >
				Senior
			</c:if>
		</c:if>
		</display:column>
		
		<display:column title="Last Visit" style="width:125px;">
			<c:if test="${not empty user.lastAppointment}" >
				<fmt:formatDate var="date" value="${user.lastAppointment.startTime }" pattern="yyyy-MM-dd" />${date }
			</c:if>
		</display:column>
		
		<display:column title="Reason for Visit" style="width:225px;"> 
			<c:if test="${not empty user.lastAppointment}" >
				${user.lastAppointment.reasonForAppointment.name }
			</c:if>
		</display:column>
		
		
		<display:column title="Appointments" style="text-align: center; white-space: nowrap; width:225px;">
			<c:if test="${not empty user.futureAppointment}" > 
					<a href="<c:url value='/appointment/student/viewAppointment.html?studentId=${user.id }'/>"><font color="red">Future Appointments</font></a>
			</c:if>
		</display:column>

		<display:column style="text-align: center; white-space: nowrap; width:75px;">
			<a href="<c:url value='/user/edit/account.html?userId=${user.id }'/>">Edit</a>	
		</display:column>

	</display:table></form>

</c:if>

<%@ include file="/WEB-INF/views/include/footer.jspf"%>