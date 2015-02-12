<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
	<title>新增通讯信息</title>
	<link href="${ctx}/static/select2-3.4.6/select2.css" rel="stylesheet"/>
	<link href="${ctx}/static/bootstrap/additions/jasny/jasny-bootstrap.min.css" type="text/css" rel="stylesheet" />
	<script src="${ctx}/static/select2-3.4.6/select2.js"></script>
	<script src="${ctx}/static/bootstrap/additions/jasny/jasny-bootstrap.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/bootstrap/hold.js" type="text/javascript"></script>
</head>

<body>
<div class="row">
		<div class="col-md-2">
			<%@ include file="nav.jsp"%>
		</div>	
		<div class="col-md-10 ">
	<div class="page-header">
   		<h2>新增通讯信息</h2>
 	</div>

	<form id="inputForm" action="${ctx}/mgr/addressList/save" method="post" enctype="multipart/form-data"  class="form-horizontal"  >
		<input type="hidden" name="uid" value="${uid }" >

<div class="form-group">
	<label class="col-sm-2 control-label" >&nbsp;&nbsp;姓名：</label>
	<div class="col-sm-10 controls">
	    
		<input  type = "text" id="name" name="name"  class="form-control input-large required"  value="" />
	</div>
</div>
<div class="form-group">
	<label class="col-sm-2 control-label" >&nbsp;&nbsp;部门：</label>
	<div class="col-sm-10 controls">
		<input  type = "text" id="dept" name="dept"  class="form-control input-large"  value="" />
	</div>
</div>
<div class="form-group">
	<label class="col-sm-2 control-label" >&nbsp;&nbsp;职位：</label>
	<div class="col-sm-10 controls">
		<input  type = "text" id="name" name="position"  class="form-control input-large required"  value="" />
	</div>
</div>
<div class="form-group">
     <label class="col-sm-2 control-label" for="file-input">头像：</label>
     <div class="col-sm-10 controls">
		<div class="fileinput fileinput-new" data-provides="fileinput">
			<div class="fileinput-new thumbnail" style="width: 300px; height: 150px;">
					     <img data-src="holder.js/300x150" alt=""/>
			</div>
				 <div class="fileinput-preview fileinput-exists thumbnail" style="max-width: 300px; max-height: 150px;">
				 </div>  
			<div>
				<span class="btn btn-default btn-file">
				<span class="fileinput-new">添加图片</span>
				<span class="fileinput-exists">更改</span>
				<input type="file" class="file-input" name="fileInput_thum" id="file" />
			 </span>
				<a class="btn btn-danger fileinput-exists" data-dismiss="fileinput"><span class="glyphicon glyphicon-remove"></span>移除</a>
			</div>
	</div>
</div>
</div>
<div class="form-group">
	<label class="col-sm-2 control-label" for="deptId">选择分组：</label>
	<div class="col-sm-10 controls">
		<select  name="deptId" style="min-width:220px;" class="populate placeholder select2-offscreen se2" tabindex="-1" title="">
		<option value="">--------请选择一个分组--------</option>
		<c:forEach items="${addressListTypes}" var="addressType">
			<option value="${addressType.id}">${addressType.name}</option>
		</c:forEach>
		</select>
	</div>
</div>
<div class="form-group">
	<label class="col-sm-2 control-label" for="deptId">选择关联用户：</label>
	<div class="col-sm-10 controls">
		<select  name="userId" style="min-width:220px;" class="populate placeholder select2-offscreen se2" tabindex="-1" title="">
		<option value="">--------请选择一个用户--------</option>
		<c:forEach items="${users}" var="user">
			<option value="${user.id}">${user.name}</option>
		</c:forEach>
		</select>
	</div>
</div>
<div class="form-group ">
	<label class="col-sm-2 control-label" for="tel">&nbsp;&nbsp;邮箱：</label>
	<div class="col-sm-10 controls">
    <div class="input-group">
    <input type = "text" class="form-control input-large " name="email" id="email"  value="" />
      <span class="input-group-addon">
       <input type="radio" name="publicEmail" value="1">公开
		<input type="radio"  name="publicEmail" value="0" checked="checked"/>不公开 
      </span>
     
    </div>
	</div>
</div>
<div class="form-group ">
	<label class="col-sm-2 control-label" for="tel">&nbsp;&nbsp;私人电话：</label>
	<div class="col-sm-10 controls">
    <div class="input-group">
     <input type = "text" class="form-control input-large " name="tel" id="tel" placeholder="输入一个座机号或者一个手机号" value="" />
      <span class="input-group-addon">
       <input type="radio" name="publicTel" value="1">公开
		<input type="radio"  name="publicTel" value="0" checked="checked"/>不公开 
      </span>
    
    </div>
	</div>
</div>
<div class="form-group">
	<label class="col-sm-2 control-label" for="officePhone">&nbsp;&nbsp;办公电话：</label>
	<div class="col-sm-10 controls">
	<div class="input-group">
	 <input type = "text" class="form-control input-large " name="officePhone" placeholder="输入一个座机号或者一个手机号" id="officePhone" value="" />
      <span class="input-group-addon">
        <input type="radio" name="publicPhone" value="1" checked="checked">公开
        <input type="radio" name="publicPhone" value="0">不公开
      </span>
     
    </div>
	</div>
</div>
<div class="form-group">
	<label class="col-sm-2 control-label" for="e5">公司名称:</label>
	<div class="col-sm-10 controls">
		<div class="select2-container" >
				<input id="e5" type="hidden" style="min-width:250px;"/>
		</div>
		<span for="companyName" class="error" style="display:none" id="classSelect">必须输入或者选择公司名称</span>
		<input type="hidden" id="companyName" name="companyName" />
	</div>
</div>
<div class="form-group">
	<label class="col-sm-2 control-label" for="province">所在省:</label>
	<div class="col-sm-10 controls">
				<select id="province" name="province" class="province-select se2" style="min-width:250px;" >
					<option value="">请选择省份</option>
					<c:forEach items = "${province}" var="pro">
						<option  value="${pro.id}">${pro.name}</option>
					</c:forEach>
				</select>
			</div>
</div>
<div class="form-group">
	<label class="col-sm-2 control-label" for="cityCode">城市:</label>
	<div class="col-sm-10 controls" >
				 <input type="hidden" value = "${store.city}" id="initCity"/>
				<select  id="cityCode" name="city"  class="city-select se2" style="min-width:250px;"></select> 
			</div>
</div>
<div class="form-group">
	<label class="col-sm-2 control-label" >&nbsp;&nbsp;详细地址：</label>
	<div class="col-sm-10 controls">
		<input  type = "text" id="address" name="address"  class="form-control"  value="" />
	</div>
</div>
<div class="form-group ">
	<label class="col-sm-2 control-label" >&nbsp;&nbsp;附加说明：</label>
	<div class="col-sm-10 controls">
	<textarea rows="5"  name="content" class="form-control" ></textarea>
	</div>
</div>
 			<div class="form-group">
 				<label class="col-sm-2 control-label" ></label>
 				<div class="col-sm-10 controls">
  			     <button type="submit" class="btn btn-primary" id="submit">保存</button>
				 <a href="<%=request.getContextPath()%>/mgr/addressList/index" class="btn btn-primary">返回</a>
				 </div>
	        </div>

	</form>
	
<script type="text/javascript">
var re = [];
<c:forEach items="${tcustomers}" var="tc">
	re.push({id:"${tc.name}",text:"${tc.name}"});
</c:forEach>
$(".se2").select2({
    placeholder: "请选择一个分组",
    allowClear: true
});
$(document).ready(function(){
	$("#province").change(function(e){
		var value = $("#province").val();
		$("#cityCode").empty();
		e.preventDefault();
		
		$.ajax({
			url: '<%=request.getContextPath()%>/mgr/addressList/findCitys?provinceId=' + value, 
			type: 'GET',
			contentType: "application/json;charset=UTF-8",
			data: JSON.stringify({name:value}),					
			dataType: 'json',
			success: function(data){
				//var parsedJson = JSON.parse(data);
				$("#cityCode").append("<option value=''>"+"选择下级城市"+"</option>");
				$("#cityCode").select2("val","");
				 jQuery.each(data, function(index, itemData) {
				 $("#cityCode").append("<option value='"+itemData.areaCode+"'>"+itemData.name+"</option>"); 
				 });
			},error:function(xhr){alert('错误了\n\n'+xhr.responseText)}//回调看看是否有出错
		});
	}); 
	
	jQuery.validator.addMethod("validateCity",function(value,element,params){
		var ct=$("#city").val();
		return (ct==''|| ct==null);
	},"请选择城市");
	// 联系电话(手机/电话皆可)验证   
    jQuery.validator.addMethod("isTel", function(value,element) {   
        var length = value.length;   
        var mobile = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;   
        var tel = /^(\d{3,4}-?)?\d{7,8}$/g;       
        return this.optional(element) || tel.test(value) || (length==11 && mobile.test(value));   
    }, "请正确填写联系方式");
    var data ={results:re};
    $("#e5").select2({
    	 placeholder: "输入或者选择一个公司名称",
        createSearchChoice:function(term, data) { if ($(data).filter(function() { return this.text.localeCompare(term)===0; }).length===0) {return {id:term, text:term};} },
        multiple: false,
        data: data
    });
	
	 	
	 	$("#e5").on("change",function(e){
	 		$("#companyName").val($("#e5").select2("val"));
	 		
	 	});
	 	$("#e5").on("select2-close",function(e){
 		if($("#e5").select2("val") == ""){
 			$("#classSelect").show();
 		}else{
 			$("#companyName").val($("#e5").select2("val"));
 			$("#classSelect").hide();
 		}
 	});
	$("#submit").click(function(){
		$("#inputForm").validate({
			rules:{
				name:{
					required:true,
					maxlength:20
				},
				dept:{
					maxlength:20
				},
				position:{
					maxlength:20
				},
				address:{
					maxlength:60
				},
				tel:{
					required:true,
					isTel:true
				},
				officePhone:{
					isTel:true
				},
				city:{
		     		required:true
				},
				province:{
		     		required:true
				},
				companyName:{
					required:true
				},
				email:{
					email:true
				}
			},messages:{
				name:{
					required:"必须填写",
				},
				tel:{
					required:"必须填写",
				}
			},submitHandler:function(form){
				if($("#companyName").val() == ""){
					$("#classSelect").show();
					return;
				}
   	 			form.submit();
         }  
		});
	});
});
</script> 
</div>
</div>
</body>