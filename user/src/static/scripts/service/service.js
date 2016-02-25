/**
 * Http interceptor listens to all server responses. If the response http code is 401, we'll
 * then redirect the current user to login page.
 * @param $q
 * @param $location
 * @returns {Function}
 */
function httpInterceptor($q, $location) {
    return function (promise) {

        var success = function (response) {
            return response;
        };

        var error = function (response) {
            // Unauthorized API calls
            if (response.status === 401) {
                $location.path('/login');
            }
            // Wrong response data
            if (response.data.code) {
                console.log(response.data.message);
            }

            return $q.reject(response);
        };

        return promise.then(success, error);
    };
}

/**
 * Wrap up the $http service for all restful api calls, which returns a $promise that we can add
 * handlers with .then()
 * @param $http
 * @returns {{getData: Function}}
 */
function apiService($http) {
    return {
        init: function (token) {
            $http.defaults.headers.common['X-Access-Token'] = token;
        },
        off: function () {
            $http.defaults.headers.common['X-Access-Token'] = "";
        },
        getData: function (data) {
            return $http(data);
        }
    }
}

angular.module('modeApp')
    .factory('httpInterceptor', httpInterceptor)
    .factory('apiService', apiService);