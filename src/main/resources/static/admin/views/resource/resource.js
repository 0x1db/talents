angular.module('talents.on', ['ui.bootstrap'], ['ui.select2'])
.controller('resourceCtrl',
    function ($scope, $rootScope, $http, mainUrl, $timeout) {

      //分页组件的参数
      $scope.pageParams = {
        maxSize: 5,
        totalItems: 0,
        currentPage: 1,
        itemsPerPage: 15
      }

      //查询业主用户的参数
      $scope.getAgentsParams = {
        account: '',
        name: '',
        status: '1',//只查询未删除的用户，取消禁用之后改为删除。
        page: '',
        type: '0',//类型为0标识为手机业主用户
        portalId: '',
        towerId: '',
        communityId: '',
        elementId: ''
      }

      //业主用户列表信息
      $scope.agents = [];

      //重置查询业主参数
      $scope.resetParams = function () {
        $scope.getAgentsParams = {
          account: '',
          name: '',
          status: '1',
          type: '0',
          portalId: '',
          towerId: '',
          communityId: '',
          elementId: ''
        }

        //判断如果小区Id默认存在的就不重置。
        if (typeof ($rootScope.COMMUNITY) == 'undefined' || $rootScope.COMMUNITY
            == '') {
          //清除选中
          angular.element("#communityId").select2("val", "");
          $scope.getAgentsParams.communityId = '';
        }
        $scope.towerDataList = '';
        $scope.elementDataList = '';
        $scope.portalDataList = '';
      }

      //初始化页面时判断当前管理员权限，如果是物业管理员就存在小区ID，在用户搜索框显示默认小区，并禁用
      $scope.getAgentsParams.communityId = $rootScope.COMMUNITY;
      if (typeof ($scope.getAgentsParams.communityId) == 'undefined'
          || $scope.getAgentsParams.communityId
          == '') {
        $scope.getAgentsParams.communityId = ''
        $scope.flag = false;
      } else {
        $scope.flag = true;
      }
      console.log($scope.getAgentsParams.communityId);

      //查询用户列表
      $scope.getAgents = function () {
        $scope.getAgentsParams.page = $scope.pageParams.currentPage - 1;
        console.log($scope.getAgentsParams);
        $http({
          method: 'get',
          url: mainUrl + '/v1/agents/getByCondition',
          params: $scope.getAgentsParams
        }).then(function (res) {
          console.log(res);
          if (res.data.responseCode == '_200') {
            $scope.agents = res.data.data.content;
            $scope.pageParams.totalItems = res.data.data.totalElements;
            $scope.pageParams.itemsPerPage = res.data.data.size;
          } else if (res.data.responseCode == '_501') {
            layer.msg(res.data.errorMsg);
          }
        }, function (err) {
          console.log(err);
          layer.msg(err.data.message);
        })
      }

      //调用一次获取用户
      $scope.getAgents();

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
//	    					$scope.agents[index].status = 'STATUS_NORMAL';
                $scope.getAgents();
                layer.msg('成功启用！', {
                  icon: 1,
                  time: 1000 //1秒关闭（如果不配置，默认是3秒）
                });
              }
            } else {
              if (res.status === 200) {
//	    					$scope.agents[index].status = 'STATUS_DISABLE';
                $scope.getAgents();
                layer.msg('禁用成功！', {
                  icon: 0,
                  time: 1000 //1秒关闭（如果不配置，默认是3秒）
                });
              }
            }
          }, function (err) {
            console.log(err);
            layer.msg(err.data);
          })
        }
      }
      //审核业主信息
      $scope.notOwner = function (agentId, portalId, flag, index) {
        if (flag) {
          layer.confirm('是否确认审核通过?', {icon: 3, title: '提示'}, function (index) {
            approval();
            layer.close(index);
          })
        } else {
          layer.confirm('是否取消审核?', {icon: 3, title: '提示'}, function (index) {
            approval();
            layer.close(index);
          })
        }

        function approval() {
          $http({
            method: 'post',
            url: mainUrl + '/v1/agents/approval',
            params: {
              id: agentId,
              portalId: portalId,
              flag: flag
            }
          }).then(function (res) {
            console.log(res);
            console.log(flag);
            if (flag) {
              if (res.status === 200) {
//	    					$scope.agents[index].status = 'STATUS_NORMAL';
                $scope.getAgents();
                layer.msg('成功审核！', {
                  icon: 1,
                  time: 1000 //1秒关闭（如果不配置，默认是3秒）
                });
              }
            } else {
              if (res.status === 200) {
//	    					$scope.agents[index].status = 'STATUS_DISABLE';
                $scope.getAgents();
                layer.msg('取消审核！', {
                  icon: 0,
                  time: 1000 //1秒关闭（如果不配置，默认是3秒）
                });
              }
            }
          }, function (err) {
            console.log(err);
            layer.msg(err.data);
          })
        }
      }

      //删除用户
      $scope.deleteAgent = function (agentId, flag) {
        layer.confirm('是否确认删除?', {icon: 3, title: '提示'}, function (index) {
          deleteInfo(agentId, flag)
          layer.close(index);
        });

        function deleteInfo(agentId) {
          $http({
            method: 'delete',
            url: mainUrl + '/v1/agents/delete/' + agentId,
          }).then(function (res) {
            if (res.data.responseCode == '_200') {
              $scope.getAgents();
              layer.msg('删除成功！', {
                icon: 0,
                time: 1000 //1秒关闭（如果不配置，默认是3秒）
              });
            } else if (res.data.responseCode = "_501") {
              layer.msg(res.data.errorMsg);
            }
          }, function (err) {
            console.log(err);
            layer.msg(err.data);
          });
        }
      }

      //清除用户卡号
      $scope.deleteSNumber = function (agentId, flag) {
        layer.confirm('是否确认清除?', {icon: 3, title: '提示'}, function (index) {
          deleteSNumber(agentId, flag)
          layer.close(index);
        });

        function deleteSNumber(agentId) {
          $http({
            method: 'delete',
            url: mainUrl + '/v1/cardNumber/deleteCardNumber/' + agentId,
          }).then(function (res) {
            if (res.data.responseCode == '_200') {
              $scope.getAgents();
              layer.msg('清除成功！', {
                icon: 0,
                time: 1000 //1秒关闭（如果不配置，默认是3秒）
              });
            } else if (res.data.responseCode = "_501") {
              layer.msg(res.data.errorMsg);
            }
          }, function (err) {
            console.log(err);
            layer.msg(err.data);
          });
        }
      }

      //记录model变化
      $scope.fileChanged = function (ele) {
        $scope.files = ele.files;
        $scope.$apply(); //传播Model的变化。

        //准备上传文件
        var form = new FormData();
        var file = document.getElementById("uploadBtn").files[0];
        if (file == null) {
          layer.msg("请选择文件");
          return;
        }
        form.append('file', file);
        var index = layer.load(1, {
          shade: [0.1, '#fff'] //0.1透明度的白色背景
        });
        $http({
          method: 'POST',
          url: '/v1/agents/upload',
          data: form,
          headers: {'Content-Type': undefined}
        }).then(function (res) {
          if (res.data.responseCode == "_200") {
            $scope.dataList = res.data.data;
            $timeout(function () {
              console.log("timeout");
              if ($scope.dataList != null && $scope.dataList.length > 0) {
                console.log($scope.dataList);
                layer.close(index);
                layer.open({
                  type: 1,
                  skin: 'layui-layer-rim', //加上边框
                  area: ['420px', '240px'], //宽高
                  content: angular.element(".errorList").html()
                });
              } else {
                console.log('upload success');
                layer.close(index);
                layer.msg("上传成功");
                //刷新页面
                $scope.getAgents();
              }
              //清除已经选中的文件
              angular.element("input[type = file]")[0].value = ""
            }, 3000);
          } else if (res.data.responseCode = "_501") {
            layer.msg(res.data.errorMsg);
            layer.close(index);
          }
        }, function (err) {
          console.log(err);
        });
      }

      /*
     * select2 四级联动筛选查询
     */
      //获取小区列表
      $scope.getCommunity = function () {
        $http({
          method: "GET",
          url: mainUrl + '/v1/community/getListTree',
        }).then(function (res) {
          if (res.data.responseCode == '_200') {
            console.log('获取小区列表', res.data.data);
            $scope.communityDataList = res.data.data;
          } else if (res.data.responseCode == '_501') {
            layer.msg(res.data.errorMsg);
          }
        }, function (error) {
          layer.msg(error.data.message);
        });
      }
      $scope.getCommunity();

      //根据ID获取楼栋-单元-门户
      $scope.getHousingInfo = function (id, node) {
        if (node == 'tower') {
          $http({
            method: "GET",
            url: mainUrl + '/v1/tower/getListTree/' + id,
          }).then(function (res) {
            if (res.data.responseCode == '_200') {
              console.log('获取楼栋列表', res.data.data);
              $scope.towerDataList = res.data.data;
              //传入id调用方法，生成楼栋列表
            } else if (res.data.responseCode == '_501') {
              layer.msg(res.data.errorMsg);
            }
          }, function (error) {
            layer.msg(error.data.message);
          });
        }
        if (node == 'element') {
          $http({
            method: "GET",
            url: mainUrl + '/v1/element/getListTree/' + id,
          }).then(function (res) {
            if (res.data.responseCode == '_200') {
              console.log('获取单元列表', res.data.data);
              $scope.elementDataList = res.data.data;
              //传入id调用方法，生成楼栋列表
            } else if (res.data.responseCode == '_501') {
              layer.msg(res.data.errorMsg);
            }
          }, function (error) {
            layer.msg(error.data.message);
          });
        }
        if (node == 'portal') {
          $http({
            method: "GET",
            url: mainUrl + '/v1/portal/getListTree/' + id,
          }).then(function (res) {
            if (res.data.responseCode == '_200') {
              console.log('获取门户列表', res.data.data);
              $scope.portalDataList = res.data.data;
              //传入id调用方法，生成楼栋列表
            } else if (res.data.responseCode == '_501') {
              layer.msg(res.data.errorMsg);
            }
          }, function (error) {
            layer.msg(error.data.message);
          });
        }
      }

      //监听小区下拉菜单
      $scope.$watch("getAgentsParams.communityId", function () {
        //当小区Id发生变化时,清空楼栋-单元-门户下拉内容
        $scope.towerDataList = '';
        $scope.elementDataList = '';
        $scope.portalDataList = '';
        //调用楼栋查询
        console.log("小区Id：", $scope.getAgentsParams.communityId);
        if ($scope.getAgentsParams.communityId != ''
            && typeof ($scope.getAgentsParams.communityId)
            != 'undefined') {
          $scope.getHousingInfo($scope.getAgentsParams.communityId, "tower");
        }
      });
      //监听楼栋下拉菜单
      $scope.$watch("getAgentsParams.towerId", function () {
        $scope.elementDataList = '';
        $scope.portalDataList = '';
        //调用单元查询
        console.log("楼栋Id：", $scope.towerId);
        if ($scope.getAgentsParams.towerId != ''
            && typeof ($scope.getAgentsParams.towerId) != 'undefined') {
          $scope.getHousingInfo($scope.getAgentsParams.towerId, "element");
        }
      });
      //监听门户Id
      $scope.$watch("getAgentsParams.elementId", function () {
        $scope.portalDataList = '';
        console.log("单元Id：", $scope.getAgentsParams.elementId);
        if ($scope.getAgentsParams.elementId != null
            && typeof ($scope.getAgentsParams.elementId)
            != 'undefined') {
          $scope.getHousingInfo($scope.getAgentsParams.elementId, "portal");
        }
      })
    })
//过滤器
.filter('hideCard', function () {
  return function (text) {
    if (text == "" || text == null) {
      return "";
    } else {
      var length = text.length;
      var prefix = "";
      var suffix = "";
      switch (length) {
        case 15:
          subResult(12, 15);
          break;
        case 18:
          subResult(15, 18);
          break;
        default:
          break;
      }

      function subResult(i, k) {
        prefix = text.substring(0, 3);
        suffix = text.substring(i, k);
      }

      return prefix + "***" + suffix;
    }
  }
});