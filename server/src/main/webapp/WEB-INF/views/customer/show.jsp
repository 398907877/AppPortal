<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<%@ page session="false"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>会员企业信息显示（合作企业）</title>
<style type="text/css">
	p span{width: 100px; display: inline-block; font-weight: bold;}
	#customer{float: left; width: 50%;}
	#type{float: right; width: 50%;}
	.clear{clear: both;}
</style>
</head>
<body>
		<div class="page-header">
			<h2>企业：${customer.name }</h2>
		</div>
		<div>
			<div id="customer" >
			
			<p><span>创建时间：</span><fmt:formatDate value="${customer.createDate}" pattern="yyyy/MM/dd  HH:mm:ss" /></p>
			<p><span>联系电话：</span>${customer.tel}</p>
			<p><span>企业传真：</span>${customer.fax }</p>
			<p><span>联系地址：</span>${customer.address }</p>
			<p><span>企业介绍：</span>${customer.content }</p>
			<p><span>企业产品：</span>${customer.products }</p>
		</div>
		
		<div id="type">
		<p><span>所属分类</span></p>
	
		<c:forEach items="${customer.customerTypes}" var="itemx" varStatus="i">
		      <div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${itemx.name}</div>
		</c:forEach>
		
		</div>
		</div>
		<div class="clear"></div>
</body>
</html>