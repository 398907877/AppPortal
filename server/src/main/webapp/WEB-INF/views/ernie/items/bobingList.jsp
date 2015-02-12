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
	<title>奖项设置</title>
</head>

<body>
	<div class="row">
	
		<div class="col-md-2">
			<%@ include file="../nav.jsp"%>
		</div>	
		<div class="col-md-10 ">
			<div class="page-header" >
				<h4>${ernieItems.content[0].ernie.title}的奖品设置管理</h4>
			</div>
			<c:if test="${not empty message}">
				<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
			</c:if>
			
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th width="10%">操作</th>
						<th>奖品名称</th>
						<th>奖品等级</th>
						<th>奖品数量</th>
						<th>中奖积分</th>					
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${ernieItems.content}" var="ernieItem" varStatus="i">
					<tr id="delete_${ernieItem.id}">
						<td>
							<div class="btn-group">
								<button type="button" class="btn btn-info"># ${i.index+1}</button>
								<button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown">
									<span class="caret"></span>
									<span class="sr-only">Toggle Dropdown</span>
								</button>
								<ul class="dropdown-menu">
							         <li><a href="${ctx}/mgr/ernieItems/bobing/${ernieItem.id}/edit" ><i class="icon-edit"></i>修改</a></li>
							         <li><a href="${ctx}/mgr/ernieItems/bobing/${ernieItem.id}/delete"><i class="icon-trash"></i>删除</a></li>
							     </ul>
							</div>	
						</td>
						<td><a href="#">${ernieItem.name}</a></td>
						<td>
						<c:if test="${ernieItem.bobing != null }">
							<c:choose>
								<c:when test="${ernieItem.bobing eq 'yixiu' }">
									一秀
								</c:when>
								<c:when test="${ernieItem.bobing eq 'erju' }">
									二举
								</c:when>
								<c:when test="${ernieItem.bobing eq 'sanhong' }">
									三红
								</c:when>
								<c:when test="${ernieItem.bobing eq 'sijin' }">
									四进
								</c:when>
								<c:when test="${ernieItem.bobing eq 'duitang' }">
									对堂
								</c:when>
								<c:when test="${ernieItem.bobing eq 'zhuangyuan' }">
									状元
								</c:when>
								<c:otherwise>
									
								</c:otherwise>
							</c:choose>
						</c:if>
						</td>
						<th>${ernieItem.num }</th>
						<th>${ernieItem.integral }</th>
					</tr>
				</c:forEach>
				</tbody>
			</table>
			<div class="row">
				<div class="col-sm-4">
					<a href="${ctx}/mgr/ernieItems/bobing/${ernieID}/new" class="btn btn-primary">添加奖项</a>
					<input id="cancel_btn" class="btn btn-primary" type="button" value="返回" onclick="history.back()"/>
				</div>
				<div class="col-sm-15">
					<tags:pagination page="${ernieItems}" paginationSize="10"/>
				</div>
			</div>
		</div>
	</div>
  </body>
</html>