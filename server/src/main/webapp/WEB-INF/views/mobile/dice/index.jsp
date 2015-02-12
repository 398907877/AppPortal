<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="huake" uri="/huake"%>
<%@ page session="false"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title><huake:getErine id="${ernie.id}" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, maximum-scale=1.0, user-scalable=no" />
<script src="${ctx}/static/js/jquery.min.js" type="text/javascript"></script>
<script type="text/javascript" src="<c:url value="${ctx}/static/js/MSClass.js" />"></script>
<script type="text/javascript" src="<c:url value="${ctx}/static/js/NativeBridge.js" />"></script>
<script type="text/javascript" src="<c:url value="${ctx}/static/ratchet/js/ratchet.js" />"></script>
<script src="http://mat1.gtimg.com/app/openjs/openjs.js#autoboot=no&debug=no"></script>
<script src=" http://tjs.sjs.sinajs.cn/open/api/js/wb.js" type="text/javascript" charset="utf-8"></script>
<style type="text/css">
body,div,dl,dt,dd,ul,ol,li,h1,h2,h3,h4,h5,h6,pre,form,fieldset,input,textarea,p,blockquote,th,td {padding: 0;margin: 0;}
body {font-family: '微软雅黑','黑体','宋体', Verdana, sans-serif;position:static;}
table {border-collapse: collapse;border-spacing: 0;}
img{ display:block; border:none;} a{ text-decoration:none;}
ul li{ list-style:none;}
.fl{ float:left;} .fr{ float:right;}.clear{ clear:both;}
.container{ width:100%;height:auto; overflow:hidden; background:url(<c:url value="${ctx}/static/img/dice/bg_02.jpg" />) repeat-y; background-size:100% 100%;max-width:500px; margin:0 auto;}
.top img,.bottom img{ width:100%; }
.wAuto{ margin:0 auto;}
.bobing{ width:100%; position:relative}
.bobing h1{ text-align:center; color:#de2127; font-size:18px; font-weight:normal; margin-bottom:5px;}
.color_w{color:white;margin-left:35%}
.gif_box{position: absolute;z-index:1; width:100%; display:none;}
.gif{ width:250px; height:220px; margin:0 auto;}
.gif img{width:250px; height:220px;}
.bowl_pic{ width:250px; height:220px;}
.bowl{ position:relative;}
.sz-1,.sz-2,.sz-3,.sz-4,.sz-5,.sz-6{ width:35px; height:35px;}
.sz-1{ position:absolute; top:50%; left:32%;}
.sz-2{ position:absolute; top:50%; left:44%;}
.sz-3{ position:absolute; top:46%; left:55%;}
.sz-4{ position:absolute; top:63%; left:38%;}
.sz-5{ position:absolute; top:62%; left:50%;}
.sz-6{ position:absolute; top:35%; left:47%;}
.btn01{ width:100px; height:30px; line-height:30px; text-align:center; background:#980203;display:block; border-radius:5px; color:#FFFFFF; margin:10px auto; border:0;}
.bb_gz,.wbfx{ line-height:26px; height:26px; width:300px;margin:10px auto; text-align:center; }
.bb_gz span,.wbfx span{ color:#b06d0e; float:leftt; font-size:14px;}
.bb_gz strong,.wbfx strong{ font-size:16px;}
.btn02{ padding:0 5px; height:26px;line-height:26px; text-align:center; display:block; border-radius:3px; float:right;color:#FFFFFF;background:#b06d0e; margin-right:10px;}
.bb_tit{height:40px; line-height:28px; padding-left:5%; background:url(<c:url value="${ctx}/static/img/dice/bg_04.jpg" />) no-repeat;background-size:100% 100%; color:#fff6cb; margin:5px 0 0 0;}
.hj_name{  width:100%;height:auto;}
.table{ padding:0 5%;}
.table_h{ width:100%; height:auto; overflow:hidden;}
.hj_name tr{ width:100%; display:inline-table;}
.hj_name td{text-align:center; font-size:12px; border-bottom:1px dashed #b06d0e;height:29px;width:33.3%}
.hj_name td span{ color:#b06d0e;}
.hj_name td a{ color:#b12116;}	/*兑奖样式*/
.hj_name td a.used{ color:#666666;}
.page { margin:10px 0;}
.jpsm { width:100%;text-align:center;}
.jpsm th,td{ height:20px; }
.jpsm span{ font-size:14px; color:#B06D0E;}
.jpsm th{ font-size:16px; color:red; font-weight:normal;border:1px solid #b12116;}
.jpsm td{ font-size:12px;border:1px solid #b12116;}
</style>
</head>
<body>
<header class="bar bar-nav" style="background:#CD0000">	
    	    <a class="icon icon-left-nav pull-left" style="font-size:17px;color: white;padding-top:15px" href="javascript:go();">返回</a>
      <h1 class="title" style="color:white;"><huake:getErine id="${ernie.id}" /></h1>
    </header>
<div class="container" style="margin-top: 40px">
  <div class="top"> <img src="<c:url value="${ctx}/static/img/dice/bg_01.jpg" />" /> </div>
  <div class="bobing">
    <h1 id="showAward">更多大奖等你来拿哦！</h1>
    <div class="gif_box">
      <div class="gif"><img src="<c:url value="${ctx}/static/img/dice/blow.gif" />" /></div>
    </div>
    <div class="bowl"> <img src="<c:url value="${ctx}/static/img/dice/blow.jpg" />" class="wAuto bowl_pic" /> 
    <img src="<c:url value="${ctx}/static/img/dice/sz_1.png" />" class="sz-1" /> 
    <img src="<c:url value="${ctx}/static/img/dice/sz_2.png" />" class="sz-2" /> 
    <img src="<c:url value="${ctx}/static/img/dice/sz_3.png" />" class="sz-3" /> 
    <img src="<c:url value="${ctx}/static/img/dice/sz_4.png" />" class="sz-4" /> 
    <img src="<c:url value="${ctx}/static/img/dice/sz_5.png" />" class="sz-5" /> 
    <img src="<c:url value="${ctx}/static/img/dice/sz_6.png" />" class="sz-6" />
	</div>
  </div>
  <button class="btn01" id="start">摇一摇</button>
  <div class="bb_gz"><!-- <span>您还有<strong> ${times} </strong>次博饼机会</span> -->
	<div class="clear"></div>
	</div>
	<div class="bb_tit">
规则说明
</div>
<div style="margin-left: 20px;padding-right:15px;">
<p>${ernie.content}</p>

</div>
<div class="bb_tit">
获奖记录
<a id="lookAward" onclick="lookAward();" href="javascript:void(0);" class="color_w">查看我的奖品</a>
</div>
<div class="hj_name">
  <div class="clear"></div>
  <div class="table_h">
  <table width="100%">
	   <tr class="h_tit">
		  <td style="padding-left: 20px">用户名</td>
		  <td style="padding-right: 20px">获得奖品</td>
	   </tr>
  </table>
  </div>
	<div class="table" id="marquee" style="height:84px;">
		<table width="100%" id="table" style="display: none;height:84px;margin-top:60px">
			 <c:forEach items="${ernies.content}" var="ernieItem">
				<tr style="display:inline-table;">
					<td><huake:getMember id="${ernieItem.memberId}" /></td>
					<td><huake:getErineItems id="${ernieItem.winning}" /></td>
				</tr>
			</c:forEach>
		 </table>
	</div>
  </div>
<div class="bb_tit">
奖品说明
</div>  
<div class="jpsm">
<div class="table">
<table width="100%">
	<tr><th width="20%">奖项</th><th width="65%">奖品</th><th width="15%">数量</th></tr>
	<c:forEach var="item" items="${ernieItem.content}"> 
		<tr><td width="20%"><span>
		<c:if test="${item.bobing != null }">
							<c:choose>
								<c:when test="${item.bobing eq 'yixiu' }">
									一秀
								</c:when>
								<c:when test="${item.bobing eq 'erju' }">
									二举
								</c:when>
								<c:when test="${item.bobing eq 'sanhong' }">
									三红
								</c:when>
								<c:when test="${item.bobing eq 'sijin' }">
									四进
								</c:when>
								<c:when test="${item.bobing eq 'duitang' }">
									对堂
								</c:when>
								<c:when test="${item.bobing eq 'zhuangyuan' }">
									状元
								</c:when>
								<c:otherwise>
									
								</c:otherwise>
							</c:choose>
						</c:if>
		</span></td><td width="65%">${item.name}</td><td width="10%">${item.num}</td></tr>
	</c:forEach> 

</table>
<%
				HttpSession session = request.getSession(); 
			%>
       <input type="hidden" id="memberId" name="memberId" value="<%=session.getAttribute("memberId") %>"/>
       <input type="hidden" id="login" name="login" value="<%=session.getAttribute("login") %>"/>
	   <input type="hidden" id="uid" name="uid" value="${uid}"/>
	   <input type="hidden" id="ernieId" name="ernieId" value="${ernieId}"/>
	   <input type="hidden" id="count" name="count" value="${count}"/>
</div>
</div>
<div class="bottom" ><img src="<c:url value="${ctx}/static/img/dice/bg_06.jpg" />"/></div>	
</div>
<script type="text/javascript">

	var count = $("#count").val();
	if(count>2){
		/*********文字翻屏滚动***************/
		new Marquee(
				{
					MSClassID :  "marquee",
					ContentID :  "table",
					Direction : "top",
					Step : [0.5,20],
					Timer : 20,
					Height : 90,
					DelayTime : 3000,
					WaitTime : 3000,
					ScrollStep: 60,
					AutoStart : 1
				}); 
		$("#table").css("display", "inline");
	}else if(count==2){
		/*********文字翻屏滚动***************/
		new Marquee(
				{
					MSClassID :  "marquee",
					ContentID :  "table",
					Direction : "top",
					Step : [0.5,20],
					Timer : 20,
					Height : 60,
					DelayTime : 3000,
					WaitTime : 3000,
					ScrollStep: 60,
					AutoStart : 1
				}); 
		$("#table").css("display", "inline");
	}else if(count==1){
		/*********文字翻屏滚动***************/
		new Marquee(
				{
					MSClassID :  "marquee",
					ContentID :  "table",
					Direction : "top",
					Step : [0.5,20],
					Timer : 20,
					Height : 30,
					DelayTime : 3000,
					WaitTime : 3000,
					ScrollStep: 60,
					AutoStart : 1
				}); 
		$("#table").css("display", "inline");
	}
	
	
	function go()
	   {
	   	var uid = $("#uid").val();
	   	var url = "<%=request.getContextPath()%>/m/ernies?uid="+uid;
	   	location.href = url;
	   }

	function lookAward(){
        var ernieId = $("#ernieId").val();
        window.location.href = "<%=request.getContextPath()%>/m/ernies/award/"+ernieId;
	};
	
   var status = '${status}';
   var click = false;
   var obj;
	$(function(){
		
		$('#start').click(function(){
			 if(status!=null && status!=""){
				 //密码验证
				if(status=="01"){
					alert("您未通过验证");
				}else{
					alert(status);
				}
				click=true;
			} 
			
			if(click){
				return;
			}
			click=true;
			$(".gif_box").show();
			var uid = $("#uid").val();
	        var ernieId = $("#ernieId").val();
	        var url;
	        $.ajax({
						url: "<%=request.getContextPath()%>/m/ernies/doErnie/"+ernieId+"?uid="+uid, 
						type: 'GET',
						contentType: "application/json;charset=UTF-8",
						async:false,
						success: function(bobingDate){
							if(bobingDate.login=='false'){
								//alert("您尚未登陆哦！请先登陆。");
								url = "<%=request.getContextPath()%>/m/login?uid="+uid+"&url=/m/ernies/choujiang/bobing/"+ernieId+"?uid="+uid+"%26memberId=";
								location.href = url;
							}else{
							obj=bobingDate;
							prize_code =bobingDate.ernieItemId;
							//alert(ernieId);
					        //alert(uid);
					        //alert(memberId);
					        //alert(bobingDate.success);
							//alert(bobingDate.message);
							//alert(bobingDate.ernieItemId);
							if(obj.dice==null||obj.dice==""){
								alert(bobingDate.message);
								window.location.href ='<%=request.getContextPath()%>/m/ernies/bobing/'+ernieId+'/'+uid;
							}else {
								setTimeout("getResult()", 3000);
							}
							
							}
						},error:function(xhr,textStatus, errorThrown){
							alert('网络异常，请稍后再试');
						}
					});
		});
		
	});
	//获取摇骰子结果
	function getResult(){
			$("#showAward").text(obj.message);
		var points=obj.dice;
		//结束动画
		$(".gif_box").hide();
		$(".bowl").empty();
		$(".bowl").append("<img src='${ctx}/static/img/dice/blow.jpg'  class='wAuto bowl_pic' /> ");
		 $.each(points, function(i,item){      
			$(".bowl").append("<img src='${ctx}/static/img/dice/sz_"+item+".png' class='sz-"+(i+1)+"'>");
			
			
		});
		 if(prize_code==null||prize_code==""||obj.message=="很遗憾，您没有摇中"){
			 setTimeout("action()",2000);
		 }else{
			 setTimeout("memberInfo()",2000);
		 }
	}
	function memberInfo(){
		var memberId =   $("#memberId").val();
		window.location.href ='<%=request.getContextPath()%>/m/ernies/bobing/memberInfo/'+memberId+'/'+prize_code;
	}
	function action(){
		var uid = $("#uid").val();
        var ernieId = $("#ernieId").val();
		window.location.href ='<%=request.getContextPath()%>/m/ernies/choujiang/bobing/'+ernieId+'?uid='+uid;
	}
	
</script>
</body>

</html>
