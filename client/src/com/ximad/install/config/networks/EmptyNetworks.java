/**
 *
 */
package com.ximad.install.config.networks;

/**
 * @author Vladimir Baraznovsky
 *
 */
public class EmptyNetworks implements INetwork {

	@Override
	public String getBannerUrl() {
		return null;
	}




	@Override
	public boolean isAllowAlways() {
		return true;
	}

}
