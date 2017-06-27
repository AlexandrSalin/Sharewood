<%@ page import="org.springframework.security.core.AuthenticationException" %>
<%@ page import="org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter" %>
<%@ page import="org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException" %>
<%@ taglib prefix="authz" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Sharewood</title>

<link type="text/css" rel="stylesheet"
		href="<c:url value="/resource/stylesheet/main.css" />" />

</head>

<body>
	<div>
		<h1><spring:message code="access.confirmation" /></h1>
		
		<c:remove scope="session" var="SPRING_SECURITY_LAST_EXCEPTION" />

		<authz:authorize access="hasRole('USER')">
			<br />
			<h2><spring:message code="confirm.ask" /></h2>

			<p>
			<spring:message code="confirm.confirm">
			   	<spring:argument value="${client.clientId}" />
    		</spring:message> 
			</p>

			<form id="confirmationForm" name="confirmationForm"
				action="<c:out value="${pageContext.request.contextPath}" />/oauth/authorize" method="post">
				<input name="user_oauth_approval" value="true" type="hidden" />
				<ul>
					<c:forEach items="${scopes}" var="scope">
						<c:set var="approved">
							<c:if test="${scope.value}"> checked</c:if>
						</c:set>
						<c:set var="denied">
							<c:if test="${!scope.value}"> checked</c:if>
						</c:set>
						<li>
							<div>
								${scope.key}: <input type="radio" name="${scope.key}"
									value="true" ${approved} /><spring:message code="approve" />
									<input type="radio"
									name="${scope.key}" value="false" ${denied} /><spring:message code="deny" />
							</div>
						</li>
					</c:forEach>
				</ul>
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
				<button type="submit"><spring:message code="confirm" /></button>
			</form>

		</authz:authorize>

	</div>

</body>
</html>
