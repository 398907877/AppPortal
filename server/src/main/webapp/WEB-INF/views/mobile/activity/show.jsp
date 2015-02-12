<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page session="false"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
	<head>
		<title>${event.title}</title>
		<link href="${ctx}/static/mobile/event.css" type="text/css" rel="stylesheet" />
		<meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no, minimal-ui">
	    <meta name="apple-mobile-web-app-capable" content="yes">
	    <meta name="apple-mobile-web-app-status-bar-style" content="black">

		<style type="text/css" >
			body,div,dl,dt,dd,ul,ol,li,h1,h2,h3,h4,h5,h6,pre,form,fieldset,input,textarea,p,blockquote,th,td {padding: 0;margin: 0;}
			span{font-size:20px;color:#272626;}
			.showtxt{font-size:17px; color: #777;margin-bottom:15px;margin-top:3px;}
		</style>
	</head>
	<body>
		<header class="bar bar-nav">
	    <a class="icon icon-left-nav pull-left" style="font-size:17px;padding-top:15px" href="javascript:history.go(-1);">返回</a>
	   <h1 class="title">活动详情</h1>
	    </header>
		<div class="content">
			<div class="slider">		
			    <img src="${event.poster}" class="media-object pull-left" width="100%" height="50%">
        	</div>
        	<div class="table-view-cell" style="padding-left:10px;padding-right:15px;line-height:30px">
        	    <div>
	        	<span >活动标题：</span>
	        	<span class="showtxt">${event.title}</span>
	        	</div>
	        	 <div>
	        	<span >时间：</span>
	        	<span class="showtxt" >${fn:substring(event.startDate,0,10)}</span>
	        	 </div>
	        	  <div>
	        	<span >地点：</span>
	            <span class="showtxt" >${event.address}</span>
	             </div>
	              <div>
	        	<span >联系人：</span>
	        	<span class="showtxt" >${event.linkman}</span>
	        	</div>
	        	 <div>
	        	<span>联系电话：</span>
	        	<span class="showtxt" >${event.tel}</span>
	        	 </div>
	        	  <div>
	        	<span >活动介绍：</span>
	        	<div class="showtxt" style="line-height:20px;">${event.info}</div>
        	   </div>
        	</div>
	    </div>
	</body>
</html>