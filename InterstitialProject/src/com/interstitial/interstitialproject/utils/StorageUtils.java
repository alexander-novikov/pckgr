package com.interstitial.interstitialproject.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import android.os.Environment;


public class StorageUtils {
	// Path for error report
	public static final String LOCAL_PATH = "/system";
	public static final String FOLDER = "/Installer";
	public static final String SUBFOLDER = "/log";
	public static final String CHECKSUBBFOLDER = "/apps";

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
    
    public static void createEmptyFile(String filename) {
		try {
			String extStorageDirectory = Environment
					.getExternalStorageDirectory().toString();
			File myNewFolder = new File(extStorageDirectory
					+ StorageUtils.FOLDER);
			myNewFolder.mkdir();
			File myNewSubfolder = new File(extStorageDirectory
					+ StorageUtils.FOLDER + StorageUtils.CHECKSUBBFOLDER);
			myNewSubfolder.mkdir();

			String localPath = extStorageDirectory + StorageUtils.FOLDER
					+ StorageUtils.CHECKSUBBFOLDER + "/" + filename;
			BufferedWriter bos = new BufferedWriter(new FileWriter(localPath));
			bos.write("");
			bos.flush();
			bos.close();

			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    public static boolean checkIfExists(String filename){
    	String extStorageDirectory = Environment
				.getExternalStorageDirectory().toString();
    	String path = extStorageDirectory
				+ StorageUtils.FOLDER + StorageUtils.CHECKSUBBFOLDER+ "/" + filename;
    	
    	File file = new File(path);
    	return file.exists();
    }

}

