/**
 *
 */
package com.ximad.install.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

import com.ximad.install.LoggerPackager;
import com.ximad.install.config.networks.EmptyNetworks;
import com.ximad.install.config.networks.INetwork;
import com.ximad.install.config.networks.NetworkFactory;
import com.ximad.install.exceptions.ConfigException;

/**
 * @author Vladimir Baraznovsky
 *
 */
public class Config implements IElementJson {
	private static final String NAME_FIELD_OF_INETWORKS = "inetworks";
	Random mRandom = new Random();
	private List<INetwork> mListINetworks = new ArrayList<INetwork>();

	public INetwork getNextNetwork() {
		int count = mListINetworks.size();
		if (count < 1) {
			return null;
		}
		return mListINetworks.get(mRandom.nextInt(count));
	}

	@Override
	public void parseJson(JSONObject pJsonObject) {
		mListINetworks.clear();
		JSONArray inetworks = pJsonObject.optJSONArray(NAME_FIELD_OF_INETWORKS);
		if (inetworks != null) {
			for (int i = 0; i < inetworks.length(); i++) {
				JSONObject jsonObject = inetworks.optJSONObject(i);
				INetwork network;
				try {
					network = NetworkFactory.createFromJson(jsonObject);
					if (network != null) {
						mListINetworks.add(network);
					}
				} catch (ConfigException e) {
					LoggerPackager.message(e);
				}
			}
		}

		if (mListINetworks.size()<1){
			mListINetworks.add(new EmptyNetworks());
		}
	}

}
