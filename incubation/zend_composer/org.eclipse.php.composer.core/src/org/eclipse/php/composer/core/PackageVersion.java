/*******************************************************************************
 * Copyright (c) 2013, 2016 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.core;

/**
 * 
 * @author Wojciech Galanciak, 2013
 * 
 */
public class PackageVersion implements Comparable<PackageVersion> {

	public static final PackageVersion UNKNOWN = new PackageVersion(-1, -1, -1,
			-1, Suffix.NONE, -1, "unknown"); //$NON-NLS-1$

	public enum Suffix {

		ALPHA("alpha"), //$NON-NLS-1$

		BETA("beta"), //$NON-NLS-1$

		RC("rc"), //$NON-NLS-1$

		DEV("dev"), //$NON-NLS-1$

		UNKNOWN(""), //$NON-NLS-1$

		NONE(null);

		private String name;

		private Suffix(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public static Suffix byName(String name) {
			if (name == null) {
				return UNKNOWN;
			}
			String value = parseSuffix(name);
			value = value.toLowerCase();
			Suffix[] values = values();
			for (Suffix suffix : values) {
				if (value.equals(suffix.getName())) {
					return suffix;
				}
			}
			return UNKNOWN;
		}

		private static String parseSuffix(String name) {
			return name.replaceAll("[0-9]", ""); // returns 123 //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	private int major;
	private int minor;
	private int build;
	private int revision;
	private Suffix suffix;
	private int suffixVersion;

	private String fullVersion;

	private PackageVersion(int major, int minor, int build, int revision,
			Suffix suffix, int suffixVersion, String fullVersion) {
		this.major = major;
		this.minor = minor;
		this.build = build;
		this.revision = revision;
		this.suffix = suffix;
		this.suffixVersion = suffixVersion;
		this.fullVersion = fullVersion;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.core.library.ILibraryVersion#getName()
	 */
	public String getName() {
		return major + "." + minor + "." + build + "." + revision; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.core.library.ILibraryVersion#getMajor()
	 */
	public int getMajor() {
		return major;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.core.library.ILibraryVersion#getMinor()
	 */
	public int getMinor() {
		return minor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.core.library.ILibraryVersion#getBuild()
	 */
	public int getBuild() {
		return build;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.core.library.ILibraryVersion#getRevision()
	 */
	public int getRevision() {
		return revision;
	}

	public Suffix getSuffix() {
		return suffix;
	}

	public int getSuffixVersion() {
		return suffixVersion;
	}

	public String toString() {
		return fullVersion;
	}

	public int compareTo(PackageVersion v) {
		// TODO include suffix support
		if (getMajor() < v.getMajor()) {
			return -1;
		}
		if (getMajor() > v.getMajor()) {
			return 1;
		}
		if (getMinor() != -1 && v.getMinor() != -1) {
			if (getMinor() < v.getMinor()) {
				return -1;
			}
			if (getMinor() > v.getMinor()) {
				return 1;
			}
		}
		if (getBuild() != -1 && v.getBuild() != -1) {
			if (getBuild() < v.getBuild()) {
				return -1;
			}
			if (getBuild() > v.getBuild()) {
				return 1;
			}
		}
		if (getRevision() != -1 && v.getRevision() != -1) {
			if (getRevision() < v.getRevision()) {
				return -1;
			}
			if (getRevision() > v.getRevision()) {
				return 1;
			}
		}
		int result = getSuffix().compareTo(v.getSuffix());
		if (result > 0) {
			return 1;
		}
		if (result < 0) {
			return -1;
		}
		if (result == 0) {
			if (suffixVersion > v.getSuffixVersion()) {
				return 1;
			}
			if (suffixVersion < v.getSuffixVersion()) {
				return -1;
			}
		}
		return 0;
	}

	public static PackageVersion byName(String name) {
		if (name == null) {
			return UNKNOWN;
		}
		return parse(name);
	}

	private static PackageVersion parse(final String name) {
		// TODO consider "self.version" value
		Suffix suffix = Suffix.NONE;
		int suffixVersion = -1;
		String toParse = name.trim();
		if (name == null || name.equals("*")) { //$NON-NLS-1$
			return UNKNOWN;
		}

		// e.g. v2.0.0
		if (toParse.startsWith("v") || toParse.startsWith("V")) { //$NON-NLS-1$ //$NON-NLS-2$
			toParse = toParse.substring(1);
		}

		// e.g. 2.0.0-dev or 2.0.0_dev,
		int index = -1;
		if (toParse.indexOf("-") != -1) { //$NON-NLS-1$
			index = toParse.indexOf("-"); //$NON-NLS-1$
		}
		if (toParse.indexOf("_") != -1) { //$NON-NLS-1$
			int i = toParse.indexOf("_"); //$NON-NLS-1$
			if (index == -1 || index > i) {
				index = i;
			}
		}
		if (index != -1) {
			String suffixString = toParse.substring(index + 1);
			suffix = Suffix.byName(suffixString);
			if (suffix != Suffix.UNKNOWN) {
				suffixVersion = parseSuffixVersion(suffixString);
			}
			toParse = toParse.substring(0, index);
		}

		String[] segments = toParse.split("\\."); //$NON-NLS-1$
		int[] result = new int[4];
		for (int i = 0; i < result.length; i++) {
			if (segments.length > i) {
				if (segments[i].equalsIgnoreCase("x")) { //$NON-NLS-1$
					result[i] = 9999999;
				} else if (segments[i].equalsIgnoreCase("*")) { //$NON-NLS-1$
					result[i] = -1;
				} else {
					try {
						result[i] = Integer.valueOf(segments[i]);
					} catch (NumberFormatException e) {
						result[i] = -1;
					}
				}
			} else {
				result[i] = 0;
			}
		}
		return new PackageVersion(result[0], result[1], result[2], result[3],
				suffix, suffixVersion, name);
	}

	private static int parseSuffixVersion(String name) {
		String val = name.replaceAll("[a-zA-Z]", ""); // returns 123 //$NON-NLS-1$ //$NON-NLS-2$
		if (!val.isEmpty()) {
			return Integer.valueOf(val);
		}
		return -1;
	}

}