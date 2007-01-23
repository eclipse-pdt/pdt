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
package org.eclipse.php.internal.core.project.build;

import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.php.core.project.build.IPHPBuilderExtension;

/**
 * This is a composite PHP builder which hold references to all extensions contributed to it,
 * and delegates all the responsibility to them.
 * 
 * @author michael
 */
public class PHPIncrementalProjectBuilder extends IncrementalProjectBuilder {
	
	private IPHPBuilderExtension[] extensions;

	public PHPIncrementalProjectBuilder() {
		extensions = PHPBuilderExtensionsRegistry.getInstance().getExtensions();
		
		for (int i = 0; i < extensions.length; ++i) {
			extensions[i].setContainingBuilder(this);
		}
	}

	/**
	 * We do not support returning projects from this method, <code>null</code> will be returned always.
	 * @see IncrementalProjectBuilder#build(int, Map, IProgressMonitor)
	 * @return <code>null</code>
	 */
	protected IProject[] build(int kind, Map args, IProgressMonitor monitor) throws CoreException {
		for (int i = 0; i < extensions.length; ++i) {
			if (extensions[i].isEnabled()) {
				extensions[i].build(kind, args, monitor);
			}
		}
		return null;
	}

	protected void clean(IProgressMonitor monitor) throws CoreException {
		for (int i = 0; i < extensions.length; ++i) {
			if (extensions[i].isEnabled()) {
				extensions[i].clean(monitor);
			}
		}
	}

	protected void startupOnInitialize() {
		super.startupOnInitialize();
		
		for (int i = 0; i < extensions.length; ++i) {
			extensions[i].startupOnInitialize();
		}
	}
}
