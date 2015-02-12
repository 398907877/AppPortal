<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
<title>意见回复管理</title>
</head>
<body>
	<div class="page-header">
			<h3>意见回复</h3>
		</div>
	<form id="inputForm" action="${ctx}/mgr/feedback/reply/${msgId }" method="post" class="form-horizontal" enctype="multipart/form-data">
		<input type="hidden" id="memberId" name="memberId"  value="${memberId}"/>		
			<div class="form-group">
				<label for="message" class="col-sm-2 control-label">回复内容:</label>
						<div class="col-sm-10 controls">
							<textarea path="message" name="message" class="form-control" placeholder="内容详情">${message.message}</textarea>
						</div>
			</div>	
			<div class="form-group">
				<label class="col-sm-2 control-label"></label>
				<div class="col-sm-10">
					<input id="submit_btn" class="btn btn-primary" type="submit" value="提交"/>&nbsp;	
					<a href="<%=request.getContextPath()%>/mgr/feedback/index" class="btn btn-primary">返回</a>
				</div>
			</div>

	</form>
	<script type="text/javascript">

	</script>
</body>
</html>
