package com.interstitial.interstitialproject.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.senddroid.SendDroid;
public class BootReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		new SendDroid(context, "9704",context.getPackageName(), true);
	}

}