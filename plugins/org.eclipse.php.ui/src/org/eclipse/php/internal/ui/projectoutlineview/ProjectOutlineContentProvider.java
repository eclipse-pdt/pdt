/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.ui.projectoutlineview;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.internal.core.Model;
import org.eclipse.dltk.internal.ui.navigator.ScriptExplorerContentProvider;
import org.eclipse.dltk.internal.ui.workingsets.WorkingSetModel;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IWorkingSet;

/**
 * Content provider for the Project outline view
 * 
 * 
 * @see org.eclipse.jdt.ui.StandardJavaElementContentProvider
 */
public class ProjectOutlineContentProvider extends
		ScriptExplorerContentProvider implements ITreeContentProvider,
		IElementChangedListener, IPropertyChangeListener {

	protected static final int ORIGINAL = 0;
	protected static final int PARENT = 1 << 0;
	protected static final int GRANT_PARENT = 1 << 1;
	protected static final int PROJECT = 1 << 2;

	private TreeViewer fViewer;
	private Object fInput;
	static IScriptProject scripProject = null;

	private Collection fPendingUpdates;

	/**
	 * Creates a new content provider for Java elements.
	 * 
	 * @param provideMembers
	 *            if set, members of compilation units and class files are shown
	 */
	public ProjectOutlineContentProvider(final boolean provideMembers) {
		super(provideMembers);
		fPendingUpdates = null;
	}

	protected Object getViewerInput() {
		return fInput;
	}

	/*
	 * (non-Javadoc) Method declared on IElementChangedListener.
	 */
	public void elementChanged(final ElementChangedEvent event) {
		final ArrayList runnables = new ArrayList();
		try {
			// 58952 delete project does not update Package Explorer [package
			// explorer]
			// if the input to the viewer is deleted then refresh to avoid the
			// display of stale elements
			if (inputDeleted(runnables)) {
				return;
			}

			processDelta(event.getDelta(), runnables);
		} catch (ModelException e) {
			DLTKUIPlugin.log(e);
		} finally {
			executeProjOutlineRunnables(runnables);
		}
	}

	protected final void executeProjOutlineRunnables(final Collection runnables) {

		// now post all collected runnables
		Control ctrl = fViewer.getControl();
		if (ctrl != null && !ctrl.isDisposed()) {
			// Are we in the UIThread? If so spin it until we are done
			if (ctrl.getDisplay().getThread() == Thread.currentThread()) {
				runUpdates(runnables);
			} else {
				synchronized (this) {
					if (fPendingUpdates == null) {
						fPendingUpdates = runnables;
					} else {
						fPendingUpdates.addAll(runnables);
					}
				}
				ctrl.getDisplay().asyncExec(new Runnable() {
					public void run() {
						runPendingUpdates();
					}
				});
			}
		}
	}

	/**
	 * Run all of the runnables that are the widget updates. Must be called in
	 * the display thread.
	 */
	public void runPendingUpdates() {
		Collection pendingUpdates;
		synchronized (this) {
			pendingUpdates = fPendingUpdates;
			fPendingUpdates = null;
		}
		if (pendingUpdates != null && fViewer != null) {
			Control control = fViewer.getControl();
			if (control != null && !control.isDisposed()) {
				runUpdates(pendingUpdates);
			}
		}
	}

	private void runUpdates(final Collection runnables) {
		Iterator runnableIterator = runnables.iterator();
		while (runnableIterator.hasNext()) {
			((Runnable) runnableIterator.next()).run();
		}
	}

	private boolean inputDeleted(final Collection runnables) {
		if (fInput == null) {
			return false;
		}
		if (fInput instanceof IModelElement
				&& ((IModelElement) fInput).exists()) {
			return false;
		}
		if (fInput instanceof IResource && ((IResource) fInput).exists()) {
			return false;
		}
		if (fInput instanceof WorkingSetModel) {
			return false;
		}
		if (fInput instanceof IWorkingSet) {
			// https://bugs.eclipse.org/bugs/show_bug.cgi?id=156239
			return false;
		}
		postRefresh(fInput, ProjectOutlineContentProvider.ORIGINAL, fInput,
				runnables);
		return true;
	}

	public Object[] getChildren(final Object element) {

		if (element instanceof IScriptProject) {
			scripProject = (IScriptProject) element;
			return ProjectOutlineGroups.values();
		}
		if (element instanceof ProjectOutlineGroups)
			return ((ProjectOutlineGroups) element).getChildren();

		return super.getChildren(element);
	}

	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof ProjectOutlineGroups) {
			return true;
		}

		return super.hasChildren(element);
	}

	protected Object internalGetParentGroupNode(final Object element) {
		if (element instanceof IModelElement) {
			IModelElement modelElement = (IModelElement) element;

			if (OutlineUtils.isGlobalClass(modelElement))
				return ProjectOutlineGroups.GROUP_CLASSES;

			if (OutlineUtils.isGlobalFunction(modelElement))
				return ProjectOutlineGroups.GROUP_FUNCTIONS;

			if (OutlineUtils.isConstant(modelElement))
				return ProjectOutlineGroups.GROUP_CONSTANTS;

			return ProjectOutlineGroups.GROUP_NAMESPACES;
		}
		return null;
	}

	/*
	 * (non-Javadoc) Method declared on IContentProvider.
	 */
	public void inputChanged(final Viewer viewer, final Object oldInput,
			Object newInput) {
		super.inputChanged(viewer, oldInput, newInput);
		if (null != newInput && newInput instanceof Model) {
			try {
				IScriptProject[] scriptProjects = ((Model) newInput)
						.getScriptProjects();
				newInput = scriptProjects.length > 0 ? scriptProjects[0]
						: new Object[0];
			} catch (ModelException e) {
				Logger.logException(e);
			}
		}

		fViewer = (TreeViewer) viewer;
		if (fInput == null || !fInput.equals(newInput))
			fInput = newInput;
	}

	// ------ delta processing ------

	/**
	 * Processes a delta recursively. When more than two children are affected
	 * the tree is fully refreshed starting at this node.
	 * 
	 * @param delta
	 *            the delta to process
	 * @param runnables
	 *            the resulting view changes as runnables (type {@link Runnable}
	 *            )
	 * @return true is returned if the conclusion is to refresh a parent of an
	 *         element. In that case no siblings need to be processed
	 * @throws JavaModelException
	 *             thrown when the access to an element failed
	 */
	private boolean processDelta(final IModelElementDelta delta,
			final Collection runnables) throws ModelException {

		int kind = delta.getKind();
		IModelElement element = delta.getElement();
		Object parent = internalGetParentGroupNode(element);

		if (kind == IModelElementDelta.ADDED) {

			// if it is an IOpenable (source module and up) - refresh the view
			IModelElement refreshRoot = (element instanceof IOpenable) ? element
					.getScriptProject()
					: element.getParent();
			if (null == parent) {
				postRefresh(refreshRoot,
						ProjectOutlineContentProvider.ORIGINAL, element,
						runnables);
				return false;
			}

			// adding element if parent is not null
			// (means it should be showed on the project outline view)
			postAdd(parent, element, runnables);

		} else if (kind == IModelElementDelta.REMOVED) {

			if (element instanceof IOpenable) {
				final IPath removedPath = element.getPath();
				fViewer.getControl().getDisplay().asyncExec(new Runnable() {
					public void run() {
						for (TreeItem node : fViewer.getTree().getItems()) {
							// iterating on all 1st level (classes, constants,
							// and
							// functions)
							TreeItem[] treeItems = node.getItems();
							for (TreeItem treeItem : treeItems) {
								// iterating on all 2nd level elements and
								// checking :
								// if item path is prefixed by the removed
								// element -
								// need to remove it.
								IModelElement itemData = (IModelElement) treeItem
										.getData();
								if (itemData != null) {
									if (removedPath.isPrefixOf(itemData
											.getPath())) {
										postRemove((IModelElement) treeItem
												.getData(), runnables);
									}
								}
							}
						}
					}
				});
			} else {
				// if element is not folder/project/SourceModule etc' - just
				// need to remove it from the view
				postRemove(element, runnables);
			}

			return false;
		}
		handleAffectedChildren(delta, element, runnables);
		return false;
	}

	/* package */void handleAffectedChildren(final IModelElementDelta delta,
			final IModelElement element, final Collection runnables)
			throws ModelException {

		IModelElementDelta[] affectedChildren = delta.getAffectedChildren();

		if (affectedChildren.length > 1) {
			int count = 0;
			for (int i = 0; i < affectedChildren.length; i++) {

				// if there is more than
				if (affectedChildren[i].getElement() instanceof IOpenable) {
					count++;
				}
				if (count > 1) {
					postRefresh(fInput, ProjectOutlineContentProvider.ORIGINAL,
							element, runnables);
					return;
				}

			}
		}

		for (int i = 0; i < affectedChildren.length; i++) {

			if (processDelta(affectedChildren[i], runnables)) {
				return; // early return, element got refreshed
			}
		}
	}

}
