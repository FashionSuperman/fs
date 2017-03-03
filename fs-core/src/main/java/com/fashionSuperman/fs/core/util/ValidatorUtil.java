package com.fashionSuperman.fs.core.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.fashionSuperman.fs.core.exception.BizException;

import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;

/**
 * 使用oval校验bean   bean的属性上要加oval注解
 * @description 
 * @author FashionSuperman
 * @date 2017年2月24日 上午9:11:59
 * @version 1.0
 */
public class ValidatorUtil {

	/**
	 * 获取校验信息的集合
	 * 
	 * @param bean
	 * @return
	 */
	public static <T> Map<String, String> getValidator(T bean) {
		if (bean == null) {
			throw new NullPointerException("bean 为空");
		}

		Validator validator = new Validator();
		List<ConstraintViolation> ret = validator.validate(bean);
		Map<String, String> map = new HashMap<String, String>();
		ret.forEach(a -> {
			// System.out.println(a.getErrorCode() + a.getMessage());
			map.put(a.getErrorCode(), a.getMessage());
		});
		return map;
	}

	/**
	 * 从错误中获取一条记录作为异常
	 * 
	 * @param bean
	 * @return BizException 异常信息
	 */
	public static <T> BizException throw1BizException(T bean) {
		Map<String, String> map = getValidator(bean);
		if (!map.isEmpty()) {
			for (Iterator<String> it = map.keySet().iterator(); it.hasNext();) {

				String key = it.next();
				String mes = map.get(key);
				BizException biz = new BizException(key, mes);
				throw biz;
			}
		}
		return null;
	}

}
