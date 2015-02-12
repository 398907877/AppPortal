<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>

<head>
	<title>资讯分类列表</title>
	<style type="text/css">
		.navigate-right:after{
			content:'\e826';
		}
		.title{
			text-align:center;
		}
	</style>
</head>
<body>
	<header class="bar bar-nav  bar-standard">
	 <a href="${ctx }/m/index?uid=${currentUID}" data-transition="slide-in" class="btn btn-link btn-nav pull-left"><span class="icon icon-left-nav"></span>返回</a>
      <h1 class="title">新闻资讯</h1>
    </header>
	<div class="content">
		<ul class="table-view">
			<c:forEach var="category" items="${categories}">
			  <li class="table-view-cell">
			    <a class="navigate-right" href="${ctx}/m/news/${category.id}/list?uid=${currentUID}" data-transition="slide-in">
			      ${category.name}
			    </a>
			  </li>
		  	</c:forEach>
		</ul>
    </div>
</body>
</html>