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
package org.eclipse.php.core.index;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.index2.IIndexingRequestor;
import org.eclipse.dltk.core.index2.IIndexingRequestor.DeclarationInfo;
import org.eclipse.dltk.core.index2.IIndexingRequestor.ReferenceInfo;

/**
 * This extension can be used for modifying or adding new index elements.
 * 
 * @author michael
 * @since 2.2
 */
public abstract class PhpIndexingVisitorExtension extends ASTVisitor {

	protected IIndexingRequestor requestor;
	protected ISourceModule sourceModule;

	/**
	 * This is a last chance before modifying element declaration information
	 * before inserting it into index.
	 * 
	 * @param node
	 *            Declaration node
	 * @param info
	 *            Declaration info
	 */
	public void modifyDeclaration(ASTNode node, DeclarationInfo info) {
	}

	/**
	 * This is a last chance before modifying element reference information
	 * before inserting it into index.
	 * 
	 * @param node
	 *            Reference node
	 * @param info
	 *            Reference info
	 */
	public void modifyReference(ASTNode node, ReferenceInfo info) {
	}

	public void setRequestor(IIndexingRequestor requestor) {

		this.requestor = requestor;
	}

	public void setSourceModule(ISourceModule module) {

		this.sourceModule = module;

	}
}
