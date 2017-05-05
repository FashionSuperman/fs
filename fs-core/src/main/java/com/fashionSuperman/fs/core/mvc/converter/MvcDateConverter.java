package com.fashionSuperman.fs.core.mvc.converter;

import java.text.ParseException;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

import com.fashionSuperman.fs.core.util.DateUtil;

public class MvcDateConverter implements Converter<String, Date>{

	@Override
	public Date convert(String source) {
		try {
			return DateUtil.convertStringToyyMMdd(source);
		} catch (ParseException e) {
			return null;
		}
	}
	
}
