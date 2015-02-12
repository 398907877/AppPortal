<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
<title>恭喜你中奖啦！</title>
<style type="text/css" >
	body,div,dl,dt,dd,ul,ol,li,h1,h2,h3,h4,h5,h6,pre,form,fieldset,input,textarea,p,blockquote,th,td {padding: 0;margin: 0;}
	ul li{ list-style:none;}
	a{ text-decoration:none; color:#0088CC;}
	table {border-collapse: collapse;border-spacing: 0;}
	.container{ width:100%; overflow:hidden; margin:0 auto ; z-index:-2; padding:40px 0 100px; position:relative;}
	.container h1{ font-size:35px; color:#FFc00e; font-family:Arial, Helvetica, sans-serif; padding:10px 0;}
	.container p{font-size:15px; color:#FFc00e; font-family:"微软雅黑"; line-height:26px;}
	input{border:0px solid black;-webkit-border-radius:20px; -moz-border-radius:20px; -khtml-border-radius:20px; border-radius:20px;padding-left:15px;padding-right:15px;height: 50;}
	button{border:0px solid black;-webkit-border-radius:15px; -moz-border-radius:15px; -khtml-border-radius:15px; border-radius:15px;height: 50}
	span{color:#ffffff}
</style>
<script src="${ctx}/static/js/jquery.min.js" type="text/javascript"></script>
<script type="text/javascript" src="<c:url value="${ctx}/static/js/jquery.validate.js" />"></script>
<script type="text/javascript" src="<c:url value="${ctx}/static/js/NativeBridge.js" />"></script>
</head>
<body >
	<div class="container" style="background-color: #ec2638;">
		<div style="padding:0 10px;" >
		  	<h1 style="text-align:center">恭喜您中奖啦！</h1>
		  	<p >尊敬的用户：</p>
		  	<p><span style="margin-left: 26px">&nbsp</span>恭喜您在本次抽奖活动中获得<span>${prizeName}。</span>我们将在活动结束后的<span>10个工作日内</span>统一发放奖品。
		  	      请务必留下您的<span>真实姓名和联系方式，</span>并前往<span>个人中心完善您的具体地址，</span>我们将以此次填写结果来发放奖品，如
		  	   <span>未填写对应信息,将视为自动弃权;</span>若因用户个人原因造成奖品无法正常发放，额外产生的邮费将由用户自行承担。
		  	</p>	
	  </div> 
	<br>
	 <form:form modelAttribute="tenancyUser" method="post" id="form" enctype="multipart/form-data" cssClass="form-horizontal"  action="${ctx}/m/ernies/memberInfoSave">
		<input type="hidden" value="${memberId}" id="id" name="id"/>
		<input type="hidden" value="${ernieId}" name="ernieId"/>
		<div class="control-group" style="text-align:center">
			<div class="controls" >
			<label for="admin.memberId" class="control-label" style="color:#FFc00e;">姓名</label>
				<form:input style="width:80%" path="name" name="name" id="name" placeholder="请输入您的真实姓名(必填项)"/>
			</div>
		</div>
		<br>
		<div class="control-group" style="text-align:center">
			<div class="controls">
			<label for="admin.memberId" class="control-label" style="color:#FFc00e;">手机</label>
				<form:input style="width:80%" path="mobile" name="mobile" id="mobile"  placeholder="请输入您的手机号码(必填项)"/>
			</div>
		</div >
		<br>
		<div style="text-align:center">
			<button  type="submit" style="background: #FFc00e;width: 125px;" class="btn btn-primary" id="submit"><span style="color:#ec2638;font-size:30px;font-family:微软雅黑" >确定</span></button>
		</div>
		
	</form:form>
	<%
				HttpSession session = request.getSession(); 
			%>
	   <input type="hidden" id="memberId" name="memberId" value="<%=session.getAttribute("memberId") %>"/>
       <input type="hidden" id="login" name="login" value="<%=session.getAttribute("login") %>"/>
       <input type="hidden" id="uid" name="uid" value="${uid}"/>
	</div>
	<script type="text/javascript">
	
	$(function(){
		 var login = $("#login").val();
		 var uid = $("#uid").val();
		 var memberId = $("#memberId").val();
		 var id = $("#id").val();
		 if(login=="false"||login=="null"||id!=memberId){
				alert("您尚未登陆哦！请先登陆。");
				url = "<%=request.getContextPath()%>/m/login?uid="+uid
			   	location.href = url;
		 }
	})
	
	/* 表单非空验证 */
	$("#form").validate({
		rules:{
			name:{
				required:true
			},
			mobile:{
				required:true
			}
		},errorPlacement: function(error, element) {
			error.insertAfter(element).attr("style","color:yellow;position: relative;left:20px;"); 
			$('<br/>').insertAfter(element);
		} ,
		messages:{
			name:{
				required:"真实姓名不能为空",
			},
			mobile:{
				required:"电话号码不能为空"
			}
		}
	});
	$("#submit").click(function(){
		var name = $("#name").val();
		var mobile = $("#mobile").val();
		if(name==null || name=="" || mobile==null || mobile==""){
			alert("您尚未填写完整！");
		}else{
			alert("如需更新个人信息，请到个人中心进行修改。");
		}
	});
	</script>	
</body>
</html>
