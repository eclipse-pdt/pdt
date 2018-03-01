/*******************************************************************************
 * Copyright (c) 2007, 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.core.organizeIncludes;

public class CodeDataMatch {

	private String elementName;

	private int elementType;

	/**
	 * @return the elementName
	 */
	public String getElementName() {
		return elementName;
	}

	/**
	 * @return the elementType
	 */
	public int getElementType() {
		return elementType;
	}

	public CodeDataMatch(String elementName, int elementType) {
		this.elementName = elementName;
		this.elementType = elementType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((elementName == null) ? 0 : elementName.hashCode());
		result = prime * result + elementType;
		return result;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (o == this) {
			return true;
		}
		if (!(o instanceof CodeDataMatch)) {
			return false;
		}
		CodeDataMatch codeDataMatch = (CodeDataMatch) o;
		if (codeDataMatch.elementName != elementName) {
			return false;
		}
		if (codeDataMatch.elementType != elementType) {
			return false;
		}
		return true;
	}
}