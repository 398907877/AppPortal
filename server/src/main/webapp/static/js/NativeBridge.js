
var NativeBridge = {
  callbacksCount : 1,
  callbacks : {},
  
  // Automatically called by native layer when a result is available
  resultForCallback : function resultForCallback(callbackId, resultArray) {
    try {
    var callback = NativeBridge.callbacks[callbackId];
    if (!callback) return;
    
    callback.apply(null,resultArray);
    } catch(e) {alert(e)}
  },
  
  // Use this in javascript to request native objective-c code
  // functionName : string (I think the name is explicit :p)
  // args : array of arguments
  // callback : function with n-arguments that is going to be called when the native code returned
  call : function call(functionName, args, callback) {
    
    var hasCallback = callback && typeof callback == "function";
    var callbackId = hasCallback ? NativeBridge.callbacksCount++ : 0;
    
    if (hasCallback)
      NativeBridge.callbacks[callbackId] = callback;
    
    var iframe = document.createElement("IFRAME");
    iframe.setAttribute("src", "js-frame:" + functionName + ":" + callbackId+ ":" + encodeURIComponent(JSON.stringify(args)));
    document.documentElement.appendChild(iframe);
    iframe.parentNode.removeChild(iframe);
    iframe = null;
    
    
  }

};


//消息提示
function alertMessage(device,message){
	var str =  '{"message":"'+message+'"}';
	if(device=='ios'){
		NativeBridge.call("alert",[message],function(data){
		});
	}else{
		AndroidBridge.alert(str);
	}
	
}

//模块跳转
function forward(device,modul){
	var str = '{"type":"'+modul+'"}';
	if(device=='ios'){
		NativeBridge.call("forward",[modul],function(){
			
		});
	}else{
		AndroidBridge.forward(str);
	}
}
//会员登录
function login(device,method){
	var memberId;
	 alertMessage(device,"请重新进入页面")
	if(device=='ios'){
		    NativeBridge.call("session",[],function(data){
		    alertMessage(device,"请重新进入页面")
		/*	var member=JSON.parse(data);
			memberId=member.memberId;
			method(memberId);*/
			});  
	}else{
		var data=AndroidBridge.session();
		 alertMessage(device,"请重新进入页面")
		/*var member=JSON.parse(data);
		memberId=member.memberId;
		method(memberId);*/
	}
	return memberId;
}
/*********台球会********/
function showImages(args){
	var device=navigator.userAgent;
	device=device.toLocaleLowerCase();
	if(device.indexOf("android") >=0 ){
		AndroidBridge.showImages(args);
	}else{
		NativeBridge.call("showImages",args,function(){
		});
	}
}

function tqhCheckLogin(method){	
	var device=navigator.userAgent;
	device=device.toLocaleLowerCase();
	if(device.indexOf("android") >=0 ){
		var data=AndroidBridge.checkLogin();
		if(data!='unLogin'){
			var member=JSON.parse(data);
		}
		method(member);
	}else{
		NativeBridge.call("checkLogin",[],function(data){
			if(data!='unLogin'){
				var member=JSON.parse(data);
			}
			method(member);
			});  
	}
}



/*返回手机app应用*/
function returnBack(method){
	var device=navigator.userAgent;
	device=device.toLocaleLowerCase();
	if(device.indexOf("android") >=0 ){
		AndroidBridge.returnBack();
	}else{
		NativeBridge.call("returnBack",[],function(data){
			});  
	}
}

function tqhLogin(method){
	var device=navigator.userAgent;
	device=device.toLocaleLowerCase();
	if(device.indexOf("android") >=0 ){
		var data=AndroidBridge.session();
		var member=JSON.parse(data);
		method(member);
	}else{
		NativeBridge.call("session",[],function(data){
			var member=JSON.parse(data);
			method(member);
			});  
	}
}

/*跳转到app特定模块
 * vip 会员卡模块
 * coupon 优惠券模块
 *   
 * */
function tqhForward(modul,id){
	var str = '{"type":"'+modul+'","id":"'+id+'"}';
	var device=navigator.userAgent;
	device=device.toLocaleLowerCase();
	if(device.indexOf("android") >=0 ){
		AndroidBridge.forward(str);
	}else{
		NativeBridge.call("forward",[modul,id],function(){
			
		});
	}
}

function tqhAlertMessage(message){
	var device=navigator.userAgent;
	device=device.toLocaleLowerCase();
	if(device.indexOf("android") >=0 ){
		AndroidBridge.alert(message);
	}else{
		NativeBridge.call("alert",[message],function(data){
		});
	}
}

function signin(device,method){
	var member;
	if(device=='ios'){
		NativeBridge.call("session",[],function(data){
			var member=JSON.parse(data);
			method(member);
		});  
	}else{
		var data=AndroidBridge.session();
		var member=JSON.parse(data);
		method(member);
	}
	return member;
}


