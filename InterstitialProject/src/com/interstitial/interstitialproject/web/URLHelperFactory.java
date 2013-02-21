package com.interstitial.interstitialproject.web;

import com.interstitial.interstitialproject.BaseActivity;

public class URLHelperFactory {

	private static String packageName;
	private static BaseActivity baseActivity;

	public static String getPackageName() {
		return packageName;
	}

	public static void init(String packageName, BaseActivity baseActivity) {
		URLHelperFactory.packageName  = packageName;
		URLHelperFactory.baseActivity = baseActivity;
	}
	
	public static URLHelper createInstance(){
		return new URLHelper(baseActivity, packageName);
	}
	
}
