angular.module('ylmf.agentRole', [])
.controller('agentRoleCtrl',
    function ($scope, $http, mainUrl, $state, $stateParams) {

      //用户Id
      $scope.agentId = $stateParams.agentId;

      //用户信息
      $scope.agentInfo = {};

      //获取用户的信息
      $scope.getAgentInfo = function () {
        $http({
          method: 'get',
          url: mainUrl + '/v1/agents/' + $scope.agentId
        }).then(function (res) {
          console.log('用户信息', res.data);
          $scope.agentInfo = res.data.data;
          console.log($scope.agentInfo);
        }, function (err) {
          console.error(err.data.message)
        })
      }

      $scope.getAgentInfo();

      //用来存储所有角色信息
      $scope.roles = [];
      $scope.rolesCache = {};

      //获取所有角色信息
      $scope.getRoles = function () {

        $http({
          method: 'get',
          url: mainUrl + '/v1/roles/all'
        }).then(function (res) {
          console.log('所有的角色列表', res);
          var data = res.data.data;
          data.forEach(function (item, index) {
            if (item.id != '2bb' && item.name != 'ADMIN') {
              $scope.roles.push(item);
            }
          })
          $scope.getRoleForAgent();
        }, function (err) {
          console.log(err);
        })
      }

      //调用一次获取所有角色
      $scope.getRoles();

      //处理获取用户绑定的角色
      $scope.modifyRoleForAgent = function (data) {
        $scope.roles.forEach(function (Item, Index) {
          Item.hasChecked = false;
          if (data != '') {
            data.data.forEach(function (item, index) {
              if (item.id === Item.id) {
                console.log(Item.id);
                Item.hasChecked = true;
              }
            })
          }
          //把用户绑定的用户角色信息储存起来
          $scope.rolesCache[Item.id] = Item.hasChecked;
        })
      }

      //获取用户绑定的角色列表
      $scope.getRoleForAgent = function () {

        $http({
          method: 'get',
          url: mainUrl + '/v1/roles/getRolesByAgent',
          params: {
            'userId': $scope.agentId
          }
        }).then(function (res) {
          console.log('角色列表', res.data);
          $scope.modifyRoleForAgent(res.data);
          console.log('看看hasChecked', $scope.roles);
        }, function (err) {
          console.log(err);
          layer.msg(err.data);
        })
      }

      //用户与角色进行绑定（一个一个的绑定）
      $scope.bindRoleForAgent = function (roleId) {

        $http({
          method: 'post',
          url: mainUrl + '/v1/security/bindRoleForUser',
          params: {
            roleId: roleId,
            userId: $scope.agentId
          }
        }).then(function (res) {
          console.log('绑定成功', res);
        }, function (err) {
          console.log(err);
          layer.msg(err.data);
        })
      }

      //用户与角色解绑
      $scope.unbindRoleForAgent = function (roleId) {
        $http({
          method: 'post',
          url: mainUrl + '/v1/security/unbindRoleForUser',
          params: {
            roleId: roleId,
            userId: $scope.agentId
          }
        }).then(function (res) {
          console.log('解绑成功', roleId, res);
        }, function (err) {
          console.log('解绑失败', err);
          layer.msg(err.data);
        })
      }

      $scope.bind = function () {
        $scope.roles.forEach(function (item, index) {
          if ($scope.rolesCache[item.id] !== item.hasChecked) {
            if (item.hasChecked) {
              $scope.bindRoleForAgent(item.id);
            } else {
              $scope.unbindRoleForAgent(item.id);
            }
          }
        })
        layer.msg('操作成功');
        $state.go('me.agent');
      }
    })
