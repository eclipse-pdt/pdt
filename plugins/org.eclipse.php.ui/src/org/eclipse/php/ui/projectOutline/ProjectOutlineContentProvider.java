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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.php.core.documentModel.IWorkspaceModelListener;
import org.eclipse.php.core.phpModel.PHPModelUtil;
import org.eclipse.php.core.phpModel.parser.ModelListener;
import org.eclipse.php.core.phpModel.parser.PHPProjectModel;
import org.eclipse.php.core.phpModel.parser.PHPWorkspaceModelManager;
import org.eclipse.php.core.phpModel.phpElementData.PHPClassData;
import org.eclipse.php.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.core.phpModel.phpElementData.PHPConstantData;
import org.eclipse.php.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.php.core.phpModel.phpElementData.PHPFunctionData;
import org.eclipse.php.ui.StandardPHPElementContentProvider;
import org.eclipse.php.ui.explorer.PHPTreeViewer;
import org.eclipse.swt.widgets.Control;

public class ProjectOutlineContentProvider extends StandardPHPElementContentProvider implements ModelListener, IWorkspaceModelListener {
	public static class OutlineNode implements Comparable {
		HashMap children;
		PHPProjectModel model;
		ProjectOutlinePart part;
		String text;
		int type;
		Boolean b = new Boolean(true);

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
			return children.keySet().toArray();
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
			return children.size() > 0;
		}

		PHPCodeData[] addChildren(PHPFileData newData) {
			PHPCodeData[] newChildren = new PHPCodeData[0];
			if (newData != null) {
				switch (type) {
					case CLASSES:
						newChildren = newData.getClasses();
						break;
					case FUNCTIONS:
						newChildren = newData.getFunctions();
						break;
					case CONSTANTS:
						newChildren = newData.getConstants();
						break;
				}
			}
			if (children == null) {
				children = new HashMap(newChildren.length);
			}
			for (int i = 0; i < newChildren.length; ++i) {
				children.put(newChildren[i], b);
			}

			return newChildren;

		}

		PHPCodeData[] removeChildren(PHPFileData oldData) {
			PHPCodeData[] oldChildren = new PHPCodeData[0];
			if (oldData != null) {
				switch (type) {
					case CLASSES:
						oldChildren = oldData.getClasses();
						break;
					case FUNCTIONS:
						oldChildren = oldData.getFunctions();
						break;
					case CONSTANTS:
						oldChildren = oldData.getConstants();
						break;
				}
			}
			for (int i = 0; i < oldChildren.length; ++i) {
				children.remove(oldChildren[i]);
			}

			return oldChildren;

		}

		void loadChildren() {
			if (model == null)
				return;
			children = new HashMap(1);
			Object[] aChildren = new Object[0];
			switch (type) {
				case CLASSES:
					if (part.isShowAll())
						aChildren = model.getClasses();
					else
						aChildren = model.getPHPUserModel().getClasses();
					break;

				case FUNCTIONS:
					if (part.isShowAll())
						aChildren = model.getFunctions();
					else
						aChildren = model.getPHPUserModel().getFunctions();
					break;

				case CONSTANTS:
					if (part.isShowAll())
						aChildren = model.getConstants();
					else
						aChildren = model.getPHPUserModel().getConstants();
					break;

			}
			for (int i = 0; i < aChildren.length; ++i) {
				children.put(aChildren[i], b);
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
		if (timer != null)
			timer.cancel();
		super.dispose();
	}

	private ProjectOutlinePart fPart;
	IProject fStoredProject;

	private PHPTreeViewer fViewer;

	OutlineNode[] groupNodes;

	public ProjectOutlineContentProvider(final ProjectOutlinePart part, final boolean provideMembers) {
		fPart = part;
		fViewer = part.getViewer();

	}

	public void dataCleared() {
	}

	public void fileDataAdded(final PHPFileData fileData) {
		postAdd(fileData);
	}

	public void fileDataChanged(final PHPFileData fileData) {
		postRefresh(fileData, true);
	}

	public void fileDataRemoved(final PHPFileData fileData) {
		postRemove(fileData);

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
		fViewer = (PHPTreeViewer) viewer;
		if (oldInput == null && newInput != null) {
			PHPWorkspaceModelManager.getInstance().addWorkspaceModelListener(this);
			PHPWorkspaceModelManager.getInstance().addModelListener(this);
		} else if (oldInput != null && newInput == null) {
			PHPWorkspaceModelManager.getInstance().removeWorkspaceModelListener(this);
			PHPWorkspaceModelManager.getInstance().removeModelListener(this);
		}
		postRefresh(newInput, true);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.ui.StandardPHPElementContentProvider#internalGetParent(java.lang.Object)
	 */
	protected Object internalGetParent(final Object element) {
		// TODO Auto-generated method stub
		final Object parent = super.internalGetParent(element);
		if (parent == fViewer.getInput() || parent instanceof PHPFileData) {
			if (groupNodes == null) {
				groupNodes = getOutlineChildren(fStoredProject);
			}
			for (int i = 0; i < groupNodes.length; ++i) {
				if (groupNodes[i].getType() == CLASSES && element instanceof PHPClassData) {
					return groupNodes[i];
				} else if (groupNodes[i].getType() == FUNCTIONS && element instanceof PHPFunctionData) {
					return groupNodes[i];
				} else if (groupNodes[i].getType() == CONSTANTS && element instanceof PHPConstantData) {
					return groupNodes[i];
				}
			}
		}
		return parent;
	}

	private void postAdd(final PHPFileData fileData) {
		if (fViewer == null || fViewer.getControl() == null)
			return;

		Runnable runnable = new Runnable() {
			public void run() {
				if (fViewer == null)
					return;

				Control control = fViewer.getControl();
				if (control == null || control.isDisposed() || !control.isVisible())
					return;

				IResource res = PHPModelUtil.getResource(fileData);
				if (res == null)
					return;

				if (res.getProject() != fStoredProject)
					return;

				PHPProjectModel model = PHPWorkspaceModelManager.getInstance().getModelForProject(res.getProject());
				OutlineNode outlineNode;
				Object[] toUpdate;
				for (int i = 0; i < groupNodes.length; i++) {
					outlineNode = groupNodes[i];
					if (model != outlineNode.getModel())
						outlineNode.setModel(model);
					toUpdate = outlineNode.addChildren(fileData);
					fViewer.add(outlineNode, toUpdate);
				}
			}
		};
		fViewer.getControl().getDisplay().asyncExec(runnable);
	}

	private void postRemove(final PHPFileData fileData) {
		if (fViewer == null || fViewer.getControl() == null)
			return;

		Runnable runnable = new Runnable() {
			public void run() {
				if (fViewer == null)
					return;

				Control control = fViewer.getControl();
				if (control == null || control.isDisposed() || !control.isVisible())
					return;

				IResource res = PHPModelUtil.getResource(fileData);
				if (res == null)
					return;

				if (res.getProject() != fStoredProject)
					return;

				PHPProjectModel model = PHPWorkspaceModelManager.getInstance().getModelForProject(res.getProject());
				OutlineNode outlineNode;
				Object[] toUpdate;
				for (int i = 0; i < groupNodes.length; i++) {
					outlineNode = groupNodes[i];
					if (model != outlineNode.getModel())
						outlineNode.setModel(model);
					toUpdate = outlineNode.removeChildren(fileData);
					fViewer.remove(outlineNode, toUpdate);
				}
			}
		};
		fViewer.getControl().getDisplay().asyncExec(runnable);
	}

	PHPFileData fStoredFileData;
	int inProgress = 0;
	LinkedList toAdd = new LinkedList();
	LinkedList toRemove = new LinkedList();
	Timer timer;

	public void postRefresh(final Object root, final boolean updateLabels) {
		if (fViewer == null || fViewer.getControl() == null)
			return;
		final Runnable runnable = new Runnable() {
			public void run() {
				if (fViewer == null) {
					//					--inProgress;
					return;
				}
				Control control = fViewer.getControl();
				if (control == null || control.isDisposed() || !control.isVisible()) {
					//					--inProgress;
					return;
				}

				IResource res = PHPModelUtil.getResource(root);
				if (res == null) {
					//					--inProgress;
					return;
				}

				if (res.getProject() != fStoredProject) {
					//					--inProgress;
					return;
				}

				PHPProjectModel model = null;
				model = PHPWorkspaceModelManager.getInstance().getModelForProject(res.getProject());
				OutlineNode outlineNode;
				//				if (inProgress < 2) {
				for (int i = 0; i < groupNodes.length; i++) {
					outlineNode = groupNodes[i];
					if (model != outlineNode.getModel())
						outlineNode.setModel(model);
					outlineNode.loadChildren();
					fViewer.refresh(outlineNode, true);
					fViewer.setSelection(fViewer.getStoredSelection(), true);
				}
				//				}
				//				}
				//				--inProgress;
			}
		};
		//		if (inProgress < 2) {
		//			++inProgress;
		if (timer != null) {
			timer.cancel();
		}
		timer = new Timer(true);
		timer.schedule(new TimerTask() {
			public void run() {
				fViewer.getControl().getDisplay().asyncExec(runnable);
			}
		}, 1000);
		//		}
	}

	public void projectModelAdded(final IProject project) {
		if (fPart.isInCurrentProject(project))
			postRefresh(project, true);
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
