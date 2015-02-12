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
		<title>${product.title}</title>
		<link href="${ctx}/static/mobile/event.css" type="text/css" rel="stylesheet" />
		<meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no, minimal-ui">
	    <meta name="apple-mobile-web-app-capable" content="yes">
	    <meta name="apple-mobile-web-app-status-bar-style" content="black">
		<link rel="stylesheet" href="${ctx}/static/ratchet/css/ratchet.min.css">
	    <script src="${ctx}/static/ratchet/js/ratchet.min.js"></script>
		<script src="${ctx}/static/js/jquery.min.js" type="text/javascript"></script>
		<style type="text/css" >
			body,div,dl,dt,dd,ul,ol,li,h1,h2,h3,h4,h5,h6,pre,form,fieldset,input,textarea,p,blockquote,th,td {padding: 0;margin: 0;}
			span{font-size:20px;color:#272626;}
			.showtxt{font-size:17px; color: #777;margin-bottom:15px;margin-top:3px;}
		</style>
	</head>
	<body>
		<header class="bar bar-nav">
	    <a class="icon icon-left-nav pull-left" style="font-size:17px;padding-top:15px" href="javascript:history.go(-1);">返回</a>
	   <h1 class="title">产品详情</h1>
	    </header>
		<div class="content">
			<div class="slider">
		        <div class="slide-group">
			      	<c:forEach var="pic" items="${pictures}">
			          <div class="slide">
			            <img src="${pic.url}" width="100%" height="50%">
			          </div>
			       </c:forEach>
		        </div>
	    	</div>
        	<div class="table-view-cell" style="padding-left:10px;padding-right:10px;line-height:30px" >
        	    <div>
	        	<span >产品名称：</span>
	        	<span class="showtxt">${product.title}</span>
	        	</div>
	        	 <div>
	        	<span >产品类型：</span>
	        	<span class="showtxt" >${category.name}</span>
	        	 </div>
	        	  <div>
	        	<span >产品价格：</span>
	            <span class="showtxt" >${product.price} 元</span>
	             </div>
	              <div>
	        	<span >产品库存：</span>
	        	<span class="showtxt" >${product.total}</span>
	        	</div>
	        	 <div>
	        	<span>产品运费：</span>
	        	<span class="showtxt" >${product.freight} 元</span>
	        	 </div>
	        	  <div>
	        	<span >限购数：</span>
	        	<span class="showtxt" >${product.buyLimit}</span>
        	   </div>
        	    <div>
	        	<span >产品简介：</span></br>
	        	<span class="showtxt" style="font-size:14px;">${product.intro}</span>
        	   </div>
        	    <div>
	        	<span >详细内容：</span>
	        	<span class="showtxt">${product.detail}</span>
        	   </div>
        	</div>
	    </div>
	</body>
</html>