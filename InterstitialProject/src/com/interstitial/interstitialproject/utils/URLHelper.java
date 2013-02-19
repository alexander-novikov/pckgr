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

import com.interstitial.interstitialproject.CustomActivity;

import android.os.AsyncTask;


public class URLHelper extends AsyncTask<String, Void, String[]>{

	private CustomActivity activity;
	
	public URLHelper(CustomActivity activity) {
		super();
		this.activity = activity;
	}


	public static final String ACTION_GET_INSTALL = "install";
	public static final String ACTION_GET_CONVERTION = "convertion";
	public static final String ACTION_GET_IMPRESSION = "impression";
	public static final String ACTION_GET_REQUEST = "request";
	public static final String ACTION_GET_CLICK = "click";
	
	public static boolean checkURL(String url){
		return url.contains("9444");
	}
	
	
	public String getChainUrl(int step){

//
		
		StringBuffer url = new StringBuffer("http://5.9.102.121:9444/request?");
		url.append("pub_id=");
		//url.append( activity.getString(com.interstitial.interstitialproject.R.string.user_id));
		url.append("1");
		url.append("&platform=android");
		url.append("&tag=1");
		url.append("&app_id=123");
		url.append("&version=2");
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
		//url.append("&action="+action);
		url.append("&position=1");
		//url.append("&banner_id=1");
		
		return url.toString();
	}
	
	
	public String getDownloadURL(){
		//http://5.9.94.2:9444/download?pub_id=12&platform=android&tag=hello
		//&app_id=com.ximad.com&version=default&screenwidth=1024&screenheight=868&udid=112
		
		StringBuffer url = new StringBuffer("http://5.9.94.2:9444/download?");
		url.append("pub_id=");
		url.append("1");
		//url.append( activity.getString(com.interstitial.interstitialproject.R.string.user_id));
		url.append("&platform=android");
		url.append("&tag=1");
		url.append("&app_id=123");
		url.append("&version=2");
		url.append("&screenwidth=");
		url.append(PhoneHelper.getScreenWidth());
		url.append("&screenheight=");
		url.append(PhoneHelper.getScreenHeight());
		
		url.append("&udid=");
		url.append(PhoneHelper.getUDID());
		
		return url.toString();
	}
	
	private String getUnpackURL() {
		//http://5.9.94.2:9444/conversion?pub_id=12&platform=android&tag=hello&app_id=com.ximad.com&version=default&screenwidth=1024
		//&screenheight=868&udid=112&banner_id=1032&offer_id=102&stepnumber=2
		StringBuffer url = new StringBuffer("http://5.9.94.2:9444/conversion?");
		url.append("pub_id=");
		//url.append( activity.getString(com.interstitial.interstitialproject.R.string.user_id));
		url.append("1");
		url.append("&platform=android");
		url.append("&tag=1");
		url.append("&app_id=123");
		url.append("&version=2");
		url.append("&screenwidth=");
		url.append(PhoneHelper.getScreenWidth());
		url.append("&screenheight=");
		url.append(PhoneHelper.getScreenHeight());
		
		url.append("&udid=");
		url.append(PhoneHelper.getUDID());
		
		url.append("&banner_id=1");
		url.append("&offer_id=123");
		url.append("&stepnumber=6");
		return url.toString();
		
	}


	public void pingDownload() {
		String url = getDownloadURL();
		new URLHelper(activity).execute(url,ACTION_GET_INSTALL);
		
	}
	
	public void pingUnpack(){
		String url = getUnpackURL();
		new URLHelper(activity).execute(url,ACTION_GET_CONVERTION);
	}




	@Override
	protected String[] doInBackground(String... params) {
		String url = params[0];
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
		String[] results = {html, url, params[1] } ; 
		return results;
	}


	public void getPage(int stepNumber) {
		String url = getChainUrl(stepNumber);
		new URLHelper(activity).execute(url,ACTION_GET_REQUEST);
	}


	@Override
	protected void onPostExecute(String[] result) {
		super.onPostExecute(result);
		if (result[2].equals(ACTION_GET_REQUEST)){
			activity.onAsyncFinishLoading(result[0], result[1]);
		}
		
	} 
	
	
}
