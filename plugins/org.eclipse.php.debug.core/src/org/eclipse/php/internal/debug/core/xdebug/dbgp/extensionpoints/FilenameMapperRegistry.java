package org.eclipse.php.internal.debug.core.xdebug.dbgp.extensionpoints;
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
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;

// TODO - Expose this extension
public class FilenameMapperRegistry {

	private static final String EXTENSION_POINT_NAME = "fileMapper"; //$NON-NLS-1$
	private static final String MAPPER_TAG = "mapper"; //$NON-NLS-1$
	private static final String NAME_ATTRIBUTE = "name"; //$NON-NLS-1$
	private static final String DESC_ATTRIBUTE = "description";
	private static final String CLASS_ATTRIBUTE = "class"; //$NON-NLS-1$

	private static FilenameMapperRegistry _instance = new FilenameMapperRegistry();
	private IFileMapper activeMapper = null;

	//private HashMap registeredMappers = new HashMap();
	IConfigurationElement firstMapperEntry;

	private FilenameMapperRegistry() {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] elements = registry.getConfigurationElementsFor(PHPDebugPlugin.ID, EXTENSION_POINT_NAME);

		for (int i = 0; i < elements.length; i++) {
			final IConfigurationElement element = elements[i];
			if (MAPPER_TAG.equals(element.getName())) {
				firstMapperEntry = element;
			}
		}
	}

	public static FilenameMapperRegistry getRegistry() {
		return _instance;
	}

	private IFileMapper createFileMapperExtension() {
		final IConfigurationElement element = firstMapperEntry;
		final IFileMapper[] extensionInstance = new IFileMapper[1];
		SafeRunner.run(new SafeRunnable("failed to load Mapper extension : " + element.getAttribute(NAME_ATTRIBUTE) + " :" + PHPDebugPlugin.ID + "." + EXTENSION_POINT_NAME) {
			public void run() throws Exception {
				extensionInstance[0] = (IFileMapper) element.createExecutableExtension(CLASS_ATTRIBUTE);
			}
		});
		return extensionInstance[0];
	}

	public IFileMapper getActiveMapper() {
		if (activeMapper == null && firstMapperEntry != null) {
			activeMapper = createFileMapperExtension();
		}
		return activeMapper;
	}

}
