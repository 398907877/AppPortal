<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
	<title>新增操作员</title>
<style type="text/css"> 
.error{ 
color:Red; 
margin-left:10px;  
} 
</style> 
<link href="${ctx}/static/select2-3.4.6/select2.css" rel="stylesheet"/>
	<script src="${ctx}/static/select2-3.4.6/select2.js"></script> 
</head>

<body>

	<div class="page-header">
   		<h2>新增操作员</h2>
 	</div>
 	 <c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<form id="inputForm" method="post" Class="form-horizontal" action="${ctx}/mgr/crm/operator/save" >
			<jsp:include page="form.jsp"/>
			<input type="hidden" name="uid" value="${uid}"/>
 			<div class="form-group">
 				<label class="col-sm-2 control-label"></label>
 				<div class="col-sm-10">
  			     <button type="submit" class="btn btn-primary" id="submit">保存</button>
				 <a href="<%=request.getContextPath()%>/mgr/crm/userList?uid=${uid}" class="btn btn-primary">返回</a>
				 </div>
	        </div>
	</form>
</body>