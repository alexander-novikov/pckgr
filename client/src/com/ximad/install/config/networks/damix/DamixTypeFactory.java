package com.ximad.install.config.networks.damix;

import org.json.JSONObject;

import com.ximad.install.exceptions.ConfigException;

public class DamixTypeFactory {

	public static IDamixType createType(final String pTypeString,
			final JSONObject pOptions) throws ConfigException {
		EnumUnlokerTypes type = EnumUnlokerTypes.findByName(pTypeString);
		if (type == null) {
			throw new ConfigException("Not found type " + pTypeString);
		}
		try {
			IDamixType unlokerType = type.getClassType().newInstance();
			unlokerType.parseOptionsJson(pOptions);
			return unlokerType;
		} catch (IllegalAccessException e) {
			throw new ConfigException("Error in class of type "
					+ String.valueOf(type), e);
		} catch (InstantiationException e) {
			throw new ConfigException("Error in class of type "
					+ String.valueOf(type), e);
		}

	}

}
