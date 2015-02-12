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
</head>
<body>
	<header class="bar bar-nav">
		<a href="${ctx }/m/news/index?uid=${currentUID}" data-transition="slide-in" class="btn btn-link btn-nav pull-left"><span class="icon icon-left-nav"></span>返回</a>
      	<h1 class="title">${category.name}</h1>
    </header>
	<div class="content">
		<ul class="table-view">
			<c:forEach var="news" items="${news.content}">
			  <li class="table-view-cell media">
			    <a class="">
			      <img class="media-object pull-left" width="92px" height="62px" src="http://localhost/file/news/2014/6/30/2014_6_30_1404094230695.jpg">
			      <div class="media-body">
			      	${news.title}
			        <p>${news.intro}</p>
			      </div>
			    </a>
			  </li>
		  </c:forEach>
		 </ul>
		 <button class="btn btn-block btn-primary">点击加载更多</button>
    </div>
</body>
</html>