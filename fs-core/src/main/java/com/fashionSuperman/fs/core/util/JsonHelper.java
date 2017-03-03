package com.fashionSuperman.fs.core.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import com.fashionSuperman.fs.core.constant.StatusCode;
import com.fashionSuperman.fs.core.exception.BizException;
import com.fasterxml.jackson.core.type.TypeReference;
/**
 * json帮助类 
 * @description 
 * @author FashionSuperman
 * @date 2017年2月23日 下午4:27:13
 * @version 1.0
 */
public class JsonHelper {

	/**
	 * 从文件中读取json 对象
	 * 
	 * @param filePath
	 * @return
	 */
	public static <T> T getJsonFromFile(String filePath, TypeReference<T> ref) {
		String json = getStringFromFile(filePath);
		T t = StringUtil.fromJson(json, ref);
		return t;
	}

	/**
	 * 读取文件
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getStringFromFile(String filePath) {
		if (StringUtil.isNullOrEmpty(filePath)) {
			throw new BizException(StatusCode.FAILURE_EMPTY, "文件路径找不到");
		}

		StringBuffer sb = new StringBuffer();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));
			String data = null;
			while ((data = br.readLine()) != null) {
				sb.append(data);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sb.toString();

	}

}
