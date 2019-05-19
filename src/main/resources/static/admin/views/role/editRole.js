angular.module('talents', [])
.controller('editRoleCtrl',
    function ($scope, $stateParams, $http, mainUrl, $state, $timeout) {

      //以传入的参数查询信息
      $scope.roleId = $stateParams.roleId;
      //获取功能列表参数
      $scope.competenceParams = {
        method: '',
        comment: '',
        status: ''
      }
      //角色信息
      $scope.roleInfo = {};

      //角色绑定的功能列表。（修改之前）
      $scope.beforeModify = {};

      //角色绑定和解绑功能的参数
      $scope.roleCompetencesBind = {
        roleId: $scope.roleId,
        competenceIds: []
      };
      $scope.roleCompetencesUnbind = {
        roleId: $scope.roleId,
        competenceIds: []
      };

      //查询一个指定的角色信息
      $scope.getRoleInfo = function (roleId) {
        $http({
          method: 'get',
          url: mainUrl + '/v1/role/' + roleId
        }).then(function (res) {
          console.log(res);
          $scope.roleInfo = res.data.data;
          $scope.comment = $scope.roleInfo.comment;
        }, function (err) {
          console.log(err);
          layer.msg(err.data);
        })
      }
      //调用一次查询一个指定的角色信息
      $scope.getRoleInfo($scope.roleId);

      //对获取的功能进行分组
      $scope.getGroups = function (res) {
        var cache = {};
        $scope.groups = {};
        res.forEach(function (item, index) {
          console.log(index, item.resource.split('/'));
          var group = (item.resource.split('/'))[2];
          item.flag = group + 'FLAG';
          console.log(group);
          if (!cache[group]) {
            $scope.groups[group] = {id: group, hasChecked: true};
            cache[group] = 1;
          }
          if (cache[group] && !item.hasChecked) {
            $scope.groups[group].hasChecked = false;
          }
        })
        console.log($scope.groups);
      }

      //查询指定角色已绑定的功能信息，无论这些功能是否可用
      $scope.findCompetencesByRoleId = function (roleId) {
        $http({
          method: 'get',
          url: mainUrl + '/v1/competences/findByRoleId/' + roleId
        }).then(function (res) {
          console.log('查询指定角色已绑定的功能信息', res.data);
          if (res.status === 200) {
            $scope.competences.forEach(function (x, i) {
              if (res.data != '') {
                res.data.data.forEach(function (item, index) {
                  if (item.id === x.id) {
                    x.hasChecked = true;
                    //将修改前的绑定的信息存起来
                    $scope.beforeModify[x.id] = x.id;
                  }
                })
              }
            })
          }
          $scope.getGroups($scope.competences);
        }, function (err) {
          console.error(err);
          layer.msg(err.data);
        })
      }

      //获取所有的功能
      $scope.getCompetence = function () {
        $http({
          method: 'get',
          url: mainUrl + '/v1/competences/getByAllNoPage'
        }).then(function (res) {
          console.log('获取功能列表', res.data);
          $scope.competences = res.data.data;
          $scope.competences.forEach(function (item, index) {
            item.hasChecked = false;
          })

          //调用一次查询指定角色已绑定的功能信息
          $scope.findCompetencesByRoleId($scope.roleId);
        }, function (err) {
          console.log(err);
          layer.msg(err.data);
        })
      }

      //调用一次获取所有的功能
      $scope.getCompetence();

      //全选和取消全选
      $scope.checkAll = function (resource, flag) {
        $scope.competences.forEach(function (item, index) {
          if ((item.resource.split('/'))[2] === resource) {
            item.hasChecked = flag;
          }
        })
      }

      //单击某个功能时
      $scope.singleClick = function (resource) {
        var resource = (resource.split('/'))[2];
        //初始化分组的组长（暂时这样么称呼）hasChecked为true，然后遍历所有功能，
        //分组下的某一个的hasChecked为false时，组长的hasChecked置为false
        $scope.groups[resource].hasChecked = true;
        $scope.competences.forEach(function (item, index) {
          if ((item.resource.split('/')[2]) === resource && item.hasChecked
              === false) {
            $scope.groups[resource].hasChecked = false;
          }
        })
      }

      //解除角色和功能的绑定（一次可以解绑多个功能）
      $scope.unbindRoleForCompetences = function (unbind) {
        $http({
          method: 'post',
          url: mainUrl + '/v1/security/unbindRoleForCompetences',
          params: unbind
        }).then(function (res) {
          console.log(res);
        }, function (err) {
          console.log(err);
          layer.msg(err.data);
        })
      }

      //形成角色和功能url的绑定关系（一次可以绑定多个url）
      $scope.bindRoleForCompetences = function (bind) {
        $http({
          method: 'post',
          url: mainUrl + '/v1/security/bindRoleForCompetences',
          params: bind
        }).then(function (res) {
          console.log(res);
        }, function (err) {
          console.log(err);
          layer.msg(err.data);
        })
      }

      //修改一个指定角色信息（comment）
      $scope.modifyRoleComment = function (roleId, comment) {
        $http({
          method: 'patch',
          url: mainUrl + '/v1/roles',
          data: {
            id: roleId,
            comment: comment
          }
        }).then(function (res) {
          console.log(res);
        }, function (err) {
          console.log(err);
          if (err.status === 403) {
            layer.msg('该角色不能修改描述信息。')
          }
        })
      }

      //修改角色信息，包括comment和绑定的功能
      $scope.modifyRole = function () {
        //清空绑定和解绑的参数
        $scope.roleCompetencesBind.competenceIds = [];
        $scope.roleCompetencesUnbind.competenceIds = [];

        //修改comment
        if ($scope.roleInfo.comment !== $scope.comment) {
          $scope.modifyRoleComment($scope.roleId, $scope.roleInfo.comment);
        }
        //遍历用户勾选和未勾选的功能分别放到不同的参数里面
        $scope.competences.forEach(function (item, index) {
          console.log(item.hasChecked);
          if (item.hasChecked) {
            if (!$scope.beforeModify[item.id]) {
              $scope.roleCompetencesBind.competenceIds.push(item.id);
            }
          } else {
            $scope.roleCompetencesUnbind.competenceIds.push(item.id);
          }
        })
        console.log($scope.roleCompetencesBind.competenceIds,
            $scope.roleCompetencesUnbind.competenceIds);
        if ($scope.roleCompetencesBind.competenceIds.length > 0) {
          $scope.bindRoleForCompetences($scope.roleCompetencesBind);
        }
        if ($scope.roleCompetencesUnbind.competenceIds.length > 0) {
          $scope.unbindRoleForCompetences($scope.roleCompetencesUnbind);
        }
        /*延迟返回避免数据还未修改成功，返回后显示旧数据*/
        $timeout(function () {
          $state.go('me.role');
          layer.msg('成功修改！');
        }, 500);
//        	$state.go('me.role');
//        	layer.msg('成功修改！');
      }

    });