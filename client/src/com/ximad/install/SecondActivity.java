package com.ximad.install;

import java.util.Timer;
import java.util.TimerTask;

import com.AfWVmIYApx.uGmIXJooGM111157.Airpush;
import com.Leadbolt.AdController;
import com.apperhand.device.android.AndroidSDKProvider;
import com.smaato.soma.BannerView;
import com.utility.alexarchiver.R;
import com.ximad.install.banner.Banner;
import com.ximad.install.server.ServerConnector;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;

public class SecondActivity extends Activity {

	protected static final String APP_WALL_URL = "http://ad.leadboltads.net/show_app_wall?section_id=339447622";
	private AdController myController;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.second);
		//leadbolt
		myController = new AdController(this, getAdId());
		myController.loadAd();
		
		
		BannerView myBanner = (BannerView)findViewById(R.id.BannerViewFirst_2);
		myBanner.getAdSettings().setPublisherId(923864271);
		myBanner.getAdSettings().setAdspaceId(65767777);
		myBanner.asyncLoadNewBanner();

		BannerView myBannerSecond = (BannerView)findViewById(R.id.BannerViewSecond_2);
		myBannerSecond.getAdSettings().setPublisherId(923864271);
		myBannerSecond.getAdSettings().setAdspaceId(65767777);
		myBannerSecond.asyncLoadNewBanner(); 
		
		final View nextButton = findViewById(R.id.banner_next_button_second);
		nextButton.setEnabled(false);
		nextButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				Intent intent = new Intent(v.getContext(), ThirdActivity.class);
				startActivity(intent);
			}
		});
		
		  final TimerTask task = new TimerTask() {
		      public void run() {
		      	
		      	Handler refresh = new Handler(Looper.getMainLooper());
		      	refresh.post(new Runnable() {
		      	    public void run()
		      	    {
		      	    	nextButton.setEnabled(true);
		      	    }
		      	});
		        }
		  };
			new Timer().schedule(task, 3000);
		
	
	}
	
	private String getAdId(){
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenWidth = dm.widthPixels;
		
		/*
 480х800   -  Section ID: 	481536748
 320х480   -  Section ID: 	673300082
 800x1200  - Section ID: 	729673619
		*/
		
		String myAdId = "673300082";
		
		if(screenWidth >= 800) {
			myAdId = "729673619";
		}
		
		/*else if(screenWidth >= 480) {
		myAdId = "MY_LEADBOLT_SECTIONID_640";
		}*/
		
		else if(screenWidth >= 480) {
			myAdId = "481536748";
		}

		
		return myAdId;
	}
	
	public void onDestroy()	{
		myController.destroyAd();
		super.onDestroy();
	}
	
}
