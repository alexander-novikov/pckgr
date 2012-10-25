/**
 * 
 */
package com.ximad.apkpackager.ant;

/**
 * @author Vladimir Baraznovsky
 * 
 */
public class AntConst {

	public static final String TARGET_BUILD_SIGNER = "create-signed-build";
	public static final String TARGET_BUILD_UNSIGNER = "create-unsigned-build";

	public static final String PROPERTY_SDK_DIR = "sdk.dir";
	public static final String PROPERTY_PROJECT_PATH = "project.path";
	public static final String PROPERTY_BUILD_PATH = "build.path";
	public static final String PROPERTY_BUILD_PACKAGE = "build.package";
	public static final String PROPERTY_BUILD_LABEL = "build.label";
	public static final String PROPERTY_APK_LABEL = "apk.label";
	public static final String PROPERTY_APK_PATH = "apk.path";
	
	public static final String PROPERTY_KEY_STORE = "key.store";
	public static final String PROPERTY_KEY_ALIAS = "key.alias";
	public static final String PROPERTY_KEY_STORE_PASSWORD = "key.store.password";
	public static final String PROPERTY_KEY_ALIAS_PASSWORD = "key.alias.password";

	public static final String PROPERTY_USER_ID = "banner.user_id";
	
}
