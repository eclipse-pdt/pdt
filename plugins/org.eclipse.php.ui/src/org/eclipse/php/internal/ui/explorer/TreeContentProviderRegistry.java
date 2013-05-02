/*******************************************************************************
 * Copyright (c) 2012 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.ui.explorer;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.*;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.php.internal.ui.PHPUiPlugin;

/**
 * 
 * Registry for phpTreeContentProviders extension point contributions.
 * 
 * @since 3.1.2
 * @author Robert Gruendler <r.gruendler@gmail.com>
 */
public class TreeContentProviderRegistry {

	private static final String EXTENSION_POINT = "phpTreeContentProviders"; //$NON-NLS-1$
	private static final String LABEL_PROVIDER = "labelProvider"; //$NON-NLS-1$
	private static final String CONTENT_PROVIDER = "contentProvider"; //$NON-NLS-1$

	private static TreeContentProviderRegistry instance;
	private List<ITreeContentProvider> contentProviders = new ArrayList<ITreeContentProvider>();
	private List<ILabelProvider> labelProviders = new ArrayList<ILabelProvider>();

	private TreeContentProviderRegistry() {

		IExtensionPoint extension = Platform.getExtensionRegistry()
				.getExtensionPoint(PHPUiPlugin.ID, EXTENSION_POINT);
		if (extension != null) {
			IExtension[] extensions = extension.getExtensions();
			for (int i = 0; i < extensions.length; i++) {
				IConfigurationElement[] configElements = extensions[i]
						.getConfigurationElements();
				for (int j = 0; j < configElements.length; j++) {
					IConfigurationElement configElement = configElements[j];
					try {

						Object execExt = configElement
								.createExecutableExtension(CONTENT_PROVIDER); 
						if (execExt instanceof ITreeContentProvider) {
							contentProviders
									.add((ITreeContentProvider) execExt);
						}

						Object labelProvider = configElement
								.createExecutableExtension(LABEL_PROVIDER); 
						if (labelProvider instanceof ILabelProvider) {
							labelProviders.add((ILabelProvider) labelProvider);
						}

					} catch (CoreException e) {
						// executable extension could not be
						// created: ignore
						// this initializer
						Logger.logException(e);
					}
				}
			}
		}
	}

	public synchronized static TreeContentProviderRegistry getInstance() {

		if (instance == null) {
			instance = new TreeContentProviderRegistry();
		}

		return instance;
	}

	public List<ITreeContentProvider> getTreeProviders() {
		return contentProviders;
	}

	public List<ILabelProvider> getLabelProviders() {
		return labelProviders;
	}
}
