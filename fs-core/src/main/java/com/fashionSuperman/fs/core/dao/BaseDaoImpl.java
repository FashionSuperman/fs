package com.fashionSuperman.fs.core.dao;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.jdbc.SqlRunner;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.druid.pool.DruidDataSource;
import com.fashionSuperman.fs.core.exception.BizException;

public class BaseDaoImpl<T extends BaseEntity> extends SqlSessionDaoSupport implements BaseDao<T> {
	protected static final Logger log = LoggerFactory.getLogger(BaseDaoImpl.class);
	
	public static final String SQL_INSERT = "insert";
	public static final String SQL_BATCH_INSERT = "batchInsert";
	public static final String SQL_UPDATE = "update";
	public static final String SQL_GET_BY_ID = "getById";
	public static final String SQL_DELETE_BY_ID = "deleteById";
	public static final String SQL_LIST_PAGE = "listPage";
	public static final String SQL_LIST_BY = "listBy";
	public static final String SQL_COUNT_BY_PAGE_PARAM = "countByPageParam";// 根据当前分页参数进行统计
	
	
	private SqlSessionTemplate sessionTemplate;
	protected SqlSessionFactory sqlSessionFactory;
	@Autowired
	private DruidDataSource druidDataSource;
	
	@Autowired
//	@Qualifier("commonSessionTemplate")
	public void setSessionTemplate(SqlSessionTemplate sessionTemplate) {
		this.sessionTemplate = sessionTemplate;
	}
	@Autowired
//	@Qualifier("commonSessionFactory")
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		super.setSqlSessionFactory(sqlSessionFactory);
		this.sqlSessionFactory = sqlSessionFactory;
	}


	public String getStatement(String sqlId){
		String name = this.getClass().getName();
		name = name.replace("daoimpl", "mapper");
		String simpleName = this.getClass().getSimpleName();
		String newSimpleName = simpleName.replace("DaoImpl", "Mapper");
		name = name.replace(simpleName, newSimpleName);
		
		
		StringBuffer sb = new StringBuffer().append(name).append(".").append(sqlId);
		return sb.toString();
	}
	
	@Override
	public String insert(T entity) {
		if(entity == null){
			throw new RuntimeException("entity is null");
		}
		String sqlMapId = getStatement(SQL_INSERT);
		int result = sessionTemplate.insert(sqlMapId, entity);
		
		if(result <= 0){
			throw BizException.DB_INSERT_RESULT_0;
		}
		
		if(entity.getId() != null && result > 0){
			return entity.getId();
		}
		return String.valueOf(result);
	}

	@Override
	public int insert(List<T> list) {
		if(list == null || list.size() <= 0){
			return 0;
		}
		String sqlMapId = getStatement(SQL_BATCH_INSERT);
		int result = sessionTemplate.insert(sqlMapId,list);
		if(result <= 0){
			throw BizException.DB_INSERT_RESULT_0;
		}
		return result;
	}

	@Override
	public int update(T entity) {
		if(entity == null){
			throw new RuntimeException("entity is null");
		}
		String sqlMapId = getStatement(SQL_UPDATE);
		int result = sessionTemplate.update(sqlMapId, entity);
		if(result <= 0){
			throw BizException.DB_UPDATE_RESULT_0;
		}
		return result;
	}

	@Override
	public int update(List<T> list) {
		if(list == null || list.size() <= 0){
			return 0;
		}
		int result = 0;
		
		for(int i = 0 ; i < list.size() ; i++){
			this.update(list.get(i));
			result += 1;
		}
		if(result <= 0){
			throw BizException.DB_UPDATE_RESULT_0;
		}
		return result;
	}

	@Override
	public T getById(String id) {
		String sqlMapId = getStatement(SQL_GET_BY_ID);
		T result = sessionTemplate.selectOne(sqlMapId, id);
		return result;
	}

	@Override
	public int deleteById(String id) {
		String sqlMapId = getStatement(SQL_GET_BY_ID);
		return sessionTemplate.delete(sqlMapId, id);
	}

	@Override
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) {
		if(paramMap == null){
			paramMap = new HashMap<>();
		}
		//获取分页数据集 , 注:切勿换成 sessionTemplate 对象
		String sqlMapId = getStatement(SQL_LIST_PAGE);
		RowBounds rowBounds = new RowBounds(pageParam.getStartIndex(), pageParam.getPageSize());
		List<Object> list = getSqlSession().selectList(sqlMapId, paramMap, rowBounds);
		//统计总记录数
		Object countObject = getSqlSession().selectOne(getStatement(SQL_LIST_PAGE),
				new ExecutorInterceptor.CountParameter(paramMap));
		int count = Integer.valueOf(countObject.toString());
		
		// 是否统计当前分页条件下的数据：1:是，其他为否
		Object isCount = paramMap.get("isCount");
		if(isCount != null
				&& "1".equals(isCount.toString())){
			Map<String, Object> countResultMap = sessionTemplate.selectOne(getStatement(SQL_COUNT_BY_PAGE_PARAM), paramMap);
			return new PageBean(pageParam.getCurrentPage(), pageParam.getPageSize(), count, list, countResultMap);
		}else{
			return new PageBean(pageParam.getCurrentPage(), pageParam.getPageSize(), count, list);
		}
	}

	@Override
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap, String sqlId) {
		if(paramMap == null){
			paramMap = new HashMap<>();
		}
		// 获取分页数据集 , 注切勿换成 sessionTemplate 对象
		String sqlMapId = getStatement(sqlId);
		RowBounds rowBounds = new RowBounds(pageParam.getStartIndex(), pageParam.getPageSize());
		List<Object> list = getSqlSession().selectList(sqlMapId, paramMap, rowBounds);
		//统计总记录数
		Object countObject = (Object) getSqlSession().selectOne(getStatement(sqlId), new ExecutorInterceptor.CountParameter(paramMap));
		int count = Integer.valueOf(countObject.toString());
		
		return new PageBean(pageParam.getCurrentPage(), pageParam.getPageSize(), count, list);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<T> listBy(Map<String, Object> paramMap) {
		return (List)this.listBy(paramMap, SQL_LIST_BY);
	}

	@Override
	public List<Object> listBy(Map<String, Object> paramMap, String sqlId) {
		if(paramMap == null){
			paramMap = new HashMap<>();
		}
		String sqlMapId = getStatement(sqlId);
		
		return sessionTemplate.selectList(sqlMapId, paramMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T getBy(Map<String, Object> paramMap) {
		return (T) this.getBy(paramMap, SQL_LIST_BY);
	}

	@Override
	public Object getBy(Map<String, Object> paramMap, String sqlId) {
		if (paramMap == null || paramMap.isEmpty())
			return null;

		return this.getSqlSession().selectOne(getStatement(sqlId), paramMap);
	}

	@Override
	public String getSeqNextValue(String seqName) {
		boolean isClosedConn = false;
		// 获取当前线程的连接
		Connection connection = this.sessionTemplate.getConnection();
		// 获取Mybatis的SQLRunner类
		SqlRunner sqlRunner = null;
		try {
			// 要执行的SQL
			String sql = "";
			// 数据库驱动类
			String driverClass = druidDataSource.getDriver().getClass().getName();
			// 不同的数据库,拼接SQL语句
			if (driverClass.equals("com.ibm.db2.jcc.DB2Driver")) {
				sql = "  VALUES " + seqName.toUpperCase() + ".NEXTVAL";
			}
			if (driverClass.equals("oracle.jdbc.OracleDriver")) {
				sql = "SELECT " + seqName.toUpperCase() + ".NEXTVAL FROM DUAL";
			}
			if (driverClass.equals("com.mysql.jdbc.Driver")) {
				sql = "SELECT  FUN_SEQ('" + seqName.toUpperCase() + "')";
			}
			// 如果状态为关闭,则需要从新打开一个连接
			if (connection.isClosed()) {
				connection = sqlSessionFactory.openSession().getConnection();
				isClosedConn = true;
			}
			sqlRunner = new SqlRunner(connection);
			Object[] args = {};
			// 执行SQL语句
			Map<String, Object> params = sqlRunner.selectOne(sql, args);
			for (Object o : params.values()) {
				return o.toString();
			}
			return null;
		} catch (Exception e) {
			throw BizException.DB_GET_SEQ_NEXT_VALUE_ERROR.newInstance("获取序列出现错误!序列名称:{%s}", seqName);
		} finally {
			if (isClosedConn) {
				sqlRunner.closeConnection();
			}
		}
	}

	@Override
	public SqlSessionTemplate getSessionTemplate() {
		return this.sessionTemplate;
	}

	@Override
	public SqlSession getSqlSession() {
		return super.getSqlSession();
	}

}
