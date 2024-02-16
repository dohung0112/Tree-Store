package com.datamining.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.datamining.DTO.OrderDTO;
import com.datamining.DTO.ProductDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.var;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "Orders")
public class Order implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String phone;
	private String address;
	private Integer coupon;
	private Double ship;
	private String note;
	private Double total;
	@Temporal(TemporalType.DATE)
	private Date create_date = new Date();
	@Temporal(TemporalType.DATE)
	private Date update_date = new Date();

	// Order_Detail
	@OneToMany(mappedBy = "order")
	private List<OrderDetail> oderDetails;

	// orderstatus_id
	@ManyToOne
	@JoinColumn(name = "orderstatus_id")
	private OrderStatus status;

	// ordermethod_id
	@ManyToOne
	@JoinColumn(name = "ordermethod_id")
	private OrderPayment payment;

	// profile_id
	@ManyToOne
	@JoinColumn(name = "profile_id")
	private Profile profile;
	
	public static Order convert(OrderDTO orderDTO) {
		var order = new Order();
		order.setId(orderDTO.getId());
		order.setPhone(orderDTO.getPhone());
		order.setAddress(orderDTO.getAddress());
		order.setNote(orderDTO.getNote());
		order.setTotal(orderDTO.getTotal());
		order.setCreate_date(orderDTO.getCreate_date());
		order.setUpdate_date(new Date());
		order.setStatus(orderDTO.getStatus());
		order.setPayment(orderDTO.getPayment());
		order.setProfile(orderDTO.getProfile());
		return order;
	}
}
