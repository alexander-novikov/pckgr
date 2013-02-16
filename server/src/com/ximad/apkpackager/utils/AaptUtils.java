/**
 * 
 */
package com.ximad.apkpackager.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import com.ximad.apkpackager.apk.IBadgingListener;

/**
 * @author Vladimir Baraznovsky
 * 
 */
public class AaptUtils {

	private static final class CommandApptConst {
		private final static String ATRIBUTE_DUMP = "d";
		private final static String ATRIBUTE_BADGING = "badging";
	}

	public static void execDumpBadging(final File aaptFile, final File apkFile,
			IBadgingListener badgingListener) {
		Runtime run = Runtime.getRuntime();

		String command[] = new String[] { aaptFile.getAbsolutePath(),
				CommandApptConst.ATRIBUTE_DUMP,
				CommandApptConst.ATRIBUTE_BADGING, apkFile.getAbsolutePath() };

		Process currentProcess;
		try {
			currentProcess = run.exec(command);

			currentProcess.waitFor();
			if (currentProcess.exitValue() == 0) {
				InputStreamReader in = new InputStreamReader(
						currentProcess.getInputStream());

				BufferedReader buf = new BufferedReader(in);
				String line = buf.readLine();
				while ((line != null)) {
					if (!line.equals("")) {
						badgingListener.onGetStringProperty(line);
					}
					line = buf.readLine();
				}
				
				buf.close();
				in.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

}
