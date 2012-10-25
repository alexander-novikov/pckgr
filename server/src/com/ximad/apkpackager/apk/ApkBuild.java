/**
 * 
 */
package com.ximad.apkpackager.apk;

import java.awt.peer.LabelPeer;
import java.io.File;
import java.util.EnumMap;
import java.util.Map;

import com.ximad.apkpackager.EnumIconsApk;
import com.ximad.apkpackager.utils.RegExpUtils;
import com.ximad.apkpackager.utils.ZipUtils;

/**
 * @author Vladimir Baraznovsky
 * 
 */
public class ApkBuild implements IBadgingListener {

	// TODO CHECK patterns
	private static final String PROPERTY_LABEL = "application-label:'([^']*)'";
	private static final String PROPERTY_PACKAGE_NAME = "package: name='([^']*)'.*";

	private File file;
	private String label = null;
	private Map<EnumIconsApk, String> icons = new EnumMap<EnumIconsApk, String>(
			EnumIconsApk.class);
	private String packageName;

	ApkBuild(File file) {
		super();
		this.file = file;
	}

	private boolean saveIcon(String pathIcon, File toFile) {
		if (pathIcon == null) {
			return false;
		}
		ZipUtils.unpackFile(file, pathIcon, toFile);
		return true;
	}

	public boolean saveIcon(EnumIconsApk icon, File file) {
		return saveIcon(icons.get(icon), file);
	}

	public File getFile() {
		return file;
	}

	public String getLabel() {
		return label;
	}

	public String getPackageName() {
		return packageName;
	}

	@Override
	public void onGetStringProperty(String line) {

		String labelValue = RegExpUtils.getSingleGroupPattern(line,
				PROPERTY_LABEL);
		if (labelValue != null) {
			label = labelValue;
			return;
		}

		String packageNameValue = RegExpUtils.getSingleGroupPattern(line,
				PROPERTY_PACKAGE_NAME);
		if (packageNameValue != null) {
			packageName = packageNameValue;
			return;
		}

		for (EnumIconsApk enumIconsApk : EnumIconsApk.values()) {
			String iconValue = RegExpUtils.getSingleGroupPattern(line,
					enumIconsApk.getPatternAapt());
			if (iconValue != null) {
				icons.put(enumIconsApk, iconValue);
				return;
			}

		}

	}
}
