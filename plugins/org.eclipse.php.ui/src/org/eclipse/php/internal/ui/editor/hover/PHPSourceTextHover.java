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

import org.eclipse.dltk.internal.ui.text.hover.ScriptSourceHover;
import org.eclipse.jface.text.AbstractReusableInformationControlCreator;
import org.eclipse.jface.text.IInformationControl;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.text.ITextHoverExtension;
import org.eclipse.jface.text.information.IInformationProviderExtension2;
import org.eclipse.php.ui.editor.hover.IHoverMessageDecorator;
import org.eclipse.php.ui.editor.hover.IPHPTextHover;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.editors.text.EditorsUI;

public class PHPSourceTextHover extends ScriptSourceHover implements
		IPHPTextHover, IInformationProviderExtension2, ITextHoverExtension {

	/**
	 * The hover control creator.
	 * 
	 * @since 3.2
	 */
	private IInformationControlCreator fHoverControlCreator;

	/**
	 * The presentation control creator.
	 * 
	 * @since 3.2
	 */
	private IInformationControlCreator fPresenterControlCreator;

	/*
	 * @see
	 * IInformationProviderExtension2#getInformationPresenterControlCreator()
	 * 
	 * @since 3.1 This is the format of the window on focus
	 */
	public IInformationControlCreator getInformationPresenterControlCreator() {
		if (fPresenterControlCreator == null) {
			fPresenterControlCreator = new AbstractReusableInformationControlCreator() {

				/*
				 * @seeorg.eclipse.jdt.internal.ui.text.java.hover.
				 * AbstractReusableInformationControlCreator
				 * #doCreateInformationControl(org.eclipse.swt.widgets.Shell)
				 */
				public IInformationControl doCreateInformationControl(
						Shell parent) {
					int shellStyle = SWT.RESIZE | SWT.TOOL;
					int style = SWT.V_SCROLL | SWT.H_SCROLL;
					return new PHPSourceViewerInformationControl(parent,
							shellStyle, style);
				}
			};
		}
		return fPresenterControlCreator;
	}

	/*
	 * @see ITextHoverExtension#getHoverControlCreator()
	 * 
	 * @since 3.2 - This is the format of the window on hover
	 */
	public IInformationControlCreator getHoverControlCreator() {
		if (fHoverControlCreator == null) {
			fHoverControlCreator = new AbstractReusableInformationControlCreator() {
				/*
				 * @seeorg.eclipse.jdt.internal.ui.text.java.hover.
				 * AbstractReusableInformationControlCreator
				 * #doCreateInformationControl(org.eclipse.swt.widgets.Shell)
				 */
				public IInformationControl doCreateInformationControl(
						Shell parent) {
					return new PHPSourceViewerInformationControl(parent,
							SWT.NONE, EditorsUI.getTooltipAffordanceString());
				}
			};
		}
		return fHoverControlCreator;
	}

	public IHoverMessageDecorator getMessageDecorator() {
		return null;
	}
}