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
import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.php.internal.core.PHPToolkitUtil;
import org.eclipse.php.internal.debug.core.zend.communication.IRemoteFileContentRequestor;
import org.eclipse.php.internal.debug.core.zend.debugger.CodeCoverageData;
import org.eclipse.php.internal.debug.core.zend.debugger.RemoteDebugger;
import org.eclipse.php.internal.debug.ui.Logger;
import org.eclipse.php.internal.debug.ui.editor.OpenRemoteFileContentRequestor;
import org.eclipse.php.internal.ui.util.EditorUtility;
import org.eclipse.ui.*;
import org.eclipse.ui.actions.ActionGroup;

/**
 * Code coverage action group.
 */
public class CodeCoverageActionGroup extends ActionGroup implements IMenuListener {

	private static final String COVERAGE_VIEW_ID = "org.eclipse.php.debug.ui.CodeCoverageView"; //$NON-NLS-1$

	private OpenCoverageAction openCoverageAction;

	private CodeCoverageViewer viewer;

	public CodeCoverageActionGroup(final CodeCoverageViewer viewer) {
		this.viewer = viewer;
		openCoverageAction = new OpenCoverageAction(this);
		createContextMenu();
	}

	private void createContextMenu() {
		final MenuManager menuManager = new MenuManager("#PopupMenu"); //$NON-NLS-1$
		menuManager.setRemoveAllWhenShown(true);
		menuManager.addMenuListener(this);
		menuManager.createContextMenu(viewer.getTree());
		viewer.getTree().setMenu(menuManager.createContextMenu(viewer.getTree()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.actions.ActionGroup#dispose()
	 */
	public void dispose() {
		super.dispose();
	}

	public void doubleClickFile(final Object element) {
		try {
			if (element instanceof String) {
				String remoteFile = (String) element;
				final CodeCoverageData data = ((CodeCoverageContentProvider) viewer.getContentProvider())
						.getCoverageData(remoteFile);
				IEditorPart part = EditorUtility.openLocalFile(remoteFile, 0);
				if (part == null) {
					IRemoteFileContentRequestor requestor = new OpenRemoteFileContentRequestor();
					RemoteDebugger.requestRemoteFile(requestor, remoteFile, 1, data.getURL());
				}
			} else if (element instanceof ISourceModule || element instanceof IFile) {
				final IEditorPart part = org.eclipse.dltk.internal.ui.editor.EditorUtility.openInEditor(element, false);
				if (part != null && element instanceof IModelElement && !(element instanceof ISourceModule))
					org.eclipse.dltk.internal.ui.editor.EditorUtility.revealInEditor(part, (IModelElement) element);
			} else
				viewer.setExpandedState(element, !viewer.getExpandedState(element));
		} catch (final CoreException e) {
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.actions.ActionGroup#fillContextMenu(org.eclipse.jface.
	 * action.IMenuManager)
	 */
	public void fillContextMenu(final IMenuManager menu) {
		final ISelection selection = viewer.getSelection();
		if (selection != null && selection instanceof IStructuredSelection) {
			final Object element = ((IStructuredSelection) selection).getFirstElement();
			if (element != null && PHPToolkitUtil.getSourceModule(element) != null) {
				openCoverageAction.updateSelection(selection);
				menu.add(openCoverageAction);
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.action.IMenuListener#menuAboutToShow(org.eclipse.jface
	 * .action.IMenuManager)
	 */
	public void menuAboutToShow(final IMenuManager manager) {
		fillContextMenu(manager);
	}

	public void showCoverage(final CodeCoverageData coverageData) {
		IWorkbench workbench = PlatformUI.getWorkbench();
		if (workbench == null)
			return;
		final IWorkbenchWindow activeWorkbenchWindow = workbench.getActiveWorkbenchWindow();
		if (activeWorkbenchWindow == null)
			return;
		final IWorkbenchPage page = activeWorkbenchWindow.getActivePage();
		if (page == null)
			return;
		try {
			final IViewPart part = page.showView(COVERAGE_VIEW_ID, coverageData.getFileName().replace(':', '_'),
					IWorkbenchPage.VIEW_ACTIVATE);
			if (part != null && part instanceof CodeCoverageView) {
				final CodeCoverageView view = (CodeCoverageView) part;
				view.setInput(coverageData);
			}
		} catch (final PartInitException e) {
			Logger.logException(e);
		}
	}

	public void showCoverage(final Object data) {
		if (data == null)
			return;
		final CodeCoverageContentProvider provider = (CodeCoverageContentProvider) viewer.getContentProvider();
		CodeCoverageData coverageData = null;
		if (data instanceof ISourceModule) {
			coverageData = provider.getCoverageData((ISourceModule) data);
		} else if (data instanceof IFile) {
			coverageData = provider.getCoverageData((IFile) data);
		} else if (data instanceof String) {
			coverageData = provider.getCoverageData((String) data);
		}
		if (coverageData != null)
			showCoverage(coverageData);
	}

}