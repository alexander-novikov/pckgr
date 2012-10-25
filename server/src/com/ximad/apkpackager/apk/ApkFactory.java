package com.ximad.apkpackager.apk;

import java.io.File;

import com.ximad.apkpackager.utils.AaptUtils;

public class ApkFactory {

	private static final String AAPT_PATH = "platform-tools/aapt";

	public static ApkBuild createApkBuild(File fileApk, File sdkDir) {
		ApkBuild apkBuild = new ApkBuild(fileApk);
		AaptUtils.execDumpBadging(new File(sdkDir, AAPT_PATH), fileApk,
				apkBuild);
		return apkBuild;

	}
}
