<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page session="false"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no, minimal-ui">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
	<title>回复</title>
 	<link rel="stylesheet" href="${ctx}/static/ratchet/css/ratchet.min.css">
    <script src="${ctx}/static/ratchet/js/ratchet.min.js"></script>
	<script src="${ctx}/static/js/jquery.min.js" type="text/javascript"></script>
 <link rel="stylesheet" type="text/css" href="${ctx}/static/emotions/emoticon.css" />
<link rel="stylesheet" href="${ctx}/static/ratchet/css/reset.css">	
<link rel="stylesheet" href="${ctx}/static/jquery-validation/1.11.1/validate.css">
<script src="${ctx}/static/jquery-validation/1.11.1/jquery.validate.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validation/1.11.1/messages_bs_zh.js" type="text/javascript"></script>
<script src="${ctx}/static/emotions/jquery.emoticons.js" type="text/javascript"></script>
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



</style>
</head>
<body>
   <header class="bar bar-nav">
	 <a class="pull-left" href="javascript:click();">
            <span class="icon icon-w3c-list" style="font-size: 18px;">返回</span></a>
      <h1 class="title" >手机论坛</h1>
    </header>
<div class="comment"> 
 <div id="container" class="com_form" >
 	<form:form id="form" method="post" modelAttribute="invitationReply" cssClass="form-horizontal" action="${ctx}/m/invitation/save"  enctype="multipart/form-data">
		<form:hidden path="invitationId" value="${invitationId}"/>
		<input type="hidden" name="userId" value="${invitationReply.userId }"/> 
		 <textarea class="input" name="introduce" placeholder="请输入内容" id="msg"   ></textarea>
	 	 <div><p>
	 	 <button type="submit" class="sub_btn" style="width: auto;" id="submit">提交</button>
	 	 <span class="emotion"></span><a href="JavaScript:void(0)" id="message_face">表情</a></p></div>  
	</form:form>
 		
	  
 </div>
</div>
	<script type="text/javascript">
            //放新浪微博表情
            $("#message_face").jqfaceedit({txtAreaObj:$("#msg"),containerObj:$('#container'),top:25,left:-27});
            function click()
    	    {
    	    	history.go(-1);
    	    }
            $(document).ready(function(){
    		    $("#submit").click(function(){
    				$("#form").validate({
    					rules:{
    						introduce:{
    							required:true
    						}
    					},messages:{
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
        </script>
</body>
  


</html>




  
  