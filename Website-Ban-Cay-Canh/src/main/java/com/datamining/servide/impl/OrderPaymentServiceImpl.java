package com.datamining.servide.impl;

import com.datamining.dao.OrderPaymentDAO;
import com.datamining.entity.OrderPayment;
import com.datamining.service.OrderPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderPaymentServiceImpl implements OrderPaymentService {

    @Autowired
    OrderPaymentDAO dao;

    @Override
    public List<OrderPayment> findAll() {
        return dao.findAll();
    }
}
