package com.zhong.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhong.dao.OrderItemMapper;
import com.zhong.model.OrderItem;
import com.zhong.service.OrderItemService;
@Service
public class OrderItemServiceImpl implements OrderItemService {
	@Autowired
	private OrderItemMapper mapper;
	@Override
	public int saveOrderItem(OrderItem item) {
		return mapper.saveOrderItem(item);
	}

}
