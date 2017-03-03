package com.fashionSuperman.fs.core.util;

import java.lang.reflect.Method;
import java.util.List;
import java.util.ListIterator;
/**
 * 
 * @description 排序工具
 * @author FashionSuperman
 * @date 2017年2月22日 上午10:29:05
 * @version 1.0
 */
public class CompareUtil {
	/**
	 * 冒泡排序list
	 * @param param
	 * @param field
	 * @param comparator
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static <T,D> List<T> bubbleSortList(List<T> param , 
			String field , 
			Comparator<D> comparator) throws Exception{
		if(param == null || param.size() ==0){
			return null;
		}
		if(field != null && field.length() > 0){
			String firstS = field.substring(0, 1);
			firstS = firstS.toUpperCase();
			field = firstS + field.substring(1);
		}else{
			return null;
		}
		
		T temp = null;
		T[] array = (T[]) param.toArray(new Object[param.size()]);
		
		Class<T> tClass = (Class<T>) param.get(0).getClass();
		
		Method[] methods = tClass.getDeclaredMethods();
		Method methodTarget = null;
		
		for(int i = 0 ; i < methods.length ; i++){
			Method method = methods[i];
			if(method.getName().contains(field) && method.getName().contains("get")){
				methodTarget = method;
			}
		}
		
		if(methodTarget == null){
			return null;
		}
		
		for(int i = 0 ; i < array.length ; i++){
			for(int j = 0 ; j < (array.length -i - 1) ; j++){
				Object data1 = methodTarget.invoke(array[j]);
				Object data2 = methodTarget.invoke(array[j+1]);
				
				if(comparator.compare((D)data1,(D)data2) > 0){
					temp = array[j];
					array[j] = array[j+1];
					array[j+1] = temp;
				}
			}
		}
		
		ListIterator<T> iterator = param.listIterator();
		for(int i = 0 ; i < array.length ; i++){
			iterator.next();
			iterator.set((T)array[i]);
		}
		
		return param;
	}
}
