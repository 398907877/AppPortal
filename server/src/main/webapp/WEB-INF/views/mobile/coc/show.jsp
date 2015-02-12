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
		<title>${tcustomer.name}</title>
		<link href="${ctx}/static/mobile/event.css" type="text/css" rel="stylesheet" />
		<meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no, minimal-ui">
	    <meta name="apple-mobile-web-app-capable" content="yes">
	    <meta name="apple-mobile-web-app-status-bar-style" content="black">
	 
		<style type="text/css" >
			body,div,dl,dt,dd,ul,ol,li,h1,h2,h3,h4,h5,h6,pre,form,fieldset,input,textarea,p,blockquote,th,td {padding: 0;margin: 0;}
			span{font-size:17px;color:#272626;}
			.showtxt{font-size:14px; color: #777;margin-bottom:15px;margin-top:3px;}
		</style>
	</head>
	<body>
		<header class="bar bar-nav">
	    <a class="icon icon-left-nav pull-left" style="font-size:17px;padding-top:15px" href="javascript:history.go(-1);">返回</a>
	      <h1 class="title">企业详情</h1>
	    </header>
		<div class="content">
			<div class="table-view-cell media" style="padding-right: 25px;">		
			    <img src="${tcustomer.pic}" class="media-object pull-left" width="80px" height="80px">
			    <div class="media-body" style="font-size:20px;margin-top: 10px;margin-left:100px;">
			   ${tcustomer.name}
			    </div>
		
        	</div>
        	<div class="table-view-cell" style="padding-left:10px;padding-right:15px;line-height:30px" >
        
	        	<div>
	        	<span >电话：</span>
	        	<span class="showtxt">${tcustomer.tel}</span>
	        	</div>
	        	<div>
	        	<span  >地址：</span>
	        	<span class="showtxt" >${tcustomer.address}</span>
	           </div>
	          <div>
	        	<span >企业介绍：</span>
	        	<div   class="showtxt" style="line-height:20px;">${tcustomer.content}</div>
	        </div>
	        <div>
	        	<span  >服务产品：</span>
	        	<div  class="showtxt" style="line-height:20px;">${tcustomer.products}</div>
        	</div>
	    </div>
	</body>
</html>