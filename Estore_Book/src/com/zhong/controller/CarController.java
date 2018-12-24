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

	// ��ʾ���ﳵҳ��
	@RequestMapping("showCar")
	public String showCar() {
		return "showcar";
	}

	// ���빺�ﳵ
	@RequestMapping("addCar")
	@ResponseBody
	public String addCar(String id, HttpServletRequest request) {
		// ��ȡ�����빺�ﳵ����Ʒ
		Products product = null;
		HttpSession session = request.getSession();
		// �ȴ�session�л�ȡ���ﳵ����
		Map<Products, Integer> car = (Map<Products, Integer>) session.getAttribute("car");
		if (car == null) {
			// ����һ��map������Ϊ���ﳵ
			car = new HashMap<Products, Integer>();
			product = service.findProductById(id);
			car.put(product, 1);
		} else {
			// �жϼ������Ʒ�Ƿ����
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
		// �����ﳵ����ŵ�session��
		return "{\"msg\":\"true\"}";
	}

	// �޸Ĺ��ﳵ
	@RequestMapping("updateCar")
	public String updateCar(String id, Integer count, HttpServletRequest request) {
		// ��ȡ�����ﳵ����
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
