<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page session="false"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
	<head>
		<title>登陆</title>
		<link href="${ctx}/static/mobile/event.css" type="text/css" rel="stylesheet" />
		<meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no, minimal-ui">
	    <meta name="apple-mobile-web-app-capable" content="yes">
	    <meta name="apple-mobile-web-app-status-bar-style" content="black">
	    <link href="${ctx}/static/select2-3.4.6/select2.css" rel="stylesheet"/>
	    <script src="${ctx}/static/select2-3.4.6/select2.js"></script>
	    <script src="${ctx}/static/js/jquery.min.js" type="text/javascript"></script>
	    <script type="text/javascript" src="<c:url value="${ctx}/static/js/jquery.validate.js" />"></script>
	    <link href="${ctx}/static/bootstrap/3.1.1/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
	    <script src="${ctx}/static/bootstrap/3.1.1/js/bootstrap.min.js" type="text/javascript"></script>
	    
	    
	 
		<style type="text/css" >
			body,div,dl,dt,dd,ul,ol,li,h1,h2,h3,h4,h5,h6,pre,form,fieldset,input,textarea,p,blockquote,th,td {padding: 0;margin: 0;}
			span{font-size:17px;color:#272626;}
			.showtxt{font-size:14px; color: #777;margin-bottom:15px;margin-top:3px;}
		</style>
	</head>
	<body >
		<header class="bar bar-nav">
	      <h1 class="title">登陆</h1>
	    </header>
		<div class="container" style="margin-top: 100px">
			<form:form modelAttribute="TenancyUser" method="post" id="form" enctype="multipart/form-data" cssClass="form-horizontal"  action="${ctx}/m/login/check?url=${url}">
				<input type="hidden" id="url" name="url" value="${url}"/>
				<%
				HttpSession session = request.getSession(); 
				if(session.getAttribute("login")!=null){
					if(session.getAttribute("login").equals("false")){
			%>
			
				<div class="alert alert-error input-medium controls">
					<button class="close" data-dismiss="alert">×</button>登录失败，请重试.
				</div>
			<%
					}
				}
						%>

				<div class="control-group" style="text-align:center" >
					<div class="controls" >
					<label class="control-label" style="color:#FFc00e;font-size:17px;">登录账号:</label>
						<form:input style="width:70%" path="loginname" name="loginname" id="loginname" placeholder="请输入登录名"/>
					</div>
				</div>
				<br>
				<div class="control-group" style="text-align:center">
					<div class="controls">
					<label class="control-label" style="color:#FFc00e;font-size:17px;">登录密码:</label>
						<form:password style="width:70%" path="password" name="password" id="password"  placeholder="请输入密码"/>
					</div>
				</div >
				
				<br>
				<br>
				<div style="text-align:center">
					<input type="hidden" style="width:70%" id="uid" name="uid" value="${uid}" readonly="readonly"/>
					<button  type="submit" style="background: #FFc00e;width: 125px;" class="btn btn-primary" id="submit"><span style="color:#ec2638;font-size:30px;font-family:微软雅黑" >确定</span></button>
				</div>
			</form:form>
	    </div>
	    <script> 
	    $("#form").validate({
			rules:{
				loginname:{
					required:true
				},
				password:{
					required:true
				}
			},errorPlacement: function(error, element) {
				error.insertAfter(element).attr("style","color:red;position: relative;left:20px;"); 
				$('<br/>').insertAfter(element);
			} ,
			messages:{
				loginname:{
				required:"登录账号不能为空",
				},
				password:{
					required:"登录密码不能为空"
				}
			}
		});
	    </script>
	</body>
</html>