package com.fashionSuperman.fs.core.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * 资源文件操作工具
 * @description 
 * @author FashionSuperman
 * @date 2017年2月23日 下午2:20:32
 * @version 1.0
 */
public class PropertyUtil {
	//单例模式
	private static final PropertyUtil propertyUtil = new PropertyUtil();
	public static PropertyUtil getInstance(){
		return propertyUtil;
	}
	
	/**
	 * 获取资源文件信息
	 * @param path
	 * @return
	 */
	public Properties getProperties(String relativePath){
		Properties prop = new Properties();
		String basePath = this.getClass().getResource("/").getPath();
		String path = (basePath + relativePath).replace("%20", " ");
		try{
            //读取属性文件a.properties
            InputStream in = new BufferedInputStream (new FileInputStream(path));
            prop.load(in);     ///加载属性列表
        }
        catch(Exception e){}
		
		return prop;
	}
	
	/**
	 * 更新配置文件信息
	 * @param relativePath
	 * @param props
	 * @param keyName
	 */
	public void setProperties(String relativePath, Properties props, String keyName){
		String basePath = this.getClass().getResource("/").getPath();
		String path = (basePath + relativePath).replace("%20", " ");
		try {
			OutputStream fos = new FileOutputStream(path);
			props.store(fos, "Update '" + keyName + "' value");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}
