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
<%@ taglib prefix="huake" uri="/huake"%>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>客户端版本管理</title>
	<link href="${ctx}/static/fancybox/jquery.fancybox-1.3.4.css" rel="stylesheet" />
	
	<link href="${ctx}/static/select2-3.4.6/select2.css" rel="stylesheet"/>
	 
	<script src="${ctx}/static/js/jquery.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="${ctx}/static/fancybox/jquery.fancybox-1.3.4.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/jquery-ui-1.8.21.custom.min.js"></script>
	
	<script src="${ctx}/static/select2-3.4.6/select2.js"></script>	
</head>

<body>
     <div class="page-header">
		<h2>客户端版本管理</h2>
	</div>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
		 <div  class="bs-callout bs-callout-info">
		    <form class="form-inline" action="#">
				<select name="search_EQ_appCategory">
						<option value="">---------请选择---------</option>
						<option value="ANDROID-NHD" ${param.search_EQ_appCategory == 'ANDROID-NHD' ? 'selected' : '' } >新华都android版</option>
						<option value="IOS-NHD_FREE" ${param.search_EQ_appCategory == 'IOS-NHD_FREE' ? 'selected' : '' } >新华都iPhone越狱版</option>
						<option value="IOS-NHD_ITUNES" ${param.search_EQ_appCategory == 'IOS-NHD_ITUNES' ? 'selected' : '' } >新华都iPhone正式版</option>
						</select>
				<button type="submit" class="btn btn-success btn-sm" name="account-tab" id="search_btn">查询</button>
				<tags:sort/>
			</form>
	   
	    </div>
	
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<tr>
					<th title="编号">操作</th>
					<th title="客户端类别">客户端类别</th>
					<th title="客户端软件名称">客户端软件名称</th>
					<th title="所属租户">所属租户</th>
					<th title="下载地址">下载地址</th>
				</tr>
		
		</thead>
		<tbody>
		<c:forEach items="${versions.content}" var="item" varStatus="s">
			<input type="hidden" name="appCategory" value="${item.appCategory}"/>
				<tr>
					<td>
						<div class="btn-group">
							<a class="btn" href="#">#${s.index+1}</a>
							<a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
							    <span class="caret"></span>
							</a>
							<ul class="dropdown-menu">
					            <li><a href="<%=request.getContextPath()%>/mgr/apps/version/edit?id=${item.id }"><i class="icon-edit"></i>修改</a></li>
								<li><a href="<%=request.getContextPath()%>/mgr/apps/version/${item.id }/delete"><i class="icon-edit"></i>删除</a></li>
					            <li class="divider"></li>
					          </ul>
						</div>	
					</td>
					<td>${item.appCategory}</td>
					<td>${item.appName}</td>
					<td><huake:getTenancy id="${item.tenancyId}"></huake:getTenancy></td>
					<td>${item.url}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="row">
		<div class="col-sm-4">
			<a href="<%=request.getContextPath()%>/mgr/apps/version/add" class="btn btn-primary">添加新的软件版本</a>
		</div>
		<div class="col-sm-8">
			<tags:pagination page="${versions}" paginationSize="5"/>
		</div>
	</div>
	

</body>

</html>