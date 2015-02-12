<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
	<title>订单修改</title>
<style type="text/css"> 
.error{ 
	color:Red; 
	margin-left:10px;  
} 
</style> 
</head>

<body>
<div class="row">
		<div class="col-md-2">
			<%@ include file="nav.jsp"%>
		</div>	
		<div class="col-md-10 ">
	<div class="page-header">
   		<h2>订单修改</h2>
 	</div>
	<form id="inputForm" method="post" Class="form-horizontal" action="${ctx}/mgr/order/save" >
	<input type="hidden" name="id" value="${order.id}" > 
	<input type="hidden" name="user.id" value="${order.user.id}" > 
	<div class="form-group">
		<label class="col-sm-2 control-label" for="orderNumber">订单号：</label>
		<div class="col-sm-10 controls">
			<input type="text" name="orderNo"  value="${order.orderNo }"   class="form-control" readonly="readonly"  />
		</div>
	</div>  
	  
	<div class="form-group">
	<label class="col-sm-2 control-label" for="user.name">用户名:</label>
	<div class="col-sm-10 controls">
		<input type="text" name="user.name" class="form-control " value="${order.user.name }"  readonly="readonly" />
	</div>
</div>

<div
	class="form-group">
	<label class="col-sm-2 control-label" for="payment">应付(元)：</label>
	<div class="col-sm-10 controls">
		<input type="text" name="payment"  value="${order.payment }"   class="form-control" readonly="readonly"  />
	</div>
</div>


<div
	class="form-group">
	<label class="col-sm-2 control-label" for="freight">运费(元)：</label>
	<div class="col-sm-10 controls">
		<input type="text" name="freight" id="birth" value="${order.freight }"   class="form-control" readonly="readonly" />
	</div>
</div>
		<div class="form-group">
			<label class="col-sm-2 control-label" for="state">订单状态：</label>
			<div class="col-sm-10 controls">
				<select name="state" id="state" class="form-control">
					<c:choose>
						<c:when  test="${order.state eq '1'}">
						<option value="2">卖家确认收款</option>
						<option value="1" selected="selected">买家已付款</option>
						</c:when >
						<c:when  test="${order.state eq '2'}">
						<option value="3" >卖家已发货</option>
						<option value="2" selected="selected" id="notship">卖家已收款</option>
						</c:when >
						<c:when test="${order.state==0}">
							<option value="0" selected="selected" >买家未付款</option>	
							<option value="1" style="color: red">买家已付款（手动确认）</option>      
						</c:when>
						<c:when  test="${order.state eq '4'}">
							<option value="4" selected="selected">买家已确认收货</option>
							<option value="5" >确认订单完成</option>			             
						</c:when>
						<c:when test="${order.state==3}">
							<option value="3" selected="selected">卖家已发货</option>
							<option value="5" >订单已完成（手动确认）</option>			              
						</c:when>
						<c:when test="${order.state==5}">
							<option value="5" selected="selected">订单完成</option>		             
						</c:when>
					</c:choose>
				</select>
			</div>
		</div>
	

		<div class="form-group" id="logistic">
			<label class="col-sm-2 control-label" for="logistic">物流公司：</label>
			<div class="col-sm-10 controls">
				<select name="logistic.id" class="form-control">
					<c:forEach items="${lis}" var="pro">
						<option ${order.logistic.id==pro.id?'selected':'' }
							value="${pro.id}">${pro.logisticName}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="form-group" id="logisticNo">
			<label class="col-sm-2 control-label" for="logisticNo">运单号：</label>
			<div class="col-sm-10 controls">
				<input type="text" name="logisticNo" value="${order.logisticNo }"
					class="form-control" />
			</div>
		</div>

		<div class="form-group">
			<label class="col-sm-2" for="logisticNo"></label>
			<div class="col-sm-10 controls">
				<button type="submit" class="btn btn-info" id="submit">
				保存
				</button>
				<a onclick="history.back()" class="btn btn-default">返回</a>
			</div>
	    </div>
	</form>
	</div>
	</div>
	<script type="text/javascript">
		$(document).ready(function(){
				if($("#state").val()==0){
	 			    $("#logistic").hide();
				    $("#logisticNo").hide();
				}else if($("#state").val()==1){
	 			    $("#logistic").hide();
				    $("#logisticNo").hide(); 
				}else if($("#state").val()==2){
	 			    $("#logistic").hide();
				    $("#logisticNo").hide(); 
				}
				
				$("#state").change(function(){
					if($("#state").val()==3){
		 			    $("#logistic").show();
					    $("#logisticNo").show(); 
					}else if($("#state").val()==2){
		 			    $("#logistic").hide();
					    $("#logisticNo").hide(); 
					}
				});

		});
		
		$(function(){
			$("#inputForm").validate({
				rules:{
					logistic:{
						required:true
					},
					logisticNo:{
						required:true
					}
				},messages:{
					logistic:{
						required:"必须填写",
					},
					logisticNo:{
						required:"必须填写",
					}
				}
			});
		})
    </script> 
</body>
