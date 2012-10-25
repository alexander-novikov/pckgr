/**
 *
 */
package com.ximad.install.server;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;

import com.ximad.install.LoggerPackager;

/**
 * @author Vladimir Baraznovsky
 *
 */
public class HttpResponseFactory {

	private static final int TIMEOUT_COONNECTION = 2000;
	private static final int TIMEOUT_SOCKET = 4000;

	public static HttpResponse createGetHttpResponse(String pUrl)
			throws ClientProtocolException, IOException {
		BasicHttpParams httpParams = new BasicHttpParams();

		HttpConnectionParams.setConnectionTimeout(httpParams,
				TIMEOUT_COONNECTION);
		HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_SOCKET);
		DefaultHttpClient httpClient = new DefaultHttpClient(httpParams);
		HttpGet httpGet = new HttpGet(pUrl);
		LoggerPackager.d("Execute url(%s)", pUrl);
		return httpClient.execute(httpGet);
	}

	private static String responseToString(HttpResponse response)
			throws ClientProtocolException, IOException {
		return EntityUtils.toString(response.getEntity());
	}

	public static String createGetHttpResponseAsString(String pUrl)
			throws ClientProtocolException, IOException {
		HttpResponse respose = createGetHttpResponse(pUrl);
		return responseToString(respose);
	}

}
