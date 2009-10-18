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
package org.eclipse.php.internal.ui;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;

public class PHPTreeViewer extends TreeViewer {

	public PHPTreeViewer(Composite parent) {
		super(parent);
	}

	public PHPTreeViewer(Composite parent, int style) {
		super(parent, style);
	}

	public PHPTreeViewer(Tree tree) {
		super(tree);
	}

	ISelection storedSelection;

	/**
	 * @return the storedSelection
	 */
	public ISelection getStoredSelection() {
		return storedSelection;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.StructuredViewer#setSelection(org.eclipse.jface
	 * .viewers.ISelection, boolean)
	 */
	public void setSelection(ISelection selection, boolean reveal) {
		storedSelection = selection;
		// TODO Auto-generated method stub
		super.setSelection(selection, reveal);
	}

}
