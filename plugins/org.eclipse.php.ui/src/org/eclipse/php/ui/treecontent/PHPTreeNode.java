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
package org.eclipse.php.ui.treecontent;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.graphics.Image;

public class PHPTreeNode implements IAdaptable {

	String fId;
	String fText;
	Image fImage;
	Object[] fChildren;
	private Object fData;

	public PHPTreeNode(String text, Image image, String id, Object data, Object[] children) {
		fText = text;
		fImage = image;
		fId = id;
		fChildren = children;
		fData = data;
	}

	public Object[] getChildren() {
		return fChildren;
	}

	public boolean hasChildren() {
		if (fChildren == null) {
			return false;
		}
		return fChildren.length > 0;
	}

	public void setChildren(Object[] children) {
		fChildren = children;
	}

	public String getId() {
		return fId;
	}

	public void setId(String id) {
		fId = id;
	}

	public Object getData() {
		return fData;
	}

	public Image getImage() {
		return fImage;
	}

	public void setImage(Image image) {
		fImage = image;
	}

	public String getText() {
		return fText;
	}

	public void setText(String text) {
		fText = text;
	}

	public Object getAdapter(Class adapter) {
		return Platform.getAdapterManager().getAdapter(this, adapter);
	}
}
