/**
 * 
 */
package com.ximad.apkpackager.utils;

import org.apache.tomcat.util.http.fileupload.FileItem;

/**
 * @author Vladimir Baraznovsky
 * 
 */
public interface IUploadListener<Data> {
	boolean onInitParameter(Data data, String name, String value);

	boolean onInitFileParameter(Data data, FileItem item);
}
