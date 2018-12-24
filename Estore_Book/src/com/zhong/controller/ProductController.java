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
	
	//打开商品管理的页面
	@RequestMapping("showProduct")
	public String showProduct(Model model) {
		//查询要修改的商品
		List<Products> pros = service.findProductsList();
		//将商品传到页面展示数据
		//相当于request.setAttribute(arg0,arg1)
		model.addAttribute("productsList",pros);
		return "admin/product";
	}
	
	//打开添加商品的页面
	@RequestMapping("showAddProduct")
	public String showAddProduct() {
		return "admin/addproduct";
	}
	
	//添加商品
	@RequestMapping("saveProduct")
	public String saveProduct(Products pro,MultipartFile imgpic) {
		//将上传的文件上传到服务器	
		//获取上传文件的路径
		String upload = UploadUtils.upload(imgpic);
		
		//将路径保存到数据库中
		pro.setId(UUIDUtils.getUUID());
		pro.setImgurl(upload);
		pro.setState(1);
		service.saveProduct(pro);
		return "redirect:showProduct";
	}
	//删除商品
	@RequestMapping("delProduct")
	public String delProduct(String id) {
		
		//调用service进行删除
		service.delProduct(id);
		
		return "redirect:showProduct";
	}
	//打开修改商品页面
	@RequestMapping("showEditProduct")
	public String showEditProduct(String id,Model model) {
		//查询要修改的商品
		Products pros = service.findProductById(id);
		//将商品传到页面展示数据
		//相当于request.setAttribute(arg0,arg1)
		model.addAttribute("product",pros);
		
		return "admin/editproduct";
	}
	
	//修改商品
	@RequestMapping("editProduct")
	public String editPorduct(Products pro,MultipartFile imgpic) {
		//判断有没有上传新的文件
		if(imgpic!=null) {
			String upload = UploadUtils.upload(imgpic);
			pro.setImgurl(upload);
		}
		System.out.println(pro);
		service.updateProduct(pro);
		return "redirect:showProduct";
	}
	
	//打开商品的类别的页面
	@RequestMapping("showProductkinds")
	public String showProductkinds(String type,Model model,Integer page) {
		Integer count = service.findCountByType(type);
		PageBean pagebean = new PageBean(8, page, count);
		//查询要显示的类型的商品
		List<Products> productList = service.findProductsListByType(type,pagebean);
		//将商品传递到页面中展示
		model.addAttribute("productList",productList);
		return "productkinds";
	}
	
	//打开商品的展示的页面
		@RequestMapping("showProductInfo")
		public String showProductInfo(String id,Model model) {
			//查询要显示的商品
			Products pro = service.findProductById(id);
			//将商品传递到页面中展示
			model.addAttribute("product",pro);
			return "productinfo";
		}
}
