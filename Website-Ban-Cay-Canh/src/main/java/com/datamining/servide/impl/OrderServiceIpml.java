package com.datamining.servide.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datamining.dao.OrderDAO;
import com.datamining.entity.Order;
import com.datamining.service.OrderService;


@Service
public class OrderServiceIpml implements OrderService {
	@Autowired
	OrderDAO dao;

	public List<Order> findAll() {
		return dao.findAllOrders();
	}

	@Override
	public List<Order> findAllByCompleted() {
		return dao.findAllByCompleted();
	}

	@Override
	public List<Order> findAllByCanceled() {
		return dao.findAllByCanceled();
	}
	
	@Override
	public List<Order> findAllByFilter(Integer idStatus) {
		return dao.findAllByFilter(idStatus);
	}
	
	@Override
	public List<Order> findAllByFilter(String createDate) {		
		return dao.findAllByFilter(createDate);
	}
	
	@Override
	public List<Order> findAllByFilter(Integer idStatus,String createDate) {		
		return dao.findAllByFilter(idStatus, createDate);
	}
	
	@Override
	public Order findById(Integer id) {
		return dao.findById(id).get();
	}

	@Override
	public Order update(Integer id, Order order) {
		return dao.save(order);
	}

	@Override
	public Order create(Order order) {
		return dao.save(order);
	}
	
	@Override
	public void delete(Integer id) {
		dao.deleteById(id);
	}
	
	@Override
	public List<Order> findByProfileId(Integer idProfile) {
		return dao.findByProfileId(idProfile);
	}

	@Override
	public List<Order> findByKeyWord(String keyword) {
		return dao.findByKeyWord(keyword);
	}

	@Override
	public Integer getTotalProductSold() {
		return dao.getTotalProductSold();
	}
}



