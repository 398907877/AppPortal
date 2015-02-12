<!DOCTYPE html>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Bootstrap-修改会员卡类型的一种或几种属性</title>
    <link href ="${ctx }/static/bootstrap/3.1.1/ css/bootstrap.min.css" rel ="stylesheet ">
    <script src ="${ctx }/static/js/jquery.min.js"></script>
    <script src ="${ctx }/static/js/jquery.validate.js"></script>
    <script src ="${ctx }/static/bootstrap/3.1.1/ js/bootstrap.min.js"></script>	
	<link href="${ctx}/static/bootstrap/additions/jasny/jasny-bootstrap.min.css" type="text/css" rel="stylesheet" />
	<link href="${ctx}/static/fancybox/jquery.fancybox-1.3.4.css" rel="stylesheet" /> 
	<link href="${ctx}/static/bootstrap/additions/wysihtml5/bootstrap-wysihtml5.css" type="text/css" rel="stylesheet" />	
	<link href="${ctx}/static/select2-3.4.6/select2.css" rel="stylesheet"/>

    <script type="text/javascript" src="${ctx}/static/js/jquery-ui-1.8.21.custom.min.js"></script>
 	<script src="${ctx}/static/bootstrap/additions/wysihtml5/wysihtml5-0.3.0.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/bootstrap/additions/wysihtml5/bootstrap3-wysihtml5.js" type="text/javascript"></script>
	<script src="${ctx}/static/bootstrap/additions/wysihtml5/locales/bootstrap-wysihtml5.zh-CN.js" type="text/javascript"></script>
	<script src="${ctx}/static/select2-3.4.6/select2.js"></script>
	<link href="${ctx}/static/bootstrap/additions/datepicker/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />
	<script src="${ctx}/static/bootstrap/additions/datepicker/bootstrap-datetimepicker.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/bootstrap/additions/datepicker/locales/bootstrap-datetimepicker.zh-CN.js" type="text/javascript"></script>
	<script src="${ctx}/static/bootstrap/additions/jasny/jasny-bootstrap.min.js" type="text/javascript"></script>	
	<script type="text/javascript" src="${ctx}/static/fancybox/jquery.fancybox-1.3.4.js"></script>
	<script src="${ctx}/static/bootstrap/additions/jasny/jasny-bootstrap.min.js" type="text/javascript"></script>
	<style type="text/css">
	.categoryManage{margin-left:10px}
	.error{color:red}
	</style>
</head>
<body>
  <div class="container">
	<div class="row">
	    <div class="col-md-2">
			<%@ include file="nav.jsp"%>
		</div>
	    <div class="col-md-10 bs-callout bs-callout-info">
			<div>
				<h4>修改会员卡的属性</h4>
				<h4><small>用于修改会员卡的属性，可修改属性含(Logo，会员卡模型，背景图片等)，还有其它属性</small></h4>
			</div>
		    <div>			
		      <c:if test="${not empty message}">
			  <div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
		    </c:if>
		    </div>
		    <div>
		    <h4>属性修改：</h4>
		      <c:if test='${count!=0}'>
	          <span><h4>已有用户使用原有会员卡，请确认是否修改,用户数为:${count}</h3><span>
	          </c:if>
		    </div>	    
	    
	<form:form id="mcardForm" method="post" modelAttribute="mcard" cssClass="form-horizontal" action="${ctx}/mgr/membercard/update"  enctype="multipart/form-data">		
		<form:hidden path="id" />
		<jsp:include page="form.jsp"/>
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10 controls">
				<input type="submit" id="onsubmit" style="display:none"/>
				<input  value="提交"  class="btn btn-primary" id="submit"  type="button"/>
				<input id="cancel_btn" class="btn btn-primary" type="button" value="返回" onclick="history.back()"/>
				<a class="btn btn-primary" href="<%=request.getContextPath()%>/mgr/membercard/index" class="btn">返回管理页</a>
			</div>
		</div>
	</form:form>
	</div>
	</div>
</div>
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
						required:true,
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
	<script type="text/javascript">
	function deleteoldpic(id){
		if(confirm("确定要删除吗?")){ 
           $.ajax({
	         url: "${ctx}/mgr/membercard/"+id+"/delpic",
	         type: 'DELETE',
	         async:false,
	         contentType: "application/json;charset=UTF-8"
	       })
	       .done(function( html ) {
	    	   $("#dispic_"+id).remove();
	       }).fail( function(jqXHR, textStatus){
	    	  alert("删除失败jqXHR="+jqXHR+"textStatus="+textStatus);
	   });
     }
   }
	
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
	});	
	</script> 	
</body>
</html>