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
package org.eclipse.php.internal.ui.editor.hover;

import org.eclipse.core.runtime.Assert;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.jface.internal.text.html.BrowserInformationControlInput;

/**
 * Browser input for Javadoc hover.
 * 
 * @since 3.4
 */
public class PHPDocumentationBrowserInformationControlInput extends BrowserInformationControlInput {

	private final IModelElement fElement;
	private final String fHtml;
	private final int fLeadingImageWidth;

	/**
	 * Creates a new browser information control input.
	 * 
	 * @param previous
	 *            previous input, or <code>null</code> if none available
	 * @param element
	 *            the element, or <code>null</code> if none available
	 * @param html
	 *            HTML contents, must not be null
	 * @param leadingImageWidth
	 *            the indent required for the element image
	 */
	public PHPDocumentationBrowserInformationControlInput(PHPDocumentationBrowserInformationControlInput previous,
			IModelElement element, String html, int leadingImageWidth) {
		super(previous);
		Assert.isNotNull(html);
		fElement = element;
		fHtml = html;
		fLeadingImageWidth = leadingImageWidth;
	}

	/*
	 * @seeorg.eclipse.jface.internal.text.html.BrowserInformationControlInput#
	 * getLeadingImageWidth()
	 * 
	 * @since 3.4
	 */
	@Override
	public int getLeadingImageWidth() {
		return fLeadingImageWidth;
	}

	/**
	 * Returns the Java element.
	 * 
	 * @return the element or <code>null</code> if none available
	 */
	public IModelElement getElement() {
		return fElement;
	}

	/*
	 * @see org.eclipse.jface.internal.text.html.BrowserInput#getHtml()
	 */
	@Override
	public String getHtml() {
		return fHtml;
	}

	/*
	 * @see org.eclipse.jdt.internal.ui.infoviews.BrowserInput#getInputElement()
	 */
	@Override
	public Object getInputElement() {
		return fElement == null ? (Object) fHtml : fElement;
	}

	/*
	 * @see org.eclipse.jdt.internal.ui.infoviews.BrowserInput#getInputName()
	 */
	@Override
	public String getInputName() {
		return fElement == null ? "" : fElement.getElementName(); //$NON-NLS-1$
	}

}
