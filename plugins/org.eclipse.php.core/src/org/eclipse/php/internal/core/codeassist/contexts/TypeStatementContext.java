/*******************************************************************************
 * Copyright (c) 2009, 2016 IBM Corporation and others.
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
import org.eclipse.php.core.codeassist.ICompletionScope;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.format.PHPHeuristicScanner;

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
public final class TypeStatementContext extends AbstractGlobalStatementContext {
	private boolean isAssignment = false;

	public enum Type {
		CLASS, TRAIT, INTERFACE;
	}

	private Type type;

	public Type getType() {
		return type;
	}

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
			ICompletionScope scope = getCompanion().getScope();

			if (scope.getType() == ICompletionScope.Type.FIELD || scope.getType() == ICompletionScope.Type.FUNCTION
					|| scope.getType() == ICompletionScope.Type.TRAIT_USE
					|| scope.getType() == ICompletionScope.Type.TRAIT_PRECEDENCE) {
				scope = scope.getParent();
			}
			if (scope.getType() != ICompletionScope.Type.BLOCK) {
				return false;
			}
			scope = scope.getParent();
			switch (scope.getType()) {
			case CLASS:
				type = Type.CLASS;
				break;
			case INTERFACE:
				type = Type.INTERFACE;
				break;
			case TRAIT:
				type = Type.TRAIT;
				break;
			default:
				return false;
			}

			isAssignment = scanner2.scanBackward(offset, getCompanion().getScope().getOffset(), '=') > -1;

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
