<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	
	<link href="${ctx}/static/bootstrap/additions/jasny/jasny-bootstrap.min.css" type="text/css" rel="stylesheet" />
	<link href="${ctx}/static/bootstrap/additions/wysihtml5/bootstrap-wysihtml5.css" type="text/css" rel="stylesheet" />	
	<link href="${ctx}/static/select2-3.4.6/select2.css" rel="stylesheet"/>
    <link href="${ctx}/static/bootstrap/additions/datepicker/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />
    
   	<link href="${ctx}/static/bootstrap/additions/datepicker/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />
	
 	<script src="${ctx}/static/bootstrap/additions/wysihtml5/wysihtml5-0.3.0.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/bootstrap/additions/wysihtml5/bootstrap3-wysihtml5.js" type="text/javascript"></script>
	<script src="${ctx}/static/bootstrap/additions/wysihtml5/locales/bootstrap-wysihtml5.zh-CN.js" type="text/javascript"></script>
	<script src="${ctx}/static/select2-3.4.6/select2.js"></script>
	
	<script src="${ctx}/static/bootstrap/additions/datepicker/bootstrap-datetimepicker.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/bootstrap/additions/datepicker/locales/bootstrap-datetimepicker.zh-CN.js" type="text/javascript"></script>
	
	<script src="${ctx}/static/bootstrap/additions/jasny/jasny-bootstrap.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/bootstrap/hold.js" type="text/javascript"></script>
	<style type="text/css">
	.categoryManage{margin-left:10px}
	.error{color:red}
	</style>
</head>
<body>
	<div class="page-header">
		<h2>修改企业介绍</h2>
	</div>
	
	<form:form id="form" method="post" modelAttribute="company" cssClass="form-horizontal" action="${ctx}/mgr/company/update"  enctype="multipart/form-data">
		<form:hidden path="id" />
		<form:hidden path="uid"/>
		<jsp:include page="form.jsp"/>
		<div class="form-group">
			<label class="col-sm-2 control-label"></label>
			
			<div class="col-sm-10">
				<input type="submit" id="onsubmit" class="display"/>
				<input  value="提交"  class="btn" id="submit"  type="button"/>
				<input id="cancel_btn" class="btn btn-primary" type="button" value="返回" onclick="history.back()"/>
				<%-- <a href="<%=request.getContextPath()%>/mgr/company" class="btn btn-primary">返回企业主页</a> --%>
			</div>
		</div>
	</form:form>
	<script type="text/javascript">

		function deleteoldpic(id){
			if(confirm("确定要删除吗?")){ 
	           $.ajax({
		         url: "${ctx}/mgr/company/"+id+"/delpic",
		         type: 'DELETE',
		         async:false,
		         contentType: "application/json;charset=UTF-8"
		       })
		       .done(function( html ) {
		    	  $("#dispic_"+id).empty();
		       }).fail( function(jqXHR, textStatus){
		    	  alert("删除失败jqXHR="+jqXHR+"textStatus="+textStatus);
		   });
	     }
	   }
	
	$(function(){
		$(".display").hide();
		$("#submit").click(function(){
			
			var pic=$('input[name="choose"]:checked').val();
			if(pic=='picFile'){
				$("#picUrl").remove();
			}else if(pic=='picUrl'){
				$("#picFile").html("<input class='input-file' name='fileInput' id='fileInput' type='file'>");
			}
			$("#onsubmit").trigger("click"); 
		});
	});
	
	</script> 	
	
</body>
</html>