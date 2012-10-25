/**
 * 
 */
package com.ximad.apkpackager.ant;

import java.io.File;

import org.apache.tools.ant.Project;

import com.ximad.apkpackager.apk.config.IConfigBuilder;
import com.ximad.apkpackager.apk.config.ISignerConfig;

/**
 * @author Vladimir Baraznovsky
 * 
 */
public class SignedAntBuilder extends AntBuilder {

	private final File keyStoreFile;
	private final String keyAlias;
	private final String keyStorePassword;
	private final String keyAliasPassword;

	
	public SignedAntBuilder(IConfigBuilder configBuilder,
			ISignerConfig signerConfig) {
		super(configBuilder);
		this.keyStoreFile = signerConfig.getKeyStoreFile();
		this.keyAlias = signerConfig.getKeyAlias();
		this.keyStorePassword = signerConfig.getKeyStorePassword();
		this.keyAliasPassword = signerConfig.getKeyAliasPassword();
	}

	@Override
	protected String getExecuteTarget() {
		return AntConst.TARGET_BUILD_SIGNER;
	}

	@Override
	protected void initAntProperties(Project projectAnt) {
		super.initAntProperties(projectAnt);
		projectAnt.setProperty(AntConst.PROPERTY_KEY_STORE,
				keyStoreFile.getAbsolutePath());
		projectAnt.setProperty(AntConst.PROPERTY_KEY_ALIAS, keyAlias);
		projectAnt.setProperty(AntConst.PROPERTY_KEY_STORE_PASSWORD,
				keyStorePassword);
		projectAnt.setProperty(AntConst.PROPERTY_KEY_ALIAS_PASSWORD,
				keyAliasPassword);

	}

}
