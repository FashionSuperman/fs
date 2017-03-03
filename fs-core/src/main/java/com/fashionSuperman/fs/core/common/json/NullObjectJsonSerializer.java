package com.fashionSuperman.fs.core.common.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class NullObjectJsonSerializer extends JsonSerializer<Object>{

	@Override
	public void serialize(Object arg0, JsonGenerator arg1,
			SerializerProvider arg2) throws IOException,
			JsonProcessingException {
		if(arg0 == null){
			arg1.writeStartObject();
			arg1.writeEndObject();
		}
		else{
			arg1.writeObject(arg0);
		}
		
	}
}