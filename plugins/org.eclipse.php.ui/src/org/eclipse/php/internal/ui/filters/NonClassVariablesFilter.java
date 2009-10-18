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
package org.eclipse.php.internal.ui.filters;

import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IField;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

/**
 * This class used for filtering non-class variable declarations, like: in
 * method, in function parameters, global variables, etc...
 */
public class NonClassVariablesFilter extends ViewerFilter {

	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (element instanceof IField) {
			IField field = (IField) element;
			try {
				if ((field.getFlags() & Modifiers.AccConstant) == 0) {
					return (field.getParent().getElementType() == IModelElement.TYPE);
				}
			} catch (ModelException e) {
				if (DLTKCore.DEBUG) {
					e.printStackTrace();
				}
			}
		}
		return true;
	}

}
