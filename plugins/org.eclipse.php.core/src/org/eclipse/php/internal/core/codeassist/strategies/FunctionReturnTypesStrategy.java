/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.codeassist.strategies;

import org.eclipse.dltk.ast.declarations.Declaration;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.internal.core.codeassist.CodeAssistUtils;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;
import org.eclipse.php.internal.core.codeassist.contexts.ICompletionContext;
import org.eclipse.php.internal.core.compiler.PHPFlags;
import org.eclipse.php.internal.core.compiler.ast.parser.ASTUtils;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;

/**
 * This strategy completes type determined from function return type.
 * @author michael
 */
public class FunctionReturnTypesStrategy extends AbstractCompletionStrategy {

	public void apply(ICompletionContext context, ICompletionReporter reporter) throws BadLocationException {

		AbstractCompletionContext abstractContext = (AbstractCompletionContext) context;

		int offset = abstractContext.getOffset();
		ISourceModule sourceModule = abstractContext.getSourceModule();

		// find function return types
		ModuleDeclaration moduleDeclaration = SourceParserUtil.getModuleDeclaration(sourceModule, null);

		Declaration declaration = ASTUtils.findDeclarationAfterPHPdoc(moduleDeclaration, offset);
		if (declaration instanceof MethodDeclaration) {
			IMethod method = (IMethod) PHPModelUtils.getModelElementByNode(sourceModule, moduleDeclaration, declaration);
			if (method != null) {
				
				IType[] returnTypes = CodeAssistUtils.getFunctionReturnType(method, 0);
				if (returnTypes != null) {

					String className = abstractContext.getPrefix();
					SourceRange replaceRange = getReplacementRange(abstractContext);

					for (IType type : returnTypes) {
						try {
							if (CodeAssistUtils.startsWithIgnoreCase(type.getElementName(), className) && !PHPFlags.isInternal(type.getFlags())) {
								reporter.reportType(type, "", replaceRange);
							}
						} catch (ModelException e) {
							if (DLTKCore.DEBUG_COMPLETION) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
	}

}
