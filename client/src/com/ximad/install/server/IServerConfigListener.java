/**
 *
 */
package com.ximad.install.server;

/**
 * @author Vladimir Baraznovsky
 *
 */
public interface IServerConfigListener {
	void onGetConfigString(String pConfigString);
	void onErrorGetConfig();
}
