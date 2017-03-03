package com.fashionSuperman.fs.core.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fashionSuperman.fs.core.common.ObjectMapperCustomer;
import com.fashionSuperman.fs.core.dao.BaseDaoImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

/**
 * 
 * @description 
 * @author FashionSuperman
 * @date 2017年2月23日 下午1:58:39
 * @version 1.0
 */
public class StringUtil {
	protected static final Logger logger = LoggerFactory.getLogger(BaseDaoImpl.class);

	/**
	 * 判断字符串是否为空，null,空字符串，空格字符串都是返回true
	 * 
	 * @param str
	 * @return 是空，返回true，否则false
	 */
	public static boolean isNullOrEmpty(String str) {
		if (str == null || str.trim().length() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 判断字符串是否不为空，null,空字符串，空格字符串都是返回false
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
		if (str == null || str.trim().length() == 0) {
			return false;
		}
		return true;
	}

	/**
	 * 格式化对象为json字符串
	 * 
	 * @param t
	 *            对象
	 * @return json 字符串，转换失败，返回空字符串
	 */
	public static <T> String toJson(T t) {
		ObjectMapperCustomer mapper = new ObjectMapperCustomer();
		String json;
		try {
			json = mapper.writeValueAsString(t);
			return json;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 比较两个字符串的是否相等，不区分大小写
	 * 
	 * @param source
	 * @param dest
	 * @return
	 */
	public static boolean equals(String source, String dest) {
		if (source == null && dest == null)
			return true;
		if (source == null && dest != null) {
			return false;
		}
		if (source != null && dest == null) {
			return false;
		}
		return source.equalsIgnoreCase(dest);
	}

	/**
	 * 
	 * @param json
	 * @return
	 */
	public static <T> T fromJson(String json, TypeReference<T> ref) {
		if (isNullOrEmpty(json) || ref == null)
			return null;
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try {
			return mapper.readValue(json, ref);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * 字符串转Integer
	 * 
	 * @param str
	 * @return
	 *
	 * 		2016年7月23日 上午10:49:17
	 */
	public static Integer toInteger(String str) {
		if (isNullOrEmpty(str))
			return 0;
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * 生成字母和数字组成的随机数
	 *
	 * @param length
	 * @param num
	 * @return
	 *
	 * 		2016年8月15日 下午7:37:22
	 */
	public static String createRandomCode(int length, long num) {
		List<String> results = new ArrayList<String>();
		for (int j = 0; j < num; j++) {
			String val = "";

			Random random = new Random();
			for (int i = 0; i < length; i++) {
				String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; // 输出字母还是数字

				if ("char".equalsIgnoreCase(charOrNum)) // 字符串
				{
					int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; // 取得大写字母还是小写字母
					val += (char) (choice + random.nextInt(26));
				} else if ("num".equalsIgnoreCase(charOrNum)) // 数字
				{
					val += String.valueOf(random.nextInt(10));
				}
			}
			val = val.toLowerCase();
			if (results.contains(val)) {
				continue;
			} else {
				results.add(val);
			}
		}
		String result = String.join("", results);
		return result;
	}

	/**
	 * 右补位，左对齐
	 * 
	 * @param oriStr
	 *            原字符串
	 * @param len
	 *            目标字符串长度
	 * @param alexin
	 *            补位字符
	 * @return 目标字符串
	 */
	public static String padRight(String oriStr, int len, char alexin) {
		String str = "";
		int strlen = oriStr.length();
		if (strlen < len) {
			for (int i = 0; i < len - strlen; i++) {
				str = str + alexin;
			}
		}
		str = str + oriStr;
		return str;
	}

	/**
	 * 左补位，右对齐
	 * 
	 * @param oriStr
	 *            原字符串
	 * @param len
	 *            目标字符串长度
	 * @param alexin
	 *            补位字符
	 * @return 目标字符串
	 */
	public static String padLeft(String oriStr, int len, char alexin) {
		String str = "";
		int strlen = oriStr.length();
		if (strlen < len) {
			for (int i = 0; i < len - strlen; i++) {
				str = str + alexin;
			}
		}
		str = str + oriStr;
		return str;
	}

	/**
	 * 产生六位的随机数
	 * 
	 *
	 *         2016年11月4日 上午11:54:48
	 *
	 * @return
	 */
	public static String create6Random() {
		String val = (int) ((Math.random() * 9 + 1) * 100000) + "";
		return val;
	}

	/**
	 * 组建get请求使用的参数，没有参数返回"",有参数返回？xx=1&yy=2格式的数据
	 * 
	 *
	 *         2016年11月28日 上午11:14:57
	 *
	 * @param map
	 * @return 没有参数返回"",有参数返回？xx=1&yy=2格式的数据
	 */
	public static String buildUrlParams(Map<String, Object> map) {
		if (map == null) {
			return "";
		}
		// 添加参数
		List<String> list = new ArrayList<String>();
		if (map != null) {
			for (Entry<String, Object> ele : map.entrySet()) {
				String temp = ele.getKey() + "=" + ele.getValue();
				list.add(temp);
			}
		}
		if (!list.isEmpty()) {
			return String.join("&", list);
		}
		return "";
	}

	
	/**
	 * 32位的guid
	 *
	 * 2016年12月4日 下午3:41:02
	 *
	 * @return
	 */
	public static String createGuid() {
		return UUID.randomUUID().toString().replaceAll("-", "").toLowerCase();
	}
	/**
	 * 驼峰规则转换
	 * 
	 *
	 *         2016年12月14日 下午2:47:49
	 *
	 * @param json
	 * @param ref
	 * @return
	 */
	public static <T> T fromJsonCamel(String json, TypeReference<T> ref) {
		if (isNullOrEmpty(json) || ref == null)
			return null;
		ObjectMapper mapper = new ObjectMapper();
		// 下划线变驼峰标准
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
		try {
			return mapper.readValue(json, ref);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

}
