package com.xwd.base.util;

import org.apache.commons.lang3.StringUtils;

public class StringUtil {

	/**
	 * 处理题库里面的内容
	 * 发送给移动端
	 * @param str
	 * @return
	 */
	public static String convert(String str){
		if(StringUtils.isNotBlank(str)){
			StringBuffer sb = new StringBuffer(1024);
			int length = str.length();
			boolean bool = true;
			for(int i = 0; i < length; i++){
				char c = str.charAt(i);
				if(c == 60){
					bool = false;
					continue;
				}
				if(c == 62){
					bool = true;
					continue;
				}
				if(bool){
					sb.append(c);
				}
			}
			return sb.toString();
		}
		return "";
	}
}
