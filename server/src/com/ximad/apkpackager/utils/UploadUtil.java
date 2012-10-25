/**
 * 
 */
package com.ximad.apkpackager.utils;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;


/**
 * @author Vladimir Baraznovsky
 * 
 */
public class UploadUtil {
	public static final long MAX_ZISE_UPLOAD_APK = 50 * 1024 * 1024;// 50MB
	public static final int UPLOAD_BUFFER_SIZE = 1024 * 1024;// 1MB
	public static final String PREFIX_OF_UPLOAD_FILE = "upload_";
	public static final int DOWNLOAD_BUFFER_SIZE = 1024;//1KB

	public static <Data> void initParameters(Data configBuilder,
			HttpServletRequest request, File tempDirectory,
			IUploadListener<Data> uploadListener) throws ServletException {

		// If Server is not Tomcat use Apache
		// http://commons.apache.org/fileupload/

		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(UploadUtil.UPLOAD_BUFFER_SIZE);
		factory.setRepository(tempDirectory);

		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setSizeMax(UploadUtil.MAX_ZISE_UPLOAD_APK);

		List<FileItem> items;
		try {
			items = upload.parseRequest(request);
		} catch (FileUploadException e) {
			// TODO FIX Exception
			e.printStackTrace();
			return;
		}
		Iterator<FileItem> iter = items.iterator();
		while (iter.hasNext()) {
			FileItem item = (FileItem) iter.next();
			if (item.isFormField()) {
				uploadListener.onInitParameter(configBuilder,
						item.getFieldName(), new String(item.get()));
			} else {
				uploadListener.onInitFileParameter(configBuilder, item);
			}
		}

	}

	public static File uploadFile(FileItem item, File directory) {
		String name = item.getName();
		File file = null;
		try {
			file = File.createTempFile(UploadUtil.PREFIX_OF_UPLOAD_FILE, name,
					directory);
			file.createNewFile();
			item.write(file);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return file;
	}

	

}
