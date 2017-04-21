package com.fashionSuperman.fs.core.common;

import java.util.ArrayList;
import java.util.List;

public class PageInfo {
	public static final int DEFAULT_CURR_PAGE = 0;
	public static final int DEFAULT_PAGE_SIZE = 20;

	/**
	 * 当前页
	 */
	private int currentPage;
	/**
	 * 每页大小
	 */
	private int pageSize;
	public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	/**
	 * 总页数
	 */
	private int totalPage;
	/**
	 * 总行数
	 */
	private int totalRows;
	
	/**
	 * 存放表格显示数据
	 */
	private List<?>rows;
	
	/**
	 * 总条数页面表格用
	 */
	private long  total;
	
	/**
	 * 存放消息内容
	 */
	@SuppressWarnings("rawtypes")
	private ArrayList errList;

	/**
	 * 消息类型
	 */
	private String errType;

	@SuppressWarnings("rawtypes")
	public ArrayList getErrList() {
		return errList;
	}

	@SuppressWarnings("rawtypes")
	public void setErrList(ArrayList errList) {
		this.errList = errList;
	}

	public String getErrType() {
		return errType;
	}

	public void setErrType(String errType) {
		this.errType = errType;
	}

	public PageInfo(){

	}

	public void finalize() throws Throwable {

	}

	/**
	 * 当前页
	 */
	public int getCurrentPage(){
		return currentPage;
	}

	/**
	 * 每页大小
	 */
	public int getPageSize(){
		return pageSize;
	}

	/**
	 * 总页数
	 */
	public int getTotalPage(){
		return totalPage;
	}

	/**
	 * 总行数
	 */
	public int getTotalRows(){
		return totalRows;
	}

	/**
	 * 当前页
	 * 
	 * @param newVal
	 */
	public void setCurrentPage(int newVal){
		currentPage = newVal;
	}

	/**
	 * 每页大小
	 * 
	 * @param newVal
	 */
	public void setPageSize(int newVal){
		pageSize = newVal;
	}

	/**
	 * 总页数
	 * 
	 * @param newVal
	 */
	public void setTotalPage(int newVal){
		totalPage = newVal;
	}

	/**
	 * 总行数
	 * 
	 * @param newVal
	 */
	public void setTotalRows(int newVal){
		totalRows = newVal;
	}
}
