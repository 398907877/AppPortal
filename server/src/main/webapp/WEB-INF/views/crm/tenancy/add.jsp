<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
	<title>登记租户信息</title>
<style type="text/css"> 
.error{ 
color:Red; 
margin-left:10px;  
} 
</style> 
<link href="${ctx}/static/bootstrap/additions/datepicker/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />
	<script src="${ctx}/static/bootstrap/additions/datepicker/bootstrap-datetimepicker.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/bootstrap/additions/datepicker/locales/bootstrap-datetimepicker.zh-CN.js" type="text/javascript"></script>
</head>

<body>
	<div class="page-header">
   		<h2>登记租户信息</h2>
 	</div>

	<form id="inputForm" action="${ctx}/mgr/crm/tenancy/save" method="post"  class="form-horizontal"  >
			

			<jsp:include page="form.jsp"/>
 			<div class="form-group">
 				<label class="col-sm-2 control-label"></label>
 				<div class="col-sm-10">
  			     <button type="submit" class="btn btn-primary" id="submit">保存</button>
				 <a href="<%=request.getContextPath()%>/mgr/crm/index" class="btn btn-primary">返回</a>
				 </div>
	        </div>

	</form>
<script type="text/javascript">
$(document).ready(function() {
	
	//为inputForm注册validate函数
	$("#inputForm").validate();
	
});
</script> 

</body>