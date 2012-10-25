/**
 *
 */
package com.ximad.install.exceptions;

/**
 * @author Vladimir Baraznovsky
 *
 */
public class ConfigException extends Exception {

	private static final long serialVersionUID = -9152646136471714429L;

	public ConfigException() {

	}

	public ConfigException(String pDetailMessage) {
		super(pDetailMessage);
	}

	public ConfigException(Throwable pThrowable) {
		super(pThrowable);
	}

	public ConfigException(String pDetailMessage, Throwable pThrowable) {
		super(pDetailMessage, pThrowable);

	}

}
