<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.huake.com/functions" prefix="function" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ page session="false"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
    <title>Bootstrap-定制的会员卡模块显示</title>
	<link href="${ctx}/static/bootstrap/additions/lightbox/bootstrap-lightbox.min.css" type="text/css" rel="stylesheet" />
	<link href="${ctx}/static/fancybox/jquery.fancybox-1.3.4.css" rel="stylesheet" /> 
	<link href="${ctx}/static/select2-3.4.6/select2.css" rel="stylesheet"/>	
	<script type="text/javascript" src="${ctx}/static/js/jquery-ui-1.8.21.custom.min.js"></script>
	<script type="text/javascript" src="${ctx}/static/fancybox/jquery.fancybox-1.3.4.js"></script>	
	<script type="text/javascript" src="${ctx}/static/fancybox/jquery.fancybox-1.3.4.pack.js"></script>	
    <script src="${ctx}/static/select2-3.4.6/select2.js"></script>
	<script src="${ctx}/static/bootstrap/additions/lightbox/bootstrap-lightbox.min.js" type="text/javascript"></script>
<style>
.div_background{position:relative;text-align: center; width: 400px; height: 180px; border: 1px solid darkred}
.div_logo{position:absolute;width:50px;height:50px;left:20px;top:20px}
.div_text1{position:absolute;width:240px;height:90px;left:70px;top:0px;padding-top:30px;padding-left:10px;color:#FFFFFF}
.div_text2{position:absolute;width:110px;height:90px;left:270px;top:0px;padding-top:30px;padding-left:20px;color:#FFFFFF}
.div_number{position:absolute;width:400px;height:90px;left:0px;top:90px;padding-top:50px;padding-left:30px}

.display_no{display:none}
.display_ok{display:block}
.add1{background-color:red}
.red{color:red;border:1px solid darkred}
</style>	
</head>
<body>
   <div class="container">
	<div class="row">
	    <div class="col-md-2">
			<%@ include file="nav.jsp"%>
		</div>
		<div class="col-md-10">
	        <div class="bs-callout bs-callout-info">
				<div>
					<h4>通过定制电子会员卡类型，供会员申请使用。</h4>
				</div>
			</div>
		    <c:if test="${not empty message}">
			  <div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
		    </c:if>
			<div class="panel panel-default">
			  <div class="panel-heading">查询会员卡</div>
			  <div class="panel-body">
			    <form id="queryForm" class="form-inline" role="form"  method="get" action="${ctx}/mgr/membercard/index">
			     <div class="form-group">
		               <label for="status">状态:</label>
					   <select name="search_LIKE_status" style="min-width:240px;" class="form-control" value="${param.search_LIKE_status}" placeholder="请选择会员有效性">
		                         <option value="">选择</option>
		                         <option value="1">有效</option>
		                         <option value="0">无效</option>
		                  </select>	                
					<input type="submit" class="btn btn-success" value="查询" />
				  </div>
				  <tags:sort />	
				</form>
				</div>
			</div>
			<div class="panel panel-default">
			  <div class="panel-heading">已定制的会员卡类型</div>
			  <div class="panel-body">
			  	<a href="<%=request.getContextPath()%>/mgr/membercard/add" class="btn btn-primary"><span class="glyphicon glyphicon-plus"></span>定制新卡</a>
			  </div>
			  <div class="row">
			  	<c:forEach items="${mcards.content}" var="mcard" begin="${s.index}" step="1" end="${s.index+1}">
			  		<div class="col-xs-9">
			  			<a href="${ctx}/mgr/membercard/showcard/${mcard.id}" class="thumbnail fancybox" data-fancybox-type="iframe" rel="fancy_${mcard.id}">
					      <img data-src="holder.js/100%x180" alt="" src="${mcard.logo}" />
					    </a>
			  		</div>
			  	</c:forEach>
			  </div>
			  <div class="panel-footer">
			  	<tags:pagination page="${mcards}" paginationSize="5"/>
			  </div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
$(document).ready(function () {
    $('.fancybox')
    	.fancybox({
        type: "iframe",
        padding: 0,
        fitToView: false,
        autoSize: false,
        width: 718,
        height: 503
    });
});
</script>   
<script type="text/javascript">
$(document).ready(function() {
	$(".delete_").click(function(){
		var id = $(this).attr("rel");
		if(confirm("确定要删除吗?")){ 
           $.ajax({
	         url: "${ctx}/mgr/membercard/delete/"+id,
	         type: 'DELETE',
	         async:false,
	         contentType: "application/json;charset=UTF-8"
	       })
	       .done(function( html ) {
	    	   //$("#deleteMcard_"+id).remove();
	    	   //$("#display_"+id).parent().html("<div class='red'>已失效</div>")
	    	   //$(this).find("td").each(function(){
	    	   	//	$("#display_"+id).remove();
	    	   //});
	    	   $("#display_"+id).parent().html("<div class='red'>已失效</div>")
	    	   $("#display_"+id).remove();
	    	   $(".page-header").after("<div id='message' class='alert alert-success'><button data-dismiss='alert' class='close'>×</button>会员卡&nbsp;'"+html.title+"'&nbsp;删除成功</div>");
	       }).fail( function(jqXHR, textStatus){
	    	  alert("删除失败jqXHR="+jqXHR+"textStatus="+textStatus);
	   });
     } 
   });
   
 //  $("#show_menu").bind("click",function(){alert(111);}).trigger("click");//绑定一个事件
//   $(".div_text").mouseover(
//		function(e){
//			$(".div_text").append("<div><ul><li><a href="www.sina.com.cn">修改</a></li><li><a>删除</a></li></ul></div>");
//			$("#msg").append(e.pageX+"-"+e.pageY+"<br />");
//		});
//		function(e){
//			$(this).css("background-color","green");
//			$("#msg").append(e.pageX+"-"+e.pageY+"<br />");
//		}  
       $(".div_text2").bind({
              mouseout:function(){
                     //mouseLeave(this);
                     //$(this).removeClass("add1");
                     $(this).children("div").removeClass("display_ok");
              },
              mouseover:function(e){
              		$(this).children("div").addClass("display_ok");
              		//$(this).addClass("add1");
              		//$(this).css('background-color','red')
              		//if($("div.display_no").parent().find("div[id='1']"))
              		//{
              			//$("div.display_no").addClass("display_ok");
              		//}
              		//$("div.display_no").addClass("display_ok");
              		//$('#msg').html("value:"+$('div.div_text').val() +"<br/>");
                    //$('#msg').html("X Axis : " + e.pageX + " | Y Axis " + e.pageY +"<br/>");
                    //$('#msg').append("P X Axis : " + $('.div_text').position().left + " | P Y Axis " + $('.div_text').position().top +"<br/>");   
                    //$('#msg').append("P X Axis : " + $('.div_background').position().left + " | P Y Axis " + $('.div_background').position().top +"<br/>");                                     
              }
       });
       
       $(".div_text1").bind({
              mouseout:function(){
                     $(this).children("div").removeClass("display_ok");
              },
              mouseover:function(e){
              		$(this).children("div").addClass("display_ok");                                 
              }
       });
});

$(document).ready(function() {
              $("td#deleteMcard_0").each(function(){
                     $(this).find("div.display_no").remove();
                     $(this).find("div.div_text2").html("<div class='red'>已失效</div>");
              });
        //$("#deleteMcard_0").find('div.display_no').remove();
        //$("#deleteMcard_0").find('div.div_text2').html("已失效");
        //$("#deleteMcard_0").find('div.div_text2').html("<div class='red'>已失效</div>")
             
});
</script>
</body>
</html>