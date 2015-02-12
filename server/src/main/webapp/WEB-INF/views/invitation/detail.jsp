<!DOCTYPE HTML>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>App接入 -帖子详情</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
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
		<div class="list-group col-md-11" >
 			<div class="list-group-item list-group-item-success">
    			<h4>${invitation.title }<small class="pull-right text-primary">楼主：${invitation.crUser }   <fmt:formatDate value="${invitation.crtDate }" pattern="yyyy-MM-dd HH:mm:ss" /></small></h4>
    			<p class="text-info">${invitation.introduce}</p>
    			
    			 <c:forEach items="${pictures}" var="pic">
  					<img class="img-thumbnail" src="${pic.url}" />
  				</c:forEach>
  			</div>
  			
  			<c:forEach items="${invitationReplys}" var="invitationReply" varStatus="idxStatus">
  				 <div class="list-group-item ">
			    ${idxStatus.index+1}楼:${invitationReply.crUser}   <fmt:formatDate value="${invitationReply.crtDate }" pattern="yyyy-MM-dd HH:mm:ss" /><p>${invitationReply.introduce }</p>
			    </div>
			</c:forEach>
	
</div>

	</body>
</html>