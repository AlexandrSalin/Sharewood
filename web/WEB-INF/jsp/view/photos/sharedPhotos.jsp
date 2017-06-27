<%@ taglib prefix="authz" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>myPhotos</title>
	<link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/2.3.1/css/bootstrap.min.css" />    
    <link rel="stylesheet" href="<c:url value="/resource/stylesheet/main.css" />" />    
</head>

<body>

		<h1>ShareWood</h1>
		
		<br /><br />
		<a href="<c:url value="/backHome"/>"><spring:message code="home" /></a><br /><br />
		
		<a href="<c:url value="/logout"/>"><spring:message code="logout" /></a><br /><br />
		
		<sec:authorize access="hasAnyRole('USER', 'ADMIN')">
			<h2><spring:message code="photos.shared" /></h2>
		</sec:authorize>
			
		<br /><br />
		<c:choose>
    		<c:when test="${photos.size() > 0}">
				<c:forEach items="${photos}" var="photo">
					<spring:message code="photo.id" />: <c:out value="${photo.id}"/><br/>
					<spring:message code="photo.title" />: <c:out value="${photo.title}"/><br/>
					<spring:message code="photo.shared" />: <c:out value="${photo.username}"/><br/>
    				<sec:authorize access="hasAnyRole('USER', 'ADMIN')">
      					<img src="<c:url value="/photos/doGetPhoto/${photo.id}"/>"><br/><br/>                   
    				</sec:authorize>
    			</c:forEach>
    		</c:when>
    
    		<c:otherwise>
    			<spring:message code="photos.none" />
    		</c:otherwise>
    
    	</c:choose>
	
</body>
</html>
