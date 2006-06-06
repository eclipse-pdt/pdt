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
package org.eclipse.php.ui.preferences.includepath;

import java.util.ArrayList;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.php.core.PHPCorePlugin;
import org.eclipse.php.core.project.IIncludePathEntry;
import org.eclipse.php.ui.PHPUiPlugin;
import org.eclipse.swt.custom.BusyIndicator;
import org.osgi.framework.Bundle;

/**
 */
public class IncludePathContainerDescriptor {

	private IConfigurationElement fConfigElement;
	private IIncludePathContainerPage fPage;

	private static final String ATT_EXTENSION = "includepathContainerPage"; //$NON-NLS-1$

	private static final String ATT_ID = "id"; //$NON-NLS-1$
	private static final String ATT_NAME = "name"; //$NON-NLS-1$
	private static final String ATT_PAGE_CLASS = "class"; //$NON-NLS-1$	

	public IncludePathContainerDescriptor(IConfigurationElement configElement) throws CoreException {
		super();
		fConfigElement = configElement;
		fPage = null;

		String id = fConfigElement.getAttribute(ATT_ID);
		String name = configElement.getAttribute(ATT_NAME);
		String pageClassName = configElement.getAttribute(ATT_PAGE_CLASS);

		if (name == null) {
			throw new CoreException(new Status(IStatus.ERROR, PHPUiPlugin.ID, 0, "Invalid extension (missing name): " + id, null)); //$NON-NLS-1$
		}
		if (pageClassName == null) {
			throw new CoreException(new Status(IStatus.ERROR, PHPUiPlugin.ID, 0, "Invalid extension (missing page class name): " + id, null)); //$NON-NLS-1$
		}
	}

	public IIncludePathContainerPage createPage() throws CoreException {
		if (fPage == null) {
			Object elem = createExtension(fConfigElement, ATT_PAGE_CLASS);
			if (elem instanceof IIncludePathContainerPage) {
				fPage = (IIncludePathContainerPage) elem;
			} else {
				String id = fConfigElement.getAttribute(ATT_ID);
				throw new CoreException(new Status(IStatus.ERROR, PHPUiPlugin.ID, 0, "Invalid extension (page not of type IIncludePathContainerPage): " + id, null)); //$NON-NLS-1$
			}
		}
		return fPage;
	}

	public static Object createExtension(final IConfigurationElement element, final String classAttribute) throws CoreException {
		// If plugin has been loaded create extension.
		// Otherwise, show busy cursor then create extension.
		String pluginId = element.getNamespaceIdentifier();
		Bundle bundle = Platform.getBundle(pluginId);
		if (bundle != null && bundle.getState() == Bundle.ACTIVE) {
			return element.createExecutableExtension(classAttribute);
		} else {
			final Object[] ret = new Object[1];
			final CoreException[] exc = new CoreException[1];
			BusyIndicator.showWhile(null, new Runnable() {
				public void run() {
					try {
						ret[0] = element.createExecutableExtension(classAttribute);
					} catch (CoreException e) {
						exc[0] = e;
					}
				}
			});
			if (exc[0] != null)
				throw exc[0];
			else
				return ret[0];
		}
	}

	public IIncludePathContainerPage getPage() {
		return fPage;
	}

	public void setPage(IIncludePathContainerPage page) {
		fPage = page;
	}

	public void dispose() {
		if (fPage != null) {
			fPage.dispose();
			fPage = null;
		}
	}

	public String getName() {
		return fConfigElement.getAttribute(ATT_NAME);
	}

	public String getPageClass() {
		return fConfigElement.getAttribute(ATT_PAGE_CLASS);
	}

	public boolean canEdit(IIncludePathEntry entry) {
		String id = fConfigElement.getAttribute(ATT_ID);
		if (entry.getEntryKind() == IIncludePathEntry.IPE_CONTAINER) {
			String type = entry.getPath().segment(0);
			return id.equals(type);
		}
		return false;
	}

	public static IncludePathContainerDescriptor[] getDescriptors() {
		ArrayList containers = new ArrayList();

		IExtensionPoint extensionPoint = Platform.getExtensionRegistry().getExtensionPoint(PHPUiPlugin.ID, ATT_EXTENSION);
		if (extensionPoint != null) {
			IncludePathContainerDescriptor defaultPage = null;
			String defaultPageName = IncludePathContainerDefaultPage.class.getName();

			IConfigurationElement[] elements = extensionPoint.getConfigurationElements();
			for (int i = 0; i < elements.length; i++) {
				try {
					IncludePathContainerDescriptor curr = new IncludePathContainerDescriptor(elements[i]);
					if (defaultPageName.equals(curr.getPageClass())) {
						defaultPage = curr;
					} else {
						containers.add(curr);
					}
				} catch (CoreException e) {
					PHPCorePlugin.log(e);
				}
			}
			if (defaultPageName != null && containers.isEmpty()) {
				// default page only added of no other extensions found
				containers.add(defaultPage);
			}
		}
		return (IncludePathContainerDescriptor[]) containers.toArray(new IncludePathContainerDescriptor[containers.size()]);
	}

}
