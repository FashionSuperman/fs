package com.fashionSuperman.fs.core.dao;

import java.io.Serializable;
/**
 * 
 * @description 数据库实体基类
 * @author FashionSuperman
 * @date 2017年2月15日 上午10:59:48
 * @version 1.0
 */
import java.util.Date;
public class BaseEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	/**
	 * 实体id
	 */
	private String id;
	/**
	 * 版本号
	 */
	private String version;
	/**
	 * 创建时间
	 */
	private Date createDate;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	
	

}
