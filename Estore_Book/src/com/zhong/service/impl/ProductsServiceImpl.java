package com.zhong.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhong.dao.ProductsMapper;
import com.zhong.model.PageBean;
import com.zhong.model.Products;
import com.zhong.service.ProductsService;
@Service
public class ProductsServiceImpl implements ProductsService {
	@Autowired
	ProductsMapper productsmapper;
	@Override
	public int saveProduct(Products pro) {
		
		return productsmapper.saveProduct(pro);
	}

	@Override
	public int updateProduct(Products pro) {
		// TODO Auto-generated method stub
		return productsmapper.updateProduct(pro);
	}

	@Override
	public int delProduct(String id) {
		// TODO Auto-generated method stub
		return productsmapper.delProduct(id);
	}

	@Override
	public Products findProductById(String id) {
		// TODO Auto-generated method stub
		return productsmapper.findProductById(id);
	}

	@Override
	public List<Products> findProductsList() {
		// TODO Auto-generated method stub
		return productsmapper.findProductsList();
	}

	@Override
	public List<Products> findProductsListByType(String type,PageBean pagebean) {
		return productsmapper.findProductsListByType(type,pagebean);
	}

	@Override
	public Integer findPageCount() {
		// TODO Auto-generated method stub
		return productsmapper.findPageCount();
	}

	@Override
	public List<Products> findProductsPage(PageBean page) {
		// TODO Auto-generated method stub
		return productsmapper.findProductsPage(page);
	}

	@Override
	public Integer findCountByType(String type) {
		// TODO Auto-generated method stub
		return productsmapper.findCountByType(type);
	}
	
	
}
