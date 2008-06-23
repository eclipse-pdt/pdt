/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
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
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.php.core.project.build.IPHPBuilderExtension;
import org.eclipse.php.internal.core.CoreMessages;

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
	}

	/**
	 * We do not support returning projects from this method, <code>null</code> will be returned always.
	 * @see IncrementalProjectBuilder#build(int, Map, IProgressMonitor)
	 * @return <code>null</code>
	 */
	protected IProject[] build(int kind, Map args, IProgressMonitor monitor) throws CoreException {
		monitor.beginTask(CoreMessages.getString("PHPIncrementalProjectBuilder_0"), extensions.length);
		for (int i = 0; i < extensions.length; ++i) {
			IProgressMonitor subMonitor = new SubProgressMonitor(monitor, 1);
			if (extensions[i].isEnabled()) {
				extensions[i].build(this, kind, args, subMonitor);
			}
		}
		return null;
	}

	protected void clean(IProgressMonitor monitor) throws CoreException {
		for (int i = 0; i < extensions.length; ++i) {
			if (extensions[i].isEnabled()) {
				extensions[i].clean(this, monitor);
			}
		}
	}

	protected void startupOnInitialize() {
		super.startupOnInitialize();
		
		for (int i = 0; i < extensions.length; ++i) {
			extensions[i].startupOnInitialize(this);
		}
	}
}
