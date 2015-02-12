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
	<title>手机网站</title>
</head>
<body>
	<header class="bar bar-nav">
      <h1 class="title">xxxx公司标题</h1>
    </header>
	<div class="content">
      <ul class="table-view">
        <li class="table-view-cell media">
          <a class="navigate-right" href="inbox.html" data-transition="slide-in">
            <span class="media-object icon icon-pages pull-left"></span>
            <div class="media-body">
              最新动态
            </div>
          </a>
        </li>
        <li class="table-view-cell media">
          <a class="navigate-right" href="${ctx}/m/events/" data-transition="slide-in">
            <span class="media-object icon icon-person pull-left"></span>
            <div class="media-body">
              活动
            </div>
          </a>
        </li>
        <li class="table-view-cell media">
          <a class="navigate-right" href="inbox.html" data-transition="slide-in">
            <span class="media-object icon icon-star-filled pull-left"></span>
            <div class="media-body">
              Starred
            </div>
          </a>
        </li>
        <li class="table-view-cell media">
          <a class="navigate-right" href="inbox.html" data-transition="slide-in">
            <span class="media-object icon icon-trash pull-left"></span>
            <div class="media-body">
              Trash
            </div>
          </a>
        </li>
      </ul>

      <h5 class="content-padded">Other accounts</h5>
      <ul class="table-view">
        <li class="table-view-cell media">
          <a class="navigate-right" href="inbox.html" data-transition="slide-in">
            <span class="media-object icon icon-more pull-left"></span>
            <div class="media-body">
              Misc
            </div>
          </a>
        </li>
      </ul>
    </div>
</body>
</html>