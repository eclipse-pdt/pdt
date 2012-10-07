package org.eclipse.php.internal.ui.explorer;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.*;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.php.internal.ui.PHPUiPlugin;

/**
 * 
 * Registry for {@link IPHPTreeContentProvider} contributions.
 * 
 * @since 3.1.2
 * @author Robert Gruendler <r.gruendler@gmail.com>
 */
public class TreeContentProviderRegistry {

	private static final String EXTENSION_POINT = "phpTreeContentProviders"; //$NON-NLS-1$
	private static final String CLASS_ATTRIBUTE = "class"; //$NON-NLS-1$

	private static TreeContentProviderRegistry instance;
	private List<IPHPTreeContentProvider> contributions = new ArrayList<IPHPTreeContentProvider>();

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
								.createExecutableExtension(CLASS_ATTRIBUTE); //$NON-NLS-1$
						if (execExt instanceof IPHPTreeContentProvider) {
							IPHPTreeContentProvider treeProvider = (IPHPTreeContentProvider) execExt;
							contributions.add(treeProvider);
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

	public List<IPHPTreeContentProvider> getTreeProviders() {
		return contributions;
	}
}
