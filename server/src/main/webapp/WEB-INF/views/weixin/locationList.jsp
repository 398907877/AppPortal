<!DOCTYPE>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>产品管理</title>
</head>

<body>
	<div class="row">
		<div class="col-md-2">
			<%@ include file="nav.jsp"%>
		</div>	
		<div class="col-md-10 ">
			<div class="bs-callout bs-callout-info">
				<h4>微信位置服务管理</h4>
				<p>用于微信用户发送位置后的服务设置。</p>
			</div>
			
			<form:form role="form" method="post" modelAttribute="rule" action="${ctx}/mgr/weixin/location/saveCfg">
			  <div class="form-group">
			    <label for="exampleInputEmail1">appid</label>
			    <input type="text" class="form-control" id="appid" name="appid" ng-model="appid">
			    <span class="help-block">看看该填什么?</span>
			  </div>
			  <div class="form-group">
			    <label for="exampleInputPassword1">Secure</label>
			    <input type="text" class="form-control" id="secure" name="secure" ng-model="secure">
			    <span class="help-block">看看该填什么?</span>
			  </div>
			  <div class="form-group">
			    <label for="exampleInputPassword1">Token</label>
			    <input type="text" class="form-control" id="token" name="token">
			    <span class="help-block">看看该填什么?<span class="glyphicon glyphicon-exclamation-sign"></span></span>
			  </div>
			  <div class="form-group">
			    <label for="exampleInputPassword1">AccessToken</label>
			    <input type="text" class="form-control" id="accessToken" name="accessToken" readonly="readonly" ng-model="accesstoken">
			    <a href="#" ng-click="requestAccess();"><span class="help-block">点击获取按钮获取</span></a>
			  </div>
			  <button type="submit" class="btn btn-default">保存</button>
			</form:form>
			
		</div>
		
	</div>
</body>

</html>