/**
 *
 */
package com.ximad.install.config.networks;

import org.json.JSONObject;

import com.ximad.install.config.networks.damix.IDamixType;
import com.ximad.install.config.networks.damix.DamixTypeFactory;
import com.ximad.install.exceptions.ConfigException;

/**
 * @author Vladimir Baraznovsky
 *
 */
public class DamixNetwork implements INetworkJson {

	private static final String NAME_FIELD_OF_TYPE = "type";
	private static final String NAME_FIELD_OF_OPTIONS = "options";
	private static final String NAME_FIELD_OF_PRIORITY = "priority";

	private IDamixType mType = null;
	private int mPriority;
	private boolean mAllowAlways;
	private String mBannerUrl;

	@Override
	public String getBannerUrl() {
		return mBannerUrl;
	}

	public void setBannerUrl(String pBannerUrl) {
		mBannerUrl = pBannerUrl;
	}

	@Override
	public void parseJson(JSONObject pJsonObject) throws ConfigException {
		String type = pJsonObject.optString(NAME_FIELD_OF_TYPE);
		JSONObject options = pJsonObject.optJSONObject(NAME_FIELD_OF_OPTIONS);
		mType = DamixTypeFactory.createType(type, options);
		mPriority=pJsonObject.optInt(NAME_FIELD_OF_PRIORITY);
		mType.applyOptions(this);
	}


	public int getPriority() {
		return mPriority;
	}


	@Override
	public boolean isAllowAlways() {
		return mAllowAlways;
	}

	public void setAllowAlways(boolean allowAlways) {
		mAllowAlways = allowAlways;
	}

}
