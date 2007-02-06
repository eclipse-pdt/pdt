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
package org.eclipse.php.internal.ui.outline;

import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.php.internal.core.documentModel.DOMModelForPHP;
import org.eclipse.php.internal.core.phpModel.parser.ModelListener;
import org.eclipse.php.internal.core.phpModel.parser.PHPWorkspaceModelManager;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.php.internal.core.phpModel.phpElementData.UserData;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.StandardPHPElementContentProvider;
import org.eclipse.php.internal.ui.SuperClassTreeContentProvider;
import org.eclipse.php.internal.ui.treecontent.PHPTreeNode;
import org.eclipse.php.internal.ui.util.PHPPluginImages;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.wst.sse.ui.internal.contentoutline.IJFaceNodeAdapter;
import org.eclipse.wst.xml.ui.internal.contentoutline.JFaceNodeContentProvider;

public class PHPOutlineContentProvider extends JFaceNodeContentProvider implements ModelListener {

	static public class GroupNode implements Comparable {

		Object[] children;
		PHPFileData fileData;
		String text;
		int type;

		GroupNode(final int type, final String text, final PHPFileData fileData) {
			this.type = type;
			this.text = text;
			this.fileData = fileData;
		}

		public int compareTo(final Object other) {
			//this optimization is usually worthwhile, and can always be added
			if (this == other)
				return 0;

			// compares the type field
			if (other instanceof GroupNode) {
				final GroupNode otherNode = (GroupNode) other;
				return type - otherNode.type;
			}
			return 0;
		}

		public Object[] getChildren() {
			if (children == null)
				loadChildren();
			return children;
		}

		public Image getImage() {
			switch (type) {
				case GROUP_CLASSES:
					return CLASSES_GROUP_IMAGE;
				case GROUP_FUNCTIONS:
					return FUNCTIONS_GROUP_IMAGE;
				case GROUP_CONSTANTS:
					return CONSTANTS_GROUP_IMAGE;
				case GROUP_INCLUDES:
					return INCLUDES_GROUP_IMAGE;
			}
			return null;
		}

		public String getText() {
			return text;
		}

		public boolean hasChildren() {
			if (children == null)
				loadChildren();
			return children.length > 0;
		}

		void loadChildren() {
			if (fileData != null)
				switch (type) {
					case GROUP_CLASSES:
						children = fileData.getClasses();
						break;

					case GROUP_FUNCTIONS:
						children = fileData.getFunctions();
						break;

					case GROUP_CONSTANTS:
						children = fileData.getConstants();
						break;

					case GROUP_INCLUDES:
						children = fileData.getIncludeFiles();
						break;
				}
			if (children == null)
				children = new Object[0];
		}

		public void reset(final PHPFileData fileData) {
			this.fileData = fileData;
			children = null;
		}

		public void setFileData(final PHPFileData fileData) {
			this.fileData = fileData;
			loadChildren();
		}
	}

	// this class intension is to get the selection event from the editor and then make sure the 
	// selected elements have the IJFaceNodeAdapter as adapter - this adapter is responsible to refresh the outlineView 
	private class PostSelectionServiceListener implements ISelectionListener {

		public void selectionChanged(final IWorkbenchPart part, final ISelection selection) {
			//			if (selection instanceof IStructuredSelection) {
			//				final IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			//				for (final Iterator iter = structuredSelection.iterator(); iter.hasNext();) {
			//					final Object next = iter.next();
			//					if (next instanceof INodeNotifier) {
			//						final INodeNotifier node = (INodeNotifier) next;
			//						node.getAdapterFor(IJFaceNodeAdapter.class);
			//					}
			//				}
			//			}
		}

	}

	private static final Image CLASSES_GROUP_IMAGE = PHPPluginImages.DESC_OBJ_PHP_CLASSES_GROUP.createImage();

	private static final Image CONSTANTS_GROUP_IMAGE = PHPPluginImages.DESC_OBJ_PHP_CONSTANTS_GROUP.createImage();

	private static final Image FUNCTIONS_GROUP_IMAGE = PHPPluginImages.DESC_OBJ_PHP_FUNCTIONS_GROUP.createImage();

	public static final int GROUP_CLASSES = 3;

	public static final int GROUP_CONSTANTS = 2;

	public static final int GROUP_FUNCTIONS = 4;

	public static final int GROUP_INCLUDES = 1;
	private static final Image INCLUDES_GROUP_IMAGE = PHPPluginImages.DESC_OBJS_INCLUDE.createImage();

	public static final int MODE_HTML = 2;

	public static final int MODE_MIXED = 3;
	public static final int MODE_PHP = 1;
	DOMModelForPHP editorModel;
	private ISelectionListener fSelectionListener = null;
	GroupNode[] groupNodes;
	private PHPOutlineLabelProvider labelProvider;
	int mode;
	StandardPHPElementContentProvider phpContentProvider = new StandardPHPElementContentProvider(true);

	boolean showGroups;

	ITreeContentProvider superClassContentProvider = new SuperClassTreeContentProvider(this);
	TreeViewer viewer;

	public PHPOutlineContentProvider(final TreeViewer viewer, PHPOutlineLabelProvider labelProvider) {
		super();

		this.viewer = viewer;
		this.labelProvider = labelProvider;

		mode = PHPUiPlugin.getDefault().getPreferenceStore().getInt(ChangeOutlineModeAction.PREF_OUTLINEMODE);
		if (mode == 0) {
			mode = MODE_PHP;
			PHPUiPlugin.getDefault().getPreferenceStore().setValue(ChangeOutlineModeAction.PREF_OUTLINEMODE, mode);

		}
		showGroups = PHPUiPlugin.getDefault().getPreferenceStore().getBoolean(ShowGroupsAction.PREF_SHOW_GROUPS);

		PHPUiPlugin.getActiveWorkbenchWindow().getSelectionService().addPostSelectionListener(getSelectionServiceListener());

		registerMouseTrackListener();
	}

	/** (non-Javadoc)
	 * @see org.eclipse.php.internal.core.phpModel.parser.ModelListener#dataCleared()
	 */
	public void dataCleared() {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.wst.xml.ui.internal.contentoutline.JFaceNodeContentProvider#dispose()
	 */
	public void dispose() {
		PHPWorkspaceModelManager.getInstance().removeModelListener(this);
		PHPUiPlugin.getActiveWorkbenchWindow().getSelectionService().removePostSelectionListener(getSelectionServiceListener());
		super.dispose();
	}

	/** (non-Javadoc)
	 * @see org.eclipse.php.internal.core.phpModel.parser.ModelListener#fileDataAdded(org.eclipse.php.internal.core.phpModel.phpElementData.PHPFileData)
	 */
	public void fileDataAdded(final PHPFileData fileData) {
		if (editorModel != null && editorModel.getFileData() != null && editorModel.getFileData().getComparableName().equals(fileData.getComparableName()))
			postRefresh(true);
	}

	/** (non-Javadoc)
	 * @see org.eclipse.php.internal.core.phpModel.parser.ModelListener#fileDataChanged(org.eclipse.php.internal.core.phpModel.phpElementData.PHPFileData)
	 */
	public void fileDataChanged(final PHPFileData fileData) {
		if (editorModel != null && editorModel.getFileData() != null && editorModel.getFileData().getComparableName().equals(fileData.getComparableName()))
			if (groupNodes == null)
				postRefresh(true);
			else
				for (int i = 0; i < groupNodes.length; ++i) {
					groupNodes[i].reset(fileData);
					postRefresh(groupNodes[i], true);
				}
	}

	/** (non-Javadoc)
	 * @see org.eclipse.php.internal.core.phpModel.parser.ModelListener#fileDataRemoved(org.eclipse.php.internal.core.phpModel.phpElementData.PHPFileData)
	 */
	public void fileDataRemoved(final PHPFileData fileData) {
	}

	public Object[] getChildren(final Object object) {
		if (object instanceof PHPCodeData) {
			ArrayList children = new ArrayList(Arrays.asList(phpContentProvider.getChildren(object)));
			children.addAll(Arrays.asList(superClassContentProvider.getChildren(object)));
			return children.toArray();
		} else if (object instanceof DOMModelForPHP && mode == MODE_PHP) {
			editorModel = (DOMModelForPHP) object;
			editorModel.getDocument().getAdapterFor(IJFaceNodeAdapter.class);
			final PHPFileData fileData = editorModel.getFileData();
			if (fileData != null) {
				final GroupNode[] groupNodes = getGroupNodes(fileData);
				if (groupNodes != null)
					return groupNodes;
			}
			return getChildren(fileData);
		} else if (object instanceof GroupNode)
			return ((GroupNode) object).getChildren();
		else if (object instanceof PHPTreeNode)
			return ((PHPTreeNode) object).getChildren();
		return super.getChildren(object);
	}

	public Object[] getElements(final Object object) {
		if (object instanceof PHPCodeData)
			return phpContentProvider.getChildren(object);
		else if (object instanceof DOMModelForPHP && mode == MODE_PHP) {
			editorModel = (DOMModelForPHP) object;
			editorModel.getDocument().getAdapterFor(IJFaceNodeAdapter.class);
			final PHPFileData fileData = editorModel.getFileData();
			final GroupNode[] groupNodes = getGroupNodes(fileData);
			if (groupNodes != null)
				return groupNodes;
			return getElements(fileData);
		}
		return super.getElements(object);
	}

	GroupNode[] getGroupNodes(final PHPFileData fileData) {
		if (showGroups) {
			if (groupNodes != null)
				for (int i = 0; i < groupNodes.length; i++)
					groupNodes[i].setFileData(fileData);
			else
				groupNodes = new GroupNode[] { new GroupNode(GROUP_INCLUDES, "include files", fileData), new GroupNode(GROUP_CONSTANTS, "constants", fileData), new GroupNode(GROUP_CLASSES, "classes", fileData), new GroupNode(GROUP_FUNCTIONS, "functions", fileData) };
		} else
			groupNodes = null;
		return groupNodes;
	}

	public int getMode() {
		return mode;
	}

	public Object getParent(final Object object) {

		if (object instanceof PHPCodeData) {
			Object parent = superClassContentProvider.getParent(object);
			if (parent != null) {
				return parent;
			}
			final PHPCodeData codeData = (PHPCodeData) object;
			final PHPCodeData container = codeData.getContainer();
			if (container instanceof PHPFileData && showGroups) {
				Object[] children;
				for (int i = 0; i < groupNodes.length; ++i) {
					children = groupNodes[i].getChildren();
					for (int j = 0; j < children.length; ++j)
						if (children[j] == object)
							return groupNodes[i];
				}
			} else
				return container;
		}
		return super.getParent(object);
	}

	private ISelectionListener getSelectionServiceListener() {
		if (fSelectionListener == null)
			fSelectionListener = new PostSelectionServiceListener();
		return fSelectionListener;
	}

	public boolean getShowGroups() {
		return showGroups;
	}

	public boolean hasChildren(final Object object) {
		if (object instanceof PHPCodeData) {
			if (superClassContentProvider.hasChildren(object)) {
				return true;
			}
			final Object[] phpChildren = getChildren(object);
			return phpChildren.length > 0;
		} else if (object instanceof GroupNode)
			return ((GroupNode) object).hasChildren();
		else if (object instanceof PHPTreeNode)
			return ((PHPTreeNode) object).hasChildren();

		return super.hasChildren(object);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.wst.xml.ui.internal.contentoutline.JFaceNodeContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	public void inputChanged(final Viewer viewer, final Object oldInput, final Object newInput) {
		super.inputChanged(viewer, oldInput, newInput);
		if (oldInput == null && newInput != null) {
			PHPWorkspaceModelManager.getInstance().addModelListener(this);
			postRefresh(newInput, true);
		} else if (oldInput != null && newInput == null) {
			PHPWorkspaceModelManager.getInstance().removeModelListener(this);
			postRefresh(true);
		}
	}

	boolean isInside(final int start, final int end, final PHPCodeData codeData) {
		final UserData userData = codeData.getUserData();
		if (userData == null)
			return false;
		if (start <= userData.getStartPosition() && end >= userData.getEndPosition())
			return true;
		return false;
	}

	private void postRefresh(final boolean updateLabels) {
		postRefresh(null, updateLabels);
	}

	private void postRefresh(final Object element, final boolean updateLabels) {
		final Runnable runnable = new Runnable() {
			public void run() {
				if (viewer == null)
					return;
				Control control = viewer.getControl();
				if (control == null || control.isDisposed())
					return;

				if (element == null)
					viewer.refresh(updateLabels);
				else
					viewer.refresh(element, updateLabels);
				if (element instanceof DOMModelForPHP) {
					viewer.expandToLevel(2);
				}
			}
		};
		final Control control = viewer.getControl();
		if (control != null && !control.isDisposed())
			viewer.getControl().getDisplay().asyncExec(runnable);
	}

	private void registerMouseTrackListener() {
		final Tree tree = viewer.getTree();
		tree.addMouseTrackListener(new MouseTrackAdapter() {
			public void mouseHover(final MouseEvent e) {
				final TreeItem item = tree.getItem(new Point(e.x, e.y));
				if (item != null) {
					final Object o = item.getData();
					if (o instanceof PHPCodeData)
						tree.setToolTipText(labelProvider.getTooltipText(o));
				}
			}

		});

	}

	public void setMode(final int mode) {
		this.mode = mode;
	}

	public void setShowGroups(final boolean show) {
		showGroups = show;
		postRefresh(true);
	}
}
