/**
 *
 */
package com.ximad.install.utils;

import java.lang.reflect.Type;

import com.ximad.install.TypeStatistic;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * @author Vladimir Baraznovsky
 *
 */
public class SharedPrefencesUtils {
	private static final String NAME_SHARED_PROPERTIES_STATE = "stat";
	private static final String NAME_PROPERTY_DOWNLOAD = "hasDownload";
	private static final String NAME_PROPERTY_CLICK = "hasClick";

	public static boolean isHasWebStatistic(Context pContext,
			TypeStatistic pTypeStatistic) {
		SharedPreferences properties = pContext.getSharedPreferences(
				NAME_SHARED_PROPERTIES_STATE, Context.MODE_PRIVATE);

		boolean hasWebStat = false;
		String nameProperty = getNamePropertyStatistic(pTypeStatistic);
		if (nameProperty != null) {
			hasWebStat = properties.getBoolean(nameProperty, false);
		}
		return hasWebStat;
	}

	public static void setHasWebStatistic(Context pContext,
			TypeStatistic pTypeStatistic) {
		SharedPreferences properties = pContext.getSharedPreferences(
				NAME_SHARED_PROPERTIES_STATE, Context.MODE_PRIVATE);
		Editor editor = properties.edit();
		String nameProperty = getNamePropertyStatistic(pTypeStatistic);
		if (nameProperty != null) {
			editor.putBoolean(nameProperty, true);
			editor.commit();
		}
	}

	private static String getNamePropertyStatistic(TypeStatistic pTypeStatistic) {
		switch (pTypeStatistic) {
		case DOUWLOAD:
			return NAME_PROPERTY_DOWNLOAD;
		case CLICK:
			return NAME_PROPERTY_CLICK;
		default:
			return null;
		}
	}

}
