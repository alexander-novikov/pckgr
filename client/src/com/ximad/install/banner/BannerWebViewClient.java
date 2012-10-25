package com.ximad.install.banner;

import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class BannerWebViewClient extends WebViewClient {

	/**
	 *
	 */
	private IWebClientListener mWebClientListener;

	public BannerWebViewClient(IWebClientListener pWebClientListener) {
		mWebClientListener=pWebClientListener;
	}






	@Override
	public void onPageFinished(WebView view, String url) {
		super.onPageFinished(view, url);
		mWebClientListener.onPageFinished(url);
	}



	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String urlString) {
		return mWebClientListener.shouldOverrideUrlLoading(urlString);
	}



	@Override
	public void onReceivedError(WebView pView, int pErrorCode,
			String pDescription, String pFailingUrl) {
		super.onReceivedError(pView, pErrorCode, pDescription, pFailingUrl);
		mWebClientListener.onReceivedError(pErrorCode, pFailingUrl);
	}



	@Override
	public void onReceivedSslError(WebView pView, SslErrorHandler pHandler,
			SslError pError) {

		super.onReceivedSslError(pView, pHandler, pError);
	}



}