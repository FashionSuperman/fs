package com.fashionSuperman.fs.core.dao;

import java.io.Serializable;
/**
 * 
 * @description 分页参数 从第一页开始
 * @author FashionSuperman
 * @date 2017年2月15日 上午11:22:02
 * @version 1.0
 */
public class PageParam implements Serializable{

	private static final long serialVersionUID = 1L;
	/**
	 * 当前页
	 */
	private int currentPage;
	/**
	 * 每页大小
	 */
	private int pageSize;
	
	public PageParam() {
		this.currentPage = 1;
		this.pageSize = 10;
	}
	
	public PageParam(int currentPage,int pageSize) {
		
		if(currentPage < 1){
			currentPage = 1;
		}
		if(pageSize < 1){
			pageSize = 10;
		}
		
		this.currentPage = currentPage;
		this.pageSize = pageSize;
	}
	/**
	 * 获取分页开始条目索引
	 * @return
	 */
	public int getStartIndex(){
		return (this.currentPage - 1) * this.pageSize;
	}
	/**
	 * 获取分页结束条目索引
	 * @return
	 */
	public int getEndIndex(){
		return (this.currentPage * this.pageSize) - 1;
	}
	
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	
	
	
}
