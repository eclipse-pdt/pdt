/*******************************************************************************
 * Copyright (c) 2019 Dawid Pakuła and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Dawid Pakuła - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.codeassist.contexts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dltk.annotations.NonNull;
import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.IType;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.codeassist.ICompletionScope;
import org.eclipse.php.core.codeassist.ICompletionScope.Type;
import org.eclipse.php.internal.core.documentModel.parser.PHPRegionContext;
import org.eclipse.php.internal.core.documentModel.parser.regions.IPHPScriptRegion;
import org.eclipse.php.internal.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegionCollection;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegionContainer;

public class TraitConflictContext extends StatementContext implements IClassMemberContext {

	public static final int NONE = 0;
	public static final int TRAIT_NAME = 1;
	public static final int TRAIT_KEYWORD = 2;

	@Override
	public boolean isValid(@NonNull ISourceModule sourceModule, int offset, CompletionRequestor requestor) {
		if (!super.isValid(sourceModule, offset, requestor)) {
			return false;
		}
		ICompletionScope scope = this.getCompanion().getScope();
		if (scope.getType() == Type.TRAIT_CONFLICT) {
			return true;
		}

		return false;
	}

	public int getUseTraitStatementContext() {
		return getUseTraitStatementContext(getCompanion().getOffset(), getCompanion().getStructuredDocumentRegion());
	}

	public int getUseTraitStatementContext(int offset, IStructuredDocumentRegion sdRegion) {
		List<String> types = new ArrayList<>();
		if (sdRegion == null) {
			sdRegion = getCompanion().getStructuredDocumentRegion();
		}
		int documentOffset = offset;
		if (documentOffset == sdRegion.getEndOffset()) {
			documentOffset -= 1;
		}
		ITextRegion tRegion = sdRegion.getRegionAtCharacterOffset(documentOffset);

		ITextRegionCollection container = sdRegion;

		if (tRegion instanceof ITextRegionContainer) {
			container = (ITextRegionContainer) tRegion;
			tRegion = container.getRegionAtCharacterOffset(offset);
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
					startTokenRegion = phpScriptRegion.getPHPToken(offset - startOffset - 1);
				}
				// If statement start is at the beginning of the PHP script
				// region:
				while (true) {
					if (startTokenRegion.getStart() == 0) {
						return NONE;
					}
					if (!PHPPartitionTypes.isPHPCommentState(startTokenRegion.getType())
							&& startTokenRegion.getType() != PHPRegionTypes.WHITESPACE) {
						types.add(startTokenRegion.getType());
					}
					if (startTokenRegion.getType() == PHPRegionTypes.PHP_CURLY_OPEN
							|| startTokenRegion.getType() == PHPRegionTypes.PHP_INSTEADOF
							|| startTokenRegion.getType() == PHPRegionTypes.PHP_SEMICOLON
							|| startTokenRegion.getType() == PHPRegionTypes.PHP_AS) {
						break;
					}

					startTokenRegion = phpScriptRegion.getPHPToken(startTokenRegion.getStart() - 1);
				}

			} catch (BadLocationException e) {
			}
		}
		if (types.size() == 1) {
			String type = types.get(0);
			if (type == PHPRegionTypes.PHP_CURLY_OPEN || type == PHPRegionTypes.PHP_INSTEADOF
					|| type == PHPRegionTypes.PHP_SEMICOLON) {
				return TRAIT_NAME;
			} else if (type == PHPRegionTypes.PHP_AS) {
				return TRAIT_KEYWORD;
			}
			if (type == PHPRegionTypes.PHP_INSTEADOF) {
				return TRAIT_KEYWORD;
			}
		} else if (types.size() == 2) {
			String type1 = types.get(0);
			String type = types.get(1);
			try {
				if (type == PHPRegionTypes.PHP_SEMICOLON && type1 == PHPRegionTypes.PHP_LABEL
						&& Character.isWhitespace(getCompanion().getDocument().getChar(offset - 1))) {

					return TRAIT_KEYWORD;
				}
			} catch (BadLocationException e) {
			}
			if (type == PHPRegionTypes.PHP_CURLY_OPEN || type == PHPRegionTypes.PHP_INSTEADOF
					|| type == PHPRegionTypes.PHP_SEMICOLON || type1 == PHPRegionTypes.PHP_LABEL) {
				return TRAIT_NAME;
			}
		} else if (types.size() == 3) {
			String type = types.get(0);
			String type1 = types.get(1);
			String type2 = types.get(2);
			if (type == PHPRegionTypes.PHP_LABEL && type1 == PHPRegionTypes.PHP_LABEL
					&& type2 == PHPRegionTypes.PHP_SEMICOLON) {
				return TRAIT_KEYWORD;
			}
		} else if (types.size() == 4) {
			String type = types.get(0);
			String type1 = types.get(1);
			String type2 = types.get(2);
			if (type == PHPRegionTypes.PHP_LABEL && type1 == PHPRegionTypes.PHP_PAAMAYIM_NEKUDOTAYIM
					&& type2 == PHPRegionTypes.PHP_LABEL) {
				return TRAIT_KEYWORD;
			}
		}

		return NONE;
	}

	@Override
	public Trigger getTriggerType() {
		return Trigger.CLASS;
	}

	@Override
	public IType[] getLhsTypes() {
		return getCompanion().getLeftHandType(this, false);
	}
}
