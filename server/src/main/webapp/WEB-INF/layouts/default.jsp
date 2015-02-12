<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="cn" ng-app='<sitemesh:getProperty property="ng-app" default=""/>' xmlns:ng="http://angularjs.org">
<head>
<title>华科企业应用中心管理平台:<sitemesh:title/></title>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<meta http-equiv="Cache-Control" content="no-store" />
	<meta http-equiv="Pragma" content="no-cache" />
	<meta http-equiv="Expires" content="0" />
	
	<link type="image/x-icon" href="${ctx}/static/images/favicon.ico" rel="shortcut icon">
	<link href="${ctx}/static/bootstrap/3.1.1/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
	<link href="${ctx}/static/jquery-validation/1.11.1/validate.css" type="text/css" rel="stylesheet" />
	<link href="${ctx}/static/styles/default.css" type="text/css" rel="stylesheet" />
	<script src="${ctx}/static/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/bootstrap/3.1.1/js/bootstrap.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/jquery-validation/1.11.1/jquery.validate.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/jquery-validation/1.11.1/messages_bs_zh.js" type="text/javascript"></script>
	<script type="text/javascript" src="${ctx}/static/ckeditor/ckeditor.js"></script>
	<sitemesh:head/>
</head>

<body ng-controller='<sitemesh:getProperty property="body.ng-controller" default=""/>' id="<sitemesh:getProperty property="body.id" default=""/>">
	<shiro:authenticated> 
		<%@ include file="/WEB-INF/layouts/nav.jsp"%>
	</shiro:authenticated>
	<div class="container">
		<div id="content">
			<sitemesh:body/>
		</div>
	</div>
	
</body>
</html>