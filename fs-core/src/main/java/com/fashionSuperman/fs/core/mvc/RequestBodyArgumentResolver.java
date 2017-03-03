package com.fashionSuperman.fs.core.mvc;
//TODO spring3版本原因  有些方法无法使用

//import java.io.IOException;
//import java.lang.reflect.Type;
//import java.util.List;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.core.Conventions;
//import org.springframework.core.MethodParameter;
//import org.springframework.core.annotation.AnnotationUtils;
//import org.springframework.http.converter.HttpMessageConverter;
//import org.springframework.http.converter.HttpMessageNotReadableException;
//import org.springframework.http.converter.HttpMessageNotWritableException;
//import org.springframework.http.server.ServletServerHttpRequest;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.HttpMediaTypeNotAcceptableException;
//import org.springframework.web.HttpMediaTypeNotSupportedException;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.WebDataBinder;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.support.WebDataBinderFactory;
//import org.springframework.web.context.request.NativeWebRequest;
//import org.springframework.web.method.support.ModelAndViewContainer;
//import org.springframework.web.servlet.mvc.method.annotation.AbstractMessageConverterMethodProcessor;
//
///**
// * 
// * @description 反序列化请求数据，代替@RequestBody
// * @author FashionSuperman
// * @date 2017年2月22日 上午11:44:38
// * @version 1.0
// */
//public class RequestBodyArgumentResolver extends AbstractMessageConverterMethodProcessor {
//
//	/**
//	 * Basic constructor with converters only. Suitable for resolving
//	 * {@code @RequestBody}. For handling {@code @ResponseBody} consider also
//	 * providing a {@code ContentNegotiationManager}.
//	 */
//	public RequestBodyArgumentResolver(List<HttpMessageConverter<?>> converters) {
//		super(converters);
//	}
//
//	
//
//	@Override
//	public boolean supportsParameter(MethodParameter parameter) {
//		return parameter.getParameterAnnotations().length == 0;
//	}
//
//	@Override
//	public boolean supportsReturnType(MethodParameter returnType) {
//		return (AnnotationUtils.findAnnotation(returnType.getContainingClass(), ResponseBody.class) != null ||
//				returnType.getMethodAnnotation(ResponseBody.class) != null);
//	}
//
//	/**
//	 * Throws MethodArgumentNotValidException if validation fails.
//	 * @throws HttpMessageNotReadableException if {@link RequestBody#required()}
//	 * is {@code true} and there is no body content or if there is no suitable
//	 * converter to read the content with.
//	 */
//	@Override
//	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
//			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
//
//		Object arg = readWithMessageConverters(webRequest, parameter, parameter.getGenericParameterType());
//		String name = Conventions.getVariableNameForParameter(parameter);
//
//		WebDataBinder binder = binderFactory.createBinder(webRequest, arg, name);
//		if (arg != null) {
//			validateIfApplicable(binder, parameter);
//			if (binder.getBindingResult().hasErrors() && isBindExceptionRequired(binder, parameter)) {
//				throw new MethodArgumentNotValidException(parameter, binder.getBindingResult());
//			}
//		}
//		mavContainer.addAttribute(BindingResult.MODEL_KEY_PREFIX + name, binder.getBindingResult());
//
//		return arg;
//	}
//
//	@Override
//	protected <T> Object readWithMessageConverters(NativeWebRequest webRequest, MethodParameter methodParam,
//			Type paramType) throws IOException, HttpMediaTypeNotSupportedException, HttpMessageNotReadableException {
//
//		HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
//		ServletServerHttpRequest inputMessage = new ServletServerHttpRequest(servletRequest);
//
//		Object arg = readWithMessageConverters(inputMessage, methodParam, paramType);
//		
//		return arg;
//	}
//
//	@Override
//	public void handleReturnValue(Object returnValue, MethodParameter returnType,
//			ModelAndViewContainer mavContainer, NativeWebRequest webRequest)
//			throws IOException, HttpMediaTypeNotAcceptableException, HttpMessageNotWritableException {
//
//		mavContainer.setRequestHandled(true);
//
//		// Try even with null return value. ResponseBodyAdvice could get involved.
//		writeWithMessageConverters(returnValue, returnType, webRequest);
//	}
//
//}
