<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>订单详情</title>
</head>
<body>
	
	<fieldset>
		<legend>订单详情</legend>
	
			<div class="bs-callout bs-callout-info" style="width:90%">
				订单号: ${order.orderNo}<br />
				物流号: ${order.logisticNo}<br />
				订单总价: ${order.price}<br />
				订单积分: ${order.integralCount}<br />
				收货人: ${order.address.name}<br />
				联系电话: ${order.address.mobile}<br />
				收货地址: ${order.address.province}${order.address.city}${order.address.area}${order.address.address}<br />
				邮编: ${order.address.zipcode}
				<p class="text-center text-success">
					<c:forEach items="${orderState}" var="os">
						<c:if test="${os.key == order.state }">订单状态:${ os.value}</c:if>
					</c:forEach>
				</p>
				
			</div>
			<table class="table table-striped" style="width:90%">
				<tr><th>商品名称</th><th>商品单价</th><th>商品数量</th><th>小计</th></tr>
				<c:forEach items="${order.products }" var="p">
					<tr>
						<td>${p.name }</td><td>${p.price }</td><td>${p.num }</td><td>${p.num*p.price }</td>
					</tr>
				</c:forEach>
			</table>
	</fieldset>
</body>
</html>