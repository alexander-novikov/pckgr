/**
 * 
 */
package com.ximad.apkpackager.utils;

import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * @author Vladimir Baraznovsky
 * 
 */
public class FileUtils {
	public static void recursiveDelete(File path) throws IOException {
		if ((path == null) || (!path.exists())) {
			return;
		}
		if (path.isDirectory()) {
			for (File child : path.listFiles()) {
				recursiveDelete(child);
			}
		}
		if (!path.delete()) {
			throw new IOException("Could not delete " + path);
		}
	}

	
}
