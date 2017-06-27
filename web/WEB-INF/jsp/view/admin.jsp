<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>  
    <title>admin</title>
    <link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/2.3.1/css/bootstrap.min.css" />    
    <link rel="stylesheet" href="<c:url value="/resource/stylesheet/main.css" />" />    
</head>
<body>

	<h2><spring:message code="title.welcome.admin" /></h2>
	<a href="<c:url value="/logout" />"><spring:message code="logout" /></a><br/><br/><br/>

 	<br/> 
 	<br />
 	
    <div>
    	<sec:authorize access="hasRole('ADMIN')">
			<br />
			<a href="<c:url value="/admin/allUsers"/>"><spring:message code="users.seeAll" /></a><br />
		</sec:authorize>
	</div>
	
	<div>
    	<sec:authorize access="hasRole('ADMIN')">
			<br />
			<a href="<c:url value="/admin/userAuthorities"/>"><spring:message code="users.seeRoles" /></a><br />
		</sec:authorize>
	</div>
	
	
</body>
</html>