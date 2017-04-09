/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.profile.ui.views;

import java.util.ArrayList;
import java.util.List;

/**
 * Tree element.
 */
public class TreeElement {

	private List<TreeElement> fChildren = new ArrayList<TreeElement>();
	private TreeElement fParent;
	private Object fData;
	private boolean fExpanded = false;

	public TreeElement() {
		this(null, null);
	}

	public TreeElement(TreeElement parent, Object data) {
		setParent(parent);
		setData(data);
	}

	public void addChild(TreeElement child) {
		if (child != null) {
			fChildren.add(child);
		}
	}

	public Object[] getChildren() {
		return fChildren.toArray();
	}

	public boolean hasChildren() {
		return fChildren.size() > 0;
	}

	public TreeElement getParent() {
		return fParent;
	}

	public void setParent(TreeElement parent) {
		fParent = parent;
	}

	public Object getData() {
		return fData;
	}

	public void setData(Object data) {
		fData = data;
	}

	public void setExpanded(boolean expanded) {
		fExpanded = expanded;
	}

	public boolean getExpanded() {
		return fExpanded;
	}
}
