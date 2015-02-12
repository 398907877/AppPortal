<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<%@ page session="false"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>租户信息显示</title>
<style type="text/css">
	p span{width: 120px; display: inline-block; font-weight: bold;}
	#tenancy{float: left; }
	#operator{float: right; width: 50%;}
	.clear{clear: both;}
</style>
</head>
<body>
		<div class="page-header">
			<h2>企业名称：${tenancy.name }</h2>
		</div>
		<div class="col-sm-10">
			<p><span>uid：</span>${tenancy.uid }</p>
			<p><span>企业名称：</span>${tenancy.name }</p>
			<p><span>appId：</span>${tenancy.appId }</p>
			<p><span>appSecret：</span>${tenancy.appSecret }</p>
			<p><span>创建时间：</span><fmt:formatDate value="${tenancy.crtDate}" pattern="yyyy/MM/dd  HH:mm:ss" /></p>
			<p><span>公司电话：</span>${tenancy.tel }</p>
			<p><span>联系地址：</span>${tenancy.address }</p>
			<p><span>合同开始日期：</span><fmt:formatDate value="${tenancy.startDate}" pattern="yyyy/MM/dd" /></p>
			<p><span>合同结束日期：</span><fmt:formatDate value="${tenancy.endDate}" pattern="yyyy/MM/dd" /></p>
			<p><span>联系人姓名：</span>${tenancy.linkName }</p>
			<p><span>联系人电话：</span>${tenancy.linkTel }</p>
			<p><span>租户用户：</span></p>
			<table class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th title="编号" width="80px">编号</th>
						<th title="姓名">姓名</th>
						<th title="登录名">登录名</th>
						<th title="类型">类型</th>
						<th title="状态">状态</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${users}" var="item" varStatus="s">
					<tr>
						<td>#${item.id}</td>
						<td>${item.name }</td>
						<td>${item.loginName }</td>
						<td>
							<c:forEach items="${bizcode }" var="role">
								<c:if test="${item.roles eq role.key }">${role.value }</c:if>
							</c:forEach>
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
			</div>
</body>
</html>