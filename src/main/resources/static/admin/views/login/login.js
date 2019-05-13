angular.module('talents.login', ['talents.serve'])
.controller('loginCtrl',
    function ($scope, $rootScope, $http, mainUrl, $state, $stateParams,
        permissions, $window) {
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
          /*          console.log(res.data);
                    console.log(from)*/
          if (res.data.data == null || res.data.data == '') {
            layer.msg("用户名或密码错误,或者账号被禁用")
            return false;
          }
          //避免暴露用户信息，这里直接返回用户名
          if (res.data.data != null) {
            setCookie('USERNAME', res.data.data);
          }
          $state.go(from && from != "login" ? from : 'me.agent');
        }, function (err) {
          console.log(err);
          layer.msg('用户名或密码错误，或者账号被禁用');
        })
      };

      /*设置cookie*/
      function setCookie(c_name, value, expiredays) {
        var exdate = new Date()
        exdate.setDate(exdate.getDate() + expiredays)
        document.cookie = c_name + "=" + escape(value) +
            ((expiredays == null) ? "" : ";expires=" + exdate.toGMTString())
      };

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
        setCookie('USERNAME', '', -1);
        /*        $cookies.remove('UI', '', -1);
                $cookies.remove('UID', '', -1);*/
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
        if (getCookie('USERNAME')) {
          $state.go('me.role');
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
