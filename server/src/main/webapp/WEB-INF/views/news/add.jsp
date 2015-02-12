<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
	<link href="${ctx}/static/bootstrap/additions/jasny/jasny-bootstrap.min.css" type="text/css" rel="stylesheet" />
	<link href="${ctx}/static/select2-3.4.6/select2.css" rel="stylesheet"/>
	
	<link rel="stylesheet" type="text/css" href="${ctx}/static/uploadify/uploadify.css"> 
	<link href="${ctx}/static/bootstrap/additions/wysihtml5/bootstrap-wysihtml5.css" type="text/css" rel="stylesheet" />
	
	<script src="${ctx}/static/bootstrap/additions/wysihtml5/wysihtml5-0.3.0.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/bootstrap/additions/wysihtml5/bootstrap3-wysihtml5.js" type="text/javascript"></script>
	<script src="${ctx}/static/bootstrap/additions/wysihtml5/locales/bootstrap-wysihtml5.zh-CN.js" type="text/javascript"></script>
	<script src="${ctx}/static/uploadify/jquery.uploadify.min.js" type="text/javascript"></script>  
	<script src="${ctx}/static/bootstrap/additions/jasny/jasny-bootstrap.min.js" type="text/javascript"></script>
    <script src="${ctx}/static/select2-3.4.6/select2.js"></script>
    <script src="${ctx}/static/bootstrap/hold.js" type="text/javascript"></script>
	<style type="text/css">
	.categoryManage{margin-left:10px}
	.error{color:red}
	.content{ border: 1px solid #E1E1E8;
		border-radius:4px;
		padding:15px;
		 background-color: #f7f7f9;
	}
	</style>
</head>
<body>
<div class="row">
		<div class="col-md-2">
			<%@ include file="nav.jsp"%>
		</div>	
		<div class="col-md-10 ">
		<div >
	<div class="page-header">
		<h2>添加图文资讯</h2>
	</div>
	
	<form:form id="form" method="post" modelAttribute="news" cssClass="form-horizontal" action="${ctx}/mgr/news/save"  enctype="multipart/form-data">
		<jsp:include page="form.jsp"/>
		<div class="form-group">
			<label class="col-sm-2 control-label" ></label>
 			<div class="col-sm-10 controls">
			<input type="submit" id="onsubmit" class="display"/>
			<input   value="提交"  class="btn btn-primary" id="submit"  type="button"/>
			<a href="<%=request.getContextPath()%>/mgr/news" class="btn btn-default">返回</a>
			</div>
		</div>
	</form:form>
	</div>
	<script type="text/javascript">
	
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
	</div>
	</div>
</body>
</html>