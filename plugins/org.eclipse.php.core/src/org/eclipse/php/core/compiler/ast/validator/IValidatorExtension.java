/*******************************************************************************
 * Copyright (c) 2017 Dawid Pakuła and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Dawid Pakuła <zulus@w3des.net> - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.core.compiler.ast.validator;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.compiler.problem.IProblemIdentifier;
import org.eclipse.dltk.core.builder.IBuildContext;

/**
 * @provisional
 */
public interface IValidatorExtension {
	public void visit(ASTNode s) throws Exception;

	public void endvisit(ASTNode s) throws Exception;

	public void init(IBuildContext buildContext, IValidatorVisitor validator);

	public boolean isSupported(IBuildContext buildContext);

	public boolean skipProblem(int start, int end, String message, IProblemIdentifier id);

	public boolean skipProblem(ASTNode node, String message, IProblemIdentifier id);
}
