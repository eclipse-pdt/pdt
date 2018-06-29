/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.core.typeinference.evaluators;

import org.eclipse.php.internal.core.typeinference.PHPClassType;

public class PHPTraitType extends PHPClassType {

	public PHPTraitType(String typeName) {
		super(typeName);
	}

	public PHPTraitType(String namespace, String typeName) {
		super(namespace, typeName);
	}

	@Override
	public int hashCode() {
		final int prime = 33;
		int result = 1;
		result = prime * result + ((getNamespace() == null) ? 0 : getNamespace().hashCode());
		result = prime * result + ((getTypeName() == null) ? 0 : getTypeName().hashCode());
		return result;
	}
}
