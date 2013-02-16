/**
 * 
 */
package com.ximad.apkpackager.apk.config;

import java.io.File;
import java.io.IOException;

import com.ximad.apkpackager.EnumIconsApk;
import com.ximad.apkpackager.ServerConsts;
import com.ximad.apkpackager.apk.ApkBuild;
import com.ximad.apkpackager.apk.ApkFactory;

/**
 * @author Vladimir Baraznovsky
 * 
 */
public class ConfigFactory {
	
	public static final String VARIABLE_NAME_DIR_SDK="android.sdk";
	public static final String VARIABLE_NAME_ANT_PATH="android.packager.antnew";
	public static final String VARIABLE_NAME_PROJECT_PATH="android.packager.projectnew";
	public static final String VARIABLE_NAME_KEY_STORE="android.key.store";
	public static final String VARIABLE_NAME_KEY_ALIAS="android.key.alias";
	public static final String VARIABLE_NAME_KEY_STORE_PASSWORD="android.key.store.password";
	public static final String VARIABLE_NAME_KEY_ALIAS_PASSWORD="android.key.alias.password";


	public static final String LABEL_CLIENT_PACKAGER = "%s Installer";
	public static final String RESULT_FILE_NAME = "Installer_%s.apk";
	private static final String PREFIX_OF_ICON = "icon_";
	private static final String SUFIX_OF_ICON = ".png";
	private static final String PACKAGE_NAME_CLIENT_PACKAGER = "installer.%s";
	private static final char CHAR_SPACE = ' ';
	private static final char CHAR_FILE_NAME_SPACE = '_';
	

	public static IConfigBuilder createDefaultConfig(File tempDirectory) {
		IConfigBuilder configBuilder = new Config();
		String sdkDir = System.getProperty(ConfigFactory.VARIABLE_NAME_DIR_SDK);
		String projectPath = System
				.getProperty(ConfigFactory.VARIABLE_NAME_PROJECT_PATH);
		configBuilder.setBuildAntFile(new File(System
				.getProperty(ConfigFactory.VARIABLE_NAME_ANT_PATH)));
		configBuilder.setAndroidSdkDirectory(new File(sdkDir));
		configBuilder.setProjectDirectory(new File(projectPath));
		configBuilder.setOutputStream(null);
		configBuilder.setTempGlobalDirectory(tempDirectory);

		configBuilder.setResultFile(new File(configBuilder
				.getTempGlobalDirectory(), ServerConsts.RESULT_FILE_NAME_DEFAULT));
		configBuilder.setTempBuildDirectory(new File(configBuilder
				.getTempGlobalDirectory(), ServerConsts.TEMP_PROJECT_NAME));
		return configBuilder;
	}

	public static ISignerConfig createSigner() {
		ISignerConfig signerConfig = new SignerConfig();
		String keyStore = System
				.getProperty(ConfigFactory.VARIABLE_NAME_KEY_STORE);
		String keyAlias = System
				.getProperty(ConfigFactory.VARIABLE_NAME_KEY_ALIAS);
		String keyStorePassword = System
				.getProperty(ConfigFactory.VARIABLE_NAME_KEY_STORE_PASSWORD);
		String keyAliasPassword = System
				.getProperty(ConfigFactory.VARIABLE_NAME_KEY_ALIAS_PASSWORD);
		signerConfig.setKeyStoreFile(new File(keyStore));
		signerConfig.setKeyAlias(keyAlias);
		signerConfig.setKeyStorePassword(keyStorePassword);
		signerConfig.setKeyAliasPassword(keyAliasPassword);
		return signerConfig;
	}

	public static void initParametrConfigFromApkFile(
			IConfigBuilder configBuilder) throws IOException {
		ApkBuild apk = ApkFactory.createApkBuild(configBuilder.getApkFile(),
				configBuilder.getAndroidSdkDirectory());

		for (EnumIconsApk enumIconsApk : EnumIconsApk.values()) {
			File fileIcon = File.createTempFile(PREFIX_OF_ICON, SUFIX_OF_ICON,
					configBuilder.getTempGlobalDirectory());
			if (apk.saveIcon(enumIconsApk, fileIcon)) {
				configBuilder.putResultIcon(enumIconsApk, fileIcon);
			}
		}

		configBuilder.setApkLabel(apk.getLabel());
		configBuilder.setResultLabel(String.format(
				ConfigFactory.LABEL_CLIENT_PACKAGER, apk.getLabel()));
		configBuilder.setResultPackageName(String.format(
				ConfigFactory.PACKAGE_NAME_CLIENT_PACKAGER, apk.getPackageName()));
		configBuilder.setResultFile(new File(configBuilder
				.getTempGlobalDirectory(), String.format(RESULT_FILE_NAME,
				apk.getLabel().replace(CHAR_SPACE, CHAR_FILE_NAME_SPACE))));

	}

	
}
