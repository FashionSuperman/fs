package com.fashionSuperman.fs.core.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;


/**
 * bean 转换，可以实现父类的拷贝
 * 
 *
 */
public class BeanConvert {

	public static <S, T> void copyProperties(S s, T t) {
		ConvertUtils.register(new StringConverter(), String.class);
		ConvertUtils.register(new DateConverter(), Date.class);
		try {
			BeanUtils.copyProperties(t, s);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	public static <S, T> T copyProperties(S s, Class<T> clazz) {
		if (s == null)
			return null;
		ConvertUtils.register(new StringConverter(), String.class);
		ConvertUtils.register(new DateConverter(), Date.class);
		T t = null;
		try {
			t = clazz.newInstance();
			BeanUtils.copyProperties(t, s);
			return t;
		} catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static <S, T> List<T> copyProperties(List<S> sList, Class<T> clazz) {
		if (sList == null || sList.isEmpty())
			return new ArrayList<T>();
		ConvertUtils.register(new StringConverter(), String.class);
		ConvertUtils.register(new DateConverter(), Date.class);

		List<T> tList = new ArrayList<T>();
		sList.forEach(s -> {
			try {
				T t = clazz.newInstance();
				BeanUtils.copyProperties(t, s);
				tList.add(t);
			} catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
				e.printStackTrace();
			}
		});
		return tList;
	}

	/**
	 * javaBean 转 map
	 *
	 *
	 * @param javaBean
	 * @return
	 *
	 * 		2016年8月6日 下午4:05:15
	 */
	@SuppressWarnings("unchecked")
	public static <K, V> Map<K, V> Bean2Map(Object javaBean) {
		Map<K, V> ret = new HashMap<K, V>();
		try {
			Method[] methods = javaBean.getClass().getDeclaredMethods();
			for (Method method : methods) {
				if (method.getName().startsWith("get")) {
					String field = method.getName();
					field = field.substring(field.indexOf("get") + 3);
					field = field.toLowerCase().charAt(0) + field.substring(1);
					Object value = method.invoke(javaBean, (Object[]) null);
					ret.put((K) field, (V) (value));
				}
			}
		} catch (Exception e) {
		}
		return ret;
	}

	/**
	 * 转换对象到map
	 * 
	 *
	 *         2016年11月14日 下午1:49:21
	 *
	 * @param javaBean
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <K, V> Map<K, V> Bean2MapEmpty(Object javaBean) {
		Map<K, V> ret = new HashMap<K, V>();
		try {
			Method[] methods = javaBean.getClass().getDeclaredMethods();
			for (Method method : methods) {
				if (method.getName().startsWith("get")) {
					String field = method.getName();
					field = field.substring(field.indexOf("get") + 3);
					field = field.toLowerCase().charAt(0) + field.substring(1);
					Object value = method.invoke(javaBean, (Object[]) null);
					if (value == null) {
						value = "";
					}
					ret.put((K) field, (V) (value));
				}
			}
		} catch (Exception e) {
		}
		return ret;
	}

}

class StringConverter implements Converter {

	@SuppressWarnings("unchecked")
	@Override
	public <T> T convert(Class<T> type, Object value) {

		if (value == null) {
			return null;
		}

		if (value instanceof Date) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			try {
				return (T) format.format(value);
			} catch (Exception e) {
				format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				return (T) format.format(value);
			}
		}
		return (T) value.toString();
	}

}

class DateConverter implements Converter {

	@SuppressWarnings("unchecked")
	@Override
	public <T> T convert(Class<T> type, Object value) {
		if (value == null) {
			return null;
		}

		if (StringUtil.isNullOrEmpty(value.toString())) {
			return null;
		}
		String tTypeName = "";
		String valueTypeName = "";
		try {
			tTypeName = type.newInstance().getClass().getName();
			valueTypeName = value.getClass().getName();
		} catch (InstantiationException | IllegalAccessException e1) {
			e1.printStackTrace();
		}
		if (tTypeName.equalsIgnoreCase(valueTypeName)) {
			return (T) value;
		}
		if (tTypeName.equals(Date.class.getName())) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				return (T) format.parse(value.toString());
			} catch (ParseException e) {
				format = new SimpleDateFormat("yyyy-MM-dd");
				try {
					return (T) format.parse(value.toString());
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
			}
		}
		return null;
	}
}