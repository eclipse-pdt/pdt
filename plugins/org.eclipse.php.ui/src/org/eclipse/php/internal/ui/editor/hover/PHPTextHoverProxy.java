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

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.*;
import org.eclipse.jface.text.information.IInformationProviderExtension2;
import org.eclipse.php.internal.ui.text.hover.PHPEditorTextHoverDescriptor;
import org.eclipse.php.ui.editor.hover.IHoverMessageDecorator;
import org.eclipse.php.ui.editor.hover.IPHPTextHover;
import org.eclipse.ui.IEditorPart;

public class PHPTextHoverProxy extends AbstractPHPEditorTextHover implements
		IPHPTextHover, ITextHoverExtension, IInformationProviderExtension2 {

	private PHPEditorTextHoverDescriptor fHoverDescriptor;
	private IPHPTextHover fHover;

	public PHPTextHoverProxy(PHPEditorTextHoverDescriptor descriptor,
			IEditorPart editor, IPreferenceStore store) {
		fHoverDescriptor = descriptor;
		setEditor(editor);
		setPreferenceStore(store);
	}

	public void setPreferenceStore(IPreferenceStore store) {
		super.setPreferenceStore(store);

		if (fHover != null)
			fHover.setPreferenceStore(getPreferenceStore());
	}

	public void setEditor(IEditorPart editor) {
		super.setEditor(editor);
		if (fHover != null && getEditor() != null) {
			fHover.setEditor(getEditor());
		}
	}

	public boolean isEnabled() {
		return true;
	}

	/*
	 * @see ITextHover#getHoverRegion(ITextViewer, int)
	 */
	public IRegion getHoverRegion(ITextViewer textViewer, int offset) {
		if (ensureHoverCreated()) {
			return fHover.getHoverRegion(textViewer, offset);
		}
		return null;
	}

	/*
	 * @see ITextHover#getHoverInfo(ITextViewer, IRegion)
	 */
	@SuppressWarnings("deprecation")
	public String getHoverInfo(ITextViewer textViewer, IRegion hoverRegion) {
		if (ensureHoverCreated()) {
			return fHover.getHoverInfo(textViewer, hoverRegion);
		}
		return null;
	}

	public Object getHoverInfo2(ITextViewer textViewer, IRegion hoverRegion) {
		if (ensureHoverCreated()) {
			if (fHover instanceof ITextHoverExtension2)
				return ((ITextHoverExtension2) fHover).getHoverInfo2(
						textViewer, hoverRegion);
			else
				return fHover.getHoverInfo(textViewer, hoverRegion);
		}

		return null;
	}

	private boolean ensureHoverCreated() {
		if (!isEnabled() || fHoverDescriptor == null)
			return false;
		return isCreated() || createHover();
	}

	private boolean isCreated() {
		return fHover != null;
	}

	private boolean createHover() {
		fHover = fHoverDescriptor.createTextHover();
		if (fHover != null && getEditor() != null) {
			fHover.setEditor(getEditor());
		}
		return isCreated();
	}

	/*
	 * @see org.eclipse.jface.text.ITextHoverExtension#getHoverControlCreator()
	 * 
	 * @since 3.0
	 */
	public IInformationControlCreator getHoverControlCreator() {
		if (ensureHoverCreated() && (fHover instanceof ITextHoverExtension))
			return ((ITextHoverExtension) fHover).getHoverControlCreator();

		return null;
	}

	public IInformationControlCreator getInformationPresenterControlCreator() {
		if (ensureHoverCreated()
				&& (fHover instanceof IInformationProviderExtension2))
			return ((IInformationProviderExtension2) fHover)
					.getInformationPresenterControlCreator();

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.ui.editor.hover.IPHPTextHover#getMessageDecorator
	 * ()
	 */
	public IHoverMessageDecorator getMessageDecorator() {
		return fHover.getMessageDecorator();
	}
}
