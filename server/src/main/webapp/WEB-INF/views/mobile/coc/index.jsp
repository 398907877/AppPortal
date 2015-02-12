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
    <title>商会企业</title>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no, minimal-ui">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
 	<link rel="stylesheet" href="${ctx}/static/ratchet/css/ratchet.min.css">
    <script src="${ctx}/static/ratchet/js/ratchet.min.js"></script>
	<script src="${ctx}/static/js/jquery.min.js" type="text/javascript"></script>
  </head>
  <body>
  <div id="category" class="popover">
    <ul class="table-view">
	   <li class="table-view-cell">		
			  企业类型
		</li>
    	<li class="table-view-cell">
    		<a class="navigate-right" href="javascript:turn(${UID});" data-transition="slide-in">
			  全部类型
			</a>
		</li>
    	<c:forEach var="category" items="${categories}">
    		  <li class="table-view-cell">
			    <a class="navigate-right" href="javascript:turn(${UID},${category.id});" data-transition="slide-in">
			      ${category.name}
			    </a>
			  </li>
		</c:forEach>
    </ul>
</div>
  
  
    <header class="bar bar-nav">

      	    <a class="icon icon-left-nav pull-left" style="font-size:17px;padding-top:15px" href="javascript:go();">返回</a> 

                  <h1 class="title">商会企业</h1>
    </header>

    <div class="content">
    <ul class="table-view" style="margin-top: 0px" id="table-view">
        <li class="table-view-divider">企业列表</li>
	<c:forEach var="customer" items="${customers.content}">
			  <li class="table-view-cell media">
			    <a style="padding-right:15px" data-transition="slide-in" href="javascript:turn(${customer.id});">
			      <img class="media-object pull-left" width="100px" height="100px" src="${customer.pic}">
			      <div class="media-body">
			      	${customer.name}
			        <p>联系人：${customer.linkman}</br>电话：${customer.tel}</br>地址：${customer.address}</br>经营范围：${customer.businessScope}</p>
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
   	var url = "<%=request.getContextPath()%>/m/customer/"+id;
   	location.href = url;
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
			$.ajax({
				url: '<%=request.getContextPath()%>/m/customer/list?UID='+UID+'&pageNum='+pn, 
				type: 'GET',
				contentType: "application/json;charset=UTF-8",
				dataType: 'text',
				success: function(data){
					var parsedJson = $.parseJSON(data);
					jQuery.each(parsedJson, function(index, itemData) {
							 $("#table-view").append("<li class='table-view-cell media'><a style='padding-right:15px' data-transition='slide-in' href='javascript:turn("+itemData.id+");'><img class='media-object pull-left' width='100px' height='100px' src='"+itemData.pic+"'><div class='media-body'>"+itemData.name+"<p>联系人："+itemData.linkman+"</br>电话：："+itemData.tel+"</br>地址："+itemData.address+"</br>经营范围："+itemData.businessScope+"</p></div></a></li>"); 
					});
				}
			});

		}
	
	</script>
  </body>
</html>

