package com.ximad.apkpackager.apk.config;

import java.io.File;
import java.io.OutputStream;
import java.util.EnumMap;
import java.util.Map;

import com.ximad.apkpackager.EnumIconsApk;

public class Config implements IConfigBuilder {
	private File buildAntFile;
	/**
	 * directory of android project packager
	 */
	private File projectDirectory;
	private File tempBuildDirectory;
	/**
	 * result apk-build of packager
	 */
	private File resultFile;

	private String version;
	private String versionCode;
	/**
	 * Path of Android SDK
	 */
	private File androidSdkDirectory;
	/**
	 * Package Name of result application
	 */
	private String resultPackageName;
	/**
	 * Label of result application
	 */
	private String resultLabel;
	private File apkFile;

	private OutputStream outputStream = null;
	private File tempGlobalDirectory;

	private Map<EnumIconsApk, File> resultIcons = new EnumMap<EnumIconsApk, File>(
			EnumIconsApk.class);
	private String apkLabel;
	private String userId;

	public File getProjectDirectory() {
		return projectDirectory;
	}

	public void setProjectDirectory(File projectDirectory) {
		this.projectDirectory = projectDirectory;
	}

	public File getTempBuildDirectory() {
		return tempBuildDirectory;
	}

	public void setTempBuildDirectory(File tempBuildDirectory) {
		this.tempBuildDirectory = tempBuildDirectory;
	}

	public File getResultFile() {
		return resultFile;
	}

	public void setResultFile(File resultFile) {
		this.resultFile = resultFile;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}

	public File getAndroidSdkDirectory() {
		return androidSdkDirectory;
	}

	public void setAndroidSdkDirectory(File androidSdkDirectory) {
		this.androidSdkDirectory = androidSdkDirectory;
	}

	public String getResultPackageName() {
		return resultPackageName;
	}

	public void setResultPackageName(String resultPackageName) {
		this.resultPackageName = resultPackageName;
	}

	public String getResultLabel() {
		return resultLabel;
	}

	public void setResultLabel(String resultLabel) {
		this.resultLabel = resultLabel;
	}

	public File getApkFile() {
		return apkFile;
	}

	public void setApkFile(File apkFile) {
		this.apkFile = apkFile;
	}

	public File getBuildAntFile() {
		return buildAntFile;
	}

	public OutputStream getOutputStream() {
		return outputStream;
	}

	public void setOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
	}

	@Override
	public void setBuildAntFile(File file) {
		buildAntFile = file;
	}

	@Override
	public File getTempGlobalDirectory() {
		return tempGlobalDirectory;
	}

	@Override
	public void setTempGlobalDirectory(File tempDirectory) {
		this.tempGlobalDirectory = tempDirectory;

	}

	

	@Override
	public File getResultIcon(EnumIconsApk iconType) {
		return resultIcons.get(iconType);
	}

	@Override
	public void putResultIcon(EnumIconsApk iconType, File file) {
		resultIcons.put(iconType, file);

	}

	@Override
	public String getApkLabel() {
		return this.apkLabel;
	}

	@Override
	public void setApkLabel(String apkLabel) {
		this.apkLabel = apkLabel;
	}

	@Override
	public void setUserId(String value) {
		userId=value;
	}

	@Override
	public String getUserId() {
		return userId;
	}

}
