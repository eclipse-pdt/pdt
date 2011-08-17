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
package org.eclipse.php.internal.core.codeassist.contexts;

import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.internal.core.compiler.ast.nodes.NamespaceReference;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPPartitionTypes;

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
public abstract class NamespacePHPDocContext extends AbstractCompletionContext {

	public boolean isValid(ISourceModule sourceModule, int offset,
			CompletionRequestor requestor) {
		if (!super.isValid(sourceModule, offset, requestor)) {
			return false;
		}
		return getPartitionType() == PHPPartitionTypes.PHP_DOC;
	}

	public int getPrefixEnd() throws BadLocationException {
		int prefixEnd = getOffset();
		while (!Character.isWhitespace(getDocument().getChar(prefixEnd))) {
			++prefixEnd;
		}
		return prefixEnd;
	}

	public String getPrefix() throws BadLocationException {
		String prefix = super.getPrefix();
		if (prefix.length() > 0
				&& prefix.charAt(0) == NamespaceReference.NAMESPACE_SEPARATOR) {
			return prefix.substring(1);
		}
		return prefix;
	}
}
