/*******************************************************************************
 * Copyright (c) 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.preferences.includepath;

import java.util.ArrayList;
import java.util.StringTokenizer;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.*;
import org.eclipse.php.internal.ui.corext.util.Strings;
import org.eclipse.php.internal.ui.util.SWTUtil;
import org.eclipse.php.internal.ui.util.StringMatcher;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.dialogs.FilteredTree;
import org.eclipse.ui.dialogs.PatternFilter;
import org.eclipse.ui.progress.WorkbenchJob;

/**
 * An element tree selection dialog with a filter box on top.
 */
public class FilteredElementTreeSelectionDialog extends
		ElementTreeSelectionDialog {

	private static class MultiplePatternFilter extends PatternFilter {

		private StringMatcher[] fMatchers;
		private final boolean fIsDeepFiltering;

		public MultiplePatternFilter(boolean deepFiltering) {
			fIsDeepFiltering = deepFiltering;
		}

		public void setPattern(String patternString) {
			super.setPattern(patternString);
			fMatchers = null;
			if (patternString != null && patternString.length() > 0) {
				ArrayList res = new ArrayList();
				StringTokenizer tok = new StringTokenizer(patternString, ",;"); //$NON-NLS-1$
				fMatchers = new StringMatcher[tok.countTokens()];
				for (int i = 0; i < fMatchers.length; i++) {
					String token = tok.nextToken().trim();
					if (token.length() > 0) {
						res.add(new StringMatcher(token + '*', true, false));
					}
				}
				if (!res.isEmpty()) {
					fMatchers = (StringMatcher[]) res
							.toArray(new StringMatcher[res.size()]);
				}
			}
		}

		protected boolean wordMatches(String text) {
			if (text != null) {
				if (fMatchers == null || fMatchers.length == 0) {
					return true;
				}
				for (int i = 0; i < fMatchers.length; i++) {
					if (fMatchers[i].match(text)) {
						return true;
					}
				}
			}
			return false;
		}

		/*
		 * @see
		 * org.eclipse.ui.dialogs.PatternFilter#isElementVisible(org.eclipse
		 * .jface.viewers.Viewer, java.lang.Object)
		 * 
		 * @since 3.5
		 */
		public boolean isElementVisible(Viewer viewer, Object element) {
			boolean hasChildren = ((ITreeContentProvider) ((AbstractTreeViewer) viewer)
					.getContentProvider()).hasChildren(element);
			if (fIsDeepFiltering) {
				if (!super.isElementVisible(viewer, element))
					return false;

				// Also apply deep filtering to the other registered filters
				ViewerFilter[] filters = ((TreeViewer) viewer).getFilters();
				for (int i = 0; i < filters.length; i++) {
					if (filters[i] == this)
						continue;
					if (!filters[i].select(viewer, element, element))
						return false;
				}
				return true;
			}
			return hasChildren || isLeafMatch(viewer, element);
		}
	}

	private static class FilteredTreeWithFilter extends FilteredTree {
		private boolean narrowingDown;
		private String previousFilterText;

		public FilteredTreeWithFilter(Composite parent, int treeStyle,
				String initialFilter, boolean deepFiltering) {
			super(parent, treeStyle, new MultiplePatternFilter(deepFiltering),
					true);
			if (initialFilter != null) {
				setFilterText(initialFilter);
				textChanged();
			}

		}

		protected void textChanged() {
			narrowingDown = previousFilterText == null
					|| getFilterString().startsWith(previousFilterText);
			previousFilterText = getFilterString();
			super.textChanged();
		}

		// This is a copy of the super method, but without auto-expansion.
		protected WorkbenchJob doCreateRefreshJob() {
			return new WorkbenchJob(
					IncludePathMessages.FilteredElementTreeSelectionDialog_1) {

				public IStatus runInUIThread(IProgressMonitor monitor) {
					if (treeViewer.getControl().isDisposed()) {
						return Status.CANCEL_STATUS;
					}

					String text = getFilterString();
					if (text == null) {
						return Status.OK_STATUS;
					}

					boolean initial = initialText != null
							&& initialText.equals(text);
					if (initial) {
						getPatternFilter().setPattern(null);
					} else {
						getPatternFilter().setPattern(text);
					}

					Control redrawFalseControl = treeComposite != null ? treeComposite
							: treeViewer.getControl();
					try {
						// don't want the user to see updates that will be made
						// to
						// the tree
						// we are setting redraw(false) on the composite to
						// avoid
						// dancing scrollbar
						redrawFalseControl.setRedraw(false);
						if (!narrowingDown) {
							// collapse all
							TreeItem[] is = treeViewer.getTree().getItems();
							for (int i = 0; i < is.length; i++) {
								TreeItem item = is[i];
								if (item.getExpanded()) {
									treeViewer.setExpandedState(item.getData(),
											false);
								}
							}
						}
						treeViewer.refresh(true);

						updateToolbar(text.length() > 0 && !initial);

					} finally {
						// done updating the tree - set redraw back to true
						TreeItem[] items = getViewer().getTree().getItems();
						if (items.length > 0
								&& getViewer().getTree().getSelectionCount() == 0) {
							treeViewer.getTree().setTopItem(items[0]);
						}
						redrawFalseControl.setRedraw(true);
					}
					return Status.OK_STATUS;
				}
			};
		}

	}

	private String fInitialFilter;
	private boolean fIsDeepFiltering;

	public FilteredElementTreeSelectionDialog(Shell parent,
			ILabelProvider labelProvider, ITreeContentProvider contentProvider) {
		this(parent, labelProvider, contentProvider, true);
	}

	public FilteredElementTreeSelectionDialog(Shell parent,
			ILabelProvider labelProvider, ITreeContentProvider contentProvider,
			boolean isDeepFiltering) {
		super(parent, labelProvider, contentProvider);
		fInitialFilter = null;
		fIsDeepFiltering = isDeepFiltering;
	}

	/**
	 * A comma separate list of patterns that are filled in initial filter list.
	 * Example is: '*.jar, *.zip'
	 * 
	 * @param initialFilter
	 *            the initial filter or <code>null</code>.
	 */
	public void setInitialFilter(String initialFilter) {
		fInitialFilter = initialFilter;
	}

	protected TreeViewer doCreateTreeViewer(Composite parent, int style) {
		FilteredTree tree = new FilteredTreeWithFilter(parent, style,
				fInitialFilter, fIsDeepFiltering);
		tree.setLayoutData(new GridData(GridData.FILL_BOTH));

		applyDialogFont(tree);

		TreeViewer viewer = tree.getViewer();
		SWTUtil.setAccessibilityText(viewer.getControl(),
				Strings.removeMnemonicIndicator(getMessage()));
		return viewer;
	}

}
