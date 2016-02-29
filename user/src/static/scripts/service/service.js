/**
 * Http interceptor listens to all server responses. If the response http code is 401, we'll
 * then redirect the current user to login page.
 * @param $q
 * @param $location
 * @returns {Function}
 */
function httpInterceptor($q, $location, $cookies) {
    return {
        request: function (request) {
            // do something on success
            if ($cookies.get("X-Access-Token")) {
                // if user logged in, supply that token as HTTP header parameter to all API
                // calls that client issues.
                request.headers['X-Access-Token'] = $cookies.get("X-Access-Token");
            }
            return request;
        },
        response: function (response) {
            // do something on success
            if (response.data.code) {
                // Validate response, if not ok reject
                alert(response.data);
                return $q.reject(response); // Wrong response data
            }
            return response;
        },
        responseError: function (response) {
            // do something on error
            if (response.status === 401) {
                // Unauthorized API calls, redirect to login page
                alert(response.data);
                $location.path('/login');
            }
            return $q.reject(response);
        }
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
        getData: function (data) {
            return $http(data);
        }
    }
}

angular.module('modeApp')
    .factory('httpInterceptor', httpInterceptor)
    .factory('apiService', apiService);