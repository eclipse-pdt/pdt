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

import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.wst.sse.ui.internal.taginfo.AnnotationHoverProcessor;

public class ProblemHover extends AbstractPHPTextHover {
	
	private static final AnnotationHoverProcessor annotationHover = new AnnotationHoverProcessor();

	public String getHoverInfo(ITextViewer textViewer, IRegion hoverRegion) {
		return annotationHover.getHoverInfo(textViewer, hoverRegion);
	}

	public IRegion getHoverRegion(ITextViewer textViewer, int offset) {
		return annotationHover.getHoverRegion(textViewer, offset);
	}
}
