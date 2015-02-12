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
    <title>营销互动</title>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no, minimal-ui">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">

  </head>
  <body>
    <header class="bar bar-nav">	
    	    <a class="icon icon-left-nav pull-left" style="font-size:17px;padding-top:15px" href="javascript:go();">返回</a>
      <h1 class="title">营销互动</h1>
    </header>

    <div class="content">
    <ul class="table-view" style="margin-top: 0px" id="table-view">
        <li class="table-view-divider">活动列表</li>
	<c:forEach var="event" items="${lists.content}">
			  <li class="table-view-cell media">
			    <a  href="javascript:turn(${event.id});">
			      <img class="media-object pull-left" width="80px" height="80px" src="${event.image}">
			      <div class="media-body">
			      	${event.title}
			        <p>开始时间：${fn:substring(event.startDate,0,10)}</br>结束时间：${fn:substring(event.endDate,0,10)}</br>简要说明：${event.description}</p>
			      </div>	
			      </a>
			  </li>
		  </c:forEach>
      </ul>
     <div style="padding: 14px 40px;">
     <%
				HttpSession session = request.getSession(); 
			%>
       <input type="hidden" id="memberId" name="memberId" value="<%=session.getAttribute("memberId") %>"/>
       <input type="hidden" id="login" name="login" value="<%=session.getAttribute("login") %>"/>
     	<input type="hidden" id="UID" name="UID" value="${UID}"/>
	    <button class="btn btn-block btn-primary" style="background-color: #f8f9fa;color:black;border: 1px solid #ccc;" onclick="more()">点击加载更多</button>
	 </div>
    </div>
   <script type="text/javascript">
   function turn(id)
   {
		var uid = $("#UID").val();
   	$.ajax({
		url: '<%=request.getContextPath()%>/m/ernies/action/'+id+'?uid='+uid, 
		type: 'GET',
		contentType: "application/json;charset=UTF-8",
		success: function(data){
			if(data.action=="false"){
				alert("活动尚未开始！");
			}else if(data.action=="true"){
				location.href = "<%=request.getContextPath()%>"+data.url;
			}
		}
	});
   }
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
			var s="";
			$.ajax({
				url: '<%=request.getContextPath()%>/m/ernies/list?UID='+UID+'&pageNum='+pn, 
				type: 'GET',
				contentType: "application/json;charset=UTF-8",
				dataType: 'text',
				success: function(data){
					var parsedJson = $.parseJSON(data);
					jQuery.each(parsedJson, function(index, itemData) {
							var strDate = itemData.startDate.substring(0,10);
							var endDate = itemData.endDate.substring(0,10);
							s="<li class='table-view-cell media'><a  data-transition='slide-in' href='javascript:turn("+itemData.id+");'>"+
							"<img class='media-object pull-left' width='80px' height='80px' src='"+itemData.image+"'>"+
							"<div class='media-body'>"+itemData.title+"<p>开始时间："+strDate+"</br>结束时间："+endDate+"</br>"+
							"简要说明："+itemData.description+"</p></div></a></li>";
							 $("#table-view").append(s); 
					});
				}
			});

		}
	
	</script>
  </body>
</html>

