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
	<title>营销管理</title>
</head>

<body>
	<div class="row">
		<div class="col-md-2">
			<%@ include file="../nav.jsp"%>
		</div>	
		<div class="col-md-10 ">
			<div class="bs-callout bs-callout-info">
				<h4>博饼活动管理</h4>
			</div>
			<c:if test="${not empty message}">
				<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
			</c:if>
			
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th width="10%">操作</th>
						<th>标题</th>
						<th>开始时间</th>
						<th>结束时间</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${ernies.content}" var="ernie" varStatus="i">
					<tr id="ernie${ernie.id}">
						<td >
							<div class="btn-group">
								<button type="button" class="btn btn-info"># ${i.index+1}</button>
								<button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown">
									<span class="caret"></span>
									<span class="sr-only">Toggle Dropdown</span>
								</button>
								<ul class="dropdown-menu">
							         <li><a href="${ctx}/mgr/ernies/bobing/${ernie.id}/edit" ><i class="icon-edit"></i>修改</a></li>
							         <li><a href="javascript:void(0);" class="delete_" rel="${ernie.id}"><i class="icon-trash"></i>删除</a></li>
							         <li class="divider"></li>
							         <li><a href="${ctx}/mgr/ernieItems/bobing/${ernie.id}/items"><i class="icon-trash"></i>奖项设置</a></li>
							     </ul>
							</div>	
						</td>
						<td><a href="#">${ernie.title}</a></td>
						<td>${ernie.startDate}</td>
						<td>${ernie.endDate}</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
			<div class="row">
				<div class="col-sm-2">
					<a href="${ctx}/mgr/ernies/bobing/new" class="btn btn-primary">添加博饼活动</a>
				</div>
				<div class="col-sm-10">
					<tags:pagination page="${ernies}" paginationSize="10"/>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
	$(document).ready(function() {
		$(".delete_").click(function(){
			var id = $(this).attr("rel");
			if(confirm("确定要删除吗?")){ 
	           $.ajax({
		         url: "${ctx}/mgr/ernies/"+id+"/delete",
		         type: 'GET',
		         async:false,
		         contentType: "application/json;charset=UTF-8"
		       })
		       .done(function( html ) {
		    	   $("#ernie"+id).remove();
                   alert("删除成功！");
		       }).fail( function(jqXHR, textStatus){
		    	  alert("删除失败jqXHR="+jqXHR+"textStatus="+textStatus);
		   });
	     }
	   });
		
	});
	
	</script>
</html>