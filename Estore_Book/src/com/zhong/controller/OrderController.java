package com.zhong.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhong.model.OrderItem;
import com.zhong.model.Orders;
import com.zhong.model.Products;
import com.zhong.model.Users;
import com.zhong.service.OrdersService;
import com.zhong.utils.DateUtils;
import com.zhong.utils.UUIDUtils;
import com.mysql.jdbc.StandardSocketFactory;

@Controller
public class OrderController {
	@Autowired
	private OrdersService service;
	
	//打开生成订单的页面
	@RequestMapping("showCreateOrder")
	public String showCreateOrder(Orders order,HttpServletRequest request){
		
		return "order";
	}
	
	//生成订单
	@RequestMapping("createOrder")
	public String createOrder(Orders order,HttpServletRequest request){
		//只获取到了金额和收货地信息，需要补充订单的其他信息
		String id = UUIDUtils.getUUID();
		order.setId(id);
		order.setOrdertime(DateUtils.format(new Date()));
		order.setPaystate(0);//0:未支付   1:已支付
		HttpSession session = request.getSession();
		Users user = (Users) session.getAttribute("user");
		order.setUserId(user.getId());
		
		
		
		//获取购物车信息
		Map<Products, Integer> cart = (Map<Products, Integer>) session.getAttribute("car");
		//获取购物车中所有的商品
		Set<Products> keySet = cart.keySet();
		
		List<OrderItem> items = new ArrayList<OrderItem>();
		
		for (Products products : keySet) {
			//设置订单项信息
			OrderItem item = new OrderItem();
			item.setOrderId(id);
			item.setProductId(products.getId());
			item.setBuynum(cart.get(products));
			
			items.add(item);
		}
		order.setOrderItems(items);
		service.saveOrder(order);
		session.removeAttribute("car");
		return "redirect:showOrders";
	}
	
	//打开查看订单页面
	@RequestMapping("showOrders")
	public String showOrders(HttpServletRequest request){
		Users user = (Users)request.getSession().getAttribute("user");
		//查询当前用户的所有的订单
		List<Orders> list = service.findOrdersList(user.getId());
//		for (Orders orders : list) {
//			System.out.println(orders);
//			List<OrderItem> orderItems = orders.getOrderItems();
//			System.out.println(orderItems);
//			System.out.println("----------");
//			for (OrderItem orderItem : orderItems) {
//				System.out.println(orderItem.getProduct());
//			}
//			
//		}
//		
		
		//将查到的订单信息传递到页面中展示
		request.setAttribute("list", list);
		
		return "showorder";
	}
	
	/*@RequestMapping("delOrders")
	public String delOrders(String id,HttpServletRequest request) {
		Users user = (Users)request.getSession().getAttribute("user");
		//查询当前用户的所有的订单
		List<Orders> list = service.findOrdersList(user.getId());
		for (Orders orders : list) {
			if(orders.getId().equals("id")) {
				service.delOrder(id);
			}
		}
		return "redirect:showOrders";
	}*/
}
