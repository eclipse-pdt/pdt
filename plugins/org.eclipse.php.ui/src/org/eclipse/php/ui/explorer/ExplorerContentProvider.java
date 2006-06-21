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
package org.eclipse.php.ui.explorer;

import java.util.*;

import org.eclipse.core.resources.*;
import org.eclipse.jface.viewers.IBasicPropertyConstants;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.php.core.phpModel.PHPModelUtil;
import org.eclipse.php.core.phpModel.parser.*;
import org.eclipse.php.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.core.phpModel.phpElementData.PHPDocBlockImp;
import org.eclipse.php.core.phpModel.phpElementData.PHPDocTag;
import org.eclipse.php.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.php.ui.StandardPHPElementContentProvider;
import org.eclipse.swt.widgets.Control;


public class ExplorerContentProvider extends StandardPHPElementContentProvider implements ITreeContentProvider, ModelListener, IResourceChangeListener {

	ExplorerPart fPart;
	private Object fInput;
	TreeViewer fViewer;
	private int fPendingChanges;

	protected static final int ORIGINAL = 0;
	protected static final int PARENT = 1 << 0;
	protected static final int GRANT_PARENT = 1 << 1;
	protected static final int PROJECT = 1 << 2;

	public ExplorerContentProvider(ExplorerPart part, boolean provideMembers) {
		super(provideMembers);
		fPart = part;
		ResourcesPlugin.getWorkspace().addResourceChangeListener(this);
	}

	private boolean inputDeleted() {
		if (fInput == null)
			return false;
		if ((fInput instanceof IResource) && ((IResource) fInput).exists())
			return false;
		postRefresh(fInput);
		return true;
	}

	public void dispose() {
		super.dispose();
		PHPWorkspaceModelManager.getInstance().removeModelListener(this);
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
	}

	private Object[] rootsAndContainers(PHPProjectModel project, Object[] roots) {
		List result = new ArrayList(roots.length);
		Set containers = new HashSet(roots.length);
		Set containedRoots = new HashSet(roots.length);
		return roots;
	}

	/* (non-Javadoc)
	 * Method declared on IContentProvider.
	 */
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		super.inputChanged(viewer, oldInput, newInput);
		fViewer = (TreeViewer) viewer;
		if (oldInput == null && newInput != null) {
			PHPWorkspaceModelManager.getInstance().addModelListener(this);
		} else if (oldInput != null && newInput == null) {
			PHPWorkspaceModelManager.getInstance().removeModelListener(this);
		}
		fInput = newInput;
	}

	private void postRefresh(Object root) {
		// JFace doesn't refresh when object isn't part of the viewer
		// Therefore move the refresh start down to the viewer's input
		if (root instanceof IWorkspaceRoot)
			root=PHPWorkspaceModelManager.getInstance();
		if (isParent(root, fInput))
			root = fInput;
		postRefresh(root, true);
	}

	boolean isParent(Object root, Object child) {
		Object parent = getParent(child);
		if (parent == null)
			return false;
		if (parent.equals(root))
			return true;
		return isParent(root, parent);
	}

	private void postRefresh(final Object root, final boolean updateLabels) {
		postRunnable(new Runnable() {
			public void run() {
				Control ctrl = fViewer.getControl();
				if (ctrl != null && !ctrl.isDisposed()) {
					fViewer.refresh(root, updateLabels);
				}
			}
		});
	}

	private void postRunnable(final Runnable r) {
		Control ctrl = fViewer.getControl();
		final Runnable trackedRunnable = new Runnable() {
			public void run() {
				try {
					r.run();
				} finally {
					removePendingChange();
				}
			}
		};
		if (ctrl != null && !ctrl.isDisposed()) {
			addPendingChange();
			try {
				ctrl.getDisplay().asyncExec(trackedRunnable);
			} catch (RuntimeException e) {
				removePendingChange();
				throw e;
			} catch (Error e) {
				removePendingChange();
				throw e;
			}
		}
	}
	
	public void resourceChanged (IResourceChangeEvent event) {
		IResourceDelta delta = event.getDelta();
		if (delta!=null)
		{
			IResource resource = delta.getResource();
			processResourceDeltas (delta.getAffectedChildren(), resource);
		}
	}

	private boolean processResourceDeltas(IResourceDelta[] deltas, Object parent) {
		if (deltas == null)
			return false;

		if (deltas.length > 1) {
			// more than one child changed, refresh from here downwards
			postRefresh(parent);
			return true;
		}

		for (int i = 0; i < deltas.length; i++) {
			if (processResourceDelta(deltas[i], parent))
				return true;
		}

		return false;
	}

	private boolean processResourceDelta(IResourceDelta delta, Object parent) {
		int status = delta.getKind();
		int flags = delta.getFlags();

		IResource resource = delta.getResource();
		// filter out changes affecting the output folder
		if (resource == null)
			return false;

		// this could be optimized by handling all the added children in the parent
		if ((status & IResourceDelta.REMOVED) != 0) {
			if (parent instanceof IFolder) {
				// refresh one level above to deal with empty folder filtering properly
				postRefresh(internalGetParent(parent));
				return true;
			} else {
				Object removeItem=resource;
				if (resource instanceof IFile)
				{
					PHPFileData fileData=PHPModelUtil.getPHPFile((IFile)resource);
					if (fileData!=null)
						removeItem=fileData;
				}
 			
				postRemove(parent, removeItem);
			}
		}
		if ((status & IResourceDelta.ADDED) != 0) {
			if (parent instanceof IFolder) {
				// refresh one level above to deal with empty folder filtering properly
				postRefresh(internalGetParent(parent));
				return true;
			} else {
				Object addItem=resource;
				// if adding file, convert to php element
				if (resource instanceof IFile && PHPModelUtil.isPhpFile((IFile) resource))
				{
					PHPFileData fileData=PHPWorkspaceModelManager.getInstance().getModelForFile((IFile) resource, false);
					if(fileData == null){
						fileData = PHPCodeDataFactory.createPHPFileData(((IFile) resource).getFullPath().toString(), PHPCodeDataFactory.createUserData(((IFile) resource).getFullPath().toString(), 0, 0, 0, 0), PHPCodeDataFactory.EMPTY_CLASS_DATA_ARRAY, PHPCodeDataFactory.EMPTY_FUNCTIONS_DATA_ARRAY, VariableContextBuilder.createPHPVariablesTypeManager(new HashMap(), new HashMap()), PHPCodeDataFactory.EMPTY_INCLUDE_DATA_ARRAY, PHPCodeDataFactory.EMPTY_CONSTANT_DATA_ARRAY, PHPCodeDataFactory.EMPTY_MARKERS_DATA_ARRAY, PHPCodeDataFactory.EMPTY_PHP_BLOCK_ARRAY, new PHPDocBlockImp("","",new PHPDocTag[0],0), System.currentTimeMillis());
					}
					if (fileData!=null)
						addItem=fileData;
				}
				postAdd(parent, addItem);
			}
		}
		// open/close state change of a project
		if ((flags & IResourceDelta.OPEN) != 0) {
			postProjectStateChanged(internalGetParent(parent));
			return true;
		}
		processResourceDeltas(delta.getAffectedChildren(), resource);
		return false;
	}

	private void postAdd(final Object parent, final Object element) {
		postRunnable(new Runnable() {
			public void run() {
				Control ctrl = fViewer.getControl();
				if (ctrl != null && !ctrl.isDisposed()) {
					if (fViewer.testFindItem(element) == null)
						fViewer.add(parent, element);
				}
			}
		});
	}

	private void postRemove(final Object parent, final Object element) {
		postRunnable(new Runnable() {
			public void run() {
				Control ctrl = fViewer.getControl();
				if (ctrl != null && !ctrl.isDisposed()) {
					fViewer.remove(parent, new Object[] {element});
				}
			}
		});
	}

	private void postProjectStateChanged(final Object root) {
		postRunnable(new Runnable() {
			public void run() {
				fPart.projectStateChanged(root);
			}
		});
	}

	private void postUpdateIcon(final PHPCodeData element) {
		postRunnable(new Runnable() {
			public void run() {
				Control ctrl = fViewer.getControl();
				if (ctrl != null && !ctrl.isDisposed())
					fViewer.update(element, new String[] { IBasicPropertyConstants.P_IMAGE });
			}
		});
	}

	private synchronized void addPendingChange() {
		fPendingChanges++;
		// System.out.print(fPendingChanges);
	}

	synchronized void removePendingChange() {
		fPendingChanges--;
		if (fPendingChanges < 0)
			fPendingChanges = 0;
		// System.out.print(fPendingChanges);
	}

	public synchronized boolean hasPendingChanges() {
		return fPendingChanges > 0;
	}

	public void fileDataChanged(PHPFileData fileData) {
		ArrayList list = new ArrayList();
		list.add(fileData);
		postRefresh(list, true);
	}

	public void fileDataAdded(PHPFileData fileData) {
		// not needed, since this event is notified in <code>resourceChanged</code>
		//postAdd(PHPModelUtil.getParent(fileData), fileData);
	}

	public void fileDataRemoved(PHPFileData fileData) {
		// not needed, since this event is notified in <code>resourceChanged</code>
		//postRemove(fileData);
	}

	public void dataCleared() {
	}

	void postRefresh(final List toRefresh, final boolean updateLabels) {
		postRunnable(new Runnable() {
			public void run() {
				Control ctrl = fViewer.getControl();
				if (ctrl != null && !ctrl.isDisposed()) {
					for (Iterator iter = toRefresh.iterator(); iter.hasNext();) {
						fViewer.refresh(iter.next(), updateLabels);
					}
				}
			}
		});
	}

	protected Object getViewerInput() {
		return fInput;
	}

	public Object[] getChildrenInternal(Object parentElement) {
		if (parentElement instanceof PHPWorkspaceModelManager)
		{
			return getAllProjects();
		}

		return super.getChildrenInternal(parentElement);
	}

	private Object[] getAllProjects() {
		return ResourcesPlugin.getWorkspace().getRoot().getProjects();
	}
}
