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
package org.eclipse.php.internal.debug.ui.views.variables;

import org.eclipse.debug.internal.ui.model.elements.VariableEditor;
import org.eclipse.debug.internal.ui.viewers.model.provisional.IPresentationContext;
import org.eclipse.jface.viewers.ICellModifier;

/**
 * PHPVariableColumnEditor that returns PHPVariableCellModifiers.
 * 
 * @author shalom
 */
public class PHPVariableColumnEditor extends VariableEditor {

	public ICellModifier getCellModifier(IPresentationContext context,
			Object element) {
		return new PHPVariableCellModifier();
	}
}
