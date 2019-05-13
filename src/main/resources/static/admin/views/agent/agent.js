angular.module('talents.agent', ['ui.bootstrap'])
.controller('agentCtrl', function ($scope, $http, mainUrl, $state) {

  //分页组件的参数
  $scope.pageParams = {
    maxSize: 5,
    totalItems: 0,
    currentPage: 1,
    itemsPerPage: 15
  }

  //查询用户的参数
  $scope.getAgentsParams = {
    account: '',
    name: '',
    status: '',
    page: '',
    type: '1'//类型为1标识为后台用户
  }

  //代理商列表信息
  $scope.agents = [];

  //重置查询代理商参数
  $scope.resetParams = function () {
    $scope.getAgentsParams = {
      account: '',
      name: '',
      status: '',
      type: '1'
    }
  }

  //查询用户列表
  $scope.getAgents = function () {
    $scope.getAgentsParams.page = $scope.pageParams.currentPage - 1;
    console.log($scope.getAgentsParams);
    $http({
      method: 'get',
      url: mainUrl + '/v1/user/getByCondition',
      params: $scope.getAgentsParams
    }).then(function (res) {
      console.log(res);
      if (res.status === 200) {
        $scope.agents = res.data.data.content;
        $scope.pageParams.totalItems = res.data.data.totalElements;
        $scope.pageParams.itemsPerPage = res.data.data.size;
      }
    }, function (err) {
      console.log(err);
      layer.msg(err.data.message);
    })
  }

  //调用一次获取用户
  // $scope.getAgents();

  //启用、禁用用户
  $scope.notAgent = function (agentId, flag, index) {
    if (flag) {
      layer.confirm('是否启用该用户?', {icon: 3, title: '提示'}, function (index) {
        disableOrUnDisable();
        layer.close(index);
      })
    } else {
      layer.confirm('是否禁用该用户?', {icon: 3, title: '提示'}, function (index) {
        disableOrUnDisable();
        layer.close(index);
      })
    }

    function disableOrUnDisable() {
      $http({
        method: 'post',
        url: mainUrl + '/v1/agents/disableOrUnDisable',
        params: {
          id: agentId,
          flag: flag
        }
      }).then(function (res) {
        console.log(res);
        console.log(flag);
        if (flag) {
          if (res.status === 200) {
            layer.msg('启用成功！', {
              icon: 1,
              time: 1000 //1秒关闭（如果不配置，默认是3秒）
            });
            $scope.getAgents();
          }
        } else {
          if (res.status === 200) {
            layer.msg('禁用成功！', {
              icon: 0,
              time: 1000 //1秒关闭（如果不配置，默认是3秒）
            });
            $scope.getAgents();
          }
        }
      }, function (err) {
        console.log(err);
        layer.msg(err.data);
      })
    }
  }

  $scope.resetPassword = function (agentId) {
    layer.confirm('是否重置密码?', {icon: 3, title: '提示'}, function (index) {
      $http({
        method: 'PATCH',
        url: mainUrl + '/v1/agents/reset/' + agentId
      }).then(function (res) {
        if (res.data.data != '' && res.data.data != null) {
          layer.confirm(res.data.data, {icon: 1, title: '该密码只显示一次'},
              function (index) {
                layer.close(index);
              })
        } else {
          layer.confirm("登录信息验证失败，请重新登录。", {icon: 2, title: '用户提示'},
              function (index) {
                layer.close(index);
                $state.go('loginOut', {flag: true});
              })
        }
      }, function (err) {
        console.log(err);
        layer.msg(err.data);
      })
      layer.close(index);
    })
  }
});