angular.module('ylmf.addAgent', [])
.controller('addAgentCtrl', function ($scope, $http, mainUrl, $state) {

  //用户数据对象
  $scope.agentInfo = {
    name: '',
    account: '',
    password: '',
    community: {
      id: ''
    }
  }

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

  //注册用户
  $scope.registerAgent = function () {
    console.log($scope.agentInfo)
    $http({
      method: 'post',
      url: mainUrl + '/v1/agents/register',
      data: $scope.agentInfo
    }).then(function (res) {
      console.log(res);
      if (res.data.responseCode == '_200') {
        layer.msg('新增成功！');
        $state.go('me.agent');
      } else {
        layer.msg('新增失败，请重试！');
      }
    }, function (err) {
      console.log(err);
      layer.msg(err.data.message)
    })
  }
}).directive('accountValidate', function ($http, mainUrl) {
  return {
    require: 'ngModel',
    link: function (scope, elm, attrs, ctrl) {
      elm.bind('change', function () {
        if (scope.agentInfo.account != null
            && scope.agentInfo.account != '') {
          if (scope.agentInfo.account.length >= 4
              && scope.agentInfo.account.length <= 20) {
            $http({
              method: 'GET',
              url: mainUrl + '/v1/agents/accountValidate/'
                  + scope.agentInfo.account
            }).then(function (res) {
              console.log(res.data);
              if (res.data.data) {
                ctrl.$setValidity('account-validate', false);
              } else {
                ctrl.$setValidity('account-validate', true);
              }
            }, function (err) {
            });
          }
        }
      });
    }
  };
});