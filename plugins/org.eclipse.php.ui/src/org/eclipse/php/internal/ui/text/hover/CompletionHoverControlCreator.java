/*******************************************************************************
 * Copyright (c) 2017 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.ui.text.hover;

import org.eclipse.dltk.internal.ui.BrowserInformationControl;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.jface.text.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.editors.text.EditorsUI;

/**
 * @deprecated
 */
@Deprecated
public final class CompletionHoverControlCreator extends AbstractReusableInformationControlCreator {
	/**
	 * The information presenter control creator.
	 */
	private final IInformationControlCreator fInformationPresenterControlCreator;
	/**
	 * <code>true</code> to use the additional info affordance, <code>false</code>
	 * to use the hover affordance.
	 */
	private final boolean fAdditionalInfoAffordance;

	/**
	 * @param informationPresenterControlCreator
	 *            control creator for enriched hover
	 */
	public CompletionHoverControlCreator(IInformationControlCreator informationPresenterControlCreator) {
		this(informationPresenterControlCreator, false);
	}

	/**
	 * @param informationPresenterControlCreator
	 *            control creator for enriched hover
	 * @param additionalInfoAffordance
	 *            <code>true</code> to use the additional info affordance,
	 *            <code>false</code> to use the hover affordance
	 */
	public CompletionHoverControlCreator(IInformationControlCreator informationPresenterControlCreator,
			boolean additionalInfoAffordance) {
		fInformationPresenterControlCreator = informationPresenterControlCreator;
		fAdditionalInfoAffordance = additionalInfoAffordance;
	}

	/*
	 * @seeorg.eclipse.jdt.internal.ui.text.java.hover.
	 * AbstractReusableInformationControlCreator
	 * #doCreateInformationControl(org.eclipse.swt.widgets.Shell)
	 */
	@Override
	public IInformationControl doCreateInformationControl(Shell parent) {
		String tooltipAffordanceString = fAdditionalInfoAffordance ? DLTKUIPlugin.getAdditionalInfoAffordanceString()
				: EditorsUI.getTooltipAffordanceString();
		if (BrowserInformationControl.isAvailable(parent)) {
			BrowserInformationControl2 iControl = new BrowserInformationControl2(parent, SWT.TOOL | SWT.NO_TRIM,
					SWT.NONE, tooltipAffordanceString) {
				@Override
				public IInformationControlCreator getInformationPresenterControlCreator() {
					return fInformationPresenterControlCreator;
				}
			};
			return iControl;
		} else {
			return new DefaultInformationControl(parent, tooltipAffordanceString);
		}
	}

	/*
	 * @seeorg.eclipse.jdt.internal.ui.text.java.hover.
	 * AbstractReusableInformationControlCreator
	 * #canReuse(org.eclipse.jface.text.IInformationControl)
	 */
	@Override
	public boolean canReuse(IInformationControl control) {
		if (!super.canReuse(control)) {
			return false;
		}

		if (control instanceof IInformationControlExtension4) {
			String tooltipAffordanceString = fAdditionalInfoAffordance
					? DLTKUIPlugin.getAdditionalInfoAffordanceString()
					: EditorsUI.getTooltipAffordanceString();
			((IInformationControlExtension4) control).setStatusText(tooltipAffordanceString);
		}

		return true;
	}
}
