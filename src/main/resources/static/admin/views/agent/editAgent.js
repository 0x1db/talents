angular.module('ylmf.editAgent', [])
.controller('editAgentCtrl',
    function ($scope, $http, mainUrl, $state, $stateParams) {

      $scope.agentId = $stateParams.agentId;

      //修改用户信息时的参数
      $scope.agentInfo = {
        id: $scope.agentId,
        name: '',
        password: '',
        community: {
          id: ''
        }
      }

      console.log($scope.agentId);

      //获取用户信息
      $scope.getAgentInfo = function () {
        $http({
          method: 'get',
          url: mainUrl + '/v1/agents/' + $scope.agentId + '/1'
        }).then(function (res) {
          console.log('用户信息', res.data);
          var data = res.data.data;
          $scope.agentInfo.name = data.name;
          if (data.community.id != '') {
            $scope.agentInfo.community.id = data.community.id;
          }
          console.log($scope.agentInfo);
        }, function (err) {
          layer.msg(err.data);
        })
      }
      //调用一次获取用户信息
      $scope.getAgentInfo();

      $scope.getCommunity = function () {
        $http({
          method: "GET",
          url: mainUrl + '/v1/community/getListTree',
        }).then(function (res) {
          console.log('获取所有小区', res.data.data);
          console.log(res);
          $scope.dataListCommunity = res.data.data;
        }, function (error) {
          layer.msg(error.data.message);
        });
      }
      //执行一次获取所有的小区
      $scope.getCommunity();

      $scope.submit = function () {

        $http({
          method: 'patch',
          url: mainUrl + '/v1/agents',
          data: $scope.agentInfo
        }).then(function (res) {
          console.log(res);
          if (res.status === 200) {
            layer.msg('修改成功');
            $state.go('me.agent');
          }
        }, function (err) {
          console.log(err);
          layer.msg(err.data);
        })
      }
    });