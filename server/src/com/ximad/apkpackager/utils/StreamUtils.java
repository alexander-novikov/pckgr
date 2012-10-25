package com.ximad.apkpackager.utils;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class StreamUtils {
	private static final int BUFFER_SIZE = 1024;

	public static void safeCloseStream(Closeable pCloseable) {
		if (pCloseable != null) {
			try {
				pCloseable.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void copyToStream(final InputStream pFrom,
			final OutputStream pTo) throws IOException {
		byte[] buffer = new byte[BUFFER_SIZE];
		int read;
		while ((read = pFrom.read(buffer)) != -1) {
			pTo.write(buffer, 0, read);
		}
		pTo.flush();
	}
}
