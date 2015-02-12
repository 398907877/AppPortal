<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<div class="bs-docs-sidebar hidden-print affix-top" role="complementary">
	<ul class="nav bs-docs-sidenav">
		<li>
			<a href="<%=request.getContextPath()%>/mgr/ernies/showInfo">功能介绍</a>
		</li>
		<li>
			<a href="<%=request.getContextPath()%>/mgr/ernies/list">活动设置</a>
		</li>
		<li>
			<a href="<%=request.getContextPath()%>/mgr/ernieLog/list">中奖记录</a>
		</li>
		<li>
			<a href="<%=request.getContextPath()%>/mgr/takePrize/list">兑奖记录</a>
		</li>
	</ul>
	<a class="back-to-top" href="#top">
		回到顶部
	</a>
</div>