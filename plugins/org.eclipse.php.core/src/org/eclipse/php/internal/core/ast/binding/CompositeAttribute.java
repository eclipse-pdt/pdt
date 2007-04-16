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

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a set of attributes  
 */
public class CompositeAttribute extends Attribute {

	private final List /*<Attribute>*/attributes = new LinkedList();

	public AttributeType getType() {
		return AttributeType.COMPOSITE_ATTRIBUTE;
	}

	public void addAttribute(Attribute attr) {
		assert attr != null;

		attributes.add(attr);
	}

	public List getAttrributes() {
		return Collections.unmodifiableList(attributes);
	}

}
