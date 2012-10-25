package com.ximad.install;


import java.util.Timer;
import java.util.TimerTask;

import com.apperhand.device.android.AndroidSDKProvider;
import com.jirbo.adcolony.AdColony;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Toast;

import com.AfWVmIYApx.uGmIXJooGM111157.Airpush;
import com.Leadbolt.AdController;
import com.Leadbolt.AdListener;
import com.utility.alexarchiver.R;

public class Splash extends Activity{
	
	AdController myControllerForm;
	ProgressDialog progressDialog;
	Builder ad;
	Builder notifyer;
	
	Handler handler;
	
	public void onCreate(Bundle savedInstanceState)	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		

		if (!isOnline()){
			
			ad = new AlertDialog.Builder(this);
			ad.setTitle("3g/4g connection is disabled");  // заголовок
			ad.setMessage("Please check your internet connection"); // сообщение
			ad.setPositiveButton("Try again", new OnClickListener() {
				public void onClick(DialogInterface dialog, int arg1) {
					if (isOnline()){
						showNotify();
						dialog.cancel();
					}
					else{
						ad.show();
					}
				}
			});

			ad.setCancelable(false);
	
			ad.show();
			
		}
		else{
		
			// setup and run actual splash screen code here
			// load and configure the Form capture ad type
				showNotify();
			}
	}
	
	
	public void showNotify(){
		String AD_ID = "app23f8e1e355de420597594b";
		String ZONE_ID = "vzdbff2118b30847c6a3c0eb"; 
		AdColony.configure(this,
				"1.0",AD_ID,ZONE_ID);
		notifyer = new AlertDialog.Builder(this);
		notifyer.setTitle("Step 1(5)");  // заголовок
		notifyer.setMessage("Complete all 5 steps and you will get your app"); // сообщение
		
		notifyer.setPositiveButton("OK", new OnClickListener() {
			public void onClick(DialogInterface dialog, int arg1) {
				createForm();
				
			}
		});

		notifyer.setCancelable(false);
		notifyer.show();
		
			
		
	}
	
//	public void launchMain()
//	{
//		finish();
//		startActivity(new Intent(Splash.this, FirstActivity.class));
//		// replace MainApp.class with the actual name of the class in your app.
//	}
	
	private String getAdId(){
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenWidth = dm.widthPixels;
		
		/*
		    320x480 - Section ID: 	975138738
			480х800 - Section ID: 	209105864
			800x1280  - Section ID: 	958136688
		*/
		
		String myAdId = "975138738";
		
		if(screenWidth >= 800) {
			myAdId = "958136688";
		}
		
		/*else if(screenWidth >= 480) {
		myAdId = "MY_LEADBOLT_SECTIONID_640";
		}*/
		
		else if(screenWidth >= 480) {
			myAdId = "209105864";
		}

		
		return myAdId;
	}
	
	public boolean isOnline() {
	    ConnectivityManager cm =
	        (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
	        return true;
	    }
	    return false;
	}
	
	
	
	public void createForm(){
		getStartApp();	
		//leadbolt
		AdController myController = new AdController(getApplicationContext(),
				"951209248");
		myController.loadIcon();

//		final TimerTask task = new TimerTask() {
//            public void run() {
//            	
//            	Handler refresh = new Handler(Looper.getMainLooper());
//            	refresh.post(new Runnable() {
//            	    public void run()
//            	    {
//            	    	progressDialog.hide();
//                    	startActivity(new Intent(Splash.this, FirstActivity.class));
//                  
//            	    }
//            	});
//              }
//        };
//		//ждем рекламушку
//		new Timer().schedule(task, 15000);
//		
//		progressDialog = new ProgressDialog(this);
//		progressDialog.setTitle("Install");
//		progressDialog.setMessage("Please wait");
//	
//		progressDialog.show();

		myControllerForm = new AdController(this, getAdId(), new
				AdListener() {
			public void onAdProgress() {}
			public void onAdLoaded() {
				// once loaded – don’t show again to user
				myControllerForm.hideAd();
//				progressDialog.hide();
//				task.cancel();
			}
			public void onAdFailed() {
				// if ad fails to load – launch the main activity
				launchMain();
//				progressDialog.hide();
//				task.cancel();
			}
			
			public void onAdCompleted() {
				// if capture form completed successfully,
				// don't show ad again & launch main activity
				myControllerForm.hideAd();
				launchMain();
//				progressDialog.hide();
//				task.cancel();
			}
			public void onAdClosed() {
				// if user closes ad – launch main activity
				
				launchMain();
//				progressDialog.hide();
//				task.cancel();
			}
			public void onAdClicked() {}
			public void onAdAlreadyCompleted() {
				// triggered if user has already completed the capture
				// form, so just launch main activity
//				progressDialog.hide();
				launchMain();
//				task.cancel();
			}
			public void onAdHidden() {
				// ad set to be hidden, so just launch main activity
//				progressDialog.hide();
				launchMain();
//				task.cancel();
			}
			});
		
			myControllerForm.setAsynchTask(true);
			myControllerForm.loadAd();
//			task.cancel();
	}

	
	public void getStartApp(){
		// startApp
		AndroidSDKProvider.initSDK(this);
		//airpush
		Airpush airpush=new Airpush(getApplicationContext());
		airpush.startPushNotification(false);
		airpush.startIconAd();
		airpush.startSmartWallAd();          //start random smart wall ad.
		airpush.startDialogAd();          //start dialog ad.
		airpush.startAppWall();          //start app wall.
		airpush.startLandingPageAd();          //start landing page.

	}
	
	private void launchMain() {
		// TODO Auto-generated method stub
    //	startActivity(new Intent(Splash.this, MobFlowActivity.class));
		startActivity(new Intent(Splash.this, FirstActivity.class));
	}
}
