/*******************************************************************************
 * Copyright (c) 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.ui.util;

import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.php.core.ast.nodes.ASTNode;
import org.eclipse.php.core.ast.nodes.IBinding;
import org.eclipse.php.core.ast.nodes.Identifier;
import org.eclipse.php.core.ast.nodes.Program;
import org.eclipse.php.internal.core.corext.dom.NodeFinder;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.php.ui.editor.SharedASTProvider;

public class PHPSelectionUtil {

	/**
	 * Returns an {@link IModelElement} from the given selection. In case that
	 * the element is not resolvable, return null.
	 * 
	 * @param selection
	 * @param sourceModule
	 * @return The {@link IModelElement} or null.
	 */
	public static IModelElement getSelectionModelElement(int offset, int length, ISourceModule sourceModule) {
		try {
			// https://bugs.eclipse.org/bugs/show_bug.cgi?id=471729
			// https://bugs.eclipse.org/bugs/show_bug.cgi?id=491105
			if (sourceModule == null || !sourceModule.isConsistent()) {
				return null;
			}
		} catch (Exception e) {
			if (DLTKCore.DEBUG) {
				Logger.logException(e);
			}
			return null;
		}
		IModelElement element = null;
		try {
			Program ast = SharedASTProvider.getAST(sourceModule, SharedASTProvider.WAIT_NO, null);
			if (ast != null) {
				ASTNode selectedNode = NodeFinder.perform(ast, offset, length);
				if (selectedNode != null && selectedNode.getType() == ASTNode.IDENTIFIER) {
					IBinding binding = ((Identifier) selectedNode).resolveBinding();
					if (binding != null) {
						element = binding.getPHPElement();
					}
				}
			}
		} catch (Exception e) {
			if (DLTKCore.DEBUG) {
				Logger.logException(e);
			}
		}
		if (element != null) {
			return element;
		}
		try {
			IModelElement[] selected = sourceModule.codeSelect(offset, length);
			if (selected.length > 0) {
				element = selected[0];
			} else {
				element = sourceModule.getElementAt(offset);
			}
		} catch (ModelException e) {
			if (DLTKCore.DEBUG) {
				Logger.logException(e);
			}
		}
		return element;
	}
}
