<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<%@ taglib prefix='security'
	uri='http://www.springframework.org/security/tags'%>
<%@ page isELIgnored="false"%>
<c:url value="/j_spring_security_logout" var="logoutUrl" />
<html>
	<body>
		<h1 id="banner">Welcome to Spring Security MongoDB User Page</h1>
		<h2>Your User info</h2>
		 <h3><a href="${logoutUrl}">Click here to logout</a></h3> 
		 Username:  ${userName}<br>
		 Role: ${role}	
	</body>
</html>