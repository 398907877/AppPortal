<%-- <%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>产品详情</title>
	<script src="${ctx}/static/ckeditor/ckeditor.js" type="text/javascript"></script>
</head>

<body>
	<form id="inputForm" action="${ctx}/mgr/supply/${action}" method="post" class="form-horizontal">
		<input type="hidden" name="id" value="${product.id}"/>
		<fieldset>
		
			<legend>
			供求信息详情
			</legend>


<div class="control-group">
	<label class="control-label" for="title">供求标题</label>
	<div class="controls">
		<input  disabled="disabled" name="title" class="input" value="${supplyDemand.title }"/>
	</div>
</div>



<div class="control-group">
	<label class="control-label" for="title">供求类型</label>
	<div class="controls">
	<c:if test="${supplyDemand.type eq 0}">
		<input  disabled="disabled" name="title" class="input" value="供"/>
	</c:if>
	
	<c:if test="${supplyDemand.type eq 1}">
		<input  disabled="disabled" name="title" class="input" value="求"/>
	</c:if>
	</div>
</div>

<div class="control-group">
	<label class="control-label" for="title">信息类型</label>
	<div class="controls">
		<input  disabled="disabled" name="title" class="input" value="${supplyDemandType.supplyDemandType }"/>
	</div>
</div>

<div class="control-group">
	<label class="control-label" for="title">联系人</label>
	<div class="controls">
		<input  disabled="disabled" name="title" class="input" value="${supplyDemand.toUser }"/>
	</div>
</div>


<div class="control-group">
	<label class="control-label" for="title">发布时间</label>
	<div class="controls">
		<input  disabled="disabled" name="title" class="input" value="${supplyDemand.crtDate }"/>
	</div>
</div>

    <div class="control-group">
	<label class="control-label" for="title">简介</label>
	<div class="controls">
		<input  name="introduce" style="width: 400px;height: 50px;" value="${supplyDemand.introduce }"/>
	</div>
 </div>

<div class="control-group">
	<label class="control-label" for="detail">详细内容</label>
	<div class="controls">
		<textarea rows="6" disabled="disabled"  name="detailInfo" class="input-xxlarge"  placeholder="请填写详细内容">${supplyDemand.introduce }</textarea>
	</div>
</div>

		</fieldset>
	</form>
	<script type="text/javascript">
/* 		CKEDITOR.replace('intro',{width:600,filebrowserUploadUrl:'${ctx}/mgr/product/upload'}); */
		function getContentDetail(){
			return CKEDITOR.instances.contentDetail.getData(); 
		}
		CKEDITOR.replace('detailInfo',{filebrowserUploadUrl:'${ctx}/appportal/mgr/supply/upload'});
		
	</script>  
</body>
</html> --%>

<!DOCTYPE HTML>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>供求信息详情</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
		<link href="${ctx}/static/bootstrap/3.1.1/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
	</head>
	<body>
	<form id="inputForm" action="" method="post" class="form-horizontal">
		<fieldset>
		
			<legend>
				供求信息详情
			</legend>
			<div class="form-group">
				<label class="col-sm-2 control-label" for="title">信息标题:</label>
				<div class="col-sm-8 controls">
					<p class="form-control-static">${supplyDemand.title }</p>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label" for="title">发布时间:</label>
				<div class="col-sm-8 controls">
					<p class="form-control-static"><fmt:formatDate value="${supplyDemand.crtDate }" pattern="yyyy-MM-dd HH:mm:ss" /></p>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label" for="title">联系人:</label>
				<div class="col-sm-8 controls">
					<p class="form-control-static">${supplyDemand.toUser }</p>
				</div>
			</div>
			<div class="form-group">
	<label class="col-sm-2 control-label" for="price">价格:</label>
		<div class="col-sm-10 controls">
		<p class="form-control-static">${supplyDemand.price }</p>
	</div>
</div>
<div class="form-group">
	<label class="col-sm-2 control-label" for="total">总量:</label>
	<div class="col-sm-10 controls">
		<p class="form-control-static">${supplyDemand.total }</p>
	</div>
</div>
<div class="form-group">
	<label class="col-sm-2 control-label" for="address">所在地:</label>
	<div class="col-sm-10 controls">
		<p class="form-control-static">${supplyDemand.address }</p>
	</div>
</div>
<div class="form-group">
	<label class="col-sm-2 control-label" for="limitNum">起购数:</label>
	<div class="col-sm-10 controls">
		<p class="form-control-static">${supplyDemand.limitNum }</p>
	</div>
</div>
	<div class="form-group">
				<label class="col-sm-2 control-label" for="startDate">开始日期:</label>
				<div class="col-sm-8 controls">
					<p class="form-control-static"><fmt:formatDate value="${supplyDemand.startDate}" pattern="yyyy-MM-dd" /></p>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label" for="endDate">截止日期:</label>
				<div class="col-sm-8 controls">
					<p class="form-control-static"><fmt:formatDate value="${supplyDemand.endDate}" pattern="yyyy-MM-dd" /></p>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label" for="intro">简介:</label>
				<div class="col-sm-8 controls">
					<p class="form-control-static">${supplyDemand.introduce }</p>
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-sm-2 control-label" for="detail">详细内容:</label>
				<div class="col-sm-8 controls">
					<p class="form-control-static">${what }</p>
				</div>
			</div>
		
		</fieldset>
	</form>
	</body>
</html>
