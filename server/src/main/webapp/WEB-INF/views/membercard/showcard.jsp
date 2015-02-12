<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>会员卡详情</title>
	<link href="${ctx}/static/bootstrap/3.1.1/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
	<script src="${ctx}/static/js/jquery.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="${ctx}/static/js/jquery-ui-1.8.21.custom.min.js"></script>
<style>
.display_no{display:none}
.display_ok{display:block}
</style>	
</head>

<body>
	<div class="container">
	<table class="table">
		<caption>该类会员卡信息</caption>
		
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
         <td>Logo缩略图:</td>
         <td>        
         <div class="gallery-set-thumbail">
        			<c:if test='${mcard.logo!= null}'>
  					<img class="image" src="${mcard.logo}" style="width: 200px;height: 200px;margin-top:10px"/>
  					</c:if>
  		 </div>
  		 </td>
      </tr>  
       <tr>
         <td>背景图片:</td>
         <td>        
         <div class="gallery-set-thumbail">
        			<c:if test='${mcard.background!= null}'>
  					<img class="image" src="${mcard.background}" style="width: 200px;height: 200px;margin-top:10px"/>
  					</c:if>
  		</div></td>
      </tr>  
      <tr>
         <td>模板图片:</td>
         <td>
         <div class="gallery-set-thumbail">
        			<c:if test='${mcard.cardModel!= null}'>
  					<img class="image" src="${mcard.cardModel}" style="width: 200px;height: 200px;margin-top:10px"/>
  					</c:if>
  		</div></td>
      </tr>                                               
   </tbody>		
  </table>
</div>
</body>
</html>
