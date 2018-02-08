/*******************************************************************************
 * Copyright (c) 2009, 2016 IBM Corporation and others.
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

import org.apache.commons.lang3.StringUtils;
import org.eclipse.dltk.annotations.NonNull;
import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IRegion;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.format.PHPHeuristicScanner;
import org.eclipse.php.internal.core.util.text.PHPTextSequenceUtilities;
import org.eclipse.php.internal.core.util.text.TextSequence;

/**
 * This context represents state when staying in a class statements. <br/>
 * Examples:
 * 
 * <pre>
 *  1. class A { | }
 *  2. class A { p| }
 *  etc...
 * </pre>
 * 
 * @author michael
 */
public final class ClassStatementContext extends AbstractGlobalStatementContext {
	private boolean isAssignment = false;

	@Override
	public boolean isValid(@NonNull ISourceModule sourceModule, int offset, CompletionRequestor requestor) {
		if (!super.isValid(sourceModule, offset, requestor)) {
			return false;
		}

		// check whether enclosing element is class
		try {
			PHPHeuristicScanner scanner2 = PHPHeuristicScanner.createHeuristicScanner(getCompanion().getDocument(),
					offset, true);
			if (!scanner2.isDefaultPartition(offset)) {
				return false;
			}
			IRegion surroundingBlock = scanner2.findSurroundingBlock(offset);
			if (surroundingBlock == null) {
				return false;
			}
			TextSequence statement = PHPTextSequenceUtilities.getStatement(surroundingBlock.getOffset(),
					getCompanion().getStructuredDocumentRegion(), true);
			if (!StringUtils.startsWithAny(statement.toString().toLowerCase(), "class", "interface", "trait")) { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				return false;
			}

			isAssignment = scanner2.scanBackward(offset, statement.getOriginalOffset(0), '=') > -1;
			return true;
		} catch (BadLocationException e) {
			PHPCorePlugin.log(e);
		}

		return false;
	}

	public boolean isAssignment() {
		return isAssignment;
	}
}
