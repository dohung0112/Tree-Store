package com.datamining.dao;

import java.util.List;

import com.datamining.entity.OrderDetail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderDetailDAO extends JpaRepository<OrderDetail, Integer> {
	@Query(value="SELECT * FROM Order_Detail WHERE order_id = ?1", nativeQuery = true)
    List<OrderDetail> findByOrderId(Integer id);
}
