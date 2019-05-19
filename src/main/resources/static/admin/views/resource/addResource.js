angular.module('talents.addResource', ['ui.bootstrap'], ['ui.select2'])
.controller('addResourceCtrl', function ($scope, $http, mainUrl, $state) {
  //资源实体
  $scope.resourceEntity = {
    description: '',
    methods: '',
    resource: '',
    type: '',
    parent: {
      id: ''
    }
  }

  //新增资源
  $scope.registerResource = function () {
    $http({
      method: 'post',
      url: mainUrl + '/v1/resource/',
      data: $scope.resourceEntity
    }).then(function (res) {
      if (res.data.responseCode == '_200') {
        layer.msg('添加权限成功！');
        $state.go('me.resource');
      }
    }, function (err) {
      if (err.data) {
        layer.msg(err.data.message);
      }
      console.log(err);
    })
  }

  //当选择为类型为导航组件时触发
  $scope.changeType = function () {
    var type = $scope.resourceEntity.type;
    if (type == 'RESOURCE_MENU') {
      angular.element(".resourceMenu").addClass("hide")
      angular.element(".resourceComponent").removeClass("hide");
    } else if (type == 'RESOURCE_BUTTON') {
      angular.element(".resourceComponent").addClass("hide");
      angular.element(".resourceMenu").removeClass("hide");
    } else {
      $scope.resourceEntity.parent = null;
      angular.element(".resourceComponent").addClass("hide");
      angular.element(".resourceMenu").addClass("hide")
    }
  }

  //获取所有状态正常的父级组件资源
  $scope.getComponent = function () {
    $http({
      method: 'GET',
      url: mainUrl + '/v1/resource/findAll'
    }).then(function (res) {
      if (res.data.responseCode = '_200') {
        $scope.resourceComponents = [];
        $scope.resourceMenus = [];
        var data = res.data.data;
        if (data != '' || data != null) {
          data.forEach(function (item, index) {
            if (item.type == 'RESOURCE_COMPONENT') {
              $scope.resourceComponents.push(item);
            }
            if (item.type == 'RESOURCE_MENU') {
              $scope.resourceMenus.push(item);
            }
          });
        }
      } else {
        layer.msg(res.data.errorMsg);
      }
    }, function (error) {
      layer.msg(error.data);
    });
  }
  $scope.getComponent();

});