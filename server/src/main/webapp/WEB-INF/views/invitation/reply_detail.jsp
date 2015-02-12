<!DOCTYPE HTML>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>App接入--评论详情</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
		<script type="text/javascript" src="/resources/js/jquery.min.js"></script>
		<script type="text/javascript" src="/resources/js/NativeBridge.js"></script>
		<style type="text/css">
			.radius-8{-webkit-border-radius: 8px;-moz-border-radius: 8px;border-radius: 8px;-webkit-background-clip: padding-box;}
			.header, .content{background-color: white;}
			.header h2{color: #333;font: 15px "Helvetica-Bold", Helvetica, Geneva, Arial, sans-serif;margin: 0 0 3px 0}
			.header p {font-size: 13px;font-weight: bold;color: #666;display: block;margin: 0;}
			a:link {text-decoration:none;} 
			body {letter-spacing:1.5px;width:90%;margin-left:auto;margin-right:auto}
			.imgage{width: 200px;height: 100px;}
		</style>
	</head>
	<body>
		<div class="header">
			<div style="font-size:18px;color:#212121;margin-bottom:3px">${invitation.title }</div>
			<p>评论于&nbsp;<fmt:formatDate value="${invitationReply.crtDate }" pattern="yyyy-MM-dd HH:mm:ss" /></p>
			<p>评论来源&nbsp;${invitationReply.crUser}</p>
		</div>
		<hr size=1 color=#00ffff align=center noshade>	
		<div class="content" style="font-size:17px;color:#575757;line-height:25px">
			&nbsp;&nbsp;${invitationReply.introduce}
		</div>
		<div class="control-group" id="picFile">
	<label class="control-label" for="file-input">图片展示：</label>
	<div class="controls">
        <div class="gallery-set-thumbail">
         <c:forEach items="${pictures}" var="pic">
  			<img class="imgage" src="${pic.url}" style="width: 200px;height: 200px;"/>
  		</c:forEach>
  		</div>
	</div>
</div>
		<div></div>
		<hr size=1 color=#00ffff align=center noshade>
		<div class="header" id="default" style="display:none">
			相关评论:
			<div id="news">
			</div>
		</div>
	</body>
</html>