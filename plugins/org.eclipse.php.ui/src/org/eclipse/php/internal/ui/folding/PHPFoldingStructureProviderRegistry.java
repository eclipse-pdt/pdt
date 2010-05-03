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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.preferences.PreferenceConstants;

/**
 * The PHPFoldingStructureProviderRegistry is defined as
 * IStructuredTextFoldingProvider to supply a proxy for the registered
 * IStructuredTextFoldingProvider for the PHP editor.
 * 
 * @author shalom
 * @since 3.1
 */
public class PHPFoldingStructureProviderRegistry {

	private static final String EXTENSION_POINT = "foldingStructureProviders"; //$NON-NLS-1$

	/** The map of descriptors, indexed by their identifiers. */
	private Map fDescriptors;

	/**
	 * Creates a new instance.
	 */
	public PHPFoldingStructureProviderRegistry() {
	}

	/**
	 * Returns an array of <code>PHPFoldingStructureProviderDescriptor</code>
	 * describing all extension to the <code>foldingProviders</code> extension
	 * point.
	 * 
	 * @return the list of extensions to the <code>foldingProviders</code>
	 *         extension point.
	 */
	public PHPFoldingStructureProviderDescriptor[] getFoldingProviderDescriptors() {
		synchronized (this) {
			ensureRegistered();
			return (PHPFoldingStructureProviderDescriptor[]) fDescriptors
					.values()
					.toArray(
							new PHPFoldingStructureProviderDescriptor[fDescriptors
									.size()]);
		}
	}

	/**
	 * Returns the folding provider with identifier <code>id</code> or
	 * <code>null</code> if no such provider is registered.
	 * 
	 * @param id
	 *            the identifier for which a provider is wanted
	 * @return the corresponding provider, or <code>null</code> if none can be
	 *         found
	 */
	public PHPFoldingStructureProviderDescriptor getFoldingProviderDescriptor(
			String id) {
		synchronized (this) {
			ensureRegistered();
			return (PHPFoldingStructureProviderDescriptor) fDescriptors.get(id);
		}
	}

	/**
	 * Instantiates and returns the provider that is currently configured in the
	 * preferences.
	 * 
	 * @return the current provider according to the preferences
	 */
	public IStructuredTextFoldingProvider getCurrentFoldingProvider() {
		String id = PreferenceConstants.getPreferenceStore().getString(
				PreferenceConstants.EDITOR_FOLDING_PROVIDER);
		PHPFoldingStructureProviderDescriptor desc = getFoldingProviderDescriptor(id);
		if (desc != null) {
			try {
				return desc.createProvider();
			} catch (CoreException e) {
				PHPUiPlugin.log(e);
			}
		}
		return null;
	}

	/**
	 * Ensures that the extensions are read and stored in
	 * <code>fDescriptors</code>.
	 */
	private void ensureRegistered() {
		if (fDescriptors == null)
			reloadExtensions();
	}

	/**
	 * Reads all extensions.
	 * <p>
	 * This method can be called more than once in order to reload from a
	 * changed extension registry.
	 * </p>
	 */
	public void reloadExtensions() {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		Map map = new HashMap();

		IConfigurationElement[] elements = registry
				.getConfigurationElementsFor(PHPUiPlugin.getPluginId(),
						EXTENSION_POINT);
		for (int i = 0; i < elements.length; i++) {
			PHPFoldingStructureProviderDescriptor desc = new PHPFoldingStructureProviderDescriptor(
					elements[i]);
			map.put(desc.getId(), desc);
		}

		synchronized (this) {
			fDescriptors = Collections.unmodifiableMap(map);
		}
	}

}
