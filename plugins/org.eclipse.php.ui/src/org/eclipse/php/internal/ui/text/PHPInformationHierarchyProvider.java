/*******************************************************************************
 * Copyright (c) 2009, 2015, 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.ui.text;

import org.eclipse.dltk.compiler.env.ISourceType;
import org.eclipse.dltk.core.IMember;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.internal.ui.editor.EditorUtility;
import org.eclipse.dltk.internal.ui.text.ScriptWordFinder;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.information.IInformationProvider;
import org.eclipse.jface.text.information.IInformationProviderExtension;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.php.internal.ui.util.PHPSelectionUtil;
import org.eclipse.ui.IEditorPart;

/**
 * Provides a PHP element information to be displayed in by an information
 * presenter. IMPORTANT : This class is for Open Hierarchy actions only ! This
 * class can handle IModelElements, unlike the {@link PHPElementProvider}.
 */
public class PHPInformationHierarchyProvider implements IInformationProvider, IInformationProviderExtension {

	private PHPStructuredEditor fEditor;
	private boolean fUseCodeResolve;

	public PHPInformationHierarchyProvider(IEditorPart editor) {
		fUseCodeResolve = false;
		if (editor instanceof PHPStructuredEditor) {
			fEditor = (PHPStructuredEditor) editor;
		}
	}

	public PHPInformationHierarchyProvider(IEditorPart editor, boolean useCodeResolve) {
		this(editor);
		fUseCodeResolve = useCodeResolve;
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

	/**
	 * This method functionality is slightly different then the method it
	 * implements (
	 * org.eclipse.jface.text.information.IInformationProviderExtension
	 * #getInformation2(org.eclipse.jface.text.ITextViewer,
	 * org.eclipse.jface.text.IRegion)) as it returns the enclosing type for
	 * members, and not the element itself (nirc)
	 */
	@Override
	public Object getInformation2(ITextViewer textViewer, IRegion subject) {
		if (fEditor == null) {
			return null;
		}

		if (fUseCodeResolve) {
			IModelElement inputModelElement = fEditor.getModelElement();
			if (inputModelElement instanceof ISourceModule && subject != null) {
				ISourceModule sourceModule = (ISourceModule) inputModelElement;
				IModelElement modelElement = PHPSelectionUtil.getSelectionModelElement(subject.getOffset(),
						subject.getLength(), sourceModule);

				if (modelElement != null) {
					if (modelElement instanceof ISourceType) {
						return modelElement;
					}
					if (modelElement instanceof IMember) {
						return modelElement;
					}
				} else { // if no element - returns null
					return null;
				}
			}
		}

		return EditorUtility.getEditorInputModelElement(fEditor, false);

	}
}
