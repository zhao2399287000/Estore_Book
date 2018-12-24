package com.zhong.service;

import java.util.List;

import com.zhong.model.PageBean;
import com.zhong.model.Products;

public interface ProductsService {
	public int saveProduct(Products pro);
	public int updateProduct(Products pro);
	public int delProduct(String id);
	public Products findProductById(String id);
	public List<Products> findProductsList();
	public List<Products> findProductsListByType(String type,PageBean page);
	public Integer findPageCount();
	public List<Products> findProductsPage(PageBean page);
	public Integer findCountByType(String type);
}
