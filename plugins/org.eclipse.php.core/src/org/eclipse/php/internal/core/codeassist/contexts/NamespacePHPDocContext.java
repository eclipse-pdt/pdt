/*******************************************************************************
 * Copyright (c) 2009, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.core.codeassist.contexts;

import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.compiler.ast.nodes.NamespaceReference;
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
 * @todo this class should extend PHPDocContext rather than
 *       AbstractCompletionContext
 */
public abstract class NamespacePHPDocContext extends AbstractCompletionContext {

	public boolean isValid(ISourceModule sourceModule, int offset, CompletionRequestor requestor) {
		if (!super.isValid(sourceModule, offset, requestor)) {
			return false;
		}
		return isRightPartitionType();
	}

	protected boolean isRightPartitionType() {
		return getPartitionType() == PHPPartitionTypes.PHP_DOC;
	}

	public int getPrefixEnd() throws BadLocationException {
		int prefixEnd = getOffset();
		// NB: getChar(prefixEnd) returns ' ' if offset is at end of document
		while (!Character.isWhitespace(getChar(prefixEnd)) && getChar(prefixEnd) != Constants.TYPE_SEPERATOR_CHAR) {
			++prefixEnd;
		}
		return prefixEnd;
	}

	public String getPrefix() throws BadLocationException {
		String prefix = super.getPrefix();
		if (prefix.length() > 0 && prefix.charAt(0) == NamespaceReference.NAMESPACE_SEPARATOR) {
			return prefix.substring(1);
		}
		return prefix;
	}

	@Override
	public TextSequence getStatementText() {
		return PHPTextSequenceUtilities.getStatement(getOffset(), getStructuredDocumentRegion(), false);
	}

	@Override
	public TextSequence getStatementText(int offset) {
		return PHPTextSequenceUtilities.getStatement(offset, getStructuredDocumentRegion(), false);
	}

}
