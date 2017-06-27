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

		<sec:authorize access="hasRole('ADMIN')">
		
			<h1><spring:message code="users.all"/></h1>
		
			<br /><br />
			<c:choose>
    			<c:when test="${users.size() > 0}">
					<c:forEach items="${users}" var="user">
						<spring:message code="user.name" />: <c:out value="${user.username}"/><br/><br/>
    				</c:forEach>
    			</c:when>
    
    			<c:otherwise>
    				<c:out value="No user found"/>
    			</c:otherwise>
		
			</c:choose>
		</sec:authorize>
		
		<br /><br />
		<a href="<c:url value="/backHome"/>"><spring:message code="home" /></a><br /><br />
		
</body>
</html>
