/**
 * 
 */

		var app = angular.module("app", []);
		app.controller("NavController", function($scope) {
		
			$scope.userid =sessionStorage.getItem("userid");
			$scope.fname =sessionStorage.getItem("fname");
			$scope.role =sessionStorage.getItem("role").toUpperCase();
			
			
			$scope.IsDoctor = function(){
				
				return $scope.role == "DOCTOR";
			}

		});