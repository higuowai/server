
function config($stateProvider, $urlRouterProvider,$httpProvider) {
    
    $httpProvider.interceptors.push('errorInterceptor');
    
    $urlRouterProvider.otherwise("/home/get");
    $stateProvider
        .state('login', {
            url: "/login",
            templateUrl: "views/login.html",
        })
        .state('home', {
            abstract: true,
            url: "/home",
            templateUrl: "views/common/content.html",
        })
        .state('home.get', {
            url: "/get",
            templateUrl: "views/get.html",
            data: { pageTitle: 'Example view' }
        })
        .state('home.post', {
            url: "/post",
            templateUrl: "views/post.html",
            data: { pageTitle: 'Example view' }
        })
}

app = angular.module('demo');
app.config(config);