<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="huake" uri="/huake"%>
<%@ page session="false"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
  <head>
    <meta charset="UTF-8">
    <title>团购活动</title>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no, minimal-ui">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">

  </head>
  <body>
    <header class="bar bar-nav" >	
    	    <a class="icon icon-left-nav pull-left" style="font-size:18px;padding-top:12px" href="javascript:go();">返回</a>
      <h1 class="title">团购活动</h1>
    </header>

    <div class="content">
    <ul class="table-view" style="margin-top: 0px" id="table-view">
        <li class="table-view-divider">团购列表</li>
	<c:forEach var="groupOn" items="${groupOns.content}">
			  <li class="table-view-cell media" style="padding-right: 15px">
			    <a  href="javascript:turn(${groupOn.id});" >
			      <div class="media-body" style="margin-right: 10px">
			      	标题
			        <p>时间:${fn:substring(groupOn.startDate,0,10)}——${fn:substring(groupOn.endDate,0,10)}</p>
			        <p>团购价:￥${groupOn.groupOnPrice}&nbsp&nbsp&nbsp&nbsp原价:￥${groupOn.originPrice}&nbsp&nbsp&nbsp&nbsp折扣:${groupOn.discount}</p>
			        <p>参与人数:${groupOn.peopleCount}</p>
			        <p>简单说明:${groupOn.intro}</p>
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
   function turn(id)
   {
   	var url = "<%=request.getContextPath()%>/m/groupon/detail/"+id;
   	location.href = url;
   }
   function go()
   {
   	var uid = $("#UID").val();
   	var url = "<%=request.getContextPath()%>/m/index?uid="+uid;
   	location.href = url;
   }
   
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
								s=s+"<span class='badge badge-negative' onclick='cancel("+itemData.id+")'>已报名</span>";
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

