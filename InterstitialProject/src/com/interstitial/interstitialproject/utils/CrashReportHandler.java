package com.interstitial.interstitialproject.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;



public class CrashReportHandler implements UncaughtExceptionHandler {
	private Context mContext;

	private UncaughtExceptionHandler defaultUEH;

	private CrashReportHandler(Context context) {
		mContext = context;
	}

	public static void attach(Context context) {
		Thread.setDefaultUncaughtExceptionHandler(new CrashReportHandler(
				context));
	}

	@Override
	public void uncaughtException(Thread t, Throwable e) {
		String timestamp = "" + System.currentTimeMillis();
		final Writer result = new StringWriter();
		final PrintWriter printWriter = new PrintWriter(result);
		e.printStackTrace(printWriter);
		String stacktrace = result.toString();
		printWriter.close();
		String filename = timestamp + ".stacktrace";
		writeToFile(stacktrace, filename);

		defaultUEH.uncaughtException(t, e);

		e.printStackTrace();
		Log.e("Uncaught Error", stacktrace);
	}

	private void writeToFile(String stacktrace, String filename) {
		try {
			String extStorageDirectory = Environment
					.getExternalStorageDirectory().toString();
			File myNewFolder = new File(extStorageDirectory
					+ StorageUtils.FOLDER);
			myNewFolder.mkdir();
			File myNewSubfolder = new File(extStorageDirectory
					+ StorageUtils.FOLDER + StorageUtils.SUBFOLDER);
			myNewSubfolder.mkdir();

			String localPath = extStorageDirectory + StorageUtils.FOLDER
					+ StorageUtils.SUBFOLDER + "/" + filename;
			BufferedWriter bos = new BufferedWriter(new FileWriter(localPath));
			bos.write(stacktrace);
			bos.flush();
			bos.close();

			final SharedPreferences prefs = PreferenceManager
					.getDefaultSharedPreferences(mContext);
			SharedPreferences.Editor editor = prefs.edit();
			editor.putString("ERROR_REPORT_PATH", localPath);
			editor.commit();
			Log.i("Error report", "Error report file was created.");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.exit(0);
	}
}