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
package org.eclipse.php.phpunit.ui.view;

import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.dltk.ui.viewsupport.SelectionProviderMediator;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.*;
import org.eclipse.php.phpunit.PHPUnitPlugin;
import org.eclipse.php.phpunit.model.elements.*;
import org.eclipse.php.phpunit.model.providers.PHPUnitElementTreeContentProvider;
import org.eclipse.php.phpunit.ui.preference.PHPUnitPreferenceKeys;
import org.eclipse.php.phpunit.ui.view.actions.OpenTestAction;
import org.eclipse.php.phpunit.ui.view.actions.RerunAction;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.part.PageBook;

public class TestViewer {
	private final class FailuresOnlyFilter extends ViewerFilter {
		public boolean select(final PHPUnitElement testInfo) {
			if (testInfo instanceof PHPUnitTestGroup) {
				final PHPUnitTestGroup testGroup = (PHPUnitTestGroup) testInfo;
				if (testGroup.getRunCount() > testGroup.getTotalCount())
					return true;
				if (testInfo == PHPUnitElementManager.getInstance().getRoot())
					return true;
			}
			if (testInfo instanceof PHPUnitTest && ((PHPUnitTest) testInfo).getStatus() >= PHPUnitTest.STATUS_FAIL) {
				return true;
			}
			return false;
		}

		@Override
		public boolean select(final Viewer viewer, final Object parentElement, final Object element) {
			return select(((PHPUnitElement) element));
		}
	}

	private final class TestSelectionListener implements ISelectionChangedListener {
		@Override
		public void selectionChanged(final SelectionChangedEvent event) {
			handleSelected();
		}

		private void handleSelected() {
			final IStructuredSelection selection = (IStructuredSelection) fSelectionProvider.getSelection();
			PHPUnitElement test = null;
			if (selection.size() == 1) {
				test = (PHPUnitElement) selection.getFirstElement();
			}
			view.handleTestSelected(test);
		}
	}

	private LinkedList<Object>/* <TestSuiteElement> */ fAutoClose;

	private Set<Object> fAutoExpand;
	private PHPUnitTestCase fAutoScrollTarget;

	private final FailuresOnlyFilter fFailuresOnlyFilter = new FailuresOnlyFilter();
	private final Image fHierarchyIcon;
	private Set<PHPUnitElement> fNeedUpdate;
	private IPostSelectionProvider fSelectionProvider;

	private PHPUnitElementTreeContentProvider fTreeContentProvider;

	private boolean fTreeHasFilter;

	private boolean fTreeNeedsRefresh;
	private TreeViewer fTreeViewer;
	private PageBook fViewerbook;

	private PHPUnitTestGroup testRoot;
	private final PHPUnitView view;

	private IPropertyChangeListener preferenceChangeListener = new IPropertyChangeListener() {

		@Override
		public void propertyChange(PropertyChangeEvent event) {
			if (StringUtils.equals(event.getProperty(), PHPUnitPreferenceKeys.SHOW_EXECUTION_TIME)) {
				getActiveViewer().refresh();
			}
		}
	};

	public TestViewer(final Composite parent, final PHPUnitView runner) {
		view = runner;

		fHierarchyIcon = PHPUnitPlugin.createImage("obj16/testhier.png"); //$NON-NLS-1$
		parent.addDisposeListener(e -> disposeIcons());

		createTestViewers(parent);
		registerViewersRefresh();

		PHPUnitPlugin.getDefault().getPreferenceStore().addPropertyChangeListener(preferenceChangeListener);

		initContextMenu();
	}

	private void autoScrollInUI() {
		if (!view.isAutoScroll()) {
			clearAutoExpand();
			fAutoClose.clear();
			return;
		}

		synchronized (this) {
			for (final Iterator<Object> iter = fAutoExpand.iterator(); iter.hasNext();) {
				final PHPUnitTestGroup suite = (PHPUnitTestGroup) iter.next();
				fTreeViewer.setExpandedState(suite, true);
			}
			clearAutoExpand();
		}

		final PHPUnitTestCase current = fAutoScrollTarget;
		fAutoScrollTarget = null;

		PHPUnitTestGroup parent = current == null ? null : (PHPUnitTestGroup) fTreeContentProvider.getParent(current);
		if (fAutoClose.isEmpty() || !fAutoClose.getLast().equals(parent)) {
			// we're in a new branch, so let's close old OK branches:
			for (final ListIterator<Object> iter = fAutoClose.listIterator(fAutoClose.size()); iter.hasPrevious();) {
				final PHPUnitTestGroup previousAutoOpened = (PHPUnitTestGroup) iter.previous();
				if (previousAutoOpened.equals(parent))
					break;

				if (previousAutoOpened.getStatus() == PHPUnitTest.STATUS_PASS) {
					// auto-opened the element, and all children are OK -> auto
					// close
					iter.remove();
					fTreeViewer.collapseToLevel(previousAutoOpened, AbstractTreeViewer.ALL_LEVELS);
				}
			}

			while (parent != null && !testRoot.equals(parent) && fTreeViewer.getExpandedState(parent) == false) {
				fAutoClose.add(parent); // add to auto-opened elements -> close
				// later if STATUS_OK
				parent = (PHPUnitTestGroup) fTreeContentProvider.getParent(parent);
			}
		}
		if (current != null) {
			fTreeViewer.reveal(current);
		}
	}

	private synchronized void clearAutoExpand() {
		fAutoExpand.clear();
	}

	private void clearUpdateAndExpansion() {
		fNeedUpdate = new LinkedHashSet<>();
		fAutoClose = new LinkedList<>();
		fAutoExpand = new HashSet<>();
	}

	public void collapseAll() {
		fTreeViewer.collapseAll();
		fTreeViewer.refresh();
	}

	private void createTestViewers(final Composite parent) {
		fViewerbook = new PageBook(parent, SWT.NULL);

		fTreeViewer = new TreeViewer(fViewerbook, SWT.V_SCROLL | SWT.SINGLE);
		fTreeViewer.setUseHashlookup(true);
		fTreeContentProvider = new PHPUnitElementTreeContentProvider();
		fTreeViewer.setContentProvider(fTreeContentProvider);
		DecoratingStyledCellLabelProvider labelProvider = new DecoratingStyledCellLabelProvider(
				new TestLabelProvider(view), null, null);
		fTreeViewer.setLabelProvider(labelProvider);

		fSelectionProvider = new SelectionProviderMediator(new StructuredViewer[] { fTreeViewer }, fTreeViewer);
		fSelectionProvider.addSelectionChangedListener(new TestSelectionListener());
		fTreeViewer.addDoubleClickListener(event -> handleDefaultSelected((IStructuredSelection) event.getSelection()));

		fViewerbook.showPage(fTreeViewer.getTree());
	}

	void disposeIcons() {
		fHierarchyIcon.dispose();
		PHPUnitPlugin.getDefault().getPreferenceStore().removePropertyChangeListener(preferenceChangeListener);
	}

	public void expandAll() {
		fTreeViewer.expandAll();
		fTreeViewer.refresh();
	}

	private StructuredViewer getActiveViewer() {
		return fTreeViewer;
	}

	private boolean getActiveViewerHasFilter() {
		return fTreeHasFilter;
	}

	private boolean getActiveViewerNeedsRefresh() {
		return fTreeNeedsRefresh;
	}

	private PHPUnitTestCase getNextChildFailure(final PHPUnitTestGroup root, boolean showNext) {
		List<PHPUnitTest> children = new ArrayList<>(root.getChildren());
		if (!showNext) {
			Collections.reverse(children);
		}
		for (PHPUnitTest child : children) {
			if (child.getStatus() > PHPUnitTest.STATUS_PASS) {
				if (child instanceof PHPUnitTestCase) {
					return (PHPUnitTestCase) child;
				}
				return getNextChildFailure((PHPUnitTestGroup) child, showNext);
			}
		}
		return null;
	}

	private PHPUnitElement getNextFailure(final PHPUnitElement selected, final boolean showNext) {
		if (selected instanceof PHPUnitTestGroup) {
			final PHPUnitElement nextChild = getNextChildFailure((PHPUnitTestGroup) selected, showNext);
			if (nextChild != null) {
				return nextChild;
			}
		}
		return getNextFailureSibling(selected, showNext);
	}

	private PHPUnitTestCase getNextFailureSibling(final PHPUnitElement current, boolean showNext) {
		final PHPUnitTestGroup parent = (PHPUnitTestGroup) current.getParent();
		if (parent == null) {
			return null;
		}

		Set<PHPUnitTest> children = parent.getChildren();
		if (children == null) {
			return null;
		}
		List<PHPUnitTest> siblings = new ArrayList<>(children);
		if (!showNext) {
			Collections.reverse(siblings);
		}

		final int nextIndex = siblings.indexOf(current) + 1;
		for (int i = nextIndex; i < siblings.size(); i++) {
			final PHPUnitTest sibling = siblings.get(i);
			if (sibling.getStatus() > PHPUnitTest.STATUS_PASS) {
				if (sibling instanceof PHPUnitTestCase)
					return (PHPUnitTestCase) sibling;
				return getNextChildFailure((PHPUnitTestGroup) sibling, showNext);
			}
		}
		return getNextFailureSibling(parent, showNext);
	}

	public Control getTestViewerControl() {
		return fViewerbook;
	}

	void handleDefaultSelected(IStructuredSelection selection) {
		if (selection.isEmpty()) {
			return;
		}

		PHPUnitTest test = (PHPUnitTest) selection.getFirstElement();

		OpenTestAction action = null;
		if (test instanceof PHPUnitTestCase) {
			final PHPUnitTestCase testCase = (PHPUnitTestCase) test;
			if (((PHPUnitTestCase) test).isDataProviderCase()) {
				test = (PHPUnitTest) test.getParent();
			} else {
				action = new OpenTestAction(null, view, ((PHPUnitTestGroup) testCase.getParent()).getName(),
						testCase.getLocalFile(), testCase.getLine(), testCase.getName());
			}

		}
		if (test instanceof PHPUnitTestGroup) {
			if (((PHPUnitTestGroup) test).isMethod()) {
				PHPUnitTestGroup parent = (PHPUnitTestGroup) test.getParent();
				action = new OpenTestAction(null, view, parent.getName(), test.getLocalFile(), test.getLine(),
						test.getName());
			} else {
				action = new OpenTestAction(null, view, test.getName(), test.getLocalFile(), test.getLine());
			}
		} else if (action == null) {
			throw new IllegalStateException(String.valueOf(test));
		}

		if (action.isEnabled()) {
			action.run();
		}
	}

	void handleMenuAboutToShow(final IMenuManager manager) {
		final IStructuredSelection selection = (IStructuredSelection) fSelectionProvider.getSelection();
		if (!selection.isEmpty()) {
			final PHPUnitTest test = (PHPUnitTest) selection.getFirstElement();

			final String testLabel = test.getName();
			String fileName = test.getLocalFile();
			int lineNumber = test.getLine();
			if (test instanceof PHPUnitTestGroup) {
				if (((PHPUnitTestGroup) test).isMethod()) {
					PHPUnitTestGroup parent = (PHPUnitTestGroup) test.getParent();
					manager.add(new OpenTestAction(null, view, parent.getName(), fileName, lineNumber, testLabel));
				} else {
					manager.add(new OpenTestAction(null, view, testLabel, fileName, lineNumber));
				}
				manager.add(new Separator());
				manager.add(new RerunAction(view, test.getTestId(), ILaunchManager.RUN_MODE));
				manager.add(new RerunAction(view, test.getTestId(), ILaunchManager.DEBUG_MODE));
			} else {
				String testMethodName = test.getName();
				PHPUnitTestGroup parent = (PHPUnitTestGroup) test.getParent();

				if (parent.isMethod()) {
					parent = ((PHPUnitTestGroup) parent.getParent());
				}
				fileName = parent.getFile();
				lineNumber = parent.getLine();
				if (test instanceof PHPUnitTestCase && !((PHPUnitTestCase) test).isDataProviderCase()) {
					manager.add(new OpenTestAction(null, view, parent.getName(), fileName, lineNumber, testMethodName));
					manager.add(new Separator());
				}
				manager.add(new RerunAction(view, test.getTestId(), ILaunchManager.RUN_MODE));
				manager.add(new RerunAction(view, test.getTestId(), ILaunchManager.DEBUG_MODE));
			}
		}
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS + "-end")); //$NON-NLS-1$
	}

	private void initContextMenu() {
		final MenuManager menuMgr = new MenuManager("#PopupMenu"); //$NON-NLS-1$
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(manager -> handleMenuAboutToShow(manager));
		view.getSite().registerContextMenu(menuMgr, fSelectionProvider);
		final Menu menu = menuMgr.createContextMenu(fViewerbook);
		fTreeViewer.getTree().setMenu(menu);
	}

	private boolean isShown(final PHPUnitElement current) {
		return fFailuresOnlyFilter.select(current);
	}

	/**
	 * To be called periodically by the PHPUnitView (in the UI thread).
	 */
	public void processChangesInUI() {
		if (view.isDisposed()) {
			return;
		}
		if (testRoot == null) {
			registerViewersRefresh();
			fTreeNeedsRefresh = false;
			fTreeViewer.setInput(null);
			return;
		}

		final StructuredViewer viewer = getActiveViewer();
		if (viewer.getInput() != testRoot) {
			viewer.setInput(testRoot);
		}
		viewer.refresh();

		if (getActiveViewerNeedsRefresh()) {
			clearUpdateAndExpansion();
			setActiveViewerRefreshed();
		} else {
			PHPUnitElement[] toUpdate;
			synchronized (this) {
				toUpdate = fNeedUpdate.toArray(new PHPUnitElement[0]);
				fNeedUpdate.clear();
			}
			if (!fTreeNeedsRefresh && toUpdate.length > 0)
				if (fTreeHasFilter)
					for (PHPUnitElement element : toUpdate)
						updateElementInTree(element);
				else {
					final Set<PHPUnitElement> toUpdateWithParents = new HashSet<>();
					toUpdateWithParents.addAll(Arrays.asList(toUpdate));
					for (PHPUnitElement element : toUpdate) {
						if (element != null) {
							PHPUnitElement parent = element.getParent();
							while (parent != null) {
								toUpdateWithParents.add(parent);
								parent = parent.getParent();
							}
						}
					}
					fTreeViewer.update(toUpdateWithParents.toArray(), null);
				}
		}
		autoScrollInUI();
	}

	public synchronized void registerActiveSession(final PHPUnitTestGroup testRoot) {
		if (this.testRoot != testRoot) {
			this.testRoot = testRoot;
			registerAutoScrollTarget(null);
			registerViewersRefresh();
		}
	}

	public void registerAutoScrollTarget(final PHPUnitTestCase TestCase) {
		fAutoScrollTarget = TestCase;
	}

	public synchronized void registerFailedForAutoScroll(final PHPUnitElement testRunInfo) {
		final Object parent = fTreeContentProvider.getParent(testRunInfo);
		if (parent != null) {
			fAutoExpand.add(parent);
		}
	}

	public synchronized void registerTestAdded() {
		fTreeNeedsRefresh = true;
	}

	public synchronized void registerViewersRefresh() {
		fTreeNeedsRefresh = true;
		clearUpdateAndExpansion();
	}

	public synchronized void registerViewerUpdate(final PHPUnitElement test) {
		if (test == null) {
			return;
		}
		fNeedUpdate.add(test);
	}

	public void selectFailure(final boolean showNext) {
		final IStructuredSelection selection = (IStructuredSelection) getActiveViewer().getSelection();
		final PHPUnitElement selected = (PHPUnitElement) selection.getFirstElement();
		PHPUnitElement next;

		if (selected == null) {
			next = getNextChildFailure(testRoot, showNext);
		} else {
			next = getNextFailure(selected, showNext);
		}

		if (next != null) {
			getActiveViewer().setSelection(new StructuredSelection(next), true);
		}
	}

	public void selectFirstFailure() {
		final PHPUnitTestCase firstFailure = getNextChildFailure(testRoot, true);
		if (firstFailure != null)
			getActiveViewer().setSelection(new StructuredSelection(firstFailure), true);
	}

	private void setActiveViewerHasFilter(final boolean filter) {
		fTreeHasFilter = filter;
	}

	private void setActiveViewerRefreshed() {
		fTreeNeedsRefresh = false;
	}

	public synchronized void setShowFailuresOnly(final boolean failuresOnly) {
		try {
			fViewerbook.setRedraw(false);

			// avoid realizing all TableItems, especially in flat mode!
			final StructuredViewer viewer = getActiveViewer();
			if (failuresOnly) {
				if (!getActiveViewerHasFilter()) {
					setActiveViewerHasFilter(true);
					if (getActiveViewerNeedsRefresh())
						viewer.setInput(null);
					viewer.addFilter(fFailuresOnlyFilter);
				}

			} else if (getActiveViewerHasFilter()) {
				setActiveViewerHasFilter(false);
				if (getActiveViewerNeedsRefresh())
					viewer.setInput(null);
				viewer.removeFilter(fFailuresOnlyFilter);
			}
			processChangesInUI();
		} finally {
			fViewerbook.setRedraw(true);
		}
	}

	private void updateElementInTree(final PHPUnitElement test) {
		if (isShown(test))
			updateShownElementInTree(test);
		else {
			PHPUnitElement current = test;
			do {
				if (fTreeViewer.testFindItem(current) != null)
					fTreeViewer.remove(current);
				current = current.getParent();
			} while (!(current instanceof PHPUnitTestGroup) && !isShown(current));

			while (current != null && !(current == testRoot)) {
				fTreeViewer.update(current, null);
				current = current.getParent();
			}
		}
	}

	private void updateShownElementInTree(final PHPUnitElement test) {
		if (test == null || test == testRoot) { // paranoia null check
			return;
		}

		final PHPUnitTestGroup parent = (PHPUnitTestGroup) test.getParent();
		if (parent == null) {
			return;
		}
		updateShownElementInTree(parent); // make sure parent is shown and
		// up-to-date

		if (fTreeViewer.testFindItem(test) == null)
			fTreeViewer.add(parent, test); // if not yet in tree: add
		else
			fTreeViewer.update(test, null); // if in tree: update
	}
}
