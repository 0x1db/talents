angular.module('talents.addRole', [])
.controller('addRoleCtrl', function ($scope, $http, mainUrl, $state) {
  console.log('addRoleCtrl');

  //获取功能列表参数
  $scope.competenceParams = {
    method: '',
    comment: '',
    status: ''
  }

  //功能列表
  $scope.competences = '';

  //角色对象，新增角色时
  $scope.roleEntity = {
    "comment": "",
    "createDate": "",
    "modifyDate": "",
    "name": "",
    "status": "1"
  }

  //修改角色对象的creatDate
  $scope.modifyDate = function () {
    var date = new Date();
    $scope.creatDate = date;
  }

  //角色和功能绑定（一对多绑定）
  $scope.roleCompetences = {
    roleId: '',
    competenceIds: []
  }

  //对获取的功能进行分组
  $scope.getGroups = function (res) {
    var cache = {};
    $scope.groups = {};
    res.forEach(function (item, index) {
//        		console.log(index, item.resource.split('/'));
      var group = (item.resource.split('/'))[2];
      item.flag = group + 'FLAG';
//        		console.log('item', item, group);
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

  //获取所有的功能列表
  $scope.getCompetence = function () {
    $http({
      method: 'get',
      url: mainUrl + '/v1/competences/getByAllNoPage'
    }).then(function (res) {
      if (res.status === 200) {
        console.log('获取功能列表', res.data.data);
        $scope.competences = res.data.data;
        $scope.competences.forEach(function (item, index) {
          item.hasChecked = false;
        })
        $scope.getGroups($scope.competences);
      }
    }, function (err) {
      console.log(err);
      layer.msg(err.data.message);
    })
  }

  //调用一次获取所有的功能列表
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

  //添加一个角色信息
  $scope.registerRole = function () {
    $scope.modifyDate();
    console.log($scope.competences);
    $http({
      method: 'post',
      url: mainUrl + '/v1/roles',
      data: $scope.roleEntity
    }).then(function (res) {
      console.log(res);
      if (res.data.data != '' && res.data.data != null) {
        //添加一个角色成功的回调，把勾选的功能和角色绑定起来
        $scope.bindRoleForCompetences(res.data.data.id);
        // $state.go('me.role');
      } else {
        layer.confirm("登录信息验证失败，请重新登录。", {icon: 2, title: '用户提示'},
            function (index) {
              layer.close(index);
              $state.go('loginOut', {flag: true});
            })
      }
    }, function (err) {
      console.log(err);
      if (err.data) {
        layer.msg(err.data.message);
      }
    })
  }

  //形成角色和功能url的绑定关系（一次可以绑定多个url）
  $scope.bindRoleForCompetences = function (roleId) {
    $scope.roleCompetences.roleId = roleId;
    $scope.competences.forEach(function (item, index) {
      if (item.hasChecked) {
        $scope.roleCompetences.competenceIds.push(item.id)
      }
    })
    $http({
      method: 'post',
      url: mainUrl + '/v1/security/bindRoleForCompetences',
      params: $scope.roleCompetences
    }).then(function (res) {
      console.log(res);
      layer.msg('创建角色成功');
      $state.go('me.role')
    }, function (err) {
      console.log(err);
      layer.msg(err.data.message);
    })
  }

}).directive('roleNameValidate', function ($http, mainUrl) {
  return {
    require: 'ngModel',
    link: function (scope, elm, attrs, ctrl) {
      elm.bind('change', function () {
        if (scope.roleEntity.name != null && scope.roleEntity.name != '') {
          if (scope.roleEntity.name.length >= 4 && scope.roleEntity.name.length
              <= 20) {
            $http({
              method: 'GET',
              url: mainUrl + '/v1/roles/checkRoleName/' + scope.roleEntity.name
            }).then(function (res) {
              console.log(res.data);
              if (res.data.data) {
                ctrl.$setValidity('role-name-validate', false);
              } else {
                ctrl.$setValidity('role-name-validate', true);
              }
            }, function (err) {
            });
          }
        }
      });
    }
  };
})