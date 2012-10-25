/**
 *
 */
package com.ximad.install.banner;

/**
 * @author Vladimir Baraznovsky
 *
 */
public interface IWebClientListener {

	void onPageFinished(String url);

	boolean shouldOverrideUrlLoading(String urlString);

	void onReceivedError(int pErrorCode, String pFailingUrl);

}
