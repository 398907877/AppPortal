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
    <title>企业活动</title>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no, minimal-ui">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">

  </head>
  <body>
    <header class="bar bar-nav" >	
    	    <a class="icon icon-left-nav pull-left" style="font-size:18px;padding-top:12px" href="javascript:go();">返回</a>
      <h1 class="title">企业活动</h1>
    </header>

    <div class="content">
    <ul class="table-view" style="margin-top: 0px" id="table-view">
        <li class="table-view-divider">活动列表</li>
	<c:forEach var="event" items="${events.content}">
			  <li class="table-view-cell media">
			    <a  href="javascript:turn(${event.id});">
			      <img class="media-object pull-left" width="80px" height="80px" src="${event.poster}">
			      <div class="media-body">
			      	${event.title}
			        <p>时间：${fn:substring(event.startDate,0,10)}</br>地点：${event.address}</p>
			      </div>	
			      </a>
			      <c:choose>
					<c:when test="${event.singUp == ''|| event.singUp == 'joinFalse'|| event.singUp == null }">
					         <span id="id1_${event.id}" class="badge badge-negative" onclick="join(${event.id})" >报名</span>
					         <span id="id2_${event.id}"  class="badge badge-negative" onclick="cancel(${event.id})" style="display: none">已报名</span>
					</c:when>
					<c:when test="${event.singUp == 'joinTrue'}">
							 <span id="id1_${event.id}" class="badge badge-negative" onclick="join(${event.id})" style="display: none">报名</span>
					         <span id="id2_${event.id}"  class="badge badge-negative" onclick="cancel(${event.id})">已报名</span>
					</c:when>
				</c:choose>
			      
			    
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
   	var url = "<%=request.getContextPath()%>/m/events/"+id;
   	location.href = url;
   }
   function go()
   {
   	var uid = $("#UID").val();
   	var url = "<%=request.getContextPath()%>/m/index?uid="+uid;
   	location.href = url;
   }
   
   function join(eventId){
	   var uid = $("#UID").val();
       var url;
       var s ;
	   $.ajax({
			url: '<%=request.getContextPath()%>/m/events/partake?eventId='+eventId+'&uid='+uid, 
			type: 'DELETE',
			contentType: "application/json;charset=UTF-8",
			dataType: 'json',
			success: function(data){
				if(data.login=='false'){
					alert("您尚未登陆哦！请先登陆。");
					s="/m/events?uid="+uid+"%26memberId=";
					url = "<%=request.getContextPath()%>/m/login?uid="+uid+"&url="+s;
				   	location.href = url;
				}else{
				var s1 = "#id1_"+eventId;
				var s2 = "#id2_"+eventId;
				$(s1).css("display","none");
				$(s2).css("display","block");
				//$(s1).html(""); 
				//$(s1).append("已报名");
				}
			},error:function(xhr){
				alert('错误了，请重试');
			}
		});
   }
   
   function cancel(eventId){
	   var memberId = $("#memberId").val();
	   $.ajax({
			url: '<%=request.getContextPath()%>/m/events/cancel?eventId='+eventId+'&userId='+memberId, 
			type: 'DELETE',
			contentType: "application/json;charset=UTF-8",
			dataType: 'json',
			success: function(data){
				var s1 = "#id1_"+eventId;
				var s2 = "#id2_"+eventId;
				$(s2).css("display","none");
				$(s1).css("display","block");
			},error:function(xhr){
				alert('错误了，请重试');
			}
		});
   }

		var pn =1;
		function more(){
			pn = pn+1;
			var UID = $("#UID").val();
			var memberId = $("#memberId").val();
			var s="";
			$.ajax({
				url: '<%=request.getContextPath()%>/m/events/list?UID='+UID+'&pageNum='+pn, 
				type: 'GET',
				contentType: "application/json;charset=UTF-8",
				dataType: 'text',
				success: function(data){
					alert(data);
					var parsedJson = $.parseJSON(data);
					jQuery.each(parsedJson, function(index, itemData) {
						alert(itemData.singUp);
							var strDate = itemData.startDate.substring(0,10);
							s="<li class='table-view-cell media'><a  data-transition='slide-in' href='javascript:turn("+itemData.id+");'>"+
							"<img class='media-object pull-left' width='80px' height='80px' src='"+itemData.poster+"'>"+
							"<div class='media-body'>"+itemData.title+"<p>时间："+strDate+"</br>地点："+itemData.address+"</p></div></a>";
							if(itemData.singUp == "joinTrue"){
								s=s+"<span id='id_"+itemData.id+"' class='badge badge-negative' onclick='cancel("+itemData.id+")'>已报名</span>";
							}else{
								s=s+"<span id='id_"+itemData.id+"' class='badge badge-negative' onclick='join("+itemData.id+")'>报名</span>";
							}
							s=s+"</li>";
							 $("#table-view").append(s); 
					});
				}
			});

		}
	
	</script>
  </body>
</html>

					         
