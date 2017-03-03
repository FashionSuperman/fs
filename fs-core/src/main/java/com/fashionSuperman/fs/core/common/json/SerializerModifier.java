package com.fashionSuperman.fs.core.common.json;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;

public class SerializerModifier extends BeanSerializerModifier {

	private JsonSerializer<Object> nullStringJsonSerializer = new NullStringJsonSerializer();
	private JsonSerializer<Object> nullObjectJsonSerializer = new NullObjectJsonSerializer();
    private JsonSerializer<Object> nullListJsonSerializer = new NullListJsonSerializer();
    private JsonSerializer<Object> nullNumberJsonSerializer = new NullNumberJsonSerializer();
    
    private JsonSerializer<Object> dateJsonSerializer = new DateJsonSerializer();
    
    @Override
    public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc,
            List<BeanPropertyWriter> beanProperties) {
        // 循环所有的beanPropertyWriter
        for (int i = 0; i < beanProperties.size(); i++) {
            BeanPropertyWriter writer = beanProperties.get(i);
            // 判断字段的类型，如果是array，list，set则注册nullSerializer
            Class<?> clazz = writer.getPropertyType();
            if(clazz.equals(String.class)){
            	writer.assignNullSerializer(this.nullStringJsonSerializer);
            }
            else if(clazz.equals(Date.class)){
            	writer.assignNullSerializer(this.dateJsonSerializer);
            	writer.assignSerializer(this.dateJsonSerializer);
            }
            else if(clazz.equals(Integer.class) || clazz.equals(Short.class) || clazz.equals(BigDecimal.class)){
            	writer.assignNullSerializer(this.nullNumberJsonSerializer);
            }
            else if(clazz.isArray() || clazz.equals(List.class) || clazz.equals(Set.class)){
            	writer.assignNullSerializer(this.nullListJsonSerializer);
            }
            else{
            	//给writer注册一个自己的nullSerializer
                writer.assignNullSerializer(this.nullObjectJsonSerializer);
            }
        }
        return beanProperties;
    }

}
