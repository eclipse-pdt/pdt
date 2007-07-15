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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IBreakpointManager;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.php.internal.core.phpModel.PHPModelUtil;
import org.eclipse.php.internal.core.phpModel.phpElementData.CodeData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.php.internal.ui.explorer.ExplorerPart;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchSite;
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
		if(selection instanceof ITextSelection) {
		setEnabled(true);
			return;
		}
		if(selection.size() != 1) {
			setEnabled(false);
			return;
		}
		Object object = selection.toArray()[0];
		if(object instanceof PHPFileData || object instanceof IResource) {
			IResource res = PHPModelUtil.getResource(object);
			if(res != null && res.exists()) {
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
		if ((first instanceof PHPCodeData) && !(first instanceof PHPFileData))
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
			// Get the breakpoints and bookmarks that were set on this resource before running the 
			// workbench action. Add these markers after the workbench action is processed.
			final ArrayList breakpoints = new ArrayList(6);
			final ArrayList oldAttributes = new ArrayList(6);
			IBreakpointManager breakpointManager = DebugPlugin.getDefault().getBreakpointManager();
			try {
				IMarker[] markers = resource.findMarkers(IBreakpoint.LINE_BREAKPOINT_MARKER, true, IResource.DEPTH_ZERO);
				for (int i = 0; i < markers.length; i++) {
					IBreakpoint breakpoint = breakpointManager.getBreakpoint(markers[i]);
					if (breakpoint != null && !breakpoints.contains(breakpoint)) {
						breakpoints.add(breakpoint);
						oldAttributes.add(breakpoint.getMarker().getAttributes());
					}
				}
			} catch (CoreException e) {
				Logger.logException(e);
			}
			// Call the super implementation
			super.runWithNewPath(path, resource);

			// Add the breakpoints that got removed after the rename action.
			Iterator breakpointsIterator = breakpoints.iterator();
			Iterator attributesIterator = oldAttributes.iterator();
			while (breakpointsIterator.hasNext()) {
				IBreakpoint breakpoint = (IBreakpoint) breakpointsIterator.next();
				Map oldAttributesMap = (Map) attributesIterator.next();
				try {
					IMarker newMarker = ResourcesPlugin.getWorkspace().getRoot().getFile(path).createMarker("org.eclipse.php.debug.core.PHPConditionalBreakpointMarker");
					// Fix the breakpoint's tooltip string before applying the old attributes to the new marker.
					String oldMessge = (String) oldAttributesMap.get(IMarker.MESSAGE);
					if (oldMessge != null) {
						oldAttributesMap.put(IMarker.MESSAGE, oldMessge.replaceFirst(resource.getName(), path.lastSegment()));
					}
					newMarker.setAttributes(oldAttributesMap);
					breakpoint.setMarker(newMarker);
					breakpointManager.addBreakpoint(breakpoint);
				} catch (CoreException e) {
					Logger.logException(e);
				}
			}
		}

	}
}
