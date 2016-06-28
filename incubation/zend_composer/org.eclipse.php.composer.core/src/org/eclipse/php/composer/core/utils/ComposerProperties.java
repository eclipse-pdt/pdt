/*******************************************************************************
 * Copyright (c) 2014, 2016 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.core.utils;

import java.util.Map;

import org.eclipse.php.composer.core.ComposerService;
import org.eclipse.php.composer.core.model.ComposerRoot;

/**
 * @author Michal Niewrzal, 2014
 * 
 */
public class ComposerProperties {

	private static final String CONFIG_TAG_NAME = "config"; //$NON-NLS-1$
	private static final String VENDOR_DIR_TAG_NAME = "vendor-dir"; //$NON-NLS-1$

	private ComposerRoot composerRoot;

	public ComposerProperties(ComposerRoot composerRoot) {
		this.composerRoot = composerRoot;
	}

	/**
	 * Returns the configured vendor directory from the given composer model.
	 * 
	 * <p>
	 * If there is no vendor directory configured in the model then this method
	 * returns the default name "vendor".
	 * </p>
	 * 
	 * @param root
	 *            the root of the composer model
	 * 
	 * @return the name of the vendor directory
	 */
	public String getVendorDir() {
		String result = ComposerService.VENDOR; // default
		if (composerRoot == null) {
			return result;
		}

		Object config = composerRoot.any().get(CONFIG_TAG_NAME);
		if (config != null && config instanceof Map) {
			Object vendorDir = ((Map) config).get(VENDOR_DIR_TAG_NAME);
			if (vendorDir != null && vendorDir instanceof String) {
				result = (String) vendorDir;
			}
		}

		return result;
	}

}
