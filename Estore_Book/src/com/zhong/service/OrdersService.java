package com.zhong.service;

import java.util.List;

import com.zhong.model.Orders;

public interface OrdersService {
	public int saveOrder(Orders order);
	/**
	 * �����û�id��ѯ����
	 * @param id	�û�id
	 * @return		��ѯ���Ķ���
	 */
	public List<Orders> findOrdersList(Integer id);
}
