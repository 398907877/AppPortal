<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
	<title>租户活动管理 - 新增活动</title>
	<link href="${ctx}/static/bootstrap/additions/wysihtml5/bootstrap-wysihtml5.css" type="text/css" rel="stylesheet" />
	<link href="${ctx}/static/bootstrap/additions/jasny/jasny-bootstrap.min.css" type="text/css" rel="stylesheet" />
	
	<script src="${ctx}/static/bootstrap/additions/wysihtml5/wysihtml5-0.3.0.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/bootstrap/additions/wysihtml5/bootstrap3-wysihtml5.js" type="text/javascript"></script>
	<script src="${ctx}/static/bootstrap/additions/wysihtml5/locales/bootstrap-wysihtml5.zh-CN.js" type="text/javascript"></script>
	
	<link href="${ctx}/static/bootstrap/additions/datepicker/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />
	<script src="${ctx}/static/bootstrap/additions/datepicker/bootstrap-datetimepicker.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/bootstrap/additions/datepicker/locales/bootstrap-datetimepicker.zh-CN.js" type="text/javascript"></script>
	<script src="${ctx}/static/bootstrap/additions/jasny/jasny-bootstrap.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/bootstrap/hold.js" type="text/javascript"></script>
</head>
<body>
   <div class="row">
		<div class="col-md-2">
			<%@ include file="nav.jsp"%>
		</div>	
		<div class="col-md-10 ">
	<div >
		<div class="page-header">
			<h3>租户活动管理 - 新增活动</h3>
		</div>
		
		<form:form id="eventForm" method="post" modelAttribute="event" cssClass="form-horizontal" role="form" action="${ctx}/mgr/events/save"  enctype="multipart/form-data">
			<div class="form-group">
				<label class="col-sm-2 control-label">活动名称:</label>
			    <div class="col-sm-10">
			    	<form:input path="title" name="title" cssClass="form-control input-large required"/>
			    </div>
			</div>	
			<div class="form-group">
				<label class="col-sm-2 control-label">活动地点:</label>
			    <div class="col-sm-10">
			    	<form:input path="address" name="address" cssClass="form-control input-large"/>
			    </div>
			</div>	
			<div class="form-group">
				<label class="col-sm-2 control-label">描述:</label>
			    <div class="col-sm-10">
			    	<form:textarea path="info" placeholder="输入活动描述" class="form-control input-small required" cols="10" rows="5"></form:textarea>
			    </div>
			</div>	
			<div class="form-group">
				<label class="col-sm-2 control-label">起始日期:</label>
			    <div class="col-sm-10">
			    	<form:input path="startDate" name="startDate" class="datepicker form-control input-small required"/>
			    </div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">截止日期:</label>
			    <div class="col-sm-10">
			    	<form:input path="endDate" name="endDate" class="datepicker form-control input-small required"/>
			    </div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">联系人:</label>
			    <div class="col-sm-10">
			    	<form:input path="linkman" name="linkman" cssClass="form-control input-large"/>
			    </div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">联系电话:</label>
			    <div class="col-sm-10">
			    	<form:input path="tel" name="tel" cssClass="form-control input-large"/>
			    </div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">海报:</label>
			    <div class="col-sm-10">
			    	<div class="fileinput fileinput-new" data-provides="fileinput">
					  <div class="fileinput-new thumbnail" style="width: 300px; height: 150px;">
					     <img data-src="holder.js/350x150" alt=""/>
					  </div>
					  <div class="fileinput-preview fileinput-exists thumbnail" style="max-width: 300px; max-height: 150px;"></div>
					  
					  <div>
					    <span class="btn btn-default btn-file">
					    	<span class="fileinput-new">选择原始图片</span>
					    	<span class="fileinput-exists">更改</span>
					    	<input type="file" class="file-input" name="file" multiple="multiple" id="file" />
					    </span>
					    <a href="#" class="btn btn-default fileinput-exists" data-dismiss="fileinput">移除</a>
					  </div>
					</div>
			    </div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label"></label>
			    <div class="col-sm-10">
			      <button type="submit" class="btn btn-default">发布</button>&nbsp;<a href="<%=request.getContextPath()%>/mgr/events/index?uid=${event.uid}" class="btn btn-primary">返回</a>
			    </div>
			</div>
		</form:form>
	</div>
	<script type="text/javascript">
	$(document).ready(function(){
		$('#info').wysihtml5({locale: "zh-CN"});
		$('.datepicker').datetimepicker({
		    format: 'yyyy-mm-dd hh:ii',
		    language: 'zh-CN'
		});
		$('.fileinput').fileinput();
		jQuery(function(){        
	        jQuery.validator.methods.compareDate = function(value, element, param) {
	            
	            var startDate = jQuery(param).val()+":00";
	            value = value+":00";
	            
	            var date1 = new Date(Date.parse(startDate.replace("-", "/")));
	            var date2 = new Date(Date.parse(value.replace("-", "/")));
	            
	            return date1 < date2;
	        };
	    });
		// 联系电话(手机/电话皆可)验证   
	    jQuery.validator.addMethod("isTel", function(value,element) {   
	        var length = value.length;   
	        var mobile = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;   
	        var tel = /^(\d{3,4}-?)?\d{7,9}$/g;       
	        return this.optional(element) || tel.test(value) || (length==11 && mobile.test(value));   
	    }, "请正确填写您的联系方式"); 
		$("#eventForm").validate({
			rules:{
				title:{
					required:true,
					maxlength:30
				},
				address:{
	            	 required: true,
					 maxlength:50
	            },
				info:{
					required:true
				},
				linkman:{
	            	 required: true,
					 maxlength:20
	             },
				tel:{
					isTel:true
				},startDate:{
	                 required: true
	             },
	             endDate: {
	                 required: true,
	                 compareDate: "#startDate"
	             }
			},messages:{
				title:{
					required:"必须填写",
					maxlength:"超出30字符"
				},
				address:{
					 required:"必须填写"
				},
				linkName:{
					required:"必须填写"
				},
				startDate:{
	                 required: "开始时间不能为空"
	             },
	             endDate:{
	                 required: "截止日期不能为空",
	                 compareDate: "截止日期必须大于开始日期!"
	             }
			}
		});
	});
	</script>
	</div>
	</div>
</body>