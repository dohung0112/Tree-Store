package com.datamining.dao;

import com.datamining.entity.OrderPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderPaymentDAO extends JpaRepository<OrderPayment, Integer> {


}
