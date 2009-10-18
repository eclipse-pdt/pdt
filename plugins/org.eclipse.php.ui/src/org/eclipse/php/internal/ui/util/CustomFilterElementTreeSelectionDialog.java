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
package org.eclipse.php.internal.ui.util;

import org.eclipse.dltk.ui.actions.CustomFiltersActionGroup;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;

/**
 * @author seva
 * 
 */
public class CustomFilterElementTreeSelectionDialog extends
		ElementTreeSelectionDialog {

	private String preferenceId;

	public CustomFilterElementTreeSelectionDialog(final Shell parent,
			final ILabelProvider labelProvider,
			final ITreeContentProvider contentProvider,
			final String preferenceId) {
		super(parent, labelProvider, contentProvider);
		this.preferenceId = preferenceId;
	}

	protected TreeViewer createTreeViewer(final Composite parent) {
		final TreeViewer viewer = super.createTreeViewer(parent);
		new CustomFiltersActionGroup(preferenceId, viewer);
		return viewer;
	}
}
