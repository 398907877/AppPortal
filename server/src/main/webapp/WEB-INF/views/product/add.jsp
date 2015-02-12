<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	
	<link href="${ctx}/static/bootstrap/additions/jasny/jasny-bootstrap.min.css" type="text/css" rel="stylesheet" />
	<link href="${ctx}/static/bootstrap/additions/wysihtml5/bootstrap-wysihtml5.css" type="text/css" rel="stylesheet" />	
	<link href="${ctx}/static/select2-3.4.6/select2.css" rel="stylesheet"/>
 	<script src="${ctx}/static/bootstrap/additions/wysihtml5/wysihtml5-0.3.0.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/bootstrap/additions/wysihtml5/bootstrap3-wysihtml5.js" type="text/javascript"></script>
	<script src="${ctx}/static/bootstrap/additions/wysihtml5/locales/bootstrap-wysihtml5.zh-CN.js" type="text/javascript"></script>
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
		<div >
	<div class="page-header">
		<h2>添加产品信息</h2>
	</div>
	
	<form:form id="form" method="post" modelAttribute="product" role="form" cssClass="form-horizontal" action="${ctx}/mgr/product/save"  enctype="multipart/form-data">
		<jsp:include page="form.jsp"/>
		<div class="form-group">
		<label class="col-sm-2 control-label"></label>
			<div class="col-sm-10">
				<input type="submit" id="onsubmit" class="display" style="display:none"/>
				<input  value="提交"  class="btn btn-default" id="submit"  type="button"/>
				<input id="cancel_btn" class="btn btn-primary" type="button" value="返回" onclick="history.back()"/>
			</div>
		</div>
	</form:form>
	</div>
	<script type="text/javascript">
	
	$('#intro').wysihtml5({locale: "zh-CN"});
		$(function(){
			$('.fileinput').fileinput();
			
		});
		$("#submit").click(function(){
			/* var pic=$('input[name="choose"]:checked').val();
			if(pic=='picFile'){
				$("#picUrl").remove();
			}else if(pic=='picUrl'){
				$("#picFile").html("<input class='input-file' name='fileInput' id='fileInput' type='file'>");
			} */
			
			$("#detail").text(editor1.getData());
			$("#onsubmit").trigger("click"); 
		});
	</script> 
	</div>
	</div>	
</body>
</html>