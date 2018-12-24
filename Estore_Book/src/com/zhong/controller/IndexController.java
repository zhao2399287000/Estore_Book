package com.zhong.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhong.model.PageBean;
import com.zhong.model.Products;
import com.zhong.service.ProductsService;

@Controller
public class IndexController {
	@Autowired
	private ProductsService productservice;

	@RequestMapping("showIndex")
	public String showIndex(Model model,Integer page) {
		return showIndex1(model,page);
	}
	@RequestMapping("/")
	public String showIndex1(Model model,Integer page) {
		// 查询商品
		//List<Products> productsList = productservice.findProductsList();
		
		//获取商品的总记录数
		Integer count = productservice.findPageCount();
		//创建pagebean的对象，设置页面的信息
		PageBean pagebean = new PageBean(8 , page , count);
		model.addAttribute("pagebean", pagebean);
		//调用具有分页功能的查询所有商品的方法
		List<Products> productsList = productservice.findProductsPage(pagebean);
		// 将查询到的商品在首页面中展示
		model.addAttribute("productsList", productsList);

		return "index";
	}

	// 打开后台管理的主页面
	@RequestMapping("showAdminIndex")
	public String showAdminIndex() {
		return "admin/index";
	}
}
