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
package org.eclipse.php.internal.core.typeinference;

import java.util.Collection;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.core.*;
import org.eclipse.php.internal.core.filenetwork.ReferenceTree;

public interface IModelAccessCache {
	/**
	 * Filters given set of elements according to a file network
	 */
	public <T extends IModelElement> Collection<T> filterModelElements(ISourceModule sourceModule,
			Collection<T> elements, IProgressMonitor monitor);

	/**
	 * Filters given set of elements according to a file network, but only if
	 * all elements represent the same type, name and namespace
	 */
	public <T extends IModelElement> Collection<T> filterSameModelElements(ISourceModule sourceModule,
			Collection<T> elements, IProgressMonitor monitor);

	public abstract ITypeHierarchy getSuperTypeHierarchy(IType type, IProgressMonitor monitor) throws ModelException;

	/**
	 * Analyzes file dependences, and builds tree of all source modules, which
	 * are referenced by the given source module.
	 * 
	 * Uses local cache for repeating queries.
	 */
	public abstract ReferenceTree getFileHierarchy(ISourceModule sourceModule, IProgressMonitor monitor);

	/**
	 * Returns cached methods for the given name (with filter
	 * filterModelElements() applied on result)
	 */
	public abstract Collection<IMethod> getGlobalFunctions(ISourceModule sourceModule, String functionName,
			IProgressMonitor monitor);

	/**
	 * Returns cached types for the given name (with filter
	 * filterModelElements() applied on result)
	 */
	public abstract Collection<IType> getTypes(ISourceModule sourceModule, String typeName, String namespaceName,
			IProgressMonitor monitor);

	/**
	 * Returns cached traits for the given name (with filter
	 * filterModelElements() applied on result)
	 */
	public abstract Collection<IType> getTraits(ISourceModule sourceModule, String typeName, String namespaceName,
			IProgressMonitor monitor);

	/**
	 * Returns cached classes for the given name
	 */
	public abstract Collection<IType> getClasses(ISourceModule sourceModule, String typeName, String namespaceName,
			IProgressMonitor monitor) throws ModelException;

	/**
	 * Returns cached interfaces for the given name
	 */
	public abstract Collection<IType> getInterfaces(ISourceModule sourceModule, String typeName, String namespaceName,
			IProgressMonitor monitor) throws ModelException;

	/**
	 * Returns cached interfaces for the given name
	 */
	public abstract Collection<IType> getNamespaces(ISourceModule sourceModule, String namespaceName,
			IProgressMonitor monitor) throws ModelException;

	/**
	 * Returns cached classes or interfaces for the given name (with filter
	 * filterModelElements() applied on result)
	 */
	public abstract Collection<IType> getClassesOrInterfaces(ISourceModule sourceModule, String typeName,
			String namespaceName, IProgressMonitor monitor) throws ModelException;

}