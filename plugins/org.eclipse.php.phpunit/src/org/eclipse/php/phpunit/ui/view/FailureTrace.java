/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.phpunit.ui.view;

import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.php.phpunit.PHPUnitPlugin;
import org.eclipse.php.phpunit.PHPUnitPreferenceKeys;
import org.eclipse.php.phpunit.model.elements.PHPUnitElement;
import org.eclipse.php.phpunit.model.elements.PHPUnitTest;
import org.eclipse.php.phpunit.model.elements.PHPUnitTestException;
import org.eclipse.php.phpunit.model.elements.PHPUnitTraceFrame;
import org.eclipse.php.phpunit.model.providers.PHPUnitTestTraceTreeContentProvider;
import org.eclipse.php.phpunit.ui.view.actions.EnableStackFilterAction;
import org.eclipse.php.phpunit.ui.view.actions.OpenEditorAction;
import org.eclipse.php.phpunit.ui.view.actions.OpenEditorAtLineAction;
import org.eclipse.php.phpunit.ui.view.actions.OpenTestAction;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.ToolBar;

/**
 * A pane that shows a stack trace of a failed test.
 */
public class FailureTrace implements IMenuListener {
	private final class TestOpenListener extends SelectionAdapter {
		@Override
		public void widgetDefaultSelected(final SelectionEvent e) {
			handleDefaultSelected();
		}
	}

	private PHPUnitElement fInput;
	private final Image fStackIcon = PHPUnitPlugin.createImage("obj16/stkfrm_obj.png"); //$NON-NLS-1$
	private PHPUnitView fTestRunner;

	private TreeViewer fTreeViewer;

	public FailureTrace(final Composite parent, final PHPUnitView testRunner, final ToolBar toolBar) {
		this.fTestRunner = testRunner;

		// fill the failure trace viewer toolbar
		final ToolBarManager failureToolBarmanager = new ToolBarManager(toolBar);
		failureToolBarmanager.add(new EnableStackFilterAction(this));
		failureToolBarmanager.update(true);

		fTreeViewer = new TreeViewer(parent, SWT.SINGLE | SWT.V_SCROLL | SWT.H_SCROLL);
		fTreeViewer.setAutoExpandLevel(AbstractTreeViewer.ALL_LEVELS);
		fTreeViewer.setLabelProvider(new DelegatingStyledCellLabelProvider(new TestLabelProvider(fTestRunner)));

		final TestOpenListener testOpenListener = new TestOpenListener();
		fTreeViewer.getTree().addSelectionListener(testOpenListener);
		initMenu();

		parent.addDisposeListener(e -> disposeIcons());
	}

	private void disposeIcons() {
		if (fStackIcon != null && !fStackIcon.isDisposed()) {
			fStackIcon.dispose();
		}
	}

	void handleDefaultSelected() {
		final IStructuredSelection selection = (IStructuredSelection) fTreeViewer.getSelection();
		if (selection.size() != 1) {
			return;
		}

		final PHPUnitElement test = (PHPUnitElement) selection.getFirstElement();
		OpenEditorAction action = null;

		if (test instanceof PHPUnitTraceFrame) {
			PHPUnitTraceFrame frame = (PHPUnitTraceFrame) test;
			String methodName = frame.getFunction();
			action = new OpenEditorAtLineAction("", fTestRunner, frame.getFile(), frame.getLine(), methodName); //$NON-NLS-1$
		} else if (test instanceof PHPUnitTestException) {
			PHPUnitTestException te = (PHPUnitTestException) test;
			PHPUnitTest parent = (PHPUnitTest) te.getParent();
			String methodName = parent.getName();
			action = new OpenEditorAtLineAction("", fTestRunner, test.getParent().getFile(), parent.getLine(), //$NON-NLS-1$
					methodName);
		}

		if (action != null && action.isEnabled()) {
			action.run();
		}
	}

	private void initMenu() {
		final MenuManager menuMgr = new MenuManager();
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(this);
		final Menu menu = menuMgr.createContextMenu(fTreeViewer.getTree());
		fTreeViewer.getTree().setMenu(menu);
	}

	@Override
	public void menuAboutToShow(final IMenuManager manager) {
		final IStructuredSelection selection = (IStructuredSelection) fTreeViewer.getSelection();
		if (!selection.isEmpty()) {
			final PHPUnitElement test = (PHPUnitElement) selection.getFirstElement();
			final String fileName = test.getLocalFile();
			final int lineNumber = test.getLine();

			String methodName = null;

			if (test instanceof PHPUnitTraceFrame) {
				methodName = ((PHPUnitTraceFrame) test).getFunction();
			}

			if (test instanceof PHPUnitTestException) {
				manager.add(new OpenTestAction(OpenEditorAction.GOTO_CLASS, fTestRunner,
						((PHPUnitTestException) test).getExceptionClass(), fileName, lineNumber));
			}

			if (test instanceof PHPUnitTraceFrame) {
				final PHPUnitTraceFrame frame = (PHPUnitTraceFrame) test;
				final String className = frame.getClassName();
				if (className != null && !className.equals("")) { //$NON-NLS-1$
					manager.add(new OpenTestAction(OpenEditorAction.GOTO_CLASS, fTestRunner, className, null, 0, null));
					manager.add(new OpenTestAction(OpenEditorAction.GOTO_METHOD, fTestRunner, className, null, 0,
							methodName));
				} else {
					manager.add(
							new OpenTestAction(OpenEditorAction.GOTO_FUNCTION, fTestRunner, null, null, 0, methodName));
				}
			}

		}
	}

	/**
	 * Refresh the table from the the trace.
	 */
	public void refresh() {
		showFailure(fInput);
	}

	/**
	 * Shows a TestFailure
	 * 
	 * @param failure
	 *            the failed test
	 */
	public void showFailure(final PHPUnitElement failure) {
		fTreeViewer.setContentProvider(new PHPUnitTestTraceTreeContentProvider(PHPUnitPreferenceKeys.getFilterStack()));
		fTreeViewer.setInput(failure);
		fInput = failure;

		fTreeViewer.refresh();
	}

	/**
	 * Returns the composite used to present the trace
	 * 
	 * @return The composite
	 */
	Composite getComposite() {
		return fTreeViewer.getTree();
	}
}
