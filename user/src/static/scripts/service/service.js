/**
 * Http interceptor listens to all server responses. If the response http code is 401, we'll
 * then redirect the current user to login page.
 * @param $q
 * @param $location
 * @returns {Function}
 */
function httpInterceptor($q, $location) {
    return {
        response: function (response) {
            // do something on success
            if (response.headers()['content-type'] === "application/json; charset=utf-8") {
                // Validate response, if not ok reject
                if (response.data.code !== 0) {
                    alert(response.data);
                    return $q.reject(response); // Wrong response data
                }
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
        login: function (token) {
            $http.defaults.headers.common['X-Access-Token'] = token;
        },
        logout: function () {
            delete $http.defaults.headers.common['X-Access-Token'];
        },
        getData: function (data) {
            return $http(data);
        }
    }
}

angular.module('modeApp')
    .factory('httpInterceptor', httpInterceptor)
    .factory('apiService', apiService);