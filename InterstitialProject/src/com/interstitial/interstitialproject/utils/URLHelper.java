package com.interstitial.interstitialproject.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;


public class URLHelper extends AsyncTask<Integer, Void, String>{

	public static final String ACTION_GET_INSTALL = "install";
	public static final String ACTION_GET_CONVERTION = "convertion";
	public static final String ACTION_GET_IMPRESSION = "impression";
	public static final String ACTION_GET_REQUEST = "impression";
	public static final String ACTION_GET_CLICK = "click";
	
	public static boolean checkURL(String url){
		return url.contains("kissmyfiles");
	}
	
	
	public static String getURLById(int step, String action){
		//String url = "http://adserver.kissmyfiles.com:8444/request?pub_id=1&platform=android&tag=1&app_id=123&version=1&width=240&height=320&screenwidth=240&screenheight=320
		//&udid=asda-23423as-2313asd&stepnumber=2&type=1&action=1&position=1";
		StringBuffer url = new StringBuffer("http://adserver.kissmyfiles.com:8444/request?");
		url.append("pub_id=1");
		url.append("&platform=android");
		url.append("&tag=1");
		url.append("&app_id=123");
		url.append("&version=1");
		url.append("&width=240");
		url.append("&height=320");
		url.append("&screenwidth=");
		url.append(PhoneHelper.getScreenWidth());
		url.append("&screenheight=");
		url.append(PhoneHelper.getScreenHeight());
		
		url.append("&udid=");
		url.append(PhoneHelper.getUDID());
		
		url.append("&stepnumber=");
		url.append(step);
		
		url.append("&type=1");
		url.append("&action="+action);
		url.append("&position=1");
		
		return url.toString();
	}
	
//	public static String getHTMLByStep(int step){
//		String url = getURLById(step, ACTION_GET_REQUEST);
//    	HttpClient client = new DefaultHttpClient();
//        HttpGet request = new HttpGet(url);
//        HttpResponse response;
//        String html = "";
//		try {
//			response = client.execute(request);
//			
//		    InputStream in = response.getEntity().getContent();
//		    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
//		    StringBuilder str = new StringBuilder();
//		    String line = null;
//		    while((line = reader.readLine()) != null){
//		            str.append(line);
//		    }
//		    in.close();
//		    html = str.toString();
//		} 
//		catch (ClientProtocolException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//		return html;
//	}



	public static void pingInstall() {
		String url = getURLById(1, ACTION_GET_INSTALL);
    	HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);
        @SuppressWarnings("unused")
		HttpResponse response;
		try {
			response = client.execute(request);
			
		} 
		catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}


	@Override
	protected String doInBackground(Integer... params) {
		String url = getURLById(params[0], ACTION_GET_REQUEST);
    	HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);
        HttpResponse response;
        String html = "";
		try {
			response = client.execute(request);
			
		    InputStream in = response.getEntity().getContent();
		    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		    StringBuilder str = new StringBuilder();
		    String line = null;
		    while((line = reader.readLine()) != null){
		            str.append(line);
		    }
		    in.close();
		    html = str.toString();
		} 
		catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return html;
	} 
	
	
}
