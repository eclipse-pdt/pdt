/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.ui.views.coverage;

import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.php.internal.debug.ui.PHPDebugUIMessages;
import org.eclipse.php.internal.debug.ui.preferences.coverage.CodeCoveragePreferenceKeys;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;

/**
 * Code coverage viewer.
 */
public class CodeCoverageViewer extends TreeViewer {
	private TreeColumn[] treeColumns = new TreeColumn[2];

	public CodeCoverageViewer(final Composite parent) {
		super(parent, SWT.SINGLE | SWT.V_SCROLL | SWT.H_SCROLL);
		createColumns();
		getTree().addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				dispose();
			}
		});
	}

	protected void createColumns() {
		Tree tree = getTree();
		TableLayout layout = new TableLayout();
		tree.setLayout(layout);
		tree.setHeaderVisible(true);
		tree.setLinesVisible(true);
		int[] columnWidths = CodeCoveragePreferenceKeys.getCodeCoverageColumnWidths();
		int i = 0;
		treeColumns[i] = new TreeColumn(tree, SWT.LEFT, i);
		treeColumns[i].setText(PHPDebugUIMessages.CodeCoverageViewer_0);
		treeColumns[i].setMoveable(false);
		treeColumns[i].setWidth(columnWidths[i]);
		++i;
		treeColumns[i] = new TreeColumn(tree, SWT.LEFT, i);
		treeColumns[i].setText(PHPDebugUIMessages.CodeCoverageViewer_1);
		treeColumns[i].setMoveable(false);
		treeColumns[i].setWidth(columnWidths[i]);
	}

	public void dispose() {
		CodeCoveragePreferenceKeys.setCodeCoverageColumnWidths(getColumnWidths());
		getTree().dispose();
	}

	int[] getColumnWidths() {
		int[] widths = new int[treeColumns.length];
		for (int i = 0; i < treeColumns.length; ++i) {
			widths[i] = treeColumns[i].getWidth();
		}
		return widths;
	}

	void setColumnWidths(int[] widths) {
		boolean isParamLimiter = widths.length < treeColumns.length;
		int limit = isParamLimiter ? widths.length : treeColumns.length;
		for (int i = 0; i < limit; ++i) {
			treeColumns[i].setWidth(widths[i]);
		}
		if (!isParamLimiter) {
			for (int i = widths.length; i < treeColumns.length; ++i) {
				treeColumns[i].setWidth(widths[widths.length - 1]);
			}
		}
	}
}