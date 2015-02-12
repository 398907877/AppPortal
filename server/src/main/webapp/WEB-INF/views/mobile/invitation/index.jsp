<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page session="false"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8">
    <title>手机论坛</title>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no, minimal-ui">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
 	<link rel="stylesheet" href="${ctx}/static/ratchet/css/ratchet.min.css">
    <script src="${ctx}/static/ratchet/js/ratchet.min.js"></script>
	<script src="${ctx}/static/js/jquery.min.js" type="text/javascript"></script>

	<style>
ul, ol {
    list-style: none outside none;
}
body, ul, li, dl, dt, dd, h1, h2, h3, h4, p, a, img {
    font-family: "Helvetica Neue",Helvetica,STHeiTi,sans-serif;
    font-size: 12px;
    margin: 0;
    padding: 0;
}
.fl {
    float: left;
}
a, button, input {
    outline: 0 none;
    text-decoration: none;
}


.topicBox {
    background-color: #fff;
    box-shadow: 0 1px 2px #cacaca;
    margin-bottom: 10px;
    overflow: hidden;
	border-radius: 1px;
	padding: 11px 11px 11px 15px; 
 }
.detailCon p {
    color: #545454;
    font-size: 16px;
    line-height: 22px;
    max-height: 155px;
    overflow: hidden;
    position: relative;
}

.noPerImg li {
    padding: 0 0 4px;
}
.topicList {
    background-color: #fafafa;
    border-top: 1px solid #ebebeb;
    padding: 10px 10px 7px;
}
.topicList .more {
    border-top: 1px solid #ebebeb;
    color: #a7a7a7;
    font-size: 12px;
    line-height: 25px;
    margin-top: 10px;
    overflow: hidden;
    padding-top: 5px;
    text-align: left;
}
.topicList li {
    line-height: 20px;
    overflow: hidden;
    padding: 0 0 8px 3px;
    position: relative;
}
.topicList li a {
    color: #545454;
    display: block;
}
.topicList .sW span, .topicList .reply {
    color: #000;
}
.topicList .more a {
    margin-right: 10px;
    padding: 5px 5px 10px;
}


	</style>
  </head>
  <body>



    <header class="bar bar-nav">
	 <a class="pull-left" href="javascript:go();">
            <span class="icon icon-w3c-list" style="font-size: 18px;">返回</span></a>
      <h1 class="title" >手机论坛</h1>
    </header>

    <div class="content " style="background-color: #efefef; ">


      <ul class="table-view" style="background-color: #efefef;padding: 10px;" id="table-view">
         <li class="table-view-cell media topicBox">
            <img class="media-object pull-left" src="http://placehold.it/64x64" alt="Placeholder image for Argo's poster">
            <div class="media-body" style="margin-top:5px">
              华科论坛
			  <div style="padding-top:15px">
			  <p>
             <span>${countInvs}话题数</span> <span>${countTus}成员</span>
			 </p>
			 </div>
            </div>
        </li>

 	 	<c:forEach var="invitation" items="${invitations.content}">
        <li class="table-view-cell media topicBox">
          <img class="media-object  pull-left"  width="38" height="38"  src="${invitation.user.avatar}" alt="头像">
            <div class="media-body">
              <span style="font-size: 16px;" >${invitation.crUser}</span>
              <p style="color: #a0a0a0;display: block;font-size: 11px;">
              ${fn:substring(invitation.crtDate,0,10)}</p>
            </div>
			<div class="detailCon">
<p id="p1_${invitation.id}">
<span style="font-size: 20px">${invitation.title}</span>
<br>
<br>
------------------------------------
<br>
 ${invitation.introduce}
<br>
</p>
			</div>
            <div style="padding-bottom : 10px">
			<div >
			<a  href="javascript:all(${invitation.id});" style="font-size: 12px;padding-top:10px">
			<span id="s1_${invitation.id}">全文</span>
			<span id="s2_${invitation.id}" style="display:none">隐藏</span>
			</a>
			<a  style="font-size: 12px;float:right;margin-left:10px"  href="javascript:reply(${invitation.id});"> 回复</a>
			<!-- <a  style="font-size: 12px;float:right;margin-left:10px"  href="#"> 点赞</a> -->
            </div>
			
		
		<div class="slider">
          <div class="slide-group">
           <c:forEach var="resource" items="${invitation.resources}">
	          <div class="slide">
	            <img src="${resource.url}" alt="Argo"  height="100">
	          </div>
	       </c:forEach>
        </div>
      </div>
	  
	  
			</div>
               

			<div class="topicList noPerImg">
            <ul class="replylist" id="replyList_${invitation.id}">
            <c:forEach var="reply" items="${invitation.replys}" end="2">
               <li id="p_429_3650_0" uid="${UID}" author="${reply.crUser}">
                <a href="javascript:;" class="sW fl"><span>${reply.crUser}:</span>${reply.introduce}</a>
               </li>
            </c:forEach>
            </ul>
            <p id="rCount_429" class="more" rcount="105"><a href="javascript:click(${invitation.id});" title="">更多</a>共${invitation.replyNum}条回复</p>
            </div>
         
        </li>
		 
       </c:forEach>
      </ul>

     <div style="padding: 14px 40px 60px 40px;">
     	<%
				HttpSession session = request.getSession(); 
			%>
       <input type="hidden" id="UID" name="UID" value="${UID}"/>
       <input type="hidden" id="memberId" name="memberId" value="<%=session.getAttribute("memberId") %>"/>
       <input type="hidden" id="login" name="login" value="<%=session.getAttribute("login") %>"/>
	   <button class="btn btn-block btn-primary" style="background-color: #f8f9fa;color:black;border: 1px solid #ccc;" onclick="more()">点击加载更多</button>
	 </div>
    </div>
<nav class="bar bar-tab">
    <a class="tab-item" href="javascript:add();">发话题</a>
    
</nav>
 	 <script type="text/javascript">
		var pn =1;
		function more(){
			pn = pn+1;
			var UID = $("#UID").val();
			$.ajax({
				url: '<%=request.getContextPath()%>/m/invitation/list?UID='+UID+'&pageNum='+pn, 
				type: 'GET',
				contentType: "application/json;charset=UTF-8",
				dataType: 'text',
				success: function(data){
					var parsedJson = $.parseJSON(data);
					jQuery.each(parsedJson, function(index, itemData) {
							var crtDate = itemData.crtDate.substring(0,10);
							var s = "<li class='table-view-cell media topicBox'><img class='media-object  pull-left'  width='38' height='38' "+
							"src='"+itemData.user.avatar+"' alt='头像'><div class='media-body'><span style='font-size: 16px;' >"+itemData.crUser+"</span>"+
							"<p style='color: #a0a0a0;display: block;font-size: 11px;'>"+crtDate+"</p></div><div class='detailCon'><p id='p1_"+itemData.id+"'><span style='font-size: 20px'>"+itemData.title+"</span>"+
							"<br><br>------------------------------------<br>"+itemData.introduce+"<br></p></div><div style='padding-bottom : 10px'>"+
							"<div ><a href='javascript:all("+itemData.id+");' style='font-size: 12px;padding-top:10px'><span id='s1_"+itemData.id+"'>全文</span><span id='s2_"+itemData.id+"' style='display:none'>隐藏</span></a>"+
							"<a style='font-size: 12px;float:right;margin-left:10px' href='javascript:reply("+itemData.id+");'> 回复</a></div><div class='slider'><div class='slide-group'>";
							var resources = itemData.resources;
							var replys = itemData.replys;
							for(var i=0;i<resources.length;i++){
								s=s+"<div class='slide'><img src='"+resources[i].url+"' alt='Argo' height='100'></div>";
							}
							s=s+"</div></div></div><div class='topicList noPerImg'><ul class='replylist' id='replyList_"+itemData.id+"'>";
							for(var i=0;i<replys.length;i++){
								if(i<3){
								s=s+"<li id='p_429_3650_0' uid='"+UID+"' author='"+replys[i].crUser+"'><a href='javascript:;' class='sW fl'>"+
								"<span>"+replys[i].crUser+":</span>"+replys[i].introduce+"</a></li>";
								}
							}
							s=s+"</ul><p id='rCount_429' class='more' rcount='105'><a href='javascript:click("+itemData.id+");' title=''>更多</a>共"+itemData.replyNum+"条回复</p></div></li>";
							 $("#table-view").append(s); 
					});
				}
			});
		}
		var ids = null;
		function click(id){
			if(ids!=id){
				ids=id;
				pn=1;
			}
			pn = pn+1;
			var UID = $("#UID").val();
			
			$.ajax({
				url: '<%=request.getContextPath()%>/m/invitation/replys?id='+id+'&pageNum='+pn, 
				type: 'GET',
				contentType: "application/json;charset=UTF-8",
				dataType: 'text',
				success: function(data){
					var parsedJson = $.parseJSON(data);
					jQuery.each(parsedJson, function(index, itemData) {
						var s = "<li id='p_429_3650_0' uid='"+UID+"' author='"+itemData.crUser+"'><a href='javascript:;' class='sW fl'>"+
						"<span>"+itemData.crUser+":</span>"+itemData.introduce+"</a></li>";
						var s1 = "#replyList_"+id;
						$(s1).append(s); 
					});
				}
			});
		}
		
		function reply(id){
			var uid = $("#UID").val();
			var url;
			    url = "<%=request.getContextPath()%>/m/invitation/"+id+"/create?uid="+uid;
			
		   	location.href = url;
		}
		function add(){
			var memberId = $("#memberId").val();
			var uid = $("#UID").val();
			var url;
			var login = $("#login").val();
			if(memberId==null||memberId=="null"||login=="false"||login=="null"){
				memberId = "";
			}
				url = "<%=request.getContextPath()%>/m/invitation/add?uid="+uid;
		   	location.href = url;
		}
		function go()
	    {
	    	var uid = $("#UID").val();
	    	var url = "<%=request.getContextPath()%>/m/index?uid="+uid;
	    	location.href = url;
	    }
		var i=0;
		var p;
		var s;
		var s1;
		function all(id){
			p1 = "p1_"+id;
			s1 = "s1_"+id;
			s2 = "s2_"+id;
			if(i==0){
				i=1;
				document.getElementById(p1).style.display='inline'; 
				document.getElementById(s1).style.display='none'; 
				document.getElementById(s2).style.display='inline'; 
			}else{
				i=0;
				document.getElementById(p1).style.display='';
				document.getElementById(s2).style.display='none'; 
				document.getElementById(s1).style.display='inline';
			}
			
		}
	</script>
  </body>
</html>
