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
package org.eclipse.php.astview.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.eclipse.php.astview.ASTViewPlugin;

import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import org.eclipse.jface.action.Action;

import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.texteditor.IWorkbenchActionDefinitionIds;


public class TreeCopyAction extends Action {

	private static class TreeObject {
		private final TreeItem fTreeItem;
		private boolean fSelected;
		private final List fChildren;
		public TreeObject(TreeItem element, boolean selected) {
			fTreeItem= element;
			fSelected= selected;
			fChildren= new ArrayList();
		}
		public void setSelected() {
			fSelected= true;
		}
		public void addChild(TreeObject child) {
			fChildren.add(child);
		}
		public boolean isSelected() {
			return fSelected;
		}
		public TreeItem getTreeItem() {
			return fTreeItem;
		}
		public List getChildren() {
			return fChildren;
		}
		public String toString() {
			StringBuffer buf= new StringBuffer();
			if (fSelected)
				buf.append("* "); //$NON-NLS-1$
			buf.append(trim(fTreeItem.getText())).append(" ["); //$NON-NLS-1$
			for (int i= 0; i < fChildren.size(); i++) {
				TreeObject child= (TreeObject) fChildren.get(i);
				buf.append(trim(child.getTreeItem().getText()));
				if (i > 0)
					buf.append(", "); //$NON-NLS-1$
			}
			return buf.append("]").toString(); //$NON-NLS-1$
		}
		private String trim(String string) {
			if (string.length() > 60)
				return string.substring(0, 60) + "..."; //$NON-NLS-1$
			else
				return string;
		}
	}
	
	private final Tree[] fTrees;
	
	public TreeCopyAction(Tree[] trees) {
		fTrees= trees;
		setText("&Copy"); //$NON-NLS-1$
		setToolTipText("Copy to Clipboard"); //$NON-NLS-1$
		setEnabled(false);
		setImageDescriptor(ASTViewPlugin.getDefault().getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_TOOL_COPY));
		setId(ActionFactory.COPY.getId());
		setActionDefinitionId(IWorkbenchActionDefinitionIds.COPY);
	}

	public void run() {
		Tree tree= null;
		for (int i= 0; i < fTrees.length; i++) {
			if (fTrees[i].isFocusControl()) {
				tree= fTrees[i];
				break;
			}
		}
		if (tree == null)
			return;
		
		TreeItem[] selection= tree.getSelection();
		if (selection.length == 0)
			return;
		
		Clipboard clipboard= null;
		try {
			clipboard= new Clipboard(tree.getDisplay());
			if (selection.length == 1) {
				clipboard.setContents(new Object[]{selection[0].getText()}, new Transfer[]{TextTransfer.getInstance()});
			} else if (selection.length > 1) {
				copyTree(selection, clipboard);
			}
		} finally {
			if (clipboard != null)
				clipboard.dispose();
		}
	}

	private void copyTree(TreeItem[] selection, Clipboard clipboard) {
		HashMap elementToTreeObj= new HashMap();
		List roots= new ArrayList();
		int indent= Integer.MIN_VALUE;
		
		for (int i= 0; i < selection.length; i++) {
			TreeItem item= selection[i];
			TreeObject treeObj= (TreeObject) elementToTreeObj.get(item);
			if (treeObj == null) {
				treeObj= new TreeObject(item, true);
				elementToTreeObj.put(item, treeObj);
			} else {
				treeObj.setSelected();
				continue;
			}
			// walk up to roots:
			int level= 0;
			item= item.getParentItem();
			while (item != null) {
				TreeObject parentTreeObj= (TreeObject) elementToTreeObj.get(item);
				if (parentTreeObj == null) {
					parentTreeObj= new TreeObject(item, false);
					elementToTreeObj.put(item, parentTreeObj);
					parentTreeObj.addChild(treeObj);
					treeObj= parentTreeObj;
					item= item.getParentItem();
					level--;
				} else {
					parentTreeObj.addChild(treeObj);
					treeObj= parentTreeObj;
					break;
				}
			}
			if (item == null) {
				roots.add(treeObj);
				indent= Math.max(indent, level);
			}
		}
		elementToTreeObj= null;
		StringBuffer buf= new StringBuffer();
		appendSelectionObjects(buf, indent, roots);
		String result= buf.length() > 0 ? buf.substring(1) : buf.toString();
		clipboard.setContents(new Object[] { result }, new Transfer[] { TextTransfer.getInstance() });
	}

	private void appendSelectionObjects(StringBuffer buffer, int indent, List selObjs) {
		for (Iterator iter= selObjs.iterator(); iter.hasNext();) {
			TreeObject selObj= (TreeObject) iter.next();
			if (selObj.isSelected()) {
				buffer.append('\n');
				for (int d= 0; d < indent; d++)
					buffer.append('\t');
				buffer.append(selObj.getTreeItem().getText());
			}
			appendSelectionObjects(buffer, indent + 1, selObj.getChildren());
		}
	}
}