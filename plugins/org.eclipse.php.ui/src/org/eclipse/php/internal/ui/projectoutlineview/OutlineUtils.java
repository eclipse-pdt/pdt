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
package org.eclipse.php.internal.ui.projectoutlineview;

import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.core.*;
import org.eclipse.php.core.compiler.PHPFlags;

public class OutlineUtils {

	protected static boolean isConstant(IModelElement modelElement) {

		if (modelElement instanceof IField && modelElement.getParent() instanceof ISourceModule) {
			int flags = 0;
			try {
				flags = ((IField) modelElement).getFlags();
			} catch (ModelException e) {
			}
			if ((flags & Modifiers.AccConstant) != 0) {
				return true;
			}
		}
		return false;
	}

	protected static boolean isGlobalClass(IModelElement modelElement) {
		try {
			if (modelElement instanceof IType && !PHPFlags.isNamespace(((IType) modelElement).getFlags())
					&& modelElement.getParent() instanceof ISourceModule) {
				return true;
			}
		} catch (ModelException e) {
		}
		return false;
	}

	protected static boolean isGlobalFunction(IModelElement modelElement) {
		if (modelElement instanceof IMethod && modelElement.getParent() instanceof ISourceModule) {
			return true;
		}
		return false;
	}
}
