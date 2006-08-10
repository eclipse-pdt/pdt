/**
 * Copyright (c) 2006 Zend Technologies
 * 
 */
package org.eclipse.php.ui.util;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.php.ui.actions.CustomFiltersActionGroup;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;

/**
 * @author seva
 *
 */
public class CustomFilterElementTreeSelectionDialog extends ElementTreeSelectionDialog {

	private String preferenceId;
	public CustomFilterElementTreeSelectionDialog(final Shell parent, final ILabelProvider labelProvider, final ITreeContentProvider contentProvider, final String preferenceId) {
		super(parent, labelProvider, contentProvider);
		this.preferenceId = preferenceId;
	}
	
	protected TreeViewer createTreeViewer(final Composite parent) {
		final TreeViewer viewer = super.createTreeViewer(parent);
		new CustomFiltersActionGroup(preferenceId, viewer);
		return viewer;
	}
}
