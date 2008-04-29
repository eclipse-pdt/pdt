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

import org.eclipse.php.internal.core.phpModel.phpElementData.*;

/**
 * A folded element
 * @author Roy, 2007
 */
public class Element {

	/**
	 * an element type
	 */
	public static enum ElementType {
		FILE("File"), //$NON-NLS-1$
		CLASS("Class"), //$NON-NLS-1$
		FUNCTION("Function"), //$NON-NLS-1$
		METHOD("Method"), //$NON-NLS-1$
		FIELD("Field"), //$NON-NLS-1$
		CONSTANT("Constant"), //$NON-NLS-1$
		DOC("PHP Doc"); //$NON-NLS-1$

		private final String elementTypeName;

		private ElementType(String elementTypeName) {
			this.elementTypeName = elementTypeName;
		}

		public String toString() {
			return this.elementTypeName;
		}
	}

	public final ElementType type;
	public final String name;
	public final Element parent;
	public final int index;
	public int length; // can update the length without other information

	Element(Element parent, ElementType type, String name, int length) {
		this(parent, type, name, length, 0);
	}

	Element(Element parent, ElementType type, String name, int length, int index) {
		this.type = type;
		this.name = name;
		this.parent = parent;
		this.index = index;
		this.length = length;
	}

	public Element(ElementType type, String name, int length) {
		this(null, type, name, length);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (parent == null ? 0 : parent.hashCode());
		result = prime * result + (name == null ? 0 : name.hashCode());
		result = prime * result + (type == null ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		assert obj instanceof Element;
		final Element other = (Element) obj;
		if (parent == null) {
			if (other.parent != null)
				return false;
		} else if (!parent.equals(other.parent))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		else if (index != other.index)
			return false;
		return true;
	}

	public String toString() {
		final StringBuffer buffer = new StringBuffer();
		buffer.append("[Element "); //$NON-NLS-1$
		buffer.append(type.toString());
		buffer.append(": "); //$NON-NLS-1$
		buffer.append(", Name: "); //$NON-NLS-1$
		buffer.append(name);
		buffer.append(", Parent: "); //$NON-NLS-1$
		buffer.append(parent);
		buffer.append(", Index: "); //$NON-NLS-1$
		buffer.append(index);
		buffer.append("]"); //$NON-NLS-1$
		return buffer.toString();
	}

	/**
	 * This factory constructs elements
	 */
	public static class ElementFactory {

		private ElementFactory() {
		}

		public static Element createDocElement(Element container, PHPDocBlock codeData) {
			return new Element(container, ElementType.DOC, container.name, codeData.getEndPosition() - codeData.getStartPosition());
		}

		public static Element createFileElement(PHPFileData codeData) {
			return createElement(null, codeData, 0);
		}

		public static Element createElement(Element container, PHPCodeData codeData, int index) {
			Element element = null;

			// file element
			if (codeData instanceof PHPFileData) {
				element = new Element(ElementType.FILE, codeData.getName(), 0);

				// class element
			} else if (codeData instanceof PHPClassData) {
				element = new Element(container, ElementType.CLASS, codeData.getName(), getLength(codeData), index);

			} else if (codeData instanceof PHPFunctionData) {
				// method element
				if (container.type == ElementType.FILE) {
					element = new Element(container, ElementType.FUNCTION, codeData.getName(), getLength(codeData), index);
				} else {
					// function element
					element = new Element(container, ElementType.METHOD, codeData.getName(), getLength(codeData), index);
				}

				// field element
			} else if (codeData instanceof PHPClassVarData) {
				element = new Element(container, ElementType.FIELD, codeData.getName(), getLength(codeData), index);

				// class constant
			} else if (codeData instanceof PHPClassConstData) {
				assert container != null;
				element = new Element(container, ElementType.CONSTANT, codeData.getName(), getLength(codeData), index);
			} else {
				throw new IllegalStateException("Internal Error: CodeData is not supported as folded element"); //$NON-NLS-1$
			}

			return element;
		}

		private final static int getLength(PHPCodeData codeData) {
			final int len = codeData.getUserData().getEndPosition() - codeData.getUserData().getStartPosition();
			return len < 0 ? 0 : len;
		}
	}

}
