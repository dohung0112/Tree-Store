package com.datamining.service;

import java.util.List;

import com.datamining.entity.OrderDetail;

public interface OrderDetailService {
	List<OrderDetail> findByOrderId(Integer id);
	
	OrderDetail create(OrderDetail orderDetail);
	
	void deleteList(List<OrderDetail> list);
}
