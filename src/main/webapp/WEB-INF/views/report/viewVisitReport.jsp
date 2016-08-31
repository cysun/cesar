<?xml version="1.0" encoding="iso-8859-1"?>
<%@ include file="/WEB-INF/views/include/taglib.jspf"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="headElements">
	<title>Display visit report</title>
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
		                text: 'Total Number of ' + title + ' by Term'
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
		                    text: 'Number of ' + title
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
		                        this.x +': '+ this.y;
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
        
	});

	/* ]]> */
	</script>
</c:set>
<%@ include file="/WEB-INF/views/include/header.jspf"%>
<h3> Service/Visit Reason/No Seen Reason Reports </h3>
<div id="container" style="width:100%">
<div id="menu" style="background-color:#FFFFFF;height:100%;width:15%;float:left;">
<div id="tabs" style="width: 96%;"><br />
<form:form modelAttribute="report" action ="/cesar/report/viewVisitReport.html"  method="post" >
	<label for="type">Type</label><br />
	<form:select path="type" style="width:90%;">
		<form:option value="service"> Service </form:option>
		<form:option value="visit" > Visit Reason </form:option>
		<form:option value="noseen" > No Seen Reason </form:option>
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