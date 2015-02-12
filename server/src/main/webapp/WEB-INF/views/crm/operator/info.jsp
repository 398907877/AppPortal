<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ page session="false"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>租户信息显示</title>
<style type="text/css">
	p span{width: 100px; display: inline-block; font-weight: bold;}
	#tenancy{float: left; }
	#operator{float: right; width: 50%;}
	.clear{clear: both;}
</style>
<link href="${ctx}/static/select2-3.4.6/select2.css" rel="stylesheet"/>
	<script src="${ctx}/static/select2-3.4.6/select2.js"></script> 
</head>
<body>
		<div class="page-header">
			<h2>用户管理</h2>
		</div>
		 <c:if test="${not empty message}">
			<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
		</c:if>
			<%-- <div id="tenancy">
	         <p><span>uid：</span>${tenancy.uid }</p>
			<p><span>企业名称：</span>${tenancy.name }</p>
			<!-- 
			<p><span>appId：</span>${tenancy.appId }</p>
			<p><span>appSecret：</span>${tenancy.appSecret }</p>
			 -->
			<p><span>创建时间：</span><fmt:formatDate value="${tenancy.crtDate}" pattern="yyyy/MM/dd  HH:mm:ss" /></p>
			<p><span>联系电话：</span>${tenancy.tel }</p>
			<p><span>联系地址：</span>${tenancy.address }</p>		
		</div> --%>
		<div class="bs-callout bs-callout-info">
		    <form id="queryForm" class="form-inline" role="form"  method="get" action="${ctx}/mgr/crm/userList">
		    	<shiro:hasAnyRoles name='SYSADMIN'>
		    		<label>企业：</label>
		    		 <select name="search_EQ_uid" style="min-width:220px;" class="populate placeholder select2-offscreen se2" tabindex="-1" title="">
		    		 	<option value="">---------请选择---------</option>
		    		 	<c:forEach items="${tenancies }" var="t">
		    		 		<option value="${t.uid }" ${param.search_EQ_uid == t.uid  ? 'selected' : '' }>${t.name }</option>
		    		 	</c:forEach>
		    		 </select>
		    	</shiro:hasAnyRoles>
				<label>姓名：</label> 
				<input name="search_LIKE_name" type="text" class="form-control" value="${param.search_LIKE_name}" />
				<label>状态：</label> 
						
						<select name="search_EQ_status" class="form-control">
						<option value="">---------请选择---------</option>
						<option value="1" ${param.search_EQ_status == '1' ? 'selected' : '' } >有效</option>
						<option value="0" ${param.search_EQ_status == '0' ? 'selected' : '' }  >无效</option>
						<option value="2" ${param.search_EQ_status == '2' ? 'selected' : '' }  >冻结</option>
						</select>
				<input type="submit" class="btn btn-success" value="查 找" />
				<tags:sort />
			</form>
		</div>
			<table class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th title="操作" width="120px">操作</th>
						<shiro:hasAnyRoles name='SYSADMIN'>
						<th title="企业名称">企业名称</th>
						</shiro:hasAnyRoles>
						<th title="姓名">姓名</th>
						<th title="登录名">登录名</th>
						<th title="类型">类型</th>
						<th title="状态">状态</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${users.content}" var="item" varStatus="s">
					<tr>
						<td>
							<div class="btn-group">
								<a class="btn btn-info" href="#">操作</a>
								<a class="btn btn-info dropdown-toggle" data-toggle="dropdown" href="#">
								    <span class="caret"></span>
								</a>
								<ul class="dropdown-menu">
						            <li><a href="<%=request.getContextPath()%>/mgr/crm/operator/edit?oid=${item.id}"><i class="icon-edit"></i>修改</a></li>
						            <li><a href="<%=request.getContextPath()%>/mgr/crm/operator/resetPwd?oid=${item.id}"><i class="icon-edit"></i>重置密码</a></li>
						            <li><a href="javascript:void(0);"  rel="${item.id}" class="del"><i class="icon-th"></i>删除</a></li>
						            <li><a href="javascript:void(0);"  rel="${item.id}" class="lock"><i class="icon-th"></i>锁定</a></li>
						            <li><a href="javascript:void(0);"  rel="${item.id}" class="unlock"><i class="icon-th"></i>解锁</a></li>
						            <li class="divider"></li>
						          </ul>
							</div>	
						</td>
						<shiro:hasAnyRoles name='SYSADMIN'>
						<td>
							<c:forEach items="${tenancies }" var="te">
								<c:if test="${te.uid == item.uid }">${te.name }</c:if>
							</c:forEach>
						</td>
						</shiro:hasAnyRoles>
						<td>${item.name }</td>
						<td>${item.loginName }</td>
						<td>
						<c:if test="${item.roles == 'SYSADMIN' }">平台管理员</c:if>
						<c:if test="${item.roles == 'ADMIN' }">管理员</c:if>
						<c:if test="${item.roles == 'OPERATOR' }">普通用户</c:if>
						</td>
						<td>
							<c:if test="${item.status == 0 }">无效</c:if>
							<c:if test="${item.status == 1 }">有效</c:if>
							<c:if test="${item.status == 2 }">冻结</c:if>
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
        <div class="row">
			<div class="col-sm-2">
				<a href="<%=request.getContextPath()%>/mgr/crm/operator/add?uid=${currentTenancyID}" class="btn btn-primary">新增操作员</a>
			</div>
			<div class="col-sm-10">
				<tags:pagination page="${users}" paginationSize="5"/>
			</div>
		</div>
<script type="text/javascript">
	$(document).ready(function(){
		$(".se2").select2({
		    placeholder: "请选择一个分组",
		    allowClear: true
		});
		$(".del").click(function(){
			var id = $(this).attr("rel");
			if(confirm("确定删除吗？")){
				$.ajax({
					url: '<%=request.getContextPath()%>/mgr/crm/operator/del?oid=' + id, 
					type: 'DELETE',
					contentType: "application/json;charset=UTF-8",
					dataType: 'json',
					success: function(data){
						window.location.href = window.location.href;
					},error:function(xhr){
					    alert('错误了，请重试');
					}
				});
			}
		});
		$(".lock").click(function(){
			var id = $(this).attr("rel");
			if(confirm("确定锁定吗？")){
				$.ajax({
					url: '<%=request.getContextPath()%>/mgr/crm/operator/lock?oid=' + id, 
					type: 'DELETE',
					contentType: "application/json;charset=UTF-8",
					dataType: 'json',
					success: function(data){
						window.location.href = window.location.href;
					},error:function(xhr){
						alert('错误了，请重试');
					}
				});
			}		
		});
		$(".unlock").click(function(){
			var id = $(this).attr("rel");
			if(confirm("确定解锁吗？")){
				$.ajax({
					url: '<%=request.getContextPath()%>/mgr/crm/operator/unlock?oid=' + id, 
					type: 'DELETE',
					contentType: "application/json;charset=UTF-8",
					dataType: 'json',
					success: function(data){
						window.location.href = window.location.href;
					},error:function(xhr){
						alert('错误了，请重试');
					}
				});
			}
		});
	});
</script>
</body>
</html>