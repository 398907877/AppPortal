<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>微信接入管理</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${ctx}/static/zTree//zTreeStyle.css"
	type="text/css">

<script type="text/javascript"
	src="${ctx}/static/zTree/jquery.ztree.core-3.5.js"></script>


<script src="${ctx}/static/layer1.7.0/layer.min.js"></script>
<script type="text/javascript"
	src="${ctx}/static/zTree/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript"
	src="${ctx}/static/zTree/jquery.ztree.exedit.js"></script>

<SCRIPT type="text/javascript">
	var setting = {

		async : {
			enable : true,
			url : "${ctx}/mgr/weixin/getTreeNode",
			autoParam : [ "id", "name", "menutype", "menukey" ],
			otherParam : {
				"otherParam" : "zTreeAsyncTest"
			},
			dataFilter : filter,

		},

		view : {
			addHoverDom : addHoverDom,
			removeHoverDom : removeHoverDom,
			selectedMulti : false
		},
		edit : {
			enable : true,
			editNameSelectAll : true,
			showRemoveBtn : showRemoveBtn,
			showRenameBtn : showRenameBtn
		},
		data : {
			simpleData : {
				enable : true,

			}
		},
		callback : {

			onAsyncSuccess : successa,

			beforeEditName : beforeEditName,
			beforeRemove : beforeRemove,
			beforeRename : beforeRename,
			onRemove : onRemove,
			onRename : onRename,
			beforeDrag : beforeDrag,
			beforeDrop : beforeDrop,
			beforeClick : beforeClick,
			onClick : onClick

		}
	};

	var aNodes = [

	{
		id : 0,
		pId : 0,
		name : "微信自定义菜单",
		isParent : true,
		open : false
	}, {
		id : 1,
		pId : 0,
		name : "子菜单1",
		open : true
	}, {
		id : 22,
		pId : 1,
		name : "子菜单1",
		menutype : "1",
		menukey : "jun"
	}, {
		id : 33,
		pId : 1,
		name : "子菜单2",
		menutype : "2",
		menukey : "http://www.baidu.com"
	},

	{
		id : 2,
		pId : 0,
		name : "子菜单1",
		open : true
	}, {
		id : 22,
		pId : 2,
		name : "子菜单1",
		menutype : "1",
		menukey : "jun"
	}, {
		id : 33,
		pId : 2,
		name : "子菜单2",
		menutype : "2",
		menukey : "http://www.baidu.com"
	},

	];

	function successa() {
		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		treeObj.expandAll(true);
	}

	//start  click
	function beforeClick(treeId, treeNode, clickFlag) {
		//点击前

	}
	function onClick(event, treeId, treeNode, clickFlag) {
		//点击的时候 显示 节点数据

		//清除上次的点击效果
		$("#typediv").css("display", "none");
		$("#divrule").css("display", "none");
		$("#divurl").css("display", "none");
		$("#savediv").css("display", "none");
		
		
		
		$("#typediv").css("display", "");
		$("#savediv").css("display", "");
		

		var key = treeNode.menukey;
		var type = treeNode.menutype;
		

		var menuid = treeNode.id;
		var menuname = treeNode.name;

		if (type == "1") {
			
			$("#divrule").css("display", "");

			$("#menuruleid").val(key);

		} else if (type == "2") {
		
			$("#divurl").css("display", "");
			$("#menuurl").val(key);
		}

		$("#menutype").val(type);
		$("#menukey").val(key);
		$("#menuid").val(menuid);

	}

	//end  click

	function beforeDrag(treeId, treeNodes) {

		return true;
	}

	function beforeDrop(treeId, treeNodes, targetNode, moveType) {

		if (moveType == "inner") {
			return false;
		} else {

			$.ajax({
				type : "GET",
				url : "${ctx}/mgr/weixin/drop?nodeId=" + treeNodes[0].id
						+ "&targetId=" + targetNode.id + "&position="
						+ moveType,
				dataType : 'text',//返回的类型，对象的话改成 json即可
				contentType : "application/json;charset=UTF-8",
				success : function(data) {
					//TODO 	

				},
				error : function() {
					alert('操作错误,请与系统管理员联系!');
				}
			});

		}

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

		var treeid = treeNode.id;
		
	    layer.confirm('确定删除吗？ 删除后将不能恢复', function(){
		    //删除成功
		    
			$.ajax({
				type : "get",
				url : "${ctx}/mgr/weixin/deleteTreeNode?treeid=" + treeid,
				dataType : 'text',//返回的类型，对象的话改成 json即可
				contentType : "application/json;charset=UTF-8",
				success : function(result) {
					//成功
		
				 //success执行以下
				  	var index	=layer.msg("删除成功",0,3);
					setTimeout(function() {
						layer.close(index);
		      		}, 500);
					var zTree = $.fn.zTree.getZTreeObj("treeDemo");
					zTree.removeNode(treeNode);

					
				
					

				},
				error : function() {
					alert('操作错误,请与系统管理员联系!');
				}
			});
		    
		});
		
		
		

		return false;
	}

	function onRemove(e, treeId, treeNode) {
		//删除节点后的操作   目前没发现用处 直接return false了

	}
	function beforeRename(treeId, treeNode, newName, isCancel) {
		className = (className === "dark" ? "" : "dark");
		showLog((isCancel ? "<span style='color:red'>" : "") + "[ " + getTime()
				+ " beforeRename ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name
				+ (isCancel ? "</span>" : ""));
		if (newName.length == 0) {
			alert("节点名称不能为空.");
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			setTimeout(function() {
				zTree.editName(treeNode)
			}, 10);
			return false;
		}

		return true;
	}
	function onRename(e, treeId, treeNode, isCancel) {
		//修改名称时候  发送ajax 到后台修改//TODO

		var nodename = treeNode.name;
		var nodeid = treeNode.id;

		$.ajax({
			type : "get",
			url : "${ctx}/mgr/weixin/noderename?nodename=" + nodename
					+ "&nodeid=" + nodeid + "",
			dataType : 'text',//返回的类型，对象的话改成 json即可
			contentType : "application/json;charset=UTF-8",
			success : function(data) {
				//成功

				var index = layer.msg('重命名成功', 0, 2);

				setTimeout(function() {
					layer.close(index);
				}, 500);

			},
			error : function() {
				alert('操作错误,请与系统管理员联系!');
			}
		});

	}
	function showRemoveBtn(treeId, treeNode) {
		return treeNode;
	}
	function showRenameBtn(treeId, treeNode) {
		return treeNode;
	}

	function showLog(str) {
		if (!log)
			log = $("#log");
		log.append("<li class='"+className+"'>" + str + "</li>");
		if (log.children("li").length > 8) {
			log.get(0).removeChild(log.children("li")[0]);
		}
	}
	function getTime() {
		var now = new Date(), h = now.getHours(), m = now.getMinutes(), s = now
				.getSeconds(), ms = now.getMilliseconds();
		return (h + ":" + m + ":" + s + " " + ms);
	}

	var newCount = 1;
	function addHoverDom(treeId, treeNode) {

		var sObj = $("#" + treeNode.tId + "_span");
		if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0)
			return;
		var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
				+ "' title='add node' onfocus='this.blur();'></span>";
		sObj.after(addStr);
		var btn = $("#addBtn_" + treeNode.tId);
		if (btn)
			btn.bind("click", function() {
				//点击添加 以后  触发事件

				if (treeNode.name == "微信主菜单入口") {

					//父级菜单的处理

					var zTree = $.fn.zTree.getZTreeObj("treeDemo");

					var kidid = 100 + newCount;
					var kidpid = treeNode.id;
					var kidname = "父级菜单" + (newCount++);
					var menutype = "0";
					var menukey = "请修改。。。";

					//把kidid  用ajax 发送到后台  保存 
					var datatoservice = {
						"name" : kidname,
						"treeid" : kidid,
						"pId" : kidpid,
						"menutype" : menutype,
						"menukey" : menukey
					};

					$.ajax({
						type : "post",
						url : "${ctx}/mgr/weixin/addTreeNode",
						dataType : 'text',//返回的类型，对象的话改成 json即可
						data : JSON.stringify(datatoservice),
						contentType : "application/json;charset=UTF-8",
						success : function(kidid) {
							//成功
							zTree.addNodes(treeNode, {
								id : kidid,
								pId : kidpid,
								name : kidname,
								menutype : menutype,
								menukey : menukey
							});

							return false;

						},
						error : function() {
							alert('操作错误,请与系统管理员联系!');
						}
					});

				} else {

					//子菜单的处理

					var zTree = $.fn.zTree.getZTreeObj("treeDemo");

					var kidid = 100 + newCount;
					var kidpid = treeNode.id;
					var kidname = "子集菜单" + (newCount++);
					var menutype = "0";
					var menukey = "请修改。。。";

					//把kidid  用ajax 发送到后台  保存 
					var datatoservice = {
						"name" : kidname,
						"treeid" : kidid,
						"pId" : kidpid,
						"menutype" : menutype,
						"menukey" : menukey
					};

					$.ajax({
						type : "post",
						url : "${ctx}/mgr/weixin/addTreeNode",
						dataType : 'text',//返回的类型，对象的话改成 json即可
						data : JSON.stringify(datatoservice),
						contentType : "application/json;charset=UTF-8",
						success : function(kidid) {
							//成功

							zTree.addNodes(treeNode, {
								id : kidid,
								pId : kidpid,
								name : kidname,
								menutype : menutype,
								menukey : menukey
							});

							return false;

						},
						error : function() {
							alert('操作错误,请与系统管理员联系!');
						}
					});

				}
			});
	};

	function removeHoverDom(treeId, treeNode) {

		$("#addBtn_" + treeNode.tId).unbind().remove();

	};

	function selectAll() {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		zTree.setting.edit.editNameSelectAll = $("#selectAll").attr("checked");
	}

	$(document).ready(function() {
		//默认进来读取setting  然后去async 异步加载 数据，然后回显
		$.fn.zTree.init($("#treeDemo"), setting);

	});

	//请求服务器的时候如果返回的结果是空的话 页面默认值
	function filter(treeId, parentNode, childNodes) {
		if (childNodes == "") {

			$("#createmenu").css("display", "");
			$("#menushow").css("display", "none");
			$("#menushowinfo").css("display", "none");

			return aNodes;

		}

		//TODO

		return childNodes;

	}

	//修改节点！！！
	function modifynode() {

		var menuid = $("#menuid").val();
		//		var menuname= $("#menuname").val();
		var menutype = $("#menutype").val();

		var menuurl = $("#menuurl").val();
		var menuruleid = $("#menuruleid").val();

		var menukey;

		if (menutype == "1") {
			//click
			menukey = menuruleid;
		} else if (menutype == "2") {

			menukey = menuurl;
		}

		//发送ajax 请求 然后回显示

		var datatoservice = {
			"id" : menuid,
			"menutype" : menutype,
			"menukey" : menukey
		};

		$.ajax({
			type : "post",
			url : "${ctx}/mgr/weixin/modifyTreeNode",
			dataType : 'json',//返回的类型，对象的话改成 json即可
			data : JSON.stringify(datatoservice),
			contentType : "application/json;charset=UTF-8",
			success : function(datanode) {
				//成功

				//成功后节点显示  修改完的name
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				var node = zTree.getNodeByParam("id", datanode.id);

				node.menukey = datanode.menukey;
				node.menutype = datanode.menutype;
				zTree.updateNode(node);

				//
				
				var index = layer.msg('修改成功', 0, 1);

				setTimeout(function() {
					layer.close(index);
				}, 500);

				return false;

			},
			error : function() {
				alert('操作错误,请与系统管理员联系!');
			}
		});

	}
</SCRIPT>

<script type="text/javascript">
	$(function() {
		
		
	

		//
		$("#menutype").change(function() {

			var type = $("#menutype").val();
			$("#divrule").css("display", "none");
			$("#divurl").css("display", "none");

			if (type == "1") {
				//click   rule
				$("#divrule").css("display", "");

			} else if (type == "2") {
				//view url
				$("#divurl").css("display", "");
				$("#menuurl").val("http://");

			}
		});
		
		
		
		

		
	
		
		
		
	})
</script>
<style type="text/css">
.ztree li span.button.add {
	margin-left: 2px;
	margin-right: -1px;
	background-position: -144px 0;
	vertical-align: top;
	*vertical-align: middle
}
table td,table th{text-align:center}
</style>
<script type="text/javascript">

//弹出框 选择链接

function  adddurl(){
	
	var  uurl = $('#InfoModal input:radio:checked').val(); 
	$("#closego").trigger("click");
	
	$("#menuurl").val(uurl);
	
}
</script>

</head>
<body>
	<br />
	<br />
	<div class="row">



		<div class="col-md-2">
			<%@ include file="nav.jsp"%>
		</div>


		<div class="col-md-10 ">
			<div class="bs-callout bs-callout-info">
				<h4>微信菜单管理</h4>
				<p>用于管理微信渠道的菜单管理。</p>

			</div>

			<br />


			<!--  还没创建菜单时候 ，显示的东西   开始-->

			<div id="createmenu" style="display: none">
				<p style="width: 300px;; margin-left: 210px;; margin-top: 100px"
					class="bg-success">
					您还未创建
					<kbd>菜单</kbd>
					， 点击下面
					<kbd>按钮</kbd>
					创建菜单

				</p>

				<a href="${ctx}/mgr/weixin/createmenuroot">
					<button style="margin-top: 50px; margin-left: 15px;" type="button"
						class="btn btn btn-info">单击以创建菜单</button>
				</a>



			</div>



			<!--  还没创建菜单时候 ，显示的东西  结束 -->


			<div id="menushow" class="row">

				<div class="col-md-3">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h3 class="panel-title">菜单管理</h3>
						</div>
						<div class="panel-body">

							<div class="zTreeDemoBackground left"
								style="float: left; width: 200px;">
								<ul id="treeDemo" class="ztree"></ul>
							</div>
						</div>
					</div>

				</div>


				<div class="col-md-9">

					<div class="panel panel-default">
						<div class="panel-heading">
							<h3 class="panel-title">设置动作</h3>
						</div>
						<div class="panel-body">

							<div
								style="float: left; margin-top: 0px; height: 260px; width: 600px">

								<!-- 节点 id  隐藏-->
								<input type="hidden" id="menuid" value="jun" />


								
								<div>
								
								
								<br />
								<div style="float: left;">
								      
								      <!-- 节点类型 -->
								     <div     id ="typediv" style="display: none;">
								    <label style="float: left; font-size: large;">选择类型</label><br />
									<select id="menutype" class="form-control"
										name="populationType" style="width: 300px;">

										<option value="0">请选择</option>
										<option value=1>用于点击的菜单选项-CLICK</option>
										<option value=2>用于连接跳转的菜单选项-VIEW</option>
									</select>
									</div>
									 <br>
									<!-- 节点 key url-->
									<div id="divurl" style="display: none;">
										<label for="disabledTextInput">连接地址</label> 
										
								   <div   data-toggle='modal' data-target='#InfoModal'    style="display: inline;">
									 <a > <span
										class="label label-default">选择模块</span></a>
								   </div>  
										
										
										
										<input type="text"
											id="menuurl" class="form-control" placeholder=" 请输入对应的值"
											name="urlname" style="width: 300px" value="http://">
											
									
						
											
											
									</div>

									<div id="divrule" style="display: none;">
										<label for="disabledTextInput">选择规则</label> <select
											id="menuruleid" class="form-control" name="ruleid"
											style="width: 300px;">

											<option value="0">请选择</option>
											<c:forEach items="${rules}" var="rule">
												<option value=${rule.id}>${rule.title}</option>
											</c:forEach>
										</select>
									</div>



									<br>
									<div    id="savediv" style="display: none;">
									 <a onclick="modifynode()"> <span
										class="label label-success">保存</span></a>
										  
							       </div>

								</div>
							
						</div>
					</div>




				</div>
			</div>








		</div>

	</div>
	
	
		</div>
		
		<div id="menushowinfo" style="margin-left: 17%;">
			<a href="${ctx}/mgr/weixin/synchro"><button id="jun"
					type="button" class="btn btn-default">发布</button></a>

			<button type="button" class="btn btn-default"
				style="margin-left: 50px" onclick="javascript:history.go(-1);">返回</button>
			<br /> <br /> <br />


			<c:choose>

				<c:when test="${not empty time}">
					<div>
						<strong>温馨提示：</strong> 您上次的同步时间是 &nbsp;
						<kbd>${time.menucreatetime }</kbd>
					</div>
				</c:when>

				<c:otherwise>
					<div>
						<strong style="color: #e15f63">温馨提示：</strong>
						您还没有同步过菜单，建议调整完以后同步哦！
					</div>
				</c:otherwise>
			</c:choose>

		</div>
		
		
		</div>
		
		
		<!--    弹出框    START -->





<div class="modal fade" id="InfoModal"      tabindex="-1" >
<div class="modal-dialog" style="width:800px;">
<div class="modal-content">
    <div class="modal-header">
    <button type="button" class="close"    id="closego" data-dismiss="modal" >&times;</button>
    <h4 class="modal-title" id="myModalLabel">添加信息</h4><span id="error" class="label label-danger"></span>
    </div>
    <div class="modal-body">
       
  
      <form class="navbar-form navbar-left" method="get" action="#">
        <div class="form-group">
          &nbsp;&nbsp;&nbsp;&nbsp;<label>模块名称：</label> 
          <input name="search_LIKE_word" value="" type="text" class="form-control" placeholder="输入查找的名称">
        </div>
         
        <button type="submit" class="btn btn-default">查找</button>
      </form>
      <br/><br/><br/>
      
      
      <table   style="width: 700px;" class="table table-hover table-bordered">
				<thead>
					<tr>
						<th  style="width: 70px;">
						请选择
						</th>
						<th>
						模块名称
						</th>


					</tr>
				</thead>
				<tbody>
				
				<c:forEach items="${res}"  var="oneres">
				<tr>
						<td>
						<input type="radio" name="optionsRadios"  value="${oneres.target}">
						</td>
						<td>
						  ${oneres.title}
						</td>



					</tr>
				
				</c:forEach>
					
					
					
				</tbody>
			</table>
			
			 <br/>
       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
       <ul class="pagination">
           <li><a href="#">&laquo;</a></li>
           <li class="active"><a href="#">1</a></li>

            <li><a href="#">&raquo;</a></li>
        </ul>
       
        
        
    </div>
    <div class="modal-footer">
    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
    <button type="button" class="btn btn-primary" onclick="adddurl()">提交数据</button>
    </div>
</div>
</div>
</div>  
<!--    弹出框    END -->
		
		
		
	
</body>
</html>