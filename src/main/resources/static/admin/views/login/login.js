angular.module('talents.login', ['talents.serve'])
.controller('loginCtrl',
    function ($scope, $rootScope, $http, mainUrl, $state, $stateParams,
        permissions, $window, $cookies, getUserRole) {
      $scope.username = '';
      $scope.password = '';

      //登录
      $scope.login = function () {
        if ($scope.username === '' && $scope.password === '') {
          layer.msg('用户名和密码不能为空');
          return false;
        }
        if ($scope.username === '') {
          layer.msg('用户名不能为空');
          return false;
        }
        if ($scope.password === '') {
          layer.msg('密码不能为空');
          return false;
        }

        var username = $scope.username;
        var password = $scope.password;
        console.log($scope.username);
        var formData = new FormData();
        formData.append('username', username);
        formData.append('password', password);
        $http({
          method: 'post',
          url: mainUrl + '/login',
          headers: {'Content-Type': 'application/x-www-form-urlencoded'},
          data: {
            username: username,
            password: password
          },
          transformRequest: function (obj) {
            var str = [];
            for (var s in obj) {
              str.push(encodeURIComponent(s) + "=" + encodeURIComponent(
                  obj[s]));
            }
            return str.join("&");
          }
        }).then(function (res) {
          var from = $stateParams["from"];
          if (res.data.data == null || res.data.data == '') {
            layer.msg("用户名或密码错误,或者账号被禁用")
            return false;
          }
          //避免暴露用户信息，这里直接返回用户名
          if (res.data.data != null) {
            $cookies.put('USERNAME', res.data.data);
          }
          //获取权限列表
          getUserRole();
          $state.go(from && from != "login" ? from : 'me.agent');
        }, function (err) {
          console.log(err);
          layer.msg('用户名或密码错误，或者账号被禁用');
        })
      };

      /*登出*/
      $scope.loginOut = function () {

        $http({
          method: 'get',
          url: mainUrl + '/v1/security/logout'
        }).then(function (res) {

        }, function (err) {
          console.log(err);
        })
        /*清除用户名*/
        $cookies.remove('USERNAME');

        $window.sessionStorage.removeItem('userPermissions');
        $window.sessionStorage.removeItem('nouserPermissions');

        /*重置权限列表*/
        permissions.restPermissionList();
        $state.go('login');
      };

      /*登出处理*/
      if ($stateParams.flag) {
        $scope.loginOut();
      } else {
        if ($cookies.get('USERNAME')) {
          $state.go('me.agent');
        }
      }

      $scope.version = "ADMIN_VERSION";
      $scope.footer = "";
      /*      $scope.get = function () {
              $http({
                method: 'get',
                url: mainUrl + '/v1/coDict/get/' + $scope.version,
              }).then(function (res) {
                console.log('获取页脚信息', res.data.data);
                $scope.footer = res.data.data;
              }, function (err) {
                console.log(err);
                layer.msg(err.data.message);
              })
            }
            $scope.get();*/
    })

//登录的时候按回车就可以登录
.directive('ngEnter', function () {
  return function (scope, element, attrs) {
    element.bind("keydown keypress", function (event) {
      if (event.which === 13) {
        console.log('Enter');
        scope.$apply(function () {
          scope.$eval(attrs.ngEnter);
        });
        event.preventDefault();
      }
    });
  };
});
