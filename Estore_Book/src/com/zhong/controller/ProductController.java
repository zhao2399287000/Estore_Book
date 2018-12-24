package com.zhong.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.zhong.model.PageBean;
import com.zhong.model.Products;
import com.zhong.service.ProductsService;
import com.zhong.utils.UUIDUtils;
import com.zhong.utils.UploadUtils;

@Controller
public class ProductController {
	@Autowired
	private ProductsService service;
	
	//����Ʒ�����ҳ��
	@RequestMapping("showProduct")
	public String showProduct(Model model) {
		//��ѯҪ�޸ĵ���Ʒ
		List<Products> pros = service.findProductsList();
		//����Ʒ����ҳ��չʾ����
		//�൱��request.setAttribute(arg0,arg1)
		model.addAttribute("productsList",pros);
		return "admin/product";
	}
	
	//�������Ʒ��ҳ��
	@RequestMapping("showAddProduct")
	public String showAddProduct() {
		return "admin/addproduct";
	}
	
	//�����Ʒ
	@RequestMapping("saveProduct")
	public String saveProduct(Products pro,MultipartFile imgpic) {
		//���ϴ����ļ��ϴ���������	
		//��ȡ�ϴ��ļ���·��
		String upload = UploadUtils.upload(imgpic);
		
		//��·�����浽���ݿ���
		pro.setId(UUIDUtils.getUUID());
		pro.setImgurl(upload);
		pro.setState(1);
		service.saveProduct(pro);
		return "redirect:showProduct";
	}
	//ɾ����Ʒ
	@RequestMapping("delProduct")
	public String delProduct(String id) {
		
		//����service����ɾ��
		service.delProduct(id);
		
		return "redirect:showProduct";
	}
	//���޸���Ʒҳ��
	@RequestMapping("showEditProduct")
	public String showEditProduct(String id,Model model) {
		//��ѯҪ�޸ĵ���Ʒ
		Products pros = service.findProductById(id);
		//����Ʒ����ҳ��չʾ����
		//�൱��request.setAttribute(arg0,arg1)
		model.addAttribute("product",pros);
		
		return "admin/editproduct";
	}
	
	//�޸���Ʒ
	@RequestMapping("editProduct")
	public String editPorduct(Products pro,MultipartFile imgpic) {
		//�ж���û���ϴ��µ��ļ�
		if(imgpic!=null) {
			String upload = UploadUtils.upload(imgpic);
			pro.setImgurl(upload);
		}
		System.out.println(pro);
		service.updateProduct(pro);
		return "redirect:showProduct";
	}
	
	//����Ʒ������ҳ��
	@RequestMapping("showProductkinds")
	public String showProductkinds(String type,Model model,Integer page) {
		Integer count = service.findCountByType(type);
		PageBean pagebean = new PageBean(8, page, count);
		//��ѯҪ��ʾ�����͵���Ʒ
		List<Products> productList = service.findProductsListByType(type,pagebean);
		//����Ʒ���ݵ�ҳ����չʾ
		model.addAttribute("productList",productList);
		return "productkinds";
	}
	
	//����Ʒ��չʾ��ҳ��
		@RequestMapping("showProductInfo")
		public String showProductInfo(String id,Model model) {
			//��ѯҪ��ʾ����Ʒ
			Products pro = service.findProductById(id);
			//����Ʒ���ݵ�ҳ����չʾ
			model.addAttribute("product",pro);
			return "productinfo";
		}
}
