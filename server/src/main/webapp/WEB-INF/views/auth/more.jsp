<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ page session="false"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
<title>更多配置</title>
</head>
<body>
	<div class="form-horizontal">
		<fieldset>
			<legend>
			 更多权限配置
			</legend>
			<div class="form-group">
				<label class="col-sm-2 control-label" for="title">企业名称：</label>
				<div class="col-sm-8 controls">
					<p class="form-control-static">${tenancy.name }</p>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label" for="title">业务模块及配置：</label>
				<div class="col-sm-8 controls">
				<c:forEach items="${bizCodes }" var="bizc" varStatus="s">
				<ul>
				<c:forEach items="authCfg"></c:forEach>
				<c:if test="${authCfgs[bizc.value] != null && bizc.key eq 'news'}">
						<li>
							${bizc.value}
							<ul>
								<li>每日限额条数：${authCfgs[bizc.value].dayLimit}</li>
								<li>月度视频限额：${authCfgs[bizc.value].monthVideoLimit}</li>
							</ul>
						</li>
				</c:if>
				
				<c:if test="${authCfgs[bizc.value] != null && bizc.key != 'news'}">
						<li>
							${bizc.value}
							<ul>
								<li>每日限额条数：${authCfgs[bizc.value].dayLimit}</li>
							</ul>
						</li>
				</c:if>
				</ul>
			</c:forEach>
				</div>
			</div>
		</fieldset>
	</div>
</body>
</html>