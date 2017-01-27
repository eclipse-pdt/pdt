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

import org.eclipse.jface.viewers.*;
import org.eclipse.php.composer.api.collection.Psr;
import org.eclipse.php.composer.api.objects.Namespace;
import org.eclipse.php.composer.ui.ComposerUIPluginImages;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.TreeItem;

public class PsrController extends StyledCellLabelProvider implements ITreeContentProvider {

	private Psr psr0;
	private Image namespaceImage = ComposerUIPluginImages.NAMESPACE.createImage();
	private Image pathImage = ComposerUIPluginImages.PACKAGE_FOLDER.createImage();

	private TreeViewer viewer;

	public PsrController(TreeViewer viewer) {
		this.viewer = viewer;
	}

	public String getText(Object element) {

		if (element instanceof Namespace) {
			return ((Namespace) element).getNamespace();
		}

		return element == null ? "" : element.toString(); //$NON-NLS-1$
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		psr0 = (Psr) newInput;
	}

	public void update(ViewerCell cell) {
		Object obj = cell.getElement();
		String text = getText(obj);

		StyledString styledString = new StyledString(text);

		if (obj instanceof Namespace) {
			Namespace namespace = (Namespace) obj;
			styledString.append(" (" + namespace.size() + ")", StyledString.COUNTER_STYLER); //$NON-NLS-1$ //$NON-NLS-2$
			cell.setImage(namespaceImage);
		} else {
			cell.setImage(pathImage);
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
		if (parentElement instanceof Psr) {
			Psr psr0 = (Psr) parentElement;
			return psr0.getNamespaces().toArray();
		} else if (parentElement instanceof Namespace) {
			Namespace model = (Namespace) parentElement;
			return model.getPaths().toArray();
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
				return psr0;
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