<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ page session="false"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>反馈意见管理</title>
	<link href="<c:url value="/static/fancybox/jquery.fancybox.css" />" rel="stylesheet" />
<script type="text/javascript" src="<c:url value="/static/js/jquery.fancybox.pack.js?v=2.1.3" />"></script>
<style type="text/css">
.group input,.group textarea {
	background-color: #FFFFD3;
	border: 1px inset #E9E9AE;
}
</style>
<style type="text/css">
.error {
	color: Red;
	margin-left: 10px;
}
</style>
</head>
<body>
	<div class="page-header">
		<h2>反馈意见管理</h2>
	</div>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success">
			<button data-dismiss="alert" class="close">×</button>
			${message}
		</div>
	</c:if>
	<div class="bs-callout bs-callout-info">
	<form class="form-inline" action="${ctx}/mgr/feedback/index">
		<label>内容：</label> <input type="text" name="search_LIKE_message"
			class="form-control" value="${param.search_LIKE_message}">
		<label>状态：</label> 
			<select name="search_EQ_status" class="form-control">
				<option value="">---------请选择---------</option>
				<option value="0" ${param.search_EQ_status == '0' ? 'selected' : '' } >无效</option>
				<option value="1" ${param.search_EQ_status == '1' ? 'selected' : '' } >未读</option>
				<option value="2" ${param.search_EQ_status == '2' ? 'selected' : '' } >已读</option>
			</select>			
			
		<button type="submit" class="btn btn-success" id="search_btn">Search</button>
		<tags:sort />
	</form>
	
	</div>
	<table class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th width="120px">操作</th>
				<th>会员</th>
				<th>消息标题</th>
				<th>消息内容</th>
				<th>创建时间</th>
				<th>状态</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${messages.content}" var="mess">
				<tr id="${mess.id }">
					<td>
						<div class="btn-group">
							<a class="btn btn-info" href="#">操作</a> <a
								class="btn btn-info dropdown-toggle" data-toggle="dropdown" href="#">
								<span class="caret"></span>
							</a>
							<ul class="dropdown-menu">
								<c:if test="${mess.status != 0 }" >
								<li><a
									href="<%=request.getContextPath()%>/mgr/feedback/${mess.memberId}/toReply/${mess.id}"><i
										class="icon-edit"></i>回复</a></li>
										</c:if>
				           		<li><a href="javascript:void(0);" class="delete" rel="${mess.id}"><i class="icon-trash"></i>删除</a></li>
				           		
									
							</ul>
						</div>
					</td>
					<td><c:forEach items="${ users}" var="user">
						<c:if test="${mess.memberId == user.id }">${ user.name}</c:if>
						</c:forEach>
					</td>
					<td><a href="#" title="${mess.title }" class="intro" data-fancybox-type="iframe" rel="fancy" id="iframe">
 					<c:choose> 
    					<c:when test="${fn:length(mess.title)>15 }"> 
     						<c:out value="${fn:substring(mess.title,0,15) }..." /> 
    					</c:when> 
    					<c:otherwise> 
     						<c:out value="${mess.title }" /> 
    					</c:otherwise> 
   					</c:choose> 
   					</a></td>
   					
					<td>
					<a href="#" title="${mess.message }" class="intro" data-fancybox-type="iframe" rel="fancy" id="iframe">
 					<c:choose> 
    					<c:when test="${fn:length(mess.message)>20 }"> 
     						<c:out value="${fn:substring(mess.message,0,20) }..." /> 
    					</c:when> 
    					<c:otherwise> 
     						<c:out value="${mess.message }" /> 
    					</c:otherwise> 
   					</c:choose> 
   					</a>
					</td>
					<td>${mess.crtDate}</td>
					<td>
					<c:choose>
					<c:when test="${mess.status==0 }">无效</c:when>
					<c:when test="${mess.status==1 }">未读</c:when>
					<c:when test="${mess.status==2 }">已读</c:when>
					</c:choose>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<tags:pagination page="${messages}" paginationSize="5" />
	<script type="text/javascript">
	$(document).ready(function(){
		$('.intro').tooltip();
		$(".delete").click(function(){
			var itemId="#"+$(this).attr("rel");
			if( confirm("确定删除信息"+itemId+"？") == true){
				$.ajax({
					url: '<%=request.getContextPath()%>/mgr/feedback/delete/' + $(this).attr("rel"), 
					type: 'DELETE',
					contentType: "application/json;charset=UTF-8",
					success: function(){
						$(itemId).remove();
					},error:function(){}
				});
				return;
			}
		});
	});
	</script>

</body>
</html>