<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<%@ page session="false"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>信息显示</title>

</head>
<body>
	
	<div >
	<c:if test="${key == 'pub'}">
		${tenancy.appId }
	</c:if>
	<c:if test="${key == 'pri'}">
		${tenancy.appSecret }
	</c:if>
	</div>
		
		
		<div class="clear"></div>
</body>
</html>