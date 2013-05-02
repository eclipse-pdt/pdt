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

import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.SourceParserUtil;
import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.IElementFilter;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.ProposalExtraInfo;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;
import org.eclipse.php.internal.core.typeinference.PHPClassType;
import org.eclipse.php.internal.core.typeinference.PHPTypeInferenceUtils;
import org.eclipse.php.internal.core.typeinference.context.FileContext;

/**
 * This strategy completes builtin array keys, like in _SERVER.
 * 
 * @author michael
 */
public class InUseTraitStrategy extends AbstractCompletionStrategy {

	public InUseTraitStrategy(ICompletionContext context,
			IElementFilter elementFilter) {
		super(context, elementFilter);
	}

	public InUseTraitStrategy(ICompletionContext context) {
		super(context);
	}

	public void apply(ICompletionReporter reporter) throws BadLocationException {
		ICompletionContext context = getContext();
		if (!(context instanceof AbstractCompletionContext)) {
			return;
		}

		AbstractCompletionContext abstractContext = (AbstractCompletionContext) context;
		int offset = abstractContext.getOffset();
		ISourceModule sourceModule = abstractContext.getSourceModule();
		List<String> useTypes = abstractContext.getUseTypes();
		String prefix = abstractContext.getPrefix();
		ModuleDeclaration moduleDeclaration = SourceParserUtil
				.getModuleDeclaration(sourceModule, null);
		FileContext fileContext = new FileContext(sourceModule,
				moduleDeclaration, offset);
		SourceRange replacementRange = getReplacementRange(abstractContext);
		for (String useType : useTypes) {
			if (useType.trim().toLowerCase().startsWith(prefix.toLowerCase())) {

				IEvaluatedType type = PHPClassType.fromTraitName(useType,
						sourceModule, offset);
				IType[] modelElements = PHPTypeInferenceUtils.getModelElements(
						type, fileContext, offset);
				if (modelElements != null) {
					for (IType typeElement : modelElements) {
						reporter.reportType(typeElement, "", replacementRange, //$NON-NLS-1$
								ProposalExtraInfo.TYPE_ONLY);
					}
				}
			}
		}

	}

	public SourceRange getReplacementRange(ICompletionContext context)
			throws BadLocationException {
		SourceRange replacementRange = super.getReplacementRange(context);
		boolean insertMode = isInsertMode();
		if (replacementRange.getLength() > 0 && insertMode) {
			return new SourceRange(replacementRange.getOffset(),
					replacementRange.getLength() - 1);
		}
		return replacementRange;
	}
}
