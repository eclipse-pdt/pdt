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
package org.eclipse.php.internal.ui.folding.projection;

import org.eclipse.jface.text.source.projection.ProjectionAnnotation;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPDocBlock;
import org.eclipse.php.internal.ui.folding.projection.Element.ElementFactory;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;

/**
 * Represents an annotation in a php node
 * @author Roy, 2007
 */
public class ElementProjectionAnnotation extends ProjectionAnnotation {

	// holds the element of this projection annotation
	public final Element element;

	///////////////
	// Constructors
	///////////////
	private ElementProjectionAnnotation(Element element, boolean isCollapsed) {
		super(isCollapsed);
		this.element = element;
	}

	public ElementProjectionAnnotation(Element parentElement, PHPCodeData codeData, int index, boolean collapse) {
		this(ElementFactory.createElement(parentElement, codeData, index), collapse);
	}

	public ElementProjectionAnnotation(Element parentElement, PHPDocBlock codeData, boolean collapse) {
		this(ElementFactory.createDocElement(parentElement, codeData), collapse);
	}

	///////////////
	// Overridden
	///////////////
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (element == null ? 0 : element.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		assert obj instanceof ElementProjectionAnnotation;
		final ElementProjectionAnnotation other = (ElementProjectionAnnotation) obj;
		if (element == null) {
			if (other.element != null)
				return false;
		} else if (!element.equals(other.element))
			return false;
		return true;
	}

	@Override
	public String toString() {
		final StringBuffer buffer = new StringBuffer();
		buffer.append("[Annotation: "); //$NON-NLS-1$
		buffer.append(this.element.toString());
		buffer.append(", collapse: "); //$NON-NLS-1$
		buffer.append(isCollapsed());
		buffer.append("]"); //$NON-NLS-1$
		return buffer.toString();
	}

	private boolean fIsVisible = false; /* workaround for BUG85874 */

	/**
	 * Does not paint hidden annotations. Annotations are hidden when they
	 * only span one line.
	 *
	 * @see ProjectionAnnotation#paint(org.eclipse.swt.graphics.GC,
	 *      org.eclipse.swt.widgets.Canvas,
	 *      org.eclipse.swt.graphics.Rectangle)
	 */
	@Override
	public void paint(GC gc, Canvas canvas, Rectangle rectangle) {
		/* workaround for BUG85874 */
		/*
		 * only need to check annotations that are expanded because hidden
		 * annotations should never have been given the chance to
		 * collapse.
		 */
		if (!isCollapsed()) {
			// working with rectangle, so line height
			FontMetrics metrics = gc.getFontMetrics();
			if (metrics != null) {
				// do not draw annotations that only span one line and
				// mark them as not visible
				if (rectangle.height / metrics.getHeight() <= 1) {
					fIsVisible = false;
					return;
				}
			}
		}
		fIsVisible = true;
		super.paint(gc, canvas, rectangle);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.jface.text.source.projection.ProjectionAnnotation#markCollapsed()
	 */
	@Override
	public void markCollapsed() {
		super.markCollapsed();
	}

}
