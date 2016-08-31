<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/WEB-INF/views/include/taglib.jspf" %>
<c:set var="headElements">
  <title>CESAR - Active Blocks</title>
  <style>
        select, input.text { margin-bottom:15px; width:42%; padding: .4em; }
        fieldset { padding:0; border:0; margin-top:25px; }
        h1 { font-size: 1.2em; margin: .6em 0; }
        div#users-contain { width: 450px; margin: 20px 0; }
        div#users-contain table { margin: 1em 0; border-collapse: collapse; width: 250%; }
        div#users-contain table td, div#users-contain table th { border: 1.5px solid #ffffff; padding: .6em 10px; text-align: middle; width:25%; }
        .ui-dialog .ui-state-error { padding: .3em; }
        .validateTips { border: 1px solid transparent; padding: 0.3em; }
  </style>
  <script type="text/javascript">
	/* <![CDATA[ */
	$(function() {
		var advisorsId = $( "#advisorsId" ),
		startDate = $( "#startDate" ),
		endDate = $( "#endDate" ),
		startTime = $( "#startTime" ),
		endTime = $( "#endTime" ),
        allFields = $( [] ).add( advisorsId ).add( startDate ).add( endDate ).add( startTime ).add( endTime ),
        tips = $( ".validateTips" );

    	function updateTips( t ) {
        	tips
            	.text( t )
            	.addClass( "ui-state-highlight" );
        	setTimeout(function() {
            	tips.removeClass( "ui-state-highlight", 1500 );
        	}, 500 );
    	}
		
    	$( "#dialog-form" ).dialog({
            autoOpen: false,
            height: 400,
            width: 400,
            modal: true,
            buttons: {
                "Block": function() {
                	
                	if ( advisorsId.val() != null && startDate.val().length != 0 && endDate.val().length != 0 && startTime.val().length != 0 && endTime.val().length != 0 ) {
                		var sd = startDate.val().split("/");
                		var ed = endDate.val().split("/");
                		
                		var st = startTime.val().split(":");
                    	var et = endTime.val().split(":");
                    	
                    	var start = new Date();
                    	start.setFullYear(sd[2], sd[0]-1, sd[1]);
            	    	start.setHours(st[0],st[1],0);
            	    	var end = new Date();
            	    	end.setFullYear(ed[2], ed[0]-1, ed[1]);           	    	
            	    	end.setHours(et[0],et[1],0);
                    	
            	    	if (start < end) {
                    		var url = '/cesar/user/advisorSchedule/block.html?advisorsId='+advisorsId.val()+'&startDate='+startDate.val()+'&endDate='+endDate.val()+'&startTime='+startTime.val()+'&endTime='+endTime.val();
                    		window.location.href = url;
                    		return false;
                    	
                        	$( this ).dialog( "close" );
            	    	} else {
            	    		$( this ).dialog( "close" );
            	    	}
                    } else if ( advisorsId.val() == null || startDate.val().length == 0 || endDate.val().length == 0 || startTime.val().length == 0 || endTime.val().length == 0 ) {
                    	$( this ).dialog( "close" );
                    }
                },
                Cancel: function() {
                    $( this ).dialog( "close" );
                }
            },
            close: function() {
                allFields.val( "" ).removeClass( "ui-state-error" );
            }
        });
  
		$( "#blockTimeButton" )
        .button()
        .click(function() {
            $( "#dialog-form" ).dialog( "open" );
        });
		
		$('#startDate').datepicker({
	        inline: true,
	        changeMonth: true,
	        changeYear: true,
	        yearRange: '-01:+01',
	       	defaultDate: "+1w",
	        onClose: function( selectedDate ) {
	        	$( "#endDate" ).datepicker( "option", "minDate", selectedDate );
	      	}
	    });
	    
		$('#endDate').datepicker({
	        inline: true,
	        changeMonth: true,
	        changeYear: true,
	        yearRange: '-01:+01',
	        defaultDate: "+1w",
	        onClose: function( selectedDate ) {
	        	$( "#startDate" ).datepicker( "option", "maxDate", selectedDate );
	        }
	    });
		
		function StartOnHourShowCallback(hour) {
			//var tpEndHour = $('#endTime').timepicker('getHour');
		    if ((hour > 20) || (hour < 9)) {
		        return false; // not valid
		    }
		    //if (hour >= tpEndHour) { return false; }
		    return true; // valid
		}
		
		function StartOnMinuteShowCallback(hour, minute) {
			//var tpEndHour = $('#endTime').timepicker('getHour');
		    //var tpEndMinute = $('#endTime').timepicker('getMinute');
		    if ((hour == 20) && (minute > 0)) { return false; } // not valid
		    if ((hour == 9) && (minute < 0)) { return false; }   // not valid
		    //if (hour > tpEndHour) { return false; }
		    //if ( (hour == tpEndHour) && (minute > tpEndMinute) ) { return false; }
		    return true;  // valid
		}
		
		function EndOnHourShowCallback(hour) {
			var tpStartHour = $('#startTime').timepicker('getHour');
		    if ((hour > 20) || (hour < 9)) {
		        return false; // not valid
		    }
		    if (hour < tpStartHour) { return false; }
		    return true; // valid
		}
		
		function EndOnMinuteShowCallback(hour, minute) {
			var tpStartHour = $('#startTime').timepicker('getHour');
		    var tpStartMinute = $('#startTime').timepicker('getMinute');
		    if ((hour == 20) && (minute > 0)) { return false; } // not valid
		    if ((hour == 9) && (minute < 0)) { return false; }   // not valid
		    if (hour < tpStartHour) { return false; }
		    if ( (hour == tpStartHour) && (minute <= tpStartMinute) ) { return false; }
		    return true;  // valid
		}
		
	    $('#startTime').timepicker({
	        onHourShow: StartOnHourShowCallback, 
	        onMinuteShow: StartOnMinuteShowCallback
	    });
	    
	    $('#endTime').timepicker({
	        onHourShow: EndOnHourShowCallback,
	        onMinuteShow: EndOnMinuteShowCallback
	    });
	    
	    $("#advisorsId").multiselect({
	    	noneSelectedText: 'Select Advisors',
	    	selectedList: 2,
	    	minWidth: 362
	    });
	});
  	/* ]]> */
  </script>
</c:set>		     
<%@ include file="/WEB-INF/views/include/header.jspf" %>

<form>
 	
 	<div style="width:100%; height:20px; position:relative;">
	    <div style="position:absolute; top:0; bottom:0; width:80%;">
    		<h3>Active Blocks</h3>
    	</div>
    	<div style="position:absolute; top:0; bottom:0; width: 19%; left:90%; top:70%">
    		<input id="blockTimeButton" type="button" value="Block Time" />
    	</div>	
    </div><br />
    <div id="users-contain" class="ui-widget">
    <table class="tablesorter" cellpadding="1.5em" cellspacing="0">
		<c:if test="${ empty blocks }">
			<p> No Advisors is blocked at this time </p>
		</c:if>
		<c:if test="${ not empty blocks }">
		<thead>
			<tr>
				<th >Advisor</th>
				<th >Start Date</th>
				<th >End Date</th>
				<th >Time Period</th> 
				<th></th>
			</tr>
		</thead>
		</c:if>
	
		<tbody>
			<c:forEach items="${blocks}" var="block">
				<tr>
					<td>${block.advisor.firstName } ${block.advisor.lastName } </td>
					<td><fmt:formatDate pattern="MM/dd/yyyy" value="${block.startDateTime.time}" /></td>
					<td><fmt:formatDate pattern="MM/dd/yyyy" value="${block.endDateTime.time}" /></td>
					<td>
						<fmt:formatDate pattern="HH:mm:ss a" value="${block.startDateTime.time}" /> - <fmt:formatDate pattern="HH:mm:ss a" value="${block.endDateTime.time}" />
					</td>
					<td><a href="deleteBlock.html?id=${block.id}">Delete</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div><br /><br />
	
 </form>
  
 <div id="dialog-form" title="Block Adviosrs">
 <p class="validateTips">All fields are required.</p>
    <form>
    <fieldset>
    	<label for="advisors">Advisors:<br /></label>
    	<select title="Advisors List" multiple="multiple" name="advisorsId" id="advisorsId" >
	    <c:forEach items="${advisors}" var="advisor">
	      <option value="${advisor.id}">${advisor.name}</option>
	    </c:forEach>
		</select><br />
		
        <label for="startDate"><br />Date:</label><br />
        <input type='date' id='startDate' name='startDate' class="text ui-widget-content ui-corner-all" />
        <label for="endDate">To</label>
        <input type='date' id='endDate' name='endDate' class="text ui-widget-content ui-corner-all" />
        <label for="startTime"><br />Time:</label><br />
        <input type="time" id="startTime" name="startTime" class="text ui-widget-content ui-corner-all" />
        <label for="endTime">To</label>
        <input type="time" id="endTime" name="endTime" class="text ui-widget-content ui-corner-all" />
    </fieldset>
    </form>
</div>
  
  
<%@ include file="/WEB-INF/views/include/footer.jspf" %>