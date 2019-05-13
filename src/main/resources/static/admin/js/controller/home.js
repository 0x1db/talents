angular.module('talents.home', ['talents.serve'])
.controller('homeCtrl', function ($scope, $http, mainUrl) {
  console.log('home');
  $scope.registerAgent = function () {
    $http({})
  }
})