/*******************************************************************************
 * Copyright (c) 2012, 2016 PDT Extension Group and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.ui.editor;

import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.php.composer.ui.parts.FormEntry;
import org.eclipse.php.composer.ui.parts.IFormEntryListener;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.forms.events.HyperlinkEvent;

public class FormEntryAdapter implements IFormEntryListener {
	protected IActionBars actionBars;

	public FormEntryAdapter(IActionBars actionBars) {
		this.actionBars = actionBars;
	}

	public FormEntryAdapter() {
	}

	@Override
	public void focusGained(FormEntry entry) {
		// ITextSelection selection = new TextSelection(1, 1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.pde.internal.ui.newparts.IFormEntryListener#textDirty(org.
	 * eclipse.pde.internal.ui.newparts.FormEntry)
	 */
	@Override
	public void textDirty(FormEntry entry) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.pde.internal.ui.newparts.IFormEntryListener#textValueChanged(
	 * org.eclipse.pde.internal.ui.newparts.FormEntry)
	 */
	@Override
	public void textValueChanged(FormEntry entry) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.pde.internal.ui.newparts.IFormEntryListener#
	 * browseButtonSelected(org.eclipse.pde.internal.ui.newparts.FormEntry)
	 */
	@Override
	public void browseButtonSelected(FormEntry entry) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.forms.events.HyperlinkListener#linkEntered(org.eclipse.ui.
	 * forms.events.HyperlinkEvent)
	 */
	@Override
	public void linkEntered(HyperlinkEvent e) {
		if (actionBars == null) {
			return;
		}
		IStatusLineManager mng = actionBars.getStatusLineManager();
		mng.setMessage(e.getLabel());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.forms.events.HyperlinkListener#linkExited(org.eclipse.ui.
	 * forms.events.HyperlinkEvent)
	 */
	@Override
	public void linkExited(HyperlinkEvent e) {
		if (actionBars == null) {
			return;
		}
		IStatusLineManager mng = actionBars.getStatusLineManager();
		mng.setMessage(null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.forms.events.HyperlinkListener#linkActivated(org.eclipse.
	 * ui.forms.events.HyperlinkEvent)
	 */
	@Override
	public void linkActivated(HyperlinkEvent e) {
	}

	@Override
	public void selectionChanged(FormEntry entry) {
		// ITextSelection selection = new TextSelection(1, 1);
	}

	@Override
	public void focusLost(FormEntry entry) {
	}
}
