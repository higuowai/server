/**
 * LoginCtrl login controller
 */
function LoginCtrl($scope, $cookies, $state, apiService) {
    // Initialize and set default variables
    $scope.remember = true;

    $scope.doLogin = function () {
        // Create a request object for login
        var req = {
            method: "POST",
            url: API_URI_ENDPOINT + "/login",
            headers: {
                "login": $scope.login,
                "password": sha256_digest($scope.password)
            }
        };

        // Authenticate user and get an access token
        apiService.getData(req).then(function (response) {
            var token = response.data.payload;
            // check if remember me has been checked, if so, save the access token to cookie
            if ($scope.remember) {
                $cookies.put("X-Access-Token", token);
            }
            // supply that token as HTTP header parameter to all API calls that client issues.
            apiService.login(token);

            $state.go("home")
        });
    };
}

/**
 * LogoutCtrl - controller
 */
function LogoutCtrl($scope, $cookies, $state, apiService) {
    $scope.logout = function () {
        $cookies.remove("X-Access-Token");
        apiService.logout();
        $state.go("home");
    };
}

function HomeCtrl($scope, apiService) {

    // Initialize and set default variables
    $scope.profile = null;

    $scope.getProfile = function () {

        // Create a reqest for profile. We don't need to supply the access token any more.
        var req = {
            method: "GET",
            url: API_URI_ENDPOINT + "/me"
        };
        // Get the authenticated user's profile and then save it to cookie
        apiService.getData(req).then(function (response) {
            $scope.profile = response.data.payload;
        });
    };
}

angular
    .module('modeApp')
    .controller('LoginCtrl', LoginCtrl)
    .controller('LogoutCtrl', LogoutCtrl)
    .controller('HomeCtrl', HomeCtrl);
