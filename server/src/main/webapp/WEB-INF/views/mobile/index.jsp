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
    <meta charset="UTF-8">
    <title>首页</title>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no, minimal-ui">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
 	<link rel="stylesheet" href="${ctx}/static/ratchet/css/ratchet.min.css">
    <script src="${ctx}/static/ratchet/js/ratchet.min.js"></script>
	<script src="${ctx}/static/js/jquery.min.js" type="text/javascript"></script>
	<style>
.row{margin-right:10px;margin-left:10px}
.col-md-3 {width: 45%;}
.thumbnail {display: block;padding: 4px;margin-bottom: 20px;
line-height: 1.42857143;background-color: #fff;border: 1px solid #ddd;
border-radius: 4px;transition: all }
.thumbnail>img, .thumbnail a>img {margin-right: auto;margin-left: auto;}
.bs-example {padding:30px 0 0;}
	</style>
  </head>
  <body>
    <header class="bar bar-nav">
      <h1 class="title">首页</h1>
    </header>

	 <div class="content">
          <div class="slide">
            <img src="${ctx}/static/images/login_slide_1.jpg" alt="Argo" width="100%" height="60%">
          </div>
          <div class="bs-example">
		<div class="row" style="width:100%">
      <div class="col-md-3" style=" float: left;margin-right: 10px;">
        <a href="javascript:link1();" class="thumbnail">
  		 <img src="${ctx}/static/images/11.jpg" style="height: 150px; width: 100%; display: block;">
        </a>
      </div>
      <div class="col-md-3" style=" float: left;margin-right: 10px;">
        <a href="javascript:link2();" class="thumbnail">
          <img src="${ctx}/static/images/11.jpg" style="height: 150px; width: 100%; display: block;">
        </a>
      </div>
</div>
<div class="row" style="width:100%">
      <div class="col-md-3" style=" float: left;margin-right: 10px;">
        <a href="javascript:link3();" class="thumbnail">
 		  <img src="${ctx}/static/images/9.jpg" style="height: 150px; width: 100%; display: block;">
        </a>
      </div>
      <div class="col-md-3" style=" float: left;margin-right: 10px;">
        <a href="javascript:link4();" class="thumbnail">
          <img src="${ctx}/static/images/9.jpg" style="height: 150px; width: 100%; display: block;">
        </a>
      </div>
</div>
<div class="row" style="width:100%">
      <div class="col-md-3" style=" float: left;margin-right: 10px;">
        <a href="javascript:link5();" class="thumbnail">
 		  <img src="${ctx}/static/images/high-bg.jpg" style="height: 150px; width: 100%; display: block;">
        </a>
      </div>
      <div class="col-md-3" style=" float: left;margin-right: 10px;">
        <a href="javascript:link6();" class="thumbnail">
          <img src="${ctx}/static/images/high-bg.jpg" style="height: 150px; width: 100%; display: block;">
        </a>
      </div>
	</div>
	<div class="row" style="width:100%">
      <div class="col-md-3" style=" float: left;margin-right: 10px;">
        <a href="javascript:link7();" class="thumbnail">
 		  <img src="${ctx}/static/images/high-bg.jpg" style="height: 150px; width: 100%; display: block;">
        </a>
      </div>
      <div class="col-md-3" style=" float: left;margin-right: 10px;">
        <a href="javascript:link8();" class="thumbnail">
 		  <img src="${ctx}/static/images/high-bg.jpg" style="height: 150px; width: 100%; display: block;">
        </a>
      </div>
	</div>
	<div class="row" style="width:100%">
      <div class="col-md-3" style=" float: left;margin-right: 10px;">
        <a href="javascript:link9();" class="thumbnail">
 		  <img src="${ctx}/static/images/high-bg.jpg" style="height: 150px; width: 100%; display: block;">
        </a>
      </div>
	</div>
	</div>
	<%
				HttpSession session = request.getSession(); 
			%>
	<input type="hidden" id="memberId" name="memberId" value="<%=session.getAttribute("memberId") %>"/>
	<input type="hidden" id="UID" name="UID" value="${uid}"/>
	<input type="hidden" id="login" name="login" value="<%=session.getAttribute("login") %>"/>
      </div>
	<script type="text/javascript">
	var UID = $("#UID").val();
	function link1()
   {
   	var url = "<%=request.getContextPath()%>/m/news?uid="+UID;
   	location.href = url;
   }
   function link2()
   {
	var memberId = $("#memberId").val();
	var login = $("#login").val();
	if(memberId==null||memberId=="null"||login=="false"||login=="null"){
		memberId = "";
	}
   	var url = "<%=request.getContextPath()%>/m/events?uid="+UID+"&memberId="+memberId;
   	location.href = url;
   }
   function link3()
   {
   	var url = "<%=request.getContextPath()%>/m/customer?uid="+UID;
   	location.href = url;
   }
   function link4()
   {
   	var url = "<%=request.getContextPath()%>/m/addressList?uid="+UID;
   	location.href = url;
   }
   function link5()
   {
   	var url = "<%=request.getContextPath()%>/m/supply?uid="+UID;
   	location.href = url;
   }
   function link6()
   {
   	var url = "<%=request.getContextPath()%>/m/invitation?uid="+UID;
   	location.href = url;
   }
   function link7()
   {
   	var url = "<%=request.getContextPath()%>/m/product?uid="+UID;
   	location.href = url;
   }
   function link8()
   {
   	var url = "<%=request.getContextPath()%>/m/ernies?uid="+UID;
   	location.href = url;
   }
   function link9()
   {
   	var url = "<%=request.getContextPath()%>/m/groupon?uid="+UID;
   	location.href = url;
   }
   </script>
  </body>
</html>
