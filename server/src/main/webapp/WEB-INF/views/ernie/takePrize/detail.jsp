<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="huake" uri="/huake"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>中奖详情</title>
	<link href="${ctx}/static/bootstrap/3.1.1/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
</head>

<body>
	<form id="inputForm" action="${ctx}/mgr/ernieLog/${action}" method="post" class="form-horizontal">
		<input type="hidden" name="id" value="${ernieLog.id}"/>
		<fieldset>
		
			<legend>
				兑奖详情信息
			</legend>


<div class="form-group">
	<label class="col-sm-2 control-label" for="title">兑奖用户:</label>
	<div class="col-sm-8 controls">
		<p class="form-control-static"><huake:getMember id="${takePrize.memberId}" /> </p>
	</div>
</div>

<div class="form-group">
	<label class="col-sm-2 control-label" for="title">兑奖活动:</label>
	<div class="col-sm-8 controls">
		<p class="form-control-static"><huake:getErine id="${takePrize.ernieId}" /></p>
	</div>
</div>
<div class="form-group">
	<label class="col-sm-2 control-label" for="total">兑奖时间:</label>
	<div class="col-sm-10 controls">
		<p class="form-control-static"><fmt:formatDate value="${takePrize.createdAt}" pattern="yyyy/MM/dd HH:mm" /></p>
	</div>
</div>
<div class="form-group">
	<label class="col-sm-2 control-label" for="price">收奖人姓名:</label>
	<div class="col-sm-10 controls">
		<p class="form-control-static">${takePrize.name}</p>
	</div>
</div>
<div class="form-group">
	<label class="col-sm-2 control-label" for="price">手机号码:</label>
	<div class="col-sm-10 controls">
		<p class="form-control-static">${takePrize.mobile}</p>
	</div>
</div>
<div class="form-group">
	<label class="col-sm-2 control-label" for="price">收奖地址:</label>
	<div class="col-sm-10 controls">
		<p class="form-control-static">${takePrize.address}</p>
	</div>
</div>
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
