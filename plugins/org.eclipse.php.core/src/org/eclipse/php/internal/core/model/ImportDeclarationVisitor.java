/*******************************************************************************
 * Copyright (c) 2016 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.model;

import org.eclipse.dltk.core.IImportDeclaration;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IModelElementVisitor;

public abstract class ImportDeclarationVisitor implements IModelElementVisitor {

	@Override
	public boolean visit(IModelElement element) {
		if (element.getElementType() == IModelElement.IMPORT_DECLARATION) {
			IImportDeclaration declaration = (IImportDeclaration) element;
			visitImport(declaration);
			return false;
		}
		return element.getElementType() == IModelElement.SOURCE_MODULE || element.getElementType() == IModelElement.TYPE
				|| element.getElementType() == IModelElement.IMPORT_CONTAINER;
	}

	abstract public void visitImport(IImportDeclaration importDeclaration);

}