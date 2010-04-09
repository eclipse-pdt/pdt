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

import org.eclipse.dltk.internal.ui.text.hover.AbstractScriptEditorTextHover;
import org.eclipse.jface.internal.text.html.HTMLTextPresenter;
import org.eclipse.jface.text.*;
import org.eclipse.jface.text.information.IInformationProviderExtension2;
import org.eclipse.php.ui.editor.hover.IHoverMessageDecorator;
import org.eclipse.php.ui.editor.hover.IPHPTextHover;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.editors.text.EditorsUI;

public class ProblemHover extends AbstractScriptEditorTextHover implements
		IPHPTextHover, IInformationProviderExtension2, ITextHoverExtension {

	private static final ProblemHoverProcessor annotationHover = new ProblemHoverProcessor();

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
					return new DefaultInformationControl(parent,
							new HTMLTextPresenter(false));
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
					return new DefaultInformationControl(parent, EditorsUI
							.getTooltipAffordanceString(),
							new HTMLTextPresenter(true));
				}
			};
		}
		return fHoverControlCreator;
	}

	public String getHoverInfo(ITextViewer textViewer, IRegion hoverRegion) {
		return annotationHover.getHoverInfo(textViewer, hoverRegion);
	}

	public IRegion getHoverRegion(ITextViewer textViewer, int offset) {
		return annotationHover.getHoverRegion(textViewer, offset);
	}

	public IHoverMessageDecorator getMessageDecorator() {
		return null;
	}
}
