var responseError = 0;
angular.module('demo')
    //this service will let the page turn to login page when the user send post request if he is
    // not login
.factory('errorInterceptor', ['$q', '$location',
function ($q, $location) {
    return {
        responseError: function (response) {
            if (response && response.status === responseError) {
                $location.path('/login');
            }
            return $q.reject(response);
        }
    };
}])

.factory('http',function($q,$http){
    //data是发送http请求所需要的header,在发送http请求时要先设置data,使用方法在GetApiCtrl中
    var service = {
        data:{},
        setData : function(data) {
            service.data = data;
        },
        getData : function() {
            var deferred = $q.defer();
            var promise = $http(this.data).then(function (response) {
              return response;
            }, function (response) {
              return response;
            });
            return promise;
        }
    };
    return service;
})

.factory('rememberMe',function($cookies){
    //check the user if select remember me
    var service = {
        data : {},
        setData : function(data) {
            service.data = data;
        },
        checkAutoLogin : function() {
            var check = document.getElementById("checkbox").checked;
            if (check == true) {
                var expireDate = new Date();
                expireDate.setDate(expireDate.getDate() + 30);
                $cookies.put('userId', this.data.loginUser.userId, {'expires': expireDate});
                $cookies.put('token', this.data.loginUser.token, {'expires': expireDate});
                $cookies.put('username', this.data.loginUser.username, {'expires': expireDate});
                $cookies.put('expires', this.data.loginUser.expires, {'expires': expireDate});
            } else {
                $cookies.put('userId', this.data.loginUser.userId);
                $cookies.put('username', this.data.loginUser.username);
                $cookies.put('token', this.data.loginUser.token);
                $cookies.put('expires', this.data.loginUser.expires);
            }
        }
    };
    return service;
});