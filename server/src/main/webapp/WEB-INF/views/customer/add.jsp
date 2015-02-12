<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>新增会员企业（合作企业）</title>
<link href="${ctx}/static/bootstrap/additions/jasny/jasny-bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/static/select2-3.4.6/select2.css" rel="stylesheet" />
<link href="${ctx}/static/bootstrap/additions/wysihtml5/bootstrap-wysihtml5.css" type="text/css" rel="stylesheet" />

<script src="${ctx}/static/bootstrap/additions/wysihtml5/wysihtml5-0.3.0.min.js" type="text/javascript"></script>
<script src="${ctx}/static/bootstrap/additions/wysihtml5/bootstrap3-wysihtml5.js" type="text/javascript"></script>
<script src="${ctx}/static/bootstrap/additions/wysihtml5/locales/bootstrap-wysihtml5.zh-CN.js" type="text/javascript"></script>
<script src="${ctx}/static/select2-3.4.6/select2.js"></script>
<script src="${ctx}/static/bootstrap/additions/jasny/jasny-bootstrap.min.js" type="text/javascript"></script>
<script src="${ctx}/static/bootstrap/hold.js" type="text/javascript"></script>
<style type="text/css">
</style>
</head>

<body>
<div class="row">
		<div class="col-md-2">
			<%@ include file="nav.jsp"%>
		</div>	
		<div class="col-md-10 ">
	<div class="page-header">
		<h2>新增会员企业（合作企业）</h2>
	</div>

	<form id="inputForm" action="${ctx}/mgr/customer/save" method="post"
		class="form-horizontal" enctype="multipart/form-data">
		<input type="hidden" name="uid" value="${uid }" />
		<div>

			<div class="form-group">
				<label class="col-sm-2 control-label">企业分类：</label>
				<div class="col-sm-10 control-group " id="cateGr">
					<select id="category" name="types" style="width:100%" multiple="multiple">
					<c:forEach items="${category}" var="ca" varStatus="i">
						<c:if test="${ca.parentId == 0 }">
							<optgroup label="${ca.name }">
								<c:forEach items= "${category }" var="c" >
									<c:if test="${ca.id == c.parentId }">
										<option value="${c.id }"> ${c.name }</option>
									</c:if>
								</c:forEach>
							</optgroup>
						</c:if>
					</c:forEach>
					</select>
					<span id="chooseC" class="error hide">请至少选择一个分类</span>
				</div>
			</div>
			<div class="form-group ">
				<label class="col-sm-2 control-label" for="title">企业名称：</label>
				<div class="col-sm-10 controls">
					<input type="text" id="name" name="name"
						class="form-control required" value="" />
				</div>
			</div>

			<div class="form-group" id="picFile">
				<label class="col-sm-2 control-label" for="file-input">企业logo：</label>
				<div class="col-sm-10 controls" id="uploadpictureid">
					<div data-provides="fileinput" class="fileinput fileinput-new">
						<div class="fileinput-new thumbnail"
							style="width: 300px; height: 150px;">
							<img data-src="holder.js/300x150" alt="" />
						</div>
						<div class="fileinput-preview fileinput-exists thumbnail"
							style="max-width: 300px; max-height: 150px;">
							<img src="" style="max-width: 300px; max-height: 150px;" />
						</div>
						<div>
							<span class="btn btn-default btn-file"> <span
								class="fileinput-new">添加图片</span> <span class="fileinput-exists">更改</span>
								<input type="file" class="file-input" name="fileInput_thum"
								id="file" />
							</span> <a class="btn btn-danger fileinput-exists"
								data-dismiss="fileinput"><span
								class="glyphicon glyphicon-remove"></span>移除</a>
						</div>
					</div>
				</div>
			</div>
			<div class="form-group ">
				<label class="col-sm-2 control-label" for="businessScope">经营范围：</label>
				<div class="col-sm-10 controls">
					<input type="text" class="form-control " name="businessScope"
						id="businessScope" value="${customer.tel }" />
				</div>
			</div>
			<div class="form-group ">
				<label class="col-sm-2 control-label" for="linkman">联系人：</label>
				<div class="col-sm-10 controls">
					<input type="text" class="form-control " name="linkman"
						id="linkman" value="${customer.tel }" />
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label" for="tel">联系电话：</label>
				<div class="col-sm-10 controls">
					<input type="text" class="form-control " id="tel" name="tel"
						value="" />
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label" for="fax">企业传真：</label>
				<div class="col-sm-10 controls">
					<input type="text" class="form-control " name="fax" id="fax" value="" />
				</div>
			</div>

			<div class="form-group ">
				<label class="col-sm-2 control-label" for="address">企业地址：</label>
				<div class="col-sm-10 controls">
					<input type="text" class="form-control" name="address" id="address" value="" />
				</div>
			</div>

			<div class="form-group ">
				<label class="col-sm-2 control-label" for="content">企业介绍：</label>
				<div class="col-sm-10 controls">
					<textarea rows="8" name="content"  class="form-control intro"> </textarea>
				</div>
			</div>
			<div class="form-group ">
				<label class="col-sm-2 control-label" for="products">产品介绍：</label>
				<div class="col-sm-10 controls">
					<textarea rows="8" name="products" id="products" class="form-control intro"></textarea>
				</div>
			</div>

			
		</div>

		<div class="form-group">
			<label class="col-sm-2 control-label"></label>
			<div class="col-sm-10 control-group ">
				<button type="submit" class="btn btn-primary" id="submit">保存</button>
				<a href="<%=request.getContextPath()%>/mgr/crm/index"
					class="btn btn-primary">返回</a>
			</div>
		</div>

	</form>

	<script type="text/javascript">
		$(document).ready(function() {
			 // 电话号码验证    
		    jQuery.validator.addMethod("isPhone", function(value, element) {    
		      var tel = /^(\d{3,4}-?)?\d{7,9}$/g;    
		      return this.optional(element) || (tel.test(value));    
		    }, "请正确填写您的电话号码。");

		    // 联系电话(手机/电话皆可)验证   
		    jQuery.validator.addMethod("isTel", function(value,element) {   
		        var length = value.length;   
		        var mobile = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;   
		        var tel = /^(\d{3,4}-?)?\d{7,9}$/g;       
		        return this.optional(element) || tel.test(value) || (length==11 && mobile.test(value));   
		    }, "请正确填写您的联系方式"); 
			$("#category").select2({
			    placeholder: "请选择一个或多个分组",
			    allowClear: true
			});
			$('.intro').wysihtml5({locale: "zh-CN"});
			$("#submit").click(function() {
				$("#inputForm").validate({
					rules : {
						name:{
							required:true,
							minlength:5,
							maxlength:30
						},
						businessScope:{
							required:true,
							maxlength:100
						},
						linkman:{
							required:true,
							maxlength:20
						},
						tel:{
							required:true,
							isTel:true
						},
						fax:{
							isPhone:true
						},
						address:{
							maxlength:60
						}
					},
					messages : {
						name:{
							required:"必须填写"
						},
						businessScope:{
							required:"必须填写"
						},
						linkman:{
							required:"必须填写"
						},
						tel:{
							required:"必须填写"
						}
					},
					submitHandler : function(inputform) {
						var cate = $.trim($("#category").select2("val"));
						if(cate == ""){
							$("#cateGr").append("<span class='error'>请至少选择一个分类</span>");
							return false;
						}
						inputform.submit();
						inputform.submit();
					}
				});
			});
		});
	</script>
</div>
</div>
</body>