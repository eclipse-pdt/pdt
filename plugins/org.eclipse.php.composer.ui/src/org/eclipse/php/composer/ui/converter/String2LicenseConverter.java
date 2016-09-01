/*******************************************************************************
 * Copyright (c) 2012, 2016 PDT Extension Group and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.ui.converter;

import org.eclipse.php.composer.api.ComposerPackage;
import org.eclipse.php.composer.api.collection.License;

public class String2LicenseConverter extends String2ListConverter {
	
	private License license;
	
	public String2LicenseConverter() {
		super(String.class, License.class);
	}
	
	public String2LicenseConverter(ComposerPackage composerPackage) {
		this();
		setComposerPackage(composerPackage);
	}
	
	@Override
	protected void composerPackageUpdated() {
		license = composerPackage.getLicense();
	}

	@Override
	protected String[] start() {
		return license.toArray(new String[]{});
	}

	@Override
	protected Object finish() {
		return license;
	}

	@Override
	protected boolean has(String value) {
		return license.has(value);
	}

	@Override
	protected void add(String value) {
		license.add(value);
	}

	@Override
	protected void remove(String value) {
		license.remove(value);
	}


}
