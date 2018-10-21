/*******************************************************************************
 * Copyright (c) 2018 Dawid Pakuła and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Dawid Pakuła - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.util;

import java.util.*;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;
import org.eclipse.php.core.util.INamespaceResolver;
import org.eclipse.php.internal.core.PHPCorePlugin;

public class ConfiguredNamespaceResolver implements INamespaceResolver {
	private static final String EXT_ID = PHPCorePlugin.ID + ".namespaceResolver"; //$NON-NLS-1$
	private static final String ATTR_CLASS = "class"; //$NON-NLS-1$
	private static final String ATTR_PRIORITY = "priority"; //$NON-NLS-1$

	private INamespaceResolver[] resolvers;
	private IProject project;

	public ConfiguredNamespaceResolver(IProject project) {
		init(project);
	}

	private void lazyResolvers() {
		if (resolvers != null) {
			return;
		}
		DefaultNamespaceResolver defaultResolver = new DefaultNamespaceResolver();
		defaultResolver.init(project);

		if (project == null) {
			resolvers = new INamespaceResolver[] { defaultResolver };
		} else {
			List<INamespaceResolver> resolverList = new ArrayList<>();
			final Map<INamespaceResolver, Integer> priorities = new HashMap<>();

			for (IConfigurationElement element : Platform.getExtensionRegistry().getConfigurationElementsFor(EXT_ID)) {
				INamespaceResolver tmp;
				try {
					tmp = (INamespaceResolver) element.createExecutableExtension(ATTR_CLASS);
					tmp.init(project);
					if (tmp != null && tmp.isSupported()) {
						resolverList.add(tmp);
						priorities.put(tmp, Integer.decode(element.getAttribute(ATTR_PRIORITY)));
					}
				} catch (CoreException e) {
					PHPCorePlugin.log(e);
				}
			}
			Collections.sort(resolverList, (o1, o2) -> Integer.compare(priorities.get(o1), priorities.get(o2)) * -1);
			resolverList.add(defaultResolver);
			resolvers = resolverList.toArray(new INamespaceResolver[0]);
		}
	}

	@Override
	public String resolveNamespace(IPath folder) {
		lazyResolvers();
		for (INamespaceResolver resolver : resolvers) {
			String result = resolver.resolveNamespace(folder);
			if (result != null) {
				return result;
			}
		}
		return null;
	}

	@Override
	public IPath resolveLocation(IPath target, String namespace) {
		lazyResolvers();
		for (INamespaceResolver resolver : resolvers) {
			IPath result = resolver.resolveLocation(target, namespace);
			if (result != null) {
				return result;
			}
		}
		return null;
	}

	@Override
	public boolean isSupported() {
		return true;
	}

	public void init(IProject project) {
		this.project = project;
		this.resolvers = null;
	}
}
