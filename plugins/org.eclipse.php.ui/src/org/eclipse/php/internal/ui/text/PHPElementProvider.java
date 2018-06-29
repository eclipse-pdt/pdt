/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
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
package org.eclipse.php.internal.ui.text;

import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.internal.ui.actions.SelectionConverter;
import org.eclipse.dltk.internal.ui.text.ScriptWordFinder;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.information.IInformationProvider;
import org.eclipse.jface.text.information.IInformationProviderExtension;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.ui.IEditorPart;

/**
 * Provides a PHP element to be displayed in by an information presenter.
 */
public class PHPElementProvider implements IInformationProvider, IInformationProviderExtension {

	private PHPStructuredEditor fEditor;

	public PHPElementProvider(IEditorPart editor) {
		if (editor instanceof PHPStructuredEditor) {
			fEditor = (PHPStructuredEditor) editor;
		}
	}

	/*
	 * @see IInformationProvider#getSubject(ITextViewer, int)
	 */
	@Override
	public IRegion getSubject(ITextViewer textViewer, int offset) {
		if (textViewer != null && fEditor != null) {
			IRegion region = ScriptWordFinder.findWord(textViewer.getDocument(), offset);
			if (region != null) {
				return region;
			} else {
				return new Region(offset, 0);
			}
		}
		return null;
	}

	/*
	 * @see IInformationProvider#getInformation(ITextViewer, IRegion)
	 */
	@Override
	public String getInformation(ITextViewer textViewer, IRegion subject) {
		return getInformation2(textViewer, subject).toString();
	}

	/*
	 * @see IInformationProviderExtension#getElement(ITextViewer, IRegion)
	 */
	@Override
	public Object getInformation2(ITextViewer textViewer, IRegion subject) {
		if (fEditor == null) {
			return null;
		}

		try {
			IModelElement element = SelectionConverter.getElementAtOffset(fEditor);
			if (element != null) {
				return element;
			}
		} catch (ModelException e) {
		}

		return fEditor.getModelElement();
	}
}
