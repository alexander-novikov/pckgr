package com.interstitial.interstitialproject.utils;

import java.io.File;

import android.content.Intent;
import android.net.Uri;

public class IntentAplicationFactory {
	private static final String PACKAGE_SCHEME = "package:";
	private static final String MARKET_URI = "market://details?id=%s";
	private static final String TYPE_ANDROID_PACKAGE = "application/vnd.android.package-archive";

	public static Intent createIntentUninstall(final String pPackageName) {
		Uri packageURI = Uri.parse(PACKAGE_SCHEME + pPackageName);
		return new Intent(Intent.ACTION_DELETE, packageURI);
	}

	public static Intent createIntentInstall(final File pFile) {
		Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(pFile), TYPE_ANDROID_PACKAGE);
		return intent;
	}


	public static Intent createIntentMarket(String pPakageName) {
		Uri uri = Uri.parse(String.format(MARKET_URI, pPakageName));
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		return intent;
	}



}