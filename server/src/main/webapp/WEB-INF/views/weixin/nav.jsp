<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<div class="bs-docs-sidebar hidden-print affix-top" role="complementary">
	<ul class="nav bs-docs-sidenav">
		<li>
			<a href="<%=request.getContextPath()%>/mgr/weixin/index">微信管理功能介绍</a>
		</li>
		<li>
			<a href="<%=request.getContextPath()%>/mgr/weixin/access">微信接入设置</a>
		</li>
		<li>
			<a href="<%=request.getContextPath()%>/mgr/weixin/keywords">业务规则设置</a>
		</li>
		<li>
			<a href="<%=request.getContextPath()%>/mgr/weixin/keywordlist">关键字回复设置</a>
		</li>
		<li>
			<a href="<%=request.getContextPath()%>/mgr/weixin/menuIndex">服务号菜单设置</a>
		</li>
		<li>
			<a href="<%=request.getContextPath()%>/mgr/weixin/location">位置服务设置</a>
		</li>
		<li>
			<a href="<%=request.getContextPath()%>/mgr/weixin/index">微信会员管理</a>
		</li>
		<li>
			<a href="<%=request.getContextPath()%>/mgr/weixin/logindex">微信消息管理</a>
		</li>
	</ul>
	<a class="back-to-top" href="#top">
		回到顶部
	</a>
</div>