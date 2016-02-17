
/**
 * MainCtrl - controller
 */
function MainCtrl($scope,$cookies,$state) {
	$scope.logout = function() {
        $cookies.remove("token");
        $cookies.remove("expires");
        $state.go("login");
    }
};

/**
 * LoginCtrl login controller
 */
function LoginCtrl($scope,$cookies,$state,rememberMe,http) {
	$scope.token = $cookies.get('token');
    $scope.expires = $cookies.get('expires');
    var expireDate = new Date();

    //Automatic login when current time smaller than expires
    if($scope.token != null && $scope.expires != null){
        var myDate = new Date();
        var time=Date.parse(myDate);
        if(time < $scope.expires){
            $state.go("home.get",{}, {reload: true});
        }
    }
	$scope.loginAction = function() {
		$scope.loginPwd = $scope.loginPassword;
		$scope.loginPwd = sha256_digest($scope.loginPwd);
		var req = {
	        method : "POST",
	        url: 'http://localhost:8080/platform-2.0-merchant/v2/login',
	        headers:{
	            'username':$scope.loginName,
	            'password':$scope.loginPwd,
	        },
	    }
	    http.setData(req);
	    promise = http.getData().then(
	    	function(response){
		    	if(response.data.message.code == 401){
		            alert("username or password error!");
		        }
		        else if(response.data.message.code == 0){
		            $scope.payload = response.data.payload;
		            $scope.loginUser = $scope.payload.loginUser;

		            if($scope.loginUser.role != "MERCHANT"){
		                alert("your account role error");
		                return;
		            }
		            rememberMe.setData($scope)
					rememberMe.checkAutoLogin();
		            $state.go("home.get",{}, {reload: true});
		        }
	    	},function(reject){

	    	}
	    )
	}
}

/**
 *GetApiCtrl ,this controller will send a get http request
 */
function GetApiCtrl($scope,$cookies,http) {
	$scope.userId = $cookies.get("userId");
	$scope.token = $cookies.get("token");
	var req= {
        method:"GET",
        url:"http://localhost:8080/platform-2.0-merchant/v2/profiles/merchants/" + $scope.userId,
        headers: {
        	'X-Auth-Token': $scope.token,
        },
    }
    http.setData(req);
    promise = http.getData().then(
    	function(response){
    		alert("send get http success")
    	},function(reject){

    	})
}

/**
 *PostApiCtrl, this controller will send a post http request
 */
function PostApiCtrl($http,$scope,$cookies) {
	$scope.userId = $cookies.get("userId");
	$scope.token = $cookies.get("token");
	$scope.postRequest = function() {
		var req = {
			method:"POST",
	        url:"http://localhost:8080/platform-2.0-merchant/v2/changePassword/",
	        headers: {
	        	'X-Auth-Token': $scope.token,
	        	'userId':$scope.userId,
	        	'oldPassword':123,
	        	'newPassword':123,
	        },
		}
		$http(req)
		.success(function(response) {
			alert("send post http success")
		})
		.error(function(response,status) {

		})
	}
}

angular
    .module('demo')
    .controller('MainCtrl', MainCtrl)
    .controller('GetApiCtrl', GetApiCtrl)
    .controller('LoginCtrl', LoginCtrl)
    .controller('PostApiCtrl', PostApiCtrl)
    