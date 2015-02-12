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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, maximum-scale=1.0, user-scalable=no" />
<script src="${ctx}/static/js/jquery.min.js" type="text/javascript"></script>
<script type="text/javascript" src="<c:url value="${ctx}/static/js/MSClass.js" />"></script>
<script type="text/javascript" src="<c:url value="${ctx}/static/js/NativeBridge.js" />"></script>
<script type="text/javascript" src="<c:url value="${ctx}/static/ratchet/js/ratchet.js" />"></script>
<title>${ernie.title}</title>
<style type="text/css">
body {font-family: '微软雅黑','黑体','宋体', Verdana, sans-serif;position:static;}
body,div,dl,dt,dd,ul,ol,li,h1,h2,h3,h4,h5,h6,pre,form,fieldset,input,textarea,p,blockquote,th,td {padding: 0;margin: 0; }
table {border-collapse: collapse;border-spacing: 0;overflow:auto;} 
img{ display:block; border:none;}
ul li{ list-style:none;} a{ text-decoration:none; cursor:pointer;}
.container{ width:100%;height:auto; overflow:hidden;background-size:100% 100%;max-width:500px; margin:0 auto;}
.fl{ float:left;} .fr{ float:right;}.clear{ clear:both;}.pr10{ padding-right:10px;}
.user_cj{ width:100%; height:30px; background:#f9bf37; color:#d24d02; font-size:16px;}
.user_cj strong{ font-weight:normal; padding-top:3px; padding-left:15px; float:left;}
.user_cj span {float:left;padding-top:5px; padding-left:15px;padding-right:15px;}
.user_cj dl{ text-align:center; padding-right:15px;padding-top:3px;}
.user_cj dl  a{ text-decoration:underline;}
.sj_cj{ width:100%; height:auto; position:relative;}
.sj_cj img{ width:100%;}
.cj_active{ display:block; position:absolute; top:33.3%; left:33.3%; width:33.3%; height:33%;}
.hjxx{ padding:5px 10px; background-color: #ec2638;}
.hjxx ul li{ width:33%; float:left; height:30px; line-height:30px; font-size:14px;}
.hjxx ul li img{ float:right;}
.hjxx tr{width:100%;}
.hjxx td{ width:33.3%;}
.color_1{ color:#ffc00e;font-size:18px;}
.color_2{ color:#51a61f;}
.color_3{ color:#ffc00e;font-size:14px;}
.color_4{ color:#ec2638;font-size:18px;}
.h_tit{ background:#ffc00e; border-bottom:1px solid #fff27f; font-size:14px; color:#2f2f2f;}
/* .table_h{border:2px solid #ffc00e; width:100%; height:auto;  font-size:12px; color:#464646; text-align:center;} */
.table{border:2px solid #ffc00e; width:100%; height:auto; font-size:12px; color:#464646;  text-align:center;background:url(<c:url value="/static/img/tb_bg1.png"/>);}
.pt15{ padding-top:15px;font-size:18px;}
.fs12{ padding-top:15px;font-size:14px;}
.img_xqDiv{background-color: #ec2638;padding-bottom: 5px;}
.img_xq{background-color: #ec2638;width:30%;position:relative;left:65%;}
.bar{background-color:#ec2638;}
.color_w{color:white;}
.table_h{border:2px solid #ffc00e; width:100%; height:auto; font-size:12px; color:#464646;  text-align:center;background:url(<c:url value="/static/img/t_y.jpg"/>);}
.sd div{margin-top:-1px;margin-bottom:0;}
.sd div img{width:105px;height:105px;float:left;}
.so-year-playbox-box{width:110px;height:105px;border:3px #912CEE solid;top:-1px;left:-1px}
</style>
</head>
<body>
<header class="bar bar-nav" style="background: #ec2638">	
    	    <a class="icon icon-left-nav pull-left" style="font-size:17px;color: white;padding-top:15px" href="javascript:go();">返回</a>
      <h1 class="title" style="color:white;">${ernie.title}</h1>
    </header>
    <div class="content" style="background-color: #ec2638;">
        	<div class="table-view-cell" style="padding-left:10px;padding-right:15px;line-height:30px;border-bottom:0px">
        	    <div>
	        	<h4>一、天天抽大奖，快来参加吧！</h4>
	        	</div>
	        	 <div>
	        	<span >活动时间：</span>
	            <span class="showtxt">
	            ${fn:substring(ernie.startDate,0,10)}——${fn:substring(ernie.endDate,0,10)}
	            </span>
	             </div>
        	</div>
<div class="sd" style="width:100%"> 
	<table style="margin:0 auto" border="0" cellpadding="0" cellspacing="1">
	<tr>
		<%int i1=0; 
		  int count1 = 0;%>
	  <c:forEach var="ernies" items="${ernie1.content}" begin="0" end="2" varStatus="status">
	  	<td>
	  	<% i1=i1+1;
	  	   count1=count1+1;%>
	  	<img src="${ernies.image}" class="${ernies.id}" width="110" height="105" id="img<%=i1%>"/>
	  	</td>
	  </c:forEach> 
	  <% 
	  if(count1<3){
		  for(int j=0;j<3-count1;j++){
			  i1=i1+1;
			  %>
	<td>
	<img src="${ctx }/static/img/lottery/44.png" width="110" height="105" id="img<%=i1%>"/>
	</td>
			  <% 
		  }
	  }
	  %>
	</tr>
	<tr>
		<%int i2=8; 
		  int count2 = 0;%>
		<c:forEach var="ernies" items="${ernie1.content}" begin="6" end="6" varStatus="status">
		<td>
	  	<% count2=count2+1;%>
	  	<img src="${ernies.image}" class="${ernies.id}" width="110" height="105" id="img<%=i2%>"/>
	  	</td>
	  </c:forEach>
		<% 
	  if(count2<1){
		  for(int j=0;j<1-count2;j++){
			  %>
	<td>
	<img src="${ctx }/static/img/lottery/44.png" width="110" height="105" id="img<%=i2%>"/>
	</td>
			  <% 
		  }
	  }
	  %>
		<td><img class="choujiang" src="${ctx }/static/img/lottery/1010.png" width="110" height="105"/></td>
		<td><img src="${ctx }/static/img/lottery/44.png" width="110" height="105" id="img4"/></td>
	</tr>
	<tr>
		<%int i=8; 
		  int count = 0;%>
		<c:forEach var="ernies" items="${ernie1.content}" begin="3" end="5" varStatus="status">
		<td>
	  	<% i=i-1;
	  	   count=count+1;%>
	  	<img src="${ernies.image}" class="${ernies.id}" width="110" height="105" id="img<%=i%>"/>
	  	</td>
	  </c:forEach> 
	  <% 
	  if(count<3){
		  for(int j=0;j<3-count;j++){
			  i=i-1;
			  %>
	<td>
	<img src="${ctx }/static/img/lottery/44.png" width="110" height="105" id="img<%=i%>"/>
	</td>
			  <% 
		  }
	  }
	  %>
	</tr>
	</table>
</div>
<div class="table-view-cell" style="padding-left:10px;padding-right:15px;line-height:30px;border-bottom:0px">
        	    <div style="margin-top: 10px">
	        	<h4>二、活动规则</h4>
	        	</div>
	        	 <div style="margin-top: 10px">
	           	<span>${ernie.content}</span>
	             </div>
        	</div>
 <div class="hjxx">
	<ul>
	    <li class="color_1">最新获奖信息</li>
	    <li style="text-align:center;"><a id="lookAward" onclick="lookAward();" href="javascript:void(0);" class="color_w">查看我的奖品</a></li>
	</ul>
	<div class="clear"></div>
	<div class="table_h">
		<table width="100%">
	        <tr class="h_tit">
		        <td>用户名</td>
		        <td>获得奖品</td>
	        </tr>
	    </table>
	</div>
	<div class="table" id="marquee" style="height:90px;">
	    <table width="100%" id="table" style="display: none;margin-top:60px">
        	<c:forEach items="${ernies.content}" var="ernieItem">
				<tr style="display:inline-table;height: 30px;">
					<td><huake:getMember id="${ernieItem.memberId}" /></td>
					<td><huake:getErineItems id="${ernieItem.winning}" /></td>
				</tr>
			</c:forEach>
	    </table>
	</div>
</div>
<%
				HttpSession session = request.getSession(); 
			%>
       <input type="hidden" id="memberId" name="memberId" value="<%=session.getAttribute("memberId") %>"/>
       <input type="hidden" id="login" name="login" value="<%=session.getAttribute("login") %>"/>
	   <input type="hidden" id="uid" name="uid" value="${uid}"/>
	   <input type="hidden" id="ernieId" name="ernieId" value="${ernie.id}"/>
	   <input type="hidden" id="count" name="count" value="${count}"/>
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
	
	$(function(){
        var click = 1; //控制在一次抽奖结束前下次点击无效
        var index; //控制的抽奖结束后效果的还原
        var not_message;
        var prize_code;
        var math=0;
        var stopPicNum = 0;
        $(".choujiang").click(function () {
            //进行判断是否抽奖完毕
            if (click != 1) {
                return;
            }
            click++;
            
            var picNo = 1; //图片轮播的地方
            var circleNum = 1; //控制转了多少圈才停下来的变量
            var uid = $("#uid").val();
            var ernieId = $("#ernieId").val();
            var memberId =   $("#memberId").val();
            var url;
            $.ajax({
				url: "<%=request.getContextPath()%>/m/ernies/doErnie/"+ernieId+"?uid="+uid, 
				type: 'GET',
				async:false,
				contentType: "application/json;charset=UTF-8",
				success: function(data){
					if(data.login=='false'){
						alert("您尚未登陆哦！请先登陆。");
						url = "<%=request.getContextPath()%>/m/login?uid="+uid+"&url=/m/ernies/choujiang/"+ernieId+"?uid="+uid+"%26memberId=";
					   	location.href = url;
					}else{
					if(data.message=='用户未中奖！'){
						math=4;
						stopPicNum=4;
						not_message= "亲，奖品可能藏在下一次机会里哦！";
					}else{
						math=2;
						//alert("中奖ID"+math);
						stopPicNum=4;
						prize_code =data.ErnieItem;
						not_message = data.message;
						var img1 = document.getElementById("img1").className;
						var img2 = document.getElementById("img2").className;
						var img3 = document.getElementById("img3").className;
						var img5 = document.getElementById("img5").className;
						var img6 = document.getElementById("img6").className;
						var img7 = document.getElementById("img7").className;
						var img8 = document.getElementById("img8").className;
						if(prize_code==img1){
                    		stopPicNum=1;
                    	}else if(prize_code==img2){
                    		stopPicNum=2;
                    	}else if(prize_code==img3){
                    		stopPicNum=3;
                    	}else if(prize_code==img5){
                    		stopPicNum=5;
                    	}else if(prize_code==img6){
                    		stopPicNum=6;
                    	}else if(prize_code==img7){
                    		stopPicNum=7;
                    	}else if(prize_code==img8){
                    		stopPicNum=8;
                    	}
					}
				}
					},error:function(data){
					alert("出错了");
				}
			}); 
          	//抽奖完后将图片还原到初始状态  
            if (index != 1) {
         	   //alert(index);
                $("#img1").addClass("so-year-playbox-box");
                $("#img" + index).removeClass("so-year-playbox-box");
            }
            if(math==0){
          	   click=1;
          	  $("#img1").removeClass("so-year-playbox-box");
          	   return false;
             }
            var time = setInterval(function () {
         	   picNo++;
                //控制图片轮播的范围（8张图片）
                if (picNo > 8) {
             	   picNo = 1;
             	   circleNum++;
                };
                index = picNo;
                //此处为图片的滚动
                if (picNo == 1) {
                    $("#img8").removeClass("so-year-playbox-box");
                }
                $("#img" + picNo).addClass("so-year-playbox-box");
                $("#img" + (picNo * 1 - 1)).removeClass("so-year-playbox-box");
                //当滚动2圈后开始出奖
                if(math!=0){
	                if (circleNum == 2) {
	                     //概率
	                    if (math == 4) {
	                    //在第几个图片停下来（下面以此类推）
	                        if (picNo == 5) {
	                            clearInterval(time);
	                            click = 1;
	                            math=0;
	                            alert(not_message);
	                            window.location.href ="<%=request.getContextPath()%>/m/ernies/choujiang/"+ernieId+"?uid="+uid;
	                        }
	                    }
	                    if (math == 2) {
		                      if (picNo == stopPicNum+1) {
		                               clearInterval(time);
		                               click = 1;
		                               math=0;
		                               alert(not_message);
		                               window.location.href ="<%=request.getContextPath()%>/m/ernies/memberInfo/"+memberId+"/"+prize_code;
		                           }
		                       }
	                }
                } else{
                	window.location.href ="<%=request.getContextPath()%>/m/ernies/"+ernieId+"/"+uid;
                }
            }, 200)

        });
	});
	</script>
</body>
</html>