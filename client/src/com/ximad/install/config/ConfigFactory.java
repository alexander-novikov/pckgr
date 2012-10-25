/**
 *
 */
package com.ximad.install.config;

import org.json.JSONException;
import org.json.JSONObject;

import com.ximad.install.LoggerPackager;

/**
 * @author Vladimir Baraznovsky
 *
 */
public class ConfigFactory {
	public static Config createConfigFromJsonString(String pString) {
		try {
			return createConfigFromJson(new JSONObject(pString));
		} catch (JSONException e) {
			LoggerPackager.w(e);
		}
		return createConfig();
	}

	public static Config createConfig() {
		Config config = new Config();
		return config;
	}

	public static Config createConfigFromJson(JSONObject pJsonObject) {
		Config config = createConfig();
		config.parseJson(pJsonObject);
		return config;
	}
}
