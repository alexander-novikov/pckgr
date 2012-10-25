/**
 * 
 */
package com.ximad.apkpackager.apk.config;

import java.io.File;
import java.io.OutputStream;

import com.ximad.apkpackager.EnumIconsApk;

/**
 * @author Vladimir Baraznovsky
 * 
 */
public interface IConfigBuilder {

	File getProjectDirectory();

	void setProjectDirectory(File projectDirectory);

	File getTempGlobalDirectory();

	void setTempGlobalDirectory(File tempDirectory);

	File getTempBuildDirectory();

	void setTempBuildDirectory(File tempBuildDirectory);

	File getResultFile();

	void setResultFile(File resultFile);

	String getVersion();

	void setVersion(String version);

	String getVersionCode();

	void setVersionCode(String versionCode);

	File getAndroidSdkDirectory();

	void setAndroidSdkDirectory(File androidSdkDirectory);

	String getResultPackageName();

	void setResultPackageName(String resultPackageName);

	String getResultLabel();

	void setResultLabel(String resultLabel);

	File getApkFile();

	void setApkFile(File apkFile);

	File getBuildAntFile();

	void setBuildAntFile(File file);

	OutputStream getOutputStream();

	void setOutputStream(OutputStream outputStream);

		
	File getResultIcon(EnumIconsApk iconType);

	void putResultIcon(EnumIconsApk iconType,File file);
	
	String getApkLabel();

	void setApkLabel(String apkLabel);

	void setUserId(String value);
	String getUserId();

}
