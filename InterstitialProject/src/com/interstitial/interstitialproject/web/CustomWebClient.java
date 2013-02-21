package com.interstitial.interstitialproject.web;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.interstitial.interstitialproject.ICallback;

import android.webkit.WebView;
import android.webkit.WebViewClient;

public class CustomWebClient extends WebViewClient{
	private int stepNumber;
	private ICallback context;
	private static CustomWebClient customWebClient;
	
	private CustomWebClient(){
		
	};
	
	public static CustomWebClient getInstance(int stepNumber, ICallback context){
		if (customWebClient == null)
			customWebClient = new CustomWebClient();
		
		customWebClient.setStepNumber(stepNumber);
		customWebClient.setContext(context);
		
		return customWebClient;
	}
	
	public int getStepNumber() {
		return stepNumber;
	}

	public void setStepNumber(int stepNumber) {
		this.stepNumber = stepNumber;
	}

	public ICallback getContext() {
		return context;
	}

	public void setContext(ICallback context) {
		this.context = context;
	}

	@Override
	public void onPageFinished(WebView view, String url) {
		context.showProgress();
		context.unlockButton(5000);
		
		if (url.contains("#")){
			context.installApplication();
			return;
		}
		
		if (URLHelper.checkURL(url)){
			stepNumber = getStepNmber(url);
			URLHelper helper = URLHelperFactory.createInstance();
			helper.getPage(stepNumber);
			
		}	
		
	}
	private int getStepNmber(String url) {
		int number = 0;
		Matcher matcher = Pattern.compile("&stepnumber=(.*?)&").matcher(url);
		while (matcher.find()){
			MatchResult result = matcher.toMatchResult();
			number = Integer.parseInt(result.group(1));

		}
		return number;
	}

}
