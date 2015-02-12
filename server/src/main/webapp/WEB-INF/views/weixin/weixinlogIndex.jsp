<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html ng-app="weixin">
<head>
<title>微信消息</title>
<style type="text/css">
table td,table th {
	text-align: center
}
</style>

<script type="text/javascript">


//显示报文内容	
function viewxml(data){
	
	$("#showxml").text("");
	
	$("#showxml").text(data);
	
	
	
	//$("#closego").trigger("click");
	
}
	

</script>


</head>
<body ng-controller="WeixinController">
	<div class="row">
		<div class="col-md-2">
			<%@ include file="nav.jsp"%>
		</div>
		<div class="col-md-10 ">

			<div class="bs-callout bs-callout-info">
				<h4>微信消息管理</h4>
				<p>微信消息管理。</p>
			</div>

			<!-- 查询 -->
			<form class="navbar-form navbar-left" method="get" action="#">
				<div class="form-group">
					<label>报文内容：</label> <input name="search_LIKE_body" type="text"
						class="form-control" placeholder="输入查找的报文内容">
				</div>

				<button type="submit" class="btn btn-default">查找</button>
			</form>
			<!-- 顺序 -->
			<tags:sort />


			<!-- 列表 -->


			<table class="table table-hover table-bordered">
				<thead>
					<tr>
						<th>操作</th>
						<th>报文内容</th>
						<th>渠道</th>
						<th>客户端</th>
						<th>请求类型</th>
						<th>远程地址</th>
						<th>创建时间</th>
					</tr>
				</thead>
				<tbody>

					<c:forEach items="${accesslog.content}" var="log">

						<tr>
							<td><div class="btn-group">
									<a class="btn btn-info" href="#">操作</a> <a
										class="btn btn-info dropdown-toggle" data-toggle="dropdown"
										href="#"> <span class="caret"></span>
									</a>
									<ul class="dropdown-menu">
										<li><a href="#" class="del"><i class="icon-th"></i>删除</a></li>
										<li><a href="#"><i class="icon-edit"></i>待定</a></li>

									</ul>
								</div></td>
							<td width="10%" style="word-break: break-all; overflow: hidden;">
								<a style="color: #428bca; text-decoration: none;" href="###"
								data-toggle='modal' data-target='#InfoModal'    onclick="viewxml('<c:out value="${log.body}" escapeXml="true"/>')"> 查看报文 </a>
							</td>
							
							<td>
							<tags:dict className='CHANNEL_CODE'   value="${ log.channelName}"></tags:dict>
							</td>
							<td>${ log.client}</td>
							<td>
							<tags:dict className='WEIXINREQ_CODE'   value="${log.path}"></tags:dict>
							</td>
							<td>${ log.remoteHost}</td>
							<td><fmt:formatDate value="${ log.reqAt}" pattern=" yyyy年MM月dd日 HH时mm分ss秒"/></td>
						</tr>


					</c:forEach>
				</tbody>
			</table>

			<!-- 分页 -->
			<div align="center">
				<tags:pagination page="${accesslog}" paginationSize="10" />
			</div>
		</div>



		<!--    弹出框    START -->

		<div class="modal fade" id="InfoModal" tabindex="-1">
			<div    class="modal-dialog" style="width: 800px;top:100px;">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" id="closego"
							data-dismiss="modal">&times;</button>
						<h4 class="modal-title" id="myModalLabel">XML报文查看</h4>
						<span id="error" class="label label-danger"></span>
					</div>
					<div class="modal-body">

						<!-- 显示报文的地方 -->


						
						<pre   id="showxml" ></pre>

						<!-- 显示报文的地方 -->





					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						<button type="button" class="btn btn-primary">复制</button>
					</div>
				</div>
			</div>
		</div>
		<!--    弹出框    END -->
</body>
</html>