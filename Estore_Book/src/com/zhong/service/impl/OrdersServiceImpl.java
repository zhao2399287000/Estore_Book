package com.zhong.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhong.dao.OrdersMapper;
import com.zhong.model.OrderItem;
import com.zhong.model.Orders;
import com.zhong.service.OrderItemService;
import com.zhong.service.OrdersService;
@Service
public class OrdersServiceImpl implements OrdersService {
	@Autowired
	private OrdersMapper mapper;
	@Autowired
	private OrderItemService service;

	@Override
	public int saveOrder(Orders order) {
		int num = mapper.saveOrder(order);
		//保存订单的订单项
		List<OrderItem> orderItems = order.getOrderItems();
		for (OrderItem orderItem : orderItems) {
			service.saveOrderItem(orderItem);
		}
		return num;
	}

	@Override
	public List<Orders> findOrdersList(Integer id) {
		return mapper.findOrdersList(id);
	}


}
