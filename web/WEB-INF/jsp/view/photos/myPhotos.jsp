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
	
		<sec:authorize access="hasRole('USER')">
			<h1><spring:message code="title.photos.your" /></h1>
		</sec:authorize>
		
		<sec:authorize access="hasRole('ADMIN')">
			<h1><spring:message code="title.photos.all" /></h1>
		</sec:authorize>
		
		<br /><br />
		<a href="<c:url value="/backHome"/>"><spring:message code="home" /></a><br /><br />
		
		<a href="<c:url value="/logout"/>"><spring:message code="logout" /></a><br /><br />
							
		<br /><br />
		<c:choose>
    		<c:when test="${photos.size() > 0}">
				<c:forEach items="${photos}" var="photo">
					<c:set var="shared" value="shared.${photo.shared}" />
					<spring:message code="photo.id" />: <c:out value="${photo.id}"/><br/>
					<spring:message code="photo.title" />: <c:out value="${photo.title}"/><br/>
					<spring:message code="photo.shared" />: <spring:message code="${shared}"/><br/>
					<sec:authorize access="hasAnyRole('USER', 'ADMIN', 'DBA')">
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
