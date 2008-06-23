/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.text;

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
	private boolean fUseCodeResolve;

	public PHPElementProvider(IEditorPart editor) {
		fUseCodeResolve = false;
		if (editor instanceof PHPStructuredEditor)
			fEditor = (PHPStructuredEditor) editor;
	}

	public PHPElementProvider(IEditorPart editor, boolean useCodeResolve) {
		this(editor);
		fUseCodeResolve = useCodeResolve;
	}

	/*
	 * @see IInformationProvider#getSubject(ITextViewer, int)
	 */
	public IRegion getSubject(ITextViewer textViewer, int offset) {
		if (textViewer != null && fEditor != null) {
			IRegion region = PHPWordFinder.findWord(textViewer.getDocument(), offset);
			if (region != null)
				return region;
			else
				return new Region(offset, 0);
		}
		return null;
	}

	/*
	 * @see IInformationProvider#getInformation(ITextViewer, IRegion)
	 */
	public String getInformation(ITextViewer textViewer, IRegion subject) {
		return getInformation2(textViewer, subject).toString();
	}

	/*
	 * @see IInformationProviderExtension#getElement(ITextViewer, IRegion)
	 */
	public Object getInformation2(ITextViewer textViewer, IRegion subject) {
		if (fEditor == null)
			return null;

//		if (fUseCodeResolve) {
//			IStructuredSelection sel = SelectionConverter.getStructuredSelection(fEditor);
//			if (!sel.isEmpty())
//				return sel.getFirstElement();
//		}
//		PHPCodeData element = SelectionConverter.getElementAtOffset(fEditor);
//		if (element != null)
//			return element;

		return fEditor.getPHPFileData();
	}
}
