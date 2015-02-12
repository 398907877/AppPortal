<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加数据字典</title>
	<link href="${ctx}/static/select2-3.4.6/select2.css" rel="stylesheet"/>
	<script src="${ctx}/static/select2-3.4.6/select2.js"></script> 
</head>
<body>
	<div class="page-header">
   		<h2>添加数据字典</h2>
 	</div>
 	
 	<div class="row">
	<div class="col-sm-6">
		<form id="form"  action="${ctx}/mgr/dict/save/add" method="post" class="form-horizontal">
		<div class="form-group">
			<label class="control-label col-sm-2" for="e5">ClassName:</label>
			<div class="controls col-sm-10">
				<div class="select2-container" >
					<input id="e5" type="hidden" style="min-width:200px;"/>
				</div>
				<span for="className" class="error" style="display:none" id="classSelect" >必须输入或者选择ClassName</span>
				<input type="hidden" id="className" name="className" />
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-sm-2" for="key">键:</label>
			<div class="controls col-sm-10">
				<input class="form-control" name="key" id="key" />
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-sm-2" for="value">值:</label>
			<div class="controls col-sm-10">
				<input class="form-control" name="value" id="value" />
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-sm-2" for="seq">排序:</label>
			<div class="controls col-sm-10">
				<input class="form-control" name="seq" id="seq" value="${dict.seq }"/>
			</div>
		</div>
		<div class="form-group">
 				<label class="col-sm-2 control-label"></label>
 				<div class="col-sm-10">
			<button type="submit" class="btn btn-primary" id="submit">保存</button>
			<a href="<%=request.getContextPath()%>/mgr/dict/index" class="btn btn-primary">返回</a>
			</div>
	    </div>
		</form>
	</div>
	<%--<div class="col-sm-6" id="showTable">
		 <table class="table table-striped table-bordered table-condensed" id="table">
       	 		<thead id="thead">
				<tr>
					<th title="ClassKey">键</th>
					<th title="value">值</th>
					<th title="seq">排序
					</th>
				</tr>
				</thead>
				<tbody id="tbody">
				<c:forEach items="${dicts.content}" var="item">
				<tr>
					<td>${item.key}</td>
					<td>${item.value}</td>
					<td >${item.seq}</td>
				</tr>
				</c:forEach>	
				</tbody>
       	 </table>
       	 <tags:pagination page="${dicts}" paginationSize="5"/>	 
	</div>--%>
	
	<div style="float:left;width:500px;">
       	<table class="table table-striped table-bordered table-condensed" id="table">
       	 		<thead id="thead">
				<tr>
					<th title="ClassKey">键</th>
					<th title="value">值</th>
					<th title="seq">排序
					</th>
				</tr>
				</thead>
				<tbody id="tbody">
				
				</tbody>
       	 </table>
     </div>
</div>
<script type="text/javascript" >
	var re = [];
	<c:forEach items="${classNames}" var="cc">
		re.push({id:"${cc}",text:"${cc}"});
	</c:forEach>

    $(function (){
    		//var maxSeq = $("#seq").val();
    		
       	 	 var data ={results:re};
    $("#e5").select2({
    	 placeholder: "输入或者选择一个ClassName",
        createSearchChoice:function(term, data) { if ($(data).filter(function() { return this.text.localeCompare(term)===0; }).length===0) {return {id:term, text:term};} },
        multiple: false,
        data: data
    });
       	 	$("#e5").on("change",function(e){
       	 		$("#className").val($("#e5").select2("val"));
       	 		classChange($("#e5").select2("val"));
       	 		classChanges($("#e5").select2("val"));
       	 	});
       	 	$("#e5").on("select2-close",function(e){
    	 		if($("#e5").select2("val") == ""){
    	 			$("#classSelect").show();
    	 		}else{
    	 			$("#classSelect").hide();
    	 		}
    	 	});
       		 $("#form").validate({
       	 		rules:{
       	 			className:{
       	 				required:true,
       	 			},
       	 			key:{
       	 				required:true,
       	 				remote: {
       	 		    		url: "<%=request.getContextPath()%>/mgr/dict/existKey",     //后台处理程序
       	 		    		type: 		"post",               //数据发送方式
       	 		    		dataType: "json",           //接受数据格式   
       	 		    		data: {  
       	 		    			//要传递的数据
       	 		    			key:function(){ return $("#key").val();},
       	 		        		className: function() {
       	 		        			if($("#className").val() == ""){
       	 								$("#classSelect").show();
       	 								return;
       	 							}
       	 		            		return $("#className").val();
       	 		        		}
       	 		    		}
       	 				}
   	 				},
   	 				value:{
   	 					required:true,
   	 				},
       	 			seq:{
       	 				required:true,
       	 				min:1,
       	 				digits:true
       	 			}
       	 		},messages:{
       	 			className:{
   	 					required:"必须填写",
 					},
       	 			key:{
       	 				required:"必须填写",
       	 				remote:"该ClassName中已存在该键值"
	 				},
	 				value:{
	 					required:"必须填写",
	 				},
   	 				seq:{
   	 					required:"必须填写",
   	 				}
       	 		},submitHandler:function(form){
					if($("#className").val() == ""){
						$("#classSelect").show();
						return;
					}
       	 			form.submit();
             }  
       	 	});
       	 			/* var clone;
       	 			var clone1;
       	 			$("#newClass").toggle(function (){
       	 				clone=$("#class").clone(true);
       	 				$("#class").remove();
       	 				clone1=$("#tbody").clone(true);
       	 				$("#tbody").remove();
       	 				$("#newClass").val("返回列表");
       	 				$("#createClass").prepend("<input id='class' name='className' />");
       	 			},function(){
       	 				$("#class").remove();
       	 				$("#newClass").val("添加className");
       	 				$("#createClass").prepend(clone);
       	 				$("#table").append(clone1);
       	 			});
       	 			$("#key").click(function (){
           	 			$("#success").hide();
           	 		}); */
       	 		});
				function classChange(className){
    				$.ajax({
						url: '<%=request.getContextPath()%>/mgr/dict/getSeq?className=' + className, 
						type: 'GET',
						contentType: "application/json;charset=UTF-8",
						success: function(data){
							$("#seq").val(data);
						},error:function(){
							
						}
					});
    				$("#tbody").html("");
					$.ajax({
						url: '<%=request.getContextPath()%>/mgr/dict/load?search_EQ_className='+className, 
						type: 'GET',
						contentType: "application/json;charset=UTF-8",
						dataType: 'text',
						success: function(data){
							var parsedJson = $.parseJSON(data);
							jQuery.each(parsedJson, function(index, itemData) {
			       	 			$("#tbody").append("<tr><td>"+itemData.key+"</td><td>"+itemData.value+"</td><td >"+itemData.seq+"</td></tr>");
							});
						}
					});
       	 		}
       	 </script>
</body>
</html>