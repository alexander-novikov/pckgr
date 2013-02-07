package com.interstitial.interstitialproject.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.util.DisplayMetrics;

// TODO: Auto-generated Javadoc
/**
 * The Class PhoneHelper.
 */
public class PhoneHelper {
	
	/** The context. */
	private static Context context;
	
	/** The ad. */
	private static android.app.AlertDialog.Builder ad;
	
	/**
	 * Sets the context.
	 *
	 * @param context the new context
	 */
	public static void setContext(Context context){
		PhoneHelper.context = context;
	}
	
	
	/**
	 * Gets the screen width.
	 *
	 * @return the screen width
	 */
	public static int getScreenWidth(){
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		return dm.widthPixels;
	}
	
	/**
	 * Gets the screen height.
	 *
	 * @return the screen height
	 */
	public static int getScreenHeight(){
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		return dm.heightPixels;
	}
	
	/**
	 * Gets the udid.
	 *
	 * @return the udid
	 */
	public static String getUDID(){
		String id = android.provider.Settings.System.getString(context.getContentResolver(), 
				android.provider.Settings.Secure.ANDROID_ID);
		return id;
	}
	
	
	/**
	 * Check connection.
	 *
	 * @param context the context
	 */
	public static void checkConnection(){
		if (!isOnline(context)){
			
            ad = new AlertDialog.Builder(context);
            ad.setTitle("3g/4g connection is disabled");  // заголовок
            ad.setMessage("Please check your internet connection"); // сообщение
            ad.setPositiveButton("Try again", new OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                            if (isOnline(context)){
                                    dialog.cancel();
                            }
                            else{
                                    ad.show();
                            }
                    }



            });
            
            ad.setNegativeButton("Network settings", new OnClickListener() {
                public void onClick(DialogInterface dialog, int arg1) {
                	if (isOnline(context)){
                        dialog.cancel();
	                }
	                else{
	                        ad.show();
	                }
                	context.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                }



        });

            ad.setCancelable(false);
            ad.show();

    }
		
		
	}
	
	/**
	 * Checks if is online.
	 *
	 * @param context the context
	 * @return true, if is online
	 */
	private static boolean isOnline(Context context) {
		ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;

	}
}
