<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<%@ page session="false"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>通讯信息</title>
<style type="text/css">
	p span{width: 100px; display: inline-block; font-weight: bold;}
	#customer{float: left; }
	#type{float: right; width: 50%;}
	.clear{clear: both;}
</style>
</head>
<body>
		<div class="page-header">
			<h2>姓名：${addressList.name }</h2>
		</div>
		<div>
			<div id="customer" >
			<p><span>现居职位：</span>${addressList.position }</p>
			<p><span>创建时间：</span><fmt:formatDate value="${addressList.createDate}" pattern="yyyy/MM/dd  HH:mm:ss" /></p>
			<p><span>私人电话：</span>${addressList.tel}</p>
			<p><span>办公电话：</span>${addressList.officePhone }</p>
		</div>
		
	
		</div>
		<div class="clear"></div>
</body>
</html>