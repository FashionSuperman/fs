package com.fashionSuperman.fs.core.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 
 * @description 分页组件
 * @author FashionSuperman
 * @date 2017年2月15日 上午11:36:09
 * @version 1.0
 */
public class PageBean implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 当前页
	 */
	private int currentPage;
	/**
	 * 每页大小
	 */
	private int pageSize;

	/**
	 * 总记录数
	 */
	private int totalCount; // 查询数据库
	/**
	 * 本页的数据列表
	 */
	private List<Object> recordList;
	/**
	 * 总页数
	 */
	private int pageCount;// 计算
	/**
	 * 页码列表的开始索引（包含）
	 */
	private int beginPageIndex;
	/**
	 * 页码列表的结束索引（包含）
	 */
	private int endPageIndex;
	/**
	 * 当前分页条件下的统计结果
	 */
	private Map<String, Object> countResultMap;
	
	public PageBean(){}
	
	/**
	 * 只接受前4个必要的属性，会自动的计算出其他3个属生的值
	 * 
	 * @param currentPage
	 * @param pageSize
	 * @param totalCount
	 * @param recordList
	 */
	public PageBean(int currentPage, int pageSize, int totalCount, List<Object> recordList) {
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.totalCount = totalCount;
		this.recordList = recordList;

		// 计算总页码
		pageCount = (totalCount + pageSize - 1) / pageSize;

		// 计算 beginPageIndex 和 endPageIndex
		// >> 总页数不多于10页，则全部显示
		if (pageCount <= 10) {
			beginPageIndex = 1;
			endPageIndex = pageCount;
		}
		// >> 总页数多于10页，则显示当前页附近的共10个页码
		else {
			// 当前页附近的共10个页码（前4个 + 当前页 + 后5个）
			beginPageIndex = currentPage - 4;
			endPageIndex = currentPage + 5;
			// 当前面的页码不足4个时，则显示前10个页码
			if (beginPageIndex < 1) {
				beginPageIndex = 1;
				endPageIndex = 10;
			}
			// 当后面的页码不足5个时，则显示后10个页码
			if (endPageIndex > pageCount) {
				endPageIndex = pageCount;
				beginPageIndex = pageCount - 10 + 1;
			}
		}
	}
	
	/**
	 * 只接受前5个必要的属性，会自动的计算出其他3个属生的值
	 * 
	 * @param currentPage
	 * @param pageSize
	 * @param totalCount
	 * @param recordList
	 */
	public PageBean(int currentPage, int pageSize, int totalCount, List<Object> recordList, Map<String, Object> countResultMap){
		this(currentPage,pageSize,totalCount,recordList);
		this.countResultMap = countResultMap;
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

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public List<Object> getRecordList() {
		return recordList;
	}

	public void setRecordList(List<Object> recordList) {
		this.recordList = recordList;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getBeginPageIndex() {
		return beginPageIndex;
	}

	public void setBeginPageIndex(int beginPageIndex) {
		this.beginPageIndex = beginPageIndex;
	}

	public int getEndPageIndex() {
		return endPageIndex;
	}

	public void setEndPageIndex(int endPageIndex) {
		this.endPageIndex = endPageIndex;
	}

	public Map<String, Object> getCountResultMap() {
		return countResultMap;
	}

	public void setCountResultMap(Map<String, Object> countResultMap) {
		this.countResultMap = countResultMap;
	}

	
	
}