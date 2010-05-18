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

import org.eclipse.dltk.internal.ui.text.hover.AbstractAnnotationHover;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.text.information.IInformationProviderExtension2;
import org.eclipse.php.internal.ui.editor.hover.PHPDocumentationHover.PresenterControlCreator;
import org.eclipse.php.ui.editor.hover.IHoverMessageDecorator;
import org.eclipse.php.ui.editor.hover.IPHPTextHover;

public class PHPAnnotationTextHover extends AbstractAnnotationHover implements
		IPHPTextHover, IInformationProviderExtension2 {

	public PHPAnnotationTextHover() {
		super(true);
	}

	public IHoverMessageDecorator getMessageDecorator() {
		return null;
	}

	/**
	 * The presentation control creator.
	 * 
	 * @since 3.2
	 */
	private IInformationControlCreator fPresenterControlCreator;

	/*
	 * @seeorg.eclipse.jface.text.ITextHoverExtension2#
	 * getInformationPresenterControlCreator()
	 * 
	 * @since 3.1
	 */
	public IInformationControlCreator getInformationPresenterControlCreator() {
		if (fPresenterControlCreator == null)
			fPresenterControlCreator = new PresenterControlCreator();
		return fPresenterControlCreator;
	}
}
