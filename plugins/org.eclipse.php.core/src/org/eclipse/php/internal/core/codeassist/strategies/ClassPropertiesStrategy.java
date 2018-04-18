/*******************************************************************************
 * Copyright (c) 2018 IBM Corporation and others.
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

import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ISourceRange;
import org.eclipse.dltk.core.SourceParserUtil;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.ICompletionReporter;
import org.eclipse.php.core.codeassist.IElementFilter;
import org.eclipse.php.core.compiler.ast.nodes.PHPFieldDeclaration;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;
import org.eclipse.php.internal.core.compiler.ast.parser.ASTUtils;
import org.eclipse.php.internal.core.typeinference.FakeField;

/**
 * This strategy completes variable names taken from class properties list.
 */
public class ClassPropertiesStrategy extends AbstractCompletionStrategy {

	public ClassPropertiesStrategy(ICompletionContext context, IElementFilter elementFilter) {
		super(context, elementFilter);
	}

	public ClassPropertiesStrategy(ICompletionContext context) {
		super(context);
	}

	@Override
	public void apply(ICompletionReporter reporter) throws BadLocationException {

		AbstractCompletionContext abstractContext = (AbstractCompletionContext) getContext();
		CompletionRequestor requestor = abstractContext.getCompletionRequestor();

		int offset = abstractContext.getOffset();

		// find class properties
		ISourceModule sourceModule = abstractContext.getSourceModule();
		ModuleDeclaration moduleDeclaration = SourceParserUtil.getModuleDeclaration(sourceModule, null);

		List<PHPFieldDeclaration> declarations = ASTUtils.findClassPropertiesAfterPHPdoc(moduleDeclaration, offset);
		for (PHPFieldDeclaration declaration : declarations) {
			String prefix = abstractContext.getPrefix();
			ISourceRange replaceRange = getReplacementRange(abstractContext);
			String suffix = ""; //$NON-NLS-1$

			String fieldVar = declaration.getName();
			if (fieldVar.startsWith(prefix)) {
				if (!requestor.isContextInformationMode() || fieldVar.length() == prefix.length()) {
					reporter.reportField(new FakeField((ModelElement) sourceModule, fieldVar, 0, 0), suffix,
							replaceRange, false);
				}
			}
		}
	}

}
