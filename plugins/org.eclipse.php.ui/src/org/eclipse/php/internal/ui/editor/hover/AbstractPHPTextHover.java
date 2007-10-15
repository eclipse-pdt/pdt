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
package org.eclipse.php.internal.ui.editor.hover;

import java.util.regex.Pattern;

import org.eclipse.jface.text.*;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.internal.core.documentModel.parser.PHPRegionContext;
import org.eclipse.php.internal.core.documentModel.parser.regions.PhpScriptRegion;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.actions.IPHPEditorActionDefinitionIds;
import org.eclipse.php.ui.editor.hover.IHoverMessageDecorator;
import org.eclipse.php.ui.editor.hover.IPHPTextHover;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.keys.IBindingService;
import org.eclipse.wst.sse.core.internal.provisional.text.*;
import org.eclipse.wst.sse.ui.internal.derived.HTMLTextPresenter;

public abstract class AbstractPHPTextHover implements IPHPTextHover, ITextHoverExtension {

	protected Pattern tab = Pattern.compile("\t"); //$NON-NLS-1$
	protected IEditorPart fEditor;
	private IBindingService fBindingService = (IBindingService) PlatformUI.getWorkbench().getAdapter(IBindingService.class);

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
		
		ITextRegionCollection container = flatNode;
		if(region instanceof ITextRegionContainer){
			container = (ITextRegionContainer)region;
			region = container.getRegionAtCharacterOffset(offset);
		}

		if (region.getType() == PHPRegionContext.PHP_CONTENT) {
			PhpScriptRegion phpScriptRegion = (PhpScriptRegion)region;
			try {
				region = phpScriptRegion.getPhpToken(offset - container.getStartOffset() - region.getStart());
			} catch (BadLocationException e) {
				region = null;
			}
			if (region != null) {
				return new Region(container.getStartOffset() + phpScriptRegion.getStart() + region.getStart() , region.getLength());
			}
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

		return NLS.bind(PHPUIMessages.getString("HoverFocus_message"), keySequence);
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

	public IHoverMessageDecorator getMessageDecorator() {
		return null;
	}
}
