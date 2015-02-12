<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<div style="float:left;width:500px;">
       	 	<table class="table table-striped table-bordered table-condensed" id="table">
       	 		<thead id="thead">
				<tr>
					<th title="ClassKey" width="200px">键</th>
					<th title="value" width="200px">值</th>
					<th title="seq" width="80px">排序
		  			<span style="margin-left:20px;"></span>&nbsp;<span id="prompt" style="color:#4169E1"></span>
					</th>
				</tr>
				</thead>
				<tbody id="tbody">
				<c:forEach items="${dicts.content}" var="item">
				<tr>
					<td>${item.key}</td>
					<td>${item.value}</td>
					<td>${item.seq}</td>
				</tr>
				</c:forEach>	
				</tbody>
       	 	</table>
       	 </div>