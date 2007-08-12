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
import org.eclipse.php.internal.ui.folding.projection.Element.ElementFactory;

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

	public ElementProjectionAnnotation(PHPCodeData codeData, boolean isPhpDoc, boolean collapse) {
		this(ElementFactory.createElement(codeData, isPhpDoc), collapse);
	}

	///////////////
	// Overridden 
	///////////////
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((element == null) ? 0 : element.hashCode());
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
		buffer.append("[Annotation: ");
		buffer.append(this.element.toString());
		buffer.append(", collapse: ");
		buffer.append(isCollapsed());
		buffer.append("]");
		return buffer.toString();
	}
}
