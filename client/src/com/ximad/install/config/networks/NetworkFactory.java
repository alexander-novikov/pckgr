/**
 *
 */
package com.ximad.install.config.networks;

import org.json.JSONObject;

import com.ximad.install.exceptions.ConfigException;

/**
 * @author Vladimir Baraznovsky
 *
 */
public class NetworkFactory {
	private static final String NAME_FILED_OF_ID = "id";
	private static final int UNLOKER_ID = 107;

	private static INetworkJson createNetworkJsonById(int pId) throws ConfigException {
		switch (pId) {
		case UNLOKER_ID:
			return new DamixNetwork();
		default:
			throw new ConfigException("Not found network by id = "+String.valueOf(pId));
		}

	}

	public static INetwork createFromJson(JSONObject pJsonObject) throws ConfigException {
		if (pJsonObject == null) {
			return null;
		}
		int id = pJsonObject.optInt(NAME_FILED_OF_ID);
		INetworkJson network = createNetworkJsonById(id);
		network.parseJson(pJsonObject);

		return network;
	}

}
