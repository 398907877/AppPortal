<!DOCTYPE>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>修改中奖记录</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/static/bootstrap/additions/wysihtml5/bootstrap-wysihtml5.css"></link>
	<link rel="stylesheet" type="text/css" href="${ctx}/static/bootstrap/additions/datepicker/bootstrap-datetimepicker.min.css"></link>
	<link rel="stylesheet" type="text/css" href="${ctx}/static/bootstrap/additions/jasny/jasny-bootstrap.min.css"></link>
	<script src="${ctx}/static/bootstrap/additions/wysihtml5/wysihtml5-0.3.0.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/bootstrap/additions/wysihtml5/bootstrap3-wysihtml5.js" type="text/javascript"></script>
	<script src="${ctx}/static/bootstrap/additions/wysihtml5/locales/bootstrap-wysihtml5.zh-CN.js" type="text/javascript"></script>
	<script src="${ctx}/static/bootstrap/additions/datepicker/bootstrap-datetimepicker.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/bootstrap/additions/datepicker/locales/bootstrap-datetimepicker.zh-CN.js" type="text/javascript"></script>
	<script src="${ctx}/static/bootstrap/additions/jasny/jasny-bootstrap.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/bootstrap/hold.js" type="text/javascript"></script>
</head>

<body>
	<div class="row">
		<div class="col-md-2">
			<%@ include file="../nav.jsp"%>
		</div>	
		<div class="col-md-10 ">
			<div class="bs-callout bs-callout-info">
				<h4>修改奖项</h4>
			</div>
			<c:if test="${not empty message}">
				<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
			</c:if>
			<form:form id="from" role="form" method="post" modelAttribute="ernieLog" action="${ctx}/mgr/ernieItems/update" enctype="multipart/form-data">
				<jsp:include page="form.jsp" />
			</form:form>
		</div>
	</div>
</html>