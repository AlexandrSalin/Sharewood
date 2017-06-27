<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>  
    <title><spring:message code="title.welcome" /></title>
	<link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/2.3.1/css/bootstrap.min.css" />    
    <link rel="stylesheet" href="<c:url value="/resource/stylesheet/main.css" />" />    
</head>
<body>
	Language:<br/>
	<a href="?locale=fr_FR">Français</a><br/>
	<a href="?locale=en_US">English</a><br/>
	<br/>
	<h2><spring:message code="title.register" /></h2>
	<br/><br/>

    <form:form method="post" modelAttribute="registerForm" autocomplete="off" >
        <table>
        <tr>
        	<td><form:label path="username"><spring:message code="field.login.username" /></form:label></td>
        	<td><form:input path="username" autocomplete="off" /></td>
        	<td><form:errors path="username" class="error" /></td>
        </tr>
        <tr>
        	<td><form:label path="password"><spring:message code="field.login.password" /></form:label></td>
        	<td><form:password path="password" autocomplete="off" /></td>
        	<td><form:errors path="password" class="error" /></td>
        </tr>
        <tr>
        <td colspan="2"><input type="submit" value="<spring:message code="field.register.submit" />" /></td>
   		</tr>
   		</table>
    </form:form>
	
</body>
</html>