/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.explorer;

import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.internal.ui.navigator.ScriptExplorerContentProvider;
import org.eclipse.dltk.internal.ui.navigator.ScriptExplorerLabelProvider;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * 
 * @author apeled, nirc
 *
 */
public class PHPExplorerLabelProvider extends ScriptExplorerLabelProvider {

	public PHPExplorerLabelProvider(ScriptExplorerContentProvider cp, IPreferenceStore store) {
		super(cp, store);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.dltk.internal.ui.navigator.ScriptExplorerLabelProvider#getText(java.lang.Object)
	 * 
	 * Override the default text - do not display a full path for a folder
	 */
	@Override
	public String getText(Object element) {
		if (element instanceof IModelElement) {
			return ((IModelElement) element).getPath().lastSegment();
		}
		return super.getText(element);
	}

}
