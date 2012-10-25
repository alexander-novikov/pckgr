/**
 *
 */
package com.ximad.install.config.networks.damix;

import org.json.JSONObject;

import com.ximad.install.config.networks.DamixNetwork;
import com.ximad.install.exceptions.ConfigException;

/**
 * @author Vladimir Baraznovsky
 *
 */
public interface IDamixType{
	String getUrl();
	void applyOptions(DamixNetwork damixNetwork);
	void parseOptionsJson(JSONObject pJsonObject) throws ConfigException;

}
