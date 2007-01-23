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
package org.eclipse.php.internal.ui.explorer;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.util.Assert;
import org.eclipse.jface.util.TransferDragSourceListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.php.internal.core.phpModel.parser.PHPProjectModel;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPClassData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.actions.WorkspaceModifyOperation;


/**
 * Drag support class to allow dragging of files and folder from
 * the explorer view to another application.
 */
class FileTransferDragAdapter extends DragSourceAdapter implements TransferDragSourceListener {

	private ISelectionProvider fProvider;

	FileTransferDragAdapter(ISelectionProvider provider) {
		fProvider = provider;
		Assert.isNotNull(fProvider);
	}

	public Transfer getTransfer() {
		return FileTransfer.getInstance();
	}

	public void dragStart(DragSourceEvent event) {
		event.doit = isDragable(fProvider.getSelection());
	}

	private boolean isDragable(ISelection s) {
		if (!(s instanceof IStructuredSelection))
			return false;
		IStructuredSelection selection = (IStructuredSelection) s;
		for (Iterator iter = selection.iterator(); iter.hasNext();) {
			Object element = iter.next();
			// valid elements are: projects, roots, units and types.
			if (!(element instanceof PHPProjectModel || element instanceof IContainer || element instanceof PHPFileData || element instanceof PHPClassData))
				return false;
		}
		List resources = convertIntoResources(selection);
		return resources.size() == selection.size();
	}

	public void dragSetData(DragSourceEvent event) {
		List elements = getResources();
		if (elements == null || elements.size() == 0) {
			event.data = null;
			return;
		}

		event.data = getResourceLocations(elements);
	}

	private static String[] getResourceLocations(List resources) {

		List result = new ArrayList(resources.size());
		for (int i = 0; i < resources.size(); i++) {
			IResource resource = (IResource) resources.get(i);
			IPath location = resource.getLocation();
			if (location != null)
				result.add(location.toOSString());
		}
		return (String[]) result.toArray(new String[result.size()]);
	}

	public void dragFinished(DragSourceEvent event) {
		if (!event.doit)
			return;

		if (event.detail == DND.DROP_MOVE) {
		} else if (event.detail == DND.DROP_NONE || event.detail == DND.DROP_TARGET_MOVE) {
			handleRefresh(event);
		}
	}

	void handleDropMove(DragSourceEvent event) {
		final List elements = getResources();
		if (elements == null || elements.size() == 0)
			return;

		WorkspaceModifyOperation op = new WorkspaceModifyOperation() {
			public void execute(IProgressMonitor monitor) throws CoreException {
				try {
					monitor.beginTask("Deleting ...", elements.size()); //$NON-NLS-1$
					MultiStatus status = createMultiStatus();
					Iterator iter = elements.iterator();
					while (iter.hasNext()) {
						IResource resource = (IResource) iter.next();
						try {
							monitor.subTask(resource.getFullPath().toOSString());
							resource.delete(true, null);

						} catch (CoreException e) {
							status.add(e.getStatus());
						} finally {
							monitor.worked(1);
						}
					}
					if (!status.isOK()) {
						throw new CoreException(status);
					}
				} finally {
					monitor.done();
				}
			}
		};
		runOperation(op, true, false);
	}

	private void handleRefresh(DragSourceEvent event) {
		final Set roots = collectRoots(getResources());

		WorkspaceModifyOperation op = new WorkspaceModifyOperation() {
			public void execute(IProgressMonitor monitor) throws CoreException {
				try {
					monitor.beginTask("Refreshing...", roots.size()); //$NON-NLS-1$
					MultiStatus status = createMultiStatus();
					Iterator iter = roots.iterator();
					while (iter.hasNext()) {
						IResource r = (IResource) iter.next();
						try {
							r.refreshLocal(IResource.DEPTH_ONE, new SubProgressMonitor(monitor, 1));
						} catch (CoreException e) {
							status.add(e.getStatus());
						}
					}
					if (!status.isOK()) {
						throw new CoreException(status);
					}
				} finally {
					monitor.done();
				}
			}
		};

		runOperation(op, true, false);
	}

	protected Set collectRoots(final List elements) {
		final Set roots = new HashSet(10);

		Iterator iter = elements.iterator();
		while (iter.hasNext()) {
			IResource resource = (IResource) iter.next();
			IResource parent = resource.getParent();
			if (parent == null) {
				roots.add(resource);
			} else {
				roots.add(parent);
			}
		}
		return roots;
	}

	private List getResources() {
		ISelection s = fProvider.getSelection();
		if (!(s instanceof IStructuredSelection))
			return null;

		return convertIntoResources((IStructuredSelection) s);
	}

	private List convertIntoResources(IStructuredSelection selection) {
		List result = new ArrayList(selection.size());
		for (Iterator iter = selection.iterator(); iter.hasNext();) {
			Object o = iter.next();
			IResource r = null;
			if (o instanceof IResource) {
				r = (IResource) o;
			} else if (o instanceof IAdaptable) {
				r = (IResource) ((IAdaptable) o).getAdapter(IResource.class);
			}
			if (r != null)
				result.add(r);
		}
		return result;
	}

	private MultiStatus createMultiStatus() {
		return new MultiStatus(PHPUiPlugin.ID, IStatus.OK, "Problem while moving or copying files.", null); //$NON-NLS-1$
	}

	private void runOperation(IRunnableWithProgress op, boolean fork, boolean cancelable) {
		try {
			Shell parent = PHPUiPlugin.getActiveWorkbenchShell();
			new ProgressMonitorDialog(parent).run(fork, cancelable, op);
		} catch (InvocationTargetException e) {
			String message = "Problem while moving or copying files."; //$NON-NLS-1$
			String title = "Drag & Drop"; //$NON-NLS-1$
			//			ExceptionHandler.handle(e, title, message);
		} catch (InterruptedException e) {
			// Do nothing. Operation has been canceled by user.
		}
	}
}
