angular.module('talents.agent', ['ui.bootstrap'])
.controller('userCtrl', function ($scope, $http, mainUrl, $state) {

  //分页组件的参数
  $scope.pageParams = {
    maxSize: 5,
    totalItems: 0,
    currentPage: 1,
    itemsPerPage: 15
  }

  //查询用户的参数
  $scope.getUserParams = {
    username: '',
    nickName: '',
    status: '',
    page: '',
    type: '1'//类型为1标识为后台用户
  }

  //重置查询用户参数
  $scope.resetParams = function () {
    $scope.getUserParams = {
      account: '',
      name: '',
      status: '',
      type: '1'
    }
  }

  //查询用户列表
  $scope.getUsers = function () {
    $scope.getUserParams.page = $scope.pageParams.currentPage - 1;
    console.log($scope.getUserParams);
    $http({
      method: 'get',
      url: mainUrl + '/v1/user/getPages',
      params: $scope.getUserParams
    }).then(function (res) {
      if (res.data.responseCode == '_200') {
        if (res.data.data != null) {
          $scope.users = res.data.data.content;
          $scope.pageParams.totalItems = res.data.data.totalElements;
          $scope.pageParams.itemsPerPage = res.data.data.size;
        }
      } else if (res.data.responseCode == '_1011') {
        layer.msg(res.data.errorMsg);
      } else if (res.data.responseCode == '_500') {
        layer.msg("服务器内部错误，请稍后重试");
      } else {
        layer.msg("未知错误");
      }
    }, function (err) {
      console.log(err);
      layer.msg(err.data.message);
    })
  }
  //调用一次获取用户
  $scope.getUsers();

  //启用、禁用用户
  $scope.notUser = function (userId, flag, index) {
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
        url: mainUrl + '/v1/user/disableOrUnDisable',
        params: {
          userId: userId,
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
            $scope.getUsers();
          }
        } else {
          if (res.status === 200) {
            layer.msg('禁用成功！', {
              icon: 0,
              time: 1000 //1秒关闭（如果不配置，默认是3秒）
            });
            $scope.getUsers();
          }
        }
      }, function (err) {
        console.log(err);
        layer.msg(err.data);
      })
    }
  }

  //重置密码
  $scope.resetPassword = function (agentId) {
    layer.confirm('是否重置密码?', {icon: 3, title: '提示'}, function (index) {
      $http({
        method: 'PATCH',
        url: mainUrl + '/v1/user/reset/' + agentId
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
      });
      layer.close(index);
    })
  }

  //删除用户（逻辑删除）
  $scope.deleteUser = function (userId) {
    layer.confirm('是否删除用户?', {icon: 3, title: '提示'}, function (index) {
      $http({
        method: 'DELETE',
        url: mainUrl + '/v1/user/' + userId
      }).then(function (res) {
        if (res.data.responseCode == '_200') {
          layer.close(index);
          layer.msg('删除成功');
          $scope.getUsers();
        }
      }, function (err) {
        layer.msg(err.data);
      });
      layer.close(index);
    })
  }
});