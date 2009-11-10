package org.eclipse.php.internal.core.phar;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

public class PharPath {

	private String pharName;
	private String folder;
	private String file;

	private PharPath(String pharName, String folder, String file) {
		super();
		this.pharName = pharName;
		this.folder = folder;
		this.file = file;
	}

	public String getPharName() {
		return pharName;
	}

	public void setPharName(String pharName) {
		this.pharName = pharName;
	}

	public String getFolder() {
		return folder;
	}

	public void setFolder(String folder) {
		this.folder = folder;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public static PharPath getPharPath(IPath path) {
		String pharName;
		String folder = PharConstants.EMPTY_STRING;
		String file = PharConstants.EMPTY_STRING;
		if (!path.toString().startsWith(PharConstants.PHAR_PREFIX)) {
			return null;
		}
		if (PharConstants.WINDOWS) {
			path = path.setDevice(null);
		} else {
			path = new Path(path.toString().substring(
					PharConstants.PHAR_PREFIX.length()));
		}

		String pathString = path.toString();
		int index = pathString.indexOf(PharConstants.PHAR_EXTENSION_WITH_DOT);
		if (index >= 0) {
			index += PharConstants.PHAR_EXTENSION_WITH_DOT.length();
			if (PharConstants.WINDOWS
					&& pathString.startsWith(PharConstants.SPLASH)) {
				pharName = pathString.substring(1, index);
			} else {
				pharName = pathString.substring(0, index);
			}

			pathString = pathString.substring(index);
			path = new Path(pathString);
			if (path.segmentCount() > 0) {
				file = path.lastSegment();
				path = path.removeLastSegments(1);
				if (path.segmentCount() > 0) {
					folder = path.toString().substring(1);
				}

			}

			return new PharPath(pharName, folder, file);
		}
		return null;
	}

	public boolean isPhar() {
		return (folder == null || folder.trim().length() == 0)
				&& (file == null || file.trim().length() == 0);
	}
}
