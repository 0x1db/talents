angular.module('talents.addUser', [])
.controller('addUserCtrl', function ($scope, $http, mainUrl, $state) {

  //用户数据对象
  $scope.userInfo = {
    nickname: '',
    username: '',
    password: ''
  }

  //注册用户
  $scope.registerUser = function () {
    console.log($scope.userInfo);
    $http({
      method: 'post',
      url: mainUrl + '/v1/user/register',
      data: $scope.userInfo
    }).then(function (res) {
      console.log(res);
      if (res.data.responseCode == '_200') {
        layer.msg('新增成功！');
        $state.go('me.user');
      } else {
        layer.msg('新增失败，请重试！');
      }
    }, function (err) {
      console.log(err);
      layer.msg(err.data.message)
    })
  }
}).directive('usernameValidate', function ($http, mainUrl) {
  return {
    require: 'ngModel',
    link: function (scope, elm, attrs, ctrl) {
      elm.bind('change', function () {
        if (scope.userInfo.username != null
            && scope.userInfo.username != '') {
          if (scope.userInfo.username.length >= 4
              && scope.userInfo.username.length <= 20) {
            $http({
              method: 'GET',
              url: mainUrl + '/v1/user/usernameValidateUnique/'
                  + scope.userInfo.username
            }).then(function (res) {
              console.log(res.data);
              if (res.data.data) {
                ctrl.$setValidity('username-validate', false);
              } else {
                ctrl.$setValidity('username-validate', true);
              }
            }, function (err) {
            });
          }
        }
      });
    }
  };
});