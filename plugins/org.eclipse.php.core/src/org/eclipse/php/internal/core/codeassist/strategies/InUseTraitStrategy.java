/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.core.codeassist.strategies;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ISourceRange;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.ICompletionReporter;
import org.eclipse.php.core.codeassist.IElementFilter;
import org.eclipse.php.internal.core.codeassist.ProposalExtraInfo;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;
import org.eclipse.php.internal.core.documentModel.parser.PHPRegionContext;
import org.eclipse.php.internal.core.documentModel.parser.regions.IPHPScriptRegion;
import org.eclipse.php.internal.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.php.internal.core.typeinference.PHPClassType;
import org.eclipse.php.internal.core.typeinference.PHPTypeInferenceUtils;
import org.eclipse.php.internal.core.typeinference.context.FileContext;
import org.eclipse.php.internal.core.util.text.PHPTextSequenceUtilities;
import org.eclipse.php.internal.core.util.text.TextSequence;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegionCollection;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegionContainer;

/**
 * This strategy completes builtin array keys, like in _SERVER.
 * 
 * body only
 * 
 * @author michael
 */
public class InUseTraitStrategy extends AbstractCompletionStrategy {

	public InUseTraitStrategy(ICompletionContext context, IElementFilter elementFilter) {
		super(context, elementFilter);
	}

	public InUseTraitStrategy(ICompletionContext context) {
		super(context);
	}

	private List<String> getUseTypes() {
		List<String> useTypes = new LinkedList<>();
		if (getCompanion().getPHPVersion().isLessThan(PHPVersion.PHP5_4)) {
			return useTypes;
		}
		IStructuredDocumentRegion sdRegion = getCompanion().getStructuredDocumentRegion();
		int documentOffset = getCompanion().getOffset();
		if (documentOffset == sdRegion.getEndOffset()) {
			documentOffset -= 1;
		}
		ITextRegion tRegion = sdRegion.getRegionAtCharacterOffset(documentOffset);

		ITextRegionCollection container = sdRegion;

		if (tRegion instanceof ITextRegionContainer) {
			container = (ITextRegionContainer) tRegion;
			tRegion = container.getRegionAtCharacterOffset(getCompanion().getOffset());
		}
		if (tRegion != null && tRegion.getType() == PHPRegionContext.PHP_CLOSE) {
			tRegion = container.getRegionAtCharacterOffset(container.getStartOffset() + tRegion.getStart() - 1);
		}

		// This text region must be of type PhpScriptRegion:
		if (tRegion != null && tRegion.getType() == PHPRegionContext.PHP_CONTENT) {
			IPHPScriptRegion phpScriptRegion = (IPHPScriptRegion) tRegion;

			try {
				// Set default starting position to the beginning of the
				// PhpScriptRegion:
				int startOffset = container.getStartOffset() + phpScriptRegion.getStart();

				// Now, search backwards for the statement start (in this
				// PhpScriptRegion):
				ITextRegion startTokenRegion;
				if (documentOffset == startOffset) {
					startTokenRegion = phpScriptRegion.getPHPToken(0);
				} else {
					startTokenRegion = phpScriptRegion.getPHPToken(getCompanion().getOffset() - startOffset - 1);
				}
				// If statement start is at the beginning of the PHP script
				// region:
				while (true) {
					if (startTokenRegion.getStart() == 0) {
						return useTypes;
					}
					if (startTokenRegion.getType() == PHPRegionTypes.PHP_CURLY_OPEN) {
						// Calculate starting position of the statement (it
						// should go right after this startTokenRegion):
						// startOffset += startTokenRegion.getEnd();
						TextSequence statementText1 = PHPTextSequenceUtilities.getStatement(
								startOffset + startTokenRegion.getStart() - 1,
								getCompanion().getStructuredDocumentRegion(), true);
						startTokenRegion = phpScriptRegion
								.getPHPToken(startTokenRegion.getStart() - statementText1.length());
						if (startTokenRegion.getType() == PHPRegionTypes.PHP_USE) {
							String[] types = statementText1.toString().trim().substring(3).trim().split(","); //$NON-NLS-1$
							useTypes = new ArrayList<>();
							for (String type : types) {
								useTypes.add(type.trim());
							}
							return useTypes;
						} else {
							return useTypes;
						}
					} else if (startTokenRegion.getType() == PHPRegionTypes.PHP_CURLY_CLOSE) {
						return useTypes;
					}

					startTokenRegion = phpScriptRegion.getPHPToken(startTokenRegion.getStart() - 1);
				}

			} catch (BadLocationException e) {
			}
		}

		return useTypes;

	}

	@Override
	public void apply(ICompletionReporter reporter) throws BadLocationException {
		ICompletionContext context = getContext();
		if (!(context instanceof AbstractCompletionContext)) {
			return;
		}

		AbstractCompletionContext abstractContext = (AbstractCompletionContext) context;

		int offset = getCompanion().getOffset();
		ISourceModule sourceModule = getCompanion().getSourceModule();
		List<String> useTypes = getUseTypes();
		String prefix = abstractContext.getPrefix();
		ModuleDeclaration moduleDeclaration = getCompanion().getModuleDeclaration();
		FileContext fileContext = new FileContext(sourceModule, moduleDeclaration, offset);
		ISourceRange replacementRange = getReplacementRange(abstractContext);
		for (String useType : useTypes) {
			if (StringUtils.startsWithIgnoreCase(useType.trim(), prefix)) {

				IEvaluatedType type = PHPClassType.fromTraitName(useType, sourceModule, offset);
				IType[] modelElements = PHPTypeInferenceUtils.getModelElements(type, fileContext, offset);
				if (modelElements != null) {
					for (IType typeElement : modelElements) {
						reporter.reportType(typeElement, "", replacementRange, //$NON-NLS-1$
								ProposalExtraInfo.TYPE_ONLY);
					}
				}
			}
		}

	}
}
