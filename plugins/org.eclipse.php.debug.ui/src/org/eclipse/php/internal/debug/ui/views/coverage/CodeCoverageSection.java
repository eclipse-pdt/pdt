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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.php.internal.core.PHPToolkitUtil;
import org.eclipse.php.internal.debug.core.zend.debugger.CodeCoverageData;
import org.eclipse.php.internal.debug.ui.PHPDebugUIMessages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.part.ViewPart;

/**
 * Code coverage section part.
 */
public class CodeCoverageSection implements Listener {

	private CodeCoverageActionGroup actionGroup;
	private Cursor arrowCursor;
	private CodeCoverageContentProvider cProvider;
	private Cursor handCursor;
	private CodeCoverageViewer viewer;
	public static final String CODE_COVERAGE_ICON_PATH = "obj16/cov_statistic_co.gif"; //$NON-NLS-1$

	public CodeCoverageSection(final Composite parent, final ViewPart view, final ToolBar codeCoverageToolBar) {
		viewer = new CodeCoverageViewer(parent);
		viewer.setContentProvider(cProvider = new CodeCoverageContentProvider(/* treeProviders */));
		viewer.setLabelProvider(new CodeCoverageLabelProvider(/* treeProviders, */cProvider));
		viewer.setSorter(new CodeCoverageSorter());
		actionGroup = new CodeCoverageActionGroup(viewer);
		viewer.getTree().addListener(SWT.MouseDoubleClick, this);
		viewer.getTree().addListener(SWT.MouseDown, this);
		viewer.getTree().addListener(SWT.MouseMove, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swt.widgets.Listener#handleEvent(org.eclipse.swt.widgets.
	 * Event)
	 */
	public void handleEvent(final Event event) {
		final Point p = new Point(event.x, event.y);
	
		final TreeItem item = getTreeItem(p);
		final int column = getColumn(p);
	
		if (event.type == SWT.MouseDoubleClick)
			doubleClick(item, column);
		if (event.type == SWT.MouseDown)
			click(item, column);
		if (event.type == SWT.MouseMove)
			hover(item, column);
	
	}

	public void addFilter(ViewerFilter filter) {
		viewer.addFilter(filter);
	}

	public void dispose() {
		if (arrowCursor != null)
			arrowCursor.dispose();
		if (handCursor != null)
			handCursor.dispose();
		if (!viewer.getTree().isDisposed()) {
			viewer.getTree().removeListener(SWT.MouseDoubleClick, this);
			viewer.getTree().removeListener(SWT.MouseDown, this);
			viewer.getTree().removeListener(SWT.MouseMove, this);
			viewer.dispose();
		}
	}

	public Control getComposite() {
		return viewer.getTree();
	}

	public void showCodeCoverage(final CodeCoverageData[] coveredFiles) {
		if (coveredFiles != null) {
			if (!viewer.getTree().isDisposed()) {
				// try to detect project:
				for (CodeCoverageData element : coveredFiles) {
					String localFileName = element.getLocalFileName();
					IResource resource = ResourcesPlugin.getWorkspace().getRoot()
							.getFileForLocation(new Path(localFileName));
					if (resource != null && resource instanceof IFile) {
						IFile file = (IFile) resource;
						cProvider.setProject(file.getProject());
						break;
					}
				}
				cProvider.setCoveredFiles(coveredFiles);
				viewer.setAutoExpandLevel(2);
				viewer.getControl().setRedraw(false);
				viewer.setInput(ResourcesPlugin.getWorkspace().getRoot());
				viewer.getControl().setRedraw(true);
				viewer.refresh(false);
			}
		} else {
			if (viewer.getContentProvider() != null) {
				viewer.setInput(null);
			}
		}
	}

	private void click(final TreeItem item, final int column) {
		Object fileData = getFileData(item);
		if (column == 1
				&& (fileData instanceof ISourceModule || fileData instanceof IFile || fileData instanceof String))
			actionGroup.showCoverage(fileData);
	}

	private void doubleClick(final TreeItem item, final int column) {
		if (column == 0 && item != null)
			actionGroup.doubleClickFile(item.getData());
	}

	private Cursor getArrowCursor() {
		if (arrowCursor == null)
			arrowCursor = new Cursor(viewer.getTree().getDisplay(), SWT.CURSOR_ARROW);
		return arrowCursor;
	}

	private int getColumn(final Point p) {
		final TreeColumn[] columns = viewer.getTree().getColumns();
		final int[] order = viewer.getTree().getColumnOrder();
		final Rectangle bounds = viewer.getTree().getBounds();
		final int gridLineWidth = viewer.getTree().getGridLineWidth();
		int left = bounds.x;
		int right;
		for (int i = 0; i < columns.length; ++i) {
			right = left + columns[order[i]].getWidth();
			if (left <= p.x && p.x < right)
				return order[i];
			left += columns[order[i]].getWidth() + gridLineWidth;
		}
		return 0;

	}

	private Object getFileData(final TreeItem item) {
		if (item == null)
			return null;
		ISourceModule fileData = PHPToolkitUtil.getSourceModule(item.getData());
		if (fileData != null) {
			return fileData;
		}
		return item.getData();
	}

	private Cursor getHandCursor() {
		if (handCursor == null)
			handCursor = new Cursor(viewer.getTree().getDisplay(), SWT.CURSOR_HAND);
		return handCursor;
	}

	private TreeItem getTreeItem(final Point p) {
		final TreeItem[] items = viewer.getTree().getItems();
		TreeItem foundItem = null;
		for (int i = 0; i < items.length; ++i) {
			foundItem = getTreeItemRecursive(p, items[i]);
			if (foundItem != null)
				return foundItem;
		}
		return null;

	}

	private TreeItem getTreeItemRecursive(final Point p, final TreeItem item) {
		final Rectangle bounds = item.getBounds();
		TreeItem foundItem = null;
		if (bounds.y <= p.y && bounds.y + bounds.height > p.y)
			return item;
		final TreeItem[] items = item.getItems();
		for (int i = 0; i < items.length; ++i) {
			foundItem = getTreeItemRecursive(p, items[i]);
			if (foundItem != null)
				return foundItem;
		}
		return null;
	}

	private void hover(final TreeItem item, final int column) {
		boolean isLink = false;
		Object fileData = getFileData(item);
		if (column == 1
				&& (fileData instanceof ISourceModule || fileData instanceof IFile || fileData instanceof String))
			isLink = true;
		viewer.getTree().setCursor(isLink ? getHandCursor() : getArrowCursor());
		if (isLink)
			viewer.getTree().setToolTipText(PHPDebugUIMessages.CodeCoverageSection_1);
		else
			viewer.getTree().setToolTipText(null);
	}
}
