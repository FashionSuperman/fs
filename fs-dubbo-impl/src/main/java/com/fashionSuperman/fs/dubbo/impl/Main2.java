package com.fashionSuperman.fs.dubbo.impl;

import java.util.ArrayList;
import java.util.List;

public class Main2 {

	public static void main(String[] args) {
		List<String> l1 = new ArrayList<>();
		l1.add("111");
		l1.add("222");
		List<String> l2 = new ArrayList<>();
		l2.addAll(l1);
		
		l2.add("333");
		
		for(String s : l1){
			System.out.println(s);
		}
	}

}
