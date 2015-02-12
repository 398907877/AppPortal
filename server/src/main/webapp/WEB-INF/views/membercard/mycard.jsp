<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.huake.com/functions" prefix="function" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ page session="false"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
    <title>Bootstrap-我的会员卡</title>
    <link href ="${ctx }/static/bootstrap/3.1.1/ css/bootstrap.min.css" rel ="stylesheet ">
    <script src ="${ctx }/static/ js/jquery.min.js"></script>
    <script src ="${ctx }/static/bootstrap/3.1.1/ js/bootstrap.min.js"></script>
</head>
<body>
   <div class="container">
	<div class="row">
	    <div class="col-md-2">
			<%@ include file="nav.jsp"%>
		</div>
        <div class="col-md-10 bs-callout bs-callout-info">
			<div>
				<h4>我的会员卡</h4>
				<h4><small>该功能面向其它平台：包括微信、手机网站、网站、手机客户端应用，非管理平台</small></h4>
			</div>
		    <div>			
		      <c:if test="${not empty message}">
			  <div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
		    </c:if>
		    </div>       
	    </div>
    </div>
 </div>
<script>
   $(function () { $('#myModal').modal('hide')})});
</script>
<script>
   $(function () { $('#myModal').on('hide.bs.modal', function () {
      alert('嘿，我听说您喜欢模态框...');})
   });
</script>
</body>
</html>