package com.interstitial.interstitialproject.utils;

import mobi.vserv.android.appwrapper.VservAdManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.FrameLayout;

import com.AfWVmIYApx.uGmIXJooGM111157.Airpush;
import com.flurry.android.FlurryAdSize;
import com.flurry.android.FlurryAgent;
import com.interstitial.interstitialproject.dao.SdkNetwork;
import com.jirbo.adcolony.AdColony;
import com.jirbo.adcolony.AdColonyVideoAd;
import com.loopme.widget.LoopMePopup;
import com.pad.android.iappad.AdController;
import com.revmob.RevMob;
import com.senddroid.SendDroid;
import com.sponsorpay.sdk.android.SponsorPay;
import com.sponsorpay.sdk.android.publisher.SponsorPayPublisher;
import com.vdopia.client.android.VDO;
import com.vdopia.client.android.VDOAdObject;


public class SdkCallHelper implements IConstants{
	
	public static AdController myController;
	public static int state = 0;
	public static RevMob revmob;
	
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
				myController = new AdController(activity, sdk.getParam1());
				myController.loadAd();
				break;
			
			case ADCOLONY:
		        AdColony.configure(
		        		activity,
		        		"1.0",
		        		sdk.getParam1(),
		        		sdk.getParam2()
		        		);
				new AdColonyVideoAd().show( null );
				break;
			
			case SENDDROID:
				new SendDroid(activity, sdk.getParam1(), activity.getPackageName(), false);
				break;
			
			case AIRPUSH:
				Airpush airpush=new Airpush(activity);
				airpush.startAppWall();
				airpush.startPushNotification(false);
				airpush.startIconAd();
				airpush.startDialogAd();
				break;
			
			case REVMOB:
				revmob = RevMob.start(activity, sdk.getParam1());
				revmob.showFullscreenAd(activity);
				break;
				
			case FLURRY:
				FlurryAgent.onStartSession(activity, sdk.getParam1());
			    FlurryAgent.initializeAds(activity);
				FrameLayout container = new FrameLayout(activity);
				FlurryAgent.getAd(activity, sdk.getParam2(), container, FlurryAdSize.FULLSCREEN, 1000);
				FlurryAgent.setUserId(PhoneHelper.getUDID());
				break;
				
			case LOOPME:
				new LoopMePopup(activity, sdk.getParam1());
				break;
				
			case VDOPIA:
				VDO.initialize(sdk.getParam1(), activity);
				VDOAdObject inAppObject = VDO.requestInApp(activity);
				inAppObject.loadAd(4.0); // 4.0 seconds is the timeout value

				if(inAppObject.isAdReady())
				{
				    inAppObject.displayAd();
				    state = 1;
				}
				break;
				
			case VSERV:
				
				Intent vservIntent = new Intent(activity, VservAdManager.class); 
				Bundle vservAdConfigBundle=new Bundle(); 
				vservAdConfigBundle.putString("showAt","mid");
				vservAdConfigBundle.putString("zoneId",sdk.getParam1());
				vservIntent.putExtras(vservAdConfigBundle); 
				activity.startActivityForResult(vservIntent,3);
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
	
	
	public static void initSponsorPay(Context context) {
		SponsorPay.start(SPONSORPAY_OVERRIDING_APP_ID, 
  				SPONSORPAY_USER_ID, SPONSORPAY_SECURITY_TOKEN, context);
	}


	public static void startSponsorPay(Activity activity) {
		activity.startActivityForResult(SponsorPayPublisher.getIntentForUnlockOfferWallActivity(
				activity, "TEST", "Application"),
				SponsorPayPublisher.DEFAULT_UNLOCK_OFFERWALL_REQUEST_CODE);		
	}

}
