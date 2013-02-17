package com.interstitial.interstitialproject.utils;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.content.res.AssetManager;

public class ExternalPackage {
	private final Context mContext;
	private final String mFileName;
	private final String mAssetsPath;

	public ExternalPackage(Context mContext, String mFileName,
			String mAssetsPath) {
		super();
		this.mContext = mContext;
		this.mFileName = mFileName;
		this.mAssetsPath = mAssetsPath;
	}

	public File getFile() {
		File file = getStorageFile(mFileName);
		if (!file.exists()) {

			copyToStorage();
		}
		return file;
	}

	private void copyToStorage() {
		AssetManager assetManager = mContext.getAssets();
		InputStream in = null;
		OutputStream out = null;

		try {
			in = assetManager.open(mAssetsPath);
			out = mContext.openFileOutput(mFileName,
					Context.MODE_WORLD_READABLE);
			StreamUtils.copyToStream(in, out);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			StreamUtils.safeCloseStream(in);
			StreamUtils.safeCloseStream(out);
		}

	}

	private File getStorageFile(String fileName) {
		return mContext.getFileStreamPath(fileName);
	}

//	public String getPackageName(){
//		File filePackage = getFile();
//		String packageName = PackageUtils.getPackageNameFromFile(mContext,
//				filePackage);
//		return packageName;
//	}
//	public boolean check() {
//		return PackageUtils.checkPackage(mContext, getPackageName());
//	}

}
