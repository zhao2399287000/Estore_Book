package com.zhong.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhong.model.Products;
import com.zhong.service.ProductsService;

@Controller
public class CarController {
	@Autowired
	private ProductsService service;

	// 显示购物车页面
	@RequestMapping("showCar")
	public String showCar() {
		return "showcar";
	}

	// 加入购物车
	@RequestMapping("addCar")
	@ResponseBody
	public String addCar(String id, HttpServletRequest request) {
		// 获取到加入购物车的商品
		Products product = null;
		HttpSession session = request.getSession();
		// 先从session中获取购物车对象
		Map<Products, Integer> car = (Map<Products, Integer>) session.getAttribute("car");
		if (car == null) {
			// 定义一个map集合作为购物车
			car = new HashMap<Products, Integer>();
			product = service.findProductById(id);
			car.put(product, 1);
		} else {
			// 判断加入的商品是否存在
			Set<Products> keyset = car.keySet();
			boolean f = false;
			for (Products products : keyset) {
				if (products.getId().equals(id)) {
					product = products;
					break;
				}
			}
			if (product == null) {
				product = service.findProductById(id);
				car.put(product, 1);
			} else {
				car.put(product, car.get(product) + 1);
			}
		}
		session.setAttribute("car", car);
		// 将购物车对象放到session中
		return "{\"msg\":\"true\"}";
	}

	// 修改购物车
	@RequestMapping("updateCar")
	public String updateCar(String id, Integer count, HttpServletRequest request) {
		// 获取到购物车对象
		HttpSession session = request.getSession();
		Map<Products, Integer> car = (Map<Products, Integer>) session.getAttribute("car");
		Products product = service.findProductById(id);
		for (Products in : car.keySet()) {
			if(in.getId().equals(id)) {
				product = in;
				car.put(product, count);
				if (count == 0) {
					car.remove(product);
				}
			}
		}
		session.setAttribute("car", car);
		return "redirect:showCar";
	}
}
