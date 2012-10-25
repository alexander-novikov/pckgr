/**
 *
 */
package com.ximad.install.utils;

/**
 * @author Vladimir Baraznovsky
 *
 */
public class ThreadUtils {
	public static void runInDaemon(Runnable pRunnable){
		Thread thread = new Thread(pRunnable);
		thread.setDaemon(true);
		thread.start();
	}
}
