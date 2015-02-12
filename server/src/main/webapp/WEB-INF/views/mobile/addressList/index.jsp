<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page session="false"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
  <head>
    <meta charset="UTF-8">
    <title>通讯录</title>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no, minimal-ui">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">

  </head>
  <body>
    <header class="bar bar-nav">	
    	    <a class="icon icon-left-nav pull-left" style="font-size:17px;padding-top:15px" href="javascript:go();">返回</a> 
      <h1 class="title">通讯录</h1>
    </header>

    <div class="content">
    <ul class="table-view" style="margin-top: 0px" id="table-view">
        <li class="table-view-divider">通讯录列表</li>
	<c:forEach var="list" items="${lists.content}">
			  <li class="table-view-cell media">
			    <a style="padding-right:15px"  data-transition="slide-in" href="#">
			      <img class="media-object pull-left" width="60px" height="60px" src="${list.avatar}">
			      <div class="media-body" style="height:47px;font-size:17px;margin-top: 10px">
			      	${list.name}
			        <p style="margin-top: 5px;font-size:14px;">${list.dept}${list.position}</p>
			      </div>
			       <div class="media-body" style="font-size:18px;margin-top: 13px">
			      	${list.companyName}
			        <p style="font-size:14px;margin-left: 1px">电话：${list.officePhone}</br>手机：${list.tel}</br>邮箱：${list.email}</br>地址：${list.address}</p>
			      </div>		      
			    </a>
			  </li>
		  </c:forEach>
      </ul>
     <div style="padding: 14px 40px;">
     	<input type="hidden" id="UID" name="UID" value="${UID}"/>
	    <button class="btn btn-block btn-primary" style="background-color: #f8f9fa;color:black;border: 1px solid #ccc;" onclick="more()">点击加载更多</button>
	 </div>
    </div>
   <script type="text/javascript">
	   function go()
	   {
	   	var uid = $("#UID").val();
	   	var url = "<%=request.getContextPath()%>/m/index?uid="+uid;
	   	location.href = url;
	   }
   		var pn =1;
		function more(){
			pn = pn+1;
			var UID = $("#UID").val();
			$.ajax({
				url: '<%=request.getContextPath()%>/m/addressList/list?UID='+UID+'&pageNum='+pn, 
				type: 'GET',
				contentType: "application/json;charset=UTF-8",
				dataType: 'text',
				success: function(data){
					var parsedJson = $.parseJSON(data);
					jQuery.each(parsedJson, function(index, itemData) {
							 $("#table-view").append("<li class='table-view-cell media'><a style='padding-right:15px' data-transition='slide-in' href='#'><img class='media-object pull-left' width='60px' height='60px' src='"+itemData.avatar+"'><div class='media-body' style='height:47px;font-size:17px;margin-top: 10px'>"+itemData.name+"<p style='margin-top: 5px;font-size:14px;'>"+itemData.dept+""+itemData.position+"</p></div><div class='media-body' style='font-size:18px;margin-top: 13px'>"+itemData.companyName+"<p style='font-size:17px;margin-left: 1px'>电话："+itemData.officePhone+"</br>手机："+itemData.tel+"</br>邮箱："+itemData.email+"</br>地址："+itemData.address+"</p></div></a></li>"); 
					});
				}
			});

		}
	
	</script>
  </body>
</html>

