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
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.php.core.phpModel.parser.ModelListener;
import org.eclipse.php.core.phpModel.parser.PHPProjectModel;
import org.eclipse.php.core.phpModel.parser.PHPWorkspaceModelManager;
import org.eclipse.php.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.php.ui.StandardPHPElementContentProvider;
import org.eclipse.swt.widgets.Control;

public class ProjectOutlineContentProvider extends StandardPHPElementContentProvider implements ModelListener {
	private ProjectOutlinePart fPart;
	private TreeViewer fViewer;

	public static final int CLASSES = 1;
	public static final int FUNCTIONS = 2;
	public static final int CONSTANTS = 3;

	Object[] groupNodes;

	public static class OutlineNode implements Comparable {
		String text;
		Object[] children;
		PHPProjectModel model;
		int type;
		ProjectOutlinePart part;

		OutlineNode(int type, String text, PHPProjectModel model, ProjectOutlinePart part) {
			this.type = type;
			this.text = text;
			this.model = model;
			this.part = part;
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

		public boolean hasChildren() {
			if (children == null)
				loadChildren();
			return children.length > 0;
		}

		public Object[] getChildren() {
			if (children == null)
				loadChildren();
			return children;
		}

		public String getText() {
			return text;
		}

		public int getType() {
			return type;
		}

		public void resetChildren() {
			children = null;
		}

		public int compareTo(Object other) {
			//this optimization is usually worthwhile, and can always be added
			if (this == other)
				return 0;

			// compares the type field
			if (other instanceof OutlineNode) {
				OutlineNode otherNode = (OutlineNode) other;
				return this.type - otherNode.type;
			}
			return 0;
		}
	}

	public ProjectOutlineContentProvider(ProjectOutlinePart part, boolean provideMembers) {
		fPart = part;
		fViewer = part.getViewer();

	}

	public Object[] getChildrenInternal(Object parentElement) {
		if (parentElement instanceof IProject) {
			groupNodes = getOutlineChildren((IProject) parentElement);
			return groupNodes;
		} else if (parentElement instanceof OutlineNode) {
			OutlineNode outlineNode = (OutlineNode) parentElement;
			return outlineNode.getChildren();
		}
		return super.getChildrenInternal(parentElement);
	}

	private Object[] getOutlineChildren(IProject project) {
		PHPProjectModel projectModel = PHPWorkspaceModelManager.getInstance().getModelForProject(project);
		if (projectModel != null) {
			Object[] nodes = { new OutlineNode(CLASSES, "classes", projectModel, fPart), new OutlineNode(FUNCTIONS, "functions", projectModel, fPart), new OutlineNode(CONSTANTS, "constants", projectModel, fPart) };
			return nodes;
		} else
			return getProjectChildren(project);
	}

	public boolean hasChildrenInternal(Object element) {
		if (element instanceof IProject) {
			return true;
		} else if (element instanceof OutlineNode) {
			OutlineNode outlineNode = (OutlineNode) element;
			return outlineNode.hasChildren();
		}
		return super.hasChildrenInternal(element);
	}

	/**
	 * Retunrs the node type or -1 if the given object is not an OutlineNode.
	 * 
	 * @param outlineNode
	 * @return One of the types: CLASSES, CONSTANTS or FUNCTIONS
	 * @see #CLASSES
	 * @see #CONSTANTS
	 * @see #FUNCTIONS
	 */
	public static int getNodeType(Object outlineNode) {
		if (outlineNode instanceof OutlineNode) {
			return ((OutlineNode) outlineNode).getType();
		}
		return -1;
	}

	public void fileDataChanged(PHPFileData fileData) {

		if (fPart.isInCurrentProject(fileData))
			postRefresh(fileData, true);
	}

	public void fileDataAdded(PHPFileData fileData) {
		// TODO Auto-generated method stub

	}

	public void fileDataRemoved(PHPFileData fileData) {
		// TODO Auto-generated method stub

	}

	public void dataCleared() {
		// TODO Auto-generated method stub

	}

	private void postRefresh(final Object root, final boolean updateLabels) {
		Runnable runnable = new Runnable() {
			public void run() {
				Control ctrl = fViewer.getControl();
				if (ctrl != null && !ctrl.isDisposed()) {
					for (int i = 0; i < groupNodes.length; i++) {
						if (groupNodes[i] instanceof OutlineNode) {
							OutlineNode outlineNode = (OutlineNode) groupNodes[i];
							outlineNode.resetChildren();
						}
						fViewer.refresh(updateLabels);

					}
				}
			}
		};
		fViewer.getControl().getDisplay().asyncExec(runnable);
	}

}
