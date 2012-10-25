/**
 *
 */
package com.ximad.install;

import android.util.Log;

/**
 * @author Vladimir Baraznovsky
 *
 */
public class LoggerPackager {
	private static final String TAG = "Packager";
	private static final boolean DEBUG = true;

	private static void debug(String message) {
		Log.d(TAG, message);
	}

	private static void warn(Throwable e) {
		Log.w(TAG, e);
	}

	private static void warn(String message) {
		Log.w(TAG, message);
	}

	public static void d(String message) {
		if (DEBUG) {
			debug(message);
		}
	}

	public static void d(String message, Object... args) {
		if (DEBUG) {
			debug(String.format(message, args));
		}
	}
	public static void w(Throwable e) {
		if (DEBUG) {
			warn(e);
		}
	}
	public static void message(Throwable e) {
		if (DEBUG) {
			warn(e.getMessage());
		}
	}


}
