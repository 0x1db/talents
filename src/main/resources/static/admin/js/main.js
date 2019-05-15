angular.module('talents',
    ['ngAnimate', 'ui.router', 'oc.lazyLoad', 'talents.serve', 'ui.bootstrap',
      'ngMessages', 'ngFileUpload', 'ui.select2', 'ngCookies'])
//懒加载配置
.config(['$ocLazyLoadProvider', function ($ocLazyLoadProvider) {
  $ocLazyLoadProvider.config({
    debug: false,
    event: false
  });
}])
.factory(
    'permissions',
    function ($rootScope, $window) {
      var permissionList = [];
      var nopermissionList = [];
      var permissionUsers = [];
      return {
        restPermissionList: function () {
          permissionList = [];
          nopermissionList = [];
        },
        setPermissions: function (key, permission) {

          if (key == 'userPermissions') {
            permissionList.push(permission);
            $window.sessionStorage[key] = permissionList;
          } else {
            nopermissionList.push(permission);
            $window.sessionStorage[key] = nopermissionList;
          }

          $rootScope.$broadcast('permissionsChanged')
        },
        hasPermission: function (key, permission) {
          if (($window.sessionStorage[key]) != ""
              && ($window.sessionStorage[key]) != null
              & ($window.sessionStorage[key]) != undefined) {
            permissionUsers = ($window.sessionStorage[key])
            .split(",");
          }
          if (permissionUsers.length == 0) {
            return false;
          }
          return $.inArray(permission, permissionUsers) > -1 ? 1
              : 0;
        }
      }
    })
.config(function ($httpProvider) {
  $httpProvider.interceptors.push('UserInterceptor');
})
.run(function ($rootScope, $urlRouter, $state, $cookies, getUserRole) {
  /*监听自定义服务*/
  $rootScope.$on('userIntercepted', function (errorType) {
    // 跳转到登录界面，这里记录了一个from，这样可以在登录后自动跳转到未登录之前的那个界面
    $cookies.remove('USERNAME');
    $state.go('login');
  });
  //获取当前地址栏的url
  $rootScope.$on('$locationChangeSuccess', function (evt) {
    if (!$cookies.get('USERNAME')) {
      evt.preventDefault();//取消默认跳转行为
      $state.go('login');
    }

    $rootScope.ch = window.location.href;
    $rootScope.USERNAME = $cookies.get('USERNAME');
  });

  $rootScope.returnPage = function () {
    window.history.go(-1);
  }
  getUserRole();
})

.directive('hasPermission', function (permissions, $http, $window) {
  return {
    link: function (scope, element, attrs) {
      if (!attrs.hasPermission) {
        return false;
      }
      var roleUrlStr = attrs.hasPermission.split('|');       //权限集合，用'|'分割的权限串
      var index = roleUrlStr.length;
      var SSRoles = $window.sessionStorage['userPermissions'];        //该用户拥有的权限

      if (index > 1) {
        var isRole = 0;     //用于记录未拥有权限的个数
        for (var i = 0; i < index; i++) {

          //判断已拥有的权限结合和未拥有的权限集合是否存在该权限串
          if (SSRoles && SSRoles.indexOf(roleUrlStr[i]) > -1) {
            element.show();
            return false;
          } else {
            isRole = isRole + 1;
          }
        }
        if (isRole) {
          element.hide();
        } else {
          element.show();
        }
      } else {
        if (SSRoles && SSRoles.indexOf(roleUrlStr[0]) > -1) {
          element.show();
        } else {
          element.hide();
        }
      }

    }
  };
})
