<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.huake.com/functions" prefix="function" %>

<%@ page session="false"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
  <head>
    <meta charset="UTF-8">
    <title>供求信息</title>
 
		<style>
.detailCon {
    color: #777;
    font-size: 14px;
    line-height: 22px;
    max-height: 155px;
    overflow: hidden;
    position: relative;
}
</style>
  </head>
  <body>
    <header class="bar bar-nav">	 
    
    	    <a class="icon icon-left-nav pull-left" style="font-size:17px;padding-top:15px" href="javascript:go();">返回</a>
      <h1 class="title">供求信息</h1>
    </header>
    
    <div class="content">
    <div style="height:40px">
		<div style="border:1px solid #EBEBEB;width:50%;height:40px;float:left;text-align:center;line-height:40px;">
		<a class="" href="javascript:click(${UID},'0');" style="font-size:20px;" data-transition="slide-in">
		<c:if test="${type eq 0 }"><span style="color:#B23AEE">供应</span></c:if>
		<c:if test="${type eq 1 }"><span style="color:#AAAAAA">供应</span></c:if>
		</a>
		</div>
		<div style="border:1px solid #EBEBEB;width:50%;height:40px;float:left;text-align:center;line-height:40px;">
		<a class="" href="javascript:click(${UID},'1');" style="font-size:20px;" data-transition="slide-in">
		<c:if test="${type eq 1 }"><span style="color:#B23AEE">需求</span></c:if>
		<c:if test="${type eq 0 }"><span style="color:#AAAAAA">需求</span></c:if>
		</a>
		</div>
	</div>
    <ul class="table-view" style="margin-top: 0px" id="table-view">
	<c:forEach var="supplyDemand" items="${supplyDemands.content}">
			  <li class="table-view-cell media">
			    <a  class="navigate-right" href="javascript:turn(${supplyDemand.id});">
						<div class="media-body">
							${function:truncate(supplyDemand.title, 10)}
							<div style="color: #777; font-size: 14px;">
								${fn:substring(supplyDemand.startDate,0,10)}</div>

							<div class="detailCon" style="margin-top: 6px;">
								<c:choose>
									<c:when test="${fn:length(supplyDemand.introduce)>30 }">
										<c:out escapeXml="false"
											value="${fn:substring(supplyDemand.introduce,0,30) }..." />
									</c:when>
									<c:otherwise>
										<c:out escapeXml="false" value="${supplyDemand.introduce}" />
									</c:otherwise>
								</c:choose>

							</div>
						</div>
				</a>
			  </li>
		  </c:forEach>
      </ul>
     <div style="padding: 14px 40px;">
     	<input type="hidden" id="UID" name="UID" value="${UID}"/>
     	<input type="hidden" id="type" name="type" value="${type}"/>
	    <button class="btn btn-block btn-primary" style="background-color: #f8f9fa;color:black;border: 1px solid #ccc;" onclick="more()">点击加载更多</button>
	 </div>
    </div>
   <script type="text/javascript">
   function click(UID,type){
	   var url = "<%=request.getContextPath()%>/m/supply?uid="+UID;
	   if(type==0){
		   url = url + "&type=0";
	   }else{
		   url = url + "&type=1";
	   }
	   
   	
	   
 	location.href = url;
   }
   
   function turn(id)
   {
   	var url = "<%=request.getContextPath()%>/m/supply/"+id;
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
			var type = $("#type").val();
			$.ajax({
				url: '<%=request.getContextPath()%>/m/supply/list?UID='+UID+'&pageNum='+pn+'&type='+type, 
				type: 'GET',
				contentType: "application/json;charset=UTF-8",
				dataType: 'text',
				success: function(data){
					var parsedJson = $.parseJSON(data);
					jQuery.each(parsedJson, function(index, itemData) {
						
						if(itemData.introduce.length>30)
						{
							var intro = itemData.introduce.substring(0,30)+"...";	
						}
						else
						{
							var intro = itemData.introduce;	
						}
						
						     var strDate = itemData.startDate.substring(0,10);
						     var str = "<li class='table-view-cell media'><a  class='navigate-right' data-transition='slide-in' href='javascript:turn("
									+itemData.id+","+itemData.type+")'><div class='media-body' >"+itemData.title+
									"<div style='color: #777;font-size: 14px;'>"+strDate+"</div><div class='detailCon' style='margin-top: 6px;'>"+itemData.introduce+"</div></div></a></li>";		
						     $("#table-view").append(str); 
	
							// $("#table-view").append("<li class='table-view-cell media'><a  class='navigate-right' data-transition='slide-in' href='javascript:turn("+itemData.id+","+itemData.type+")'><div class='media-body' >"+itemData.title+"<p style='margin-top: 6px'>"+strDate+"</br>"+itemData.introduce+"</p></div></a></li>"); 
					});
				}
			});
		}


	</script>
  </body>
</html>

