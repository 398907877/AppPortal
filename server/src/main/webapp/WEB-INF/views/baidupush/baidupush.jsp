<!DOCTYPE>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>


<html>

<head>
<title>Insert title here</title>

<style type="text/css">

.junzee td {width:100px;
word-break:keep-all;
white-space:nowrap;
overflow:hidden;
text-overflow:ellipsis; }
</style>

<script type="text/javascript">
$(function (){
	
	$(".junzee tr").addClass("info");
	$(".junzee tr:even").addClass("warning");
	
	$("#mantype").change(function(){
		var nowval=$(this).val();
			$('#add').remove();

		if(nowval!='0'){
			$("#afteryou").append("<div   id='add' class='form-group'> <br> <input type='text' id='disabledTextInput' name='edition' class='form-control'  placeholder=' 当选择单人或分组的时候，才有此选项' style='width: 300px;' > </div>");
		}
	});

})



</script>
<script src="${ctx}/static/layer1.7.0/layer.min.js"></script>
<script type="text/javascript">
var i,j;

$(function(){
	

//单人推送事件	
	$('#pushone').click(function(){
		
		//layer.msg('Hello layer', 2, -1); //2秒后自动关闭，-1代表不显示图标
	  i=$.layer({
		    type: 1,
		    title: false,
		    offset: ['7px' , ''],
		    area: ['450px', '600px'],
		    shade: [0],
		    page: {
		    	dom: '#alerttable', 
		    }, success: function(){
		        layer.shift('right'); //左边动画弹出
		    }
		});
		
		
	});
	
//分组推送事件	
	$('#pushtag').click(function(){
		

		
	j=	$.layer({
		    type: 1,
		    title: false,
		    offset: ['7px' , ''],
		    area: ['350px', '600px'],
		    shade: [0],
		    page: {
		       dom:'#doubletable'
		    }, success: function(){
		        layer.shift('right'); //左边动画弹出
		    }
		});
		
		
	});
	
	
	
	
});
$(function(){
	
//选择单人推送
	$('#11').click(function(){

	 	var a=$('input[name="optionsRadios"]:checked').val();

	    var b=a.split("/");

	 	$("#mantype").val("1");
	 	$('#add').remove();	 
	 	$("#afteryou").append("<div   id='add' class='form-group'> <br> <input type='text' id='disabledTextInput' name='edition' class='form-control'  placeholder=' 当选择单人的时候，才有此选项' style='width: 300px;' > </div>");
	    $("#disabledTextInput").val(b[0]);

	    $("#userId").val(b[1]);
	    layer.close(i);
	});
	
//选择分组推送
	$('#22').click(function(){
		var a=$('input[name="radioTag"]:checked').val();
		var b=a.split("/");
		$("#mantype").val("2");
		$('#add').remove();	 
	 	$("#afteryou").append("<div   id='add' class='form-group'> <br> <input type='text' id='disabledTextInput' name='tagName' class='form-control'  placeholder=' 当选择单人的时候，才有此选项' style='width: 300px;' > </div>");
	    $("#disabledTextInput").val(b[0]);
	    layer.close(j);
		
	});
	
	
	})


</script>





</head>

<body>

<div ></div>

<div>


<div style="margin-top: 20px;">

	<div class="jumbotron" style="height: 180px; background: #333333;">
		<h2 style="color: white; font-size: 30px;margin-left: -50px">消息推送</h2>
		<h6 style="color: windowframe;margin-left: -40px">用于消息推送</h6>
	</div>

	<br></br>
</div>
	<form role="form" style="margin-top: -50px"
		action="${ctx}/mgr/baidu/push"   method="post">
		<fieldset>
			<div class="form-group">
				<label for="disabled">推送设备类型</label> 
					<select 	id="disabledSelect" class="form-control"      name="deviceType" style="width: 300px">
				      
					       <option value="3">安卓</option>
					        <option value="4">IOS</option>
				       
				</select>
			</div>
			
			<div  id="afteryou" class="form-group">
			   
				<label  style="float: left;"  for="disabled">推送人群类型</label> <br><br>
					 <div style="float: left;">
					<select     id="mantype"  class="form-control"      name="populationType" style="width: 300px;">
				      
					       <option   value="0" >全部</option>
					     
					        <option   value="1" >单人</option>
					        
					        <option   value="2" >分组</option>
				       
				</select>
				</div>

			<button  id="pushone" type="button" class="btn btn-danger"     style="margin-left: 10px"   >选择单人推送</button>
			<button  id="pushtag" type="button" class="btn btn-info"     style="margin-left: 10px"   >分组推送</button>
		     
		     
		     <!-- id 打印 -->
		    
		     <input  id="userId"  name="memberId"  type="hidden" ></input> 
		     
			</div>


           <div style="clear: both;"  class="form-group">
				<label for="disabled">消息类型</label> 
					<select 	id="disabledSelect" class="form-control"      name="messageType" style="width: 300px">
				            <option value="0" >消息</option>
					       <option  value="1"  >通知</option>
	
				       
				</select>
			</div>

			<div  class="form-group">
				<label for="disabledTextInput">消息绑定名</label> <input type="text"
					id="disabledTextInput" class="form-control" placeholder=" 请输入。。。"
					name="title" style="width: 300px">
			</div>
			
			<div  class="form-group">
				<label for="disabledTextInput">消息内容</label> <input type="text"
					id="disabledTextInput" class="form-control" placeholder=" 请输入。。。"
					name="message" style="width: 300px">
			</div>

			<div class="checkbox">
				<label> <input type="checkbox"> 是否确认推送
				</label>
			</div>
			<div class="alert alert-success">恭喜填写正确</div>
			<br />
			<!--     完成度 -->
			<div class="progress">
				<div class="progress-bar" role="progressbar" aria-valuenow=""
					aria-valuemin="0" aria-valuemax="100" style="width: 30%;">
					30%</div>
			</div>
			
			
	
			

			<button id="jun" type="button" class="btn btn-success"
				onclick="this.form.submit()">推送</button>
		
		   <button type="button" class="btn btn-warning"   style="margin-left: 50px" onclick="javascript:history.go(-1);">返回</button>
	
		</fieldset>
	</form>


</div>


<!--begin  alert table（单人推送） -->

<div id="alerttable"   style="display:none;">
		<br />
		<div style="margin-left: 30px" class="container-fluid">
			<div class="row-fluid">
				<div class="span12">
					<h3 class="text-center">用户选择列表</h3>
					<br />

					<!-- 搜索开始 -->
					<form class="form-inline" role="form" >
						<div class="form-group">

							<input class="form-control" placeholder=" 请输入搜素的关键字"
								name="search_LIKE_name">
						</div>


						<button type="submit" class="btn btn-default">搜索</button>
					</form>
					<!-- 搜索结束 -->
					<br />
					<table class="table table-hover table-bordered"  style="table-layout: fixed;word-wrap:break-word; width:400px;height: 200px	">
						<thead>
							<tr>
								<th>选择</th>
								<th>用户名</th>
								<th>设备数量</th>
								<th>电话号码</th>
								<th>状态</th>

							</tr>
						</thead>
						<tbody class="junzee">
							<c:forEach items="${users.content}" var="user">
								<tr>
									<td><input type="radio" name="optionsRadios"
										id="optionsRadios1" value="${user.name}/${user.id}"></td>
									<td>${user.name}</td>
									<td>${fn:length(user.baiduDevice)}</td>
									<td>${user.mobile}</td>
									<td>${user.status == 1 ? '开通' : '注销'}</td>
								</tr>
							</c:forEach>


						</tbody>
					</table>

					<!--  按钮--开始-->
					<button type="button" class="btn btn-primary" id="11">选择</button>
					<!--  按钮--结束-->


					<br /> <br />
					<div class="col-sm-10"  style="margin-left: -15px;">
						<tags:jun page="${users}" paginationSize="5" />
					</div>

				</div>
			</div>
		</div>
	</div>
            <!-- end 单人推送 -->

             <!-- begin 分组推送 -->


<div id="doubletable"   style="display:none;">

  <br />	
  <div style="margin-left: 30px" class="container-fluid">	
  	<div class="row-fluid">	
	<div class="span12">	
   <h3 class="text-center">用户选择列表</h3><br />
			<table    class="table table-hover table-bordered">
				<thead><tr>
				<th>选择</th>
				<th>Id</th>
				<th>分组名称</th>
			
				</tr>
				</thead>
				<tbody class="junzee">
				<c:forEach items="${tags.content}" var="tag">
					<tr>
					<td>
					<input type="radio" name="radioTag" id="tagId" value="${tag.name}/${tag.id}" />
					</td>
                      <td>${tag.id}</td>
                      <td>${tag.name}</td>			
				</c:forEach>
		       </table>				
					<!--  按钮--开始-->				
					<button type="button" class="btn btn-primary" id="22">选择</button>				
					<!--  按钮--结束-->												
					<br /><br />	
				
					<div class="col-sm-10"  style="margin-left: -15px;">
						<tags:jun2 page2="${tags}" paginationSize="5" />
					</div>			
					</div>
					</div>
					</div>
					</div>
															
           <!-- end 分组推送 -->
</body>
</html>