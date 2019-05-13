angular.module('talents')

//    路由
.config(function ($stateProvider, $urlRouterProvider) {
  $stateProvider
  .state('login', {
    url: '/login',
    templateUrl: 'views/login/login.html',
    resolve: {
      loadPlugIn: ['$ocLazyLoad', function ($ocLazyLoad) {
        return $ocLazyLoad
        .load(['views/login/login.css', 'views/login/login.js'])
      }]
    },
    controller: 'loginCtrl'
  })
  .state('loginOut', {
    url: '/login/:flag',
    templateUrl: 'views/login/login.html',
    resolve: {
      loadPlugIn: ['$ocLazyLoad', function ($ocLazyLoad) {
        return $ocLazyLoad
        .load(['views/login/login.css', 'views/login/login.js'])
      }]
    },
    controller: 'loginCtrl'
  })
  .state('me', {
    url: '/',
    views: {
      '': {
        templateUrl: 'views/framework.html'
      },
      'header@me': {
        templateUrl: 'views/header.html'
      },
      'nav@me': {
        templateUrl: 'views/nav.html',
        resolve: {
          loadPlugIn: ['$ocLazyLoad', function ($ocLazyLoad) {
            return $ocLazyLoad
            .load(['js/nav.js']/*, {insertBefore: '#mainJs'}*/)
          }]
        },
        controller: 'navCtrl'
      }
    }
  })
  .state('me.agent', {
    url: 'systemS/agent',
    views: {
      'contents@me': {
        templateUrl: 'views/agent/agent.html',
        resolve: {
          loadPlugin: ['$ocLazyLoad', function ($ocLazyLoad) {
            return $ocLazyLoad
            .load(['views/agent/agent.js', 'views/agent/agent.css'])
          }]
        },
        controller: 'agentCtrl'
      }
    }
  })
  .state('me.role', {
    url: 'systemS/role',
    views: {
      'contents@me': {
        templateUrl: 'views/role/role.html',
        resolve: {
          loadPlugin: ['$ocLazyLoad', function ($ocLazyLoad) {
            return $ocLazyLoad
            .load(['views/role/role.js', 'views/role/role.css'])
          }]
        },
        controller: 'roleCtrl'
      }
    }
  })
  $urlRouterProvider.otherwise("/login")
});
