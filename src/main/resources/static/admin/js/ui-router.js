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
  .state('me.user', {
    url: 'systemS/user',
    views: {
      'contents@me': {
        templateUrl: 'views/user/user.html',
        resolve: {
          loadPlugin: ['$ocLazyLoad', function ($ocLazyLoad) {
            return $ocLazyLoad
            .load(['views/user/user.js', 'views/user/user.css'])
          }]
        },
        controller: 'userCtrl'
      }
    }
  })
  .state('me.addUser', {
    url: 'systemS/user/addUser',
    views: {
      'contents@me': {
        templateUrl: 'views/user/addUser.html',
        resolve: {
          loadPlugin: ['$ocLazyLoad', function ($ocLazyLoad) {
            return $ocLazyLoad
            .load(['views/user/addUser.js', 'views/user/addUser.css'])
          }]
        },
        controller: 'addUserCtrl'
      }
    }
  })
  .state('me.editUser', {
    url: 'systemS/user/editUser/:userId',
    views: {
      'contents@me': {
        templateUrl: 'views/user/editUser.html',
        resolve: {
          loadPlugin: ['$ocLazyLoad', function ($ocLazyLoad) {
            return $ocLazyLoad
            .load(['views/user/editUser.js', 'views/user/editUser.css'])
          }]
        },
        controller: 'editUserCtrl'
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
  .state('me.addRole', {
    url: 'systemS/role/addRole',
    views: {
      'contents@me': {
        templateUrl: 'views/role/addRole.html',
        resolve: {
          loadPlugin: ['$ocLazyLoad', function ($ocLazyLoad) {
            return $ocLazyLoad
            .load(['views/role/addRole.js', 'views/role/addRole.css'])
          }]
        },
        controller: 'addRoleCtrl'
      }
    }
  })
  .state('me.editRole', {
    url: 'systemS/role/editRole/:roleId',
    views: {
      'contents@me': {
        templateUrl: 'views/role/editRole.html',
        resolve: {
          loadPlugin: ['$ocLazyLoad', function ($ocLazyLoad) {
            return $ocLazyLoad
            .load(['views/role/editRole.js', 'views/role/editRole.css'])
          }]
        },
        controller: 'editRoleCtrl'
      }
    }
  })
  .state('me.resource', {
    url: 'systemS/resource',
    views: {
      'contents@me': {
        templateUrl: 'views/resource/resource.html',
        resolve: {
          loadPlugin: ['$ocLazyLoad', function ($ocLazyLoad) {
            return $ocLazyLoad
            .load(['views/resource/resource.js', 'views/resource/resource.css'])
          }]
        },
        controller: 'resourceCtrl'
      }
    }
  })
  .state('me.addResource', {
    url: 'systemS/resource/addResource',
    views: {
      'contents@me': {
        templateUrl: 'views/resource/addResource.html',
        resolve: {
          loadPlugin: ['$ocLazyLoad', function ($ocLazyLoad) {
            return $ocLazyLoad
            .load(['views/resource/addResource.js',
              'views/resource/addResource.css'])
          }]
        },
        controller: 'addResourceCtrl'
      }
    }
  })

  $urlRouterProvider.otherwise("/login")
});
