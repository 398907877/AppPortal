<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
	<title>客户类型</title>
<style type="text/css"> 
.error{ 
color:Red; 
margin-left:10px;  
} 
</style> 
</head>

<body>
	<div class="page-header">
   		<h2>修改客户类型</h2>
 	</div>

	<form id="inputForm" action="${ctx}/mgr/customer/type/save" method="post"  class="form-horizontal"  >
			
<div >



 <div class="form-group">
	<label class="col-sm-2 control-label" for="title">类型名称：</label>
     	<div class="col-sm-10 controls">
                  <input type="hidden" name="id" value="${type.id }"/>
		          <input  type = "text" id="typeContent" name="typeContent"  class="form-control required"  value="${type.typeContent} " />
      </div>
      </div>

       <div class="form-group">
			<label class="col-sm-2 control-label"></label>
			<div class="col-sm-10">
				<input   value="提交"  class="btn btn-primary" id="submit"  type="submit"/>
				<input id="cancel_btn" class="btn" type="button" value="返回" onclick="history.back()"/>
				</div>
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