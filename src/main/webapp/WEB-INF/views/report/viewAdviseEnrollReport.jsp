<?xml version="1.0" encoding="iso-8859-1"?>
<%@ include file="/WEB-INF/views/include/taglib.jspf"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="headElements">
	<title>Display advisement/enrollment report</title>
	<style>
		label{
			display:inline-block;
			vertical-align:middle;
			height:22px;
		}
	</style>
	<script type="text/javascript">
	/* <![CDATA[ */
	$(function() {	    
		$( "#tabs" ).tabs({
			ajaxOptions: {cache: false}
		});
		
		$("#studentType").multiselect({
	    	noneSelectedText: 'select students',
	    	selectedList: 2
	    });
		
		if (document.getElementById('type').value == "advisement") {
			$('#enrollment').hide();
			$('#advisement').show();
		} else if (document.getElementById('type').value == "enrollment") {
			$('#advisement').hide();
			$('#enrollment').show();
		}
		
		var flag = "${chartFlag}";
		var title = "${title}";
		var students = "${students}";
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
		                text: 'Total Number of ' + students + ' Students ' + title
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
		                    text: 'Number of Students'
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
		                        this.x +': '+ this.y +' student(s)';
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
	    
	    jQuery(document).ready(function(){
	    	jQuery("#type").change(function(){
	    		jQuery(".hiddenOption").hide();
	    		jQuery("#" + jQuery(this).val()).show();
	    	});
	    	
	    	jQuery("#y1").change(function(){
	    		jQuery(".disableOption").attr("disabled", "disabled");
	    	});
	    	
	    	jQuery("#y2").change(function(){
	    		jQuery(".disableOption").attr("disabled", "disabled");
	    	});
	    	
	    	jQuery("#y3").change(function(){
	    		jQuery(".disableOption").attr("disabled", "disabled");
	    	});
	    	
	    	jQuery("#y4").change(function(){
	    		jQuery(".disableOption").attr("disabled", "disabled");
	    	});
	    	
	    	jQuery("#y0").change(function(){
	    		jQuery(".disableOption").removeAttr('checked');
	    		jQuery(".disableOption").removeAttr('disabled');
	    	});
	    });
	    
	    $("#y0").click(function() {
	    	if (this.checked) {
	    		$(".chkGroup").removeAttr("checked");
	    		$(".chkGroup").attr('disabled','true');
	    	} else {
	    		$(".chkGroup").removeAttr("disabled");
	    	}
	    });
	    
	});

	/* ]]> */
	</script>
</c:set>
<%@ include file="/WEB-INF/views/include/header.jspf"%>
<h3> Advisement/Enrollment Reports </h3>
<div id="container" style="width:100%">
<div id="menu" style="background-color:#FFFFFF;height:100%;width:17%;float:left;">
<div id="tabs" style="width: 97%;">
<form:form modelAttribute="report" action ="/cesar/report/viewAdvisementEnrollmentReport.html"  method="post" >
	<label for="type">Number of Students</label><br />
	<form:select path="type" id="type" style="width:95%;">
		<form:option value="advisement"> Advised </form:option>
		<form:option value="enrollment" > Enrolled </form:option>
	</form:select><br /><br />
	<c:if test="${optionError!=null}">
    	<p style="color:red">${optionError }</p>
    </c:if>
	<fieldset style="width:90%;" id="studentYear">
		<legend style="height:22px;">Student Classification</legend>
		
       		<form:checkbox path="year" id="y0" value="10" />All<br />
			<form:checkbox path="year" id="y1" value="1" class="chkGroup" />First-Year<br />
			<form:checkbox path="year" id="y2" value="2" class="chkGroup" />Second-Year<br />
			<form:checkbox path="year" id="y3" value="3" class="chkGroup" />Third-Year<br />
			<form:checkbox path="year" id="y4" value="4" class="chkGroup" />Fourth-Year
		
	</fieldset><br />
	<label for="from">From</label><br />
	<form:select path="fromQuarter" style="width:52%;">
		<form:option value="fall"> Fall </form:option>
		<form:option value="winter" > Winter </form:option>
		<form:option value="spring"> Spring </form:option>
		<form:option value="summer" > Summer </form:option>
	</form:select>
	<form:select path="fromYear" id="fromYear" style="width:40%;">
		<form:options items="${yearList}" />
	</form:select><br /><br />
	<label for="to">to</label><br />
	<form:select path="toQuarter" style="width:52%;">
		<form:option value="fall"> Fall </form:option>
		<form:option value="winter" > Winter </form:option>
		<form:option value="spring"> Spring </form:option>
		<form:option value="summer" > Summer </form:option>
	</form:select>
	<form:select path="toYear" id="toYear" style="width:40%;">
		<form:options items="${yearList}" />
	</form:select><br /><br />
	<fieldset style="width:90%;" id="advisement" class="hiddenOption">
       <legend style="height:22px;">Group by</legend>

			<form:radiobutton path="group" value="advised" checked="true" />Advised<br />
			<form:radiobutton path="group" value="student" class="disableOption" />Student Classification<br />
			<form:radiobutton path="group" value="department" />Department<br />
			<form:radiobutton path="group" value="gender" />Gender<br />
			<!-- input type="radio" name="group" value="subconducted" />Advisor sub conducted  -->
		
	</fieldset>
	<fieldset style="width:90%;" id="enrollment" class="hiddenOption">
       <legend style="height:22px;">Group by</legend>
       
			<form:radiobutton path="group1" value="enrolled" checked="true" />Enrolled<br />
			<form:radiobutton path="group1" value="student" class="disableOption" />Student Classification<br />
			<form:radiobutton path="group1" value="department" />Department<br />
			<form:radiobutton path="group1" value="gender" />Gender<br />
			<!-- input type="radio" name="group1" value="subconducted" />Advisor sub conducted -->
		
	</fieldset><br />
	<input type="submit" value="Submit"/>
</form:form><br />
</div><br /><br />
</div>

<div id="content" style="background-color:#EEEEEE;height:100%;width:83%;float:left;">
<div id="chartContainer" style="width: 98%; height: 500px; margin: 0 auto; padding: 10px;"></div>

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