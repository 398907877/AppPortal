<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<%@ page session="false"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>APP用户信息</title>
<style type="text/css">
	p span{width: 100px; display: inline-block; font-weight: bold;}
	#customer{float: left; }
	#type{float: right; width: 50%;}
	.clear{clear: both;}
</style>
</head>
<body>
		<div class="page-header">
			<h2>用户名：${forumUser.name }</h2>
		</div>
		<div>
			<div id="forumUser" >
			<p>
			<c:if test="${forumUser.avatar!=null&&forumUser.avatar !=''}">
			<img src="${forumUser.avatar}">
			</c:if>
			<c:if test="${forumUser.avatar==null||forumUser.avatar ==''}">
			<img alt="暂无头像" src="${forumUser.avatar}">
			</c:if></p>
		
			<p><span>登入名：</span>${forumUser.loginname }</p>
			<p><span>出生年月：</span>${forumUser.birth }</p>
			<p><span>联系方式：</span>${forumUser.mobile }</p>
			<p><span>创建时间：</span><fmt:formatDate value="${forumUser.createDate}" pattern="yyyy/MM/dd  HH:mm:ss" /></p>
			<p><span>单位：</span>${forumUser.unit }</p>
			<p><span>状态：</span>${forumUser.status  == 1 ? '正常' : '注销'}</p>
		</div>
		
	
		</div>
		<div class="clear"></div>
</body>
</html>