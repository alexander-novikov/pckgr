package com.ximad.install.utils;

import java.io.File;
import java.util.List;

import com.ximad.install.LoggerPackager;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class PackageUtils {

	public static boolean checkPackage(final Context pContext,
			final String pPackageName) {
		
		LoggerPackager.d("Package name: %s", pPackageName);
		
		if (pPackageName==null){
			return false;
		}
		
		PackageManager pm = pContext.getPackageManager();
		List<PackageInfo> packges = pm.getInstalledPackages(0);
		for (PackageInfo packageInfo : packges) {

			if (pPackageName.equals(packageInfo.packageName)) {
				return true;
			}
		}
		return false;
	}

	public static String getPackageNameFromFile(final Context pContext,
			final File pFile) {
		PackageManager packageManager = pContext.getPackageManager();
		PackageInfo info = packageManager.getPackageArchiveInfo(pFile.getAbsolutePath(), 0);
		return info.packageName;
	}

}
