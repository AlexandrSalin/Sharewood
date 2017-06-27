<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
	
	<h2><spring:message code="title.welcome" /></h2>
	<a href="<c:url value="/logout" />"><spring:message code="logout" /></a><br/><br/><br/>

 	<br/> 
 	<br/>
 	
    <div>
    	<sec:authorize access="hasRole('USER') and !hasRole('ADMIN')">
			<br />
			<a href="<c:url value="photos/myPhotos"/>"><spring:message code="photos.seeYour" /></a><br />
			
			<a href="<c:url value="photos/sharedPhotos"/>"><spring:message code="photos.seeShared" /></a><br />
				
			<a href="<c:url value="photos/updatePhoto"/>"><spring:message code="photo.update" /></a><br />		
		</sec:authorize>
	</div>
	

    <div>
        <sec:authorize access="hasRole('ADMIN')">
        	<br/>
        	<a href="<c:url value="admin/allPhotos"/>"><spring:message code="photos.all" /></a><br />
        </sec:authorize>
    </div>
 
	
	<div>
		<sec:authorize access="hasRole('USER')">
			<br />
			<a href="<c:url value="photos/createPhotoMulti"/>"><spring:message code="photo.create" /></a><br />
		</sec:authorize>
	</div>
	
	
	<div>
		<sec:authorize access="hasAnyRole('USER','ADMIN')">
			<br />
			<a href="<c:url value="photos/deletePhoto"/>"><spring:message code="photo.delete" /></a><br />
 		</sec:authorize>
 	</div>
 	

	<div>
    	<sec:authorize access="hasRole('ADMIN')">
			<br />
			<a href="<c:url value="admin/admin"/>"><spring:message code="admin.page" /></a><br />
		</sec:authorize>
	</div>
	
</body>
</html>