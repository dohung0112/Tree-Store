package com.datamining.servide.impl;

import java.util.List;

import com.datamining.dao.OrderDAO;
import com.datamining.dao.OrderDetailDAO;
import com.datamining.entity.OrderDetail;
import com.datamining.service.OrderDetailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {
	@Autowired
	OrderDetailDAO dao;
	
	@Override
	public List<OrderDetail> findByOrderId(Integer id) {
		return dao.findByOrderId(id);
	}
	
	@Override
	public OrderDetail create(OrderDetail orderDetail) {
		return dao.save(orderDetail);
	}
	
	@Override
	public void deleteList(List<OrderDetail> list) {
		dao.deleteAll(list);;
	}
}
