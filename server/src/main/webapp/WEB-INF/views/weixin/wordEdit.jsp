<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html >
<head>

<title>微信关键字修改</title>
<style type="text/css">
.jumbotron {
	background-color: rgba(17, 17, 16, 0.08);

}
table.table.table-hover.table-bordered{
margin-left:30px;
}
</style>
<script src="${ctx}/static/layer1.7.0/layer.min.js"></script>

<script type="text/javascript">
$(function(){

	$("#ruleid").val(${keyword.rule.id});
	
	
	
	
	var title=true;
	var ruleid=false;
//关键词校验
	$("#title").blur(function(){
		$("#title").next().remove();
		if($("#title").val()==""){
			$("#title").after("<h6 class='alert alert-danger'>关键字不能为空</h6>");
			title=false;
		}else{
			$("#title").after("<span class='glyphicon glyphicon-ok'></span>");
			title=true;
		}	
	
	});
	//选择规则校验
	$("#ruleid").blur(function(){
		$("#ruleid").next().remove();
		if($("#ruleid").val()=="请选择"){
			$("#ruleid").after("<h6 class='alert alert-danger'>请选择一个规则</h6>");
			ruleid=false;
		}else{
			ruleid=true;
		}
	});
	//提交校验
	$("#but").click(function(){
	 	$("#add").empty();
	   if(!(title&&ruleid)){
			$("#add").html("<h6 class='alert alert-danger'>请完善信息</h6>");
	   }else{ 
		   $("#sub").submit();
	   }
	   
		
	});
});
//
</script>


</head>
<body >


<a style="display: inline-block;"></a>
<div id="text" style="display: none; ">
</div>
	<div class="row">
		<div class="col-md-2"  >
			<%@ include file="nav.jsp"%>
		</div>
		<div class="col-md-10 ">
			<div class="jumbotron">
				<h3 style="display: inline-block; color: #3276b1;">修改关键字</h3>
				&emsp; <span style="font-size: x-large;"
					class="glyphicon glyphicon-hand-down"></span>
				<h6>关键字修改的维护</h6>
				<br />

				<form:form role="form" method="post" modelAttribute="rule"
					action="${ctx}/mgr/weixin/wordEditSave"  id="sub">
					
				
					 
					<div class="form-group">
						<label for="title">关键字名称<h6>支持同时新增多个关键字用<kbd>逗号 ,</kbd>分隔</h6></label> 
						<input type="text" 	  disabled="disabled" 	class="form-control" id="title" name="keywordname" value="${keyword.word }">
					</div>
					  
					<input  type="hidden"    value="${keyword.id}"    name ="keywordid"/>
					
					
					<div class="form-group">
						<label for="keywords">选择规则</label> 
						<select   name="keywordruleid"  class="form-control" id="ruleid">
							<option  >请选择</option>
                            
							<c:forEach items="${rules}" var="rule">

                            <option    value="${rule.id}" >${rule.title }</option>
		
							</c:forEach>
							
						</select>


					</div>
        

		

	
					<br />
					<input type="button" class=" btn btn-primary"  value="添加关键字" id="but">
					<button style="margin-left: 50px" type="button"
						class=" btn btn-success"   onclick="history.back();" >返回上一级</button>
						<div id="add"></div>
				</form:form>

			</div>
			<div class="alert alert-success">1.点击<strong>添加关键字</strong> <br/>2.不同参数的不同页面的体现</div>
		</div>
		
	</div>


</body>



</html>