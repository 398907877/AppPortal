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

<title>微信接入管理</title>
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

function alertdivtodo(data,id){
	

	
	//发送   ajax  data  拼成 一个页面    
	$.ajax({
			url: '${ctx}/'+data, 
			type: 'GET',
			contentType: "application/json;charset=UTF-8",
			dataType: 'json',
			success: function(data){
			
				var parray=new Array();
				$.each(data,function(key,value){
		//根据key的类型，不同的处理		
					if(key=="ppp"){
		//获得属性放入数组			
				$.each(value,function(i,n){
					parray[i]=n;
				});
					}
				});	
		
				var tarray=new Array();
		//获得value值放入数组		
				$.each(data,function(key,value){
					if(key!="ppp"){
				var varray=new Array(parray.length);
						$.each(value,function(i,n){
							varray[i]=n;
						});
						tarray.push(varray);
						
					}
					
				});
				
			
			//拼出弹出框页面
           var html="<table   class='table table-hover table-bordered' style='table-layout: fixed;word-wrap:break-word; width:400px;height: 200px	'><thead><tr><th>选择</th>";
				var html1="";
				var html2="";
				var html3="</tr></thead><tbody class='junzee'>";
				var html4="</tr></tbody></table><div>&nbsp;<button type='button' class='btn btn-primary' id='11'>选择</button></div>";
				var totalhtml;
				  for(var i=0;i<parray.length;i++){
			   var html6="<th>";
			   //Id一列不显示
					  if(i==0){
						  html6="<th style='display:none;'>";
					  }
					  html1+=html6+parray[i]+"</th>";
						}
				  for (var i=0;i<tarray.length;i++){
					  
					  var darray= tarray[i];

					 var html5="";	
					  for(var j=0;j<darray.length;j++){
						  var html6="<td>";
						  if(j==0){
							  html6="<td style='display:none;'>";
						  }
					  html5+=html6+darray[j]+"</td>";
					  
					  }

					
					  html2+="<tr class='info warning'><td><input type='radio' name='optionsRadios' id='optionsRadios1' value="+darray[0]+"></td>"+html5+"</tr>";
				  }
			 
			     totalhtml=html+html1+html3+html2+html4;
		
			 	$("#text").html(totalhtml);
					

			//触发弹出框	
				 $.layer({
						    type: 1,
						    title: false,
						    offset: ['15px' , ''],
						    area: ['450px', '600px'],
						    shade: [0],
						    
						   page : {dom : '#text'}
						    	
						    , success: function(){
					//成功后执行,点击选择触发的方法		
								 layer.shift('right');
								 $('#11').click(function(){ 
									//获得被选择的值放入input中
									  var a=$('input[name="optionsRadios"]:checked').val(); 
								     
							         $("#"+id).val(a);
					//关闭弹框		         
					     
							       layer.close(1);
									});
									
						    }
						});	
				
					
				}
				});
	
}
//------------------------------------------------------------------------------------------------
//-bean ---------》method 
$(function(){

	
	
	$("#beansel").change(function(){
		
		$("#methodget").attr("style","");

		var bizcode=$("#beansel  option:selected").attr("class");
		
		//首先 把选中的这个 class  也就是bizcode  赋值到  隐藏input中,为了表单提交时给后台传值
		$("#bizcodeget").val(bizcode);
		
		//清除
		$("#methodsel option").remove();
			$("#methodsel").append("<option  >请选择</option> ");
		
		$.ajax({
			url: '${ctx}/mgr/weixin/keywords/beanname?bizcode='+bizcode, 
			type: 'GET',
			contentType: "application/json;charset=UTF-8",
			dataType: 'json',
			success: function(data){
				//把所有的方法传过来

				var zhlist=data.zhname;
				var enlist=data.enname;
				$.each( data,function(key,value){
					
					if(key=='beanname'){
						
						$("#beansel  option:selected").val(value);
						
					}
					
					
					if(key=='zhname'){
						var zhlen=value.length;
						
						for(var i=0;i<zhlen;i++){
							var j=i+1;
							$("#methodsel ").append("<option  ></option> ");
							$("#methodsel option:eq("+j+")").attr("value",value[i]);
						}		
					};
					if(key=='enname'){
						var enlen=value.length;
						for(var i=0;i<enlen;i++){
							var j=i+1;
							$("#methodsel option:eq("+j+")").html(value[i]);
						}
					};

				});	
			},error:function(xhr){
				alert('AJAX错误！！！！');
			}
		});
	});

});


//------------------------------------------------------------------------------------------------
//-method---------》paras
$(function(){
	$("#methodsel").change(function(){
		
		$("#paraminp div").remove();
		$("#paramget").attr("style","");
		
		var beanname=$("#beansel  option:selected").val();
		var methodname=$("#methodsel  option:selected").attr("value");
		
		//---
$.ajax({
			url: '${ctx}/mgr/weixin/keywords/methodname?beanname=' + beanname+'&methodname='+methodname, 
			type: 'GET',
			contentType: "application/json;charset=UTF-8",
			dataType: 'json',
			success: function(data){
			$.each(data,function(key,value){
				
				//desc
				if(key=="aparadesclist"){

					var paradesclistlen=value.length;
					for (var i = 0;  i<paradesclistlen; i++) {
						
						$("#paraminp").append("<div class='col-xs-3'><label style='font-size: x-small;' for='keywords'>参数3</label> <br /><input type='text' class='form-control'		placeholder='Waiting for input'></div>");
						
						$("#paraminp div  input:eq("+i+")").attr("placeholder",value[i]);
					}
				}
				//en
				if(key=="bparaenlist"){
					var paraenlistlen=value.length;
					for (var i = 0;  i<paraenlistlen; i++) {
						
						
						$("#paraminp div     label:eq("+i+")").html(value[i]);
					}
				}
				//zh
				if(key=="cparazhlist"){
					var parazhlistlen=value.length;
					for (var i = 0;  i<parazhlistlen; i++) {
						
						
						$("#paraminp div  input:eq("+i+")").attr("name",value[i]);
					}
				}
				//type
				if(key=="dparatypelist"){
					//对于type的处理，如果是
					//1.text就不做修改  
					//2.hidden 就改成 隐藏 
					//3.alert改成 在input后面  多一个点击，然后 选择
					var paratypelen=value.length;
					for (var i = 0;  i<paratypelen; i++) {
						if(value[i]=="text"){
				
							//不处理//1.text就不做修改  
							
							
							
						}else if(value[i]=="hidden"){
							//2.hidden 就改成 隐藏 
							
							$("#paraminp div  input:eq("+i+")").attr("disabled","disabled");
							$("#paraminp div  input:eq("+i+")").val("id 会自动生成不需要输入");
							
							
							
						}else if(value[i]=="alert"){
							//3.alert改成 在input后面  多一个点击，然后 选择
							
							
						
							$("#paraminp div  input:eq("+i+")").attr("readonly","readonly");
						}	
					}
				}
				//handle
				if(key=="eparahandlelist"){
					var handlelen=value.length;
					for (var i = 0;  i<handlelen; i++) {
						if(value[i]==""){
							
						}else{
							var api=value[i];
							$("#paraminp div  input:eq("+i+")").attr("id",i);
							$("#paraminp div  input:eq("+i+")").after(" <a onclick='alertdivtodo(\""+api+"\","+i+")'	>选择</a>");
						}

					}
				}

			});
			
			//把页面的数据放到  input隐藏域value中！  后台去处理  并获得相应的值  
			  $("#allparamname").val("");
			$("#paraminp .col-xs-3 input").each(function(){
				var Pname= $("#allparamname").val();
			    $("#allparamname").val(Pname+$(this).attr("name")+',');
			    
			});
			
			},error:function(xhr){
				alert('AJAX错误！！！！');
			}
		});
		//---

	});


	
});

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
		<div class="col-md-10 " >
			<div class="jumbotron"  >
				<h3 style="display: inline-block; color: #3276b1;">修改规则</h3>
				&emsp; <span style="font-size: x-large;"
					class="glyphicon glyphicon-hand-down"></span>
				<h6>规则维护</h6>
				<br />

				<form:form role="form" method="post" modelAttribute="rule"
					action="${ctx}/mgr/weixin/keywords/edited">
					
					<!-- 隐藏域 -->
					<input  type="hidden"    id="bizcodeget"  name="bizcode"/>
					<input  type="hidden"   id="allparamname"    name="allpara" />
					<input  type="hidden"  name="ruleId" value="${rule.id}">
					
					<div class="form-group">
						<label for="title">规则</label> 
						
						<input type="text"
					value="${title}"	readonly="readonly"	class="form-control" id="title" name="keywords">
					</div>
					<div class="form-group">
						<label for="keywords">模块标识Bean</label> 
						<select id="beansel"	  name="beanname"  class="form-control">
							<option  >请选择</option>

							<c:forEach items="${bizDic}" var="biz">

								<option    class="${biz.key}" >${biz.value }</option>
		   
							</c:forEach>
							
						</select>


					</div>


					<div  style="display: none;"     id="methodget"    class="form-group">
						<label for="keywords">逻辑调用method</label> 
						<select   name="methodname" 	 id="methodsel" class="form-control">


<%-- 							<c:forEach items="" var=""> </c:forEach> --%>
<!-- 						点击bean 的时候把  -->

						</select>

					</div>

					<div      style="display: none;"   id="paramget" class="form-group">
						<label for="keywords">所有参数</label> <br />
						<div   id="paraminp"  class="row">
						<!-- 遍历这边的input    获取到  input中  name 的值 -->


						</div>
					</div>
					<br />
					<button type="submit" class=" btn btn-primary">提交</button>
					<button style="margin-left: 50px" type="button"
						class=" btn btn-success"   onclick="history.back();">返回上一级</button>
				</form:form>
			</div>
			 <!-- 规则模块 标识-->
           <div class="jumbotron">
           <h4 style="display: inline-block; color: #3276b1;"  class="form-group">${title}规则的基本信息</h4>
				&emsp; <span style="font-size:m-middle;"
					class="glyphicon glyphicon-hand-down"></span><br/>
			<label>模块标识Bean:</label> 
			<div  class="form-group">
							${descri}
                  </div>
                  <label  >逻辑调用method:</label> 
                  
                  <div class="form-group">
                  ${enName}
                  </div>				
             <label  >  全部参数</label>
            <div >
                <c:forEach items="${params}" var="par">
                <div class="form-group"> ${par.key}:${par.value}</div>
               </c:forEach>
                </div>
                </div>
                <!-- end -->
                
			<div class="alert alert-success"  style="clear: both">1.点击<strong>关键字</strong> <br/>2.不同参数的不同页面的体现</div>
		</div>
		
	</div>
   <div>

                </div>
</body>



</html>