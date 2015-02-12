<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
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
		<div class="col-md-10">
	<div class="page-header">
		<h2>修改新闻资讯</h2>
	</div>
	
	<form:form id="form" method="post" modelAttribute="news" cssClass="form-horizontal" action="${ctx}/mgr/news/update" enctype="multipart/form-data">
		
		<div class="col-md-10 ">
		<form:hidden path="id" />
		<form:hidden path="uid"/>
		<form:hidden path="publish"/>
		<jsp:include page="form.jsp"/>
		<div class="form-group">
		<label class="col-sm-2 control-label"></label>
			<div class="col-sm-10">
			<!-- <input type="submit" id="onsubmit" class="display"/> -->
			<input  value="提交"  class="btn btn-primary" id="submit"  type="submit"/>
			<!-- <input id="cancel_btn" class="btn btn-primary" type="button" value="返回" onclick="history.back()"/> -->
			<a href="<%=request.getContextPath()%>/mgr/news" class="btn btn-primary">返回新闻主页</a>
			</div>
		</div>
		</div>
		
	</form:form>
	</div>	
	</div>
	<script type="text/javascript">
	function deleteOldContent(id,index){
		if(id != ""){
			
		if(confirm("确定要删除吗?")){ 
           $.ajax({
	         url: "${ctx}/mgr/news/delcon/"+id,
	         type: 'DELETE',
	         async:false,
	         contentType: "application/json;charset=UTF-8"
	       })
	       .done(function( html ) {
	    	   deleteCon(index);
	       }).fail( function(jqXHR, textStatus){
	    	  alert("删除失败jqXHR="+jqXHR+"textStatus="+textStatus);
	   });
     }
	}else{
		deleteCon(index);
	}
   }
	
	
	$(function(){
		$(".display").hide();
		
	});
	
	</script> 	
	
</body>
</html>