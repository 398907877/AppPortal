<!DOCTYPE HTML>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>App接入 -     ${newsCategory.name }</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
		<style type="text/css">
			.page-header{margin-top:0px}
			.page-header, .content{background-color: white;}
			
		</style>
	</head>
	<body>
	<%-- 	<div class="header">
			<div style="font-size:18px;color:#212121;margin-bottom:3px">${news.title }</div>
			<p>发表于&nbsp;<fmt:formatDate value="${news.crtDate }" pattern="yyyy-MM-dd HH:mm:ss" /></p>
			<p>本文来源&nbsp;${tenancy.name}</p>
		</div>
		<hr size=1 color=#00ffff align=center noshade>	
		<div class="content" style="font-size:17px;color:#575757;line-height:25px">
			&nbsp;&nbsp;简介：${news.intro}<br>
			&nbsp;${what}
		</div>
		<div class="control-group" id="picFile">
	<label class="control-label" for="file-input">图片展示：</label>
	<div class="controls">
        <div class="gallery-set-thumbail">
         <c:forEach items="${pictures}" var="pic">
  			<img class="imgage" src="${pic.url}" style="width: 200px;height: 200px;"/>
  		</c:forEach>
  		</div>
	</div>
</div>
		<div></div>
		<hr size=1 color=#00ffff align=center noshade>
		<div class="header" id="default" style="display:none">
			相关新闻:
			<div id="news">
			</div>
		</div> --%>
		<div class="col-sm-10 thumbnail container">
			<div class="page-header">
				<h4>${news.title }</h4><small class="text-muted">来源:${tenancy.name } <br>${news.crtDate }  </small>
			</div>
			<div class="alert alert-success">
				${news.intro }
			</div>
			<c:forEach var="con" items="${news.contents }" varStatus="status">
			<c:if test="${con.video != null && con.video ne ''}">
			视频：${con.videoTitle }<br/>
		</c:if>
		<c:if test="${con.photo != null && con.photo ne ''}">
				<img src="${con.photo }" alt="标题图片"></img>
				<p class=" text-muted text-center">
					<small>${con.photoTitle}</small>
				</p>
		</c:if>
		<p class="form-control-static"> &nbsp &nbsp &nbsp${con.detail }</p>
		</c:forEach>
		</div>
		
	</body>
</html>