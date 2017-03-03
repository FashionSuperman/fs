package com.fashionSuperman.fs.core.common.json;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;

/**
 * json属性命名规则
 * @author Administrator
 *
 */
public class PropertyNamingStrategyPascal extends PropertyNamingStrategy {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 反序列化时调用 
	 */
    @Override  
    public String nameForSetterMethod(MapperConfig<?> config,  
            AnnotatedMethod method, String defaultName) {  
        return method.getName().substring(3);  
    }  
    /**
     * 序列化时调用 
     */
    @Override  
    public String nameForGetterMethod(MapperConfig<?> config,  
            AnnotatedMethod method, String defaultName) {  
        return method.getName().substring(3);  
    }  
}
