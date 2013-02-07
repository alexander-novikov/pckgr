package com.interstitial.interstitialproject.dao;

import java.util.HashMap;
import java.util.HashSet;

/**
 * The Class Offer.
 */
public class Offer {
	
	/** The type. */
	private String type;
	
	/** The options. */
	private HashMap<String, String> options;
	
	/** The packages. */
	private HashSet<String> packages;
	
	/** The offer id. */
	private int offerId;
	
	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * Gets the options.
	 *
	 * @return the options
	 */
	public HashMap<String, String> getOptions() {
		return options;
	}
	
	/**
	 * Sets the options.
	 *
	 * @param options the options
	 */
	public void setOptions(HashMap<String, String> options) {
		this.options = options;
	}
	
	/**
	 * Gets the offer id.
	 *
	 * @return the offer id
	 */
	public int getOfferId() {
		return offerId;
	}
	
	/**
	 * Sets the offer id.
	 *
	 * @param offerId the new offer id
	 */
	public void setOfferId(int offerId) {
		this.offerId = offerId;
	}

	/**
	 * Gets the packages.
	 *
	 * @return the packages
	 */
	public HashSet<String> getPackages() {
		return packages;
	}

	/**
	 * Sets the packages.
	 *
	 * @param packages the new packages
	 */
	public void setPackages(HashSet<String> packages) {
		this.packages = packages;
	}
	
}
