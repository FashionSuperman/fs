package com.fashionSuperman.fs.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 日期转换工具类
 * @description 
 * @author FashionSuperman
 * @date 2017年2月23日 上午11:47:06
 * @version 1.0
 */
public class DateUtil {

	private static String defaultDatePattern = null;

	/**
	 * Return default datePattern (yyyy-MM-dd)
	 * 
	 * 
	 * @return a string representing the date pattern on the UI
	 */
	public static synchronized String getDatePattern() {

		defaultDatePattern = "yyyy-MM-dd HH:mm:ss";

		return defaultDatePattern;
	}

	/**
	 * This method attempts to convert an Oracle-formatted date in the form
	 * dd-MMM-yyyy to mm/dd/yyyy.
	 * 
	 * @param aDate
	 *            date from database as a string
	 * @return formatted string for the ui
	 */
	public static final String getDate(Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";

		if (aDate != null) {
			df = new SimpleDateFormat(getDatePattern());
			returnValue = df.format(aDate);
		}

		return (returnValue);
	}

	/**
	 * This method generates a string representation of a date/time in the
	 * format you specify on input
	 * 
	 * @param aMask
	 *            the date pattern the string is in
	 * @param strDate
	 *            a string representation of a date
	 * @return a converted Date object
	 * @see java.text.SimpleDateFormat
	 * @throws ParseException
	 */
	public static final Date getDateString(String aMask, String strDate) throws ParseException {
		SimpleDateFormat df = null;
		Date date = null;
		if (aMask != null && !aMask.equals("") && strDate != null && !strDate.equals("")) {
			df = new SimpleDateFormat(aMask);
			date = df.parse(strDate);
		}

		return (date);
	}

	/**
	 * This method generates a string representation of a date's date/time in
	 * the format you specify on input
	 * 
	 * @param aMask
	 *            the date pattern the string is in
	 * @param aDate
	 *            a date object
	 * @return a formatted string representation of the date
	 * 
	 * @see java.text.SimpleDateFormat
	 */
	public static final String getDateTime(String aMask, Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";

		if (aDate != null && aMask != null && !aMask.equals("")) {
			df = new SimpleDateFormat(aMask);
			returnValue = df.format(aDate);
		}

		return (returnValue);
	}

	/**
	 * This method generates a string representation of a date based on the
	 * System Property 'dateFormat' in the format you specify on input
	 * 
	 * @param aDate
	 *            A date to convert
	 * @return a string representation of the date
	 */
	public static final String convertDateToString(Date aDate) {
		return getDateTime(getDatePattern(), aDate);
	}

	/**
	 * Date转换为yyyy-MM-dd格式字符串
	 * 
	 * @param aDate
	 * @return
	 */
	public static final String convertDateToYYYYMMdd(Date aDate) {
		return getDateTime("yyyy-MM-dd", aDate);
	}

	/**
	 * This method converts a String to a date using the datePattern
	 * 
	 * @param strDate
	 *            the date to convert (in format MM/dd/yyyy)
	 * @return a date object
	 * 
	 * @throws ParseException
	 */
	public static Date convertStringToDate(String strDate) throws ParseException {
		Date aDate = null;

		aDate = getDateString(getDatePattern(), strDate);

		return aDate;
	}

	/**
	 * yyyy-MM-dd格式字符串转换为时间
	 * 
	 * @param strDate
	 * @return
	 * @throws ParseException
	 */
	public static Date convertStringToyyMMdd(String strDate) throws ParseException {
		Date aDate = null;

		aDate = getDateString("yyyy-MM-dd", strDate);

		return aDate;
	}

	/**
	 * 判断是否是日期和时分的格式
	 * 
	 * @param strDate：日期字符串
	 * @return true：是，false：否
	 * @throws ParseException
	 * @date 2016-08-12
	 */
	public static Boolean isDateHourMin(String strDate) {
		Pattern pat = Pattern.compile(RegExpStr.dateHourMin);
		Matcher mat = pat.matcher(strDate);
		boolean dateType = mat.matches();

		return dateType;
	}
	
	/**
	 * 获取日志对应的星期几
	 * @param date
	 * @return
	 */
	public static String getWeekOfDate(Date date) {
		  String[] weekDaysName = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		  //String[] weekDaysCode = { "0", "1", "2", "3", "4", "5", "6" };
		  Calendar calendar = Calendar.getInstance();
		  calendar.setTime(date);
		  int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		  return weekDaysName[intWeek];
		} 
}
