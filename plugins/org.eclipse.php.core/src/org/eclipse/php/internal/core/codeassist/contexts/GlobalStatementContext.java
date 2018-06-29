/*******************************************************************************
 * Copyright (c) 2009,2015 IBM Corporation and others.
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
import org.eclipse.dltk.core.*;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.php.internal.core.format.PHPHeuristicScanner;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;

/**
 * This context represents state when staying in a top level statement. <br/>
 * Examples:
 * 
 * <pre>
 *  1. |
 *  2. pri|
 *  3. $v|
 *  etc...
 * </pre>
 * 
 * @author michael
 */
public class GlobalStatementContext extends AbstractGlobalStatementContext {

	@Override
	public boolean isValid(@NonNull ISourceModule sourceModule, int offset, CompletionRequestor requestor) {
		if (!super.isValid(sourceModule, offset, requestor)) {
			return false;
		}

		// check whether enclosing element is not a class
		try {
			IModelElement enclosingElement = getEnclosingElement();
			if (enclosingElement == null) {
				int actualOffset = offset - 1;
				try {
					PHPHeuristicScanner scanner = PHPHeuristicScanner
							.createHeuristicScanner(getCompanion().getDocument(), actualOffset, true);
					int index = scanner.findOpeningPeer(actualOffset, PHPHeuristicScanner.UNBOUND,
							PHPHeuristicScanner.LBRACE, PHPHeuristicScanner.RBRACE);
					if (index != PHPHeuristicScanner.NOT_FOUND) {
						ITextRegion textRegion = getCompanion().getPHPToken(index);
						while (textRegion.getStart() != 0 && textRegion.getType() != PHPRegionTypes.PHP_CURLY_CLOSE) {
							if (textRegion.getType() == PHPRegionTypes.PHP_CLASS
									|| textRegion.getType() == PHPRegionTypes.PHP_INTERFACE
									|| textRegion.getType() == PHPRegionTypes.PHP_TRAIT
									|| textRegion.getType() == PHPRegionTypes.PHP_FUNCTION) {
								return false;
							}

							actualOffset = textRegion.getStart() - 1;
							textRegion = getCompanion().getPHPScriptRegion().getPHPToken(actualOffset);
						}
					}
				} catch (BadLocationException e1) {
					PHPCorePlugin.log(e1);
				}
			}
			while (enclosingElement instanceof IField) {
				enclosingElement = enclosingElement.getParent();
			}
			if ((enclosingElement instanceof IMethod) || (enclosingElement instanceof IType
					&& !PHPFlags.isNamespace(((IType) enclosingElement).getFlags()))) {
				if (enclosingElement instanceof IType && isBeforeName(offset, (ISourceReference) enclosingElement)) {
					return true;
				}
				return false;
			}
		} catch (ModelException e) {
			PHPCorePlugin.log(e);
		}

		return true;
	}
}
