/**
 *
 */
package com.ximad.install.server;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadFactory;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.util.EntityUtils;

import android.content.Context;

import com.ximad.install.LoggerPackager;
import com.ximad.install.TypeStatistic;
import com.ximad.install.utils.SharedPrefencesUtils;
import com.ximad.install.utils.ThreadUtils;

/**
 * @author Vladimir Baraznovsky
 *
 */
public class ServerConnector {
	private static final String VERSION = "prototype";

	private static final String URL_OF_DOWNLOAD_STAT = "http://opt.ximad.com/sdk/download/?platform=android&ads_app=%s&ads_pub_id=%s&pub_id=%s&version=%s&app=a_magic_puzzles";
	private static final String URL_OF_CLICK_STAT = "http://opt.ximad.com/sdk/click/?platform=android&ads_app=%s&ads_pub_id=%s&pub_id=%s&version=%s";
	private static final String URL_OF_CONFIG = "http://fly.ximad.com/stat/json.html";

	private String mUserId;

	final Context mContext;

	public ServerConnector(Context pContext, String pUserId) {
		super();
		this.mContext = pContext;
		this.mUserId = pUserId;
	}

	public void postStatistic(final TypeStatistic pType, final String pAdsApp,
			final String pAdsUserId) {
		boolean hasWebStat = SharedPrefencesUtils.isHasWebStatistic(mContext,
				pType);
		if (hasWebStat) {
			return;
		}
		Runnable runPostStatistic = new Runnable() {

			@Override
			public void run() {
				postThreadStatistic(pType, pAdsApp, pAdsUserId);
			}
		};
		ThreadUtils.runInDaemon(runPostStatistic);
	}

	private void postThreadStatistic(final TypeStatistic pType,
			final String pAdsApp, final String pAdsUserId) {
		if (postWebStat(pType, pAdsApp, pAdsUserId)) {
			SharedPrefencesUtils.setHasWebStatistic(mContext, pType);
		}
	}

	private boolean postWebStat(final TypeStatistic pType,
			final String pAdsApp, String pAdsUserId) {
		try {
			String templateUrl = getUrlStatistic(pType);
			String url = String.format(templateUrl, pAdsApp, pAdsUserId,
					mUserId, VERSION);
			HttpResponseFactory.createGetHttpResponse(url);
			return true;
		} catch (ClientProtocolException e) {
			LoggerPackager.w(e);
		} catch (IOException e) {
			LoggerPackager.w(e);
		}
		return false;
	}

	private static String getUrlStatistic(TypeStatistic pTypeStatistic) {
		switch (pTypeStatistic) {
		case DOUWLOAD:
			return URL_OF_DOWNLOAD_STAT;
		case CLICK:
			return URL_OF_CLICK_STAT;
		default:
			return null;
		}
	}

	public void initConfig(final IServerConfigListener pServerConfigListener) {
		Runnable runPostStatistic = new Runnable() {

			@Override
			public void run() {
				initThreadConfig(pServerConfigListener);
			}
		};
		Thread thread = new Thread(runPostStatistic);
		thread.start();
	}

	private void initThreadConfig(
			final IServerConfigListener pServerConfigListener) {
		try {	
			String result = getWebConfig();
			pServerConfigListener.onGetConfigString(result);
		} catch (ClientProtocolException e) {
			LoggerPackager.w(e);
			pServerConfigListener.onErrorGetConfig();
		} catch (IOException e) {
			pServerConfigListener.onErrorGetConfig();
			LoggerPackager.w(e);
		}
	}

	private String getWebConfig() throws ClientProtocolException, IOException {
		String url = String.format(URL_OF_CONFIG, mUserId, VERSION);
		return HttpResponseFactory.createGetHttpResponseAsString(url);
	}

}
