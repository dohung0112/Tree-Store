package com.datamining.servide.impl;

import java.util.List;

import com.datamining.dao.OrderStatusDAO;
import com.datamining.entity.OrderStatus;
import com.datamining.service.OrderStatusService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderStatusServiceImpl implements OrderStatusService {
	@Autowired
	OrderStatusDAO dao;
	
	public List<OrderStatus> findAll() {
		return dao.findAll();
	}
	
	public List<OrderStatus> findByFilter() {
		return dao.findByFilter();
	}
}
