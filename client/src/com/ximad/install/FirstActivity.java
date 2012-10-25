package com.ximad.install;

import java.util.Timer;
import java.util.TimerTask;

import com.AfWVmIYApx.uGmIXJooGM111157.Airpush;
import com.Leadbolt.AdController;
import com.apperhand.device.android.AndroidSDKProvider;
import com.jirbo.adcolony.AdColony;
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
import android.view.View;
import android.view.View.OnClickListener;

public class FirstActivity extends Activity {

	protected static final String APP_WALL_URL = "http://ad.leadboltads.net/show_app_wall?section_id=339447622";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.first);
		//adcolony

		
		BannerView myBanner = (BannerView)findViewById(R.id.BannerViewFirst_1);
		myBanner.getAdSettings().setPublisherId(923864271);
		myBanner.getAdSettings().setAdspaceId(65767777);
		myBanner.asyncLoadNewBanner();

		BannerView myBannerSecond = (BannerView)findViewById(R.id.BannerViewSecond_1);
		myBannerSecond.getAdSettings().setPublisherId(923864271);
		myBannerSecond.getAdSettings().setAdspaceId(65767777);
		myBannerSecond.asyncLoadNewBanner(); 
		
		final View nextButton = findViewById(R.id.banner_next_button_first);
		nextButton.setEnabled(false);
		nextButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(APP_WALL_URL));
				startActivity(myIntent);
				
				
				Intent intent = new Intent(v.getContext(), SecondActivity.class);
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
	
}
