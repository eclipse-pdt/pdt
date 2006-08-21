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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.php.core.phpModel.PHPModelUtil;
import org.eclipse.php.core.phpModel.parser.ModelListener;
import org.eclipse.php.core.phpModel.parser.PHPCodeDataFactory;
import org.eclipse.php.core.phpModel.parser.PHPWorkspaceModelManager;
import org.eclipse.php.core.phpModel.parser.VariableContextBuilder;
import org.eclipse.php.core.phpModel.phpElementData.PHPDocBlockImp;
import org.eclipse.php.core.phpModel.phpElementData.PHPDocTag;
import org.eclipse.php.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.php.ui.StandardPHPElementContentProvider;
import org.eclipse.swt.widgets.Control;

public class ExplorerContentProvider extends StandardPHPElementContentProvider implements ITreeContentProvider, ModelListener, IResourceChangeListener {

	protected static final int GRANT_PARENT = 1 << 1;
	protected static final int ORIGINAL = 0;
	protected static final int PARENT = 1 << 0;
	protected static final int PROJECT = 1 << 2;

	private Object fInput;
	ExplorerPart fPart;
	private int fPendingChanges;
	TreeViewer fViewer;

	public ExplorerContentProvider(final ExplorerPart part, final boolean provideMembers) {
		super(provideMembers);
		fPart = part;
		ResourcesPlugin.getWorkspace().addResourceChangeListener(this);
	}

	private synchronized void addPendingChange() {
		fPendingChanges++;
		// System.out.print(fPendingChanges);
	}

	public void dataCleared() {
	}

	public void dispose() {
		super.dispose();
		PHPWorkspaceModelManager.getInstance().removeModelListener(this);
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
	}

	public void fileDataAdded(final PHPFileData fileData) {
		// not needed, since this event is notified in <code>resourceChanged</code>
		//postAdd(PHPModelUtil.getParent(fileData), fileData);
	}

	public void fileDataChanged(final PHPFileData fileData) {
		final ArrayList list = new ArrayList();
		final IResource res = PHPModelUtil.getResource(fileData);
		if (res == null)
			return;
		list.add(res);
		postRefresh(list, true);
	}

	public void fileDataRemoved(final PHPFileData fileData) {
		// not needed, since this event is notified in <code>resourceChanged</code>
		//postRemove(fileData);
	}

	protected Object[] getAllProjects() {
		return ResourcesPlugin.getWorkspace().getRoot().getProjects();
	}

	public Object[] getChildrenInternal(final Object parentElement) {
		if (parentElement instanceof PHPWorkspaceModelManager)
			return getAllProjects();

		return super.getChildrenInternal(parentElement);
	}

	protected Object getViewerInput() {
		return fInput;
	}

	public synchronized boolean hasPendingChanges() {
		return fPendingChanges > 0;
	}

	/* (non-Javadoc)
	 * Method declared on IContentProvider.
	 */
	public void inputChanged(final Viewer viewer, final Object oldInput, final Object newInput) {
		super.inputChanged(viewer, oldInput, newInput);
		fViewer = (TreeViewer) viewer;
		if (oldInput == null && newInput != null)
			PHPWorkspaceModelManager.getInstance().addModelListener(this);
		else if (oldInput != null && newInput == null)
			PHPWorkspaceModelManager.getInstance().removeModelListener(this);
		fInput = newInput;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.ui.StandardPHPElementContentProvider#internalGetParent(java.lang.Object)
	 */
	protected Object internalGetParent(final Object element) {
		final Object parent = super.internalGetParent(element);
		if (parent instanceof PHPFileData) {
			final IResource file = PHPModelUtil.getResource(element);
			if (file != null)
				return file;
		}
		// TODO Auto-generated method stub
		return parent;
	}

	boolean isParent(final Object root, final Object child) {
		final Object parent = getParent(child);
		if (parent == null)
			return false;
		if (parent.equals(root))
			return true;
		return isParent(root, parent);
	}

	private void postAdd(final Object parent, final Object element) {
		postRunnable(new Runnable() {
			public void run() {
				final Control ctrl = fViewer.getControl();
				if (ctrl != null && !ctrl.isDisposed())
					if (fViewer.testFindItem(element) == null)
						fViewer.add(parent, element);
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

	void postRefresh(final List toRefresh, final boolean updateLabels) {
		postRunnable(new Runnable() {
			public void run() {
				final Control ctrl = fViewer.getControl();
				if (ctrl != null && !ctrl.isDisposed())
					for (final Iterator iter = toRefresh.iterator(); iter.hasNext();)
						fViewer.refresh(iter.next(), updateLabels);
			}
		});
	}

	private void postRefresh(Object root) {
		// JFace doesn't refresh when object isn't part of the viewer
		// Therefore move the refresh start down to the viewer's input
		if (root instanceof IWorkspaceRoot)
			root = PHPWorkspaceModelManager.getInstance();
		if (isParent(root, fInput))
			root = fInput;
		postRefresh(root, true);
	}

	private void postRefresh(final Object root, final boolean updateLabels) {
		postRunnable(new Runnable() {
			public void run() {
				final Control ctrl = fViewer.getControl();
				if (ctrl != null && !ctrl.isDisposed())
					fViewer.refresh(root, updateLabels);
			}
		});
	}

	private void postRemove(final Object parent, final Object element) {
		postRunnable(new Runnable() {
			public void run() {
				final Control ctrl = fViewer.getControl();
				if (ctrl != null && !ctrl.isDisposed())
					fViewer.remove(parent, new Object[] { element });
			}
		});
	}

	private void postRunnable(final Runnable r) {
		final Control ctrl = fViewer.getControl();
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
			} catch (final RuntimeException e) {
				removePendingChange();
				throw e;
			} catch (final Error e) {
				removePendingChange();
				throw e;
			}
		}
	}

	private boolean processResourceDelta(final IResourceDelta delta, final Object parent) {
		final int status = delta.getKind();
		final int flags = delta.getFlags();

		final IResource resource = delta.getResource();
		// filter out changes affecting the output folder
		if (resource == null)
			return false;

		// this could be optimized by handling all the added children in the parent
		if ((status & IResourceDelta.REMOVED) != 0) {
			//			look for explenation in the add section
			//			if (parent instanceof IFolder) {
			//				// refresh one level above to deal with empty folder filtering properly
			//				postRefresh(internalGetParent(parent));
			//				return true;
			//			} else {
			Object removeItem = resource;
			if (resource instanceof IFile) {
				final PHPFileData fileData = PHPModelUtil.getPHPFile((IFile) resource);
				if (fileData != null)
					removeItem = fileData;
			}

			postRemove(parent, removeItem);
			//			}
		}
		if ((status & IResourceDelta.ADDED) != 0) {
			//The following commented lines are a workaround for bug #145969 - since this code is used to support a filter we do not support
			//this code is not required
			//			if (parent instanceof IFolder) {
			//				// refresh one level above to deal with empty folder filtering properly
			//				postRefresh(internalGetParent(parent));
			//				return true;
			//			} else {
			Object addItem = resource;
			// if adding file, convert to php element
			if (resource instanceof IFile && PHPModelUtil.isPhpFile((IFile) resource)) {
				PHPFileData fileData = PHPWorkspaceModelManager.getInstance().getModelForFile((IFile) resource, false);
				if (fileData == null)
					fileData = PHPCodeDataFactory.createPHPFileData(((IFile) resource).getFullPath().toString(), PHPCodeDataFactory.createUserData(((IFile) resource).getFullPath().toString(), 0, 0, 0, 0), PHPCodeDataFactory.EMPTY_CLASS_DATA_ARRAY, PHPCodeDataFactory.EMPTY_FUNCTIONS_DATA_ARRAY,
						VariableContextBuilder.createPHPVariablesTypeManager(new HashMap(), new HashMap()), PHPCodeDataFactory.EMPTY_INCLUDE_DATA_ARRAY, PHPCodeDataFactory.EMPTY_CONSTANT_DATA_ARRAY, PHPCodeDataFactory.EMPTY_MARKERS_DATA_ARRAY, PHPCodeDataFactory.EMPTY_PHP_BLOCK_ARRAY,
						new PHPDocBlockImp("", "", new PHPDocTag[0], 0), System.currentTimeMillis());
				if (fileData != null)
					addItem = fileData;
			}
			postAdd(parent, addItem);
			//			}
		}
		// open/close state change of a project
		if ((flags & IResourceDelta.OPEN) != 0) {
			postProjectStateChanged(internalGetParent(parent));
			return true;
		}
		processResourceDeltas(delta.getAffectedChildren(), resource);
		return false;
	}

	private boolean processResourceDeltas(final IResourceDelta[] deltas, Object parent) {
		if (deltas == null)
			return false;

		if (parent instanceof IWorkspaceRoot)
			// it is represnted by the PHPWorkspaceModelManager
			parent = PHPWorkspaceModelManager.getInstance();

		if (deltas.length > 1) {
			// more than one child changed, refresh from here downwards
			postRefresh(parent);
			return true;
		}

		for (int i = 0; i < deltas.length; i++)
			if (processResourceDelta(deltas[i], parent))
				return true;

		return false;
	}

	synchronized void removePendingChange() {
		fPendingChanges--;
		if (fPendingChanges < 0)
			fPendingChanges = 0;
		// System.out.print(fPendingChanges);
	}

	public void resourceChanged(final IResourceChangeEvent event) {
		final IResourceDelta delta = event.getDelta();
		if (delta != null) {
			final IResource resource = delta.getResource();
			processResourceDeltas(delta.getAffectedChildren(), resource);
		}
	}
}
