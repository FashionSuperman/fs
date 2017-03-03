package com.fashionSuperman.fs.core.util;
/**
 * 排序比较器接口,使用{@link CompareUtil}的排序方法需要传递实现该接口的实现
 * @description 
 * @author FashionSuperman
 * @date 2017年2月22日 上午10:29:42
 * @version 1.0
 * @param <T>
 */
public interface Comparator<T> {
	public int compare(T data1 , T data2);
}
