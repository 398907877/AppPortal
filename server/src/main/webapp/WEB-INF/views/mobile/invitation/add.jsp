<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page session="false"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!doctype html>
<html lang="en">
<head>
<meta charset="UTF-8">
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no, minimal-ui">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
	<title>发表话题</title>
<script src="${ctx}/static/js/jquery.min.js" type="text/javascript"></script>
<script src="${ctx}/static/emotions/jquery.emoticons.js" type="text/javascript"></script>
 <link rel="stylesheet" type="text/css" href="${ctx}/static/emotions/emoticon.css" />
<link rel="stylesheet" href="${ctx}/static/emotions/css/reset.css">	
<link rel="stylesheet" href="${ctx}/static/ratchet/css/ratchet.min.css">

<link rel="stylesheet" href="${ctx}/static/jquery-validation/1.11.1/validate.css">
<script src="${ctx}/static/ratchet/js/ratchet.min.js"></script>
<script src="${ctx}/static/jquery-validation/1.11.1/jquery.validate.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validation/1.11.1/messages_bs_zh.js" type="text/javascript"></script>
<style>
.comment{width:90%; margin:20px auto; margin-bottom:5px;margin-top:50px; position:relative; background:#fff; padding:20px 20px 20px; border:1px solid #DDD; border-radius:5px;}
.comment h3{height:28px; line-height:28px}
.com_form{width:100%; position:relative}
.input{width:99%; height:60px; border:1px solid #ccc}
.com_form p{height:28px; line-height:28px; position:relative; margin-top:10px;}
span.emotion{width:42px; height:20px; background:url(http://www.16code.com/cache/demos/user-say/img/icon.gif) no-repeat 2px 2px; padding-left:20px; cursor:pointer}
span.emotion:hover{background-position:2px -28px}
.qqFace{margin-top:4px;background:#fff;padding:2px;border:1px #dfe6f6 solid;}
.qqFace table td{padding:0px;}
.qqFace table td img{cursor:pointer;border:1px #fff solid;}
.qqFace table td img:hover{border:1px #0066cc solid;}

.sub_btn {
	position:absolute; right:0px; top:0;
	display: inline-block;
	zoom: 1; /* zoom and *display = ie7 hack for display:inline-block */
	*display: inline;
	vertical-align: baseline;
	margin: 0 2px;
	outline: none;
	cursor: pointer;
	text-align: center;
	font: 14px/100% Arial, Helvetica, sans-serif;
	padding: .5em 2em .55em;
	text-shadow: 0 1px 1px rgba(0,0,0,.6);
	-webkit-border-radius: 3px; 
	-moz-border-radius: 3px;
	border-radius: 3px;
	-webkit-box-shadow: 0 1px 2px rgba(0,0,0,.2);
	-moz-box-shadow: 0 1px 2px rgba(0,0,0,.2);
	box-shadow: 0 1px 2px rgba(0,0,0,.2);
	color: #e8f0de;
	border: solid 1px #538312;
	background: #64991e;
	background: -webkit-gradient(linear, left top, left bottom, from(#7db72f), to(#4e7d0e));
	background: -moz-linear-gradient(top,  #7db72f,  #4e7d0e);
	filter:  progid:DXImageTransform.Microsoft.gradient(startColorstr='#7db72f', endColorstr='#4e7d0e');
}
.sub_btn:hover {
	background: #538018;
	background: -webkit-gradient(linear, left top, left bottom, from(#6b9d28), to(#436b0c));
	background: -moz-linear-gradient(top,  #6b9d28,  #436b0c);
	filter:  progid:DXImageTransform.Microsoft.gradient(startColorstr='#6b9d28', endColorstr='#436b0c');
}
.tipLayer {
    background-color: #efefef;
    border-top: 1px solid #e2e2e2;
    color: #333;
    position: relative;
}

.photoList {
    margin: 15px 10px;
    padding-top: 5px;
}
.photoList .on {
    background: url("${ctx}/static/emoticons/images/sprBg.png") no-repeat scroll -244px -4px #d9d9d9;
    cursor: pointer;
}
.photoList .on, .maskLay {
    height: 60px;
    width: 60px;
}
.photoList li {
    float: left;
    margin: 0 15px 15px 0;
    position: relative;
}


.textTip {
    clear: both;
    color: #afafaf;
    line-height: 21px;
    width: 100%;
}
</style>
</head>
<body>
   <header class="bar bar-nav">
	 <a class="pull-left" href="javascript:click();">
            <span class="icon icon-w3c-list" style="font-size: 18px;">返回</span></a>
      <h1 class="title" >手机论坛</h1>
    </header>
	<form:form id="form" method="post" modelAttribute="invitation" cssClass="form-horizontal" action="${ctx}/m/invitation/saveInv"  enctype="multipart/form-data">
		<div class="comment"> 
 			<div id="container" class="com_form" >
				<input type="hidden" name="userId" value="${invitation.userId }"/> 
				<input type="hidden" name="uid" value="${UID}"/> 
				<textarea class="input" style="height:40px" placeholder="请输入标题" name="title" id="title"   ></textarea>
				 <textarea class="input"  style="height:80px" placeholder="请输入内容" name="introduce" id="msg"   ></textarea>
			 	 <div><p>
			 	 <button type="submit" class="sub_btn" style="width: auto;" id="submit">提交</button>
			 	 <span class="emotion"></span><a href="JavaScript:void(0)" id="message_face">表情</a></p></div>  
			</div>
		</div>
		<div class="tipLayer" style="height:116px;width:100%">
            <div class="photoList">
            <ul id="photo">
                <li class="on" id="addPic1"  >
                <input class="on needsclick" name="fileInput" onclick="javascript:addFile('addPic1');" style="z-index:200;opacity:0;filter:alpha(opacity=0);-ms-filter:'alpha(opacity=0)';" id="uploadFile" accept="image/*" single="" type="file">
				</li>
				
				
            </ul>
            <p class="textTip">最多可上传8张图片</p>
            </div>
		</div>
		<div id="demo" >
	        
		</div>
	</form:form>
 <script type="text/javascript">
            //放新浪微博表情
            $("#message_face").jqfaceedit({txtAreaObj:$("#msg"),containerObj:$('#container'),top:25,left:-27});
		var i = 1;
		
		function addFile(id)
		{
			    var str = "<a href=\"javascript:del('addPic" + i +"')\" style='font-size:8px;float:right;'>删除</a>";
				$("#"+id).append(str);
			    i += 1; 
				$("#photo").append("<li class='on' id='addPic"+i+"' >"+
                "<input class='on needsclick'  onclick=\"javascript:addFile('addPic"+i+"');\" "+ 
				" style='z-index:200;opacity:0;filter:alpha(opacity=0);-ms-filter:'alpha(opacity=0)';' "+
				" id='uploadFile' accept='image/*' single='' type='file'></li>");	
	            
		}
		
	    function del(id)
		{
				$("#"+id).remove();
		}
			
	    
	    var x=document.getElementById("demo");
	    function getLocation()
	      {
	      if (navigator.geolocation)
	        {
	        navigator.geolocation.getCurrentPosition(showPosition);
	        }
	      else{x.innerHTML="Geolocation is not supported by this browser.";}
	      }
	    function showPosition(position)
	      {
	      x.innerHTML="Latitude: " + position.coords.latitude + 
	      "<br />Longitude: " + position.coords.longitude;	
	      }
	    function click()
	    {
	    	history.go(-1);
	    }
	    $(document).ready(function(){
		    $("#submit").click(function(){
				$("#form").validate({
					rules:{
						title:{
							required:true,
							maxlength:30
						},
						introduce:{
							required:true
						}
					},messages:{
						title:{
							required:"必须填写",
							maxlength:"超出30字符"
						},
						introduce:{
							required:"必须填写",
						}
					},submitHandler:function(form){
		   	 			form.submit();
		         	}
				});
			});
	    });
        </script>
</body>
  


</html>    