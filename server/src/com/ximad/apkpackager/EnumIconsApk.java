/**
 * 
 */
package com.ximad.apkpackager;

/**
 * @author Vladimir Baraznovsky
 * 
 */
public enum EnumIconsApk {

	ICON_MDPI("build.icon.160", "application-icon-160:'(.*)'"),
	ICON_HDPI("build.icon.240", "application-icon-240:'(.*)'"),
	ICON_XHDPI("build.icon.320", "application-icon-320:'(.*)'"),
	ICON_LDPI("build.icon.120", "application-icon-120:'(.*)'");

	private final String antProperty;
	private final String patternAapt;

	/**
	 * @param antProperty
	 * @param patternAapt
	 */
	private EnumIconsApk(String antProperty, String patternAapt) {
		this.antProperty = antProperty;
		this.patternAapt = patternAapt;
	}

	public String getAntProperty() {
		return antProperty;
	}

	public String getPatternAapt() {
		return patternAapt;
	}

}
