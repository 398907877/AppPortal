angular.module('weixin', []);

function WeixinController($scope) {
	$scope.config = {
			appid:"", 
			secret:"", 
			granttype:"client_credential",
			accesstoken:"",
			expiresin:""
	};
}