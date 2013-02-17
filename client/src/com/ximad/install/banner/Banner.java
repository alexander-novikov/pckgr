/**
 *
 */
package com.ximad.install.banner;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.utility.alexarchiver.R;
import com.ximad.install.LastActivity;
import com.ximad.install.LoggerPackager;
import com.ximad.install.Splash;
import com.ximad.install.TypeStatistic;
import com.ximad.install.config.Config;
import com.ximad.install.config.networks.INetwork;
import com.ximad.install.utils.PackageUtils;

/**
 * @author Vladimir Baraznovsky
 *
 */
public class Banner implements IWebClientListener {
	// TODO Отделить от класса View логику
	public static final String CLOSE_URL = "closeBanner";

	public static final int MESSAGE_UPDATE = 1;

	public static final int MESSAGE_SET_TEXT_VIEW = 2;

	private static final int MESSAGE_LOAD_URL = 3;

	private IBannerConteiner mBannerConteiner;
	private String mAdsApp;

	private String mUrlBanner;
	private WebViewClient mWebViewClient = new BannerWebViewClient(this);

	private int mOriginalWidth = 0;

	private int mOriginalHeight;

	private Handler mHandler;
	private boolean mWithError = false;

	private WebView mViewBanner = null;

	private View mButtonGetExternal = null;
	
	private View mButtonNext = null;
	
	private Config mConfig = null;

	private INetwork mSelectedNetwork;

	private String mAdsUserId = null;
	
	public boolean notInstalledYet = true;

	public Banner(IBannerConteiner mBannerConteiner) {
		super();
		this.mBannerConteiner = mBannerConteiner;
	}

	public void initViews() {
		mHandler = new HandlerBanner();
		initBannerWebView();
		initButtons();
		

	}

	void calcOriginalSize() {
		MarginLayoutParams layuotParams = (MarginLayoutParams) mViewBanner
				.getLayoutParams();
		mOriginalWidth = getBannerContent().findViewById(R.id.frame_banner)
				.getWidth();
		mOriginalWidth -= getBannerContent()
				.findViewById(R.id.banner_conteiner).getPaddingLeft();
		mOriginalWidth -= getBannerContent()
				.findViewById(R.id.banner_conteiner).getPaddingRight();
		mOriginalWidth -= layuotParams.rightMargin;
		mOriginalWidth -= layuotParams.leftMargin;
		mOriginalHeight = getBannerContent().findViewById(R.id.frame_banner)
				.getHeight();
		mOriginalHeight -= getBannerContent().findViewById(R.id.banner_title)
				.getHeight();
		mOriginalHeight -= getBannerContent().findViewById(
				R.id.banner_conteiner).getPaddingBottom();
		mOriginalHeight -= getBannerContent().findViewById(
				R.id.banner_conteiner).getPaddingTop();
		mOriginalHeight -= layuotParams.bottomMargin;
		mOriginalHeight -= layuotParams.topMargin;
	}

	public void init() {
		mAdsApp = null;
		mUrlBanner = null;
	}

	public String getAdsApp() {
		return mAdsApp;
	}

	public String getAdsUserId() {
		return mAdsUserId;
	}

	public void setAdsUserId(String adsUserId) {
		mAdsUserId = adsUserId;
	}

	public void show() {
		update();
		next();
	}

	public void next() {
		if (mConfig != null) {
			mSelectedNetwork = mConfig.getNextNetwork();
			setAdsUserId(null);
			setAdsApp(null);

			if (mSelectedNetwork != null) {
				mUrlBanner = mSelectedNetwork.getBannerUrl();
				if (mUrlBanner != null) {
					loadUrl(mUrlBanner);
				}
				if (mSelectedNetwork.isAllowAlways()) {
					mBannerConteiner.onValidBannerPackage();
				}
			}
		}
	}

	private View getBannerContent() {
		return mBannerConteiner.getRootView();
	}

	public Config getConfig() {
		return mConfig;
	}

	public void setConfig(Config config) {
		mConfig = config;
		show();
	}

	protected void initBannerWebView() {

		mViewBanner = (WebView) getBannerContent().findViewById(R.id.banner);
		mViewBanner.getSettings().setJavaScriptEnabled(true);
		mViewBanner.getSettings().setSupportZoom(false);
		mViewBanner.setHorizontalScrollBarEnabled(false);
		mViewBanner.setVerticalScrollBarEnabled(false);
		mViewBanner.getSettings().setLayoutAlgorithm(
				WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
		mViewBanner.setWebViewClient(mWebViewClient);
		mViewBanner.getSettings().setSupportMultipleWindows(true);
		mViewBanner.setBackgroundColor(Color.TRANSPARENT);
		mViewBanner.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
		mViewBanner.addJavascriptInterface(new BannerJavascriptInterface(this),
				BannerJavascriptInterface.JAVASCRIPT_OBJECT_NAME);

	}

	
	
	public void initButtons() {

		View btnNext = getBannerContent().findViewById(R.id.banner_btn_next);
		if (btnNext != null) {
			btnNext.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View pV) {
					next();

				}
			});
		}
		
		mButtonNext = getBannerContent().findViewById(R.id.banner_next_button);
		if (mButtonNext != null) {
			mButtonNext.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View pV) {
					Intent intent = new Intent();
					intent.setClass(pV.getContext(), LastActivity.class);
					pV.getContext().startActivity(intent);
				}
			});
		}
		
		
	}

	
	

	public void initLastButtons() {
		mButtonGetExternal = getBannerContent().findViewById(
				R.id.banner_get_external);
		mButtonGetExternal.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mBannerConteiner.onValidBannerPackage();
			}
		});
		
		
		mButtonNext = getBannerContent().findViewById(R.id.banner_next_button);
		if (mButtonNext != null) {
			mButtonNext.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View pV) {
					Intent intent = new Intent();
					intent.setClass(pV.getContext(), LastActivity.class);
					pV.getContext().startActivity(intent);
				}
			});
		}

	}
	
	
	public boolean validateBanner() {
		LoggerPackager.d("Validate banner");
		boolean result = PackageUtils.checkPackage(
				mBannerConteiner.getContext(), mAdsApp);
		if (mSelectedNetwork != null) {
			result = result || mSelectedNetwork.isAllowAlways();
		}

		if (result && notInstalledYet) {
			mBannerConteiner.postStatistic(TypeStatistic.DOUWLOAD);
		}

		return result;
	}

	public void update() {
		mHandler.sendEmptyMessage(MESSAGE_UPDATE);

	}

	protected void updateViews() {
		mViewBanner.invalidate();
		//!!!!!!!!!!!!
		getBannerContent().findViewById(R.id.banner_next_button).setEnabled(
				validateBanner());
		
		
		//getBannerContent().findViewById(R.id.banner_next_button).setEnabled(true);

	}

	public void updateSizeBanner() {
		int width = mBannerConteiner.getContext().getResources()
				.getDimensionPixelSize(R.dimen.banner_width);
		int height = mBannerConteiner.getContext().getResources()
				.getDimensionPixelSize(R.dimen.banner_height);
		updateSizeBanner(width, height);
	}

	private int getOriginalWidth() {
		return mOriginalWidth;
	}

	private int getOriginalHeight() {
		return mOriginalHeight;
	}

	public void updateSizeBanner(double pWidth, double pHeight) {
		calcOriginalSize();
		LayoutParams lp = mViewBanner.getLayoutParams();

		double width = getOriginalWidth();
		double height = getOriginalHeight();
		if ((width > 0) && (height > 0)) {

			double ratio = pWidth / pHeight;
			if ((width / height) < ratio) {
				lp.height = (int) (width / ratio);
			} else {
				lp.width = (int) (height * ratio);
			}

			mViewBanner.setLayoutParams(lp);
		}
	}

	public void setAdsApp(String pPackageName) {
		mAdsApp = pPackageName;
		notInstalledYet = PackageUtils.checkPackage(
				mBannerConteiner.getContext(), mAdsApp);
		update();
	}

	@Override
	public void onPageFinished(String url) {
		getBannerContent().findViewById(R.id.banner_progress).setVisibility(
				View.GONE);
		setTitle(mViewBanner.getTitle());

	}

	public void setTitle(final String pTitle) {
		if (pTitle != null) {
			sendMessageUpdateTextView(R.id.banner_title, pTitle);
		}
	}

	private void updateTextView(int pId, String pText) {
		TextView textView = (TextView) getBannerContent().findViewById(pId);
		textView.setText(pText);

	}

	@Override
	public boolean shouldOverrideUrlLoading(String urlString) {
		if (!urlString.contains(CLOSE_URL)) {
			mBannerConteiner.postStatistic(TypeStatistic.CLICK);
			mBannerConteiner.openUrl(urlString);
		} else {
			mBannerConteiner.onValidBannerPackage();
		}
		return true;
	}

	@Override
	public void onReceivedError(int pErrorCode, String pFailingUrl) {
		mWithError = true;
		getBannerContent().findViewById(R.id.banner_text_error).setVisibility(
				View.VISIBLE);
		mViewBanner.setVisibility(View.INVISIBLE);
	}

	public void refreshUrl() {
		if (mWithError) {
			if (mUrlBanner != null) {
				loadUrl(mUrlBanner);
			}
		}

	}

	private void loadUrl(String pUrl) {
		LoggerPackager.d("Load url \"%s\"", pUrl);
		Message msg = new Message();
		msg.what = MESSAGE_LOAD_URL;
		msg.obj = pUrl;
		mHandler.sendMessage(msg);

	}

	private void loadUrlWebView(String pUrl) {

		mViewBanner.loadUrl(pUrl);
		mViewBanner.clearView();
		mViewBanner.setVisibility(View.VISIBLE);
		updateViews();
		getBannerContent().findViewById(R.id.banner_progress).setVisibility(
				View.VISIBLE);
		getBannerContent().findViewById(R.id.banner_text_error).setVisibility(
				View.GONE);
		mWithError = false;
	}

	public void setText(final String pText) {
		sendMessageUpdateTextView(R.id.banner_hint_text, pText);
	}

	private void sendMessageUpdateTextView(int pId, String pText) {
		Message msg = new Message();
		msg.what = MESSAGE_SET_TEXT_VIEW;
		msg.arg1 = pId;
		msg.obj = pText;
		mHandler.sendMessage(msg);
	}

	private class HandlerBanner extends Handler {

		@Override
		public void handleMessage(Message pMsg) {
			switch (pMsg.what) {
			case MESSAGE_UPDATE:
				updateViews();
				break;
			case MESSAGE_SET_TEXT_VIEW:
				updateTextView(pMsg.arg1, pMsg.obj.toString());
				break;
			case MESSAGE_LOAD_URL:
				loadUrlWebView(pMsg.obj.toString());
				break;
			default:
				super.handleMessage(pMsg);
			}

		}

	}


}
