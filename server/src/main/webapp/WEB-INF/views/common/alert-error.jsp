<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<spring:hasBindErrors name="game" htmlEscape="true">
       <div class="alert alert-error">
       	<a class="close" data-dismiss="alert">×</a>
       	<h4 class="alert-heading">数据提交未能成功, 请重试，原因 :</h4> 
           <ul>
           <c:forEach var="error" items="${errors.allErrors}">
               <li>${error.defaultMessage}</li>
           </c:forEach>
           </ul>
       </div>
</spring:hasBindErrors>