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
package org.eclipse.php.internal.core.ast.binding;

/**
 * Represents an attribute in a scope 
 */
public abstract class Attribute {

	/**
	 * Represents the singelton null attribute  
	 */
	public final static NullAttribute NULL_ATTRIBUTE = new NullAttribute();
	/**
	 * Represents the singelton int attribute  
	 */
	public final static IntAttribute INT_ATTRIBUTE = new IntAttribute();
	/**
	 * Represents the singelton string attribute  
	 */
	public final static StringAttribute STRING_ATTRIBUTE = new StringAttribute();
	/**
	 * Represents the singelton bool attribute  
	 */
	public final static BoolAttribute BOOL_ATTRIBUTE = new BoolAttribute();
	/**
	 * Represents the singelton real attribute  
	 */
	public final static RealAttribute REAL_ATTRIBUTE = new RealAttribute();

	private static class NullAttribute extends Attribute {
		public AttributeType getType() {
			return AttributeType.NULL_ATTRIBUTE;
		}
	}

	private static class IntAttribute extends Attribute {
		public AttributeType getType() {
			return AttributeType.INT_ATTRIBUTE;
		}
	}

	private static class StringAttribute extends Attribute {
		public AttributeType getType() {
			return AttributeType.STRING_ATTRIBUTE;
		}
	}

	private static class BoolAttribute extends Attribute {
		public AttributeType getType() {
			return AttributeType.BOOL_ATTRIBUTE;
		}
	}

	private static class RealAttribute extends Attribute {
		public AttributeType getType() {
			return AttributeType.REAL_ATTRIBUTE;
		}
	}

	public abstract AttributeType getType();

	public String toString() {
		return getType().toString();
	}

}
