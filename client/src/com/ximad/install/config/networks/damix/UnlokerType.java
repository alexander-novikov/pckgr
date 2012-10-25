/**
 *
 */
package com.ximad.install.config.networks.damix;

import org.json.JSONObject;

import com.ximad.install.config.networks.DamixNetwork;

/**
 * @author Vladimir Baraznovsky
 *
 */
public class UnlokerType implements IDamixType {

	private static final String NAME_FIELD_OF_URL = "url";
	private static final String NAME_FIELD_OF_ENABLED = "enabled";
	private String mUrl=null;
	private boolean mEnabled=true;


	@Override
	public void parseOptionsJson(JSONObject pJsonObject) {
		mUrl=pJsonObject.optString(NAME_FIELD_OF_URL);
		mEnabled=pJsonObject.optBoolean(NAME_FIELD_OF_ENABLED,true);
	}

	@Override
	public String getUrl() {
		return mUrl;
	}

	@Override
	public void applyOptions(DamixNetwork pDamixNetwork) {
		pDamixNetwork.setAllowAlways(!mEnabled);
		pDamixNetwork.setBannerUrl(mUrl);

	}



}
