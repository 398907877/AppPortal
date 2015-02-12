<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html lang="zh-CN">
<head>
	<title>添加授权书</title>
	
<style type="text/css"> 
	.error{ color:Red; margin-left:10px;  } 
	.none{display:none}
	.panel-group {
		margin-bottom: 5px;
	}
	.panel-heading {
		padding: 5px 10px
	}
</style> 
</head>
<body>
	<div class="page-header">
   		<h2>添加授权书</h2>
   		
 	</div>
 	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<form id="form"  action="${ctx}/mgr/auth/create" method="post" class="form-horizontal">
	 	<jsp:include page="form.jsp"/> 
	 	<div class="form-group">
 				<label class="col-sm-2 control-label"></label>
 				<div class="col-sm-10">
			<button type="submit" class="btn btn-primary" id="submit">保存</button>
			<a href="<%=request.getContextPath()%>/mgr/crm/index" class="btn btn-primary">返回</a>
			</div>
	    </div>
	</form>

</body>
</html>