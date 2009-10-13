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
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.source.IVerticalRulerInfo;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.wst.sse.ui.internal.debug.ToggleBreakpointAction;

/**
 * Toggle breakpoint action that can handle external files.
 * 
 * @author shalom
 * 
 */
public class ToggleExternalBreakpointAction extends ToggleBreakpointAction {

	/**
	 * @param editor
	 * @param rulerInfo
	 */
	public ToggleExternalBreakpointAction(ITextEditor editor,
			IVerticalRulerInfo rulerInfo) {
		super(editor, rulerInfo);
	}

	/**
	 * @param editor
	 * @param rulerInfo
	 * @param fallbackAction
	 */
	public ToggleExternalBreakpointAction(ITextEditor editor,
			IVerticalRulerInfo rulerInfo, IAction fallbackAction) {
		super(editor, rulerInfo, fallbackAction);
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
