//------------------------------------------------------------------------------------------------
//-bean ---------》method 
$(function(){
	$("#beansel").change(function(){
		var beanname=$("#beansel  option:selected").val();
		var bizcode=$("#beansel  option:selected").attr("class");
		//清除
		$("#methodsel option").remove()
		
		$.ajax({
			url: '${ctx}/mgr/weixin/keywords/beanname?beanname=' + beanname+'&bizcode='+bizcode, 
			type: 'GET',
			contentType: "application/json;charset=UTF-8",
			dataType: 'json',
			success: function(data){
				//把所有的方法传过来

				var zhlist=data.zhname;
				var enlist=data.enname;
				$.each( data,function(key,value){
					if(key=='zhname'){
						var zhlen=value.length;
						for(var i=0;i<zhlen;i++){
							$("#methodsel").append("<option  ></option> ");
							$("#methodsel option:eq("+i+")").attr("id",value[i]);
						}		
					};
					if(key=='enname'){
						var enlen=value.length;
						for(var i=0;i<enlen;i++){
							$("#methodsel option:eq("+i+")").html(value[i]);
						}
					};

				});	
			},error:function(xhr){
				alert('AJAX错误！！！！');
			}
		});
	});
});


//------------------------------------------------------------------------------------------------
//-method---------》paras
$(function(){
	$("#methodsel").change(function(){
		
		var beanname=$("#beansel  option:selected").val();
		var methodname=$("#methodsel  option:selected").attr("id");
		
		//---
$.ajax({
			url: '${ctx}/mgr/weixin/keywords/methodname?beanname=' + beanname+'&methodname='+methodname, 
			type: 'GET',
			contentType: "application/json;charset=UTF-8",
			dataType: 'json',
			success: function(data){
			$.each(data,function(key,value){
				
				alert(key+"key"+value+"value");
				
				
			});
				
			},error:function(xhr){
				alert('AJAX错误！！！！');
			}
		});
		
		
		//---
		
		
	});
	
});

