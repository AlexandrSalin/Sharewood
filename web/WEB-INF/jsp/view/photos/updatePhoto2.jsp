<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>updatePhoto</title>
    <link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/2.3.1/css/bootstrap.min.css" />    
    <link rel="stylesheet" href="<c:url value="/resource/stylesheet/main.css" />" />    
</head>
<body>

  <h1><spring:message code="photo.update" /></h1>
  
  
  
<form:form method="POST" action="updatePhoto2" modelAttribute="photo">
  	<table>
  	<tr>
        <td><form:label path="id"><spring:message code="photo.id" />:</form:label></td>
        <td><form:input path="id" disabled="true"/></td>
    </tr>
    <tr>
        <td><form:label path="title"><spring:message code="photo.title" />:</form:label></td>
        <td><form:input path="title" disabled="false"/></td>
    </tr>
     <tr>
        <td><form:label path="username"><spring:message code="field.login.username" />:</form:label></td>
        <td><form:input path="username" disabled="true"/></td>
    </tr>
    <tr>
    	<td><form:label path="shared"><spring:message code="photo.shared" />:</form:label></td>
                <td><input type="radio" name="shared" value="true" /><spring:message code="shared.true" /> 
        			<input type="radio" name="shared" value="false" checked /><spring:message code="shared.false" /></td>
        <td><form:errors path="shared" class="error"/></td>  
    </tr>	

    <tr>
        <td colspan="2">
            <input type="submit" value="<spring:message code="valid"/>"/>
        </td>
    </tr>
</table>               
    <form:hidden path="id" />
    <form:hidden path="username" />
</form:form>

<img src="<c:url value="/photos/doGetPhoto/${photo.id}"/>"><br/><br/>                   
    				
 
  


</body>
</html>