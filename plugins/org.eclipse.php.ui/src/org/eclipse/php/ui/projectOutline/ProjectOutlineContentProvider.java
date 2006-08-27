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
package org.eclipse.php.ui.projectOutline;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.php.core.documentModel.IWorkspaceModelListener;
import org.eclipse.php.core.phpModel.PHPModelUtil;
import org.eclipse.php.core.phpModel.parser.ModelListener;
import org.eclipse.php.core.phpModel.parser.PHPProjectModel;
import org.eclipse.php.core.phpModel.parser.PHPWorkspaceModelManager;
import org.eclipse.php.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.php.ui.StandardPHPElementContentProvider;
import org.eclipse.swt.widgets.Control;

public class ProjectOutlineContentProvider extends StandardPHPElementContentProvider implements ModelListener, IWorkspaceModelListener {
	public static class OutlineNode implements Comparable {
		Object[] children;
		PHPProjectModel model;
		ProjectOutlinePart part;
		String text;
		int type;

		public OutlineNode(final int type, final String text, final PHPProjectModel model, final ProjectOutlinePart part) {
			this.type = type;
			this.text = text;
			this.model = model;
			this.part = part;
		}

		public int compareTo(final Object other) {
			//this optimization is usually worthwhile, and can always be added
			if (this == other)
				return 0;

			// compares the type field
			if (other instanceof OutlineNode) {
				final OutlineNode otherNode = (OutlineNode) other;
				return type - otherNode.type;
			}
			return 0;
		}

		public Object[] getChildren() {
			if (model == null)
				return new Object[0];
			if (children == null)
				loadChildren();
			return children;
		}

		public PHPProjectModel getModel() {
			return model;
		}

		public String getText() {
			return text;
		}

		public int getType() {
			return type;
		}

		public boolean hasChildren() {
			if (model == null)
				return false;
			if (children == null)
				loadChildren();
			return children.length > 0;
		}

		void loadChildren() {
			switch (type) {
				case CLASSES:
					if (part.isShowAll())
						children = model.getClasses();
					else
						children = model.getPHPUserModel().getClasses();
					break;

				case FUNCTIONS:
					if (part.isShowAll())
						children = model.getFunctions();
					else
						children = model.getPHPUserModel().getFunctions();
					break;

				case CONSTANTS:
					if (part.isShowAll())
						children = model.getConstants();
					else
						children = model.getPHPUserModel().getConstants();
					break;

				default:
					break;
			}
		}

		public void resetChildren() {
			children = null;
		}

		public void setModel(final PHPProjectModel model) {
			this.model = model;
		}
	}

	public static final int CLASSES = 1;

	public static final int CONSTANTS = 3;
	public static final int FUNCTIONS = 2;

	/**
	 * Retunrs the node type or -1 if the given object is not an OutlineNode.
	 * 
	 * @param outlineNode
	 * @return One of the types: CLASSES, CONSTANTS or FUNCTIONS
	 * @see #CLASSES
	 * @see #CONSTANTS
	 * @see #FUNCTIONS
	 */
	public static int getNodeType(final Object outlineNode) {
		if (outlineNode instanceof OutlineNode)
			return ((OutlineNode) outlineNode).getType();
		return -1;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.ui.StandardPHPElementContentProvider#dispose()
	 */
	public void dispose() {
		// TODO Auto-generated method stub
		PHPWorkspaceModelManager.getInstance().removeWorkspaceModelListener(this);
		PHPWorkspaceModelManager.getInstance().removeModelListener(this);
		super.dispose();
	}

	private ProjectOutlinePart fPart;
	IProject fStoredProject;

	private TreeViewer fViewer;

	OutlineNode[] groupNodes;

	public ProjectOutlineContentProvider(final ProjectOutlinePart part, final boolean provideMembers) {
		fPart = part;
		fViewer = part.getViewer();

	}

	public void dataCleared() {
	}

	public void fileDataAdded(final PHPFileData fileData) {
		// TODO Auto-generated method stub
		if (fPart.isInCurrentProject(fileData))
			postRefresh(fileData, true);

	}

	public void fileDataChanged(final PHPFileData fileData) {
		if (fPart.isInCurrentProject(fileData))
			postRefresh(fileData, true);
	}

	public void fileDataRemoved(final PHPFileData fileData) {
		// TODO Auto-generated method stub
		if (fPart.isInCurrentProject(fileData))
			postRefresh(fileData, true);

	}

	public Object[] getChildrenInternal(final Object parentElement) {
		if (parentElement instanceof IProject) {
			final IProject project = (IProject) parentElement;
			if (groupNodes == null || project != fStoredProject) {
				groupNodes = getOutlineChildren(project);
				fStoredProject = project;
			}
			return groupNodes;
		} else if (parentElement instanceof OutlineNode) {
			final OutlineNode outlineNode = (OutlineNode) parentElement;
			return outlineNode.getChildren();
		}
		return super.getChildrenInternal(parentElement);
	}

	private OutlineNode[] getOutlineChildren(final IProject project) {
		final PHPProjectModel projectModel = PHPWorkspaceModelManager.getInstance().getModelForProject(project);
		final OutlineNode[] nodes = { new OutlineNode(CLASSES, "classes", projectModel, fPart), new OutlineNode(FUNCTIONS, "functions", projectModel, fPart), new OutlineNode(CONSTANTS, "constants", projectModel, fPart) };
		return nodes;
	}

	public boolean hasChildrenInternal(final Object element) {
		if (element instanceof IProject)
			return true;
		else if (element instanceof OutlineNode) {
			final OutlineNode outlineNode = (OutlineNode) element;
			return outlineNode.hasChildren();
		}
		return super.hasChildrenInternal(element);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.ui.StandardPHPElementContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	public void inputChanged(final Viewer viewer, final Object oldInput, final Object newInput) {
		super.inputChanged(viewer, oldInput, newInput);
		fViewer = (TreeViewer) viewer;
		if (oldInput == null && newInput != null) {
			PHPWorkspaceModelManager.getInstance().addWorkspaceModelListener(this);
			PHPWorkspaceModelManager.getInstance().addModelListener(this);
		} else if (oldInput != null && newInput == null) {
			PHPWorkspaceModelManager.getInstance().removeWorkspaceModelListener(this);
			PHPWorkspaceModelManager.getInstance().removeModelListener(this);
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.ui.StandardPHPElementContentProvider#internalGetParent(java.lang.Object)
	 */
	protected Object internalGetParent(final Object element) {
		// TODO Auto-generated method stub
		final Object parent = super.internalGetParent(element);
		if (parent == fViewer.getInput() || parent instanceof PHPFileData)
			for (int i = 0; i < groupNodes.length; ++i) {
				final Object[] children = groupNodes[i].getChildren();
				for (int j = 0; j < children.length; ++j)
					if (children[j] == element)
						return groupNodes[i];
			}
		return parent;
	}

	private void postRefresh(final Object root, final boolean updateLabels) {
		
		final Runnable runnable = new Runnable() {
			public void run() {
				if(fViewer == null)
					return;
				Control control = fViewer.getControl();
				if(control == null || control.isDisposed() || !control.isVisible())
					return;
				
				IResource res = PHPModelUtil.getResource(root);
				PHPProjectModel model = null;
				if (res != null)
					model = PHPWorkspaceModelManager.getInstance().getModelForProject(res.getProject());
				OutlineNode outlineNode;
				for (int i = 0; i < groupNodes.length; i++) {
					outlineNode = groupNodes[i];
					if (model != outlineNode.getModel())
						outlineNode.setModel(model);
					outlineNode.resetChildren();

					//						ISelection selection = fViewer.getSelection();

					// bug workaround
					//						fViewer.getTree().setRedraw(false);
					//						fViewer.getTree().setRedraw(true);

					fViewer.refresh(groupNodes[i], updateLabels);
					//						fViewer.setSelection(selection);
				}
				//				fViewer.getTree().setRedraw(false);
				//				fViewer.getTree().setRedraw(true);
				fViewer.refresh(updateLabels);
			}
		};
		fViewer.getControl().getDisplay().asyncExec(runnable);
	}

	public void projectModelAdded(final IProject project) {
		if (fPart.isInCurrentProject(project))
			postRefresh(project, true);
		// TODO Auto-generated method stub

	}

	public void projectModelChanged(final IProject project) {
		if (fPart.isInCurrentProject(project))
			postRefresh(project, true);
	}

	public void projectModelRemoved(final IProject project) {
		if (fPart.isInCurrentProject(project))
			postRefresh(project, true);
	}

}
