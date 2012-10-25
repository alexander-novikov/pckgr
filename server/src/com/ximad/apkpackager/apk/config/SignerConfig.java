/**
 * 
 */
package com.ximad.apkpackager.apk.config;

import java.io.File;


/**
 * @author Vladimir Baraznovsky
 * 
 */
public class SignerConfig implements ISignerConfig {

	private File keyStoreFile;
	private String keyAlias;
	private String keyStorePassword;
	private String keyAliasPassword;

	public File getKeyStoreFile() {
		return keyStoreFile;
	}

	public void setKeyStoreFile(File keyStoreFile) {
		this.keyStoreFile = keyStoreFile;
	}

	public String getKeyAlias() {
		return keyAlias;
	}

	public void setKeyAlias(String keyAlias) {
		this.keyAlias = keyAlias;
	}

	public String getKeyStorePassword() {
		return keyStorePassword;
	}

	public void setKeyStorePassword(String keyStorePassword) {
		this.keyStorePassword = keyStorePassword;
	}

	public String getKeyAliasPassword() {
		return keyAliasPassword;
	}

	public void setKeyAliasPassword(String keyAliasPassword) {
		this.keyAliasPassword = keyAliasPassword;
	}

}
