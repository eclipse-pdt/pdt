/*******************************************************************************
 * Copyright (c) 2017 Dawid Pakuła and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Dawid Pakuła <zulus@w3des.net> - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.core.compiler.ast.validator;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.core.builder.IBuildContext;

/**
 * @provisional
 */
public interface IValidatorExtension {
	public void visit(ASTNode s) throws Exception;

	public void endvisit(ASTNode s) throws Exception;

	public void init(IBuildContext buildContext, IValidatorVisitor validator);

	public boolean isSupported(IBuildContext buildContext);
}
