package com.fashionSuperman.fs.core.common.json;

import java.io.IOException;
import java.util.Date;

import com.fashionSuperman.fs.core.util.DateUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class DateJsonSerializer extends JsonSerializer<Object>{

	@Override
	public void serialize(Object arg0, JsonGenerator arg1,
			SerializerProvider arg2) throws IOException,
			JsonProcessingException {
		if(arg0 == null){
			arg1.writeString(""); 
		}
		else if(arg0.getClass().equals(Date.class)){
			String date = DateUtil.convertDateToString((Date)arg0);
			arg1.writeString(date);
		}
		else{
			arg1.writeString(arg0.toString());
		}
		
	}
	
}
