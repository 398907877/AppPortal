<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Bootstrap-显示所有会员或查询</title>
    <link href ="${ctx }/static/bootstrap/3.1.1/ css/bootstrap.min.css" rel ="stylesheet ">
    <script src ="${ctx }/static/js/jquery.min.js"></script>
    <script src ="${ctx }/static/bootstrap/3.1.1/ js/bootstrap.min.js"></script>
	<link href="${ctx}/static/bootstrap/3.1.1/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
	<script type="text/javascript" src="${ctx}/static/js/jquery-ui-1.8.21.custom.min.js"></script>	
</head>
<body>
	<div class="container">			
		    <form id="queryForm" class="form-inline" role="form"  method="get" action="${ctx}/mgr/membercard/selects">
   				<div class="form-group">
      				<label class="sr-only" for="name">会员名称</label>	    	    
						<input name="search_LIKE_loginname" type="text" class="form-control" value="${param.search_LIKE_loginname}" placeholder="请输入要查找的会员名称"/>
						<input type="hidden" name="search_EQ_status" value="1">
						<c:if test="${''!=uid&&null!=uid }">
						<input type="hidden" name="search_EQ_uid" value="${uid}">
						</c:if> 
				   <input type="submit" class="btn btn-success" value="查 找" />
				  <tags:sort />
				</div>  
			</form>		
				
		
<div class="form-group">
	<div id="checkAll">
	<input type=checkbox value="1">全选 </input>
		<div id="checkMore">
	    <c:forEach items="${tenancyUsers.content}" var="user">
				 <input type="checkbox" value="${user.loginname}" />${user.loginname}
	    </c:forEach>
	    </div>
    </div>
</div>

	<input type="button" class="btn btn-primary" id="select" value="确定" />
</div>

	
<script type="text/javascript">
	$(document).ready(function(){
    		//第一步，查找所有 input 标签元素，并且遍历所有元素
    		$("input[type=checkbox]").each(function(){
    			if($(this).parent().is("div[id=checkAll]"))//判断是否是父类筛选框元素
    			{
    				//$(this).parent().css("overflow","auto").css("padding","10px 0").css("width","100%");
    				//$(this).parent().find("div").css("float","left");
    				//找出当前元素的父容器(div)，在查找父窗口下的子窗口(div)下的所有筛选框元素，添加自定义标签，值等于父筛选框的value
    				$(this).parent().find("div[id=checkMore]").find("input[type=checkbox]").attr("tag",$(this).val());
    			}
    		});
    		//查找所有筛选框标签元素，并绑定单击事件
    		$("input[type=checkbox]").click(function(){
				//判断当前单击事件元素是否是父筛选框元素
				if($(this).parent().is("div[id=checkAll]"))
				{
					//把当前复选框的选中状态，赋值给子复选框元素
					var test=false;
					if($(this).attr("checked"))
					{
						test=true;
					}
					alert(test);
					$("input[tag="+$(this).val()+"]").attr("checked",test);
				}
				else
				{
					//如果不是父复选框，则进入此方法
					var bo=false;
					//查找所有当前同一组子元素，判断是否有被选中的元素
					$("input[tag="+$(this).attr("tag")+"]").each(function(){
						if($(this).attr("checked")){
							bo=true;
							return false;
						}
					});
					//最后把是否选中状态，赋值给父复选框元素
					$("input[value="+$(this).attr("tag")+"]").attr("checked",bo);
				}
			});
	});
</script>
<script type="text/javascript">
	$(document).ready(function(){
		//点击确定后将选中的值传到文本框里
		$("#select").click(function(){
			//遍历所有被选中的子复选框
		   var id_array=new Array();
   		   $("input[type=checkbox]").each(function(){
    			if($(this).parent().is("div[id=checkMore]"))//判断是否是子类筛选框元素
    			{
    				//找出当前元素的父容器(div)，在查找父窗口下的子窗口(div)下的所有筛选框元素，添加自定义标签，值等于父筛选框的value
    				//$(this).parent().find("div[id=checkMore]").find("input[type=checkbox]").attr("tag",$(this).val());
    				if($(this).attr("checked"))
    				{
    				   id_array.push($(this).val());//向数组中添加元素
    				}
    			}
    		});			
			var idstr=id_array.join(',');//将数组元素连接起来以构建一个字符串
			//alert(idstr);
			//将选中的值传递给文本框
			//$("input[type=text]").val(idstr);
			//将选中的值传递给父窗口的文本框
			//$(window).close();
			$("input[type=text]",window.parent.document).val(idstr);
			parent.$.fancybox.close();
			//alert("已选择完成");
			
		});	
	});
</script>	
</body>
</html>
