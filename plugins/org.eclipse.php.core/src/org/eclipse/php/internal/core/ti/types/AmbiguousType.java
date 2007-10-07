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

package org.eclipse.php.internal.core.ti.types;

/**
 * This class represents ambiguous PHP element type. For example,
 * in the following situation: <br/>
 * <pre>
 * 	if (something()) {
 * 		$a = "string";
 * 	} else {
 * 		$a = 5;
 * 	} 
 * </pre> variable $a can be either string or integer (ambiguous type).
 */
public class AmbiguousType implements IEvaluatedType {

	private final IEvaluatedType[] possibleTypes;

	public AmbiguousType(IEvaluatedType[] possibleTypes) {
		if (possibleTypes == null) {
			throw new NullPointerException();
		}
		this.possibleTypes = possibleTypes;
	}
	
	public String toString() {
		StringBuilder buf = new StringBuilder("Ambiguous (");
		for (int i = 0; i < possibleTypes.length; ++i) {
			if (i > 0) {
				buf.append(" | ");
			}
			buf.append(possibleTypes[i].toString());
		}
		buf.append(")");
		return buf.toString();
	}
}
