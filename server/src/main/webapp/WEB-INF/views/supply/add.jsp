<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<link href="${ctx}/static/bootstrap/additions/jasny/jasny-bootstrap.min.css" type="text/css" rel="stylesheet" />
	<link href="${ctx}/static/bootstrap/additions/wysihtml5/bootstrap-wysihtml5.css" type="text/css" rel="stylesheet" />	
	<link href="${ctx}/static/select2-3.4.6/select2.css" rel="stylesheet"/>
 	<script src="${ctx}/static/bootstrap/additions/wysihtml5/wysihtml5-0.3.0.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/bootstrap/additions/wysihtml5/bootstrap3-wysihtml5.js" type="text/javascript"></script>
	<script src="${ctx}/static/bootstrap/additions/wysihtml5/locales/bootstrap-wysihtml5.zh-CN.js" type="text/javascript"></script>
	<script src="${ctx}/static/select2-3.4.6/select2.js"></script>
	<script src="${ctx}/static/bootstrap/additions/jasny/jasny-bootstrap.min.js" type="text/javascript"></script>
	<link href="${ctx}/static/bootstrap/additions/datepicker/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />
	<script src="${ctx}/static/bootstrap/additions/datepicker/bootstrap-datetimepicker.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/bootstrap/additions/datepicker/locales/bootstrap-datetimepicker.zh-CN.js" type="text/javascript"></script>
	<script src="${ctx}/static/bootstrap/hold.js" type="text/javascript"></script>
	<style type="text/css">
	.categoryManage{margin-left:10px}
	.error{color:red}
	</style>
</head>
<body>
<div class="row">
		<div class="col-md-2">
			<%@ include file="nav.jsp"%>
		</div>	
		<div class="col-md-10 ">
		<div>
	<div class="page-header">
		<h2>添加供求信息</h2>
	</div>
	
	<form:form id="form" method="post" modelAttribute="supplyDemand" cssClass="form-horizontal" action="${ctx}/mgr/supply/save"  enctype="multipart/form-data">
			<div class="form-group">
		<label class="col-sm-2 control-label" for="selectType">供求类型:</label>
		<div class="col-sm-10 controls">
		<select id="selectType" name="type" style="min-width:240px;" class="populate placeholder select2-offscreen se2" tabindex="-1" title="">
				<option value=""  selected="selected">---------请选择供求类型---------</option>
				<option value="0">供（提供给他人信息）</option>
				<option value="1">求（想要获得的信息）</option>
		 </select>
		 </div>
	</div>
	
	<div class="form-group">
	<label class="col-sm-2 control-label" for="typeId">所属行业:</label>
	<div class="col-sm-10 controls">
				
		<form:select path="typeId" name="typeId" id="typeId" cssStyle="min-width:240px;" class="se2">
			<option value="">---------请选择所属行业---------</option>
			<c:forEach items="${supplyDemandTypes}" var="ca" varStatus="i">
						<c:if test="${ca.parentId == 0 }">
							<optgroup label="${ca.name }">
								<c:forEach items= "${supplyDemandTypes }" var="c" >
									<c:if test="${ca.id == c.parentId }">
										<option value="${c.id }"> ${c.name }</option>
									</c:if>
								</c:forEach>
							</optgroup>
						</c:if>
					</c:forEach>
		</form:select>
	</div>
</div>
		<jsp:include page="form.jsp"/>
		<div class="form-group">
			<label class="col-sm-2 control-label"></label>
			<div class="col-sm-10 controls">
			<input type="submit" id="onsubmit" class="display"/>
			<input   value="提交"  class="btn btn-primary" id="submit"  type="button"/>
			<a href="<%=request.getContextPath()%>/mgr/supply" class="btn">返回</a>
			</div>
		</div>
	</form:form>
	</div>
	<script type="text/javascript">
	$(".se2").select2({
	    placeholder: "-----------请选择信息类型----------",
	    allowClear: true
	});
	$("#selectType").change(function(){
		if($(this).val()==''){
			 $("#selectinfoType").empty();
			 $("#selectinfoType").prepend("<option value=''>---------请选择信息类型---------</option>");
			 return;
		}
		 $.ajax({
	         url: "${ctx}/mgr/supply/select/type?type="+$(this).val(),
	         type: 'POST',
	         async:false,
	         contentType: "application/json;charset=UTF-8"
	       })
	       .done(function( date ) {
	    	   $("#selectinfoType").empty();
	    	   $.each(date,function(k,val){
	    		    $("#selectinfoType").append("<option  value='"+val.id+"'>"+val.supplyDemandType+"</option>");
	    		  }); 
	    	   $("#selectinfoType").prepend("<option value=''>---------请选择信息类型---------</option>");
	    	   $("#selectinfoType").select2("val", "");
	       }).fail( function(jqXHR, textStatus){
	    	  alert("删除失败jqXHR="+jqXHR+"textStatus="+textStatus);
	   });
	  })
		$(function(){
			$(".display").hide();
			$("#submit").click(function(){
				$("#onsubmit").trigger("click"); 
			});
		});
	</script> 	
	</div>
	</div>
</body>
</html>