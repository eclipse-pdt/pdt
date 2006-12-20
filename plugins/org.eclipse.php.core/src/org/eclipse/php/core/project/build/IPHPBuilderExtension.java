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
package org.eclipse.php.core.project.build;

import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * This interface is used for defining PHP builder extensions. All of its methods where taken
 * from {@link IncrementalProjectBuilder} class in order to allow {@link PHPIncrementalProjectBuilder} to delegate
 * responsibility to its extensions. 
 * 
 * @author michael
 */
public interface IPHPBuilderExtension {

	/**
	 * @see org.eclipse.core.resources.IncrementalProjectBuilder#build(int, java.util.Map, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public IProject[] build(int kind, Map args, IProgressMonitor monitor) throws CoreException;

	/**
	 * @see org.eclipse.core.resources.IncrementalProjectBuilder#clean(org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void clean(IProgressMonitor monitor) throws CoreException;

	/**
	 * @see org.eclipse.core.resources.IncrementalProjectBuilder#startupOnInitialize()
	 */
	public void startupOnInitialize();
	
	/**
	 * Sets containing PHP incremental project builder
	 */
	public void setContainingBuilder(PHPIncrementalProjectBuilder containingBuilder);
	
	/**
	 * Returns containing PHP incremental project builder
	 * @return containing builder
	 */
	public PHPIncrementalProjectBuilder getContainingBuilder();
	
	/**
	 * Returns whether this PHP builder extension is enabled
	 */
	public boolean isEnabled();
}
