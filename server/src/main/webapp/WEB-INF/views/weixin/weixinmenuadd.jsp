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

	
<HTML>
<HEAD>
	<TITLE> ZTREE DEMO - beforeEditName / beforeRemove / onRemove / beforeRename / onRename</TITLE>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="${ctx}/static/zTree//zTreeStyle.css" type="text/css">

	<script type="text/javascript" src="${ctx}/static/zTree/jquery.ztree.core-3.5.js"></script>
	


	<script type="text/javascript" src="${ctx}/static/zTree/jquery.ztree.excheck-3.5.js"></script>
	<script type="text/javascript" src="${ctx}/static/zTree/jquery.ztree.exedit.js"></script>

	<SCRIPT type="text/javascript">
	
		var setting = {
				

				async: {
					enable: true,
					url:"${ctx}/mgr/weixin/getTreeNode",
				    autoParam:["id", "name", "menutype","menukey"],
					otherParam:{"otherParam":"zTreeAsyncTest"},
					dataFilter: filter
				},
				
			view: {
				addHoverDom: addHoverDom,
				removeHoverDom: removeHoverDom,
				selectedMulti: false
			},
			edit: {
				enable: true,
				editNameSelectAll: true,
				showRemoveBtn: showRemoveBtn,	
				showRenameBtn: showRenameBtn
			},
			data: {
				simpleData: {
					enable: true,

				}
			},
			callback: {
				
				beforeEditName: beforeEditName,
				beforeRemove: beforeRemove,
				beforeRename: beforeRename,
				onRemove: onRemove,
				onRename: onRename,
				beforeDrag: beforeDrag,
				beforeDrop: beforeDrop,
				beforeClick: beforeClick,
				onClick: onClick


			}
		};

		var aNodes =[
            { id:0, pId:0, name:"微信自定义菜单", isParent:true,open:false},   
			{ id:1, pId:0, name:"子菜单1",open:true},
			{ id:22, pId:1, name:"子菜单1",menutype:"1",menukey:"jun" },
 			{ id:33, pId:1, name:"子菜单2",menutype:"2",menukey:"http://www.baidu.com"},
 			
 			
			{ id:2, pId:0, name:"子菜单1",open:true},
			{ id:22, pId:2, name:"子菜单1",menutype:"1",menukey:"jun" },
 			{ id:33, pId:2, name:"子菜单2",menutype:"2",menukey:"http://www.baidu.com"},
 			
			

		];
		
		//start  click
			function beforeClick(treeId, treeNode, clickFlag) {
			//点击前
			
		}
		function onClick(event, treeId, treeNode, clickFlag) {
			//点击的时候 显示 节点数据
			
			//清除上次的点击效果
				$("#divrule").css("display","none");
				$("#divurl").css("display","none");
			
			var key=treeNode.menukey;
			var type =treeNode.menutype;
			
			var menuid=treeNode.id;
			var menuname=treeNode.name;
			
			
			if(type=="1"){
				$("#divrule").css("display","");
				
				$("#menuruleid").val(key);
				
			}else if(type=="2"){
				$("#divurl").css("display","");
				$("#menuurl").val(key);
			}
			
			$("#menutype").val(type);
			$("#menukey").val(key);
			$("#menuid").val(menuid);
			$("#menuname").val(menuname);
			
			
			
			
			
		}	
		
		//end  click
		
		function beforeDrag(treeId, treeNodes) {

			return true;
		}
		function beforeDrop(treeId, treeNodes, targetNode, moveType) {
			return true;
		}
		
		var log, className = "dark";


		function beforeEditName(treeId, treeNode) {
// 			className = (className === "dark" ? "":"dark");
// 			showLog("[ "+getTime()+" beforeEditName ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
// 			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
// 			zTree.selectNode(treeNode);
// 			return  confirm("进入节点 -- " + treeNode.name + " 的编辑状态吗？");
			
		}
		
		function beforeRemove(treeId, treeNode) {
			//我的思路是 在 beforeRemove 中 自己去ajax 让后台 remove。 
			//同时return false  然后在ajax 的success 中去利用 removeNode 方法删除节点
			
var treeid=treeNode.id;
	     
$.ajax({
    type: "get", 
    url: "${ctx}/mgr/weixin/deleteTreeNode?treeid="+treeid, 
    dataType: 'text',//返回的类型，对象的话改成 json即可
    contentType:"application/json;charset=UTF-8",
    success: function (result) {
    	//成功
    	
		//success执行以下
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		zTree.removeNode(treeNode);
		alert("删除成功");
	   
               },
     error: function(){
            	   alert('操作错误,请与系统管理员联系!');
            	   }
});
			
			
			

			
			return false;
		}
		
		
	
		function onRemove(e, treeId, treeNode) {
		  //删除节点后的操作   目前没发现用处 直接return false了

		}
		function beforeRename(treeId, treeNode, newName, isCancel) {
			className = (className === "dark" ? "":"dark");
			showLog((isCancel ? "<span style='color:red'>":"") + "[ "+getTime()+" beforeRename ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name + (isCancel ? "</span>":""));
			if (newName.length == 0) {
				alert("节点名称不能为空.");
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				setTimeout(function(){zTree.editName(treeNode)}, 10);
				return false;
			}
			return true;
		}
		function onRename(e, treeId, treeNode, isCancel) {
			//修改名称时候  发送ajax 到后台修改
		
			
		}
		function showRemoveBtn(treeId, treeNode) {
			return treeNode;
		}
		function showRenameBtn(treeId, treeNode) {
			return treeNode;
		}
		
		function showLog(str) {
			if (!log) log = $("#log");
			log.append("<li class='"+className+"'>"+str+"</li>");
			if(log.children("li").length > 8) {
				log.get(0).removeChild(log.children("li")[0]);
			}
		}
		function getTime() {
			var now= new Date(),
			h=now.getHours(),
			m=now.getMinutes(),
			s=now.getSeconds(),
			ms=now.getMilliseconds();
			return (h+":"+m+":"+s+ " " +ms);
		}

		var newCount = 1;
		function addHoverDom(treeId, treeNode) {
		
			var sObj = $("#" + treeNode.tId + "_span");
			if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
			var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
				+ "' title='add node' onfocus='this.blur();'></span>";
			sObj.after(addStr);
			var btn = $("#addBtn_"+treeNode.tId);
			if (btn) btn.bind("click", function(){  
				//点击添加 以后  触发事件
				
				if(treeNode.name=="微信主菜单入口"){
					
					//父级菜单的处理
					
					var zTree = $.fn.zTree.getZTreeObj("treeDemo");
					
					var kidid=100 + newCount;
					var kidpid=treeNode.id;
					var kidname="父级菜单" + (newCount++);
					var menutype="0";
					var menukey="请修改。。。";
					
			
	      	//把kidid  用ajax 发送到后台  保存 
 var datatoservice={"name":kidname,"treeid":kidid,"pId":kidpid,"menutype":menutype,"menukey":menukey}; 
	      	
$.ajax({
    type: "post", 
    url: "${ctx}/mgr/weixin/addTreeNode", 
    dataType: 'text',//返回的类型，对象的话改成 json即可
    data:JSON.stringify(datatoservice),
    contentType:"application/json;charset=UTF-8",
    success: function (kidid) {
    	//成功
		zTree.addNodes(treeNode,{id:kidid, pId:kidpid, name:kidname,menutype:menutype,menukey:menukey});

		return false;
	   
               },
     error: function(){
            	   alert('操作错误,请与系统管理员联系!');
            	   }
});
					

		
			
				}else{
					
					//子菜单的处理
				
				
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				
				var kidid=100 + newCount;
				var kidpid=treeNode.id;
				var kidname="子集菜单" + (newCount++);
				var menutype="0";
				var menukey="请修改。。。";
				
		
      	//把kidid  用ajax 发送到后台  保存 
var datatoservice={"name":kidname,"treeid":kidid,"pId":kidpid,"menutype":menutype,"menukey":menukey}; 
      	
$.ajax({
type: "post", 
url: "${ctx}/mgr/weixin/addTreeNode", 
dataType: 'text',//返回的类型，对象的话改成 json即可
data:JSON.stringify(datatoservice),
contentType:"application/json;charset=UTF-8",
success: function (kidid) {
	//成功

	zTree.addNodes(treeNode,{id:kidid, pId:kidpid, name:kidname,menutype:menutype,menukey:menukey});

	return false;
   
           },
 error: function(){
        	   alert('操作错误,请与系统管理员联系!');
        	   }
});
				

		
				
				
				
				
				}
			});
		};
		

		
		function removeHoverDom(treeId, treeNode) {
		
			$("#addBtn_"+treeNode.tId).unbind().remove();
			
		};
		
		function selectAll() {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			zTree.setting.edit.editNameSelectAll =  $("#selectAll").attr("checked");
		}
		
		$(document).ready(function(){
			//默认进来读取setting  然后去async 异步加载 数据，然后回显
			$.fn.zTree.init($("#treeDemo"), setting);
		
		});
		
		
		//请求服务器的时候如果返回的结果是空的话 页面默认值
		function filter(treeId, parentNode, childNodes) {
			if(childNodes==""){	
				   return aNodes;
			}
      
         return   childNodes;

			
		}
		
		
	//修改节点！！！
		function    modifynode(){


			var menuid= $("#menuid").val();
			var menuname= $("#menuname").val();
			var menutype=  $("#menutype").val();
		
			
			var menuurl=$("#menuurl").val();
			var menuruleid=$("#menuruleid").val();
			
			var menukey;

			
			if(menutype=="1"){
				//click
				menukey=menuruleid;
			}else if(menutype=="2"){
				
				menukey=menuurl;
			}
			
			
			//发送ajax 请求 然后回显示
			
			
			
			//TODO
			var datatoservice={"name":menuname,"id":menuid,"menutype":menutype,"menukey":menukey}; 
	      	
			$.ajax({
			type: "post", 
			url: "${ctx}/mgr/weixin/modifyTreeNode", 
			dataType: 'json',//返回的类型，对象的话改成 json即可
			data:JSON.stringify(datatoservice),
			contentType:"application/json;charset=UTF-8",
			success: function (datanode) {
				//成功
				
				//TODO
				
				//成功后节点显示  修改完的name
			     var zTree = $.fn.zTree.getZTreeObj("treeDemo");		 
			     var node = zTree.getNodeByParam("id",datanode.id); 
			     var newname =datanode.name;
			     node.name=newname;     
			     zTree.updateNode(node);
			     
				
				

				return false;
			   
			           },
			 error: function(){
			        	   alert('操作错误,请与系统管理员联系!');
			        	   }
			});
			
		}
		
		
		
	</SCRIPT>
	<script type="text/javascript">
	$(function(){
		//
		$("#menutype").change(function(){
			
			var type=$("#menutype").val();
			$("#divrule").css("display","none");
			$("#divurl").css("display","none");
			
			if(type=="1"){
				//click   rule
				$("#divrule").css("display","");
				
			}else if(type=="2"){
				//view url
				$("#divurl").css("display","");
			}
		});
		
	})
	
	</script>
	<style type="text/css">
.ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
	</style>
</HEAD>

<BODY>
<div style="margin-top: 0px;">

	<div class="jumbotron" style="height: 180px; background: #333333;">
		<h2 style="color: white; font-size: 30px;margin-left: -50px">菜单增加</h2>
		<h6 style="color: windowframe;margin-left: -40px">增加微信菜单</h6>

	</div>

	<br></br>
</div>


	<div class="zTreeDemoBackground left"   style="float: left;width: 200px;" >
		<ul id="treeDemo" class="ztree"></ul>
	</div>
	<div class="jumbotron" style="background: rgba(160, 160, 156, 0.23);
	float: left; margin-top:0px;height: 350px;width: 900px">
	
	            <!-- 节点 id  隐藏-->
	            <input type="hidden"   id="menuid" value="jun"/>
	            
	            
	            <!-- 节点名称 -->
	            <label for="disabledTextInput">名称</label> 
				<input type="text" 		     id="menuname" 	class="form-control" placeholder=" 请输入对应的值"
					name="submenuname" style="width: 300px">
			<br/>
			   <!-- 节点类型 -->
				<label  style="float: left;font-size: large;"   >选择类型</label><br/><br/>
					 <div style="float: left;" >
				<select     id="menutype"  class="form-control"      name="populationType" style="width: 300px;">
					      
					             <option   value="0" >请选择</option> 
					       	      <option   value=1>用于点击的菜单选项-CLICK</option>
					       	      <option   value=2>用于连接跳转的菜单选项-VIEW</option>	       
				</select>
				
		        <br>
		           <!-- 节点 key url-->
		      <div   id="divurl"   style="display: none;">
				<label for="disabledTextInput">连接地址</label> 
				<input type="text" 		     id="menuurl" 	class="form-control" placeholder=" 请输入对应的值"
					name="urlname" style="width: 300px" value="http://">
		     </div>
				
			<div   id="divrule"     style="display: none;">
				<label for="disabledTextInput">选择规则</label>
				<select     id="menuruleid"  class="form-control"      name="ruleid" style="width: 300px;">
					      
					             <option   value="0" >请选择</option> 
		           	<c:forEach  items="${rules}" var="rule">
		                    	<option   value=${rule.id}>${rule.title}</option>					       	    
                    </c:forEach>       
				</select>
			</div>
				
					
			
				<br>
			  <a 	onclick="modifynode()"> <span class="label label-default">保存</span></a>
			  
			  
	</div>
	</div>
			<a    href="${ctx}/mgr/weixin/synchro"><button id="jun" type="button" class="btn btn-success"	>同步菜单到微信</button></a>
		
		   <button type="button" class="btn btn-warning"   style="margin-left: 50px" onclick="javascript:history.go(-1);">返回</button>
	
<script type="text/javascript">

</script>
</BODY>
</HTML>
 
  





