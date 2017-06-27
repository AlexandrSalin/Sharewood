<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>  
    <title><spring:message code="title.welcome" /></title>
    <link rel="stylesheet" href="/sharewood/resource/stylesheet/main.css" />
</head>
<body>
	Language:<br/>
	<a href="?locale=fr_FR">Français</a><br/>
	<a href="?locale=en_US">English</a><br/>
	<br/>

	<h1>
	<spring:message code="login.ask" />
	</h1>
	<br />

 	<c:if test="${param.containsKey('loginFailed')}">
        <b class="error"><spring:message code="error.login.failed" /></b><br />
    </c:if>
    
    <form:form method="post" modelAttribute="loginForm" autocomplete="off" >
        <form:label path="username"><spring:message code="field.login.username" /></form:label><br />
        <form:input path="username" autocomplete="off" /><br /><br />
        <form:label path="password"><spring:message code="field.login.password" /></form:label><br />
        <form:password path="password" autocomplete="off" /><br />
        <input type="submit" value="<spring:message code="field.login.submit" />" />
    </form:form>
	
	<br/><br/>
	<a href="register"><spring:message code="register.link" /></a>
	
</body>
</html>
