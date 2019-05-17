angular.module('talents.editUser', [])
.controller('editUserCtrl',
    function ($scope, $http, mainUrl, $state, $stateParams) {

      $scope.userId = $stateParams.userId;

      //修改用户信息时的参数
      $scope.userInfo = {
        id: $scope.userId,
        nickname: '',
        password: '',
      }

      console.log($scope.agentId);

      //获取用户信息
      $scope.getUserInfo = function () {
        $http({
          method: 'get',
          url: mainUrl + '/v1/user/' + $scope.userId
        }).then(function (res) {
          if (res.data.responseCode == '_200') {
            console.log('用户信息', res.data);
            var data = res.data.data;
            $scope.userInfo.nickname = data.nickname;
            console.log($scope.userInfo);
          }
        }, function (err) {
          layer.msg(err.data.message);
        })
      }
      //调用一次获取用户信息
      $scope.getUserInfo();

      $scope.submit = function () {
        $http({
          method: 'patch',
          url: mainUrl + '/v1/user/',
          data: $scope.userInfo
        }).then(function (res) {
          console.log(res);
          if (res.data.responseCode == '_200') {
            layer.msg('修改成功');
            $state.go('me.user');
          }
        }, function (err) {
          console.log(err);
          layer.msg(err.data);
        })
      }
    });