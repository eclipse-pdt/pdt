/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.actions;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.AbstractOperation;
import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.core.commands.operations.TriggeredOperations;
import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IBreakpointManager;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.php.internal.core.phpModel.PHPModelUtil;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.php.internal.ui.explorer.ExplorerPart;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.undo.WorkspaceUndoUtil;
import org.eclipse.ui.internal.ViewSite;
import org.eclipse.ui.views.navigator.ResourceNavigatorRenameAction;

public class RenameResourceAction extends SelectionDispatchAction {

	TreeViewer treeViewer;

	public RenameResourceAction(IWorkbenchSite site) {
		super(site);
		if (site instanceof ViewSite) {
			ViewSite viewSite = (ViewSite) site;
			IWorkbenchPart part = viewSite.getPart();
			if (part instanceof ExplorerPart) {
				ExplorerPart explorer = (ExplorerPart) part;
				treeViewer = explorer.getViewer();
			}
		}
	}

	public void selectionChanged(IStructuredSelection selection) {
		if (selection instanceof ITextSelection) {
			setEnabled(true);
			return;
		}
		if (selection.size() != 1) {
			setEnabled(false);
			return;
		}
		Object object = selection.toArray()[0];
		if (object instanceof PHPFileData || object instanceof IResource) {
			IResource res = PHPModelUtil.getResource(object);
			if (res != null && res.exists()) {
				setEnabled(true);
			} else {
				setEnabled(false);
			}
			return;
		}
		setEnabled(false);
	}

	public void run(IStructuredSelection selection) {
		IResource resource = getResource(selection);
		if (!ActionUtils.isProcessable(getShell(), resource))
			return;
		if (!ActionUtils.isRenameAvailable(resource))
			return;
		createWorkbenchAction(selection).run();
	}

	private ResourceNavigatorRenameAction createWorkbenchAction(IStructuredSelection selection) {
		ResourceNavigatorRenameAction action;
		action = new PHPResourceNavigatorRenameAction(getShell(), treeViewer);
		action.selectionChanged(selection);
		return action;
	}

	private static IResource getResource(IStructuredSelection selection) {
		if (selection.size() != 1)
			return null;
		Object first = selection.getFirstElement();
		if (first instanceof PHPCodeData && !(first instanceof PHPFileData))
			return null;
		first = PHPModelUtil.getResource(first);
		if (!(first instanceof IResource))
			return null;
		return (IResource) first;
	}

	/*
	 * A PHP resource rename action that delegates the ResourceNavigatorRenameAction and maintain
	 * any removed PHP conditional breakpoints.
	 */
	private class PHPResourceNavigatorRenameAction extends ResourceNavigatorRenameAction {

		public PHPResourceNavigatorRenameAction(Shell shell, TreeViewer treeViewer) {
			super(shell, treeViewer);
		}

		/* (non-Javadoc)
		 * @see org.eclipse.ui.views.navigator.ResourceNavigatorRenameAction#runWithNewPath(org.eclipse.core.runtime.IPath, org.eclipse.core.resources.IResource)
		 */
		protected void runWithNewPath(IPath path, IResource resource) {
			IUndoableOperation operation = createOperation(path, resource);
			try {
				TriggeredOperations triggeredOperations = new TriggeredOperations(operation, PlatformUI.getWorkbench().getOperationSupport().getOperationHistory());
				PlatformUI.getWorkbench().getOperationSupport().getOperationHistory().execute(triggeredOperations, new NullProgressMonitor(), WorkspaceUndoUtil.getUIInfoAdapter(getShell()));

			} catch (ExecutionException e) {
				if (e.getCause() instanceof CoreException) {
					Logger.logException(e.getCause());
				} else {
					Logger.logException(e);
				}
			}
		}

		protected void doRunWithNewPath(final IPath path, final IResource resource, final Map<Integer, IBreakpoint> breakpoints, final Map<Integer, Map<String, Object>> breakpointAttributes) {
			// Get the breakpoints and bookmarks that were set on this resource before running the
			// workbench action. Add these markers after the workbench action is processed.
			final IBreakpointManager breakpointManager = DebugPlugin.getDefault().getBreakpointManager();
			try {
				synchronized (this) { // this can run simultaneously
					IMarker[] markers = resource.findMarkers(IBreakpoint.LINE_BREAKPOINT_MARKER, true, IResource.DEPTH_ZERO);
					for (IMarker marker : markers) {
						IBreakpoint breakpoint = breakpointManager.getBreakpoint(marker);
						Integer line = (Integer) marker.getAttribute(IMarker.LINE_NUMBER);
						if (breakpoint != null) {
							breakpoints.put(line, breakpoint);
							breakpointAttributes.put(line, breakpoint.getMarker().getAttributes());
						}
					}
				}
			} catch (CoreException e) {
				Logger.logException(e);
			}
			// Call the super implementation
			super.runWithNewPath(path, resource);
			// Add the breakpoints that got removed after the rename action.
			final IWorkspace workspace = ResourcesPlugin.getWorkspace();
			WorkspaceJob createMarker = new WorkspaceJob("Creating markers") {
				@Override
				public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
					synchronized (this) {
						IFile oldFile = workspace.getRoot().getFile(resource.getFullPath());
						if (oldFile.isAccessible()) { // in case the old file exists (fast undo-redo)
							return Status.CANCEL_STATUS;

						}
						IResource file = workspace.getRoot().findMember(path);
						if (file == null) {
							return Status.CANCEL_STATUS;
						}
						if (file.getType() != IResource.FILE) {
							return Status.CANCEL_STATUS;
						}
						for (final Integer line : breakpoints.keySet()) {
							final Map<String, Object> oldAttributesMap = breakpointAttributes.get(line);
							IMarker newMarker = file.createMarker("org.eclipse.php.debug.core.PHPConditionalBreakpointMarker"); //$NON-NLS-1$
							// Fix the breakpoint's tooltip string before applying the old attributes to the new marker.
							String oldMessge = (String) oldAttributesMap.get(IMarker.MESSAGE);
							if (oldMessge != null) {
								oldAttributesMap.put(IMarker.MESSAGE, oldMessge.replaceFirst(resource.getName(), path.lastSegment()));
							}
							newMarker.setAttributes(oldAttributesMap);
							IBreakpoint breakpoint = breakpoints.get(line);
							breakpoint.setMarker(newMarker);
							breakpointManager.addBreakpoint(breakpoint);
						}
					}
					return Status.OK_STATUS;
				}
			};
			createMarker.setRule(workspace.getRoot());
			createMarker.setSystem(true);
			createMarker.schedule(1000); // wait for UI refresh which refreshes the markers
		}

		protected IUndoableOperation createOperation(final IPath path, final IResource resource) {
			IUndoableOperation operation = new AbstractOperation("Advanced rename") { //$NON-NLS-1$
				private IPath oldPath = resource.getFullPath();
				final private Map<Integer, IBreakpoint> breakpoints = new HashMap<Integer, IBreakpoint>(6);
				final private Map<Integer, Map<String, Object>> breakpointAttributes = new HashMap<Integer, Map<String, Object>>(6);

				@Override
				public IStatus execute(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
					doRunWithNewPath(path, resource, breakpoints, breakpointAttributes);
					return Status.OK_STATUS;
				}

				@Override
				public IStatus redo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
					IResource newResource = ResourcesPlugin.getWorkspace().getRoot().findMember(resource.getFullPath());
					selectionChanged(new StructuredSelection(newResource));
					doRunWithNewPath(path, newResource, breakpoints, breakpointAttributes);
					return Status.OK_STATUS;
				}

				@Override
				public IStatus undo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
					IResource newResource = ResourcesPlugin.getWorkspace().getRoot().findMember(path);
					selectionChanged(new StructuredSelection(newResource));
					doRunWithNewPath(oldPath, newResource, breakpoints, breakpointAttributes);
					return Status.OK_STATUS;
				}
			};
			return operation;
		}
	}
}
