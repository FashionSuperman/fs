package com.fashionSuperman.fs.dubbo.facet.common;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.alibaba.dubbo.common.serialize.support.SerializationOptimizer;
import com.fashionSuperman.fs.dubbo.facet.entity.Book;
import com.fashionSuperman.fs.dubbo.facet.entity.User;
/**
 * dubbox  kryo序列化优化注册
 * @description 
 * @author FashionSuperman
 * @date 2017年3月9日 上午9:55:36
 * @version 1.0
 */
public class SerializationOptimizerImpl implements SerializationOptimizer {
	@SuppressWarnings("rawtypes")
	@Override
	public Collection<Class> getSerializableClasses() {
		List<Class> classes = new LinkedList<>();
		classes.add(Book.class);
		classes.add(User.class);
		return classes;
	}

}
