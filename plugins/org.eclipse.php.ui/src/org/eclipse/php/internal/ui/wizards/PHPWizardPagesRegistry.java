/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.ui.wizards;

import java.util.*;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.php.internal.ui.PHPUIMessages;

public class PHPWizardPagesRegistry {

	private static final String EXTENSION_POINT = "org.eclipse.php.ui.phpWizardPages"; //$NON-NLS-1$
	private static final String PAGE_ELEMENT = "page"; //$NON-NLS-1$
	private static final String CLASS_ATTRIBUTE = "class"; //$NON-NLS-1$
	private static final String TARGET_ID_ATTRIBUTE = "targetId"; //$NON-NLS-1$

	private Map pages = new HashMap();
	private static PHPWizardPagesRegistry instance = new PHPWizardPagesRegistry();

	private PHPWizardPagesRegistry() {
		IConfigurationElement[] elements = Platform.getExtensionRegistry()
				.getConfigurationElementsFor(EXTENSION_POINT); 
		for (int i = 0; i < elements.length; i++) {
			IConfigurationElement element = elements[i];
			if (PAGE_ELEMENT.equals(element.getName())) {
				String targetId = element.getAttribute(TARGET_ID_ATTRIBUTE);
				List elementsList = (List) pages.get(targetId);
				if (elementsList == null) {
					elementsList = new LinkedList();
				}
				elementsList.add(element);
				pages.put(targetId, elementsList);
			}
		}
	}

	/**
	 * Returns all pages contributed to the Wizard with specified ID through
	 * extension point <code>org.eclipse.php.ui.phpWizardPages</code>.
	 * 
	 * @param id
	 *            Wizard id
	 * @return Array of {@link IWizardPage} pages, or <code>null</code> if no
	 *         pages where contributed.
	 */
	public static IWizardPage[] getPages(String id) {
		final List elementsList = (List) instance.pages.get(id);
		if (elementsList != null) {
			final List pagesList = new LinkedList();
			SafeRunner.run(new SafeRunnable(
					PHPUIMessages.PHPWizardPagesRegistry_0 + EXTENSION_POINT) { 
						public void run() throws Exception {
							Iterator i = elementsList.iterator();
							while (i.hasNext()) {
								IConfigurationElement element = (IConfigurationElement) i
										.next();
								pagesList
										.add(element
												.createExecutableExtension(CLASS_ATTRIBUTE));
							}
						}
					});
			return (IWizardPage[]) pagesList.toArray(new IWizardPage[pagesList
					.size()]);
		}
		return null;
	}

	/**
	 * Returns all WizardPageFactory(s) contributed to the Wizard with specified
	 * ID through extension point <code>org.eclipse.php.ui.phpWizardPages</code>
	 * .
	 * 
	 * @param id
	 *            Wizard id
	 * @return Array of {@link WizardPageFactory} pages, or <code>null</code> if
	 *         no pages where contributed.
	 */
	public static WizardPageFactory[] getPageFactories(String id) {
		final List elementsList = (List) instance.pages.get(id);
		if (elementsList != null) {
			final List pagesList = new LinkedList();
			SafeRunner.run(new SafeRunnable(
					PHPUIMessages.PHPWizardPagesRegistry_0 + EXTENSION_POINT) { 
						public void run() throws Exception {
							Iterator i = elementsList.iterator();
							while (i.hasNext()) {
								IConfigurationElement element = (IConfigurationElement) i
										.next();
								pagesList
										.add(element
												.createExecutableExtension(CLASS_ATTRIBUTE));
							}
						}
					});
			return (WizardPageFactory[]) pagesList
					.toArray(new WizardPageFactory[pagesList.size()]);
		}
		return null;
	}
}
