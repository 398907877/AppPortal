<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.huake.com/functions" prefix="function" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ page session="false"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
	<title>租户活动管理</title>
	<link href="${ctx}/static/fancybox/jquery.fancybox-1.3.4.css" rel="stylesheet" /> 
	<script src="${ctx}/static/js/jquery.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="${ctx}/static/fancybox/jquery.fancybox-1.3.4.js"></script>
</head>
<body>
   
<div class="row">
		<div class="col-md-2">
			<%@ include file="nav.jsp"%>
		</div>	
		<div class="col-md-10 ">
		<div class="page-header" style="margin-top:0px;">
			<h2  style="margin-top:0px">租户活动管理</h2>
		</div>
		 <c:if test="${not empty message}">
			<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
		</c:if>
		<div class="bs-callout bs-callout-info">
		    <p>输入查询条件过滤列表.</p>
		    <form id="queryForm" class="form-inline" role="form"  method="get" action="${ctx}/mgr/events/index">
				<label>活动名称：</label> 
				<input name="search_LIKE_title" type="text" class="form-control" value="${param.search_LIKE_name}" /> 
				<input type="submit" class="btn btn-success" value="查 找" />
				<tags:sort />
			</form>
		</div>
		
		<table class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<th title="编号" style="width:100px">编号</th>
					<th title="活动名称">活动名称</th>
					<th title="起始日期">起始日期</th>
					<th title="截止日期">截止日期</th>
					<th title="创建日期">创建日期</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${events.content}" var="event" varStatus="s">
				
					<tr>
						<td>
							<div class="btn-group">
								<button type="button" class="btn btn-info">操作</button>
								<button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown">
							        <span class="caret"></span>
							        <span class="sr-only">Toggle Dropdown</span>
							      </button>
								<ul class="dropdown-menu">
									<li><a href="${ctx}/mgr/events/update/${event.id}">修改</a></li>
									<li><a href="${ctx}/mgr/events/delete/${event.id}">删除</a></li>
								</ul>
							</div>
						</td>
						<td>
							<a href="<%=request.getContextPath()%>/mgr/events/${event.id}/show" data-fancybox-type="iframe" rel="fancy" class="showInfo"> <i
											class="icon-edit"></i>${function:truncate(event.title, 20)}</a>
							<%-- <a rel="tooltip" href="javascript:void(0);" class="title" data-placement="top" title="${item.title }"></a> --%>
						</td>
						<td>
							<fmt:formatDate value="${event.startDate}" pattern="yyyy年MM月dd日  HH时mm分ss秒" />
						</td>
						<td>
							<fmt:formatDate value="${event.endDate}" pattern="yyyy年MM月dd日  HH时mm分ss秒" />
						</td>
						<td>
							<fmt:formatDate value="${event.crtDate}" pattern="yyyy年MM月dd日  HH时mm分ss秒" />
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div id="demoLightbox" class="lightbox hide fade"  tabindex="-1" role="dialog" aria-hidden="true">
			<div class='lightbox-content'>
				<div class="lightbox-caption"><p>Your caption here</p></div>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-2">
				<a href="<%=request.getContextPath()%>/mgr/events/add" class="btn btn-primary"><span class="glyphicon glyphicon-plus"></span>新建活动</a>
			</div>
			<div class="col-sm-10">
				<tags:pagination page="${events}" paginationSize="5"/>
			</div>
		</div>
		
		<script type="text/javascript">
			$(document).ready(function(){
				$('.showInfo').fancybox({
					autoDimensions:false,
					width:800,
					height:600
				});
			    $("#tenancy").addClass("active");
				$('.title').tooltip();
			});
		
		</script> 	
	</div>
	</div>
</body>
</html>