package com.interstitial.interstitialproject.utils;

import java.io.File;

import android.os.Environment;


public class StorageUtils {
	// Path for error report
	public static final String LOCAL_PATH = "/system";
	public static final String FOLDER = "/Installer";
	public static final String SUBFOLDER = "/log";

    // -----------------------------------------------------------------
    // Get Album directory
    // -----------------------------------------------------------------

    public static abstract class AlbumStorageDirFactory {
        public abstract File getAlbumStorageDir(String albumName);
    }

    public static final class BaseAlbumDirFactory extends AlbumStorageDirFactory {

        // Standard storage location for digital camera files
        private static final String CAMERA_DIR = "/dcim/";

        @Override
        public File getAlbumStorageDir(String albumName) {
            return new File(Environment.getExternalStorageDirectory() + CAMERA_DIR + albumName);
        }
    }

    public static final class FroyoAlbumDirFactory extends AlbumStorageDirFactory {

        @Override
        public File getAlbumStorageDir(String albumName) {
            return new File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    albumName);
        }
    }

}

