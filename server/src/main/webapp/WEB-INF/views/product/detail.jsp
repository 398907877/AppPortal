<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>产品详情</title>
	<link href="${ctx}/static/bootstrap/3.1.1/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
</head>

<body>
	<form id="inputForm" action="${ctx}/mgr/product/${action}" method="post" class="form-horizontal">
		<input type="hidden" name="id" value="${product.id}"/>
		<fieldset>
		
			<legend>
				产品详情信息
			</legend>


<div class="form-group">
	<label class="col-sm-2 control-label" for="title">产品名称:</label>
	<div class="col-sm-8 controls">
		<p class="form-control-static">${product.title }</p>
	</div>
</div>

<div class="form-group">
	<label class="col-sm-2 control-label" for="title">产品类型:</label>
	<div class="col-sm-8 controls">
		<p class="form-control-static">${category.name }</p>
	</div>
</div>
<div class="form-group">
	<label class="col-sm-2 control-label" for="price">产品价格:</label>
	<div class="col-sm-10 controls">
		<p class="form-control-static">${product.price }</p>
	</div>
</div>
<div class="form-group">
	<label class="col-sm-2 control-label" for="total">产品库存:</label>
	<div class="col-sm-10 controls">
		<p class="form-control-static">${product.total }</p>
	</div>
</div>

<div class="form-group">
	<label class="col-sm-2 control-label" for="freight">产品运费:</label>
	<div class="col-sm-10 controls">
		<p class="form-control-static">${product.freight }</p>
	</div>
</div>
<div class="form-group">
	<label class="col-sm-2 control-label" for="buyLimit">限购数:</label>
	<div class="col-sm-10 controls">
		<p class="form-control-static">${product.buyLimit }</p>
	</div>
</div>

<div class="form-group">
	<label class="col-sm-2 control-label" for="intro">产品简介:</label>
	<div class="col-sm-8 controls">
		<p class="form-control-static">${product.intro }</p>
	</div>
</div>


<div class="form-group">
	<label class="col-sm-2 control-label" for="detail">详细内容:</label>
	<div class="col-sm-8 controls">
		<p class="form-control-static"> ${product.detail }</p>
	</div>
</div>

<%--  <div class="form-group" id="picFile">
	<label class="col-sm-2 control-label" for="file-input">企业图片:</label>
	<div class="col-sm-8 controls">
        <div class="gallery-set-thumbail">
        			<c:forEach items="${pictures}" var="pic">
  					<img class="imgage" src="${pic.url}" style="width: 200px;height: 200px;margin-top:10px"/>
  					</c:forEach>
  				</div>
	</div>
</div> --%>
		</fieldset>
	</form>
	<script>
	
		$(document).ready(function() {
			//聚焦第一个输入框
			$("#task_title").focus();
		});
	</script>
</body>
</html>
