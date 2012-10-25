/**
 *
 */
package com.ximad.install.config.networks.damix;


/**
 * @author Vladimir Baraznovsky
 *
 */
public enum EnumUnlokerTypes {
	UNLOKER("unloker",UnlokerType.class);

	private final Class<? extends IDamixType> mClass;
	private  final String mName;



	private EnumUnlokerTypes(String pName, Class<? extends IDamixType> pClass) {
		mName = pName;
		mClass = pClass;
	}

	public Class<? extends IDamixType> getClassType() {
		return mClass;
	}
	public String getName() {
		return mName;
	}

	public static EnumUnlokerTypes findByName(String pTypeString) {

		for (EnumUnlokerTypes enumUnlokerTypes : values()) {
			if (pTypeString.equalsIgnoreCase(enumUnlokerTypes.getName())) {
				return enumUnlokerTypes;
			}
		}
		return null;
	}


}
