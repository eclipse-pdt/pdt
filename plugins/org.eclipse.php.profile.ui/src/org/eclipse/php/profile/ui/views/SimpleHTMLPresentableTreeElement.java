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

import org.eclipse.php.profile.core.data.ProfilerClassData;
import org.eclipse.php.profile.core.data.ProfilerFileData;
import org.eclipse.php.profile.core.data.ProfilerFunctionData;

/**
 * Simple HTML presentable tree element.
 */
public class SimpleHTMLPresentableTreeElement extends TreeElement implements IHTMLPresentableTreeElement {

	private static final String FILE_IMAGE_URL = "file.png"; //$NON-NLS-1$
	private static final String CLASS_IMAGE_URL = "class.png"; //$NON-NLS-1$
	private static final String FUNCTION_IMAGE_URL = "function.png"; //$NON-NLS-1$

	private static final String FILE_CLASS = "file"; //$NON-NLS-1$
	private static final String CLASS_CLASS = "class"; //$NON-NLS-1$
	private static final String FUNCTION_CLASS = "function"; //$NON-NLS-1$

	private String fImageURL;
	private String fTableLinkClass;
	private String fTableRowClass;

	public SimpleHTMLPresentableTreeElement() {
		super();
	}

	public SimpleHTMLPresentableTreeElement(TreeElement parent, Object data) {
		super(parent, data);
		init();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.profile.ui.views.TreeElement#setData(java.lang.Object)
	 */
	@Override
	public void setData(Object data) {
		super.setData(data);
		init();
	}

	private void init() {
		Object data = getData();
		if (data instanceof ProfilerFileData) {
			fImageURL = FILE_IMAGE_URL;
			fTableLinkClass = FILE_CLASS;
			fTableRowClass = FILE_CLASS;
		} else if (data instanceof ProfilerClassData) {
			fImageURL = CLASS_IMAGE_URL;
			fTableLinkClass = CLASS_CLASS;
			fTableRowClass = CLASS_CLASS;
		} else if (data instanceof ProfilerFunctionData) {
			fImageURL = FUNCTION_IMAGE_URL;
			fTableLinkClass = FUNCTION_CLASS;
			fTableRowClass = FUNCTION_CLASS;
		}
	}

	@Override
	public String getImageURL() {
		return fImageURL;
	}

	@Override
	public String getTableLinkClass() {
		return fTableLinkClass;
	}

	@Override
	public String getTableRowClass() {
		return fTableRowClass;
	}

}
