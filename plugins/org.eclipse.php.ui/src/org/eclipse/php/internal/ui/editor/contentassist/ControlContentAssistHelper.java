/*******************************************************************************
 * Copyright (c) 2000, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor.contentassist;

import org.eclipse.jface.contentassist.SubjectControlContentAssistant;
import org.eclipse.jface.internal.text.html.HTMLTextPresenter;
import org.eclipse.jface.text.DefaultInformationControl;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IInformationControl;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.contentassist.ContentAssistHandler;


/**
 * @since 3.0
 */
public class ControlContentAssistHelper {
	
	/**
	 * @param text the text field to install ContentAssist
	 * @param processor the <code>IContentAssistProcessor</code>
	 */
	public static void createTextContentAssistant(final Text text, IContentAssistProcessor processor) {
		ContentAssistHandler.createHandlerForText(text, createPHPContentAssistant(processor));
	}

	/**
	 * @param combo the text field to install ContentAssist
	 * @param processor the <code>IContentAssistProcessor</code>
	 */
	public static void createComboContentAssistant(final Combo combo, IContentAssistProcessor processor) {
		ContentAssistHandler.createHandlerForCombo(combo, createPHPContentAssistant(processor));
	}

	public static SubjectControlContentAssistant createPHPContentAssistant(IContentAssistProcessor processor) {
		final SubjectControlContentAssistant contentAssistant = new SubjectControlContentAssistant();

		contentAssistant.setContentAssistProcessor(processor, IDocument.DEFAULT_CONTENT_TYPE);

		contentAssistant.setContextInformationPopupOrientation(IContentAssistant.CONTEXT_INFO_ABOVE);
		contentAssistant.setInformationControlCreator(new IInformationControlCreator() {
			public IInformationControl createInformationControl(Shell parent) {
				return new DefaultInformationControl(parent, SWT.NONE, new HTMLTextPresenter(true));
			}
		});

		return contentAssistant;
	}
	
}
