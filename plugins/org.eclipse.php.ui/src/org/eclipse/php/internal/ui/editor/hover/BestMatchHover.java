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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.*;
import org.eclipse.jface.text.information.IInformationProviderExtension2;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.preferences.PreferenceConstants;
import org.eclipse.php.internal.ui.text.hover.PHPEditorTextHoverDescriptor;
import org.eclipse.php.ui.editor.hover.IHoverMessageDecorator;
import org.eclipse.php.ui.editor.hover.IPHPTextHover;
import org.eclipse.ui.IEditorPart;

public class BestMatchHover extends AbstractPHPEditorTextHover implements
		IPHPTextHover, ITextHoverExtension, IInformationProviderExtension2 {

	private List<PHPEditorTextHoverDescriptor> fTextHoverSpecifications;
	private List<ITextHover> fInstantiatedTextHovers;
	private ITextHover fBestHover;

	public BestMatchHover() {
		this(PHPUiPlugin.getActivePage() == null ? null : PHPUiPlugin
				.getActivePage().getActiveEditor(), PHPUiPlugin.getDefault()
				.getPreferenceStore());
	}

	public BestMatchHover(IEditorPart editor, IPreferenceStore store) {
		setEditor(editor);
		setPreferenceStore(store);
		installTextHovers();
	}

	/**
	 * Installs all text hovers.
	 */
	private void installTextHovers() {

		// initialize lists - indicates that the initialization happened
		fTextHoverSpecifications = new ArrayList<PHPEditorTextHoverDescriptor>(
				2);
		fInstantiatedTextHovers = new ArrayList<ITextHover>(2);

		// populate list
		PHPEditorTextHoverDescriptor[] hoverDescs = PHPUiPlugin.getDefault()
				.getPHPEditorTextHoverDescriptors();
		for (int i = 0; i < hoverDescs.length; i++) {
			// ensure that we don't add ourselves to the list
			if (!PreferenceConstants.ID_BESTMATCH_HOVER.equals(hoverDescs[i]
					.getId()))
				fTextHoverSpecifications.add(hoverDescs[i]);
		}
	}

	private void checkTextHovers() {
		if (fTextHoverSpecifications.size() == 0)
			return;

		for (Iterator<PHPEditorTextHoverDescriptor> iterator = new ArrayList<PHPEditorTextHoverDescriptor>(
				fTextHoverSpecifications).iterator(); iterator.hasNext();) {
			PHPEditorTextHoverDescriptor spec = iterator.next();

			IPHPTextHover hover = spec.createTextHover();
			if (hover != null) {
				hover.setEditor(getEditor());
				hover.setPreferenceStore(getPreferenceStore());
				addTextHover(hover);
				fTextHoverSpecifications.remove(spec);
			}
		}
	}

	protected void addTextHover(ITextHover hover) {
		if (!fInstantiatedTextHovers.contains(hover))
			fInstantiatedTextHovers.add(hover);
	}

	/*
	 * @see ITextHover#getHoverInfo(ITextViewer, IRegion)
	 */
	@SuppressWarnings("deprecation")
	public String getHoverInfo(ITextViewer textViewer, IRegion hoverRegion) {
		checkTextHovers();
		fBestHover = null;

		if (fInstantiatedTextHovers == null)
			return null;

		for (Iterator<ITextHover> iterator = fInstantiatedTextHovers.iterator(); iterator
				.hasNext();) {
			ITextHover hover = (ITextHover) iterator.next();
			String s = hover.getHoverInfo(textViewer, hoverRegion);
			if (s != null && s.trim().length() > 0) {
				fBestHover = hover;
				return s;
			}
		}
		return null;
	}

	/*
	 * @see
	 * org.eclipse.jface.text.ITextHoverExtension2#getHoverInfo2(org.eclipse
	 * .jface.text.ITextViewer, org.eclipse.jface.text.IRegion)
	 */
	public Object getHoverInfo2(ITextViewer textViewer, IRegion hoverRegion) {

		checkTextHovers();
		fBestHover = null;

		if (fInstantiatedTextHovers == null)
			return null;

		for (Iterator iterator = fInstantiatedTextHovers.iterator(); iterator
				.hasNext();) {
			ITextHover hover = (ITextHover) iterator.next();

			if (hover instanceof ITextHoverExtension2) {
				Object info = ((ITextHoverExtension2) hover).getHoverInfo2(
						textViewer, hoverRegion);
				if (info != null) {
					fBestHover = hover;
					return info;
				}
			} else {
				String s = hover.getHoverInfo(textViewer, hoverRegion);
				if (s != null && s.trim().length() > 0) {
					fBestHover = hover;
					return s;
				}
			}
		}

		return null;
	}

	/*
	 * @see org.eclipse.jface.text.ITextHoverExtension#getHoverControlCreator()
	 * 
	 * @since 3.0
	 */
	public IInformationControlCreator getHoverControlCreator() {
		if (fBestHover instanceof ITextHoverExtension)
			return ((ITextHoverExtension) fBestHover).getHoverControlCreator();

		return null;
	}

	/*
	 * @seeorg.eclipse.jface.text.information.IInformationProviderExtension2#
	 * getInformationPresenterControlCreator()
	 * 
	 * @since 3.0
	 */
	public IInformationControlCreator getInformationPresenterControlCreator() {
		if (fBestHover instanceof IInformationProviderExtension2)
			return ((IInformationProviderExtension2) fBestHover)
					.getInformationPresenterControlCreator();

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.php.internal.ui.editor.hover.AbstractPHPTextHover#
	 * getMessageDecorator()
	 */
	public IHoverMessageDecorator getMessageDecorator() {
		if (fBestHover instanceof IPHPTextHover) {
			return ((IPHPTextHover) fBestHover).getMessageDecorator();
		}
		return null;
	}
}
