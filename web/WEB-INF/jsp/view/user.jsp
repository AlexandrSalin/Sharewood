<%@ taglib prefix="authz" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>allUsers</title>
	<link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/2.3.1/css/bootstrap.min.css" />    
    <link rel="stylesheet" href="<c:url value="/resource/stylesheet/main.css" />" />    
</head>

<body>

		<h1><spring:message code="user.roles"/></h1>
		<br /><br />
		
		<sec:authorize access="hasRole('ADMIN')">
			<spring:message code="user.name" />: <c:out value="${username}"/><br/><br/>
			<spring:message code="user.roles" />: <br/>
			<c:forEach items="${authorities}" var="auth">
				<c:out value="${auth.authority} "/>	
    		</c:forEach>
		</sec:authorize>
		
		<br /><br />
		<a href="<c:url value="/backHome"/>"><spring:message code="home" /></a><br /><br />
		
</body>
</html>
