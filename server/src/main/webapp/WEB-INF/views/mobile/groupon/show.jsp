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
		<title>标题</title>
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
	   <h1 class="title">团购详情</h1>
	    </header>
		<div class="content">
        	<div class="table-view-cell" style="padding-left:10px;padding-right:15px;line-height:30px">
        	    <div>
	        	<span >团购标题：</span>
	        	<span class="showtxt">标题</span>
	        	</div>
	        	 <div>
	        	<span >团购时间：</span>
	        	<span class="showtxt" >${fn:substring(groupOn.startDate,0,10)}——${fn:substring(groupOn.endDate,0,10)}</span>
	        	 </div>
	        	  <div>
	        	<span >团购价：</span>
	            <span class="showtxt" >￥${groupOn.groupOnPrice}</span>
	             </div>
	              <div>
	        	<span >原价：</span>
	        	<span class="showtxt" >￥${groupOn.originPrice}</span>
	        	</div>
	        	 <div>
	        	<span>折扣：</span>
	        	<span class="showtxt" >${groupOn.discount}</span>
	        	 </div>
	        	 <div>
	        	<span>参与人数: </span>
	        	<span class="showtxt" >${groupOn.peopleCount}</span>
	        	 </div>
	        	 <div>
	        	<span>销量：</span>
	        	<span class="showtxt" >${groupOn.sales}</span>
	        	 </div>
	        	 <div>
	        	<span>库存：</span>
	        	<span class="showtxt" >${groupOn.stock}</span>
	        	 </div>
	        	  <div>
	        	<span>团购说明：</span>
	        	<div class="showtxt" style="line-height:20px;">${groupOn.content}</div>
        	   </div>
        	</div>
	    </div>
	</body>
</html>