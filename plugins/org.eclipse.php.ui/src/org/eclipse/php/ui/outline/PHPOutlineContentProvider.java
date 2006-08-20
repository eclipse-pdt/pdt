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
package org.eclipse.php.ui.outline;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.php.core.documentModel.PHPEditorModel;
import org.eclipse.php.core.documentModel.dom.PHPElementImpl;
import org.eclipse.php.core.phpModel.parser.PHPWorkspaceModelManager;
import org.eclipse.php.core.phpModel.phpElementData.PHPClassData;
import org.eclipse.php.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.php.core.phpModel.phpElementData.PHPFunctionData;
import org.eclipse.php.core.phpModel.phpElementData.UserData;
import org.eclipse.php.ui.PHPUiPlugin;
import org.eclipse.php.ui.StandardPHPElementContentProvider;
import org.eclipse.php.ui.treecontent.PHPTreeNode;
import org.eclipse.php.ui.util.PHPPluginImages;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.wst.sse.core.internal.provisional.INodeNotifier;
import org.eclipse.wst.sse.ui.internal.contentoutline.IJFaceNodeAdapter;
import org.eclipse.wst.xml.ui.internal.contentoutline.JFaceNodeContentProvider;

public class PHPOutlineContentProvider extends JFaceNodeContentProvider {

	static class GroupNode {
		Object[] children;
		PHPFileData fileData;
		String text;
		int type;

		GroupNode(final int type, final String text, final PHPFileData fileData) {
			this.type = type;
			this.text = text;
			this.fileData = fileData;
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
					if (fileData != null)
						children = fileData.getIncludeFiles();
					else
						children = new Object[0];
					break;

				default:
					break;
			}
		}
	}
	// this class intension is to get the selection event from the editor and then make sure the 
	// selected elements have the IJFaceNodeAdapter as adapter - this adapter is responsible to refresh the outlineView 
	private class PostSelectionServiceListener implements ISelectionListener {

		public void selectionChanged(final IWorkbenchPart part, final ISelection selection) {
			if (selection instanceof IStructuredSelection) {
				final IStructuredSelection structuredSelection = (IStructuredSelection) selection;
				for (final Iterator iter = structuredSelection.iterator(); iter.hasNext();) {
					final Object next = iter.next();
					if (next instanceof INodeNotifier) {
						final INodeNotifier node = (INodeNotifier) next;
						node.getAdapterFor(IJFaceNodeAdapter.class);
					}
				}
			}
		}

	}

	private static final Image CLASSES_GROUP_IMAGE = PHPPluginImages.DESC_OBJ_PHP_CLASSES_GROUP.createImage();

	private static final Image CONSTANTS_GROUP_IMAGE = PHPPluginImages.DESC_OBJ_PHP_CONSTANTS_GROUP.createImage();
	private static final Image FUNCTIONS_GROUP_IMAGE = PHPPluginImages.DESC_OBJ_PHP_FUNCTIONS_GROUP.createImage();
	public static final int GROUP_CLASSES = 1;
	public static final int GROUP_CONSTANTS = 2;

	public static final int GROUP_FUNCTIONS = 3;
	public static final int GROUP_INCLUDES = 4;
	private static final Image INCLUDES_GROUP_IMAGE = PHPPluginImages.DESC_OBJS_INCLUDE.createImage();
	public static final int MODE_HTML = 2;

	public static final int MODE_MIXED = 3;

	public static final int MODE_PHP = 1;
	private ISelectionListener fSelectionListener = null;

	int mode;

	StandardPHPElementContentProvider phpContentProvider = new StandardPHPElementContentProvider(true);

	boolean showGroups = false;

	public PHPOutlineContentProvider() {
		super();
		mode = PHPUiPlugin.getDefault().getPreferenceStore().getInt(ChangeOutlineModeAction.PREF_OUTLINEMODE);
		if (mode == 0) {
			mode = MODE_PHP;
			PHPUiPlugin.getDefault().getPreferenceStore().setValue(ChangeOutlineModeAction.PREF_OUTLINEMODE, mode);

		}

		PHPUiPlugin.getActiveWorkbenchWindow().getSelectionService().addPostSelectionListener(getSelectionServiceListener());

	}

	public Object[] getChildren(final Object object) {
		if (object instanceof PHPElementImpl && mode == MODE_MIXED) {
			final ArrayList list = getPHPChildren((PHPElementImpl) object);
			return list.toArray();
		} else if (object instanceof PHPCodeData)
			return phpContentProvider.getChildren(object);
		else if (object instanceof PHPEditorModel && mode == MODE_PHP) {
			final PHPEditorModel editorModel = (PHPEditorModel) object;
			final PHPFileData fileData = editorModel.getFileData();
			if (showGroups) {
				final Object[] nodes = { new GroupNode(GROUP_CLASSES, "classes", fileData), new GroupNode(GROUP_FUNCTIONS, "functions", fileData), new GroupNode(GROUP_CONSTANTS, "constants", fileData), new GroupNode(GROUP_INCLUDES, "include files", fileData) };
				return nodes;
			}
			final Object[] providerChildren = phpContentProvider.getChildren(fileData);
			final Object[] children = new Object[providerChildren.length + 1];
			System.arraycopy(providerChildren, 0, children, 1, providerChildren.length);
			children[0] = new GroupNode(GROUP_INCLUDES, "include files", fileData);
			return children;
		} else if (object instanceof GroupNode)
			return ((GroupNode) object).getChildren();
		else if (object instanceof PHPTreeNode)
			return ((PHPTreeNode) object).getChildren();
		return super.getChildren(object);
	}

	public Object[] getElements(final Object object) {
		if (object instanceof PHPElementImpl && mode == MODE_MIXED) {
			final ArrayList list = getPHPChildren((PHPElementImpl) object);
			return list.toArray();
		} else if (object instanceof PHPCodeData)
			return phpContentProvider.getElements(object);
		else if (object instanceof PHPEditorModel && mode == MODE_PHP) {
			final PHPEditorModel editorModel = (PHPEditorModel) object;
			editorModel.getDocument().getAdapterFor(IJFaceNodeAdapter.class);
			final PHPFileData fileData = editorModel.getFileData();
			if (showGroups) {
				final Object[] nodes = { new GroupNode(GROUP_CLASSES, "classes", fileData), new GroupNode(GROUP_FUNCTIONS, "functions", fileData), new GroupNode(GROUP_CONSTANTS, "constants", fileData), new GroupNode(GROUP_INCLUDES, "include files", fileData) };
				return nodes;

			}
			final Object[] providerChildren = phpContentProvider.getElements(fileData);
			final Object[] children = new Object[providerChildren.length + 1];
			System.arraycopy(providerChildren, 0, children, 1, providerChildren.length);
			children[0] = new GroupNode(GROUP_INCLUDES, "include files", fileData);
			return children;

		} else if (object instanceof GroupNode)
			return ((GroupNode) object).getChildren();

		return super.getElements(object);
	}

	public int getMode() {
		return mode;
	}

	public Object getParent(final Object object) {

		if (object instanceof PHPCodeData) {
			final PHPCodeData codeData = (PHPCodeData) object;
			return codeData.getContainer();
		}
		return super.getParent(object);
	}

	private ArrayList getPHPChildren(final PHPElementImpl phpElement) {
		final String location = phpElement.getModel().getBaseLocation();
		final int start = phpElement.getStartOffset();
		final int end = phpElement.getEndOffset();

		final PHPFileData fileData = PHPWorkspaceModelManager.getInstance().getModelForFile(location);
		final ArrayList list = getPHPChildren(fileData, start, end);
		return list;
	}

	private ArrayList getPHPChildren(final PHPFileData fileData, final int start, final int end) {
		final ArrayList list = new ArrayList();

		if (fileData == null)
			return list;
		final PHPClassData[] classes = fileData.getClasses();
		if (classes != null)
			for (int i = 0; i < classes.length; i++)
				if (isInside(start, end, classes[i]))
					list.add(classes[i]);

		final PHPFunctionData[] functions = fileData.getFunctions();
		if (functions != null)
			for (int i = 0; i < functions.length; i++)
				if (isInside(start, end, functions[i]))
					list.add(functions[i]);
		return list;
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
		if (object instanceof PHPElementImpl) {
			final ArrayList list = getPHPChildren((PHPElementImpl) object);
			return list.size() > 0;
		} else if (object instanceof PHPCodeData)
			return phpContentProvider.hasChildren(object);
		else if (object instanceof GroupNode)
			return ((GroupNode) object).hasChildren();
		else if (object instanceof PHPTreeNode)
			return ((PHPTreeNode) object).hasChildren();

		return super.hasChildren(object);
	}

	boolean isInside(final int start, final int end, final PHPCodeData codeData) {
		final UserData userData = codeData.getUserData();
		if (userData == null)
			return false;
		if (start <= userData.getStartPosition() && end >= userData.getEndPosition())
			return true;
		return false;
	}

	public void setMode(final int mode) {
		this.mode = mode;
	}

	public void setShowGroups(final boolean show) {
		if (show != showGroups) {

		}
		showGroups = show;
	}
}
