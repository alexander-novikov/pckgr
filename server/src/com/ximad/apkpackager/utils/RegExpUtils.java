/**
 * 
 */
package com.ximad.apkpackager.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sun.misc.Regexp;

/**
 * @author Vladimir Baraznovsky
 * 
 */
public class RegExpUtils {
	public static String getSingleGroupPattern(String string, String regExp) {
		Pattern pattern = Pattern.compile(regExp);
		Matcher matcher = pattern.matcher(string);

		String result = null;
		if (matcher.find()) {
			if (matcher.groupCount()>0){
				result = matcher.group(1);
			}
		}
		return result;

	}

	public static String[] getGroupsPattern(String string, String regExp) {
		Pattern pattern = Pattern.compile(regExp);
		Matcher matcher = pattern.matcher(string);
		String[] result = null;
		if (matcher.find()) {
			result = new String[matcher.groupCount()];
			for (int i = 0; i < result.length; i++) {
				result[i] = matcher.group(i + 1);
			}

		}
		return result;

	}
}
