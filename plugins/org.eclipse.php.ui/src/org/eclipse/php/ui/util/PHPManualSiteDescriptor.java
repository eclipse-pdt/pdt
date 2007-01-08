/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.ui.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.text.Assert;

public class PHPManualSiteDescriptor {

	private static final String PHP_MANUAL_SITES_EXTENSION_POINT = "org.eclipse.php.ui.phpManualSites"; //$NON-NLS-1$
	private static final String SITE_TAG = "site"; //$NON-NLS-1$
	private static final String ID_ATTRIBUTE = "id"; //$NON-NLS-1$
	private static final String URL_ATTRIBUTE = "url"; //$NON-NLS-1$
	private static final String LABEL_ATTRIBUTE = "label"; //$NON-NLS-1$
	private static final String DIRECTOR_ATTRIBUTE = "director"; //$NON-NLS-1$
	private static final String EXTENSION_ATTRIBUTE = "extension"; //$NON-NLS-1$

	public static final String DEFAULT_PHP_MANUAL_SITE = "http://www.php.net/manual/en/";

	public static final String DEFAULT_PHP_MANUAL_EXTENSION = "php";

	public static final String DEFAULT_PHP_MANUAL_LABEL = "PHP.net";

	public static final String DEFAULT_PHP_MANUAL_DIRECTOR = "org.eclipse.php.ui.phpManualDirector";

	private IConfigurationElement fElement;

	public PHPManualSiteDescriptor(IConfigurationElement element) {
		Assert.isNotNull(element);
		fElement = element;
	}

	public static PHPManualSiteDescriptor[] getContributedSites() {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] elements = registry.getConfigurationElementsFor(PHP_MANUAL_SITES_EXTENSION_POINT);
		PHPManualSiteDescriptor[] manualDescs = createDescriptors(elements);
		initializeFromPreferences(manualDescs);
		return manualDescs;
	}

	public PHPManualSite createSite() {
		return new PHPManualSite(getURL(), getExtension());
	}

	public String getURL() {
		return fElement.getAttribute(URL_ATTRIBUTE);
	}

	public String getDirectorID() {
		String director = fElement.getAttribute(DIRECTOR_ATTRIBUTE);
		if (director == null) {
			director = DEFAULT_PHP_MANUAL_DIRECTOR;
		}
		return director;
	}

	public String getLabel() {
		return fElement.getAttribute(LABEL_ATTRIBUTE);
	}

	public String getExtension() {
		return fElement.getAttribute(EXTENSION_ATTRIBUTE);
	}

	public String getID() {
		return fElement.getAttribute(ID_ATTRIBUTE);
	}

	private static PHPManualSiteDescriptor[] createDescriptors(IConfigurationElement[] elements) {
		List result = new ArrayList(elements.length);
		for (int i = 0; i < elements.length; i++) {
			IConfigurationElement element = elements[i];
			if (SITE_TAG.equals(element.getName())) {
				PHPManualSiteDescriptor desc = new PHPManualSiteDescriptor(element);
				result.add(desc);
			}
		}
		return (PHPManualSiteDescriptor[]) result.toArray(new PHPManualSiteDescriptor[result.size()]);
	}

	private static void initializeFromPreferences(PHPManualSiteDescriptor[] manualDescs) {
	}
}
