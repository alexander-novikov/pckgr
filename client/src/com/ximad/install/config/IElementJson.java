/**
 *
 */
package com.ximad.install.config;

import org.json.JSONObject;

import com.ximad.install.exceptions.ConfigException;

/**
 * @author Vladimir Baraznovsky
 *
 */
public interface IElementJson {

	void parseJson(JSONObject pJsonObject) throws ConfigException;

}
