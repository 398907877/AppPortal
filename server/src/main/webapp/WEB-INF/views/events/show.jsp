<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<%@ page session="false"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>活动报名信息</title>
<style type="text/css">
	p span{width: 120px; display: inline-block; font-weight: bold;}
	#tenancy{float: left; }
	#operator{float: right; width: 50%;}
	.clear{clear: both;}
</style>
</head>
<body>
		<div class="page-header">
			<h2>活动报名人员信息</h2>
		</div>
		<div class="col-sm-10">
			<table class="table table-striped">
				<thead>
					<tr>
						<th title="姓名">姓名</th>
						<th title="电话">电话</th>
						<th title="公司">公司</th>
						<th title="部门">部门</th>
						<th title="职位">职位</th>
						<th title="地址">地址</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${users}" var="u" varStatus="s">
					<tr>
						<td>${u.name } </td>
						<td>${u.mobile } </td>
						<td>${u.addressList.companyName } </td>
						<td>${u.addressList.dept } </td>
						<td>${u.addressList.position } </td>
						<td>${u.addressList.address } </td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
			</div>
</body>
</html>