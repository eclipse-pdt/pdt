/*******************************************************************************
 * Copyright (c) 2009, 2015 IBM Corporation and others.
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
package org.eclipse.php.internal.core.codeassist.contexts;

import org.eclipse.dltk.annotations.NonNull;
import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.internal.core.Constants;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.php.internal.core.util.text.PHPTextSequenceUtilities;
import org.eclipse.php.internal.core.util.text.TextSequence;

/**
 * This context represents state when staying in a PHPDoc block. <br/>
 * Example:
 * 
 * <pre>
 *   /**
 *    * |
 * </pre>
 * 
 * @author michael
 */
public abstract class PHPDocContext extends AbstractCompletionContext {

	@Override
	public boolean isValid(@NonNull ISourceModule sourceModule, int offset, CompletionRequestor requestor) {
		if (!super.isValid(sourceModule, offset, requestor)) {
			return false;
		}
		return isRightPartitionType();
	}

	protected boolean isRightPartitionType() {
		return getCompanion().getPartitionType() == PHPPartitionTypes.PHP_DOC;
	}

	@Override
	public int getReplacementEnd() throws BadLocationException {
		int prefixEnd = getCompanion().getOffset();
		// NB: getChar(prefixEnd) returns ' ' if offset is at end of document
		while (!Character.isWhitespace(getChar(prefixEnd)) && getChar(prefixEnd) != Constants.TYPE_SEPARATOR_CHAR) {
			++prefixEnd;
		}
		return prefixEnd;
	}

	@Override
	@NonNull
	public TextSequence getStatementText() {
		return PHPTextSequenceUtilities.getStatement(getCompanion().getOffset(),
				getCompanion().getStructuredDocumentRegion(), false);
	}
}
