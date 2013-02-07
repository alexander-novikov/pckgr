package com.interstitial.interstitialproject.utils;

import java.util.List;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

public class PackageHelper {
	public static boolean isInstalled(String packageName, Activity activity){
		final PackageManager pm = activity.getPackageManager();
		List<ApplicationInfo> packages = pm
				.getInstalledApplications(PackageManager.GET_META_DATA);
	    
	    for (ApplicationInfo applicationInfo : packages) {
	    	if (packageName.equals(applicationInfo.packageName))
	    		return true;
	    }

		return false;
	}
}
