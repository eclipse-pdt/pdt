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
/**
 * 
 */
package org.eclipse.php.internal.core.format;

import java.util.Map;

import org.eclipse.dltk.annotations.NonNull;
import org.eclipse.php.core.ast.visitor.AbstractVisitor;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.text.edits.MultiTextEdit;

/**
 * An empty implementation of the {@link ICodeFormattingProcessor} that performs
 * visits on the ASTNode but does not aggregate any text edits.
 * 
 * @author shalom
 */
public class DefaultCodeFormattingProcessor extends AbstractVisitor implements ICodeFormattingProcessor {
	private final Map<String, String> options;

	public DefaultCodeFormattingProcessor(Map<String, String> options) {
		this.options = options;
	}

	@SuppressWarnings("null")
	@Override
	public @NonNull String createIndentationString(int indentationUnits) {
		if (indentationUnits > 0) {
			String useTabs = options.get(PHPCoreConstants.FORMATTER_USE_TABS);
			if (useTabs != null) {
				String indentation = "\t"; //$NON-NLS-1$
				if ("false".equalsIgnoreCase(useTabs)) { //$NON-NLS-1$
					String sizeValue = options.get(PHPCoreConstants.FORMATTER_INDENTATION_SIZE);
					if (sizeValue != null) {
						StringBuilder sb = new StringBuilder();
						int size = Integer.parseInt(sizeValue);
						for (int i = 0; i < size; i++) {
							sb.append(' ');
						}
						indentation = sb.toString();
					}
				}
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < indentationUnits; i++) {
					sb.append(indentation);
				}
				return sb.toString();
			}

		}
		return ""; //$NON-NLS-1$
	}

	@Override
	public @NonNull MultiTextEdit getTextEdits() {
		return new MultiTextEdit();
	}

}
