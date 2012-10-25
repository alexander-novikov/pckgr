/**
 *
 */
package com.ximad.install;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
import android.util.Log;

/**
 * @author Vladimir Baraznovsky
 *
 */
public class ServerConnector {

	private static final String URL_OF_WEB_STAT = "http://opt.ximad.com/sdk/download/?platform=android&app=%s&pub_id=ximad&version=prototype`";
	private static final String NAME_SHARED_PROPERTIES_STATE = "stat";
	private static final String NAME_PROPERTY_WEB = "hasWeb";
	private static final int TIMEOUT_COONNECTION = 2000;
	private static final int TIMEOUT_SOCKET = 4000;

	final Context mContext;


	public ServerConnector(Context mContext) {
		super();
		this.mContext = mContext;
	}

	public void postStatistic(final String pPackageName) {
		new Handler().post(new Runnable() {

			@Override
			public void run() {
				SharedPreferences properties = mContext.getSharedPreferences(
						NAME_SHARED_PROPERTIES_STATE, Context.MODE_PRIVATE);
				boolean hasWebStat = properties.getBoolean(NAME_PROPERTY_WEB, false);
				if (!hasWebStat) {
					if (postWebStat(pPackageName)) {
						Editor editor = properties.edit();
						editor.putBoolean(NAME_PROPERTY_WEB, true);
						editor.commit();
					}
				}
			}
		});


	}

	public boolean postWebStat(final String pPackageName) {

		BasicHttpParams httpParams=new BasicHttpParams();

		HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT_COONNECTION);
		HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_SOCKET);
		DefaultHttpClient httpClient = new DefaultHttpClient(httpParams);
		HttpGet httpGet = new HttpGet(String.format(URL_OF_WEB_STAT,pPackageName));

		try {
			httpClient.execute(httpGet);
			return true;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

}
