<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page session="false"%> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Bootstrap-确认此会员卡的信息</title>
    <link href ="${ctx }/static/bootstrap/3.1.1/ css/bootstrap.min.css" rel ="stylesheet ">
    <script src ="${ctx }/static/js/jquery.min.js"></script>
    <script src ="${ctx }/static/bootstrap/3.1.1/ js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/jquery-ui-1.8.21.custom.min.js"></script>
<style>
.display_no{display:none}
.display_ok{display:block}
</style>	
</head>
<body>
  <div class="container">
		<div class="col-md-offset-2 col-md-10 bs-callout bs-callout-info">
			<div>
				<h4>会员卡信息</h4>
				<h4><small>确认此会员卡类型的信息，准备进行删除操作</small></h4>
			</div>
			
		    <div>			
		      <c:if test="${not empty message}">
			  <div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
		    </c:if>
		    </div>
  
     <table class="table">
   <tbody>
      <tr>
         <td>会员卡名称:</td>
         <td>${mcard.cardName }</td>
      </tr>
       <tr>
         <td>会员商户编号:</td>
         <td>${tenancy.uid }</td>
      </tr>
       <tr>
         <td>会员商户名称:</td>
         <td>${tenancy.name }</td>
      </tr>
       <tr>
         <td>是否有效:</td>
         <td>${mcard.status  == 1 ? '有效' : '失效'}</td>
      </tr>
      <tr>
         <td>折扣额度:</td>
         <td>${mcard.discount }</td>
      </tr>
      <tr>
         <td>创建日期:</td>
         <td>${fn:substring(mcard.crtDate,0,19)}</td>
      </tr>
      <tr>
         <td>生效日期:</td>
         <td>${fn:substring(mcard.startDate,0,19)}</td>
      </tr>
      <tr>
         <td>失效日期:</td>
         <td>${fn:substring(mcard.expDate,0,19)} </td>
      </tr>   
      <tr>
         <td> Logo 缩略图:</td>
         <td>       
         <div class="gallery-set- thumbail ">
                           <c:if test='${mcard.logo!= null}'>
                                  <img class="image" src ="${mcard.logo}" style="width: 200px;height: 200px;margin-top:10px"/>
                                   </c:if>
               </div>
               </td>
      </tr> 
       <tr>
         <td>背景图片:</td>
         <td>       
         <div class="gallery-set- thumbail ">
                           <c:if test='${mcard.background!= null}'>
                                  <img class="image" src ="${mcard.background}" style="width: 200px;height: 200px;margin-top:10px"/>
                                   </c:if>
               </div> </td>
      </tr> 
      <tr>
         <td>模板图片:</td>
         <td>
         <div class="gallery-set- thumbail ">
                           <c:if test='${mcard.cardModel!= null}'>
                                  <img class="image" src ="${mcard.cardModel}" style="width: 200px;height: 200px;margin-top:10px"/>
                                   </c:if>
               </div> </td>
      </tr>                                              
   </tbody>          
  </table>
  
  
  
	<div class="display_no">输入企业编号：
	<input type="text" /></div>	
	<div class="form-group has-warning">
	<input type="button" class="btn btn-primary" id="delete_" value="确认删除" rel="${mcard.id}" />
	<input id="cancel_btn" class="btn btn-primary" type="button" value="返回" onclick="history.back()"/>	
	</div>
</div>

	<script type="text/javascript">
$(document).ready(function() {
	$("input[type=button]:first").click(function(){
		var id = $(this).attr("rel");
		$("div.display_no").addClass("display_ok");
		var uid=$("input[type=text]").val();
		if(uid!="")
		{
		if(${mcard.uid}!=uid){
			alert("企业编号错误");
		}
		else{
		if(confirm("确定要删除吗?企业编号:"+${mcard.uid})){ 
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
	    	    window.location.href="<%=request.getContextPath()%>/mgr/membercard/index";
	       }).fail( function(jqXHR, textStatus){
	    	  alert("删除失败jqXHR="+jqXHR+"textStatus="+textStatus);
	   });
     } 
     }
     }
     else{
     			alert("请输入企业编号：");
			$("input[type=text]").focus();
     }
     
   });
});   
	</script>
</body>
</html>
