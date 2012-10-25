/**
 *
 */
package com.ximad.install.banner;

import com.ximad.install.TypeStatistic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * @author Vladimir Baraznovsky
 *
 */
public interface IBannerConteiner {

	LayoutInflater getLayoutInflater();

	ViewGroup getRootView();

	Context getContext();

	public void onValidBannerPackage();

	void openUrl(String urlString);

	void finish();

	void postStatistic(TypeStatistic pType);

}
