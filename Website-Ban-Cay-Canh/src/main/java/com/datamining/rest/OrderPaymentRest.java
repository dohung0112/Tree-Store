package com.datamining.rest;

import com.datamining.entity.OrderPayment;
import com.datamining.service.OrderPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/payment")
public class OrderPaymentRest {

    @Autowired
    OrderPaymentService service;

    @GetMapping
    public List<OrderPayment> getAll()
    {
        return service.findAll();
    }
}
