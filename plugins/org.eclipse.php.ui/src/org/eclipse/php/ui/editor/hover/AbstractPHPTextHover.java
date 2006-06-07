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

import java.util.regex.Pattern;

import org.eclipse.jface.text.DefaultInformationControl;
import org.eclipse.jface.text.IInformationControl;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextHoverExtension;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Region;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.internal.ui.actions.IPHPEditorActionDefinitionIds;
import org.eclipse.php.ui.editor.PHPEditorMessages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.keys.IBindingService;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;
import org.eclipse.wst.sse.ui.internal.derived.HTMLTextPresenter;

public abstract class AbstractPHPTextHover implements IPHPTextHover, ITextHoverExtension {

	protected Pattern tab = Pattern.compile("\t");
	protected IEditorPart fEditor;
	private IBindingService fBindingService;
	{
		fBindingService = (IBindingService) PlatformUI.getWorkbench().getAdapter(IBindingService.class);
	}

	public IEditorPart getEditorPart() {
		return fEditor;
	}

	public void setEditorPart(IEditorPart editorPart) {
		fEditor = editorPart;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.text.ITextHover#getHoverRegion(org.eclipse.jface.text.ITextViewer, int)
	 */
	public IRegion getHoverRegion(ITextViewer textViewer, int offset) {
		if ((textViewer == null) || (textViewer.getDocument() == null))
			return null;

		IStructuredDocumentRegion flatNode = ((IStructuredDocument) textViewer.getDocument()).getRegionAtCharacterOffset(offset);
		ITextRegion region = null;

		if (flatNode != null) {
			region = flatNode.getRegionAtCharacterOffset(offset);
		}

		if (region != null) {
			return new Region(flatNode.getStartOffset(region), region.getLength());
		}
		return null;
	}

	/**
	 * Returns the tool tip affordance string.
	 *
	 * @return the affordance string or <code>null</code> if disabled or no key binding is defined
	 * @since 3.0
	 */
	protected String getTooltipAffordanceString() {
		if (fBindingService == null)// || !JavaPlugin.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.EDITOR_SHOW_TEXT_HOVER_AFFORDANCE))
			return null;

		String keySequence = fBindingService.getBestActiveBindingFormattedFor(IPHPEditorActionDefinitionIds.SHOW_PHPDOC);
		if (keySequence == null)
			return null;

		return NLS.bind(PHPEditorMessages.HoverFocus_message, keySequence);
	}

	/*
	 * @see ITextHoverExtension#getHoverControlCreator()
	 * @since 3.0
	 */
	public IInformationControlCreator getHoverControlCreator() {
		return new IInformationControlCreator() {
			public IInformationControl createInformationControl(Shell parent) {
				return new DefaultInformationControl(parent, SWT.NONE, new HTMLTextPresenter(true), getTooltipAffordanceString());
			}
		};
	}
}
