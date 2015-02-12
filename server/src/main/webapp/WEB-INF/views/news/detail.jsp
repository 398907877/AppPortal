<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>新闻资讯详情</title>
</head>

<body>
	<form id="inputForm" action="${ctx}/mgr/news/${action}" method="post" class="form-horizontal">
		<input type="hidden" name="id" value="${news.id}"/>
		<fieldset>
		
			<legend>
			图文资讯详情
			</legend>


<div class="form-group">
	<label class="col-sm-2 control-label" for="title">图文资讯标题:</label>
	<div class="col-sm-8 controls">
		<p class="form-control-static">${news.title }</p>
	</div>
</div>
<div class="form-group">
	<label class="col-sm-2 control-label" for="title">图文资讯图片:</label>
	<div class="col-sm-8 controls">
		<img src="${news.pic }" alt="标题图片" style="max-width:160px;" class="img-thumbnail">
	</div>
</div>
<div class="form-group">
	<label class="col-sm-2 control-label" >图文资讯类型:</label>
	<div class="col-sm-8 controls">
		<p class="form-control-static">${newsCategory.name }</p>
	</div>
</div>

<div class="form-group">
	<label class="col-sm-2 control-label" for="intro">图文资讯简介:</label>
	<div class="col-sm-8 controls">
		<p class="form-control-static">${news.intro }</p>
	</div>
</div>
<c:forEach var="con" items="${news.contents }" varStatus="status">
<div class="form-group">
	<label class="col-sm-2 control-label">详细内容${status.index+1 }</label>
	<div class="col-sm-8 controls">
		<c:if test="${con.video != null && con.video ne ''}">
			视频：${con.videoTitle }<br/>
		</c:if>
		<c:if test="${con.photo != null && con.photo ne ''}">
			<div class="thumbnail" style="max-width:300px;">
				<img src="${con.photo }" alt="${photo.title }"></img>
				<p class=" text-muted text-center">
					<small>${con.photoTitle}</small>
				</p>
			</div>
		</c:if>
		<p class="form-control-static">${con.detail }</p>
	</div>
</div>
</c:forEach>
<%--  <div class="control-group" id="picFile">
	<label class="control-label" for="file-input">图片集展示：</label>
	<div class="controls">
        <div class="gallery-set-thumbail">
         <c:forEach items="${pictures}" var="pic">
  			<img src="${pic.url}" width="300" height="300"/>
  		</c:forEach>
  		</div>
	</div>
</div> --%>
		</fieldset>
	</form>
	<script>
		$(document).ready(function() {
			  $("button[name='account-tab']").addClass("active"); 
		});
		$(function(){
			$("#submit_btn").click(function(){
				var pic=$('input[name="choose"]:checked').val();
				if(pic=='picFile'){
					$("#picUrl").remove();
				}else if(pic=='picUrl'){
					$("#picFile").html("<input class='input-file' name='fileInput' id='fileInput' type='file'>");
				}
				$("#onsubmit").trigger("click"); 
			});
		});
		
	</script>
</body>
</html>
