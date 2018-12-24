package com.zhong.dao;


import java.util.List;

import com.zhong.model.Orders;

public interface OrdersMapper {

	public int saveOrder(Orders order);

	public List<Orders> findOrdersList(Integer id);
	
	
}
