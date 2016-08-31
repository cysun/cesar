<?xml version="1.0" encoding="iso-8859-1"?>
<%@ include file="/WEB-INF/views/include/taglib.jspf" %>
<c:set var="headElements">
  <title>CESAR - Email Advisement</title>

</c:set>
<%@ include file="/WEB-INF/views/include/header.jspf" %>

<c:set var="user" value="${advisement.student}" />

<h3><a href="<c:url value='/user/search/student/search.html' />">Students</a>
&gt; <a href="<c:url value='/user/student/display.html?userId=' />${user.id}">${user.name}</a> &gt;Email Advisement</h3>

<form:form modelAttribute="email">
<table style="width: 90%;" cellpadding="2" cellspacing="2" border="0">
  <tr>
    <td style="width: 1%;"><b>To:</b></td>
    <td><tt>${email.recipients[0].name}</tt></td>
  </tr>
  <tr>
    <td><b>Subject:</b></td>
    <td>
      <form:input path="subject" cssClass="leftinput" cssStyle="width: 99%;" />
    </td>
  </tr>
  <tr>
    <td><b>Content:</b></td>
    <td>
      <form:textarea id="textcontent" path="content" cssClass="leftinput" cssStyle="width: 99%;" rows="15" cols="75" />
    </td>
  </tr>

  <tr>
    <td style="text-align: left;" colspan="2">
      <input type="submit" class="subbutton" name="send" value="Send" />
    </td>
  </tr>
</table>
<input type="hidden" name="advisementId" value="${advisement.id}" />
<input type="hidden" name="sendTo" value="${email.recipients[0].id }" />
</form:form>


<%@ include file="/WEB-INF/views/include/footer.jspf" %>