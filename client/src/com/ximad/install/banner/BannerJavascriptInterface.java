/**
 *
 */
package com.ximad.install.banner;


/**
 * @author Vladimir Baraznovsky
 *
 */
public class BannerJavascriptInterface {

	public static final String JAVASCRIPT_OBJECT_NAME = "PackagerBanner";
	private Banner mBanner;

	/**
	 * @param banner
	 *
	 */
	public BannerJavascriptInterface(Banner pBanner) {
		this.mBanner=pBanner;
	}

	public void setAdsUserId(String pAdsUserId) {
		mBanner.setAdsUserId(pAdsUserId);
	}

	public void setAdsApp(String pPackageName) {
		mBanner.setAdsApp(pPackageName);
	}

	public void setText(String pText) {
		mBanner.setText(pText);
	}

}
