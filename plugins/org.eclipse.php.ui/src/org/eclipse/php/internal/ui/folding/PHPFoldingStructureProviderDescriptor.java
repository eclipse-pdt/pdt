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
package org.eclipse.php.internal.ui.folding;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.ui.folding.IPHPFoldingPreferenceBlock;

/**
 * Describes a contribution to the folding provider extension point.
 */
public final class PHPFoldingStructureProviderDescriptor {

	/* extension point attribute names */

	private static final String PREFERENCES_CLASS = "preferencesClass"; //$NON-NLS-1$
	private static final String CLASS = "class"; //$NON-NLS-1$
	private static final String NAME = "name"; //$NON-NLS-1$
	private static final String ID = "id"; //$NON-NLS-1$

	/** The identifier of the extension. */
	private String fId;
	/** The name of the extension. */
	private String fName;
	/**
	 * <code>true</code> if the extension specifies a custom
	 */
	private boolean fHasPreferences;
	/** The configuration element of this extension. */
	private IConfigurationElement fElement;

	/**
	 * Creates a new descriptor.
	 * 
	 * @param element
	 *            the configuration element to read
	 */
	PHPFoldingStructureProviderDescriptor(IConfigurationElement element) {
		fElement = element;
		fId = element.getAttributeAsIs(ID);
		Assert.isLegal(fId != null);

		fName = element.getAttribute(NAME);
		if (fName == null)
			fName = fId;

		if (element.getAttributeAsIs(PREFERENCES_CLASS) == null)
			fHasPreferences = false;
		else
			fHasPreferences = true;
	}

	/**
	 * Creates a folding provider as described in the extension's xml.
	 * 
	 * @return a new instance of the folding provider described by this
	 *         descriptor
	 * @throws CoreException
	 *             if creation fails
	 */
	public IStructuredTextFoldingProvider createProvider() throws CoreException {
		IStructuredTextFoldingProvider prov = (IStructuredTextFoldingProvider) fElement
				.createExecutableExtension(CLASS);
		return prov;
	}

	/**
	 * Creates a preferences object as described in the extension's xml.
	 * 
	 * @return a new instance of the reference provider described by this
	 *         descriptor
	 * @throws CoreException
	 *             if creation fails
	 */
	public IPHPFoldingPreferenceBlock createPreferences() throws CoreException {
		if (fHasPreferences) {
			IPHPFoldingPreferenceBlock prefs = (IPHPFoldingPreferenceBlock) fElement
					.createExecutableExtension(PREFERENCES_CLASS);
			return prefs;
		} else {
			throw new IllegalStateException(
					PHPUIMessages.PHPFoldingStructureProviderDescriptor_0); 
		}
	}

	/**
	 * Returns the identifier of the described extension.
	 * 
	 * @return Returns the id
	 */
	public String getId() {
		return fId;
	}

	/**
	 * Returns the name of the described extension.
	 * 
	 * @return Returns the name
	 */
	public String getName() {
		return fName;
	}
}
