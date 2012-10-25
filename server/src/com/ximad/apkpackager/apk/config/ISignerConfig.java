package com.ximad.apkpackager.apk.config;

import java.io.File;

public interface ISignerConfig {
	
	public File getKeyStoreFile();
	public void setKeyStoreFile(File keyStoreFile);
	public String getKeyAlias();

	public void setKeyAlias(String keyAlias);

	public String getKeyStorePassword();

	public void setKeyStorePassword(String keyStorePassword);

	public String getKeyAliasPassword();
	public void setKeyAliasPassword(String keyAliasPassword);
	

}
