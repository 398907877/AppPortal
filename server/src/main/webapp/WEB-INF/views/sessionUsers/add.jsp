<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
	<title>新增论坛用户</title>
<style type="text/css"> 
.error{ 
color:Red; 
margin-left:10px;  
} 
</style> 
</head>

<body>
	<div class="page-header">
   		<h2>新增APP用户</h2>
 	</div>
 <c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<form id="inputForm" action="${ctx}/mgr/tenancyUser/save" method="post"  class="form-horizontal"  >
		
<div >

<div class="form-group ">
	<label class="control-label col-sm-2" >&nbsp;&nbsp;用户名：</label>
	<div class="controls col-sm-10">
	    
		<input  type = "text" id="name" name="name"  class="form-control required"  value="" />
	</div>
</div>

<div class="form-group ">
	<label class="control-label col-sm-2" >&nbsp;&nbsp;登入名：</label>
	<div class="controls col-sm-10">
		<input type = "text" class="form-control" name="loginname"  value=""  />
	</div>
</div>

<div class="form-group ">
	<label class="control-label col-sm-2" >&nbsp;&nbsp;性别：</label>
	<div class="controls col-sm-10">
		<select name="sex" class="form-control">
		<option value="0">女</option>
		<option value="1">男</option>
		</select>
	</div>
</div>

<div

	class="form-group ">
	<label class="control-label col-sm-2" >&nbsp;&nbsp;密码：</label>
	<div class="controls col-sm-10">
		<input type = "password" class="form-control "  name="password"  value="" />
	</div>
</div>

</div>
			
 			<div class="form-group">
 				<label class="col-sm-2 control-label" ></label>
 				<div class="col-sm-10">
  			     <button type="submit" class="btn btn-primary" id="submit">保存</button>
				 <a href="<%=request.getContextPath()%>/mgr/tenancyUser/index" class="btn btn-primary">返回</a>
				 </div>
	        </div>

	</form>
	
<script type="text/javascript">
$(document).ready(function(){
	
	
	
	
	$("#submit").click(function(){
		$("#inputForm").validate({
			rules:{
				name:{
					required:true,
					minlength:5,
					maxlength:20
				},
				loginname:{
					required:true,
					maxlength:20
				},
				password:{
					required:true,
					minlength:5,
					maxlength:15
				}
			},messages:{
				name:{
					required:"必须填写"
				},
				loginname:{
					required:"必须填写",
				},
				password:{
					required:"必须填写",
					minlength:"密码长度5-15位",
					maxlength:"密码长度5-15位"
				}
			}
		});
	});
});
</script> 

</body>