<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="${ctx}/static/select2-3.4.6/select2.css" rel="stylesheet"/>
<script src="${ctx}/static/select2-3.4.6/select2.js"></script>
</head>
<body>
<div class="row">
		<div class="col-md-2">
			<%@ include file="nav.jsp"%>
		</div>	
		<div class="col-md-10 ">
	<form:form id="form" method="post" modelAttribute="supplyDemandType" cssClass="form-horizontal" action="${ctx}/mgr/supply/type/save">
	<fieldset>
		
			<legend>
				产品详情信息
			</legend>
		<div class="form-group">
		<label class="col-sm-3 control-label" for="categoryId" >供求类型:</label>
		<div class="col-sm-6 controls">
		<select id="selectType" name="type" style="min-width:220px;" class="form-control">
				<option value=""  selected="selected">---------请选择供求类型---------</option>
				<option value="0">供（提供给他人信息）</option>
				<option value="1">求（想要获得的信息）</option>
				<option value="2">供求(供和求信息)</option>
		 </select>
		 </div>
	</div>
	
	<div class="form-group">
	<label class="col-sm-3 control-label" for="categoryId">供求类型:</label>
<div class="col-sm-6 controls">
		<input type="text" name="supplyDemandType" class="form-control">
	</div>
</div>
		<div class="form-group">
		<label class="col-sm-3 control-label"></label>
		<div class="col-sm-6 controls">
			<input type="submit" id="onsubmit" class="display"/>
			<input value="提交"  class="btn btn-primary" id="submit"  type="button"/>
			<input id="cancel_btn" class="btn btn-primary" type="button" value="返回" onclick="history.back()"/>
			</div>
		</div>
		</fieldset>
	</form:form>
	<script type="text/javascript">
	
		$(function(){
			$(".display").hide();
			$("#submit").click(function(){
				$("#onsubmit").trigger("click"); 
			});
		});
	</script> 	
	</div>
	</div>
	
</body>

</html>