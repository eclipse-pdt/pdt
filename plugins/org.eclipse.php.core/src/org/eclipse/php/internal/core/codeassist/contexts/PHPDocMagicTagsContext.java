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
package org.eclipse.php.internal.core.codeassist.contexts;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.dltk.annotations.NonNull;
import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.jface.text.BadLocationException;

/**
 * This context represents the state when staying after 'magic tags like
 * 'property' or 'method' tag in PHPDoc block <br/>
 * Examples:
 * 
 * <pre>
 *   1. /**
 *       * @property |
 *   2. /**
 *       * @method Ty|
 * </pre>
 * 
 * @author michael
 */
public class PHPDocMagicTagsContext extends PHPDocTagContext {

	public static final Set<String> TAGS = new HashSet<>();

	static {
		TAGS.add("property"); //$NON-NLS-1$
		TAGS.add("property-read"); //$NON-NLS-1$
		TAGS.add("property-write"); //$NON-NLS-1$
		TAGS.add("method"); //$NON-NLS-1$
	}

	@Override
	public boolean isValid(@NonNull ISourceModule sourceModule, int offset, CompletionRequestor requestor) {
		if (!super.isValid(sourceModule, offset, requestor)) {
			return false;
		}
		try {
			int lastWordOffset = getPreviousWordOffset(2);
			if (lastWordOffset > tagStart) {
				return false;
			}
		} catch (BadLocationException e) {
			return false;
		}
		return TAGS.contains(getTagName().toLowerCase());
	}
}
