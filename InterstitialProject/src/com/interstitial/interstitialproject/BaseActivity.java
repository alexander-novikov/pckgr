package com.interstitial.interstitialproject;

import com.interstitial.interstitialproject.utils.CrashReportHandler;

import android.app.Activity;
import android.os.Bundle;

public abstract class BaseActivity extends Activity{
	public abstract void onAsyncFinishLoading(String html, String url);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CrashReportHandler.attach(this);
	}
}
