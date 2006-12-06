/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.ui.editor.hover;

import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextHoverExtension;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.information.IInformationProviderExtension2;
import org.eclipse.php.internal.ui.text.hover.PHPEditorTextHoverDescriptor;
import org.eclipse.ui.IEditorPart;

public class PHPTextHoverProxy extends AbstractPHPTextHover implements ITextHoverExtension, IInformationProviderExtension2 {
	private PHPEditorTextHoverDescriptor fHoverDescriptor;
	private IPHPTextHover fHover;

	public PHPTextHoverProxy(PHPEditorTextHoverDescriptor descriptor, IEditorPart editor) {
		fHoverDescriptor = descriptor;
		if (editor != null) {
			setEditorPart(editor);
		}
	}

	/*
	 * @see IPHPEditorTextHover#setEditor(IEditorPart)
	 */
	public void setEditorPart(IEditorPart editor) {
		super.setEditorPart(editor);

		if (fHover != null && getEditorPart() != null)
			fHover.setEditorPart(getEditorPart());
	}

	public boolean isEnabled() {
		return true;
	}

	/*
	 * @see ITextHover#getHoverRegion(ITextViewer, int)
	 */
	public IRegion getHoverRegion(ITextViewer textViewer, int offset) {
		if (ensureHoverCreated())
			return fHover.getHoverRegion(textViewer, offset);

		return null;
	}

	/*
	 * @see ITextHover#getHoverInfo(ITextViewer, IRegion)
	 */
	public String getHoverInfo(ITextViewer textViewer, IRegion hoverRegion) {
		if (ensureHoverCreated())
			return fHover.getHoverInfo(textViewer, hoverRegion);

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
		if (fHover != null && getEditorPart() != null)
			fHover.setEditorPart(getEditorPart());
		return isCreated();
	}

	/*
	 * @see org.eclipse.jface.text.ITextHoverExtension#getHoverControlCreator()
	 * @since 3.0
	 */
	public IInformationControlCreator getHoverControlCreator() {
		if (ensureHoverCreated() && (fHover instanceof ITextHoverExtension))
			return ((ITextHoverExtension) fHover).getHoverControlCreator();

		return null;
	}

	public IInformationControlCreator getInformationPresenterControlCreator() {
		if (ensureHoverCreated() && (fHover instanceof IInformationProviderExtension2))
			return ((IInformationProviderExtension2) fHover).getInformationPresenterControlCreator();

		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.ui.editor.hover.IPHPTextHover#getMessageDecorator()
	 */
	public IHoverMessageDecorator getMessageDecorator() {
		return fHover.getMessageDecorator();
	}
}
