/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
/**
 * 
 */
package org.eclipse.php.internal.ui.actions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.wst.sse.ui.internal.debug.EditBreakpointAction;

/**
 * Edit breakpoint action that supports external files.
 * @author shalom
 *
 */
public class EditExternalBreakpointAction extends EditBreakpointAction {

	/**
	 * @param editor
	 * @param rulerInfo
	 */
	public EditExternalBreakpointAction(ITextEditor editor, IVerticalRuler rulerInfo) {
		super(editor, rulerInfo);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.wst.sse.ui.internal.debug.BreakpointRulerAction#hasMarkers()
	 */
	protected boolean hasMarkers() {
		return ExternalBreakpointActionHelper.hasMarkers(getResource(), getDocument(), getAnnotationModel(), getRulerInfo());
	}

	/* (non-Javadoc)
	 * @see org.eclipse.wst.sse.ui.internal.debug.BreakpointRulerAction#getMarkers()
	 */
	protected IMarker[] getMarkers() {
		return ExternalBreakpointActionHelper.getMarkers(getResource(), getDocument(), getAnnotationModel(), getRulerInfo());
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.wst.sse.ui.internal.debug.EditBreakpointAction#update()
	 */
	public void update() {
		breakpoints = getBreakpoints(getMarkers());
		boolean enableThisAction = hasMarkers() && breakpoints.length > 0;
		setEnabled(enableThisAction);

	}
}
