angular.module('talents.on', ['ui.bootstrap'], ['ui.select2'])
.controller('roleCtrl',
    function ($scope, $rootScope, $http, mainUrl, $timeout) {

      //分页组件的参数
      $scope.pageParams = {
        maxSize: 5,
        totalItems: 0,
        currentPage: 1,
        itemsPerPage: 15
      }

      //条件分页查询角色参数
      $scope.getRolesParams = {
        roleName: '',
        status: '',
        page: ''
      }

      //角色的状态
      var roleStatus = {
        STATUS_NORMAL: 1,// "正常"
        STATUS_DISABLE: 2//"禁用"
      }

      //启用、禁用角色
      $scope.notRole = function (roleId, flag, index) {
        if (flag) {
          layer.confirm('是否启用该角色?', {icon: 3, title: '提示'}, function (index) {
            disableOrUnDisable();
            layer.close(index);
          })
        } else {
          layer.confirm('是否禁用该角色?', {icon: 3, title: '提示'}, function (index) {
            disableOrUnDisable();
            layer.close(index);
          })
        }

        function disableOrUnDisable() {
          $http({
            method: 'PATCH',
            url: mainUrl + '/v1/role/disableOrUnDisable',
            params: {
              roleId: roleId,
              flag: flag
            }
          }).then(function (res) {
            if (flag) {
              if (res.data.responseCode == '_200') {
                layer.msg('启用成功！', {
                  icon: 1,
                  time: 1000 //1秒关闭（如果不配置，默认是3秒）
                });
                $scope.getRoles();
              }
            } else {
              if (res.data.responseCode == '_200') {
                layer.msg('禁用成功！', {
                  icon: 0,
                  time: 1000 //1秒关闭（如果不配置，默认是3秒）
                });
                $scope.getRoles();
              }
            }
          }, function (err) {
            console.log(err);
            layer.msg(err.data);
          })
        }
      }

      //条件分页查询角色列表
      $scope.getRoles = function () {
        $scope.getRolesParams.page = $scope.pageParams.currentPage - 1;
        $http({
          method: 'get',
          url: mainUrl + '/v1/role/getPages',
          params: $scope.getRolesParams
        }).then(function (res) {
          console.log('条件分页查询', res);
          if (res.data.responseCode == '_200') {
            $scope.roles = res.data.data.content;
            $scope.pageParams.totalItems = res.data.data.totalElements;
            $scope.pageParams.itemsPerPage = res.data.data.size;
          }
        }, function (err) {
          console.log(err);
          layer.msg(err.data.message);
        })
      }

      //调用一次分页查询角色列表
      $scope.getRoles();

      /*重置搜索*/
      $scope.reset = function () {
        $scope.getRolesParams = {
          roleName: '',
          status: '',
          page: ''
        }
      }
    });