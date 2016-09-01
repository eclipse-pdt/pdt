/*******************************************************************************
 * Copyright (c) 2012, 2016 PDT Extension Group and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.ui.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.php.composer.ui.ComposerUIPluginImages;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.TreeItem;

import org.eclipse.php.composer.api.objects.Scripts;

public class ScriptsController  extends StyledCellLabelProvider implements ITreeContentProvider {

	private Scripts scripts;
	private Image eventImage = ComposerUIPluginImages.EVENT.createImage();
	private Image scriptImage = ComposerUIPluginImages.SCRIPT.createImage();
	
	private TreeViewer viewer;
	
	public ScriptsController() {
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		viewer = (TreeViewer)viewer;
		scripts = (Scripts)newInput;
	}
	
	public String getText(Object element) {
		return element.toString();
	}
	
	public void update(ViewerCell cell) {
		Object obj = cell.getElement();
		String text = getText(obj);
		
		StyledString styledString = new StyledString(text);
		
		if (Arrays.asList(Scripts.getEvents()).contains(text)) {
			int count = scripts.getAsArray(text).size();
			styledString.append(" (" + count + ")", StyledString.COUNTER_STYLER);
			
			cell.setImage(eventImage);
		} else {
			cell.setImage(scriptImage); 
		}
		
		cell.setText(styledString.toString());
		cell.setStyleRanges(styledString.getStyleRanges());
		
		super.update(cell);
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
	}
	
	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof Scripts) {
			Scripts scripts = (Scripts) parentElement;
			List<String> children = new ArrayList<String>();
			
			for (String event : Scripts.getEvents()) {
				if (scripts.has(event)) {
					children.add(event);
				}
			}
			
			return children.toArray();
		} else {
			String text = parentElement.toString();
			if (Arrays.asList(Scripts.getEvents()).contains(text)) {
				return scripts.getAsArray(text).toArray();
			}
		}
		
		return new Object[]{};
	}

	@Override
	public Object getParent(Object element) {
		TreeItem item = null;
		for (TreeItem ri : viewer.getTree().getItems()) {
			for (TreeItem i : ri.getItems()) {
				if (i.getData() == element) {
					item = i;
					break;
				}
			}
		}
		
		if (item != null) {
			TreeItem parent = item.getParentItem();
			if (parent == null) {
				return scripts;
			}
			
			if (parent.getData() != null) {
				return parent.getData();
			}
			
		}
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		return getChildren(element).length > 0;
	}
}
