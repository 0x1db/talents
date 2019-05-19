angular.module('talents.on', [])
.controller('resourceCtrl',
    function ($scope, $rootScope, $http, mainUrl, $timeout) {

      //分页组件的参数
      $scope.pageParams = {
        maxSize: 5,
        totalItems: 0,
        currentPage: 1,
        itemsPerPage: 15
      }

      //获取功能列表参数
      $scope.resourceParams = {
        methods: '',
        comment: '',
        status: '',
        type: '',
        page: 0
      }

      //搜索功能
      $scope.getResources = function () {
        $scope.resourceParams.page = $scope.pageParams.currentPage - 1;
        console.log($scope.resourceParams);
        $http({
          method: 'get',
          url: mainUrl + '/v1/resource/getPages',
          params: $scope.resourceParams
        }).then(function (res) {
          console.log('获取功能列表', res.data.content);
          $scope.resources = res.data.data.content;
          $scope.pageParams.totalItems = res.data.data.totalElements;
          $scope.pageParams.itemsPerPage = res.data.data.size;
        }, function (err) {
          console.log(err);
          layer.msg(err.data.message);
        })
      }
      $scope.getResources();

      //启用、禁用功能
      $scope.notCompetence = function (id, flag, index) {
        if (flag) {
          layer.confirm('是否启用该功能?', {icon: 3, title: '提示'}, function (index) {
            disableOrUnDisable();
            layer.close(index);
          })
        } else {
          layer.confirm('是否禁用该功能?', {icon: 3, title: '提示'}, function (index) {
            disableOrUnDisable();
            layer.close(index);
          })
        }

        function disableOrUnDisable() {
          $http({
            method: 'patch',
            url: mainUrl + '/v1/resource/disableOrUnDisable',
            params: {
              id: id,
              flag: flag
            }
          }).then(function (res) {
            if (res.data.responseCode === '_200') {
              if (flag) {
                $scope.getCompetence();
                layer.msg('成功启用！', {
                  icon: 1,
                  time: 1000 //1秒关闭（如果不配置，默认是3秒）
                });
              } else {
                $scope.getCompetence();
                layer.msg('已禁用！', {
                  icon: 0,
                  time: 1000 //1秒关闭（如果不配置，默认是3秒）
                });
              }
            } else if (res.data.responseCode === '_501') {
              layer.msg(res.data.errorMsg);
            } else {
              layer.msg("操作失败！");
            }
          }, function (err) {
            console.log(err);
            layer.msg(err.data.message);
          })
        }
      }

      //获取功能详情
      $scope.getResourcesDetail = function (id) {
        $http({
          method: 'get',
          url: mainUrl + '/v1/resources/' + id
        }).then(function (res) {
          console.log(res);
        }, function (err) {
          console.log(err);
          layer.msg(err.data.message);
        })
      }

      //重置查询用户参数
      $scope.resetParams = function () {
        $scope.resourceParams = {
          methods: '',
          comment: '',
          status: '',
          type: '',
        }
      }
    });