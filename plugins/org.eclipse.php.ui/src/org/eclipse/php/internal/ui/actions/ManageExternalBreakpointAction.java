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
/**
 * 
 */
package org.eclipse.php.internal.ui.actions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.wst.sse.ui.internal.debug.ManageBreakpointAction;

/**
 * Manage breakpoint action that supports external files.
 * 
 * @author shalom
 * 
 */
public class ManageExternalBreakpointAction extends ManageBreakpointAction {

	/**
	 * @param editor
	 * @param rulerInfo
	 */
	public ManageExternalBreakpointAction(ITextEditor editor,
			IVerticalRuler rulerInfo) {
		super(editor, rulerInfo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.wst.sse.ui.internal.debug.BreakpointRulerAction#hasMarkers()
	 */
	protected boolean hasMarkers() {
		return ExternalBreakpointActionHelper.hasMarkers(getTextEditor(),
				getResource(), getDocument(), getAnnotationModel(),
				getRulerInfo());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.wst.sse.ui.internal.debug.BreakpointRulerAction#getMarkers()
	 */
	protected IMarker[] getMarkers() {
		return ExternalBreakpointActionHelper.getMarkers(getTextEditor(),
				getResource(), getDocument(), getAnnotationModel(),
				getRulerInfo());
	}
}
