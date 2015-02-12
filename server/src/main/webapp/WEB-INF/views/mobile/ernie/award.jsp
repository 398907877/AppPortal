<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="huake" uri="/huake"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width， initial-scale=1.0， minimum-scale=1.0， maximum-scale=1.0， user-scalable=no">
<script type="text/javascript" src="<c:url value="${ctx}/static/js/jquery.min.js" />"></script>
<title>我的获奖信息</title>
<style type="text/css">
body {font-family: 微软雅黑, Verdana, sans-serif;}
body,dl,dt,dd,ul,ol,li,h1,h2,h3,h4,h5,h6,pre,form,fieldset,input,textarea,p,blockquote,th{background-color:#ec2638;padding: 0;margin: 0; }
td{padding: 0;margin: 0; }
table {border-collapse: collapse;border-spacing: 0;}
img{ display:block; border:none;} a{ text-decoration:none;}
ul li{ list-style:none;}
.fl{ float:left;} .fr{ float:right;}.clear{ clear:both;}
.tit_h { width:314px; text-align:center; height:86px; line-height:60px;margin:10px auto; background:url(<c:url value="${ctx}/static/img/lottery/hdgz_h.png"/>) no-repeat; color:#3c4e18; font-size:24px; overflow:hidden;}
.hjxx{ padding:0 10px;}
.tt_l{ position:absolute; left:0; top:0;z-index: 222;}
.tt_r{position:absolute; right:0; top:0;z-index: 222;}
.ht_t{position:relative;height:32px; background:url(<c:url value="${ctx}/static/img/lottery/tt.jpg"/>) left top repeat-x; border-top:1px solid #ffc00e;}
.ht_t ul{  border-bottom:2px solid #ffc00e;height:32px;}
.ht_t ul li{  border-t:2px solid #ffc00e;width:50%; height:32px; line-height:34px; text-align:center; float:left; position:relative;}
.ht_t ul li span{display:block; width:1px; height:27px; background:#ffc00e ; position:absolute; right:0; bottom:0;}
.ht_c{ z-index: 222;background:url(<c:url value="${ctx}/static/img/lottery/cc.jpg"/>) left top repeat-y; position:static; height:150px;}
.ht_c table{background:url(<c:url value="${ctx}/static/img/lottery/cc.jpg"/>) right top repeat-y; height:180px;}
.ht_c table td{ border-t:2px solid #ffc00e;width:50%;  float:left; border-bottom:1px solid #ffc00e; height:30px; line-height:30px; text-align:center; color:#444444; font-size:14px;}
.ht_b{ position:relative; height:29px; line-height:29px;background:url(<c:url value="${ctx}/static/img/lottery/bb.jpg"/>) left bottom repeat-x;}
.ht_b a{ float:right; margin-right:30px; color:#d24d02;}
.bb_l{ position:absolute; left:0; top:0; }
.bb_r{position:absolute; right:0; top:0;}
.tag{ color:#434343; padding-top:10px; font-size:14px;}
.ht_b a.end{ color:#999999;}
</style>
<style>
@media screen and (max-width:480px) {
.tit_h{background:url(<c:url value="${ctx}/static/img/lottery/hdgz_h480.png"/>) no-repeat; width:250px; height:50px; line-height:40px;font-size:20px; }
}
</style>
</head>
<body>
<div class="tit_h">我的获奖信息</div>
<div class="hjxx">
<div class="ht_t">
<img src="<c:url value="${ctx}/static/img/lottery/tt_l.png"/>" class="tt_l"/>
<img src="<c:url value="${ctx}/static/img/lottery/tt_r.png"/>"  class="tt_r"/>
<ul><li><span></span>抽奖时间</li><li>获得奖品</li></ul>
</div>
<div class="ht_c">
<table width="100%">
<c:forEach items="${myAwards}" var="item">
<tr name="data" style="display: inline-block;">
<td><span></span><fmt:formatDate value="${item.createdAt}" pattern="MM-dd HH:mm"/></td>
<td><huake:getErineItems id="${item.winning}" /></td>
</tr>
</c:forEach>
</table>
</div>
<div class="ht_b" id="barcon">

</div>
</div>
<div id="baidu" style="display:none;">
        <script type="text/javascript">
               var _bdhmProtocol = (("https:" == document.location.protocol) ?" https://" : " http://");
               document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3Feb4498e9424feed6edb981a4c4e57a01' type='text/javascript'%3E%3C/script%3E"));
        </script>
        <script src=" http://hm.baidu.com/h.js?eb4498e9424feed6edb981a4c4e57a01" type="text/javascript"></script><a href="http://tongji.baidu.com/hm-web/welcome/ico?s=eb4498e9424feed6edb981a4c4e57a01" target="_blank"><img border="0" src="http://eiv.baidu.com/hmt/icon/21.gif" width="20" height="20"></a>
</div>
<script type="text/javascript">
	$(document).ready(function(){
		goPage(1,5);
	});
	function goPage(pno,psize){
		var itable = document.getElementsByName("data");
		var num = itable.length;//表格行数
		var pageSize = psize;//每页显示行数
		var totalPage = num/pageSize;//总页数
		var re =  /^\d+$/;//判断正整数   
	    if (!re.test(totalPage)){
	    	totalPage = parseInt(totalPage) + 1; 
	    }
		var currentPage = pno;//当前页数
		var startRow = (currentPage - 1) * pageSize;//开始显示的行   
		var endRow = currentPage * pageSize - 1;//结束显示的行   
		endRow = (endRow > num)? num : endRow;
		for(var i=0;i<num;i++){
			var irow = itable[i];
			if(i>=startRow && i<=endRow){
				irow.style.display = "block";
			}else{
				irow.style.display = "none";
			}
		}
		var tempStr = "";
		if(currentPage<totalPage){
			tempStr += "<a href='#' onclick='goPage("+(currentPage+1)+","+psize+");return false;'>下一页</a>";
		}else{
			tempStr += "<a href='#' class='end'>下一页</a>";
		}
		if(currentPage>1){//return false;阻止事件冒泡
			tempStr += "<a href='#' onclick='goPage("+(currentPage-1)+","+psize+");return false;'>上一页</a>"
		}else{
			tempStr += "<a href='#' class='end'>上一页</a>";
		}
		document.getElementById("barcon").innerHTML = "<img src='${ctx}/static/img/lottery/bb_l.png' class='bb_l' />"+tempStr+"<div class='clear'></div><img src='${ctx}/static/img/lottery/bb_r.png' class='bb_r' />";
		}
</script>
</body>
</html>



