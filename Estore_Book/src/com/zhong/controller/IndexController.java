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
		// ��ѯ��Ʒ
		//List<Products> productsList = productservice.findProductsList();
		
		//��ȡ��Ʒ���ܼ�¼��
		Integer count = productservice.findPageCount();
		//����pagebean�Ķ�������ҳ�����Ϣ
		PageBean pagebean = new PageBean(8 , page , count);
		model.addAttribute("pagebean", pagebean);
		//���þ��з�ҳ���ܵĲ�ѯ������Ʒ�ķ���
		List<Products> productsList = productservice.findProductsPage(pagebean);
		// ����ѯ������Ʒ����ҳ����չʾ
		model.addAttribute("productsList", productsList);

		return "index";
	}

	// �򿪺�̨�������ҳ��
	@RequestMapping("showAdminIndex")
	public String showAdminIndex() {
		return "admin/index";
	}
}
