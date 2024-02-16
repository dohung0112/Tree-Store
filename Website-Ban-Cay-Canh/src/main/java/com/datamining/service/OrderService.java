package com.datamining.service;


import java.util.Date;
import java.util.List;

import com.datamining.entity.Order;



public interface OrderService {
	List<Order> findAll();

	List<Order> findAllByCompleted();

	List<Order> findAllByCanceled();
	
	List<Order> findAllByFilter(Integer idStatus);
	
	List<Order> findAllByFilter(String createDate);
	
	List<Order> findAllByFilter(Integer idStatus,String createDate);

	Order findById(Integer id);
	
	Order update(Integer id, Order order);

	Order create(Order order);
	
	void delete(Integer id);
	
	List<Order> findByProfileId(Integer idProfile);
	
	List<Order> findByKeyWord(String keyword);
	
	Integer getTotalProductSold();
}
