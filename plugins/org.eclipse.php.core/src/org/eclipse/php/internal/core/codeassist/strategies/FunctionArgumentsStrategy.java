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
package org.eclipse.php.internal.core.codeassist.strategies;

import java.util.List;

import org.eclipse.dltk.ast.declarations.Argument;
import org.eclipse.dltk.ast.declarations.Declaration;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.SourceParserUtil;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.IElementFilter;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;
import org.eclipse.php.internal.core.compiler.ast.parser.ASTUtils;
import org.eclipse.php.internal.core.typeinference.FakeField;

/**
 * This strategy completes variable names taken from function parameters list.
 * @author michael
 */
public class FunctionArgumentsStrategy extends AbstractCompletionStrategy {
	
	public FunctionArgumentsStrategy(ICompletionContext context, IElementFilter elementFilter) {
		super(context, elementFilter);
	}

	public FunctionArgumentsStrategy(ICompletionContext context) {
		super(context);
	}

	@SuppressWarnings("unchecked")
	public void apply(ICompletionReporter reporter) throws BadLocationException {

		AbstractCompletionContext abstractContext = (AbstractCompletionContext) getContext();
		CompletionRequestor requestor = abstractContext.getCompletionRequestor();

		int offset = abstractContext.getOffset();

		// find function arguments
		ISourceModule sourceModule = abstractContext.getSourceModule();
		ModuleDeclaration moduleDeclaration = SourceParserUtil.getModuleDeclaration(sourceModule, null);

		Declaration declaration = ASTUtils.findDeclarationAfterPHPdoc(moduleDeclaration, offset);
		if (declaration instanceof MethodDeclaration) {

			String prefix = abstractContext.getPrefix();
			SourceRange replaceRange = getReplacementRange(abstractContext);
			String suffix = ""; //$NON-NLS-1$

			List<Argument> arguments = ((MethodDeclaration) declaration).getArguments();
			for (Argument arg : arguments) {
				String argumentVar = arg.getName();
				if (argumentVar.startsWith(prefix)) {
					if (!requestor.isContextInformationMode() || argumentVar.length() == prefix.length()) {
						reporter.reportField(new FakeField((ModelElement) sourceModule, argumentVar, 0, 0), suffix, replaceRange, false);
					}
				}
			}
		}
	}

}
