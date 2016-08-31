<?xml version="1.0" encoding="iso-8859-1"?>
<%@ include file="/WEB-INF/views/include/taglib.jspf" %>
<c:set var="headElements">
  <title>CESAR - Advisor Schedule</title>
	<script type="text/javascript">
		/* <![CDATA[ */
		$(function() {
			$(".usertable tbody tr:odd").css("background-color","#F0F0F6");
		});
		/* ]]> */
	</script>
</c:set>
<%@ include file="/WEB-INF/views/include/header.jspf" %>
    <h3>Advisors Schedule Table - ${scheduleTable.quarter }</h3>
    <div style="width:100%;height:30px;position:relative;">
	    <div style="position:absolute; top:0; bottom:0; width:80%;">
	    <c:if test="${isForAdvisor == true }" >
	 		<form action ="/cesar/user/advisorSchedule/advisorsScheduleTable.html"  method="post" >
	 			<input type="hidden" name="id" value="${scheduleTable.id }" />
	 			<select name="isRegisterPeriod">
	 				<option value="false">Regular Period </option>
	 				<option value="true" > Register Period</option>
	 			</select>
	 			<input type="submit" value="Submit"/>
	 		</form>
	 	</c:if>
	 	</div>
   		<div style="text-align: center; position:absolute; top:0; bottom:0; width: 19%; left:85%;">
  			<form>
  				<a href="/cesar/user/advisorSchedule/activeBlock.html"> Block Time </a>
  			</form>
		</div>
	</div>
	 	
	    <table class="usertable shadow" cellpadding="1.5em" cellspacing="0">
		    <thead>
		    	<tr>
		    		<th class="extraColor" >Time</th>
		    		<th class="extraColor" >Monday</th>
		    		<th class="extraColor" >Tuesday</th>
		    		<th class="extraColor" >Wednesday</th>
		    		<th class="extraColor" >Thursday</th>
		    		<th class="extraColor" >Friday</th>
		    	</tr>
		    </thead>	
		    <tbody>
		    	<tr>
		    		<th>9:00-9:30</th>
		    		<c:forEach begin="1090" end="5090" var="i" step="1000" >
			    		<td>
			    			<c:forEach items="${scheduleTable.scheduleTableSections}" var="section">
			    			
			    				<c:if test="${section.startTime == i }">
			    					<c:forEach items="${section.advisors}" var="advisor" varStatus="s"  >
			    						<c:if test="${isForAdvisor == false }">
			    						<a href="<c:url value='/appointment/student/initialMakeAppointment.html?startTime=${section.startTime }&advisorId=${advisor.id }&studentId=${studentId }&id=${id }'/>">
			    							${advisor.name} 
			    						</a>
			    						</c:if>
			    						<c:if test="${isForAdvisor == true }" >
			    							${advisor.name}
			    						</c:if>
			    						
			    						<c:if test="${ not s.last }" >
			    						,
			    						</c:if>
			    					</c:forEach>
			    				</c:if>
			    				
			    			</c:forEach>
			    			<c:if test="${isForAdvisor == true }" >
				    			<a href="/cesar/user/advisorSchedule/editScheduleTable.html?startTime=${i }">
				    			<span class="imgLink">
				    			<img src="<c:url value='/resources/img/editIcon.png' />" alt="edit icon" />
				    			</span></a>
			    			</c:if>
			    		</td>
		    		</c:forEach>
		    	</tr>
		    	
		    	<tr>
		    		<th>9:30-10:00</th>
		    		<c:forEach begin="1090" end="5090" var="i" step="1000" >
			    		<td>
			    			<c:forEach items="${scheduleTable.scheduleTableSections}" var="section" >
			    			
			    				<c:if test="${section.startTime == i+1 }">
			    					<c:forEach items="${section.advisors}" var="advisor" varStatus="s">
			    						<c:if test="${isForAdvisor == false }">
			    						<a href="<c:url value='/appointment/student/initialMakeAppointment.html?startTime=${section.startTime }&advisorId=${advisor.id }&studentId=${studentId }&id=${id }'/>">
			    							${advisor.name} 
			    						</a>
			    						</c:if>
			    						<c:if test="${isForAdvisor == true }" >
			    							${advisor.name}
			    						</c:if>
			    						
			    						<c:if test="${ not s.last }" >
			    						,
			    						</c:if>
			    					</c:forEach>
			    				</c:if>
			    				
			    			</c:forEach>
			    			<c:if test="${isForAdvisor == true }" >
				    			<a href="/cesar/user/advisorSchedule/editScheduleTable.html?startTime=${i+1 }">
				    			<span class="imgLink">
				    			<img src="<c:url value='/resources/img/editIcon.png' />" alt="edit icon" />
				    			</span></a>
			    			</c:if>
			    		</td>
		    		</c:forEach>
		    	</tr>
				
				<tr>
		    		<th>10:00-10:30</th>
		    		<c:forEach begin="1100" end="5100" var="i" step="1000" >
			    		<td>
			    			<c:forEach items="${scheduleTable.scheduleTableSections }" var="section" >
			    				<c:if test="${section.startTime == i }">
			    					<c:forEach items="${section.advisors}" var="advisor" varStatus="s">
			    						<c:if test="${isForAdvisor == false }">
			    						<a href="<c:url value='/appointment/student/initialMakeAppointment.html?startTime=${section.startTime }&advisorId=${advisor.id }&studentId=${studentId }&id=${id }'/>">
			    							${advisor.name} 
			    						</a>
			    						</c:if>
			    						<c:if test="${isForAdvisor == true }" >
			    							${advisor.name}
			    						</c:if>
			    						<c:if test="${ not s.last }" >
			    						,
			    						</c:if>
			    					</c:forEach>
			    				</c:if>
			    				
			    			</c:forEach>
			    			
			    			<c:if test="${isForAdvisor == true }" >
				    			<a href="/cesar/user/advisorSchedule/editScheduleTable.html?startTime=${i }">
				    			<span class="imgLink">
				    			<img src="<c:url value='/resources/img/editIcon.png' />" alt="edit icon" />
				    			</span></a>
			    			</c:if>
			    		</td>
		    		</c:forEach>
		    	</tr>
		    	
		    	<tr>
		    		<th>10:30-11:00</th>
		    		<c:forEach begin="1100" end="5100" var="i" step="1000" >
			    		<td>
			    			<c:forEach items="${scheduleTable.scheduleTableSections }" var="section">
			    				<c:if test="${section.startTime == i+1 }">
			    					<c:forEach items="${section.advisors}" var="advisor" varStatus="s">
			    						<c:if test="${isForAdvisor == false }">
			    						<a href="<c:url value='/appointment/student/initialMakeAppointment.html?startTime=${section.startTime }&advisorId=${advisor.id }&studentId=${studentId }&id=${id }'/>">
			    							${advisor.name} 
			    						</a>
			    						</c:if>
			    						<c:if test="${isForAdvisor == true }" >
			    							${advisor.name}
			    						</c:if>
			    						<c:if test="${ not s.last }" >
			    						,
			    						</c:if>
			    					</c:forEach>
			    				</c:if>
			    				
			    			</c:forEach>
			    			
			    			<c:if test="${isForAdvisor == true }" >
				    			<a href="/cesar/user/advisorSchedule/editScheduleTable.html?startTime=${i+1 }">
				    			<span class="imgLink">
				    			<img src="<c:url value='/resources/img/editIcon.png' />" alt="edit icon" />
				    			</span></a>
			    			</c:if>
			    		</td>
		    		</c:forEach>
		    	</tr>
		    	
		    	
		    	<tr>
		    		<th>11:00-11:30</th>
		    		<c:forEach begin="1110" end="5110" var="i" step="1000" >
			    		<td>
			    			<c:forEach items="${scheduleTable.scheduleTableSections }" var="section">
			    				<c:if test="${section.startTime == i }">
			    					<c:forEach items="${section.advisors}" var="advisor" varStatus="s">
			    						<c:if test="${isForAdvisor == false }">
			    						<a href="<c:url value='/appointment/student/initialMakeAppointment.html?startTime=${section.startTime }&advisorId=${advisor.id }&studentId=${studentId }&id=${id }'/>">
			    							${advisor.name} 
			    						</a>
			    						</c:if>
			    						<c:if test="${isForAdvisor == true }" >
			    							${advisor.name}
			    						</c:if>
			    						<c:if test="${ not s.last }" >
			    						,
			    						</c:if>
			    					</c:forEach>
			    				</c:if>
			    			</c:forEach>
			    			<c:if test="${isForAdvisor == true }" >
				    			<a href="/cesar/user/advisorSchedule/editScheduleTable.html?startTime=${i }">
				    			<span class="imgLink">
				    			<img src="<c:url value='/resources/img/editIcon.png' />" alt="edit icon" />
				    			</span></a>
			    			</c:if>
			    		</td>
		    		</c:forEach>
		    	</tr>
		    	
		    	<tr>
		    		<th>11:30-12:00</th>
		    		<c:forEach begin="1110" end="5110" var="i" step="1000" >
			    		<td>
			    			<c:forEach items="${scheduleTable.scheduleTableSections }" var="section">
			    				<c:if test="${section.startTime == i+1 }">
			    					<c:forEach items="${section.advisors}" var="advisor" varStatus="s">
			    						<c:if test="${isForAdvisor == false }">
			    						<a href="<c:url value='/appointment/student/initialMakeAppointment.html?startTime=${section.startTime }&advisorId=${advisor.id }&studentId=${studentId }&id=${id }'/>">
			    							${advisor.name} 
			    						</a>
			    						</c:if>
			    						<c:if test="${isForAdvisor == true }" >
			    							${advisor.name}
			    						</c:if>
			    						<c:if test="${ not s.last }" >
			    						,
			    						</c:if>
			    					</c:forEach>
			    				</c:if>
			    			</c:forEach>
			    			<c:if test="${isForAdvisor == true }" >
				    			<a href="/cesar/user/advisorSchedule/editScheduleTable.html?startTime=${i+1 }">
				    			<span class="imgLink">
				    			<img src="<c:url value='/resources/img/editIcon.png' />" alt="edit icon" />
				    			</span></a>
			    			</c:if>
			    		</td>
		    		</c:forEach>
		    	</tr>
		    	
		    	<tr>
		    		<th>12:00-12:30</th>
		    		<c:forEach begin="1120" end="5120" var="i" step="1000" >
			    		<td>
			    			<c:forEach items="${scheduleTable.scheduleTableSections }" var="section">
			    				<c:if test="${section.startTime == i }"> 
			    				<c:forEach items="${section.advisors}" var="advisor" varStatus="s">
			    				
			    						<c:if test="${isForAdvisor == false }">
			    						<a href="<c:url value='/appointment/student/initialMakeAppointment.html?startTime=${section.startTime }&advisorId=${advisor.id }&studentId=${studentId }&id=${id }'/>">
			    							${advisor.name} 
			    						</a>
			    						</c:if>
			    						<c:if test="${isForAdvisor == true }" >
			    							${advisor.name}
			    						</c:if>
			    						<c:if test="${ not s.last }" >
			    						,
			    						</c:if>
			    					</c:forEach>
			    				</c:if>	
			    			</c:forEach>
			    			
			    			<c:if test="${isForAdvisor == true }" >
				    			<a href="/cesar/user/advisorSchedule/editScheduleTable.html?startTime=${i }">
				    			<span class="imgLink">
				    			<img src="<c:url value='/resources/img/editIcon.png' />" alt="edit icon" />
				    			</span></a>
			    			</c:if>
			    		</td>
		    		</c:forEach>
		    	</tr>
		    	
		    	<tr>
		    		<th>12:30-1:00</th>
		    		<c:forEach begin="1120" end="5120" var="i" step="1000" >
			    		<td>
			    			<c:forEach items="${scheduleTable.scheduleTableSections }" var="section">
			    				<c:if test="${section.startTime == i+1 }">
			    				<c:forEach items="${section.advisors}" var="advisor" varStatus="s">
			    				
			    						<c:if test="${isForAdvisor == false }">
			    						<a href="<c:url value='/appointment/student/initialMakeAppointment.html?startTime=${section.startTime }&advisorId=${advisor.id }&studentId=${studentId }&id=${id }'/>">
			    							${advisor.name} 
			    						</a>
			    						</c:if>
			    						<c:if test="${isForAdvisor == true }" >
			    							${advisor.name}
			    						</c:if>
			    						<c:if test="${ not s.last }" >
			    						,
			    						</c:if>
			    					</c:forEach>
			    				</c:if>	
			    			</c:forEach>
			    			
			    			<c:if test="${isForAdvisor == true }" >
				    			<a href="/cesar/user/advisorSchedule/editScheduleTable.html?startTime=${i+1 }">
				    			<span class="imgLink">
				    			<img src="<c:url value='/resources/img/editIcon.png' />" alt="edit icon" />
				    			</span></a>
			    			</c:if>
			    		</td>
		    		</c:forEach>
		    	</tr>
		    	
		    	<tr>
		    		<th>1:00-1:30</th>
		    		<c:forEach begin="1130" end="5130" var="i" step="1000" >
			    		<td>
			    			<c:forEach items="${scheduleTable.scheduleTableSections }" var="section">
			    				<c:if test="${section.startTime == i }">
			    					<c:forEach items="${section.advisors}" var="advisor" varStatus="s">
			    						<c:if test="${isForAdvisor == false }">
			    						<a href="<c:url value='/appointment/student/initialMakeAppointment.html?startTime=${section.startTime }&advisorId=${advisor.id }&studentId=${studentId }&id=${id }'/>">
			    							${advisor.name} 
			    						</a>
			    						</c:if>
			    						<c:if test="${isForAdvisor == true }" >
			    							${advisor.name}
			    						</c:if>
			    						<c:if test="${ not s.last }" >
			    						,
			    						</c:if>
			    					</c:forEach>
			    				</c:if>
			    			</c:forEach>
			    			<c:if test="${isForAdvisor == true }" >
				    			<a href="/cesar/user/advisorSchedule/editScheduleTable.html?startTime=${i }">
				    			<span class="imgLink">
				    			<img src="<c:url value='/resources/img/editIcon.png' />" alt="edit icon" />
				    			</span></a>
			    			</c:if>
			    		</td>
		    		</c:forEach>
		    	</tr>
		    	
		    	<tr>
		    		<th>1:30-2:00</th>
		    		<c:forEach begin="1130" end="5130" var="i" step="1000" >
			    		<td>
			    			<c:forEach items="${scheduleTable.scheduleTableSections }" var="section">
			    				<c:if test="${section.startTime == i+1 }">
			    					<c:forEach items="${section.advisors}" var="advisor" varStatus="s">
			    						<c:if test="${isForAdvisor == false }">
			    						<a href="<c:url value='/appointment/student/initialMakeAppointment.html?startTime=${section.startTime }&advisorId=${advisor.id }&studentId=${studentId }&id=${id }'/>">
			    							${advisor.name} 
			    						</a>
			    						</c:if>
			    						<c:if test="${isForAdvisor == true }" >
			    							${advisor.name}
			    						</c:if>
			    						<c:if test="${ not s.last }" >
			    						,
			    						</c:if>
			    					</c:forEach>
			    				</c:if>
			    			</c:forEach>
			    			<c:if test="${isForAdvisor == true }" >
				    			<a href="/cesar/user/advisorSchedule/editScheduleTable.html?startTime=${i+1 }">
				    			<span class="imgLink">
				    			<img src="<c:url value='/resources/img/editIcon.png' />" alt="edit icon" />
				    			</span></a>
			    			</c:if>
			    		</td>
		    		</c:forEach>
		    	</tr>
		    	
				<tr>
		    		<th>2:00-2:30</th>
		    		<c:forEach begin="1140" end="5140" var="i" step="1000" >
			    		<td>
			    			<c:forEach items="${scheduleTable.scheduleTableSections }" var="section">
			    				<c:if test="${section.startTime == i }">
			    					<c:forEach items="${section.advisors}" var="advisor" varStatus="s">
			    						<c:if test="${isForAdvisor == false }">
			    						<a href="<c:url value='/appointment/student/initialMakeAppointment.html?startTime=${section.startTime }&advisorId=${advisor.id }&studentId=${studentId }&id=${id }'/>">
			    							${advisor.name} 
			    						</a>
			    						</c:if>
			    						<c:if test="${isForAdvisor == true }" >
			    							${advisor.name}
			    						</c:if>
			    						<c:if test="${ not s.last }" >
			    						,
			    						</c:if>
			    					</c:forEach>
			    				</c:if>
			    			</c:forEach>
			    			<c:if test="${isForAdvisor == true }" >
				    			<a href="/cesar/user/advisorSchedule/editScheduleTable.html?startTime=${i }">
				    			<span class="imgLink">
				    			<img src="<c:url value='/resources/img/editIcon.png' />" alt="edit icon" />
				    			</span></a>
			    			</c:if>
			    		</td>
		    		</c:forEach>
		    	</tr>
		    	
		    	<tr>
		    		<th>2:30-3:00</th>
		    		<c:forEach begin="1140" end="5140" var="i" step="1000" >
			    		<td>
			    			<c:forEach items="${scheduleTable.scheduleTableSections }" var="section">
			    				<c:if test="${section.startTime == i+1 }">
			    					<c:forEach items="${section.advisors}" var="advisor" varStatus="s">
			    						<c:if test="${isForAdvisor == false }">
			    						<a href="<c:url value='/appointment/student/initialMakeAppointment.html?startTime=${section.startTime }&advisorId=${advisor.id }&studentId=${studentId }&id=${id }'/>">
			    							${advisor.name} 
			    						</a>
			    						</c:if>
			    						<c:if test="${isForAdvisor == true }" >
			    							${advisor.name}
			    						</c:if>
			    						<c:if test="${ not s.last }" >
			    						,
			    						</c:if>
			    					</c:forEach>
			    				</c:if>
			    			</c:forEach>
			    			<c:if test="${isForAdvisor == true }" >
				    			<a href="/cesar/user/advisorSchedule/editScheduleTable.html?startTime=${i+1 }">
				    			<span class="imgLink">
				    			<img src="<c:url value='/resources/img/editIcon.png' />" alt="edit icon" />
				    			</span></a>
			    			</c:if>
			    		</td>
		    		</c:forEach>
		    	</tr>
		    	
		    	<tr>
		    		<th>3:00-3:30</th>
		    		<c:forEach begin="1150" end="5150" var="i" step="1000" >
			    		<td>
			    			<c:forEach items="${scheduleTable.scheduleTableSections }" var="section">
			    				<c:if test="${section.startTime == i }">
			    					<c:forEach items="${section.advisors}" var="advisor" varStatus="s">
			    						<c:if test="${isForAdvisor == false }">
			    						<a href="<c:url value='/appointment/student/initialMakeAppointment.html?startTime=${section.startTime }&advisorId=${advisor.id }&studentId=${studentId }&id=${id }'/>">
			    							${advisor.name} 
			    						</a>
			    						</c:if>
			    						<c:if test="${isForAdvisor == true }" >
			    							${advisor.name}
			    						</c:if>
			    						<c:if test="${ not s.last }" >
			    						,
			    						</c:if>
			    					</c:forEach>
			    				</c:if>
			    			</c:forEach>
			    			<c:if test="${isForAdvisor == true }" >
				    			<a href="/cesar/user/advisorSchedule/editScheduleTable.html?startTime=${i }">
				    			<span class="imgLink">
				    			<img src="<c:url value='/resources/img/editIcon.png' />" alt="edit icon" />
				    			</span></a>
			    			</c:if>
			    		</td>
		    		</c:forEach>
		    	</tr>
		    	
		    	<tr>
		    		<th>3:30-4:00</th>
		    		<c:forEach begin="1150" end="5150" var="i" step="1000" >
			    		<td>
			    			<c:forEach items="${scheduleTable.scheduleTableSections }" var="section">
			    				<c:if test="${section.startTime == i+1 }">
			    					<c:forEach items="${section.advisors}" var="advisor" varStatus="s">
			    						<c:if test="${isForAdvisor == false }">
			    						<a href="<c:url value='/appointment/student/initialMakeAppointment.html?startTime=${section.startTime }&advisorId=${advisor.id }&studentId=${studentId }&id=${id }'/>">
			    							${advisor.name} 
			    						</a>
			    						</c:if>
			    						<c:if test="${isForAdvisor == true }" >
			    							${advisor.name}
			    						</c:if>
			    						<c:if test="${ not s.last }" >
			    						,
			    						</c:if>
			    					</c:forEach>
			    				</c:if>
			    			</c:forEach>
			    			<c:if test="${isForAdvisor == true }" >
				    			<a href="/cesar/user/advisorSchedule/editScheduleTable.html?startTime=${i+1 }">
				    			<span class="imgLink">
				    			<img src="<c:url value='/resources/img/editIcon.png' />" alt="edit icon" />
				    			</span></a>
			    			</c:if>
			    		</td>
		    		</c:forEach>
		    	</tr>
		    	
		    	<tr>
		    		<th>4:00-4:30</th>
		    		<c:forEach begin="1160" end="5160" var="i" step="1000" >
			    		<td>
			    			<c:forEach items="${scheduleTable.scheduleTableSections }" var="section">
			    				<c:if test="${section.startTime == i }">
			    					<c:forEach items="${section.advisors}" var="advisor" varStatus="s">
			    						<c:if test="${isForAdvisor == false }">
			    						<a href="<c:url value='/appointment/student/initialMakeAppointment.html?startTime=${section.startTime }&advisorId=${advisor.id }&studentId=${studentId }&id=${id }'/>">
			    							${advisor.name} 
			    						</a>
			    						</c:if>
			    						<c:if test="${isForAdvisor == true }" >
			    							${advisor.name}
			    						</c:if>
			    						<c:if test="${ not s.last }" >
			    						,
			    						</c:if>
			    					</c:forEach>
			    				</c:if>
			    			</c:forEach>
			    			<c:if test="${isForAdvisor == true }" >
				    			<a href="/cesar/user/advisorSchedule/editScheduleTable.html?startTime=${i }">
				    			<span class="imgLink">
				    			<img src="<c:url value='/resources/img/editIcon.png' />" alt="edit icon" />
				    			</span></a>
			    			</c:if>
			    		</td>
		    		</c:forEach>
		    	</tr>
		    	
		    	<tr>
		    		<th>4:30-5:00</th>
		    		<c:forEach begin="1160" end="5160" var="i" step="1000" >
			    		<td>
			    			<c:forEach items="${scheduleTable.scheduleTableSections }" var="section">
			    				<c:if test="${section.startTime == i+1 }">
			    					<c:forEach items="${section.advisors}" var="advisor" varStatus="s">
			    						<c:if test="${isForAdvisor == false }">
			    						<a href="<c:url value='/appointment/student/initialMakeAppointment.html?startTime=${section.startTime }&advisorId=${advisor.id }&studentId=${studentId }&id=${id }'/>">
			    							${advisor.name} 
			    						</a>
			    						</c:if>
			    						<c:if test="${isForAdvisor == true }" >
			    							${advisor.name}
			    						</c:if>
			    						<c:if test="${ not s.last }" >
			    						,
			    						</c:if>
			    					</c:forEach>
			    				</c:if>
			    			</c:forEach>
			    			<c:if test="${isForAdvisor == true }" >
				    			<a href="/cesar/user/advisorSchedule/editScheduleTable.html?startTime=${i+1 }">
				    			<span class="imgLink">
				    			<img src="<c:url value='/resources/img/editIcon.png' />" alt="edit icon" />
				    			</span></a>
			    			</c:if>
			    		</td>
		    		</c:forEach>
		    	</tr>
		    	
		    	
		    	<tr>
		    		<th>5:00-5:30</th>
		    		<c:forEach begin="1170" end="5170" var="i" step="1000" >
			    		<td>
			    			<c:forEach items="${scheduleTable.scheduleTableSections }" var="section">
			    				<c:if test="${section.startTime == i }">
			    					<c:forEach items="${section.advisors}" var="advisor" varStatus="s">
			    						<c:if test="${isForAdvisor == false }">
			    						<a href="<c:url value='/appointment/student/initialMakeAppointment.html?startTime=${section.startTime }&advisorId=${advisor.id }&studentId=${studentId }&id=${id }'/>">
			    							${advisor.name} 
			    						</a>
			    						</c:if>
			    						<c:if test="${isForAdvisor == true }" >
			    							${advisor.name}
			    						</c:if>
			    						<c:if test="${ not s.last }" >
			    						,
			    						</c:if>
			    					</c:forEach>
			    				</c:if>
			    			</c:forEach>
			    			<c:if test="${isForAdvisor == true }" >
				    			<a href="/cesar/user/advisorSchedule/editScheduleTable.html?startTime=${i }">
				    			<span class="imgLink">
				    			<img src="<c:url value='/resources/img/editIcon.png' />" alt="edit icon" />
				    			</span></a>
			    			</c:if>
			    		</td>
		    		</c:forEach>
		    	</tr>
		    	
		    	<tr>
		    		<th>5:30-6:00</th>
		    		<c:forEach begin="1170" end="5170" var="i" step="1000" >
			    		<td>
			    			<c:forEach items="${scheduleTable.scheduleTableSections }" var="section">
			    				<c:if test="${section.startTime == i+1 }">
			    					<c:forEach items="${section.advisors}" var="advisor" varStatus="s">
			    						<c:if test="${isForAdvisor == false }">
			    						<a href="<c:url value='/appointment/student/initialMakeAppointment.html?startTime=${section.startTime }&advisorId=${advisor.id }&studentId=${studentId }&id=${id }'/>">
			    							${advisor.name} 
			    						</a>
			    						</c:if>
			    						<c:if test="${isForAdvisor == true }" >
			    							${advisor.name}
			    						</c:if>
			    						<c:if test="${ not s.last }" >
			    						,
			    						</c:if>
			    					</c:forEach>
			    				</c:if>
			    			</c:forEach>
			    			<c:if test="${isForAdvisor == true }" >
				    			<a href="/cesar/user/advisorSchedule/editScheduleTable.html?startTime=${i+1 }">
				    			<span class="imgLink">
				    			<img src="<c:url value='/resources/img/editIcon.png' />" alt="edit icon" />
				    			</span></a>
			    			</c:if>
			    		</td>
		    		</c:forEach>
		    	</tr>
		    	<!-- c:if test="${scheduleTable.isRegisterPeriod }" -->
			    	<tr>
			    		<th>6:00-6:30</th>
			    		<c:forEach begin="1180" end="5180" var="i" step="1000" >
				    		<td>
				    			<c:forEach items="${scheduleTable.scheduleTableSections }" var="section">
				    				<c:if test="${section.startTime == i }">
				    					<c:forEach items="${section.advisors}" var="advisor" varStatus="s">
			    						<c:if test="${isForAdvisor == false }">
			    						<a href="<c:url value='/appointment/student/initialMakeAppointment.html?startTime=${section.startTime }&advisorId=${advisor.id }&studentId=${studentId }&id=${id }'/>">
			    							${advisor.name} 
			    						</a>
			    						</c:if>
			    						<c:if test="${isForAdvisor == true }" >
			    							${advisor.name}
			    						</c:if>
			    						<c:if test="${ not s.last }" >
			    						,
			    						</c:if>
			    					</c:forEach>
				    				</c:if>
				    			</c:forEach>
				    			<c:if test="${isForAdvisor == true }" >
					    			<a href="/cesar/user/advisorSchedule/editScheduleTable.html?startTime=${i }">
					    			<span class="imgLink">
					    			<img src="<c:url value='/resources/img/editIcon.png' />" alt="edit icon" />
					    			</span></a>
			    				</c:if>
				    		</td>
			    		</c:forEach>
			    	</tr>
			    	
			    	<tr>
			    		<th>6:30-7:00</th>
			    		<c:forEach begin="1180" end="5180" var="i" step="1000" >
				    		<td>
				    			<c:forEach items="${scheduleTable.scheduleTableSections }" var="section">
				    				<c:if test="${section.startTime == i+1 }">
				    					<c:forEach items="${section.advisors}" var="advisor" varStatus="s">
			    						<c:if test="${isForAdvisor == false }">
			    						<a href="<c:url value='/appointment/student/initialMakeAppointment.html?startTime=${section.startTime }&advisorId=${advisor.id }&studentId=${studentId }&id=${id }'/>">
			    							${advisor.name} 
			    						</a>
			    						</c:if>
			    						<c:if test="${isForAdvisor == true }" >
			    							${advisor.name}
			    						</c:if>
			    						<c:if test="${ not s.last }" >
			    						,
			    						</c:if>
			    					</c:forEach>
				    				</c:if>
				    			</c:forEach>
				    			<c:if test="${isForAdvisor == true }" >
					    			<a href="/cesar/user/advisorSchedule/editScheduleTable.html?startTime=${i+1 }">
					    			<span class="imgLink">
					    			<img src="<c:url value='/resources/img/editIcon.png' />" alt="edit icon" />
					    			</span></a>
			    				</c:if>
				    		</td>
			    		</c:forEach>
			    	</tr>
			    	
			    	<tr>
			    		<th>7:00-7:30</th>
			    		<c:forEach begin="1190" end="5190" var="i" step="1000" >
				    		<td>
				    			<c:forEach items="${scheduleTable.scheduleTableSections }" var="section">
				    				<c:if test="${section.startTime == i }">
				    					<c:forEach items="${section.advisors}" var="advisor" varStatus="s">
			    						<c:if test="${isForAdvisor == false }">
			    						<a href="<c:url value='/appointment/student/initialMakeAppointment.html?startTime=${section.startTime }&advisorId=${advisor.id }&studentId=${studentId }&id=${id }'/>">
			    							${advisor.name} 
			    						</a>
			    						</c:if>
			    						<c:if test="${isForAdvisor == true }" >
			    							${advisor.name}
			    						</c:if>
			    						<c:if test="${ not s.last }" >
			    						,
			    						</c:if>
			    					</c:forEach>
				    				</c:if>
				    			</c:forEach>
				    			<c:if test="${isForAdvisor == true }" >
					    			<a href="/cesar/user/advisorSchedule/editScheduleTable.html?startTime=${i }">
					    			<span class="imgLink">
					    			<img src="<c:url value='/resources/img/editIcon.png' />" alt="edit icon" />
					    			</span></a>
			    				</c:if>
				    		</td>
			    		</c:forEach>
			    	</tr>
			    	<tr>
			    		<th>7:30-8:00</th>
			    		<c:forEach begin="1190" end="5190" var="i" step="1000" >
				    		<td>
				    			<c:forEach items="${scheduleTable.scheduleTableSections }" var="section">
				    				<c:if test="${section.startTime == i+1 }">
				    					<c:forEach items="${section.advisors}" var="advisor" varStatus="s">
			    						<c:if test="${isForAdvisor == false }">
			    						<a href="<c:url value='/appointment/student/initialMakeAppointment.html?startTime=${section.startTime }&advisorId=${advisor.id }&studentId=${studentId }&id=${id }'/>">
			    							${advisor.name} 
			    						</a>
			    						</c:if>
			    						<c:if test="${isForAdvisor == true }" >
			    							${advisor.name}
			    						</c:if>
			    						
			    						<c:if test="${ not s.last }" >
			    						,
			    						</c:if>
			    					</c:forEach>
				    				</c:if>
				    			</c:forEach>
				    			<c:if test="${isForAdvisor == true }" >
					    			<a href="/cesar/user/advisorSchedule/editScheduleTable.html?startTime=${i+1 }">
					    			<span class="imgLink">
					    			<img src="<c:url value='/resources/img/editIcon.png' />" alt="edit icon" />
					    			</span></a>
			    				</c:if>
				    		</td>
			    		</c:forEach>
			    	</tr>
		    	<!-- /c:if -->
		    </tbody>
		</table>
     
<%@ include file="/WEB-INF/views/include/footer.jspf" %>