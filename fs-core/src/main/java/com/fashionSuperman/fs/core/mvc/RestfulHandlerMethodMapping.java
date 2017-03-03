package com.fashionSuperman.fs.core.mvc;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.AbstractHandlerMapping;

/**
 * 配置该路由解析后,{@link @RestController}注解的控制器,不需要@RequestMapping配置,路由经过该类统一解析
 * 
 * @description
 * @author FashionSuperman
 * @date 2017年2月22日 上午10:31:38
 * @version 1.0
 */
public class RestfulHandlerMethodMapping extends AbstractHandlerMapping implements InitializingBean {

	protected Logger log = LoggerFactory.getLogger(RestfulHandlerMethodMapping.class);
	private static String AREA_KEY = "area.";// 接口包名关键字

	private final Map<String, HandlerMethod> urlMap = new LinkedHashMap<>();

	@Override
	public void afterPropertiesSet() throws Exception {
		this.initHandlerMethods();
	}

	@Override
	protected Object getHandlerInternal(HttpServletRequest request) throws Exception {
		String lookupPath = getUrlPathHelper().getLookupPathForRequest(request).toLowerCase();
		if (logger.isDebugEnabled()) {
			logger.debug("Looking up handler method for path " + lookupPath);
		}
		HandlerMethod handlerMethod = this.urlMap.get(lookupPath);
		return (handlerMethod != null ? handlerMethod.createWithResolvedBean() : null);
	}

	private void initHandlerMethods() {
		if (logger.isDebugEnabled()) {
			logger.debug("Looking for request mappings in application context: " + getApplicationContext());
		}

		//TODO  当前是spring3没有RestController  spring4换成RestController
		Map<String, Object> controllerBeans = this.getApplicationContext().getBeansWithAnnotation(Controller.class);
		for (Map.Entry<String, Object> controller : controllerBeans.entrySet()) {
			Object handler = controller.getValue();
			Class<?> clazz = handler.getClass();
			Method[] methodList = clazz.getDeclaredMethods();
			for (Method method : methodList) {
				HandlerMethod handlerMethod = new HandlerMethod(handler, method);

				String url = ("/" + clazz.getSimpleName().replaceAll("Controller", "") + "/" + method.getName())
						.toLowerCase();
				url = this.getAreaName(clazz.getPackage().getName().toLowerCase()) + url;

				logger.debug(url);
				this.urlMap.put(url, handlerMethod);
			}
		}
	}

	protected HandlerMethod createHandlerMethod(Object handler, Method method) {
		HandlerMethod handlerMethod;
		if (handler instanceof String) {
			String beanName = (String) handler;
			handlerMethod = new HandlerMethod(beanName, getApplicationContext().getAutowireCapableBeanFactory(),
					method);
		} else {
			handlerMethod = new HandlerMethod(handler, method);
		}
		return handlerMethod;
	}

	/**
	 * 获取接口包名
	 * 
	 * @param packageName
	 */
	private String getAreaName(String packageName) {
		int index = packageName.indexOf(AREA_KEY);
		if (index >= 0) {
			String areaName = "/" + packageName.substring(index + AREA_KEY.length()).replaceAll("[^0-9a-zA-Z]", "/");
			areaName = areaName.replaceAll("/controller", "");
			return areaName;
		} else {
			return "";
		}
	}
}
