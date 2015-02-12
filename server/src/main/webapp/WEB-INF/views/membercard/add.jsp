<!DOCTYPE html>
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
    <title>Bootstrap-新增一种新的会员卡类型</title>
    <link href ="${ctx }/static/bootstrap/3.1.1/ css/bootstrap.min.css" rel ="stylesheet ">
    <script src ="${ctx }/static/js/jquery.min.js"></script>
    <script src ="${ctx }/static/js/jquery.validate.js"></script>
    <script src ="${ctx }/static/bootstrap/3.1.1/ js/bootstrap.min.js"></script>
	<link href="${ctx}/static/bootstrap/additions/wysihtml5/bootstrap-wysihtml5.css" type="text/css" rel="stylesheet" />
	<link href="${ctx}/static/bootstrap/additions/jasny/jasny-bootstrap.min.css" type="text/css" rel="stylesheet" />
	<script src="${ctx}/static/bootstrap/additions/wysihtml5/wysihtml5-0.3.0.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/bootstrap/additions/wysihtml5/bootstrap3-wysihtml5.js" type="text/javascript"></script>
	<script src="${ctx}/static/bootstrap/additions/wysihtml5/locales/bootstrap-wysihtml5.zh-CN.js" type="text/javascript"></script>
	<link href="${ctx}/static/bootstrap/additions/datepicker/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />
	<script src="${ctx}/static/bootstrap/additions/datepicker/bootstrap-datetimepicker.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/bootstrap/additions/datepicker/locales/bootstrap-datetimepicker.zh-CN.js" type="text/javascript"></script>
	<script src="${ctx}/static/bootstrap/additions/jasny/jasny-bootstrap.min.js" type="text/javascript"></script>
</head>
<body>
  <div class="container">
	<div class="row">
	    <div class="col-md-2">
			<%@ include file="nav.jsp"%>
		</div>
		<div class="col-md-10 bs-callout bs-callout-info">
			<div>
				<h4>新增一种会员卡类型</h4>
				<h4><small>用于新增一种会员卡类型，可选择属性含(Logo，会员卡模型，背景图片等)</small></h4>
			</div>
		    <div>			
		      <c:if test="${not empty message}">
			  <div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
		    </c:if>
		    </div>
		    
		    <div>
		    <h4>属性添加：</h4>
		    </div>
		    
		    <form:form id="mcardForm" method="post" modelAttribute="mcard" class="form-horizontal" role="form" action="${ctx}/mgr/membercard/save"  enctype="multipart/form-data">
			  <jsp:include page="form.jsp"/>
		      <div class="form-group">
			  <div class="col-sm-offset-2 col-sm-10">
				<input type="submit" id="onsubmit" class="display" style="display:none"/>
				<input  value="提交"  class="btn btn-primary" id="submit"  type="button"/>
				<input id="cancel_btn" class="btn btn-primary" type="button" value="返回" onclick="history.back()"/>
			  </div>
		      </div>
		    </form:form>		    
         </div>
      </div>
   </div>     
     
<script type="text/javascript">
              $("#submit").click(function(){
                     /* var pic=$('input[name="choose"]:checked').val();
                     if(pic=='picFile'){
                           $("#picUrl").remove();
                     }else if(pic=='picUrl'){
                           $("#picFile").html("<input class='input-file' name='fileInput' id='fileInput' type='file'>");
                     } */
                     $("#onsubmit").trigger("click");
              });
$(document).ready(function(){
		$('.datepicker').datetimepicker({
		    format: 'yyyy-mm-dd hh:ii',
		    language: 'zh-CN'
		});
		$('.fileinput').fileinput();
	});
</script>
<script type="text/javascript">			    
$(document).ready(function() {
     $("#discount").val(100);             
});
</script>
<script>
$().ready(function() {
 $("#mcardForm").validate({
rules:{
					cardName:{
						required:true,
						maxlength:20
					},
					fileInput_model:{
						required:true
					},				
					startDate:{
						required:true
					},
					expDate:{
						required:true
					},
					discount:{
						required:true,
						min:0,
						max:100,
						digits:true
					}
					
				},messages:{
					cardName:{
						required:"必须填写",
						maxlength:"超出20个字符"
					},
					startDate:{
						required:"必须填写"
					},
					expDate:{
						required:"必须填写"
					},
					fileInput_model:{
					    required:"必须填写"
					},								
				} 	
 	
 });
});
</script>
<script>		
		$("#contiueadd").click(function(){
			$("#pics").append("<div class='fileinput fileinput-new' data-provides='fileinput' style='margin-right:5px'>"
					+"<div class='fileinput-new thumbnail' style='width: 150px; height: 150px;'>"
							+"<img data-src='holder.js/100%x100%' alt=''/>"
					+"</div>"
					+"<div class='fileinput-preview fileinput-exists thumbnail' style='max-width: 150px; max-height: 150px;'>"
					+"</div>"  
					+"<div>"
						+"<span class='btn btn-default btn-file'>"
							+"<span class='fileinput-new'>添加图片</span>"
							+"<span class='fileinput-exists'>更改</span>"
							+"<input type='file' class='file-input' name='fileInput' id='file' />"
						+"</span>"
						+"<a href='#' class='btn btn-danger fileinput-exists' data-dismiss='fileinput' style='margin-left:5px'><span class='glyphicon glyphicon-remove'></span>移除</a>"
					+"</div>"
				+"</div>");
			//$('.fileinput').fileinput();
			$('.fileinput:last').fileinput();
		});
		$("#deleteAll").click(function(){
			$(".deleteOld").trigger("click");
			$("#pics .fileinput").remove();
		});		
		</script>  
</body>
</html>