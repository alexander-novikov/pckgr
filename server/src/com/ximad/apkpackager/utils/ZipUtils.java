/**
 * 
 */
package com.ximad.apkpackager.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

/**
 * @author Vladimir Baraznovsky
 *
 */
public class ZipUtils {

	public static void unpackFile(File zipFile, String from, File toFile) {
		ZipFile zip = null;
		InputStream inputStream = null;
		OutputStream fileOutputStream = null;
		try {
			zip = new ZipFile(zipFile);
			ZipEntry iconZipped = zip.getEntry(from);
			inputStream = zip.getInputStream(iconZipped);
			if (toFile.exists()) {
				toFile.delete();
			}
			toFile.createNewFile();
			fileOutputStream = new FileOutputStream(toFile);
			StreamUtils.copyToStream(inputStream, fileOutputStream);
	
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			StreamUtils.safeCloseStream(inputStream);
			StreamUtils.safeCloseStream(fileOutputStream);
		}
	}

}
