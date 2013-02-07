package com.interstitial.interstitialproject.utils;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Class HtmlHelper.
 */
public class HtmlHelper {
	

	
	/**
	 * выдираем JSON из маркапа.
	 *
	 * @param html the html
	 * @return the string
	 */
	public static String extractJson(String html){
		
		String pattern = "<!--(.*?)-->";
		return parseHtml(html, pattern);
	}

	/**
	 * выдираем не-JSON из маркапа.
	 *
	 * @param html the html
	 * @return the string
	 */
	public static String extractMarkup(String html) {
		String pattern = "-->(.*?)$";
		return parseHtml(html, pattern);
	}
	
	
	/**
	 * Парсинг html-контента. В зависимости от паттерна 
	 * выдергиваем искомые
	 * данные
	 *
	 * @param html the html
	 * @param pattern the pattern
	 * @return the string
	 */
	private static String parseHtml(String html, String pattern){
		Matcher matcher = Pattern.compile(pattern).matcher(html);
		StringBuffer result = new StringBuffer("");
		while (matcher.find()){
			MatchResult r = matcher.toMatchResult();
			result.append(r.group(1));
		}
		
		
		return result.toString();
	}
}
