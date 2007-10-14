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

	public Element(ElementType type, String name, Element parent) {
		this.type = type;
		this.name = name;
		this.parent = parent;
	}

	public Element(ElementType type, String name) {
		this(type, name, null);
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
		buffer.append("]"); //$NON-NLS-1$
		return buffer.toString();
	}

	/**
	 * This factory constructs elements
	 */
	public static class ElementFactory {

		private ElementFactory() {
		}

		public static final Element createElement(final PHPFileData fileData, boolean isPhpDoc) {
			final Element element = new Element(ElementType.FILE, fileData.getComparableName());
			return isPhpDoc ? createElement(element) : element;
		}

		public static final Element createElement(final PHPClassData classData, boolean isPhpDoc) {
			final Element element = new Element(ElementType.CLASS, classData.getName());
			return isPhpDoc ? createElement(element) : element;
		}

		public static final Element createElement(final PHPFunctionData functionData, boolean isPhpDoc) {
			final Element element = new Element(ElementType.FUNCTION, functionData.getName());
			return isPhpDoc ? createElement(element) : element;
		}

		public static final Element createElement(final PHPClassData classData, final PHPFunctionData functionData, boolean isPhpDoc) {
			final Element parent = createElement(classData, false);
			final Element element = new Element(ElementType.METHOD, functionData.getName(), parent);
			return isPhpDoc ? createElement(element) : element;
		}

		public static final Element createElement(final PHPClassData classData, final PHPClassVarData variableData, boolean isPhpDoc) {
			final Element parent = createElement(classData, false);
			final Element element = new Element(ElementType.FIELD, variableData.getName(), parent);
			return isPhpDoc ? createElement(element) : element;
		}

		public static final Element createElement(final PHPClassData classData, final PHPClassConstData classConstantData, boolean isPhpDoc) {
			final Element parent = createElement(classData, false);
			final Element element = new Element(ElementType.CONSTANT, classConstantData.getName(), parent);
			return isPhpDoc ? createElement(element) : element;
		}

		public static final Element createElement(final Element documentedElement) {
			return new Element(ElementType.DOC, null, documentedElement);
		}

		public static Element createElement(PHPCodeData codeData, boolean isPhpDoc) {
			Element element = null;

			// the container of the code data is used to identify the whole picture
			final PHPCodeData container = codeData.getContainer();

			// file element
			if (codeData instanceof PHPFileData) {
				element = createElement((PHPFileData) codeData, false);

				// class element
			} else if (codeData instanceof PHPClassData) {
				element = createElement((PHPClassData) codeData, false);

			} else if (codeData instanceof PHPFunctionData) {
				// method element
				if (container instanceof PHPClassData) {
					assert container != null;
					element = createElement((PHPClassData) container, (PHPFunctionData) codeData, false);
				} else {

					// function element
					assert container instanceof PHPFileData;
					element = createElement((PHPFunctionData) codeData, false);
				}

				// field element
			} else if (codeData instanceof PHPClassVarData) {
				assert container != null;
				element = createElement((PHPClassData) container, (PHPClassVarData) codeData, false);

				// class constant
			} else if (codeData instanceof PHPClassConstData) {
				assert container != null;
				element = createElement((PHPClassData) container, (PHPClassConstData) codeData, false);

			} else {
				throw new IllegalStateException("Internal Error: CodeData is not supported as folded element"); //$NON-NLS-1$
			}

			return isPhpDoc ? createElement(element) : element;
		}
	}


}
