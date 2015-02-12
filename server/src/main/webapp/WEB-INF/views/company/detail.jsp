<!DOCTYPE HTML>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>企业介绍详情</title>
		<link href="${ctx}/static/bootstrap/3.1.1/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
		
	</head>
	<body>
		<form action="" class="form-horizontal">
		<fieldset>
		
			<legend>
				企业详情信息
			</legend>

		<div class=form-group>
			<label class="col-sm-2 control-label" for="title">企业名称:</label>
			<div class="col-sm-8 controls">
				<p class="form-control-static">${company.title }</p>
			</div>
		</div>
		<div class=form-group>
			<label class="col-sm-2 control-label" for="title">创建时间:</label>
			<div class="col-sm-8 controls">
				<p class="form-control-static"><fmt:formatDate value="${company.crtDate }" pattern="yyyy-MM-dd HH:mm:ss" /></p>
			</div>
		</div>
		<div class=form-group>
			<label class="col-sm-2 control-label" for="title">企业类型:</label>
			<div class="col-sm-8 controls">
				<p class="form-control-static">${companyCategory.name}</p>
			</div>
		</div>
		<div class=form-group>
			<label class="col-sm-2 control-label" for="title">企业简介:</label>
			<div class="col-sm-8 controls">
				<p class="form-control-static">${company.intro }</p>
			</div>
		</div>
		<div class=form-group>
			<label class="col-sm-2 control-label" for="title">企业详情:</label>
			<div class="col-sm-8 controls">
				<p class="form-control-static">${what }</p>
			</div>
		</div>
		<div class=form-group>
			<label class="col-sm-2 control-label" for="title">企业图片:</label>
			<div class="col-sm-8 controls">
				<div class="gallery-set-thumbail">
        			<c:forEach items="${pictures}" var="pic">
  					<img class="imgage" src="${pic.url}" style="width: 200px;height: 200px;margin-top:10px"/>
  					</c:forEach>
  				</div>
			</div>
		</div>
		
		</fieldset>
		</form>
	</body>
	
</html>