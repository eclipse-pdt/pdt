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
package org.eclipse.php.core;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.declarations.TypeDeclaration;
import org.eclipse.dltk.compiler.ISourceElementRequestor;
import org.eclipse.dltk.compiler.ISourceElementRequestor.TypeInfo;
import org.eclipse.dltk.compiler.env.ISourceModule;

public class PHPSourceElementRequestorExtension extends ASTVisitor {

	private ISourceModule fSourceModule;
	protected ISourceElementRequestor fRequestor;

	public PHPSourceElementRequestorExtension() {
	}

	public void setRequestor(ISourceElementRequestor requesor) {
		fRequestor = requesor;
	}
	
	public ISourceElementRequestor getRequestor() {
		return fRequestor;
	}
	
	public void setSourceModule(ISourceModule sourceModule) {
		fSourceModule = sourceModule;
	}

	public ISourceModule getSourceModule() {
		return fSourceModule;
	}
	
	public void modifyClassInfo(TypeDeclaration typeDeclaration, TypeInfo ti) {
	}
}
