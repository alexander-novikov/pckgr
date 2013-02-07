package com.interstitial.interstitialproject.utils;

import android.app.Activity;
import android.util.DisplayMetrics;

import com.AfWVmIYApx.uGmIXJooGM111157.Airpush;
import com.Leadbolt.AdController;
import com.interstitial.interstitialproject.dao.SdkNetwork;
import com.jirbo.adcolony.AdColony;
import com.jirbo.adcolony.AdColonyVideoAd;
import com.senddroid.SendDroid;


public class SdkCallHelper {
	
	private static AdController myController;

	public enum SdkTypes {
		
		LEADBOLT ("leadbolt"),
		AIRPUSH  ("airpush"),
		ADCOLONY ("adcolony"),
		SENDDROID("senddroid"),
		INTERNAL ("internal");
		
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
	public static void callSdk(SdkNetwork sdk, Activity activity) {
		SdkTypes type = SdkTypes.getType(sdk.getName());
		switch (type) {
			case LEADBOLT:
				myController = new AdController(activity, getLeadboldID(activity));
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
			break;
			
			case INTERNAL:
				
			break;

		}
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
