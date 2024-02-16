package com.datamining.service;

import java.util.List;

import com.datamining.entity.OrderStatus;

public interface OrderStatusService {
	List<OrderStatus> findAll();
	
	List<OrderStatus> findByFilter();
}
