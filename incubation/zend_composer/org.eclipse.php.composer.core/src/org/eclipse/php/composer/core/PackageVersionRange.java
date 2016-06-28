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
 * @author Wojciech Galanciak, 2013
 * 
 */
public class PackageVersionRange {

	private String downVersion;
	private String upVersion;
	private VersionConstraint downRelation;
	private VersionConstraint upRelation;

	public static PackageVersionRange getRange(String input) {
		PackageVersionRange range = new PackageVersionRange();
		String[] segments = input.split(","); //$NON-NLS-1$
		if (segments.length > 0) {
			String down = null;
			String up = null;
			down = segments[0];
			if (segments.length == 2) {
				up = segments[1];
			}
			range.setDownVersion(parseVersion(down));
			range.setDownRelation(parseRelation(down));
			if (up != null) {
				range.setUpVersion(parseVersion(up));
				range.setUpRelation(parseRelation(up));
			}
		}
		return range;
	}

	public VersionConstraint getDownRelation() {
		return downRelation;
	}

	public VersionConstraint getUpRelation() {
		return upRelation;
	}

	public String getDownVersion() {
		return downVersion;
	}

	public String getUpVersion() {
		return upVersion;
	}

	protected void setDownVersion(String downVersion) {
		this.downVersion = downVersion;
	}

	protected void setUpVersion(String upVersion) {
		this.upVersion = upVersion;
	}

	protected void setDownRelation(VersionConstraint downRelation) {
		this.downRelation = downRelation;
	}

	protected void setUpRelation(VersionConstraint upRelation) {
		this.upRelation = upRelation;
	}

	private static String parseVersion(String down) {
		if (down.startsWith(VersionConstraint.GREATER_EQUAL.getSymbol())
				|| down.startsWith(VersionConstraint.LESS_EQUAL.getSymbol())) {
			return down.substring(2);
		}
		if (down.startsWith(VersionConstraint.GREATER.getSymbol())
				|| down.startsWith(VersionConstraint.LESS.getSymbol())
				|| down.startsWith(VersionConstraint.APPROX.getSymbol())) {
			return down.substring(1);
		}
		return down;
	}

	private static VersionConstraint parseRelation(String down) {
		if (down.startsWith(VersionConstraint.GREATER_EQUAL.getSymbol())
				|| down.startsWith(VersionConstraint.LESS_EQUAL.getSymbol())) {
			return VersionConstraint.bySymbol(down.substring(0, 2));
		}
		if (down.startsWith(VersionConstraint.GREATER.getSymbol())
				|| down.startsWith(VersionConstraint.LESS.getSymbol())
				|| down.startsWith(VersionConstraint.APPROX.getSymbol())) {
			return VersionConstraint.bySymbol(down.substring(0, 1));
		}
		return VersionConstraint.NONE;
	}
}
