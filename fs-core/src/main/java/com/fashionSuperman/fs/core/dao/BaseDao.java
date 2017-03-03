package com.fashionSuperman.fs.core.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;

/**
 * 
 * @description 基础dao支持接口
 * @author FashionSuperman
 * @date 2017年2月15日 上午11:11:32
 * @version 1.0
 * @param <T>
 */
public interface BaseDao<T extends BaseEntity> {
	/**
	 * 基础插入操作
	 * @param entity
	 * @return id
	 */
	String insert(T entity);
	/**
	 * 批量插入操作
	 * @param list
	 * @return 插入成功数量
	 */
	int insert(List<T> list); 
	/**
	 * 基础更新操作
	 * @param entity
	 * @return 更新成功数量
	 */
	int update(T entity);
	/**
	 * 批量更新操作
	 * @param list
	 * @return 更新成功数量
	 */
	int update(List<T> list);
	/**
	 * 根据id查询记录
	 * @param id
	 * @return T
	 */
	T getById(String id);
	/**
	 * 根据id删除记录
	 * @param id
	 * @return 删除成功数量
	 */
	int deleteById(String id);
	/**
	 * 分页查询
	 * @param pageParam 分页参数
	 * @param paramMap 查询参数
	 * @return
	 */
	PageBean listPage(PageParam pageParam , Map<String, Object> paramMap);
	
	PageBean listPage(PageParam pageParam , Map<String, Object> paramMap , String sqlId);
	/**
	 * 根据条件查询
	 * @param paramMap
	 * @return 查询结果集
	 */
	List<T> listBy(Map<String, Object> paramMap);
	/**
	 * 
	 * @param paramMap
	 * @param sqlId
	 * @return
	 */
	List<Object> listBy(Map<String, Object> paramMap , String sqlId);
	/**
	 * 根据条件查询
	 * @param paramMap
	 * @return T
	 */
	T getBy(Map<String, Object> paramMap);
	/**
	 * 根据条件查询
	 * @param paramMap
	 * @param sqlId
	 * @return
	 */
	Object getBy(Map<String, Object> paramMap , String sqlId);
	/**
	 * 根据序列名称获取序列next值
	 * @param seqName
	 * @return
	 */
	String getSeqNextValue(String seqName);
	
	SqlSessionTemplate getSessionTemplate();

	SqlSession getSqlSession();
	
}
