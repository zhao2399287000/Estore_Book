package com.zhong.model;

public class PageBean {
	//每页中显示的记录数
	private Integer pageSize;
	//显示的页数
	private Integer page;
	//总记录数
	private Integer count;
	//总页数
	private Integer allPage;
	//偏移量
	private Integer pageNum;
	
	public Integer getPageNum() {
		return pageNum;
	}
	public void setPageNum() {
		this.pageNum = (page -1) * pageSize;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		if(page == null || page <1) {
			this.page = 1;			
		}else if(page > allPage) {
			this.page = allPage;			
		}else {
			this.page = page;			
		}
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Integer getAllPage() {
		return allPage;
	}
	//总页数
	public void setAllPage() {
		//总页数 = 总记录数/每页显示的记录数
		if(count % pageSize == 0) {
			this.allPage = count / pageSize;
		}else {
			allPage = count / pageSize + 1;
		}
		
	}
	public PageBean() {	
	}
	/**
	 * pageSize 每页显示的记录数
	 * page 显示的页码
	 * count 总记录数
	 * */
	public PageBean(Integer pageSize,Integer page,Integer count) {
		this.pageSize = pageSize;
		this.count = count;
		setAllPage();
		setPage(page);
		setPageNum();
	}
}
