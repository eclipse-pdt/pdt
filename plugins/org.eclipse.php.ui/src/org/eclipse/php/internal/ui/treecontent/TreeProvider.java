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
package org.eclipse.php.internal.ui.treecontent;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.ISafeRunnable;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.ui.treecontent.IPHPTreeContentProvider;

public class TreeProvider {
	private static final String EXTENSION_POINT_NAME = "phpTreeContentProviders"; //$NON-NLS-1$
	private static final String TREE_CONTENT_PROVIDOR_TAG = "provider"; //$NON-NLS-1$
	private static final String TARGET_ID_ATTRIBUTE = "targetId"; //$NON-NLS-1$
	private static final String CLASS_ATTRIBUTE = "class"; //$NON-NLS-1$

	private static TreeProvider[] fgTreeProviders;

	private IConfigurationElement fElement;

	private String[] targetIDs={};
	
	public TreeProvider(IConfigurationElement element)
	{
		fElement=element;
	    String target=element.getAttribute(TARGET_ID_ATTRIBUTE);
	    if (target!=null&& target.trim().length()>0)
	    	targetIDs=target.split(",");
	}
	
	
	public static TreeProvider[] getTreeProviders() {
		if (fgTreeProviders == null) {
			IExtensionRegistry registry = Platform.getExtensionRegistry();
			IConfigurationElement[] elements = registry.getConfigurationElementsFor(PHPUiPlugin.ID, EXTENSION_POINT_NAME);
			fgTreeProviders = createTreeProviders(elements);
		}
		return fgTreeProviders;
	}

	
	private static TreeProvider[] createTreeProviders(IConfigurationElement[] elements) {
		List result = new ArrayList(5);
		for (int i = 0; i < elements.length; i++) {
			final IConfigurationElement element = elements[i];
			if (TREE_CONTENT_PROVIDOR_TAG.equals(element.getName())) {

				TreeProvider provider = new TreeProvider(element);
				result.add(provider);
			}
		}
		return (TreeProvider[]) result.toArray(new TreeProvider[result.size()]);
	}
	
	public static IPHPTreeContentProvider[] getTreeProviders(String targetId) {
		TreeProvider[] treeProviders = getTreeProviders();
		List result = new ArrayList(treeProviders.length);
		for (int i = 0; i < treeProviders.length; i++) {
			if (treeProviders[i].targets(targetId))
			{
				result.add(treeProviders[i].createProvider());
			}
		}
		return (IPHPTreeContentProvider[]) result.toArray(new IPHPTreeContentProvider[result.size()]);
	}


	private IPHPTreeContentProvider createProvider() {
		final IPHPTreeContentProvider[] result = new IPHPTreeContentProvider[1];
		String message = "error createing phpTreeProvider";
		ISafeRunnable code = new SafeRunnable(message) {
			/*
			 * @see org.eclipse.core.runtime.ISafeRunnable#run()
			 */
			public void run() throws Exception {
				result[0] = (IPHPTreeContentProvider) fElement.createExecutableExtension(CLASS_ATTRIBUTE);
			}

		};
		SafeRunner.run(code);
		return result[0];	
	}


	private boolean targets(String targetId) {
		if (targetIDs.length==0)
			return true;
		for (int i = 0; i < targetIDs.length; i++) {
			if (targetIDs[i].equals(targetId))
				return true;
		}
		return false;
	}
}
