package com.fashionSuperman.fs.service;

import org.springframework.stereotype.Service;

import com.fashionSuperman.fs.core.annotation.LogAnnotaion;

/**
 * 
 * @description 
 * @author FashionSuperman
 * @date 2017年3月1日 下午4:23:46
 * @version 1.0
 */
@Service
public class TestLogService {
	@LogAnnotaion(module = "TestLogService" , operateType = "Search")
	public String getString(String name){
		return "hello" + name;
	}
}
