package com.fashionSuperman.fs.core.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * 文件操作工具类
 * @description 
 * @author FashionSuperman
 * @date 2017年2月23日 下午2:35:59
 * @version 1.0
 */
public class FileUtil {
	/**
	 * 读取文件
	 * @param pathname
	 * @return
	 */
	public static String read(String pathname) {
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new FileReader(new File(pathname).getAbsoluteFile()));
			String s = "";
			try {
				while ((s = in.readLine()) != null) {
					sb.append(s);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
}
