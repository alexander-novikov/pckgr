/*
 * 
 */
package com.interstitial.interstitialproject;


import java.util.HashSet;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.app.Activity;
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
import android.widget.Toast;

import com.interstitial.interstitialproject.dao.Offer;
import com.interstitial.interstitialproject.dao.SdkNetwork;
import com.interstitial.interstitialproject.utils.HtmlHelper;
import com.interstitial.interstitialproject.utils.JsonHelper;
import com.interstitial.interstitialproject.utils.PackageHelper;
import com.interstitial.interstitialproject.utils.PhoneHelper;
import com.interstitial.interstitialproject.utils.SdkCallHelper;
import com.interstitial.interstitialproject.utils.URLHelper;
import com.jirbo.adcolony.AdColony;

public class MainActivity extends Activity {
	
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
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        PhoneHelper.setContext(this);
        doFirstRun();
        
        PhoneHelper.checkConnection();
      //  titleText  = (TextView) findViewById(R.id.titleText);
//        nextButton = (Button) findViewById(R.id.nextButton);
//        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        
        SdkCallHelper.adcolonyInit(MainActivity.this);
        final WebView myWebView = (WebView) findViewById(R.id.webView);
        myWebView.getSettings().setSupportZoom(false);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.getSettings().setSupportMultipleWindows(true);
        myWebView.clearCache(true);
        
        myWebView.setInitialScale(100);
        myWebView.getSettings().setLoadWithOverviewMode(true);
        myWebView.getSettings().setUseWideViewPort(true);
        myWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        myWebView.setScrollbarFadingEnabled(false);
        
        
        myWebView.setBackgroundColor(Color.TRANSPARENT);
        myWebView.getSettings().setLayoutAlgorithm(
        WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        WebViewClient c = new WebViewClient(){

        	

			@Override
			public void onPageFinished(WebView view, String url) {
				if (URLHelper.checkURL(url)){
					
					URLHelper helper = new URLHelper();
					helper.execute(stepNumber);
					
					String html = null;
					try {
						html = helper.get();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					String json = HtmlHelper.extractJson(html);
					String markup = HtmlHelper.extractMarkup(html);
					
					Log.d("Interstitial", html);
					Log.d("Interstitial", json);
					Log.d("Interstitial", markup);
					
					titleText.setText(JsonHelper.getTitle(json));
			
					
					sdkNetworks = JsonHelper.getSdkList(json);
					if (sdkNetworks.size() != 0)
						isInternalFirst = sdkNetworks.get(0).getName().equals("internal");
					isShowOnlyFirst = JsonHelper.getIsShowOnlyFirst(json);
					isIncent = JsonHelper.getIncent(json);
				
					isLastStep = JsonHelper.checkLastStep(json);
					
					if (isLastStep){
						nextButton.setText("Install Your App");
					}
					
					List<Offer> offers = JsonHelper.getOffers(json);

					
					if (isIncent == true){

						isBlocked = true;
						for (Offer offer : offers) {
							packages = offer.getPackages();
							for (String packageName : packages) {
								///WTF?????
								if (PackageHelper.isInstalled(packageName, MainActivity.this))
									isBlocked = false;
							}
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
					
					if (isIncent == false || isBlocked == false)					
						unlockButton(JsonHelper.getButtonUnLockDelay(json));
				}
				
					
			}
        	
        	
        };

        myWebView.setWebViewClient(c);
        myWebView.getSettings().setLoadWithOverviewMode(true);
        myWebView.getSettings().setUseWideViewPort(false);
//        String url = URLHelper.getURLById(stepNumber,URLHelper.ACTION_GET_INSTALL);
        String url = "http://31.130.202.176/html/";
        myWebView.loadUrl(url);
        
//        nextButton.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				if (isInternalFirst){
//					if (isShowOnlyFirst)
//						//если showlogic = first
//						SdkCallHelper.callSdk(sdkNetworks.get(0), MainActivity.this);
//					
//					else
//						//вызываем по порядку sdk
//						for (SdkNetwork sdk : sdkNetworks) {
//							
//							SdkCallHelper.callSdk(sdk, MainActivity.this);
//						}
//				}
//				
//				if (isLastStep){
//					Toast toast = Toast.makeText(v.getContext(), "Thank you!", Toast.LENGTH_LONG);
//					toast.show();
//				}
//				else{
//					nextButton.setEnabled(false);
//					progressBar.setVisibility(View.VISIBLE);
//					++stepNumber;
//			        String url = URLHelper.getURLById(stepNumber,URLHelper.ACTION_GET_INSTALL);
//			        myWebView.loadUrl(url);
//				}
//				
//			}
//		});
        
       
    }

    @Override
	protected void onPause() {
		super.onPause();
		AdColony.pause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		AdColony.resume(this);
		
		if (isIncent){
			for (String packageName : packages) {
				if (PackageHelper.isInstalled(packageName, MainActivity.this))
					isBlocked = false;
			}
			
			if (isBlocked == false){
				unlockButton(0);
			}
		}
		
	}

    private void unlockButton(int delay){
    	final TimerTask task = new TimerTask() {
    		public void run() {
          	
    			Handler refresh = new Handler(Looper.getMainLooper());
    			refresh.post(new Runnable() {
    				public void run(){
//    					nextButton.setEnabled(true);
    					progressBar.setVisibility(View.GONE);
    				}
    			});
            }
    	};
      
    	if (isBlocked!=true)
    		new Timer().schedule(task, delay);
    }

    private void doFirstRun() {
        SharedPreferences settings = getSharedPreferences("PREFERENCE", MODE_PRIVATE);
        if (settings.getBoolean("isFirstRun", true)) {
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("isFirstRun", false);
            editor.commit();
            
           // URLHelper.pingInstall();
        }
}
    
}
