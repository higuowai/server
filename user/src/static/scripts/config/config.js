/**
 * Declare constants here.
 */
var API_URI_ENDPOINT = "http://localhost:8080/server-1.0-user";

/**
 * Configure the views and routes based on the UI Router, so that you can change the parts of
 * your site using your routing even if the URL does not change.
 * @param $stateProvider
 * @param $urlRouterProvider
 * @param $httpProvider
 */
function routeConfig($stateProvider, $urlRouterProvider, $httpProvider) {

    /**
     * In case of any API call returns 401 we have to redirect user to login page.
     */
    $httpProvider.interceptors.push('httpInterceptor');

    /**
     * Default all visits to the home page.
     */
    $urlRouterProvider.otherwise("/home");

    $stateProvider
        .state('login', {
            url: "/login",
            templateUrl: "views/login.html",
        })
        .state('index', {
            url: "/index",
            templateUrl: "views/common/content.html",
        })
        .state('index.home', {
            url: "/home",
            templateUrl: "views/home/home.html",
        })
        .state('profile', {
            url: "/profile",
            templateUrl: "views/home/home.html",
        });
}

/**
 * Once the module is registered using the angular.module('app', []); setter, we can get its
 * instance using the angular.module('app'); getter syntax.
 */
angular.module('modeApp')
    .config(routeConfig);