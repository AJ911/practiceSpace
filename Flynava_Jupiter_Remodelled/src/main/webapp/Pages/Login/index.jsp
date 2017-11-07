<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
  <head>
 <style type="text/css">
    #msg { 
  color: green;
  font-size: 16px;
  font-weight: 600;
  font-style: italic;
  font-family: "Times New Roman", Times, serif;
}
#error { 
  color: red;
  font-size: 16px;
  font-weight: 600;
  font-style: italic;
  font-family: "Times New Roman", Times, serif;
}
    </style>
  </head>
	<body>
		<h1 id="banner">Spring Security with MongoDB</h1>  
		<form action="<c:url value='j_spring_security_check'/>" method="POST" class="form-horizontal" role="form">
			<table>
				<tr>
					<td>Username:</td>
					<td><input type='text' name='username' /></td>
				</tr>
				<tr>
					<td>Password:</td>
					<td><input type='password' name='password'></td>
					 <input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
				</tr>
				<tr>
					<td colspan="2">&nbsp;</td>
				</tr>
				<tr>
					<td colspan='2'><input value="Login" name="submit" type="submit">&nbsp;<input value="Reset" name="reset" type="reset"></td>
				</tr>
			</table>
			<div id="error">${error}</div>
			    <div id="msg">${msg}</div>
		</form>
	</body>
</html>