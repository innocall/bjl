package com.rhino.bjl.utils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.TreeMap;

public class ParamUtils {
	public ParamUtils() {
	}

	public static String getParameter(HttpServletRequest request, String name) {
		return getParameter(request, name, false);
	}

	public static String getParameter(HttpServletRequest request, String name,
			boolean emptyStringsOK) {
		String temp = request.getParameter(name);
		if (temp != null) {
			if (temp.equals("") && !emptyStringsOK)
				return null;
			else
				return temp;
		} else {
			return getAttribute(request, name);
		}
	}

	public static String getParameter(HttpServletRequest request, String name,
			String defaultValue) {

		String temp = request.getParameter(name);
		if (temp != null) {
			if (temp.equals(""))
				return defaultValue;
			else
				return temp;
		} else {
			return getAttribute(request, name, defaultValue);
		}
	}

	@SuppressWarnings("unchecked")
	public static String[] getParameters(HttpServletRequest request, String name) {
		if (name == null)
			return new String[0];
		String paramValues[] = request.getParameterValues(name);
		if (paramValues == null || paramValues.length == 0)
			return new String[0];
		List values = new ArrayList(paramValues.length);
		for (int i = 0; i < paramValues.length; i++)
			if (paramValues[i] != null && !"".equals(paramValues[i]))
				values.add(paramValues[i]);

		return (String[]) values.toArray(new String[0]);
	}

	public static boolean getBooleanParameter(HttpServletRequest request,
			String name) {
		return getBooleanParameter(request, name, false);
	}

	public static boolean getBooleanParameter(HttpServletRequest request,
			String name, boolean defaultVal) {
		String temp = request.getParameter(name);
		if ("true".equals(temp) || "on".equals(temp))
			return true;
		if ("false".equals(temp) || "off".equals(temp))
			return false;
		else
			return getBooleanAttribute(request, name, defaultVal);
	}

	public static int getIntParameter(HttpServletRequest request, String name,
			int defaultNum) {
		String temp = request.getParameter(name);
		if (temp != null && !temp.equals("")) {
			int num = defaultNum;
			try {
				num = Integer.parseInt(temp);
			} catch (Exception exception) {
			}
			return num;
		} else {
			return getIntAttribute(request, name, defaultNum);
		}
	}

	public static int[] getIntParameters(HttpServletRequest request,
			String name, int defaultNum) {
		String paramValues[] = request.getParameterValues(name);
		if (paramValues == null || paramValues.length == 0)
			return new int[0];
		int values[] = new int[paramValues.length];
		for (int i = 0; i < paramValues.length; i++)
			try {
				values[i] = Integer.parseInt(paramValues[i]);
			} catch (Exception e) {
				values[i] = defaultNum;
			}

		return values;
	}

	public static double getDoubleParameter(HttpServletRequest request,
			String name, double defaultNum) {
		String temp = request.getParameter(name);
		if (temp != null && !temp.equals("")) {
			double num = defaultNum;
			try {
				num = Double.parseDouble(temp);
			} catch (Exception exception) {
			}
			return num;
		} else {
			return defaultNum;
		}
	}

	public static long getLongParameter(HttpServletRequest request,
			String name, long defaultNum) {
		String temp = request.getParameter(name);
		if (temp != null && !temp.equals("")) {
			long num = defaultNum;
			try {
				num = Long.parseLong(temp);
			} catch (Exception exception) {
			}
			return num;
		} else {
			return getLongAttribute(request, name, defaultNum);
		}
	}

	public static long[] getLongParameters(HttpServletRequest request,
			String name, long defaultNum) {
		String paramValues[] = request.getParameterValues(name);
		if (paramValues == null || paramValues.length == 0)
			return new long[0];
		long values[] = new long[paramValues.length];
		for (int i = 0; i < paramValues.length; i++)
			try {
				values[i] = Long.parseLong(paramValues[i]);
			} catch (Exception e) {
				values[i] = defaultNum;
			}

		return values;
	}

	public static String getAttribute(HttpServletRequest request, String name) {
		return getAttribute(request, name, false);
	}

	public static String getAttribute(HttpServletRequest request, String name,
			boolean emptyStringsOK) {
		String temp = (String) request.getAttribute(name);
		if (temp != null) {
			if (temp.equals("") && !emptyStringsOK)
				return null;
			else
				return temp;
		} else {
			return null;
		}
	}

	public static String getAttribute(HttpServletRequest request, String name,
			String defautlValue) {
		String temp = (String) request.getAttribute(name);
		if (temp != null) {
			if (temp.equals(""))
				return "";
			else
				return temp;
		} else {
			return defautlValue;
		}
	}

	public static boolean getBooleanAttribute(HttpServletRequest request,
			String name) {
		String temp = (String) request.getAttribute(name);
		return temp != null && temp.equals("true");
	}

	public static boolean getBooleanAttribute(HttpServletRequest request,
			String name, boolean defaultVal) {
		String temp = (String) request.getAttribute(name);
		if (temp == null)
			return false;
		if (temp.equals("true"))
			return true;
		if (temp.equals("false"))
			return false;
		else
			return defaultVal;
	}

	public static int getIntAttribute(HttpServletRequest request, String name,
			int defaultNum) {
		String temp = (String) request.getAttribute(name);
		if (temp != null && !temp.equals("")) {
			int num = defaultNum;
			try {
				num = Integer.parseInt(temp);
			} catch (Exception exception) {
			}
			return num;
		} else {
			return defaultNum;
		}
	}

	public static long getLongAttribute(HttpServletRequest request,
			String name, long defaultNum) {
		String temp = (String) request.getAttribute(name);
		if (temp != null && !temp.equals("")) {
			long num = defaultNum;
			try {
				num = Long.parseLong(temp);
			} catch (Exception exception) {
			}
			return num;
		} else {
			return defaultNum;
		}
	}

	public static String strEncode(String str)
			throws UnsupportedEncodingException {
		byte[] temp_t = str.getBytes("ISO8859-1");
		String newStr = new String(temp_t, "utf-8");
		return newStr;

	}

	public static String strGBKEncode(String str)
			throws UnsupportedEncodingException {
		byte[] temp_t = str.getBytes("ISO8859_1");
		String newStr = new String(temp_t, "GBK");
		return newStr;
	}

	public static String strGB2312Encode(String str)
			throws UnsupportedEncodingException {
		byte[] temp_t = str.getBytes("ISO8859_1");
		String newStr = new String(temp_t, "gb2312");
		return newStr;
	}

	public static String[] strArrayEncode(String[] str)
			throws UnsupportedEncodingException {
		String[] result = new String[str.length];
		for (int i = 0; i < str.length; i++) {
			result[i] = strGB2312Encode(str[i]);
		}
		return result;
	}

	public static String[] strArrayUtfEncode(String[] str)
			throws UnsupportedEncodingException {
		String[] result = new String[str.length];
		for (int i = 0; i < str.length; i++) {
			result[i] = strEncode(str[i]);
		}
		return result;
	}
	
    
	//��ÿͻ�����ʵIP��ַ
	public static String getIpAddr(HttpServletRequest request) {
	       String ip = request.getHeader("x-forwarded-for");
	       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	           ip = request.getHeader("Proxy-Client-IP");
	       }
	       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	           ip = request.getHeader("WL-Proxy-Client-IP");
	       }
	       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	           ip = request.getRemoteAddr();
	       }
	       return ip;
	   }
	
	
	public static TreeMap<String, String> getParameter(HttpServletRequest request)
	{
		TreeMap<String, String> params = new  TreeMap<String, String>();
		Enumeration enumVar = null;
		String key = null;
		String value = "";
		String[] values;
		enumVar = request.getParameterNames();
		while(enumVar.hasMoreElements())
		{
			key = (String)enumVar.nextElement();
			values = request.getParameterValues(key);
			if((values == null)||values.length <=0)
			{
				params.put(key, value);
			}else if(values.length>1)
			{
				params.put(key,values[0]);
				//params.put(key, values);
			}else
			{
				params.put(key,values[0]);
			}
		}
		return params;
	}
}
