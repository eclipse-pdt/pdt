/*******************************************************************************
 * Copyright (c) 2012, 2016, 2017 PDT Extension Group and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *     Kaloyan Raev - [501269] externalize strings
 *******************************************************************************/
package org.eclipse.php.composer.ui.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.viewers.*;
import org.eclipse.php.composer.api.collection.Scripts;
import org.eclipse.php.composer.api.objects.Script;
import org.eclipse.php.composer.api.objects.Script.HandlerValue;
import org.eclipse.php.composer.ui.ComposerUIPluginImages;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.TreeItem;

public class ScriptsController extends StyledCellLabelProvider implements ITreeContentProvider {

	private Scripts scripts;
	private Image eventImage = ComposerUIPluginImages.EVENT.createImage();
	private Image scriptImage = ComposerUIPluginImages.SCRIPT.createImage();

	private TreeViewer viewer;

	public ScriptsController(TreeViewer viewer) {
		this.viewer = viewer;
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		scripts = (Scripts) newInput;
	}

	public String getText(Object element) {

		if (element instanceof Script) {
			return ((Script) element).getScript();
		}

		if (element instanceof HandlerValue) {
			return ((HandlerValue) element).getAsString();
		}

		return element == null ? "" : element.toString(); //$NON-NLS-1$
	}

	public void update(ViewerCell cell) {
		Object obj = cell.getElement();
		String text = getText(obj);

		StyledString styledString = new StyledString(text);

		if (obj instanceof Script) {
			Script script = (Script) obj;
			styledString.append(" (" + script.size() + ")", StyledString.COUNTER_STYLER); //$NON-NLS-1$ //$NON-NLS-2$
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
			List<Script> children = new ArrayList<Script>();

			for (String event : Scripts.getEvents()) {
				if (scripts.has(event)) {
					children.add(scripts.get(event));
				}
			}

			return children.toArray();
		} else if (parentElement instanceof Script) {
			Script script = (Script) parentElement;
			String event = script.getScript();
			if (Arrays.asList(Scripts.getEvents()).contains(event)) {
				return script.toArray();
			}
		}

		return new Object[] {};
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
