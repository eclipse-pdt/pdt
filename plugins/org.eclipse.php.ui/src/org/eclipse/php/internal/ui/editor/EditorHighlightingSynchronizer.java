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

package org.eclipse.php.internal.ui.editor;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.text.link.ILinkedModeListener;
import org.eclipse.jface.text.link.LinkedModeModel;

public class EditorHighlightingSynchronizer implements ILinkedModeListener {

	private final PHPStructuredEditor fEditor;
	private final boolean fWasOccurrencesOn;

	/**
	 * Creates a new synchronizer.
	 * 
	 * @param editor
	 *            the java editor the occurrences markers of which will be
	 *            synchronized with the linked mode
	 * 
	 */
	public EditorHighlightingSynchronizer(PHPStructuredEditor editor) {
		Assert.isLegal(editor != null);
		fEditor = editor;
		fWasOccurrencesOn = fEditor.isMarkingOccurrences();

		if (fWasOccurrencesOn && !isEditorDisposed())
			fEditor.uninstallOccurrencesFinder();
	}

	/*
	 * @see
	 * org.eclipse.jface.text.link.ILinkedModeListener#left(org.eclipse.jface
	 * .text.link.LinkedModeModel, int)
	 */
	public void left(LinkedModeModel environment, int flags) {
		if (fWasOccurrencesOn && !isEditorDisposed())
			fEditor.installOccurrencesFinder(true);
	}

	/*
	 * @since 3.2
	 */
	private boolean isEditorDisposed() {
		return fEditor == null || fEditor.getSelectionProvider() == null;
	}

	/*
	 * @see
	 * org.eclipse.jface.text.link.ILinkedModeListener#suspend(org.eclipse.jface
	 * .text.link.LinkedModeModel)
	 */
	public void suspend(LinkedModeModel environment) {
	}

	/*
	 * @see
	 * org.eclipse.jface.text.link.ILinkedModeListener#resume(org.eclipse.jface
	 * .text.link.LinkedModeModel, int)
	 */
	public void resume(LinkedModeModel environment, int flags) {
	}

}
