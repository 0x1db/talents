angular.module('talents',
    ['ngAnimate', 'ui.router', 'oc.lazyLoad', 'talents.serve', 'ui.bootstrap',
      'ngMessages', 'ngFileUpload'])
//懒加载配置
.config(['$ocLazyLoadProvider', function ($ocLazyLoadProvider) {
  $ocLazyLoadProvider.config({
    debug: false,
    event: false
  });
}])
.config(function ($httpProvider) {
  $httpProvider.interceptors.push('UserInterceptor');
})
.run(function ($rootScope, $urlRouter, $state, getUserRole) {

  $rootScope.returnPage = function () {
    window.history.go(-1);
  }
  /*监听自定义服务*/
  $rootScope.$on('userIntercepted', function (errorType) {
    // 跳转到登录界面，这里记录了一个from，这样可以在登录后自动跳转到未登录之前的那个界面
    setCookie('USERNAME', '', -1);
//		  	$state.go('login',{from:$state.current.name,w:'seesionOut'});
    $state.go('login');
  });
  //获取当前地址栏的url
  $rootScope.$on('$locationChangeSuccess', function (evt) {
    if (!getCookie('USERNAME')) {
      evt.preventDefault();//取消默认跳转行为
      $state.go('login');
    }

    //console.log(window.location.href);
    $rootScope.ch = window.location.href;
    $rootScope.USERNAME = getCookie('USERNAME');
  });

  getUserRole();

  /*获取cookie*/
  function getCookie(c_name) {
    if (document.cookie.length > 0) {
      c_start = document.cookie.indexOf(c_name + "=")
      if (c_start != -1) {
        c_start = c_start + c_name.length + 1
        c_end = document.cookie.indexOf(";", c_start)
        if (c_end == -1) {
          c_end = document.cookie.length
        }
        return unescape(document.cookie.substring(c_start, c_end))
      }
    }
    return ""
  };

  /*设置cookie*/
  function setCookie(c_name, value, expiredays) {
    var exdate = new Date()
    exdate.setDate(exdate.getDate() + expiredays)
    document.cookie = c_name + "=" + escape(value) +
        ((expiredays == null) ? "" : ";expires=" + exdate.toGMTString())
  };
})
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
.directive('hasPermission', function (permissions, $http, $window, $cookies) {
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
            // isRole = true;
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
/*
.directive('hasPermission',
    function (permissions, $http, mainUrl, $window, $rootScope) {
      return {
        link: function (scope, element, attrs) {
          var value = attrs.hasPermission.trim();
          valueArr = value.split('&');
          if (!($window.sessionStorage['userPermissions'] == undefined
              || $window.sessionStorage['userPermissions'] == null)) {
            if (permissions.hasPermission('userPermissions', value)) {//判断是否已经存在
              element.show();
              return;
            }
          }
          if (!($window.sessionStorage['nouserPermissions'] == undefined
              || $window.sessionStorage['nouserPermissions'] == null)) {
            if (permissions.hasPermission('nouserPermissions', value)) {//判断是否已经存在
              element.hide();
              return;
            }
          }

          /!*获取当前登录用户权限------*!/
          $http({
            url: mainUrl + '/v1/menu/findCompetencePermissionByUrl',
            method: 'GET',
            params: {url: valueArr[0], methods: valueArr[1]}
          }).then(function (res) {
            if (res.data.data == true) {
              element.show();
              if ($window.sessionStorage['userPermissions'] == undefined
                  || $window.sessionStorage['userPermissions'] == null) {
                permissions.setPermissions('userPermissions', value);
              } else if (permissions.hasPermission('userPermissions', value)) {//判断是否已经存在
                return;
              } else {
                permissions.setPermissions('userPermissions', value);
              }

            } else {
              element.hide();
              if ($window.sessionStorage['nouserPermissions'] == undefined
                  || $window.sessionStorage['nouserPermissions'] == null) {
                permissions.setPermissions('nouserPermissions', value);
              } else if (permissions.hasPermission('nouserPermissions',
                  value)) {//判断是否已经存在
                return;
              } else {
                permissions.setPermissions('nouserPermissions', value);
              }
            }
          }, function (err) {
            console.log(err);
            layer.msg(err.data.message)
          })
        }
      }
    });*/
