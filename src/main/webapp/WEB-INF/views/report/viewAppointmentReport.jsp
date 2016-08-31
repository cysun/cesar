<?xml version="1.0" encoding="iso-8859-1"?>
<%@ include file="/WEB-INF/views/include/taglib.jspf"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="headElements">
	<title>Display appointment report</title>
	<style>
		label{
			display:inline-block;
			vertical-align:middle;
			width:35px;
			height:22px;
		}
	</style>
	<script type="text/javascript">
	/* <![CDATA[ */
	$(function() {	    
		$( "#tabs" ).tabs({
			ajaxOptions: {cache: false}
		});
		
		var flag = "${chartFlag}";
		var title = "${title}";
		var fromQ = "${fromQ}";
		var toQ = "${toQ}";
		var quarters = "${qList}";
		var qArray = quarters.substring(1, quarters.length-1).split(",");
		
		var options = {
		        chart: {
		                renderTo: 'chartContainer',
		                type: 'column'
		            },
		            title: {
		                text: 'Total ' + title + ' by Term'
		            },
		            subtitle: {
		                text: 'from ' + fromQ + ' to ' + toQ
		            },
		            xAxis: {
		                categories: qArray
		            },
		            yAxis: {
		                min: 0,
		                title: {
		                    text: 'Number of Appointments'
		                }
		            },
		            legend: {
		                layout: 'vertical',
		                backgroundColor: '#FFFFFF',
		                align: 'right',
		                verticalAlign: 'top',
		                x: 0,
		                y: 100,
		                //floating: true,
		                shadow: true
		            },
		            tooltip: {
		                formatter: function() {
		                    return ''+
		                        this.x +': '+ this.y +' appointment(s)';
		                }
		            },
		            plotOptions: {
		                column: {
		                    pointPadding: 0.2,
		                    borderWidth: 0
		                }
		            },
		            series: []
		    }; 
		
		<c:forEach items="${allData}" var="d">   
        	var key = '<c:out value="${d.key}"/>';
        	var value = '<c:out value="${d.value}"/>';
        	var v = value.substring(1, value.length-1).split(",");
        	var val = new Array();
        	for (var i = 0; i < v.length; i++) {
        		val[i] = parseInt(v[i]);
        	}
        
        	options.series.push({
        	    name: key,
         		data: val
        	});
        </c:forEach>

        if (flag) {
			var chart;
	    	$(document).ready(function() {
	        	chart = new Highcharts.Chart(options);
	    	});
        }
	    
	    if (document.getElementById('appointmentType').value == "appointment") {
			$('#walkin').hide();
			$('#appointment').show();
		} else if (document.getElementById('appointmentType').value == "walkin") {
			$('#appointment').hide();
			$('#walkin').show();
		}
	    
	    $("#chkAll").click(function() {
	    	if (this.checked) {
	    		$(".chkGroup").removeAttr("checked");
	    		$(".chkGroup").attr('disabled','true');
	    	} else {
	    		$(".chkGroup").removeAttr("disabled");
	    	}
	    });
	    
	    $("#chkAll2").click(function() {
	    	if (this.checked) {
	    		$(".chkGroup2").removeAttr("checked");
	    		$(".chkGroup2").attr('disabled','true');
	    	} else {
	    		$(".chkGroup2").removeAttr("disabled");
	    	}
	    });
	 
	});
	
	function myFunction()
	{
		if (document.getElementById('appointmentType').value == "appointment") {
			$("#chkAll2").removeAttr("checked");
			$(".chkGroup2").removeAttr("checked");
			$("#chkAll2").removeAttr("disabled");
			$(".chkGroup2").removeAttr("disabled");
			$('#walkin').hide();
			$('#appointment').show();
		} else if (document.getElementById('appointmentType').value == "walkin") {
			$("#chkAll").removeAttr("checked");
			$(".chkGroup").removeAttr("checked");
			$("#chkAll").removeAttr("disabled");
			$(".chkGroup").removeAttr("disabled");
			$('#appointment').hide();
			$('#walkin').show();
		}
	}
	/* ]]> */
	</script>
</c:set>
<%@ include file="/WEB-INF/views/include/header.jspf"%>
<h3> Appointment Reports </h3>
<div id="container" style="width:100%">
<div id="menu" style="background-color:#FFFFFF;height:100%;width:15%;float:left;">
<div id="tabs" style="width: 96%;"><br />
<form:form modelAttribute="report" action ="/cesar/report/viewAppointmentReport.html"  method="post" >
	<label for="type">Type</label><br />
	<form:select path="type" id="appointmentType" style="width:90%;" onchange="myFunction()">
		<form:option value="appointment"> Appointment </form:option>
		<form:option value="walkin" > Walk-in appointment </form:option>
	</form:select><br /><br />
	<label for="from">From</label><br />
	<form:select path="fromQuarter" style="width:47%;">
		<form:option value="fall"> Fall </form:option>
		<form:option value="winter" > Winter </form:option>
		<form:option value="spring"> Spring </form:option>
		<form:option value="summer" > Summer </form:option>
	</form:select>
	<form:select path="fromYear" id="fromYear" style="width:40%;">
		<form:options items="${yearList}" />
	</form:select><br /><br />
	<label for="to">to</label><br />
	<form:select path="toQuarter" style="width:47%;">
		<form:option value="fall"> Fall </form:option>
		<form:option value="winter" > Winter </form:option>
		<form:option value="spring"> Spring </form:option>
		<form:option value="summer" > Summer </form:option>
	</form:select>
	<form:select path="toYear" id="toYear" style="width:40%;">
		<form:options items="${yearList}" />
	</form:select><br /><br /><br />
	<c:if test="${optionError!=null}">
    	<p style="color:red">${optionError }</p>
    </c:if>
	<fieldset style="width:83%;" id="appointment" class="hiddenOption">
       <p>
			<form:checkbox path="option" value="allAppointment" id="chkAll" />All<br />
			<form:checkbox path="option" value="completed" class="chkGroup" />Completed<br />
			<form:checkbox path="option" value="cancelled" class="chkGroup" />Cancelled<br />
			<form:checkbox path="option" value="missed" class="chkGroup" />No shows
		</p>
	</fieldset>
	<fieldset style="width:83%;" id="walkin" class="hiddenOption">
       <p>
			<form:checkbox path="option" value="allWalkin" id="chkAll2" />All<br />
			<form:checkbox path="option" value="seen" class="chkGroup2" />Seen<br />
			<form:checkbox path="option" value="notseen" class="chkGroup2" />Not Seen
		</p>
	</fieldset><br />
	<input type="submit" value="Submit"/>
</form:form><br />
</div><br /><br />
</div>

<div id="content" style="background-color:#EEEEEE;height:100%;width:85%;float:left;">
<div id="chartContainer" style="width: 98%; height: 400px; margin: 0 auto; padding: 10px;"></div>

<div>
<c:if test="${chartFlag}">
<table class="tablesorter shadow" cellspacing="1" cellpadding="4" style="width: 80%;" align="center">
	<thead>
		<tr>
			<th style="font-size:14px;"></th>
			<c:forEach items="${qList}" var="q" >
				<th style="font-size:14px;">${q}</th>
			</c:forEach>		 
		</tr>
	</thead>
	
	<tbody>
		<c:forEach items="${allData}" var="entry">
			<tr>
				<td style="background-color: #F0F0F6; font-size:14px;">${entry.key }</td>
				<c:forEach items="${entry.value}" var="data" >
					<td style="background-color: #F0F0F6; font-size:14px;" align="center">${data }</td>
				</c:forEach>
			</tr>
		</c:forEach>	
	</tbody>
</table>
</c:if>
</div><br />
</div>
</div>

<%@ include file="/WEB-INF/views/include/footer.jspf"%>