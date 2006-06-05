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

public class PHPManualDirectorDescriptor {

	private static final String PHP_MANUAL_DIRECTORS_EXTENSION_POINT = "org.eclipse.php.ui.phpManualDirectors"; //$NON-NLS-1$
	private static final String DIRECTOR_TAG = "director"; //$NON-NLS-1$
	private static final String ID_ATTRIBUTE = "id"; //$NON-NLS-1$
	private static final String LABEL_ATTRIBUTE = "label"; //$NON-NLS-1$
	private static final String ELEMENT_TAG = "element"; //$NON-NLS-1$
	private static final String NAME_ATTRIBUTE = "name"; //$NON-NLS-1$
	private static final String PATH_ATTRIBUTE = "path"; //$NON-NLS-1$

	private IConfigurationElement fElement;

	public PHPManualDirectorDescriptor(IConfigurationElement element) {
		Assert.isNotNull(element);
		fElement = element;
	}

	public static PHPManualDirectorDescriptor[] getContributedDirectors() {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] elements = registry.getConfigurationElementsFor(PHP_MANUAL_DIRECTORS_EXTENSION_POINT);
		PHPManualDirectorDescriptor[] directorsDescs = createDescriptors(elements);
		initializeFromPreferences(directorsDescs);
		return directorsDescs;
	}

	public PHPManualDirector createDirector() {
		PHPManualDirector director = new PHPManualDirector(getLabel());
		IConfigurationElement[] elements = fElement.getChildren(ELEMENT_TAG);
		for (int i = 0; i < elements.length; ++i) {
			director.addPath(elements[i].getAttribute(NAME_ATTRIBUTE), elements[i].getAttribute(PATH_ATTRIBUTE));
		}
		return director;
	}

	public String getID() {
		return fElement.getAttribute(ID_ATTRIBUTE);
	}

	public String getLabel() {
		return fElement.getAttribute(LABEL_ATTRIBUTE);
	}

	private static PHPManualDirectorDescriptor[] createDescriptors(IConfigurationElement[] elements) {
		List result = new ArrayList(elements.length);
		for (int i = 0; i < elements.length; i++) {
			IConfigurationElement element = elements[i];
			if (DIRECTOR_TAG.equals(element.getName())) {
				PHPManualDirectorDescriptor desc = new PHPManualDirectorDescriptor(element);
				result.add(desc);
			}
		}
		return (PHPManualDirectorDescriptor[]) result.toArray(new PHPManualDirectorDescriptor[result.size()]);
	}

	private static void initializeFromPreferences(PHPManualDirectorDescriptor[] directorsDescs) {
	}
}
