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
package org.eclipse.php.internal.ui.text;

import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ITypeHierarchy;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.ui.text.ScriptOutlineInformationControl;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.corext.util.SuperTypeHierarchyCache;
import org.eclipse.swt.widgets.Shell;

/**
 * Information control for the PHP language
 * 
 * @author Roy, 2009
 * 
 */
public class PHPOutlineInformationControl extends
		ScriptOutlineInformationControl {

	public PHPOutlineInformationControl(Shell parent, int shellStyle,
			int treeStyle, String commandId) {
		super(parent, shellStyle, treeStyle, commandId, PHPUiPlugin
				.getDefault().getPreferenceStore());
	}

	protected ITypeHierarchy getSuperTypeHierarchy(
			org.eclipse.dltk.core.IType type) {

		ITypeHierarchy th = (ITypeHierarchy) fTypeHierarchies.get(type);
		if (th == null) {
			try {
				th = SuperTypeHierarchyCache.getTypeHierarchy(type,
						getProgressMonitor());
			} catch (ModelException e) {
				return null;
			} catch (OperationCanceledException e) {
				return null;
			}
			fTypeHierarchies.put(type, th);
		}
		return th;
	};

	@Override
	protected boolean isInnerType(IModelElement element) {
		if (element != null && element.getElementType() == IModelElement.TYPE) {
			IType type = (IType) element;
			type = type.getDeclaringType();
			try {
				if (type != null && !PHPFlags.isNamespace(type.getFlags())) {
					return true;
				}
			} catch (ModelException e) {
			}
		}
		return false;
	}
}