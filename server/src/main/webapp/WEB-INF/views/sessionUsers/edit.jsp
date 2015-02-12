<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
	<title>修改APP用户</title>
	<link href="${ctx}/static/bootstrap/additions/datepicker/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />
	<script src="${ctx}/static/bootstrap/additions/datepicker/bootstrap-datetimepicker.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/bootstrap/additions/datepicker/locales/bootstrap-datetimepicker.zh-CN.js" type="text/javascript"></script>
	<style type="text/css">
.error{ 
color:Red; 
margin-left:10px;  
} 
</style> 
</head>

<body>
	<div class="page-header">
   		<h2>修改APP用户</h2>
 	</div>

	<form id="inputForm" action="${ctx}/mgr/tenancyUser/update" method="post"  class="form-horizontal"  >
		<input type="hidden" name="id" value="${forumUser.id }">
<div >

<div class="form-group ">
	<label class="control-label col-sm-2" >&nbsp;&nbsp;用户名：</label>
	<div class="controls col-sm-10">
		<input  type = "text" id="name" name="name"  class="form-control required"  value="${forumUser.name }" />
	</div>
</div>
<div class="form-group ">
	<label class="control-label col-sm-2" for="nickname">&nbsp;&nbsp;昵称：</label>
	<div class="controls col-sm-10">
		<input  type = "text" id="nickname" name="nickname"  class="form-control required"  value="${forumUser.nickname }" />
	</div>
</div>
<div class="form-group ">
	<label class="control-label col-sm-2" >&nbsp;&nbsp;性别：</label>
	<div class="controls col-sm-10">
		<select name="sex" class="form-control">
		<option value="0" ${forumUser.sex=='0' ? 'selected' : ''}>女</option>
		<option value="1" ${forumUser.sex=='1' ? 'selected' : ''}>男</option>
		</select>
	</div>
</div>
<div class="form-group "> 
	<label class="control-label col-sm-2" >&nbsp;&nbsp;联系方式：</label>
	<div class="controls col-sm-10">
		<input  type = "text" id="mobile" name="mobile"  class="form-control" value="${forumUser.mobile }" />
	</div>
</div>
<div
	class="form-group ">
	<label class="col-sm-2 control-label" for="issueDate">出生年月：</label>
	<div class="controls col-sm-10" >
		<input type="text" name="birth"  id = "birth" value="<fmt:formatDate value="${forumUser.birth }" pattern="yyyy-MM-dd HH:mm:ss"/>" class="form-control datepicker"/>
	</div>
</div>

<div class="form-group ">
	<label class="col-sm-2 control-label" for="status">用户状态：</label>
		<div class="controls col-sm-10">
			<select  name="status" class="form-control">
				<option value="1" ${forumUser.status=='1' ? 'selected' : ''} >正常</option>
				<option value="0" ${forumUser.status=='0' ? 'selected' : ''} >注销</option>
			</select>
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
	$('.datepicker').datetimepicker({
	    format: 'yyyy-mm-dd hh:ii:ss',
	    language: 'zh-CN'
	});
	// 联系电话(手机/电话皆可)验证   
    jQuery.validator.addMethod("isTel", function(value,element) {   
        var length = value.length;   
        var mobile = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;   
        var tel = /^(\d{3,4}-?)?\d{7,8}$/g;       
        return this.optional(element) || tel.test(value) || (length==11 && mobile.test(value));   
    }, "请正确填写联系方式");
	$("#submit").click(function(){
		$("#inputForm").validate({
			rules:{
				name:{
					required:true,
					minlength:5,
					maxlength:20
				},
				nickname:{
					required:true,
					maxlength:20
				},
				mobile:{
					required:true,
					isTel:true
				}
			},messages:{
				name:{
					required:"必须填写",
				},
				nickname:{
					required:"必须填写"
				},
				mobile:{
					required:"必须填写",
				}
			}
		});
	});
});
</script> 

</body>