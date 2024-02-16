app.controller('order-ctrl', function($scope, $http, $location) {
	$scope.items = [];
	$scope.item = {};
    let user_id = null;
	
	$scope.initialize = function() {
		user_id = getUserId();
		$http.get(`/api/order/${user_id}/user`).then(resp => {
			$scope.items = resp.data.data;
		}).catch(error => {
			console.log("Error",error);
		});
	}
		
	$scope.orderDetail = function() {
		var order_id = window.location.href.split('=')[1]
		$http.get(`/api/order/${order_id}/detail`).then(resp => {
			$scope.item = resp.data.data;
			$scope.item.totalPrice = $scope.totalPriceProduct($scope.item);
		}).catch(error => {
			console.log("Error",error);
		});
	}
	
	if (url.match("/account/info") && getUserId()!=null) {
		$scope.initialize();
	}
	
	if (url.match("/account/order-detail") && getUserId()!=null) {
		$scope.orderDetail();
	}
	
	$scope.delete = function(productId) {
		$http.get(`/api/account/${user_id}/wishlist/delete?id=${productId}`).then(resp => {
			$scope.initialize();
			alert("Bạn vừa xóa một sản phẩm ra khỏi yêu thích!")
		}).catch(error => {
			console.log("Error",error);
		});
	}
	
	$scope.totalPriceProduct = function (item) {
		let totalPriceProduct = 0;
        for (let i = 0; i < item.details.length; i++) {
			totalPriceProduct += item.details[i].price * item.details[i].quantity;
		}
		return totalPriceProduct;
    }
	
	$scope.pager = {
		page: 0,
		size: 5,
		get items(){
			var start = this.page * this.size;
			return $scope.items.slice(start, start + this.size);
		},
		get count()
		{
			return Math.ceil(1.0 * $scope.items.length /this.size);
		},
		first()
		{
			this.page = 0;
		},
		prev()
		{
			this.page--;
			if(this.page < 0)
			{
				this.last()
			}
		},
		next()
		{
			this.page++;
			if(this.page >= this.count)
			{
				this.first();
			}
		},
		last()
		{
			this.page = this.count - 1;
		}
	}
});
function getUserId () {
	try {
		return document.getElementById('user_id').value;
	}catch {
		return null;
	}
}