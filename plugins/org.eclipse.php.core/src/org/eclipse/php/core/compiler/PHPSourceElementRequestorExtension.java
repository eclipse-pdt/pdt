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
package org.eclipse.php.core.compiler;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.TypeDeclaration;
import org.eclipse.dltk.compiler.IElementRequestor;
import org.eclipse.dltk.compiler.IElementRequestor.MethodInfo;
import org.eclipse.dltk.compiler.IElementRequestor.TypeInfo;
import org.eclipse.dltk.compiler.env.IModuleSource;

/**
 * This class provides last chance for modifying or adding new elements when
 * building DLTK structured model
 * 
 * @author michael
 * 
 */
public abstract class PHPSourceElementRequestorExtension extends ASTVisitor {

	private IModuleSource fSourceModule;
	protected IElementRequestor fRequestor;

	/**
	 * Sets source element requestor
	 * 
	 * @param requesor
	 */
	public void setRequestor(IElementRequestor requesor) {
		fRequestor = requesor;
	}

	/**
	 * Returns source element requestor
	 * 
	 * @return
	 */
	public IElementRequestor getRequestor() {
		return fRequestor;
	}

	/**
	 * Set source module to process
	 * 
	 * @param sourceModule
	 */
	public void setSourceModule(IModuleSource sourceModule) {
		fSourceModule = sourceModule;
	}

	/**
	 * Returns source model being processed
	 * 
	 * @return
	 */
	public IModuleSource getSourceModule() {
		return fSourceModule;
	}

	/**
	 * Modify type declaration before inserting it into structured model
	 * 
	 * @param typeDeclaration
	 *            Type declaration node
	 * @param ti
	 *            Type information structure
	 */
	public void modifyClassInfo(TypeDeclaration typeDeclaration, TypeInfo ti) {
	}

	/**
	 * Modify method declaration before inserting it into structured model
	 * 
	 * @param methodDeclaration
	 *            Method declaration node
	 * @param mi
	 *            Method information structure
	 */
	public void modifyMethodInfo(MethodDeclaration methodDeclaration,
			MethodInfo mi) {
	}
}
