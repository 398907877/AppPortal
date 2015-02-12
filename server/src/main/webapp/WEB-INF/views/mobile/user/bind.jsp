<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<title>会员绑定</title>
	<link href="${ctx}/static/styles/default.css" type="text/css" rel="stylesheet" />
</head>

<body>
	
	<div class="col-md-10 ">
		<div class="bs-callout bs-callout-info">
			<h4>绑定会员信息</h4>
			这里是用于绑定您的微信账号到系统的会员用户信息中，实现用户信息同步。
		</div>
	</div>
	<form role="form" action="" class="col-md-10 ">
		<div class="form-group">
    		<input type="text" class="form-control" id="username" placeholder="请输入您的用户名">
  		</div>
  		<div class="form-group">
    		<input type="password" class="form-control" id="password" placeholder="请输入您的密码">
  		</div>
  		<button type="submit" class="btn btn-default">确认</button>
	</form>
</body>
</html>