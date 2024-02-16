app.controller("dashboard-ctrl", function ($scope, $http) {
    $scope.topsale = [];
    $scope.productSold = {};

    $scope.initialize = function () {
        $http.get("/api/products/topsale").then(resp => {
            $scope.topsale = resp.data;
        })
        $http.get("/api/order/totalProductSold").then(resp => {
            $scope.productSold = resp.data;
        })
    }

    $scope.initialize();


})