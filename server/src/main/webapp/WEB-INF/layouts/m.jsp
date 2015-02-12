<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="cn">
<head>
	<meta charset="utf-8">
	<title><sitemesh:title/></title>
	<meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no, minimal-ui">
	<meta name="apple-mobile-web-app-capable" content="yes" />
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<meta http-equiv="Cache-Control" content="no-store" />
	<meta http-equiv="Pragma" content="no-cache" />
	<meta http-equiv="Expires" content="0" />
	<!--  
	<link href="${ctx}/static/bootstrap/3.1.1/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
	-->
		<script src="${ctx}/static/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
	
	<link href="${ctx}/static/ratchet/css/ratchet.min.css" type="text/css" rel="stylesheet" />
	 <script src="${ctx}/static/ratchet/js/ratchet.min.js"></script>
	<script>
		document.createElement( "picture" );
	</script>
    <script type="text/javascript" src="${ctx}/static/mobile/picturefill.min.js"></script>
	<sitemesh:head/>
</head>

<body>
	<sitemesh:body/>
	<script type="text/javascript" src="${ctx}/static/ratchet/js/ratchet.min.js"></script>
</body>
</html>