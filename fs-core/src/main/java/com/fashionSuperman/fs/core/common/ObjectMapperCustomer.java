package com.fashionSuperman.fs.core.common;

import java.text.SimpleDateFormat;

import org.springframework.stereotype.Component;

import com.fashionSuperman.fs.core.common.json.SerializerModifier;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * 自定义json序列化类
 * @description 
 * @author FashionSuperman
 * @date 2017年2月22日 上午11:19:22
 * @version 1.0
 */
//@Component("objectMapperCustomer")
public class ObjectMapperCustomer extends ObjectMapper{

	private static final long serialVersionUID = 1L;
	public ObjectMapperCustomer()  
    {  
		super();
		//数字也加引号  
        this.configure(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS, true); 
        //忽略未知属性
        this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);   
        //null值处理
        this.setSerializerFactory(this.getSerializerFactory().withSerializerModifier(new SerializerModifier()));
        
        this.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        //pascal命名规则
        //this.setPropertyNamingStrategy(new PropertyNamingStrategyPascal());
    }
}
