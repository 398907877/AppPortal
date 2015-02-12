<%@page contentType="text/html" pageEncoding="UTF-8"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ page session="false"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<link href="${ctx}/static/fancybox/jquery.fancybox-1.3.4.css"
	rel="stylesheet" /> 
<script src="${ctx}/static/js/jquery.min.js" type="text/javascript"></script>
	<link href="${ctx}/static/select2-3.4.6/select2.css" rel="stylesheet"/>
	<script src="${ctx}/static/select2-3.4.6/select2.js"></script> 
<script type="text/javascript" src="${ctx}/static/fancybox/jquery.fancybox-1.3.4.js"></script>
	
<title>授权管理</title>
<style type="text/css">
	.dropdown-menu li{
		min-width:200px;
	}
	.nav-pills .active a{
		padding:7px 25px;
	}
</style>

</head>
<body>
		<div class="page-header">
			<h2>授权管理</h2>
		</div>
		<div>
		 <c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	     </c:if>
	      <div class="bs-callout bs-callout-info">
		<form id="queryForm" class="form-inline" method="get" action="${ctx}/mgr/auth/index">
			<label>企业：</label>
		<select   style="min-width:220px;" class="populate placeholder select2-offscreen se2" tabindex="-1" title="" name="search_EQ_uid">
				<option value="">----------请选择企业---------</option>
				<c:forEach items="${list}" var="tenancy">
				       <option value="${tenancy.uid }"  ${ param.search_EQ_uid== tenancy.uid ? 'selected':''}>${tenancy.name }</option>
				</c:forEach>
			</select>
			<%-- <label>状态：</label>
			<select  name = "search_EQ_status" class="form-control">
				<option value="" >全部</option>
				<option value="0" ${ param.search_EQ_status == 0 ? 'selected':''} >删除</option>
				<option value="1" ${ param.search_EQ_status == 1 ? 'selected':''}>激活</option>
				<option value="2" ${ param.search_EQ_status == 2 ? 'selected':''}>锁定</option>
			</select> --%>
			<input type="submit" class="btn btn-success"  value="查 找" />
				<%-- <tags:sort /> --%>
		</form>
		</div>
		
		</div>
		<table class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<th title="操作" width="120px">操作</th>
					<th title="企业名称">企业名称</th>
					<th title="业务种类及设置情况">业务种类及设置情况</th>
					<th title="创建时间">创建时间</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${tenancys.content}" var="item" varStatus="s">
				<tr>
					<td>
						<div class="btn-group">
							<a class="btn btn-info" href="#">操作</a>
							<a class="btn btn-info dropdown-toggle" data-toggle="dropdown" href="#">
							    <span class="caret"></span>
							</a>
							<ul class="dropdown-menu">
					            <li><a href="<%=request.getContextPath()%>/mgr/crm/auth/grant?uid=${item.uid}"><i class="icon-edit"></i>更新授权</a></li>
					            <li class="divider"></li>
					            <li><a href="javascript:void(0);"  rel="${item.uid}" class="del"><i class="icon-th"></i>删除</a></li>
					         <%--    <li><a href="javascript:void(0);"  rel="${item.id}" class="lock"><i class="icon-th"></i>锁定</a></li>
					            <li><a href="javascript:void(0);"  rel="${item.id}" class="actives"><i class="icon-th"></i>激活</a></li> --%>
					        </ul>
						</div>	
					</td>
					<td>
						<a href="<%=request.getContextPath()%>/mgr/crm/tenancy/show?uid=${item.uid}" data-fancybox-type="iframe" rel="fancy" class="showInfo" id="iFrame${item.uid }">
						<%-- <c:forEach items="${tenancys}" var="tenancy">
							<c:if test="${ tenancy.uid == item.uid}">${ tenancy.name}</c:if>
				        </c:forEach> --%>
				        	${item.name }
						</a>
					</td>
					<td class="authCfg">
					<c:forEach items="${bizCodes}" var="bizc">
						<c:forEach items="${authCfgs}" var="a">
							<c:set var="flag" value="${bizc.key}${item.uid }"></c:set>
							<c:if test="${a.key eq flag && a.value.uid == item.uid}">
								<ul class='nav nav-pills'> <li class='dropdown'><a class='dropdown-toggle' data-toggle='dropdown' href='#'>
							 	      ${bizc.value}配置<span class='caret'></span>
								 	     </a>
								 	     <ul class='dropdown-menu' role='menu'>
								 	     <c:if test="${bizc.key eq 'news' }">
								 	    	<li><a href='#'>  <span class='badge pull-right'>${a.value.dayLimit}</span>每日限额条数</a></li>
								 			<li><a href='#'>  <span class='badge pull-right'>${a.value.monthVideoLimit}</span>月度视频限额</a></li>
								 		 </c:if>
								 		 <c:if test="${bizc.key != 'news' }">
								 	    	<li><a href='#'>  <span class='badge pull-right'>${a.value.dayLimit}</span>每日限额条数</a></li>
								 		 </c:if>
								 	  </ul></li><li class='active'><a href='${ctx}/mgr/auth/more?id=${a.value.authId}' data-fancybox-type='iframe' rel='more' class='showInfo' >更多配置</a></li></ul>
							</c:if>
						
							</c:forEach>
							
						</c:forEach>
								       
					</td>
					
					<td>
						${item.crtDate }
					</td>
					
				</tr>
			</c:forEach>
			</tbody>
		</table>
		
		<div class="row">
			<div class="col-sm-2">
				<a href="<%=request.getContextPath()%>/mgr/crm/tenancy/add" class="btn btn-primary"><span class="glyphicon glyphicon-plus"></span>租户登记</a>
			</div>
			<div class="col-sm-10">
				<tags:pagination page="${tenancys}" paginationSize="5"/>
			</div>
		</div>
	<script type="text/javascript">
	
	$("#tenancy").addClass("active");
		$(document).ready(function(){
			$(".authCfg").each(function(){
			
				if($.trim($(this).html()) == ""){
					$(this).html("暂未该模块授予业务权限，请更新授权!");
				}
			});
			$('.showInfo').fancybox({
				autoDimensions:false,
				width:800,
				height:500
			});
		
			$(".se2").select2({
			    placeholder: "请选择企业",
			    allowClear: true
			});
			$(".del").click(function(){
				var id = $(this).attr("rel");
				if(confirm("确定删除吗？")){
					$.ajax({
						url: '<%=request.getContextPath()%>/mgr/auth/del?id=' + id, 
						type: 'POST',
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
						url: '<%=request.getContextPath()%>/mgr/auth/lock?id=' + id, 
						type: 'POST',
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
			<%-- $(".actives").click(function(){
				var id = $(this).attr("rel");
				if(confirm("确定激活吗？")){
					$.ajax({
						url: '<%=request.getContextPath()%>/mgr/auth/active?id=' + id, 
						type: 'POST',
						contentType: "application/json;charset=UTF-8",
						dataType: 'json',
						success: function(data){
							window.location.href = window.location.href;
						},error:function(xhr){
							alert('错误了，请重试');
						}
					});
				}
			}); --%>

		});

	</script> 	
</body>
</html>