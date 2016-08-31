<?xml version="1.0" encoding="iso-8859-1"?>
<%@ include file="/WEB-INF/views/include/taglib.jspf" %>
<c:set var="headElements">
<title>CESAR-Email</title>
</c:set>
<%@ include file="/WEB-INF/views/include/header.jspf" %>

<form:form modelAttribute="email">
<table style="width: 90%;" cellpadding="2" cellspacing="2" border="0">
  <tr>
    <td style="width: 1%;"><b>To:</b></td>
    <td>
    	<tt>${email.recipients[0].name}
    		<c:if test="${fn:length(email.recipients) > 1}">
    			<c:forEach 
   					begin="1" end="${fn:length(email.recipients)-1}" var="i">,
   					 ${email.recipients[i].name}
   				</c:forEach>
   			</c:if>
   		</tt>
    </td>
  </tr>
  <tr>
    <td><b>Subject:</b></td>
    <td>
      <form:input path="subject" cssClass="leftinput" cssStyle="width: 99%;" />
      <div class="error"><form:errors path="subject" /></div>
    </td>
  </tr>
  <tr>
    <td><b>Content:</b></td>
    <td>
      <form:textarea id="textcontent" path="content" cssClass="leftinput" cssStyle="width: 99%;" rows="15" cols="75" />
      <div class="error"><form:errors path="content" /></div>
    </td>
  </tr>
  <tr>
    <td style="text-align: left;" colspan="2">
      <input type="submit" class="subbutton" name="send" value="Send" />
    </td>
  </tr>
</table>

</form:form>


<%@ include file="/WEB-INF/views/include/footer.jspf" %>
