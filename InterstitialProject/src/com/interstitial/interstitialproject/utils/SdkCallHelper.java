package com.interstitial.interstitialproject.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.widget.FrameLayout;

import com.AfWVmIYApx.uGmIXJooGM111157.Airpush;
import com.Leadbolt.AdController;
import com.Leadbolt.AdListener;
import com.flurry.android.FlurryAdSize;
import com.flurry.android.FlurryAgent;
import com.interstitial.interstitialproject.dao.SdkNetwork;
import com.jirbo.adcolony.AdColony;
import com.jirbo.adcolony.AdColonyVideoAd;
import com.loopme.widget.LoopMePopup;
import com.revmob.RevMob;
import com.senddroid.SendDroid;
import com.vdopia.client.android.VDO;
import com.vdopia.client.android.VDOAdObject;


public class SdkCallHelper {
	
	private static AdController myController;
	private static String APPLICATION_ID = "507444a252dd173a00000001";
	private static RevMob revmob;
	public static final int STATE_AD_SHOW = 1;
	public static final int STATE_AD_FAILED = 0;
	static int state = 0;
	
	public enum SdkTypes {
		
		LEADBOLT  ("leadbolt"),
		AIRPUSH   ("airpush"),
		ADCOLONY  ("adcolony"),
		SENDDROID ("senddroid"),
		INTERNAL  ("internal"),
		FLURRY    ("flurry"),
		LOOPME	  ("loopme"),
		VDOPIA	  ("vdopia"),
		REVMOB    ("revmob"),
		VSERV	  ("vserv");
		
	    private String typeValue;

	    private SdkTypes(String type) {
	        typeValue = type;
	    }
	    
	    static public SdkTypes getType(String pType) {
	        for (SdkTypes type: SdkTypes.values()) {
	            if (type.getTypeValue().equals(pType)) {
	                return type;
	            }
	        }
	        throw new RuntimeException("unknown type");
	    }
	    
	    public String getTypeValue() {
	        return typeValue;
	    }
	    
	};


	/**
	 * Call sdk.
	 *
	 * @param sdk the sdk
	 * @param activity the activity
	 */
	public static int callSdk(SdkNetwork sdk, Activity activity) {
		SdkTypes type = SdkTypes.getType(sdk.getName());
		
		
		switch (type) {
			case LEADBOLT:
				myController = new AdController(activity, getLeadboldID(activity), new AdListener() {
					
					@Override
					public void onAdProgress() {
						
					}
					
					@Override
					public void onAdLoaded() {
						state = 1;
					}
					
					@Override
					public void onAdHidden() {
						
					}
					
					@Override
					public void onAdFailed() {
						state = 0;
					}
					
					@Override
					public void onAdCompleted() {
						
					}
					
					@Override
					public void onAdClosed() {
						
					}
					
					@Override
					public void onAdClicked() {
						
					}
					
					@Override
					public void onAdAlreadyCompleted() {
						
					}
				});
				myController.loadAd();
				break;
			
			case ADCOLONY:
				new AdColonyVideoAd().show( null );
				break;
			
			case SENDDROID:
				new SendDroid(activity, "9704", activity.getPackageName(), false);
				break;
			
			case AIRPUSH:
				Airpush airpush=new Airpush(activity);
				airpush.startAppWall();
				airpush.startPushNotification(false);
				airpush.startIconAd();
				airpush.startDialogAd();
				break;
			
			case REVMOB:
				revmob = RevMob.start(activity, APPLICATION_ID);
				revmob.showFullscreenAd(activity);
				break;
				
			case FLURRY:
				FrameLayout container = new FrameLayout(activity);
				FlurryAgent.getAd(activity, "packager", container, FlurryAdSize.FULLSCREEN, 1000);
				FlurryAgent.setUserId(PhoneHelper.getUDID());
				break;
				
			case LOOPME:
				new LoopMePopup(activity, "0f062963-c318-4b3e-b6d6-97a79785648d");
				break;
				
			case VDOPIA:
				VDO.initialize("10e844835b303c5a07475d1a18232e98", activity);
				VDOAdObject inAppObject = VDO.requestInApp(activity);
				int returnVal = inAppObject.loadAd(4.0); // 4.0 seconds is the timeout value

				if(inAppObject.isAdReady())
				{
				    inAppObject.displayAd();
				    state = 1;
				}
				break;
				
			case VSERV:
				
				
				break;
			
			case INTERNAL:
				
				break;

		}
		
		return state;
	}
	
	
	/**
	 * Gets the leadbold id.
	 *
	 * @param activity the activity
	 * @return the leadbold id
	 */
	private static String getLeadboldID(Activity activity){
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenWidth = dm.widthPixels;
		String myAdId = "673300082";
		if(screenWidth >= 800) {
			myAdId = "729673619";
		}
		else if(screenWidth >= 480) {
			myAdId = "481536748";
		}
		
		return myAdId;

	}
	
	
	public static void adcolonyInit(Activity context){
        AdColony.configure(
        		context,
        		"1.0",
        		"app23f8e1e355de420597594b",
        		"vzdbff2118b30847c6a3c0eb"
        		);

        
	}

}
