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

import org.eclipse.dltk.internal.ui.text.HTMLTextPresenter;
import org.eclipse.dltk.internal.ui.text.hover.AbstractAnnotationHover;
import org.eclipse.jface.text.DefaultInformationControl;
import org.eclipse.jface.text.IInformationControl;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.text.information.IInformationProviderExtension2;
import org.eclipse.php.ui.editor.hover.IHoverMessageDecorator;
import org.eclipse.php.ui.editor.hover.IPHPTextHover;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.editors.text.EditorsUI;

public class PHPAnnotationTextHover extends AbstractAnnotationHover implements
		IPHPTextHover, IInformationProviderExtension2 {

	public PHPAnnotationTextHover() {
		super(true);
	}

	public IHoverMessageDecorator getMessageDecorator() {
		return null;
	}

	public IInformationControlCreator getInformationPresenterControlCreator() {
		return new IInformationControlCreator() {
			public IInformationControl createInformationControl(Shell parent) {
				return new DefaultInformationControl(parent, SWT.NONE,
						new HTMLTextPresenter(true), EditorsUI
								.getTooltipAffordanceString());
			}
		};
	}
}
