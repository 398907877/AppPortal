<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<div class="bs-docs-sidebar hidden-print affix-top" role="complementary">
	<ul class="nav bs-docs-sidenav">
	
			<li>
			<a href="<%=request.getContextPath()%>/mgr/product/showInfo">功能介绍</a>
		</li>
		<li>
			<a href="<%=request.getContextPath()%>/mgr/product">产品管理</a>
		</li>
	
		<li>
			<a href="${ctx}/mgr/category">产品类型管理</a>
		</li>
		
	</ul>
	<a class="back-to-top" href="#top">
		回到顶部
	</a>
</div>