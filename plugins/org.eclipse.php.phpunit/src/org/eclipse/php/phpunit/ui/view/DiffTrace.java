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

import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.php.phpunit.model.elements.PHPUnitElement;
import org.eclipse.php.phpunit.model.elements.PHPUnitTestEvent;
import org.eclipse.php.phpunit.model.elements.PHPUnitTestException;
import org.eclipse.php.phpunit.model.elements.PHPUnitTraceFrame;
import org.eclipse.php.phpunit.model.providers.PHPUnitTestDiffTreeContentProvider;
import org.eclipse.php.phpunit.ui.view.actions.OpenEditorAction;
import org.eclipse.php.phpunit.ui.view.actions.OpenEditorAtLineAction;
import org.eclipse.php.phpunit.ui.view.actions.OpenTestAction;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;

/**
 * A pane that shows a object of a failed test.
 */
public class DiffTrace implements IMenuListener {
	private final class CopyListener extends SelectionAdapter implements KeyListener {
		@Override
		public void keyPressed(KeyEvent event) {
			if ((((event.stateMask & SWT.CTRL) == SWT.CTRL) || ((event.stateMask & SWT.COMMAND) == SWT.COMMAND))
					&& (event.keyCode == 'c' || event.keyCode == 'C')) {
				copyPressed();
			}

		}

		@Override
		public void keyReleased(KeyEvent arg0) {
		}
	}

	private PHPUnitElement fInput;
	private PHPUnitView fTestRunner;

	private TreeViewer fTreeViewer;

	public DiffTrace(final Composite parent, final PHPUnitView testRunner) {
		fTreeViewer = new TreeViewer(parent, SWT.SINGLE | SWT.V_SCROLL | SWT.H_SCROLL);
		fTreeViewer.setAutoExpandLevel(AbstractTreeViewer.ALL_LEVELS);
		fTestRunner = testRunner;
		fTreeViewer.setLabelProvider(new DiffLabelProvider());

		final CopyListener copyListener = new CopyListener();
		fTreeViewer.getTree().addKeyListener(copyListener);
		initMenu();

	}

	/**
	 * Returns the composite used to present the diff
	 * 
	 * @return The composite
	 */
	Composite getComposite() {
		return fTreeViewer.getTree();
	}

	public PHPUnitElement getFailedTest() {
		return fInput;
	}

	public PHPUnitElement getTrace() {
		return fInput;
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

			String openLabel = OpenEditorAction.GOTO_FILE;
			if (test instanceof PHPUnitTraceFrame) {
				openLabel = OpenEditorAction.GOTO_CALL;
			} else if (test instanceof PHPUnitTestEvent) {
				openLabel = OpenEditorAction.GOTO_OCCURANCE;
			}
			manager.add(new OpenEditorAtLineAction(openLabel, fTestRunner, fileName, lineNumber, methodName));

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
	 * Refresh the table from the diff.
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
		fTreeViewer.setContentProvider(new PHPUnitTestDiffTreeContentProvider());
		fTreeViewer.setInput(failure);
		fInput = failure;

		fTreeViewer.refresh();
		fTreeViewer.expandAll();
	}

	private void copyPressed() {
		final IContentProvider contentProvider = fTreeViewer.getContentProvider();
		if (contentProvider instanceof PHPUnitTestDiffTreeContentProvider) {
			Clipboard cb = new Clipboard(Display.getDefault());
			TextTransfer textTransfer = TextTransfer.getInstance();
			final String diff = ((PHPUnitTestDiffTreeContentProvider) contentProvider).getDiff();
			cb.setContents(new Object[] { diff }, new Transfer[] { textTransfer });
		}
	}
}
