package com.datamining.rest;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import com.datamining.entity.Coupon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.datamining.DTO.AccountDTO;
import com.datamining.DTO.OrderDTO;
import com.datamining.entity.Order;
import com.datamining.entity.OrderDetail;
import com.datamining.service.OrderDetailService;
import com.datamining.service.OrderService;

import lombok.RequiredArgsConstructor;
import lombok.var;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderRest {
	@Autowired
	OrderService orderService;
	
	@Autowired
	OrderDetailService orderDetailService;

	@GetMapping()
	public ObjectResponse getAll() {
		var orders = orderService.findAll();
		var orderDTO = orders.stream().map(OrderDTO::convert).collect(Collectors.toList());
		return new ObjectResponse("ok", orderDTO, HttpStatus.OK.value());
	}

	@GetMapping("/completed")
	public ObjectResponse getAllByCompleted() {
		var orders = orderService.findAllByCompleted();
		var orderDTO = orders.stream().map(OrderDTO::convert).collect(Collectors.toList());
		return new ObjectResponse("ok", orderDTO, HttpStatus.OK.value());
	}

	@GetMapping("/canceled")
	public ObjectResponse getAllByCanceled() {
		var orders = orderService.findAllByCanceled();
		var orderDTO = orders.stream().map(OrderDTO::convert).collect(Collectors.toList());
		return new ObjectResponse("ok", orderDTO, HttpStatus.OK.value());
	}
	
	@GetMapping("/filter")
    public ObjectResponse orderFilter(@RequestParam("statusId") Integer orderStatusId, 
    												@RequestParam("createDate") String orderCreateDate) {
		var orders = orderService.findAllByFilter(orderStatusId, orderCreateDate);
		var orderDTO = orders.stream().map(OrderDTO::convert).collect(Collectors.toList());
		return new ObjectResponse("ok", orderDTO, HttpStatus.OK.value());
    }
	
	@GetMapping("/filterStatus")
    public ObjectResponse orderFilter(@RequestParam("statusId") Integer orderStatusId) {
		var orders = orderService.findAllByFilter(orderStatusId);
		var orderDTO = orders.stream().map(OrderDTO::convert).collect(Collectors.toList());
		return new ObjectResponse("ok", orderDTO, HttpStatus.OK.value());
    }
	
	@GetMapping("/filterDate")
    public ObjectResponse orderFilter(@RequestParam("createDate") String orderCreateDate) {
		var orders = orderService.findAllByFilter(orderCreateDate);
		var orderDTO = orders.stream().map(OrderDTO::convert).collect(Collectors.toList());
		return new ObjectResponse("ok", orderDTO, HttpStatus.OK.value());
    }

	@PostMapping
	public ObjectResponse create(@RequestBody Order order) {
		Order orderNew = orderService.create(order);
		for(int i = 0; i < order.getOderDetails().size(); i++) {
			OrderDetail orderDetailNew = new OrderDetail();
			orderDetailNew.setOrder(orderNew);
			orderDetailNew.setSale((float) 0);
			orderDetailNew.setPrice(order.getOderDetails().get(i).getPrice());
			orderDetailNew.setQuantity(order.getOderDetails().get(i).getQuantity());
			orderDetailNew.setProduct(order.getOderDetails().get(i).getProduct());
			orderDetailService.create(orderDetailNew);
		}
		return new ObjectResponse("success", "thêm thành công", HttpStatus.OK.value());
	}

    @PutMapping("/{id}")
    public ObjectResponse update(@PathVariable("id") Integer id, @RequestBody OrderDTO orderDTO) {
        var order = Order.convert(orderDTO);
        var newOrder = orderService.update(id, order);
        return new ObjectResponse("success", "update thành công", HttpStatus.OK.value());
    }
    
    @DeleteMapping("/{id}")
    public ObjectResponse delete(@PathVariable("id") Integer id) {
    	Order order = orderService.findById(id);
    	List<OrderDetail> orderDetails = orderDetailService.findByOrderId(id);
    	orderDetailService.deleteList(orderDetails);
    	order.setProfile(null);
    	order.setPayment(null);
    	order.setOderDetails(null);
    	order.setStatus(null);
		orderService.delete(id);
		return new ObjectResponse("ok", "xóa thành công", HttpStatus.OK.value());
    }
    
    @GetMapping("/search/{keyword}")
    public ObjectResponse search(@PathVariable("keyword") String keyword) {
        var orders = orderService.findByKeyWord(keyword);
        var orderDTO = orders.stream().map(OrderDTO::convert).collect(Collectors.toList());
        return new ObjectResponse("success", orderDTO, HttpStatus.OK.value());
    }
    
    @GetMapping("/{id}/detail")
    public ObjectResponse orderDetail_User(@PathVariable("id") Integer id) {
        var order = orderService.findById(id);
        var orderDTO = OrderDTO.convert(order);
        return new ObjectResponse("success", orderDTO, HttpStatus.OK.value());
    }
    
    @GetMapping("/{id}/user")
    public ObjectResponse orderUser(@PathVariable("id") Integer idProfile) {
		var orders = orderService.findByProfileId(idProfile);
		var orderDTO = orders.stream().map(OrderDTO::convert).collect(Collectors.toList());
		return new ObjectResponse("ok", orderDTO, HttpStatus.OK.value());
    }
    
    @GetMapping("/totalProductSold")
    public Integer getTotalProductSold() {
		return orderService.getTotalProductSold();
    }
}

