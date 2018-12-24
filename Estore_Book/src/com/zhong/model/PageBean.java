package com.zhong.model;

public class PageBean {
	//ÿҳ����ʾ�ļ�¼��
	private Integer pageSize;
	//��ʾ��ҳ��
	private Integer page;
	//�ܼ�¼��
	private Integer count;
	//��ҳ��
	private Integer allPage;
	//ƫ����
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
	//��ҳ��
	public void setAllPage() {
		//��ҳ�� = �ܼ�¼��/ÿҳ��ʾ�ļ�¼��
		if(count % pageSize == 0) {
			this.allPage = count / pageSize;
		}else {
			allPage = count / pageSize + 1;
		}
		
	}
	public PageBean() {	
	}
	/**
	 * pageSize ÿҳ��ʾ�ļ�¼��
	 * page ��ʾ��ҳ��
	 * count �ܼ�¼��
	 * */
	public PageBean(Integer pageSize,Integer page,Integer count) {
		this.pageSize = pageSize;
		this.count = count;
		setAllPage();
		setPage(page);
		setPageNum();
	}
}
