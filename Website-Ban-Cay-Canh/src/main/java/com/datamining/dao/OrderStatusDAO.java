package com.datamining.dao;

import java.util.List;

import com.datamining.entity.Order;
import com.datamining.entity.OrderStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderStatusDAO extends JpaRepository<OrderStatus, Integer> {
	
	@Query(value="SELECT * FROM Order_Status WHERE id = 1 OR id = 2 OR id = 3 OR id = 4", nativeQuery = true)
    List<OrderStatus> findByFilter();
}
