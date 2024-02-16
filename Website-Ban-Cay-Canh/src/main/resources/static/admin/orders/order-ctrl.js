
app.controller("order-ctrl", function($scope, $http) {
    $scope.orderStatus = [];
    $scope.filterStatus = [];
    $scope.form = {};
    $scope.orders = [];
	$scope.orders_completed = [];
	$scope.orders_canceled = [];
	$scope.message = "";
	$scope.status = "";
	$scope.orderCanceled = false;
	
	
    $scope.initialize = function() {
        $http.get("/api/order").then(resp =>{
			$scope.orders = resp.data.data;
		})
		
		$http.get("/api/order/completed").then(resp =>{
			$scope.orders_completed = resp.data.data;
		})
		
		$http.get("/api/order/canceled").then(resp =>{
			$scope.orders_canceled = resp.data.data;
		})
    }
    
    $scope.orderStatus = function() {
		$http.get("/api/orderStatus").then(resp =>{
			$scope.orderStatus = resp.data.data;
		})
		
		$http.get("/api/orderStatus/filter").then(resp =>{
			$scope.filterStatus = resp.data.data;
		})
	}

    $scope.initialize();
    $scope.orderStatus();
    
    $scope.edit = function (order) {
        $scope.form = angular.copy(order);
        $scope.form.totalPrice = $scope.totalPriceProduct($scope.form);
        $scope.orderCanceled = $scope.form.status.id == 6 ? true : false;
        $(".nav-tabs a:eq(3)").trigger('click');
    }
    
    $scope.orderFilter = function (orderStatus, orderDate) {
		if(orderStatus == 0 && orderDate == undefined) {
			$scope.initialize();
		}else if(orderDate == undefined){
			$http.get("/api/order/filterStatus?statusId="+orderStatus).then(resp =>{
				$scope.orders = resp.data.data;
			})
		}else if(orderStatus == 0 && orderDate != undefined){
			orderDate = formatDate(orderDate);
			$http.get("/api/order/filterDate?createDate="+orderDate).then(resp =>{
				$scope.orders = resp.data.data;
			})
		}else {
			orderDate = formatDate(orderDate);
			$http.get("/api/order/filter?statusId="+orderStatus+"&createDate="+orderDate).then(resp =>{
				$scope.orders = resp.data.data;
			})
		}
		$scope.pager1 = 0;
    }
    
    $scope.update = function () {
        var item = angular.copy($scope.form);
        $http.put(`/api/order/${item.id}`, item).then(resp => {
			$scope.initialize();
			$scope.message = "Cập nhật thành công";
			$scope.status = "Success";
        }).catch(error => {
            $scope.message = "Cập nhật thất bại";
            $scope.status = "Warning";
            console.log("Error", error);
        })
        $scope.toats();
    }
    
    $scope.delete = function (order) {
        $http.delete(`/api/order/${order.id}`).then(resp => {
			$scope.initialize();
            $scope.message = "Xóa đơn hàng thành công";
            $scope.status = "Success";
            $(".nav-tabs a:eq(2)").trigger('click');
        }).catch(error => {
            $scope.message = "Đơn hàng đang liên kết khóa ngoại";
            $scope.status = "Warning";
            console.log("Error", error);
        })
        $scope.toats();
    }
    
    $scope.find = function () {
        var kw = document.getElementById("key__word").value;
        if (kw == "") {
            $scope.initialize();
        } else {
            $http.get(`/api/order/search/${kw}`).then(resp => {
                $scope.orders = resp.data.data;
                $scope.orders_completed = $scope.orders;
				$scope.orders_canceled = $scope.orders;
            })
        }
    }
    
    $scope.totalPriceProduct = function (form) {
		let totalPriceProduct = 0;
        for (let i = 0; i < form.details.length; i++) {
			totalPriceProduct += form.details[i].price * form.details[i].quantity;
		}
		return totalPriceProduct;
    }
    
	$scope.toats = function () {
            $('.toast').toast('show');
            setTimeout(function () {
            $('.toast').toast('hide');
         }, 2000);

    }

    $scope.hideToast = function () {
            $('.toast').toast('hide');
    }
    
    $scope.pager = {
		page1: 0,
		page2: 0,
		page3: 0,
		size: 7,
		get itemOrders(){
			var start = this.page1 * this.size;
			return $scope.orders.slice(start, start + this.size);
		},
		get countOrders()
		{
			return Math.ceil(1.0 * $scope.orders.length /this.size);
		},
		get itemCompleted(){
			var start = this.page2 * this.size;
			return $scope.orders_completed.slice(start, start + this.size);
		},
		get countCompleted()
		{
			return Math.ceil(1.0 * $scope.orders_completed.length /this.size);
		},
		get itemCanceled(){
			var start = this.page3 * this.size;
			return $scope.orders_canceled.slice(start, start + this.size);
		},
		get countCanceled()
		{
			return Math.ceil(1.0 * $scope.orders_canceled.length /this.size);
		},
		first1()
		{
			this.page1 = 0;
		},
		prev1()
		{
			this.page1--;
			if(this.page1 < 0)
			{
				this.last1()
			}
		},
		next1()
		{
			this.page1++;
			if(this.page1 >= this.countOrders)
			{
				this.first1();
			}
		},
		last1()
		{
			this.page1 = this.countOrders - 1;
		},
		first2()
		{
			this.page2 = 0;
		},
		prev2()
		{
			this.page2--;
			if(this.page2 < 0)
			{
				this.last2()
			}
		},
		next2()
		{
			this.page2++;
			if(this.page2 >= this.countCompleted)
			{
				this.first2();
			}
		},
		last2()
		{
			this.page2 = this.countCompleted - 1;
		},
		first3()
		{
			this.page3 = 0;
		},
		prev3()
		{
			this.page3--;
			if(this.page3 < 0)
			{
				this.last3()
			}
		},
		next3()
		{
			this.page3++;
			if(this.page3 >= this.countCanceled)
			{
				this.first3();
			}
		},
		last3()
		{
			this.page3 = this.countCanceled - 1;
		}
	}

})
function formatDate(date) {
  return date.toLocaleDateString('en-CA'); // yyyy-MM-dd
}
