package com.eshare_android_preview.base.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {

	/**
	 * ���ƺ���Pattern
	 */
	public static final Pattern PLATE_NUMBER_PATTERN = Pattern
			.compile("^[\u0391-\uFFE5]{1}[a-zA-Z0-9]{6}$");

	/**
	 * ֤������Pattern
	 */
	public static final Pattern ID_CODE_PATTERN = Pattern
			.compile("^[a-zA-Z0-9]+$");

	/**
	 * ����Pattern
	 */
	public static final Pattern CODE_PATTERN = Pattern
			.compile("^[a-zA-Z0-9]+$");

	/**
	 * �̶��绰����Pattern
	 */
	public static final Pattern PHONE_NUMBER_PATTERN = Pattern
			.compile("0\\d{2,3}-[0-9]+");

	/**
	 * ��������Pattern
	 */
	public static final Pattern POST_CODE_PATTERN = Pattern.compile("\\d{6}");

	/**
	 * ���Pattern
	 */
	public static final Pattern AREA_PATTERN = Pattern.compile("\\d*.?\\d*");

	/**
	 * �ֻ����Pattern
	 */
	public static final Pattern MOBILE_NUMBER_PATTERN = Pattern
			.compile("\\d{11}");

	/**
	 * �����ʺ�Pattern
	 */
	public static final Pattern ACCOUNT_NUMBER_PATTERN = Pattern
			.compile("\\d{16,21}");

	/**
	 * ���ƺ����Ƿ���ȷ
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isPlateNumber(String s) {
		Matcher m = PLATE_NUMBER_PATTERN.matcher(s);
		return m.matches();
	}

	/**
	 * ֤�������Ƿ���ȷ
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isIDCode(String s) {
		Matcher m = ID_CODE_PATTERN.matcher(s);
		return m.matches();
	}

	/**
	 * �����Ƿ���ȷ
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isCode(String s) {
		Matcher m = CODE_PATTERN.matcher(s);
		return m.matches();
	}

	/**
	 * �̻������Ƿ���ȷ
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isPhoneNumber(String s) {
		Matcher m = PHONE_NUMBER_PATTERN.matcher(s);
		return m.matches();
	}

	/**
	 * ���������Ƿ���ȷ
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isPostCode(String s) {
		Matcher m = POST_CODE_PATTERN.matcher(s);
		return m.matches();
	}

	/**
	 * ����Ƿ���ȷ
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isArea(String s) {
		Matcher m = AREA_PATTERN.matcher(s);
		return m.matches();
	}

	/**
	 * �ֻ�������ȷ
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isMobileNumber(String s) {
		Matcher m = MOBILE_NUMBER_PATTERN.matcher(s);
		return m.matches();
	}

	/**
	 * �����˺ŷ���ȷ
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isAccountNumber(String s) {
		Matcher m = ACCOUNT_NUMBER_PATTERN.matcher(s);
		return m.matches();
	}
	
	
	public final static String EMAIL = "(?i)^[_a-z0-9\\-]+([._a-z0-9\\-]+)*@[a-z0-9\\-]+([.a-z0-9\\-]+)*(\\.[a-z]{2,4})$";
	
	public final static String NUMBER = "[+-]?\\d+[.0-9]*";
	
	public final static String URL = "(?i)^(http|https|www|ftp|)?(://)?([_a-z0-9\\-].)+[_a-z0-9\\-]+(\\/[_a-z0-9\\-])*(\\/)*([_a-z0-9\\-].)*([_a-z0-9\\-&#?=])*$";
	
	public final static String CASE = "^[a-zA-Z]+$";
	
	public final static String CHINESE = "[\\u4e00-\\u9fa5]*";
	
	public final static String TWOBYTES = "[^\\x00-\\xff]";
	
	public final static String IDCARD = "^(\\d{6})(\\d{4})(\\d{2})(\\d{2})(\\d{3})([0-9]|X)$";
	
	/**
	 * 手机号码
	 * 移动：134[0-8],135,136,137,138,139,150,151,157,158,159,182,187,188
	 * 联通：130,131,132,152,155,156,185,186
	 * 电信：133,1349,153,180,189
	 */
	public final static String MOBILE = "^0?(13[0-9]|15[012356789]|18[0236789]|14[57])[0-9]{8}";
	/**
	 * 中国移动：China Mobile
	 * 134[0-8],135,136,137,138,139,150,151,157,158,159,182,187,188
	 */
	public final static String MOBILE_CM = "^1(34[0-8]|(3[5-9]|5[017-9]|8[278])\\d)\\d{7}$";
	/**
	 * 中国联通：China Unicom
	 * 130,131,132,152,155,156,185,186
	 */
	public final static String MOBILE_CU = "^1(3[0-2]|5[256]|8[56])\\d{8}$";
	/**
	 * 中国电信：China Telecom
	 * 133,1349,153,180,189
	 */
	public final static String MOBILE_CT = "^1((33|53|8[09])[0-9]|349)\\d{7}$";
	/**
	 * 大陆地区固话及小灵通
	 * 区号：010,020,021,022,023,024,025,027,028,029
	 * 号码：七位或八位
	 */
	public final static String MOBILE_PHS = "^0(10|2[0-5789]|\\d{3})\\d{7,8}$";
	
	private RegexUtil() {
	}

	public static List<String> findAll(final String source, final String regex) {
		final List<String> result = new ArrayList<String>();
		final Matcher matcher = genMatcher(regex, source, null);
		while (matcher.find()) {
			result.add(matcher.group());
		}
		return result;
	}

	public static String findFirst(final String source, final String regex) {
		final Matcher matcher = genMatcher(regex, source, null);
		return matcher.find() ? matcher.group() : "";
	}

	public static String findAndReplace(final String source, final String regex, final String replacement) {
		final Matcher matcher = genMatcher(regex, source, null);
		return matcher.find() ? source.replace(matcher.group(), replacement) : source;
	}
	
	public static Matcher genMatcher(final String source, final String regex, final Integer flag) {
		return Pattern.compile(regex, (flag == null) ? 0 : flag).matcher(source);
	}
}
