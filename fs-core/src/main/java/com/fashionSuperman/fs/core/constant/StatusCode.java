package com.fashionSuperman.fs.core.constant;
/**
 * 接口调用状态代码定义
 * @description 
 * @author FashionSuperman
 * @date 2017年2月23日 下午1:48:03
 * @version 1.0
 */
public class StatusCode {

	/**
	 * 处理成功
	 */
	public static final String SUCCESS = "200";

	/**
	 * 处理失败
	 */
	public static final String FAILURE = "500";
	
	/**
	 * 权限验证失败
	 */
	public static final String FAILURE_AUTH = "401";
	/**
	 * 登陆失败
	 */
	public static final String FAILURE_LOGIN = "40101";

	/**
	 * token失效
	 */
	public static final String FAILURE_TOKEN_EXPIRE = "40102";

	/**
	 * 验证权限失败
	 */
	public static final String FAILURE_AUTHENTICATE = "40103";

	/**
	 * 微信openid登陆用户不存在
	 */
	public static final String USER_TOKEN_NOT_EXISTS = "40104";

	/**
	 * 空异常
	 */
	public static final String FAILURE_EMPTY = "50001";

	/**
	 * 服务器端逻辑异常
	 */
	public static final String FAILURE_LOGIC = "50002";

	/**
	 * 调用异常
	 */
	public static final String FAILURE_CALL_AGENT = "50003";

	/**
	 * 未定义异常
	 */
	public static final String FAILURE_UNDEFINED = "50004";

	/**
	 * 日期格式不正确异常
	 */
	public static final String FAILURE_DATE_FORMAT = "50005";

	/**
	 * 存在重复记录
	 */
	public static final String FAILURE_REPEATED = "50006";

	/**
	 * 帐号未注册
	 */
	public static final String FAILURE_NOTREGIST = "50007";

	/**
	 * 帐号或密码错误
	 */
	public static final String FAILURE_ACCOUNT_ERROR = "50008";

	/**
	 * 支付接口异常
	 */
	public static final String PAYMENT_FAILURE = "50025";
	/**
	 * 百洋通行证异常
	 */
	public static final String FAILURE_BAIYYYPASSCARD = "50026";
}
